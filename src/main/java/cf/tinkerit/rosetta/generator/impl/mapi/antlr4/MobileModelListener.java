package cf.tinkerit.rosetta.generator.impl.mapi.antlr4;

import cf.tinkerit.rosetta.antlr.JavaParser;
import cf.tinkerit.rosetta.antlr.JavaParserBaseListener;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.domain.Clazz;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.domain.Field;
import cf.tinkerit.rosetta.generator.impl.utils.CommonUtil;
import org.antlr.v4.runtime.BufferedTokenStream;

import java.util.ArrayList;

/**
 * mapi model parser listener.
 */
public class MobileModelListener extends JavaParserBaseListener {

    private static final String ANN_NAME_DESCRIPTION = "Description";

    private Clazz clazz;
    private BufferedTokenStream tokens;

    public MobileModelListener(BufferedTokenStream tokens) {
        this.tokens = tokens;
    }

    public Clazz getClazz() {
        return clazz;
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        this.clazz = new Clazz();
        this.clazz.setName(ctx.IDENTIFIER().getText());
        this.clazz.setFields(new ArrayList<>(50));
    }

    @Override
    public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        // ignore non mobile model class such as enum
        if (getClazz() == null) {
            return;
        }
        // ignore static fields
        if (ctx.parent.parent instanceof JavaParser.ClassBodyDeclarationContext) {
            JavaParser.ClassBodyDeclarationContext cbdc
                = (JavaParser.ClassBodyDeclarationContext) ctx.parent.parent;
            boolean isStatic = cbdc.modifier()
                .stream()
                .filter(m -> m.classOrInterfaceModifier() != null)
                .map(JavaParser.ModifierContext::classOrInterfaceModifier)
                .anyMatch(m -> m.STATIC() != null);
            if (isStatic) {
                return;
            }
        }

        Field field = new Field();
        this.getClazz().getFields().add(field);
        // assume one field per one declaration
        field.setName(ctx.variableDeclarators().variableDeclarator().get(0).variableDeclaratorId().IDENTIFIER().getText());
        field.setType(ctx.typeType().getText());
        processAnnotation(ctx, field);
    }

    private void processAnnotation(JavaParser.FieldDeclarationContext ctx, Field field) {
        JavaParser.ClassBodyDeclarationContext cbdc = (JavaParser.ClassBodyDeclarationContext) ctx.parent.parent;
        cbdc.modifier()
            .stream()
            .filter(mc -> {
                JavaParser.AnnotationContext annon = mc.classOrInterfaceModifier().annotation();
                return annon!= null
                        && (ANN_NAME_DESCRIPTION.equals(annon.qualifiedName().getText()));
            })
            .map(mc -> mc.classOrInterfaceModifier().annotation())
            .findFirst()
            .ifPresent(anno -> {
                field.setDescription(CommonUtil.trimDoubleQuote(anno.elementValue().getText()));
            });
    }


}

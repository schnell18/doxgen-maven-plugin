package cf.tinkerit.rosetta.generator.impl.dubbo.antlr4;

import cf.tinkerit.rosetta.antlr.JavaParser;
import cf.tinkerit.rosetta.antlr.JavaParserBaseListener;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.domain.Clazz;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.domain.Field;
import cf.tinkerit.rosetta.antlr.JavaLexer;
import cf.tinkerit.rosetta.generator.impl.utils.CommonUtil;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class DomainModelListener extends JavaParserBaseListener {
    private Clazz clazz;
    private BufferedTokenStream tokens;

    public DomainModelListener(BufferedTokenStream tokens) {
        this.tokens = tokens;
        this.clazz = new Clazz();
        this.clazz.setFields(new ArrayList<>(50));
    }

    public Clazz getClazz() {
        return clazz;
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        this.clazz.setName(ctx.IDENTIFIER().getText());
    }

    @Override
    public void enterEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
        if (ctx.getParent() instanceof JavaParser.TypeDeclarationContext) {
            this.clazz.setName(ctx.IDENTIFIER().getText());
        }
    }

    @Override
    public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
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

            Field field = new Field();
            // assume one field per one declaration
            field.setName(ctx.variableDeclarators().variableDeclarator().get(0).variableDeclaratorId().IDENTIFIER().getText());
            field.setType(ctx.typeType().getText());
            // get field description from comment
            List<Token> cmtTokens = tokens.getHiddenTokensToLeft(cbdc.start.getTokenIndex(), JavaLexer.COMMENTS);
            if (cmtTokens != null && !cmtTokens.isEmpty()) {
                StringBuilder buf = new StringBuilder();
                for (Token cmtToken: cmtTokens) {
                    String cmt = cmtToken.getText();
                    if (cmt != null) {
                        String[] lines = cmt.split(System.lineSeparator());
                        // use first line as field description
                        if (lines.length > 0) {
                            for (String line: lines) {
                                buf.append(CommonUtil.stripCommentMarker(line));
                                buf.append(" ");
                            }
                        }
                    }
                }
                field.setDescription(buf.toString());
            }
            this.clazz.getFields().add(field);
        }

    }
}

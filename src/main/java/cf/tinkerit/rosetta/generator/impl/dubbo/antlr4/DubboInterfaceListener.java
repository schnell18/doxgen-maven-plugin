package cf.tinkerit.rosetta.generator.impl.dubbo.antlr4;

import cf.tinkerit.rosetta.antlr.JavaParser;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.Method;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.Type;
import cf.tinkerit.rosetta.antlr.JavaLexer;
import cf.tinkerit.rosetta.generator.AppDefinedTypeRegistry;
import cf.tinkerit.rosetta.generator.impl.JavaInterfaceListener;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.Interface;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.Parameter;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class DubboInterfaceListener extends JavaInterfaceListener {
    private Interface theInterface;

    public DubboInterfaceListener(AppDefinedTypeRegistry registry, BufferedTokenStream tokens) {
        super(registry, tokens);
    }

    public Interface getTheInterface() {
        return theInterface;
    }

    @Override
    public void enterInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
        theInterface = new Interface();
        theInterface.setMethods(new ArrayList<>(50));
        theInterface.setQualifiedClassName(getQualifiedClassName(ctx.IDENTIFIER().getText()));
        if (ctx.typeParameters() != null) {
            theInterface.setClassName(
                String.format("%s<%s>", ctx.IDENTIFIER(), ctx.typeParameters().getText())
            );
        }
        else {
            theInterface.setClassName(ctx.IDENTIFIER().getText());
        }
    }

    @Override
    public void enterInterfaceMethodDeclaration(JavaParser.InterfaceMethodDeclarationContext ctx) {
        Method method = new Method();
        method.setName(ctx.IDENTIFIER().getText());
        processParameters(ctx, method);
        processReturnType(ctx, method);
        processSignatureAndDescription(ctx, method);
        theInterface.getMethods().add(method);
    }

    private void processSignatureAndDescription(JavaParser.InterfaceMethodDeclarationContext ctx, Method method) {
        StringBuilder sig = new StringBuilder();
        List<Token> cmtTokens = tokens.getHiddenTokensToLeft(ctx.getStart().getTokenIndex(), JavaLexer.COMMENTS);
        if (cmtTokens != null && !cmtTokens.isEmpty()) {
            String cmt = cmtTokens.get(0).getText();
            if (cmt != null) {
                String[] lines = cmt.split(System.lineSeparator());
                // remove leading white spaces
                // and follow Javadoc's convention
                sig.append(lines[0].trim());
                sig.append(System.lineSeparator());
                for (int i = 1; i < lines.length; i++) {
                    sig.append(" ");
                    sig.append(lines[i].trim());
                    sig.append(System.lineSeparator());
                }
                // use second line as method description
                if (lines.length > 1) {
                    method.setDescription(lines[1].replaceFirst("\\*","").trim());
                }
            }
        }
        sig.append(tokens.getText(ctx.getSourceInterval()));
        method.setSignature(sig.toString());
    }

    private void processReturnType(JavaParser.InterfaceMethodDeclarationContext ctx, Method method) {
        // identify type reference in return type
        JavaParser.TypeTypeOrVoidContext ttov = ctx.typeTypeOrVoid();
        if (ttov.typeType() != null) {
            Type returnType = new Type();
            returnType.setType(ttov.typeType().getText());
            // get innermost type
            returnType.setQualifiedType(resolveType(getInnermostType(ttov.typeType())));
            returnType.setNeedXref(looksLikeAppDefinedType(returnType.getQualifiedType()));
            method.setReturnType(returnType);
        }
    }

    private void processParameters(JavaParser.InterfaceMethodDeclarationContext ctx, Method method) {
        JavaParser.FormalParametersContext fps = ctx.formalParameters();
        if (ctx.formalParameters() != null) {
            JavaParser.FormalParameterListContext fpl = fps.formalParameterList();
            if (fpl != null) {
                method.setParameters(new ArrayList<>(30));
                for (JavaParser.FormalParameterContext fp : fpl.formalParameter()) {
                    Parameter parameter = new Parameter();
                    parameter.setName(fp.variableDeclaratorId().IDENTIFIER().getText());
                    String type = getInnermostType(fp.typeType());
                    if (fp.typeType().classOrInterfaceType() != null) {
                        parameter.setQualifiedType(resolveType(type));
                        parameter.setNeedXref(looksLikeAppDefinedType(parameter.getQualifiedType()));
                    }
                    parameter.setType(type);
                    method.getParameters().add(parameter);
                }
            }
        }
    }

}

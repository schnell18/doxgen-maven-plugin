package cf.tinkerit.rosetta.generator.impl.mapi.antlr4;

import cf.tinkerit.rosetta.antlr.JavaLexer;
import cf.tinkerit.rosetta.antlr.JavaParser;
import cf.tinkerit.rosetta.generator.AppDefinedTypeRegistry;
import cf.tinkerit.rosetta.generator.impl.JavaClassCategory;
import cf.tinkerit.rosetta.generator.impl.JavaInterfaceListener;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.Type;
import cf.tinkerit.rosetta.generator.impl.mapi.constants.MapiCommonParamRegistry;
import cf.tinkerit.rosetta.generator.impl.mapi.model.*;
import cf.tinkerit.rosetta.generator.impl.utils.CommonUtil;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * mapi interface parser listner.
 */
public class MobileInterfaceListener extends JavaInterfaceListener {

    private static final String ANN_NAME_API_GROUP = "ApiGroup";

    private static final String ANN_NAME_HTTP_API = "HttpApi";

    private static final String ANN_NAME_DESIGNED_ERROR_CODE = "DesignedErrorCode";

    private static final String ANN_FIELD_NAME_NAME = "name";

    private static final String ANN_FIELD_NAME_MIN_CODE = "minCode";

    private static final String ANN_FIELD_NAME_MAX_CODE = "maxCode";

    private static final String ANN_FIELD_NAME_DESC = "desc";

    private static final String ANN_FIELD_NAME_SECURITY = "security";

    private static final String ANN_FIELD_NAME_REQUIRED = "required";

    private static final String ANN_FIELD_NAME_API_AUTOWIRED = "ApiAutowired";

    private static final String ANN_FIELD_NAME_API_PARAMETER = "ApiParameter";

    private MobileInterface theInterface;

    private JavaClassCategory errorCodeCategory;

    public MobileInterfaceListener(AppDefinedTypeRegistry registry, BufferedTokenStream tokens) {
        super(registry, tokens);
    }

    public void setErrorCodeCategory(JavaClassCategory errorCodeCategory) {
        this.errorCodeCategory = errorCodeCategory;
    }

    public MobileInterface getTheInterface() {
        return theInterface;
    }

    @Override
    public void enterInterfaceMethodDeclaration(JavaParser.InterfaceMethodDeclarationContext ctx) {
        MobileMethod method = new MobileMethod();
        processMethodAnnotation(ctx, method);
        // make sure the method is annotated with @HttpApi
        // otherwise, ignore the method at all
        if (method.getName() == null) {
            return;
        }
        theInterface.getMethods().add(method);
        processParameters(ctx, method);
        processReturnType(ctx, method);
        processSignatureAndDescription(ctx, method);
    }

    @Override
    public void enterInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
        theInterface = new MobileInterface();
        theInterface.setMethods(new ArrayList<>(50));
        theInterface.setQualifiedClassName(getQualifiedClassName(ctx.IDENTIFIER().getText()));
        if (ctx.typeParameters() != null) {
            theInterface.setName(
                String.format("%s<%s>", ctx.IDENTIFIER(), ctx.typeParameters().getText())
            );
        }
        else {
            theInterface.setName(ctx.IDENTIFIER().getText());
        }
        processInterfaceAnnotation(ctx, theInterface);
    }

    private void processInterfaceAnnotation(JavaParser.InterfaceDeclarationContext ctx, MobileInterface theInterface) {
        JavaParser.TypeDeclarationContext ibdc = (JavaParser.TypeDeclarationContext) ctx.parent;
        ibdc.classOrInterfaceModifier()
            .stream()
            .filter(comc -> {
                JavaParser.AnnotationContext annon = comc.annotation();
                return annon!= null
                    && ANN_NAME_API_GROUP.equals(annon.qualifiedName().getText());
            })
            .findFirst()
            .map(JavaParser.ClassOrInterfaceModifierContext::annotation)
            .ifPresent(anno -> {
                anno.elementValuePairs().elementValuePair()
                    .forEach(evpc -> {
                        switch (evpc.IDENTIFIER().getText()) {
                            case ANN_FIELD_NAME_NAME:
                                theInterface.setGroup(CommonUtil.trimDoubleQuote(evpc.elementValue().getText()));
                                break;
                            case ANN_FIELD_NAME_MIN_CODE:
                                theInterface.setMinErrorCode(
                                    Integer.valueOf(CommonUtil.trimDoubleQuote(evpc.elementValue().getText()))
                                );
                                break;
                            case ANN_FIELD_NAME_MAX_CODE:
                                theInterface.setMaxErrorCode(
                                    Integer.valueOf(CommonUtil.trimDoubleQuote(evpc.elementValue().getText()))
                                );
                                break;
                        }
                    });
            });
    }

    private void processMethodAnnotation(JavaParser.InterfaceMethodDeclarationContext ctx, MobileMethod method) {
        JavaParser.InterfaceBodyDeclarationContext ibdc = (JavaParser.InterfaceBodyDeclarationContext) ctx.parent.parent;
        ibdc.modifier()
            .stream()
            .filter(mc -> {
                JavaParser.AnnotationContext annon = mc.classOrInterfaceModifier().annotation();
                return annon!= null
                    && (ANN_NAME_HTTP_API.equals(annon.qualifiedName().getText())
                    ||ANN_NAME_DESIGNED_ERROR_CODE.equals(annon.qualifiedName().getText()));
            })
            .map(mc -> mc.classOrInterfaceModifier().annotation())
            .forEach(anno -> {
                if (ANN_NAME_HTTP_API.equals(anno.qualifiedName().getText())) {
                    processMethodMetaInfo(anno, method);
                }
                else if (ANN_NAME_DESIGNED_ERROR_CODE.equals(anno.qualifiedName().getText())) {
                    processMethodErrorCode(anno, method);
                }
            });
    }

    private void processMethodErrorCode(JavaParser.AnnotationContext anno, MobileMethod method) {
        List<ErrorCode> errorCodes = anno.elementValue().elementValueArrayInitializer().elementValue()
            .stream()
            .map(ev -> toErrorCode(ev.getText()))
            .filter(Objects::nonNull)
            .collect(toList());
        method.setErrorCodes(errorCodes);
    }

    private ErrorCode toErrorCode(String text) {
        if (text == null || !text.contains(".")) {
            return null;
        }
        String type = text.substring(0, text.indexOf("."));
        String value = text.substring(text.indexOf(".") + 1);
        ErrorCodeClazz clazz = (ErrorCodeClazz) getRegistry().getSubRegistry(errorCodeCategory).get(resolveType(type));
        if (clazz != null) {
            return clazz.getByCode(value);
        }
        return null;
    }

    private void processMethodMetaInfo(JavaParser.AnnotationContext anno, MobileMethod method) {
        anno.elementValuePairs().elementValuePair()
            .forEach(evpc -> {
                switch (evpc.IDENTIFIER().getText()) {
                    case ANN_FIELD_NAME_SECURITY:
                        method.setSecurityType(evpc.elementValue().getText());
                        break;
                    case ANN_FIELD_NAME_DESC:
                        method.setDescription(
                            CommonUtil.trimDoubleQuote(evpc.elementValue().getText())
                        );
                        break;
                    case ANN_FIELD_NAME_NAME:
                        method.setName(CommonUtil.trimDoubleQuote(evpc.elementValue().getText()));
                        break;
                }
            });
    }


    private void processSignatureAndDescription(JavaParser.InterfaceMethodDeclarationContext ctx, MobileMethod method) {
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
            }
        }
        sig.append(tokens.getText(ctx.getSourceInterval()));
        method.setSignature(sig.toString());
    }


    private void processReturnType(JavaParser.InterfaceMethodDeclarationContext ctx, MobileMethod method) {
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

    private void processParameters(JavaParser.InterfaceMethodDeclarationContext ctx, MobileMethod method) {
        JavaParser.FormalParametersContext fps = ctx.formalParameters();
        if (fps == null) {
            return;
        }
        JavaParser.FormalParameterListContext fpl = fps.formalParameterList();
        if (fpl == null) {
            return;
        }
        method.setParameters(new ArrayList<>(30));
        for (JavaParser.FormalParameterContext fp : fpl.formalParameter()) {
            MobileParameter parameter = new MobileParameter();
            parameter.setName(fp.variableDeclaratorId().IDENTIFIER().getText());
            processParameterAnnotation(fp, parameter);
            String type = getInnermostType(fp.typeType());
            if (fp.typeType().classOrInterfaceType() != null) {
                parameter.setQualifiedType(resolveType(type));
                parameter.setNeedXref(looksLikeAppDefinedType(parameter.getQualifiedType()));
            }
            parameter.setType(type);
            method.getParameters().add(parameter);
        }
    }

    private void processParameterAnnotation(JavaParser.FormalParameterContext fp, MobileParameter parameter) {
        List<JavaParser.VariableModifierContext> variableModifierContexts = fp.variableModifier();
        if (variableModifierContexts != null && variableModifierContexts.size() > 0) {
            for (JavaParser.VariableModifierContext vmc : variableModifierContexts) {
                JavaParser.AnnotationContext ac = vmc.annotation();
                if (ac != null) {
                    processParameterMetaInfo(ac, parameter);
                }
            }
        }
    }

    private void processParameterMetaInfo(JavaParser.AnnotationContext anno, MobileParameter parameter) {
        String qName = anno.qualifiedName().getText();
        if (ANN_FIELD_NAME_API_AUTOWIRED.equals(qName)) {
            parameter.setAutowired(true);
            String desc = MapiCommonParamRegistry.getDescription(
                CommonUtil.keepLastAfter(anno.elementValue().getText(),".")
            );
            parameter.setDescription(desc);
        }
        else if (ANN_FIELD_NAME_API_PARAMETER.equals(qName)) {
            anno.elementValuePairs().elementValuePair()
                .forEach(evpc -> {
                    switch (evpc.IDENTIFIER().getText()) {
                        case ANN_FIELD_NAME_DESC:
                            parameter.setDescription(
                                CommonUtil.trimDoubleQuote(evpc.elementValue().getText())
                            );
                            break;
                        case ANN_FIELD_NAME_REQUIRED:
                            parameter.setRequired("true".equalsIgnoreCase(evpc.elementValue().getText()));
                            break;
                    }
                });
        }
    }


    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream(new File("D:\\work\\projects\\virtualenv\\backends\\rosetta\\tools\\doc-generator\\src\\test\\resources\\IconService.java"));

        CharStream input = CharStreams.fromStream(is);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();
        // print LISP-style tree
        ParseTreeWalker walker = new ParseTreeWalker();
        MobileInterfaceListener mobileInterfaceListener = new MobileInterfaceListener(null, tokens);
        walker.walk(mobileInterfaceListener, tree);
        System.out.println(mobileInterfaceListener.getTheInterface());
    }

}

package cf.tinkerit.rosetta.generator.impl.mapi.antlr4;

import cf.tinkerit.rosetta.antlr.JavaParser;
import cf.tinkerit.rosetta.antlr.JavaParserBaseListener;
import cf.tinkerit.rosetta.generator.impl.mapi.model.ErrorCode;
import cf.tinkerit.rosetta.generator.impl.mapi.model.ErrorCodeClazz;
import org.antlr.v4.runtime.BufferedTokenStream;

import java.util.HashMap;
import java.util.Map;

import static cf.tinkerit.rosetta.generator.impl.utils.CommonUtil.trimDoubleQuote;


/**
 * mapi model parser listener.
 */
public class MobileDesignedErrorCodeListener extends JavaParserBaseListener {


    private ErrorCodeClazz clazz;

    private Map<String, String> intErrorCodes = new HashMap<>(100);

    //private Map<String, ErrorCode> objErrorCodes = new HashMap<>(100);

    private BufferedTokenStream tokens;


    public MobileDesignedErrorCodeListener(BufferedTokenStream tokens) {
        this.tokens = tokens;
    }

    public ErrorCodeClazz getClazz() {
        return clazz;
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        this.clazz = new ErrorCodeClazz(40);
        this.clazz.setName(ctx.IDENTIFIER().getText());
    }

    @Override
    public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        JavaParser.TypeTypeContext typeTypeContext = ctx.typeType();
        if (typeTypeContext == null) {
            return;
        }
        JavaParser.VariableDeclaratorContext node = ctx.variableDeclarators().variableDeclarator().get(0);
        if (node == null || node.variableInitializer() == null || node.variableDeclaratorId() == null) {
            return;
        }
        if (typeTypeContext.primitiveType() != null) {
            String code = node.variableDeclaratorId().getText();
            String number = node.variableInitializer().getText();
            ErrorCode errorCode = clazz.getByCode(code);
            if (errorCode != null) {
                errorCode.setNumber(number);
            }
            else {
                clazz.addErrorCode(new ErrorCode(code, number, null));
            }
        }
        else {
            JavaParser.CreatorContext rhs = node.variableInitializer().expression().creator();
            if (rhs == null
                || rhs.classCreatorRest() == null
                || rhs.classCreatorRest().arguments() == null
                || rhs.classCreatorRest().arguments().expressionList() == null
                || rhs.classCreatorRest().arguments().expressionList().expression() == null
                || rhs.classCreatorRest().arguments().expressionList().expression().size() != 2) {
                return;
            }
            JavaParser.ExpressionContext arg1 = rhs.classCreatorRest().arguments().expressionList().expression(0);
            JavaParser.ExpressionContext arg2 = rhs.classCreatorRest().arguments().expressionList().expression(1);
            String code =arg2.primary().getText();
            String description = trimDoubleQuote(arg1.primary().getText());
            ErrorCode errorCode = clazz.getByCode(code);
            if (errorCode != null) {
                errorCode.setDescription(description);
            }
            else {
                clazz.addErrorCode(new ErrorCode(code, null, description));
            }
        }
    }
}

package cf.tinkerit.rosetta.generator.impl.dubbo.model;

import cf.tinkerit.rosetta.generator.impl.database.model.Base;
import cf.tinkerit.rosetta.generator.impl.mapi.model.ErrorCode;

import java.util.List;

public class Method extends Base {
    private String signature;
    private List<String> invocationSample;
    private List<Parameter> parameters;
    private Type returnType;
    private List<ErrorCode> errorCodes;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public List<String> getInvocationSample() {
        return invocationSample;
    }

    public void setInvocationSample(List<String> invocationSample) {
        this.invocationSample = invocationSample;
    }

    public List<ErrorCode> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<ErrorCode> errorCodes) {
        this.errorCodes = errorCodes;
    }

    @Override
    public String toString() {
        return "Method{" +
                "signature='" + signature + '\'' +
                ", invocationSample=" + invocationSample +
                ", parameters=" + parameters +
                ", returnType=" + returnType +
                ", errorCodes=" + errorCodes +
                '}';
    }
}

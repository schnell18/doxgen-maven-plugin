package cf.tinkerit.rosetta.generator.impl.mapi.model;


import cf.tinkerit.rosetta.generator.impl.dubbo.model.Method;

public class MobileMethod extends Method {

    private String securityType;

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    @Override
    public String toString() {
        return "MobileMethod{" +
                "securityType='" + securityType + '\'' +
                '}';
    }
}

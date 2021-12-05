package cf.tinkerit.rosetta.generator.impl.mapi.model;


import cf.tinkerit.rosetta.generator.impl.dubbo.model.Parameter;

public class MobileParameter extends Parameter {

    /**
     * 是否自动注入.
     */
    private boolean autowired;

    public boolean getAutowired() {
        return autowired;
    }

    public void setAutowired(boolean autowired) {
        this.autowired = autowired;
    }

    @Override
    public String toString() {
        return "MobileParameter{" +
            "autowired=" + autowired +
            '}';
    }
}

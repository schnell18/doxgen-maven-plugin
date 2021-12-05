package cf.tinkerit.rosetta.generator.impl.mapi.model;

import cf.tinkerit.rosetta.generator.impl.dubbo.model.Interface;

public class MobileInterface extends Interface {

    private String name;

    private String group;

    private Integer minErrorCode;

    private Integer maxErrorCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getMinErrorCode() {
        return minErrorCode;
    }

    public void setMinErrorCode(Integer minErrorCode) {
        this.minErrorCode = minErrorCode;
    }

    public Integer getMaxErrorCode() {
        return maxErrorCode;
    }

    public void setMaxErrorCode(Integer maxErrorCode) {
        this.maxErrorCode = maxErrorCode;
    }

    @Override
    public String toString() {
        return "MobileInterface{" +
            "name='" + name + '\'' +
            ", group='" + group + '\'' +
            ", minErrorCode=" + minErrorCode +
            ", maxErrorCode=" + maxErrorCode +
            '}';
    }
}

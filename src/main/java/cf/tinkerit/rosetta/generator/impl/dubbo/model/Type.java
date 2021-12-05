package cf.tinkerit.rosetta.generator.impl.dubbo.model;

import java.io.Serializable;

public class Type implements Serializable {
    private String type;
    private String qualifiedType;
    private String description;
    private boolean needXref;
    private boolean required;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQualifiedType() {
        return qualifiedType;
    }

    public void setQualifiedType(String qualifiedType) {
        this.qualifiedType = qualifiedType;
    }

    public boolean isNeedXref() {
        return needXref;
    }

    public void setNeedXref(boolean needXref) {
        this.needXref = needXref;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return "Type{" +
                "type='" + type + '\'' +
                ", qualifiedType='" + qualifiedType + '\'' +
                ", description='" + description + '\'' +
                ", needXref=" + needXref +
                ", required=" + required +
                '}';
    }
}

package cf.tinkerit.rosetta.generator.impl.dubbo.model.domain;

import java.io.Serializable;
import java.util.List;

public class Clazz implements Serializable {
    private String name;
    private String qualifiedName;
    private List<Field> fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "name='" + name + '\'' +
                ", qualifiedName='" + qualifiedName + '\'' +
                ", fields=" + fields +
                '}';
    }
}

package cf.tinkerit.rosetta.generator.impl.dubbo.model.domain;

import cf.tinkerit.rosetta.generator.impl.database.model.Base;

public class Field extends Base {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Field{" +
                "type='" + type + '\'' +
                '}';
    }
}

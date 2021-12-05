package cf.tinkerit.rosetta.generator.impl.dubbo.model;

public class Parameter extends Type {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Parameter{" +
                "name='" + name + '\'' +
                '}';
    }
}

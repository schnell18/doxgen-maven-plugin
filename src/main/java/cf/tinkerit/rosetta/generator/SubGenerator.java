package cf.tinkerit.rosetta.generator;

public interface SubGenerator extends Generator {
    String getModuleName();
    CompositeGenerator getParentGenerator();
    void setParentGenerator(CompositeGenerator parentGenerator);
}

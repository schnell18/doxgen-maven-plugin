package cf.tinkerit.rosetta.generator;

abstract public class AbstractCompositeGenerator extends AbstractGenerator implements CompositeGenerator {

    private String targetDirectory;
    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    @Override
    public String getTargetDirectory() {
        return targetDirectory;
    }

    @Override
    public boolean isTopLevel() {
        return true;
    }
}

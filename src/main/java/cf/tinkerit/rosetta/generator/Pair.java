package cf.tinkerit.rosetta.generator;

import java.io.Serializable;

public class Pair implements Serializable {
    private Object objectModel;
    private String outputFile;

    public Pair(Object templateData, String teXFileName) {
        this.objectModel = templateData;
        this.outputFile = teXFileName;
    }

    public Object getObjectModel() {
        return objectModel;
    }

    public void setObjectModel(Object objectModel) {
        this.objectModel = objectModel;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "objectModel=" + objectModel +
                ", outputFile='" + outputFile + '\'' +
                '}';
    }
}

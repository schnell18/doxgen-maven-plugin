package cf.tinkerit.rosetta.generator.impl.database;

import cf.tinkerit.rosetta.generator.AbstractSubGenerator;
import cf.tinkerit.rosetta.generator.impl.utils.CommonUtil;

abstract public class DatabaseDocGenerator extends AbstractSubGenerator {

    // regex of table names to exclude
    private String exculdeTables;

    @Override
    public String getModuleName() {
        return "datamodel";
    }

    protected String getTeXFileName(String tableName) {
        return String.format("%s.tex", CommonUtil.subst(tableName, "_", "-"));
    }

    public String getExculdeTables() {
        return exculdeTables;
    }

    public void setExculdeTables(String exculdeTables) {
        this.exculdeTables = exculdeTables;
    }
}

package cf.tinkerit.rosetta.generator.impl.database.model;


import java.util.List;

public class Table extends Base {
    private List<Column> columns;

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Table{" +
                "columns=" + columns +
                '}';
    }
}

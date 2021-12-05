package cf.tinkerit.rosetta.generator.impl.database.model;

public class Column extends Base {
    private String datatype;
    private int datatypeAsInt;
    private int size;
    private boolean nullable;

    public String getDatatype() {
        return datatype;
    }

    public void setDatatypeAsInt(int datatype) {
        // convert BIT to TINYINT(1)
        if (datatype == SqlType.BIT.value) {
            this.datatypeAsInt = SqlType.TINYINT.value;
        }
        else {
            this.datatypeAsInt = datatype;
        }
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getDataTypeDisplay() {
        if (datatype != null && !"".equalsIgnoreCase(datatype)) {
            if (size == 0)
                return datatype;

            return String.format("%s(%d)", datatype, size);
        }
        else {
            if (datatypeAsInt == SqlType.LONGVARBINARY.value) {
                return size == 65535 ? "BLOB" : SqlType.LONGVARBINARY.name();
            }
            if (datatypeAsInt == SqlType.LONGVARCHAR.value) {
                return size == 65535 ? "TEXT" : SqlType.CLOB.name();
            }
            if (datatypeAsInt == SqlType.TIMESTAMP.value) {
                return size == 19 ? "DATETIME" : SqlType.TIMESTAMP.name();
            }
            return String.format("%s(%d)", SqlType.of(datatypeAsInt).name(), size);
        }
    }

    @Override
    public String toString() {
        return String.format(
            "%s    %s    %s COMMENT '%s'",
            this.getName(),
            getDataTypeDisplay(),
            nullable ? "NULL" : "NOT NULL",
            this.getComment()
        );
    }
}

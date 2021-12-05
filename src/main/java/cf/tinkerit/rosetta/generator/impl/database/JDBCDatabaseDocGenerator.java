package cf.tinkerit.rosetta.generator.impl.database;

import cf.tinkerit.rosetta.generator.impl.database.model.Column;
import cf.tinkerit.rosetta.generator.impl.database.model.Table;
import cf.tinkerit.rosetta.generator.Pair;
import cf.tinkerit.rosetta.generator.logging.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class JDBCDatabaseDocGenerator extends DatabaseDocGenerator {

    private String jdbcUrl;
    private String jdbcUserName;
    private String jdbcPassword;

    @Override
    public Map<String, List<Pair>> prepareTemplateData() {
        Logger logger = getLogger();
        List<Pair> pairs = new ArrayList<>(50);
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(safeGuard(jdbcUrl), jdbcUserName, jdbcPassword);
            List<Table> tables = extractTableInfo(conn);
            Pattern excludePat = null;
            if (getExculdeTables() != null && !"".equalsIgnoreCase(getExculdeTables())) {
                excludePat = Pattern.compile(getExculdeTables());
            }
            for (Table table: tables) {
                if (excludePat != null && excludePat.matcher(table.getName()).matches()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(
                            String.format(
                                "Ignore table %s which matches pattern %s",
                                table.getName(),
                                getExculdeTables()
                            )
                        );
                    }
                    continue;
                }
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Working on %s", table.getName()));
                }
                Map<String, Object> templateData = new HashMap<>();
                // create a TeX file named after tableName
                table.setColumns(extractColumnInfo(conn, table.getName()));
                templateData.put("table", table);
                pairs.add(new Pair(templateData, getTeXFileName(table.getName())));
            }
        }
        catch (SQLException ex1) {
            ex1.printStackTrace();
            throw new RuntimeException(
                String.format("Fail to fetch database meta data"),
                ex1
            );
        }
        finally {
            try {
                conn.close();
            }
            catch (SQLException ex2) {

            }
        }
        Map<String, List<Pair>> tplData = new HashMap<>();
        tplData.put("datamodel", pairs);
        return tplData;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUserName() {
        return jdbcUserName;
    }

    public void setJdbcUserName(String jdbcUserName) {
        this.jdbcUserName = jdbcUserName;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    private List<Column> extractColumnInfo(Connection conn, String name) throws SQLException {
        List<Column> columns = new ArrayList<>(100);
        DatabaseMetaData dbmd = conn.getMetaData();
        if (dbmd != null) {
            ResultSet resultSet = dbmd.getColumns(null, null, name, null);
            while (resultSet.next()) {
                Column column = new Column();
                column.setName(resultSet.getString("COLUMN_NAME"));
                column.setDatatypeAsInt(resultSet.getInt("DATA_TYPE"));
                column.setSize(resultSet.getInt("COLUMN_SIZE"));
                column.setNullable(resultSet.getBoolean("IS_NULLABLE"));
                column.setComment(resultSet.getString("REMARKS"));
                columns.add(column);
            }
        }
        else {
            System.err.println("Metadata not supported");
        }
        return columns;
    }

    private List<Table> extractTableInfo(Connection conn) throws SQLException {
        List<Table> tables = new ArrayList<>(100);
        DatabaseMetaData dbmd = conn.getMetaData();
        if (dbmd != null) {
            ResultSet resultSet = dbmd.getTables(
                null,
                null,
                null,
                new String[]{"TABLE"}
            );
            while (resultSet.next()) {
                Table table = new Table();
                table.setName(resultSet.getString("TABLE_NAME"));
                table.setComment(resultSet.getString("REMARKS"));
                tables.add(table);
            }
        }
        else {
            System.err.println("Metadata not supported");
        }
        return tables;
    }

    private static String safeGuard(String jdbcUrl) {
        if (!jdbcUrl.contains("useInformationSchema=true")) {
            jdbcUrl += "&useInformationSchema=true";
        }
        if (!jdbcUrl.contains("useUnicode=true")) {
            jdbcUrl += "&useUnicode=true";
        }
        if (!jdbcUrl.contains("characterEncoding=utf-8")) {
            jdbcUrl += "&characterEncoding=utf-8";
        }
        return jdbcUrl;
    }

}

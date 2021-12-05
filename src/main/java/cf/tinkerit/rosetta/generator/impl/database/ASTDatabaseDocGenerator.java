package cf.tinkerit.rosetta.generator.impl.database;

import cf.tinkerit.rosetta.generator.impl.database.model.Table;
import cf.tinkerit.rosetta.antlr.MySqlLexer;
import cf.tinkerit.rosetta.antlr.MySqlParser;
import cf.tinkerit.rosetta.generator.AbstractCompositeGenerator;
import cf.tinkerit.rosetta.generator.Pair;
import cf.tinkerit.rosetta.generator.impl.DummyLogger;
import cf.tinkerit.rosetta.generator.impl.database.antlr4.MysqlDatabaseListener;
import cf.tinkerit.rosetta.generator.logging.Logger;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ASTDatabaseDocGenerator extends DatabaseDocGenerator {

    private String schemaFile;

    public String getSchemaFile() {
        return schemaFile;
    }

    public void setSchemaFile(String schemaFile) {
        this.schemaFile = schemaFile;
    }

    @Override
    public Map<String, List<Pair>> prepareTemplateData() {
        Logger logger = getLogger();
        List<Pair> pairs = new ArrayList<>(50);

        try {
            File schema = new File(schemaFile);
            List<Table> tables = parseMysqlTables(new FileInputStream(schema));
            if (tables != null && !tables.isEmpty()) {
                Pattern excludePat = null;
                if (getExculdeTables() != null && !"".equalsIgnoreCase(getExculdeTables())) {
                    excludePat = Pattern.compile(getExculdeTables());
                }
                for (Table table : tables) {
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
                    templateData.put("table", table);
                    pairs.add(new Pair(templateData, getTeXFileName(table.getName())));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(
                String.format("Fail to read %s", schemaFile),
                e
            );
        }

        Map<String, List<Pair>> tplData = new HashMap<>();
        tplData.put("datamodel", pairs);
        return tplData;
    }

    private List<Table> parseMysqlTables(InputStream is) throws IOException {
        CharStream input = CharStreams.fromStream(is);
        MySqlLexer lexer = new MySqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MySqlParser parser = new MySqlParser(tokens);
        ParseTree tree = parser.root();
        ParseTreeWalker walker = new ParseTreeWalker();
        MysqlDatabaseListener listener = new MysqlDatabaseListener();
        walker.walk(listener, tree);
        return listener.getTables();
    }

    public static void main(String[] args) {
        ASTDatabaseDocGenerator generator = new ASTDatabaseDocGenerator();
        generator.setSchemaFile("/Users/zhangfeng775/work/projects/virtualenv/backends/buffalo/schema/schema.sql");
        generator.setParentGenerator(new AbstractCompositeGenerator() {
            @Override
            public String getTargetDirectory() {
                return "buffalo";
            }

            @Override
            public Logger getLogger() {
                return new DummyLogger();
            }
        });
        generator.generate();
    }

}

package cf.tinkerit.rosetta.generator.impl.database.antlr4;

import cf.tinkerit.rosetta.antlr.MySqlParser;
import cf.tinkerit.rosetta.antlr.MySqlParserBaseListener;
import cf.tinkerit.rosetta.generator.impl.database.model.Column;
import cf.tinkerit.rosetta.generator.impl.database.model.Table;
import cf.tinkerit.rosetta.generator.impl.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class MysqlDatabaseListener extends MySqlParserBaseListener {

    private List<Table> tables;

    public MysqlDatabaseListener() {
        this.tables = new ArrayList<>(50);
    }

    public List<Table> getTables() {
        return tables;
    }

    @Override
    public void enterColumnCreateTable(MySqlParser.ColumnCreateTableContext ctx) {
        Table table = new Table();
        table.setColumns(new ArrayList<>(50));

        table.setName(CommonUtil.trimBackQuote(CommonUtil.keepLastAfter(ctx.tableName().getText(), ".")));
        List<MySqlParser.TableOptionContext> optionContexts = ctx.tableOption();
        if (optionContexts != null && optionContexts.size() > 0) {
            optionContexts
                .stream()
                .filter(c -> c instanceof MySqlParser.TableOptionCommentContext)
                .findAny()
                .ifPresent(commentCtx -> table.setComment(
                    CommonUtil.trimSingleQuote(commentCtx.getChild(commentCtx.getChildCount() - 1).getText())
                ));
        }
        processColumnDeclarations(ctx, table);

        tables.add(table);
    }

    private void processColumnDeclarations(MySqlParser.ColumnCreateTableContext ctx, Table table) {
        MySqlParser.CreateDefinitionsContext createDefinitionsCtx = ctx.createDefinitions();
        if (createDefinitionsCtx == null)
            return ;

        List<MySqlParser.ColumnDeclarationContext> columnDelCtxList = getColumnDeclarationContext(createDefinitionsCtx);
        if (columnDelCtxList.size() <= 0)
            return ;

        columnDelCtxList.forEach(cdc -> this.processColumnDeclaration(cdc, table));
    }

    private void processColumnDeclaration(MySqlParser.ColumnDeclarationContext ctx, Table table) {
        MySqlParser.UidContext uidCtx = getUidContext(ctx);
        if (uidCtx == null)
            return ;

        Column column = new Column();
        column.setName(CommonUtil.trimBackQuote(uidCtx.getText()));
        MySqlParser.ColumnDefinitionContext colDefCtx = getColumnDefinitionContext(ctx);
        if (colDefCtx != null) {
            MySqlParser.DataTypeContext dataTypeContext = getDataTypeContext(colDefCtx);
            if (dataTypeContext != null) {
                column.setDatatype(dataTypeContext.getChild(0).getText());
                MySqlParser.LengthOneDimensionContext lengthCxt = getLengthOneDimensionContext(dataTypeContext);
                if (lengthCxt != null) {
                    MySqlParser.DecimalLiteralContext decimalLiteralCtx = getDecimalLiteralContext(lengthCxt);
                    if (decimalLiteralCtx != null)
                        column.setSize(Integer.parseInt(decimalLiteralCtx.getChild(0).getText()));
                }
            }
            MySqlParser.NullColumnConstraintContext nullColumnConstraintCtx = getNullColumnConstraintContext(colDefCtx);
            if (nullColumnConstraintCtx != null) {
                MySqlParser.NullNotnullContext nullNotnullCtx = getNullNotnullContext(nullColumnConstraintCtx);
                column.setNullable(nullNotnullCtx != null && nullNotnullCtx.children.size() == 1);
            }
            MySqlParser.CommentColumnConstraintContext comColConstraintCtx = getCommentColumnConstraintContext(colDefCtx);
            if (comColConstraintCtx != null) {
                String comment = comColConstraintCtx.getChild(comColConstraintCtx.getChildCount() - 1).getText();
                column.setComment(CommonUtil.trimSingleQuote(comment));
            }
        }
        table.getColumns().add(column);
    }

    private List<MySqlParser.ColumnDeclarationContext> getColumnDeclarationContext(MySqlParser.CreateDefinitionsContext ctx) {
        return ctx.children
            .stream()
            .filter(child -> child instanceof MySqlParser.ColumnDeclarationContext)
            .map(child -> (MySqlParser.ColumnDeclarationContext) child)
            .collect(toList());
    }

    private MySqlParser.UidContext getUidContext(MySqlParser.ColumnDeclarationContext ctx) {
        return (MySqlParser.UidContext) ctx.children
            .stream()
            .filter(child -> child instanceof MySqlParser.UidContext)
            .findAny()
            .orElse(null);
    }

    private MySqlParser.ColumnDefinitionContext getColumnDefinitionContext(MySqlParser.ColumnDeclarationContext ctx) {
        return (MySqlParser.ColumnDefinitionContext) ctx.children
            .stream()
            .filter(child -> child instanceof MySqlParser.ColumnDefinitionContext)
            .findAny()
            .orElse(null);
    }

    private MySqlParser.DataTypeContext getDataTypeContext(MySqlParser.ColumnDefinitionContext ctx) {
        return (MySqlParser.DataTypeContext) ctx.children
            .stream()
            .filter(child -> child instanceof MySqlParser.DataTypeContext)
            .findAny()
            .orElse(null);
    }

    private MySqlParser.LengthOneDimensionContext getLengthOneDimensionContext(MySqlParser.DataTypeContext ctx) {
        return (MySqlParser.LengthOneDimensionContext) ctx.children
            .stream()
            .filter(child -> child instanceof MySqlParser.LengthOneDimensionContext)
            .findAny()
            .orElse(null);
    }

    private MySqlParser.DecimalLiteralContext getDecimalLiteralContext(MySqlParser.LengthOneDimensionContext ctx) {
        return (MySqlParser.DecimalLiteralContext) ctx.children
            .stream()
            .filter(child -> child instanceof MySqlParser.DecimalLiteralContext)
            .findAny()
            .orElse(null);
    }

    private MySqlParser.NullColumnConstraintContext getNullColumnConstraintContext(MySqlParser.ColumnDefinitionContext ctx) {
        return (MySqlParser.NullColumnConstraintContext) ctx.children
            .stream()
            .filter(child -> child instanceof MySqlParser.NullColumnConstraintContext)
            .findAny()
            .orElse(null);
    }

    private MySqlParser.NullNotnullContext getNullNotnullContext(MySqlParser.NullColumnConstraintContext ctx) {
        return (MySqlParser.NullNotnullContext) ctx.children
            .stream()
            .filter(child -> child instanceof MySqlParser.NullNotnullContext)
            .findAny()
            .orElse(null);
    }

    private MySqlParser.CommentColumnConstraintContext getCommentColumnConstraintContext(MySqlParser.ColumnDefinitionContext ctx) {
        return (MySqlParser.CommentColumnConstraintContext) ctx.children
            .stream()
            .filter(child -> child instanceof MySqlParser.CommentColumnConstraintContext)
            .findAny()
            .orElse(null);
    }

}

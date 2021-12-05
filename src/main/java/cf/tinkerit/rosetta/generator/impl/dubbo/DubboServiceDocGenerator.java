package cf.tinkerit.rosetta.generator.impl.dubbo;

import cf.tinkerit.rosetta.antlr.JavaParser;
import cf.tinkerit.rosetta.generator.impl.DummyLogger;
import cf.tinkerit.rosetta.generator.impl.JavaDocGenerator;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.domain.Clazz;
import cf.tinkerit.rosetta.antlr.JavaLexer;
import cf.tinkerit.rosetta.generator.AbstractCompositeGenerator;
import cf.tinkerit.rosetta.generator.Pair;
import cf.tinkerit.rosetta.generator.impl.JavaClassCategory;
import cf.tinkerit.rosetta.generator.impl.dubbo.antlr4.DomainModelListener;
import cf.tinkerit.rosetta.generator.impl.dubbo.antlr4.DubboInterfaceListener;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.Interface;
import cf.tinkerit.rosetta.generator.logging.Logger;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class DubboServiceDocGenerator extends JavaDocGenerator {
    private final JavaClassCategory mainCategory = new JavaClassCategory(
        JavaClassCategory.Constants.CAT_DUBBO_INTERFACE,
        (path, cls) -> {
            try {
                return parseDubboServiceInterface(Files.newInputStream(path));
            }
            catch (IOException e) {
                e.printStackTrace();
                System.err.printf("Fail to parse class %s ", cls);
                return null;
            }
        }
    );

    public final JavaClassCategory modelCategory = new JavaClassCategory(
        JavaClassCategory.Constants.CAT_DUBBO_MODEL,
        (path, cls) -> {
            try {
                return parseDomainModel(Files.newInputStream(path), cls);
            }
            catch (IOException e) {
                e.printStackTrace();
                System.err.printf("Fail to parse class %s ", cls);
                return null;
            }
        }
    );

    private String dubboPattern;

    @Override
    public String getModuleName() {
        return "dubbo";
    }

    @Override
    public Map<String, List<Pair>> prepareTemplateData() {
        getLogger().debug("Using Dubbo service interface generator...");
        if (dubboPattern == null) {
            dubboPattern = ".*Service\\.java$";
        }
        Pattern dubboPatternCompiled = Pattern.compile(dubboPattern);
        return super.walk(
            path -> {
                try {
                     if (dubboPatternCompiled.matcher(path.toString()).matches()
                        && Files.readAllLines(path).stream().noneMatch(line -> line.contains("HttpApi"))) {
                         return getMainCategory();
                     }
                     else {
                         return getModelCategory();
                     }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.err.printf("Fail to read file %s ", path);
                    return JavaClassCategory.DUMMY;
                }
            }
        );
    }

    private Interface parseDubboServiceInterface(InputStream is) throws IOException {
        CharStream input = CharStreams.fromStream(is);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        DubboInterfaceListener listener = new DubboInterfaceListener(getRegistry(), tokens);
        walker.walk(listener, tree);
        return listener.getTheInterface();
    }

    private Clazz parseDomainModel(InputStream is, String cls) {
        try {
            CharStream input = CharStreams.fromStream(is);
            JavaLexer lexer = new JavaLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaParser parser = new JavaParser(tokens);
            ParseTree tree = parser.compilationUnit();
            ParseTreeWalker walker = new ParseTreeWalker();
            DomainModelListener listener = new DomainModelListener(tokens);
            walker.walk(listener, tree);
            Clazz clazz = listener.getClazz();
            clazz.setQualifiedName(cls);
            return clazz;
        }
        catch (IOException e) {
            getLogger().error("Fail to extract source for: " + cls, e);
        }
        return null;
    }

    public void setDubboPattern(String dubboPattern) {
        this.dubboPattern = dubboPattern;
    }

    @Override
    public JavaClassCategory getMainCategory() {
        return mainCategory;
    }

    @Override
    public JavaClassCategory getModelCategory() {
        return modelCategory;
    }

    public static void main(String[] args) {
        DubboServiceDocGenerator generator = new DubboServiceDocGenerator();
        generator.setSourceRootDir("/Users/zhangfeng775/work/projects/virtualenv/backends/hippo/hippo-impl/src/main/java");
        generator.setParentGenerator(new AbstractCompositeGenerator() {
            @Override
            public String getTargetDirectory() {
                return "hippo";
            }

            @Override
            public Logger getLogger() {
                return new DummyLogger();
            }
        });
        generator.generate();
    }

}

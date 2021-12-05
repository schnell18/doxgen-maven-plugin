package cf.tinkerit.rosetta.generator.impl.mapi;

import cf.tinkerit.rosetta.antlr.JavaLexer;
import cf.tinkerit.rosetta.antlr.JavaParser;
import cf.tinkerit.rosetta.generator.AbstractCompositeGenerator;
import cf.tinkerit.rosetta.generator.Pair;
import cf.tinkerit.rosetta.generator.impl.DummyLogger;
import cf.tinkerit.rosetta.generator.impl.JavaClassCategory;
import cf.tinkerit.rosetta.generator.impl.JavaDocGenerator;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.domain.Clazz;
import cf.tinkerit.rosetta.generator.impl.mapi.antlr4.MobileDesignedErrorCodeListener;
import cf.tinkerit.rosetta.generator.impl.mapi.antlr4.MobileInterfaceListener;
import cf.tinkerit.rosetta.generator.impl.mapi.antlr4.MobileModelListener;
import cf.tinkerit.rosetta.generator.impl.mapi.model.ErrorCodeClazz;
import cf.tinkerit.rosetta.generator.impl.mapi.model.MobileInterface;
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

public class MobileServiceDocGenerator extends JavaDocGenerator {
    private final JavaClassCategory modelCategory = new JavaClassCategory(
        JavaClassCategory.Constants.CAT_MAPI_MODEL,
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

    private final JavaClassCategory mainCategory = new JavaClassCategory(
        JavaClassCategory.Constants.CAT_MAPI_INTERFACE,
        (path, cls) -> {
            try {
                return parseMobileInterface(Files.newInputStream(path));
            }
            catch (IOException e) {
                e.printStackTrace();
                System.err.printf("Fail to parse class %s ", cls);
                return null;
            }
        }
    );

    private final JavaClassCategory errorCategory = new JavaClassCategory(
        JavaClassCategory.Constants.CAT_MAPI_ERROR_CODE,
        (path, cls) -> {
            try {
                return parseErrorCode(Files.newInputStream(path), cls);
            }
            catch (IOException e) {
                e.printStackTrace();
                System.err.printf("Fail to parse class %s ", cls);
                return null;
            }
        }
    );

    private String mapiPattern;

    public void setMapiPattern(String mapiPattern) {
        this.mapiPattern = mapiPattern;
    }

    @Override
    public String getModuleName() {
        return "mapi";
    }

    @Override
    public Map<String, List<Pair>> prepareTemplateData() {
        getLogger().debug("Using Mobile API service interface generator...");
        if (mapiPattern == null) {
            mapiPattern = ".*Service\\.java$";
        }
        Pattern mapiFilePatternCompiled = Pattern.compile(mapiPattern);
        return super.walk(
            path -> {
                try {
                    if (mapiFilePatternCompiled.matcher(path.toString()).matches()
                        && Files.readAllLines(path).stream().anyMatch(line -> line.contains("HttpApi"))) {
                        return getMainCategory();
                    }
                    else if (Files.readAllLines(path).stream().anyMatch(line -> line.contains("net.pocrd.entity.AbstractReturnCode"))) {
                        return getErrorCategory();
                    }
                    else {
                        return getModelCategory();
                    }
                }
                catch (IOException e) {
                    return JavaClassCategory.DUMMY;
                }
            }
        );

    }

    @Override
    public JavaClassCategory getMainCategory() {
        return mainCategory;
    }

    @Override
    public JavaClassCategory getModelCategory() {
        return modelCategory;
    }

    private JavaClassCategory getErrorCategory() {
        return errorCategory;
    }

    private Clazz parseDomainModel(InputStream is, String cls) {
        try {
            CharStream input = CharStreams.fromStream(is);
            JavaLexer lexer = new JavaLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaParser parser = new JavaParser(tokens);
            ParseTree tree = parser.compilationUnit();
            ParseTreeWalker walker = new ParseTreeWalker();
            MobileModelListener listener = new MobileModelListener(tokens);
            walker.walk(listener, tree);
            Clazz clazz = listener.getClazz();
            if (clazz != null) {
                clazz.setQualifiedName(cls);
            }
            return clazz;
        }
        catch (IOException e) {
            System.err.printf("Fail to extract source for %s ", cls);
        }
        return null;
    }

    private ErrorCodeClazz parseErrorCode(InputStream is, String cls) {
        try {
            CharStream input = CharStreams.fromStream(is);
            JavaLexer lexer = new JavaLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaParser parser = new JavaParser(tokens);
            ParseTree tree = parser.compilationUnit();
            // print LISP-style tree
            ParseTreeWalker walker = new ParseTreeWalker();
            MobileDesignedErrorCodeListener listener = new MobileDesignedErrorCodeListener(tokens);
            walker.walk(listener, tree);
            return listener.getClazz();
        }
        catch (IOException e) {
            System.err.printf("Fail to extract source for %s ", cls);
        }
        return null;
    }

    private MobileInterface parseMobileInterface(InputStream is) throws IOException {
        CharStream input = CharStreams.fromStream(is);
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        MobileInterfaceListener listener = new MobileInterfaceListener(getRegistry(), tokens);
        listener.setErrorCodeCategory(errorCategory);
        walker.walk(listener, tree);
        return listener.getTheInterface();
    }

    public static void main(String[] args) {
        MobileServiceDocGenerator generator = new MobileServiceDocGenerator();

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

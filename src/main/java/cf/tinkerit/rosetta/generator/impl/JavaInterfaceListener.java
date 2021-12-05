package cf.tinkerit.rosetta.generator.impl;

import cf.tinkerit.rosetta.antlr.JavaParser;
import cf.tinkerit.rosetta.antlr.JavaParserBaseListener;
import cf.tinkerit.rosetta.generator.AppDefinedTypeRegistry;
import org.antlr.v4.runtime.BufferedTokenStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaInterfaceListener extends JavaParserBaseListener {
    private static final Map<String, Integer> builtIns = new HashMap<>();
    private AppDefinedTypeRegistry registry;
    static {
        builtIns.put("long", 1);
        builtIns.put("int", 1);
        builtIns.put("boolean", 1);
        builtIns.put("float", 1);
        builtIns.put("double", 1);
        builtIns.put("Long", 1);
        builtIns.put("Integer", 1);
        builtIns.put("Boolean", 1);
        builtIns.put("Float", 1);
        builtIns.put("Double", 1);
        builtIns.put("String", 1);
        builtIns.put("java.util.Date", 1);
    }
    protected BufferedTokenStream tokens;
    protected Map<String, String> imports = new HashMap<>(50);
    protected List<String> wildcardImports = new ArrayList<>(20);
    protected String thisPackage;

    public AppDefinedTypeRegistry getRegistry() {
        return registry;
    }

    public String getThisPackage() {
        return thisPackage;
    }

    public JavaInterfaceListener(AppDefinedTypeRegistry registry, BufferedTokenStream tokens) {
        this.registry = registry;
        this.tokens = tokens;
    }

    @Override
    public void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
        thisPackage = ctx.qualifiedName().getText();
    }

    @Override
    public void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
        String importStmt = ctx.getText();
        String symbol = ctx.qualifiedName().getText();
        if (importStmt.contains("*")) {
            wildcardImports.add(symbol);
        }
        else {
            int idx = symbol.lastIndexOf(".");
            imports.put(symbol.substring(idx + 1), symbol);
        }
    }

    public String getQualifiedClassName(String baseName) {
        if (getThisPackage() == null) {
            return baseName;
        }
        return String.format("%s.%s", getThisPackage(), baseName);
    }
    protected boolean looksLikeAppDefinedType(String qualifiedType) {
        return !qualifiedType.startsWith("java.lang")
                && !builtIns.containsKey(qualifiedType)
                && !qualifiedType.endsWith("Nil")
                && !qualifiedType.endsWith("Void")
                ;
    }

    protected String resolveType(String type) {
        if (builtIns.containsKey(type) || type.contains(".")) {
            return type;
        }
        if (imports.containsKey(type)) {
            return imports.get(type);
        }
        // try to resolve as type defined in the same package as current ckass

        if (thisPackage != null) {
            String packageLocalClass = thisPackage + "." + type;
            if (registry.isTypeDefined(packageLocalClass)) {
                return packageLocalClass;
            }
        }
        // resolve unqualified reference type
        // by concating wild card import package
        for (String pkg : this.wildcardImports) {
            String fqCls = pkg + "." + type;
            if (registry.isTypeDefined(fqCls)) {
                return fqCls;
            }
        }
        return type;
    }

    protected String getInnermostType(JavaParser.TypeTypeContext typeType) {
        if (typeType.primitiveType() != null) {
            return typeType.primitiveType().getText();
        }
        JavaParser.ClassOrInterfaceTypeContext cit = typeType.classOrInterfaceType();
        if (cit != null && cit.typeArguments() != null && !cit.typeArguments().isEmpty()) {
            return getInnermostType(cit.typeArguments().get(0).typeArgument(0).typeType());
        }
        else {
            return cit.IDENTIFIER().get(0).getText();
        }
    }


}

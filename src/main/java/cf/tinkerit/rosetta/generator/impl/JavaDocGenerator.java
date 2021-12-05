package cf.tinkerit.rosetta.generator.impl;

import cf.tinkerit.rosetta.generator.AbstractSubGenerator;
import cf.tinkerit.rosetta.generator.AppDefinedTypeRegistry;
import cf.tinkerit.rosetta.generator.Pair;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.Interface;
import cf.tinkerit.rosetta.generator.impl.dubbo.model.domain.Clazz;
import cf.tinkerit.rosetta.generator.logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;

import static cf.tinkerit.rosetta.generator.impl.utils.CommonUtil.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

abstract public class JavaDocGenerator extends AbstractSubGenerator {
    private String sourceRootDir;
    private static final Pattern javaPattern = Pattern.compile(".*\\.java$");

    private AppDefinedTypeRegistry registry = new AppDefinedTypeRegistry(200);
    private Map<String, Integer> referredDomainModelClasses = new HashMap<>(200);

    public abstract JavaClassCategory getMainCategory();
    public abstract JavaClassCategory getModelCategory();

    public AppDefinedTypeRegistry getRegistry() {
        return registry;
    }

    protected Map<String, List<Pair>> walk(Function<Path, JavaClassCategory> classifier) {
        Map<String, List<Pair>> tplData = new HashMap<>();
        if (sourceRootDir == null || "".equalsIgnoreCase(sourceRootDir)) {
            return tplData;
        }
        Path root = Paths.get(sourceRootDir);
        if (!Files.isDirectory(root)) {
            return tplData;
        }
        Logger logger = getLogger();
        try  {
            Map<JavaClassCategory, List<Path>> map = Files.walk(root)
                .filter(Files::isRegularFile)
                .filter(p -> javaPattern.matcher(p.toString()).matches())
                .collect(groupingBy(classifier));

            List<Path> interfacePaths = map.get(getMainCategory());
            if (interfacePaths == null || interfacePaths.isEmpty()) {
                return tplData;
            }

            map.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(e -> {
                    e.getValue().forEach(path -> {
                        if (logger.isDebugEnabled()) {
                            logger.debug(String.format("Working on %s", path));
                        }
                        String cls = toClassName(
                            forwardSlashPath(path.subpath(root.getNameCount(), path.getNameCount()))
                        );
                        Object parsedModel = e.getKey().getParser().apply(path, cls);
                        if (parsedModel != null) {
                            // some java file is invalid, so no model is generate
                            // we ignore such model here
                            registry.registerType(e.getKey(), cls, parsedModel);
                        }
                    });
                });

            // harvest interface now
            Map<String, Object> subRegistry = registry.getSubRegistry(getMainCategory());
            if (subRegistry == null) {
                return tplData;
            }
            List<Pair> interfacePairs = subRegistry.values()
                .stream()
                .map(o -> {
                    Interface intf = (Interface) o;
                    Map<String, Object> templateData = new HashMap<>();
                    mergeRefTypes(intf.getReferredTypes());
                    templateData.put("interface", intf);
                    return new Pair(templateData, getTeXFileName(intf.getQualifiedClassName()));

                    })
                    .collect(toList());

            tplData.put(getModuleName(), interfacePairs);

            // harvest referenced models
            subRegistry = registry.getSubRegistry(getModelCategory());
            if (subRegistry == null) {
                return tplData;
            }

            List<Clazz> models = new ArrayList<>();
            registry.getSubRegistry(getModelCategory()).forEach((key, value) -> {
                if (referredDomainModelClasses.containsKey(key)) {
                    models.add((Clazz) value);
                }
            });

            if (!models.isEmpty()) {
                List<Pair> domainPairs = new ArrayList<>(100);
                Map<String, Object> templateData = new HashMap<>();
                templateData.put("models", models);
                domainPairs.add(new Pair(templateData, String.format("%s-model.tex", getModuleName())));
                tplData.put("domain", domainPairs);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(
                String.format("Fail to read %s", sourceRootDir),
                e
            );
        }
        return tplData;
    }

    private void mergeRefTypes(List<String> referredTypes) {
        if (referredTypes == null || referredTypes.isEmpty()) {
            return;
        }
        referredTypes.forEach(c -> this.referredDomainModelClasses.put(c, 1));
    }

    public String getSourceRootDir() {
        return sourceRootDir;
    }

    public void setSourceRootDir(String sourceRootDir) {
        this.sourceRootDir = sourceRootDir;
    }

}

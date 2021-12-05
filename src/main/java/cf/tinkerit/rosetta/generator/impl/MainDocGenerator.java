package cf.tinkerit.rosetta.generator.impl;

import cf.tinkerit.rosetta.generator.AbstractCompositeGenerator;
import cf.tinkerit.rosetta.generator.Pair;
import cf.tinkerit.rosetta.generator.SubGenerator;
import cf.tinkerit.rosetta.generator.impl.utils.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class MainDocGenerator extends AbstractCompositeGenerator {

    private List<Author> authors;
    private String version;
    private String appVersion;
    private String title;
    private String subTitle;
    private String revisionTpl;
    private List<String> userContents;

    private List<SubGenerator> generators = new ArrayList<>();

    public void addGenerator(SubGenerator generator) {
        if (generator == null) {
            throw new RuntimeException("Generator should never be null");
        }
        generator.setParentGenerator(this);
        generators.add(generator);
    }

    public String getTemplateFileName() {
        return "manual.ftl";
    }

    @Override
    public Map<String, List<Pair>> prepareTemplateData() {
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("title", this.title);
        templateData.put("subTitle", this.subTitle);
        templateData.put("version", this.version);
        templateData.put("appVersion", this.appVersion);
        templateData.put("authors", this.authors);
        templateData.put("userContents", this.userContents);
        templateData.put("revisionTpl", this.revisionTpl);

        // integrate sub generator's results
        for (SubGenerator generator : generators) {
            if ("dubbo".equalsIgnoreCase(generator.getModuleName())) {
                List<String> dubbos = extractOutputFile(generator, "dubbo");
                if (!dubbos.isEmpty()) {
                    templateData.put("dubbos", dubbos);
                    templateData.put("models", extractOutputFile(generator, "domain"));
                }
            }
            else if ("datamodel".equalsIgnoreCase(generator.getModuleName())) {
                List<String> dms = extractOutputFile(generator, "datamodel");
                if (!dms.isEmpty()) {
                    templateData.put("datamodels", dms);
                }
            }
            else if ("mapi".equalsIgnoreCase(generator.getModuleName())) {
                List<String> mapis = extractOutputFile(generator, "mapi");
                if (!mapis.isEmpty()) {
                    templateData.put("mapis", mapis);
                    templateData.put("mapimodels", extractOutputFile(generator, "domain"));
                }
            }
        }

        String tplFile = getTemplateFileName();
        String basename = tplFile.substring(0, tplFile.length() - 4);
        Pair pair = new Pair(
            templateData,
           basename + ".tex"
        );
        List<Pair> pairs = new ArrayList<>(1);
        pairs.add(pair);
        Map<String, List<Pair>> tplData = new HashMap<>();
        tplData.put(basename, pairs);
        return tplData;
    }

    private List<String> extractOutputFile(SubGenerator generator, String key) {
        Map<String, List<Pair>> computedModel = generator.getComputedModel();
        if (computedModel == null || computedModel.isEmpty() || !computedModel.containsKey(key)) {
            return new ArrayList<>();
        }
        return computedModel.get(key)
            .stream()
            .map(p -> p.getOutputFile().substring(0, p.getOutputFile().length() - 4))
            .map(CommonUtil::quote)
            .collect(toList());
    }

    @Override
    public void generate() {
        for (SubGenerator generator: this.generators) {
            generator.generate();
        }
        super.generate();
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getUserContents() {
        return userContents;
    }

    public void setUserContents(List<String> userContents) {
        this.userContents = userContents;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getRevisionTpl() {
        return revisionTpl;
    }

    public void setRevisionTpl(String revisionTpl) {
        this.revisionTpl = revisionTpl;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}

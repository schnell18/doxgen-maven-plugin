package cf.tinkerit.rosetta.generator;

import cf.tinkerit.rosetta.generator.logging.Logger;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static cf.tinkerit.rosetta.generator.impl.utils.CommonUtil.concat;


abstract public class AbstractGenerator implements Generator {
    private Logger logger;
    private Configuration configuration;
    private Map<String, List<Pair>> computedModel;

    @Override
    public Map<String, List<Pair>> getComputedModel() {
        return this.computedModel;
    }

    public AbstractGenerator() {
        configuration = new Configuration(new Version("2.3.28"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setInterpolationSyntax(Configuration.SQUARE_BRACKET_INTERPOLATION_SYNTAX);
        ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "/");
        String cwd = System.getProperty("user.dir");
        try {
            FileTemplateLoader ftl = new FileTemplateLoader(new File(cwd));
            MultiTemplateLoader mtl = new MultiTemplateLoader(
                new TemplateLoader[] { ftl, ctl }
            );
            configuration.setTemplateLoader(mtl);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to open directory: " + cwd);
        }
    }

    protected Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public void prepare() {
        this.computedModel = prepareTemplateData();
    }

    public Map<String, List<Pair>> prepareTemplateData() {
        return null;
    }

    @Override
    public void generate() {
        prepare();
        Map<String, List<Pair>> tplData = getComputedModel();
        if (tplData == null || tplData.isEmpty()) {
            return;
        }

        for (String key: tplData.keySet()) {
            // create sub directory if necessary
            File subDir = createSubDir(key);
            Template template = prepareTemplate(key);
            for (Pair pair: tplData.get(key)) {
                File outFile = new File(subDir, pair.getOutputFile());
                try (Writer out = new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8)) {
                    template.process(pair.getObjectModel(), out);
                    out.flush();
                }
                catch (IOException e) {
                    throw new RuntimeException(
                        String.format("I/O error while generating content for %s", pair.getOutputFile()),
                        e
                    );
                }
                catch (TemplateException e) {
                    throw new RuntimeException(
                        String.format("Fail to generate content for %s", pair.getOutputFile()),
                        e
                    );
                }
            }

        }
        this.computedModel = tplData;
    }

    private File createSubDir(String key) {
        // create the base directory is it is absent
        String destDir = getTargetDirectory();
        if (destDir == null || "".equals(destDir)) {
            destDir = ".";
        }
        if (!isTopLevel()) {
            destDir = concat(destDir, key);
        }
        File dir = new File(destDir);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException(
                    String.format("Fail to create directory %s", dir)
                );
            }
        }
        return dir;
    }

    public Template prepareTemplate(String tplBaseName) {
        try {
            if (tplBaseName == null || "".equals(tplBaseName)) {
                throw new RuntimeException("Template file is not defined!");
            }
            String tplFile = String.format("%s.ftl", tplBaseName);
            return this.configuration.getTemplate(tplFile);
        }
        catch (IOException e) {
            throw new RuntimeException("Fail to load freemarker template", e);
        }
    }

    @Override
    public boolean isTopLevel() {
        return false;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}

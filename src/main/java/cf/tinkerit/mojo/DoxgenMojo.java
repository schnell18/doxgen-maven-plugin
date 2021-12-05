package cf.tinkerit.mojo;

import cf.tinkerit.rosetta.generator.impl.*;
import cf.tinkerit.rosetta.generator.impl.database.ASTDatabaseDocGenerator;
import cf.tinkerit.rosetta.generator.impl.database.JDBCDatabaseDocGenerator;
import cf.tinkerit.rosetta.generator.impl.dubbo.DubboServiceDocGenerator;
import cf.tinkerit.rosetta.generator.impl.mapi.MobileServiceDocGenerator;
import cf.tinkerit.rosetta.generator.logging.Logger;
import cf.tinkerit.rosetta.generator.SubGenerator;
import cf.tinkerit.rosetta.generator.impl.LogoGenerator;
import cf.tinkerit.rosetta.generator.impl.MakefileGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cf.tinkerit.rosetta.generator.impl.utils.CommonUtil.concat;

/**
 * 生成LaTeX文档
 *
 * @author Zhang Feng
 * @version v1.0 2019/2/20
 **/
@Mojo(name = "generate", aggregator = true, requiresProject = false)
public class DoxgenMojo extends AbstractMojo {

    /**
     * The projects in the reactor.
     */
    @Parameter(defaultValue = "${reactorProjects}", readonly = true)
    private List<MavenProject> projects;

    @Parameter(required = true)
    private String title;

    @Parameter(required = true)
    private String subTitle;

    @Parameter(required = true)
    private String version;

    @Parameter(required = true)
    private List<Author> authors;

    @Parameter
    private List<String> userContents;

    @Parameter
    private String revisionTpl;

    @Parameter
    private String dubboPattern;

    @Parameter
    private String mapiPattern;

    @Parameter
    private String jdbcUrl;

    @Parameter
    private String jdbcUserName;

    @Parameter
    private String jdbcPassword;

    @Parameter
    private String excludeTables;

    @Override
    public void execute() {
        // make sure the awt window does not show up
        System.setProperty("java.awt.headless", "true");
        // locate root project
        // locate dubbo service interface
        // locate mobile api service interface
        // locate database schema file
        List<SubGenerator> generators = new ArrayList<>();
        MavenProject rootProject = projects.get(0);
        for (int i = 1; i < projects.size(); i++) {
            MavenProject p = projects.get(i);
            for (Object obj: p.getCompileSourceRoots()) {
                DubboServiceDocGenerator dubboGenerator = new DubboServiceDocGenerator();
                dubboGenerator.setSourceRootDir(obj.toString());
                dubboGenerator.setDubboPattern(dubboPattern);
                MobileServiceDocGenerator mobileGenerator = new MobileServiceDocGenerator();
                mobileGenerator.setSourceRootDir(obj.toString());
                mobileGenerator.setMapiPattern(mapiPattern);
                generators.add(dubboGenerator);
                generators.add(mobileGenerator);
            }
        }

        MainDocGenerator mainDocGenerator = new MainDocGenerator();
        mainDocGenerator.setLogger(new MavenLogger(getLog()));
        for (SubGenerator subGenerator: generators) {
            mainDocGenerator.addGenerator(subGenerator);
        }

        File schemaFile = new File(rootProject.getBasedir(), concat("schema", "schema.sql"));
        if (schemaFile.exists()) {
            ASTDatabaseDocGenerator dbGenerator = new ASTDatabaseDocGenerator();
            dbGenerator.setExculdeTables(excludeTables);
            dbGenerator.setSchemaFile(schemaFile.toString());
            mainDocGenerator.addGenerator(dbGenerator);
            getLog().debug("Using AST database generator");
        }
        else if (jdbcUrl != null && !"".equalsIgnoreCase(jdbcUrl)) {
            JDBCDatabaseDocGenerator dbGenerator = new JDBCDatabaseDocGenerator();
            dbGenerator.setExculdeTables(excludeTables);
            dbGenerator.setJdbcUrl(jdbcUrl);
            dbGenerator.setJdbcUserName(jdbcUserName);
            dbGenerator.setJdbcPassword(jdbcPassword);
            mainDocGenerator.addGenerator(dbGenerator);
            getLog().debug("Using JDBC database generator");
        }
        mainDocGenerator.addGenerator(new MakefileGenerator());
        mainDocGenerator.addGenerator(new LogoGenerator());
        mainDocGenerator.setTargetDirectory(concat(rootProject.getBasedir().getPath(), "target", "latex"));
        mainDocGenerator.setTitle(title);
        mainDocGenerator.setSubTitle(subTitle);
        mainDocGenerator.setVersion(version);
        mainDocGenerator.setAppVersion(rootProject.getVersion());
        mainDocGenerator.setAuthors(toAuthors(authors));
        mainDocGenerator.setUserContents(userContents);
        mainDocGenerator.setRevisionTpl(revisionTpl);
        mainDocGenerator.generate();

    }

    private List<cf.tinkerit.rosetta.generator.impl.Author> toAuthors(List<Author> authors) {
        if (authors == null || authors.isEmpty()) {
            return new ArrayList<>();
        }
        return authors
            .stream()
            .map(a -> new cf.tinkerit.rosetta.generator.impl.Author(
                a.getName(),
                a.getAffiliation(),
                a.getEmail()
            ))
            .collect(Collectors.toList());
    }

    // This class should always live together with DoxgenMojo
    // NEVER MOVE THIS CLASS TO OTHER PACKAGE
    public static class Author implements Serializable {
        private String name;
        private String affiliation;
        private String email;

        public String getName() {
            return name;
        }

        public String getAffiliation() {
            return affiliation;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public String toString() {
            return "Author{" +
                    "name='" + name + '\'' +
                    ", affiliation='" + affiliation + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

    public static class MavenLogger implements Logger {
        private Log log;

        public MavenLogger(Log log) {
            this.log = log;
        }

        @Override
        public boolean isDebugEnabled() {
            return log.isDebugEnabled();
        }

        @Override
        public void debug(CharSequence var1) {
            log.debug(var1);
        }

        @Override
        public void debug(CharSequence var1, Throwable var2) {
            log.debug(var1, var2);
        }

        @Override
        public void debug(Throwable var1) {
            log.debug(var1);
        }

        @Override
        public boolean isInfoEnabled() {
            return log.isInfoEnabled();
        }

        @Override
        public void info(CharSequence var1) {
            log.info(var1);
        }

        @Override
        public void info(CharSequence var1, Throwable var2) {
            log.info(var1, var2);
        }

        @Override
        public void info(Throwable var1) {
            log.info(var1);
        }

        @Override
        public boolean isWarnEnabled() {
            return log.isWarnEnabled();
        }

        @Override
        public void warn(CharSequence var1) {
            log.warn(var1);
        }

        @Override
        public void warn(CharSequence var1, Throwable var2) {
            log.warn(var1, var2);
        }

        @Override
        public void warn(Throwable var1) {
            log.warn(var1);
        }

        @Override
        public boolean isErrorEnabled() {
            return log.isErrorEnabled();
        }

        @Override
        public void error(CharSequence var1) {
            log.error(var1);
        }

        @Override
        public void error(CharSequence var1, Throwable var2) {
            log.error(var1, var2);
        }

        @Override
        public void error(Throwable var1) {
            log.error(var1);
        }
    }
}

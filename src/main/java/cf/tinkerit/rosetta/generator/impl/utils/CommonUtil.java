package cf.tinkerit.rosetta.generator.impl.utils;

import java.io.File;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    private static final Pattern footnotePat = Pattern.compile("(.*?)[,:，：]\\s*(.*?http://.*)");

    private CommonUtil() {
    }

    public static String quote(String name) {
        if (name == null) {
            return null;
        }
        String result = name;
        if (result.contains("_")) {
            // convert "_" to "\_" requires four backslash
            result = result.replaceAll("_", "\\\\_");
        }
        if (result.contains("#")) {
            result = result.replaceAll("#", "\\\\#");
        }
        if (result.contains("{")) {
            result = result.replaceAll("\\{", "\\\\{");
        }
        if (result.contains("}")) {
            result = result.replaceAll("}", "\\\\}");
        }
        return result;
    }

    public static String subst(String name, String from, String to) {
        if (name.contains(from)) {
            return name.replaceAll(from, to);
        }
        return name;
    }

    public static String wrapFootnote(String name) {
        Matcher matcher = footnotePat.matcher(name);
        if (matcher.matches()) {
            return String.format(
                "%s\\footnote{%s}",
                matcher.group(1),
                matcher.group(2)
            );
        }
        else {
            return name;
        }
    }

    public static String getTeXFileName(String className) {
        if (className == null) {
            return null;
        }

        String version = "";
        String[] comps = className.split("\\.");
        for (String comp : comps) {
            if (comp.matches("^[v|V]\\d$")) {
                version = comp;
                break;
            }
        }
        int lastSlashIdx = className.lastIndexOf('.');
        return String.format(
                "%s%s.%s",
                className.substring(lastSlashIdx + 1),
                version.toUpperCase(),
                "tex"
        );
    }

    public static String toClassName(String fileName) {
        return fileName.substring(0, fileName.length() - 5).replaceAll("/", ".");
    }

    public static String stripCommentMarker(String line) {
        if (line != null) {
            return line.replaceAll("^//(/)*\\s*", "")
                .replaceAll("^/(\\*)+\\s*", "")
                .replaceAll("\\s*(\\*)+/$", "")
                .replaceAll("^\\s*(\\*)+\\s*", "");
        }
        return line;

    }

    public static String trimSingleQuote(String comment) {
        return trimQuote(comment, "'");
    }

    public static String trimDoubleQuote(String comment) {
        return trimQuote(comment, "\"");
    }

    public static String trimBackQuote(String comment) {
        return trimQuote(comment, "`");
    }

    public static String trimQuote(String comment, String quote) {
        if (comment != null && comment.startsWith(quote) && comment.endsWith(quote))
            return comment.substring(1, comment.length() - 1);
        return comment;
    }

    public static String concat(String... args) {
        if (args != null && args.length >= 1) {
            StringBuilder buf = new StringBuilder();
            buf.append(args[0]);
            for (int i = 1; i < args.length ; i++) {
                buf.append(File.separator);
                buf.append(args[i]);
            }
            return buf.toString();
        }
        return null;
    }


    public static String forwardSlashPath(Path path) {
        StringBuilder buf = new StringBuilder();
        buf.append(path.getName(0));
        for (int i = 1; i < path.getNameCount(); i++) {
            buf.append("/");
            buf.append(path.getName(i));
        }
        return buf.toString();
    }

    public static String keepLastAfter(String qualifiedParam, String delimmiter) {
        if (qualifiedParam != null && qualifiedParam.contains(delimmiter) && !qualifiedParam.endsWith(delimmiter)) {
            int idx = qualifiedParam.indexOf(delimmiter);
            return qualifiedParam.substring(idx + 1);
        }
        return qualifiedParam;
    }



}

package cf.tinkerit.rosetta.generator;

import cf.tinkerit.rosetta.generator.logging.Logger;

import java.util.List;
import java.util.Map;

public interface Generator {

    /**
     * Prepare data required for generation.
     *
     */
    void prepare();

    /**
     * Generate the target files by blending data and template
     */
    void generate();

    /**
     * This method can be invoked only after successful call of prepare().
     * @return computed data model by invocation of prepare()
     */
    Map<String, List<Pair>> getComputedModel();

    /**
     * Base directory where to put every generated files
     * @return
     */
    String getTargetDirectory();

    /**
     * Get a Logger instance for logging.
     * @return
     */
    Logger getLogger();

    /**
     * Generate file under top level directory
     * @return true: do not create sub directory, false: otherwise
     */
    boolean isTopLevel();
}

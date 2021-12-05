package cf.tinkerit.rosetta.generator.impl;

import cf.tinkerit.rosetta.generator.AbstractSubGenerator;
import cf.tinkerit.rosetta.generator.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakefileGenerator extends AbstractSubGenerator {

    @Override
    public String getModuleName() {
        return "Makefile";
    }

    @Override
    public Map<String, List<Pair>> prepareTemplateData() {
        List<Pair> pairs = new ArrayList<>(1);
        Map<String, Object> templateData = new HashMap<>();
        pairs.add(new Pair(templateData, getModuleName()));

        Map<String, List<Pair>> tplData = new HashMap<>();
        tplData.put("Makefile", pairs);
        return tplData;
    }

    @Override
    public boolean isTopLevel() {
        return true;
    }
}

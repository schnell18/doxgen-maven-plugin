package cf.tinkerit.rosetta.generator;

import cf.tinkerit.rosetta.generator.impl.JavaClassCategory;

import java.util.HashMap;
import java.util.Map;

public class AppDefinedTypeRegistry {
    private Map<JavaClassCategory, Map<String, Object>> appDefinedTypeRegistry;

    public AppDefinedTypeRegistry(int size) {
        this.appDefinedTypeRegistry = new HashMap<>(size);
    }

    public Map<String, Object> getSubRegistry(JavaClassCategory cat) {
        return appDefinedTypeRegistry.get(cat);
    }

    public void registerType(JavaClassCategory key, String toClassName, Object parsedModel) {
        Map<String, Object> map = this.appDefinedTypeRegistry.computeIfAbsent(key, k -> new HashMap<>());
        map.put(toClassName, parsedModel);
    }

    public boolean isTypeDefined(String fqCls) {
        for (JavaClassCategory cat : this.appDefinedTypeRegistry.keySet()) {

            if (this.appDefinedTypeRegistry.get(cat).containsKey(fqCls)) {
                return true;
            }
        }
        return false;
    }
}

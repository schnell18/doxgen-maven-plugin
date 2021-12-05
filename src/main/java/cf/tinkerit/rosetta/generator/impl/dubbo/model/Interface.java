package cf.tinkerit.rosetta.generator.impl.dubbo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Interface implements Serializable {
    private String className;
    private String qualifiedClassName;
    private List<Method> methods;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getQualifiedClassName() {
        return qualifiedClassName;
    }

    public void setQualifiedClassName(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public List<String> getReferredTypes() {
        List<String> types = new ArrayList<>(200);
        if (methods != null) {
            for (Method method: methods) {
                // check return type
                if (method.getReturnType() != null && method.getReturnType().isNeedXref()) {
                    types.add(method.getReturnType().getQualifiedType());
                }

                // check parameters
                List<Parameter> params = method.getParameters();
                if (params != null) {
                    for (Parameter param : params) {
                        if (param.isNeedXref()) {
                            types.add(param.getQualifiedType());
                        }
                    }
                }
            }
        }
        return types;
    }

    @Override
    public String toString() {
        return "Interface{" +
                "className='" + className + '\'' +
                ", qualifiedClassName='" + qualifiedClassName + '\'' +
                ", methods=" + methods +
                '}';
    }
}

package cf.tinkerit.rosetta.generator.impl.mapi.model;

import cf.tinkerit.rosetta.generator.impl.dubbo.model.domain.Clazz;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodeClazz extends Clazz {

    private Map<String, ErrorCode> errorCodeMap;


    public ErrorCodeClazz(int size) {
        this.errorCodeMap = new HashMap<>(size);
    }

    public void addErrorCode(ErrorCode errorCode) {
        if (errorCode != null) {
            this.errorCodeMap.put(errorCode.getName(), errorCode);
        }
    }

    public ErrorCode getByCode(String errorCode) {
        return errorCodeMap.get(errorCode);
    }

}

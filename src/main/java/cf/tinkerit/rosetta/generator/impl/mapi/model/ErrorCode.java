package cf.tinkerit.rosetta.generator.impl.mapi.model;

import cf.tinkerit.rosetta.generator.impl.database.model.Base;

/**
 * mapi 错误编号模型.
 */
public class ErrorCode extends Base {

    /**
     * 错误代码.
     */
    private String number;

    public ErrorCode() {
    }

    public ErrorCode(String name, String number, String description) {
        super();
        super.setName(name);
        super.setDescription(description);
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "number='" + number + '\'' +
                '}';
    }
}

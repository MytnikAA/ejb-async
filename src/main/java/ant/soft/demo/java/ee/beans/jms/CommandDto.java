package ant.soft.demo.java.ee.beans.jms;

import java.io.Serializable;

public class CommandDto<P extends Serializable> implements Serializable {

    private static final long serialVersionUID = -6308667983964073575L;

    private String code;

    private P params;

    @Override
    public String toString() {
        return String.format("code='%s', params=%s", code, params);
    }

    public CommandDto() {
    }

    public CommandDto(String code, P params) {
        this.code = code;
        this.params = params;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public P getParams() {
        return params;
    }

    public void setParams(P params) {
        this.params = params;
    }
}

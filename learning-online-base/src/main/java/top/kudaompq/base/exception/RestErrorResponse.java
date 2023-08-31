package top.kudaompq.base.exception;

import java.io.Serializable;

/**
 * @description: 异常信息模型
 * @author: kudaompq
 **/
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}

package top.kudaompq.base.exception;

/**
 * @description: 在线学习异常类
 * @author: kudaompq
 **/
public class LeaningOnlineException extends RuntimeException{

    private String errMessage;


    public LeaningOnlineException() {
        super();
    }

    public LeaningOnlineException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public static void cast(CommonError commonError){
        throw new LeaningOnlineException(commonError.getErrMessage());
    }

    public static void cast(String errMessage){
        throw new LeaningOnlineException(errMessage);
    }
}

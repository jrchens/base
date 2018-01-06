package cn.jrry.util;

public class ExceptionUtils {
    public static String getSimpleMessage(Exception ex){
        String msg = ex.getMessage();
        if(msg != null){
            int idx = msg.indexOf(":");
            if(idx != -1){
                msg = msg.substring(idx+1);
            }
        }
        return msg;
    }
}

package exceptions;
/*
 * Ngoại lệ tùy chỉnh kế thừa từ Exception của java
 */

public class GameException extends Exception{
    public GameException(){
    }

    public GameException(String str){
        super(str);
    }

    public GameException(Throwable cause){
        super(cause);
    }

    public GameException(String str, Throwable cause){
        super(str, cause);
    }


}
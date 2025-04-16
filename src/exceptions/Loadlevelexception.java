package exceptions;

/*
 * Lớp ngoại lệ tùy chỉnh trong game
 * Xử lý lối liên quan đến việc tải màn chơi
 */

public class Loadlevelexception extends GameException{
    public Loadlevelexception(){
    }

    public Loadlevelexception(String str){
        super(str);
    }

    public Loadlevelexception(Throwable cause){
        super(cause);
    }

    public Loadlevelexception(String str, Throwable cause){
        super(str, cause);
    }
}
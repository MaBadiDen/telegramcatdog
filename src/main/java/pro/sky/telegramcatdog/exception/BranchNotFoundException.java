package pro.sky.telegramcatdog.exception;

public class BranchNotFoundException extends RuntimeException{
    private int id;

    public BranchNotFoundException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

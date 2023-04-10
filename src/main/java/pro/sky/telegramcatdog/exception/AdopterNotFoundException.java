package pro.sky.telegramcatdog.exception;

public class AdopterNotFoundException extends RuntimeException{
    private long id;

    public AdopterNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

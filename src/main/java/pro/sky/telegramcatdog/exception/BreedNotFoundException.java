package pro.sky.telegramcatdog.exception;

public class BreedNotFoundException extends RuntimeException {
    private long id;

    public BreedNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

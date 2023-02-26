package pro.sky.telegramcatdog.exception;

public class VolunteerNotFoundException extends RuntimeException {

    private int id;

    public VolunteerNotFoundException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

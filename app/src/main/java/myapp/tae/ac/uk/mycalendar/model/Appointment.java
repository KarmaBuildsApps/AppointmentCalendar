package myapp.tae.ac.uk.mycalendar.model;

/**
 * Created by Karma on 01/04/16.
 */
public class Appointment {
    private int id;
    private String title;
    private String detail;
    private int date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}

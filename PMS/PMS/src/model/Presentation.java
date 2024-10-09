package model;

import java.io.Serializable;
import java.sql.Date;

public class Presentation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Date date;
    private String topic;

    public Presentation(String id, Date date, String topic) {
        this.id = id;
        this.date = date;
        this.topic = topic;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}

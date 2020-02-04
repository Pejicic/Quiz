package rs.ac.uns.quiz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ErrorDto {

    protected String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE MMM dd HH:mm:ss zzz yyyy")
    private final Date date;

    public ErrorDto(String message) {
        this.message=message;
        this.date = new Date();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }
}

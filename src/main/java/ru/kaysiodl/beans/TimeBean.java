package ru.kaysiodl.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named("time")
@SessionScoped
public class TimeBean implements Serializable {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private LocalDateTime currentTime = LocalDateTime.now();

    public String getDateTime() {
        return currentTime.format(FORMATTER);
    }

    public void update() {
        currentTime = LocalDateTime.now();
    }
}

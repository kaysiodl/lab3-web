package ru.kaysiodl.beans;

import ru.kaysiodl.database.Result;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Named("check")
@SessionScoped
@Data
public class CheckBean implements Serializable {
    private static final double MAX_VALUE = 5.0;
    private static final double MIN_VALUE = -3.0;
    private Double x;
    private Double y;
    private Double r;

    @Inject
    private ResultsBean resultsBean;

    public void save() {
        Long startTime = System.nanoTime();
        boolean hit = checkHit(x, y, r);
        Long endTime = System.nanoTime();

        resultsBean.add(Result
                .builder()
                .x(x)
                .y(y)
                .r(r)
                .hit(hit)
                .currentTime(String.valueOf(LocalTime.now(ZoneId.of("Europe/Moscow"))
                        .withNano(0)))
                .executionTime(String.format("%.3f ms", (endTime - startTime) / 1_000_000.0)).build());
    }

    public boolean checkHit(double x, double y, double r) {
        return ((x * x + y * y <= (r * r)) && x >= 0 && y <= 0) || // sector
                (x >= 0 && x <= r/2 && y <= r && y >= 0) || //square
                ((y <= x/2 + r/2) && x <= 0 && y >= 0); //triangle
    }

}

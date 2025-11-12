package ru.kaysiodl.services.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Result implements Serializable {
    Double x;
    Double y;
    Double r;
    boolean hit;
    String currentTime;
    String time;
}

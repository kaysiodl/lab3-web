package ru.kaysiodl.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import ru.kaysiodl.services.ResultsService;

import java.io.Serializable;

@Named("canvas")
@SessionScoped
@Data
public class CanvasBean implements Serializable {
    private Double x;

    private Double y;

    private Double r;

    @Inject
    private ResultsBean resultsBean;

    private ResultsService resultsService = new ResultsService();

    public void save(){
        resultsService.save(x, y, r, resultsBean);
    }
}

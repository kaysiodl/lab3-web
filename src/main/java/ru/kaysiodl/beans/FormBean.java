package ru.kaysiodl.beans;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import ru.kaysiodl.database.Result;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;
import ru.kaysiodl.services.ResultsService;

import java.io.Serializable;

@Named("form")
@SessionScoped
@Data
public class FormBean implements Serializable {
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

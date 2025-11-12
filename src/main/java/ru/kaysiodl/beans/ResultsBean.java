package ru.kaysiodl.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import ru.kaysiodl.services.response.Result;

import java.io.Serializable;
import java.util.ArrayList;

@Setter
@Getter
@Named("results")
@SessionScoped
public class ResultsBean implements Serializable {
    ArrayList<Result> results = new ArrayList<>();

    public void clear() {
        this.results.clear();
    }

    public void add(Result result) {
        this.results.add(result);
    }
}

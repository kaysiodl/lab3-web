package ru.kaysiodl.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import ru.kaysiodl.services.response.Result;

import java.io.Serializable;
import java.util.ArrayList;

@Setter
@Getter
@Named("results")
@SessionScoped
public class ResultsBean implements Serializable {
    ArrayList<Result> results;

    public void clear() {
        results = new ArrayList<>();
    }

    public void add(Result result) {
        results.add(result);
    }
}

package ru.kaysiodl.beans;

import com.google.gson.Gson;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import ru.kaysiodl.database.ResultsRepository;
import ru.kaysiodl.database.Result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Named("results")
@SessionScoped
public class ResultsBean implements Serializable {
    @Inject
    private ResultsRepository repository;

    private List<Result> resultsCache = new ArrayList<>();

    public void add(Result result) {
        this.resultsCache.add(result);
        repository.add(result);
    }

    public void clear() {
        this.resultsCache.clear();
        repository.clear();
    }

    public List<Result> getResults() {
        return repository.getAll();
    }

    public String getJsonResults() {
        Gson gson = new Gson();
        return gson.toJson(resultsCache);
    }

}

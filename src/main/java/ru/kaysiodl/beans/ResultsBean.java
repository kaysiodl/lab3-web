package ru.kaysiodl.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import ru.kaysiodl.database.ResultsRepository;
import ru.kaysiodl.database.Result;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Named("results")
@SessionScoped
public class ResultsBean implements Serializable {
    @Inject
    private ResultsRepository repository;

    public void add(Result result) {
        repository.add(result);
    }

    public void clear() {
        repository.clear();
    }

    public List<Result> getResults() {
        return repository.getAll();
    }
}

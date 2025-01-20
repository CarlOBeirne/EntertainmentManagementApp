package com.pluralsight.dao;

import java.util.List;
import java.util.Optional;

public interface DaoInterface<T> {
    Optional<T> save(T t);
    Optional<T> getById(int id);
    List<T> getAll();
    void delete(int id);
}

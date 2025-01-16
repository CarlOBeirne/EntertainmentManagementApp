package com.pluralsight.dao;

import java.util.List;

public interface DaoInterface<T> {
    T save(T t);
    T getById(int id);
    List<T> getAll();
    void delete(int id);
}

package com.damian.javee.dao;

import com.damian.javee.entity.SuperEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SuperDAO<T extends SuperEntity, ID> {
    boolean add(T t);

    boolean update(T t);

    boolean delete(ID id);

    Optional<T> search(ID id);

    List<T> getAll();
}

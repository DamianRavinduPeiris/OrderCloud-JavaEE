package com.damian.javee.service;

import com.damian.javee.dto.SuperDTO;

import java.util.ArrayList;
import java.util.Optional;

public interface SuperService<T extends SuperDTO,ID> {
    boolean add(T t);

    boolean update(T t);

    boolean delete(ID id);

    Optional<T>search(ID id);

    ArrayList<T> getAll();
}

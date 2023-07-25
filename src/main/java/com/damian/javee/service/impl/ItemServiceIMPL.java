package com.damian.javee.service.impl;

import com.damian.javee.dao.impl.ItemDAOIMPL;
import com.damian.javee.dao.util.DAOFactory;
import com.damian.javee.dao.util.DAOTypes;
import com.damian.javee.dto.Item_Dto;
import com.damian.javee.entity.Item;
import com.damian.javee.service.custom.ItemService;
import com.damian.javee.util.Convertor;

import java.util.ArrayList;
import java.util.Optional;

public class ItemServiceIMPL implements ItemService {
    @Override
    public boolean add(Item_Dto itemDto) {
        ItemDAOIMPL dao = DAOFactory.getDAO(DAOTypes.ITEM_DAO);
        return dao.add(Convertor.convertToItem(itemDto));
    }

    @Override
    public boolean update(Item_Dto itemDto) {
        ItemDAOIMPL dao = DAOFactory.getDAO(DAOTypes.ITEM_DAO);
        return dao.update(Convertor.convertToItem(itemDto));
    }

    @Override
    public boolean delete(String s) {
        ItemDAOIMPL dao = DAOFactory.getDAO(DAOTypes.ITEM_DAO);
        return dao.delete(s);
    }

    @Override
    public Optional<Item_Dto> search(String s) {
        ItemDAOIMPL dao = DAOFactory.getDAO(DAOTypes.ITEM_DAO);
        Optional<Item> item = dao.search(s);
        return item.isPresent() ? Optional.of(Convertor.convertToItemDTO(item.get())) : Optional.empty();

    }

    @Override
    public ArrayList<Item_Dto> getAll() {
        ItemDAOIMPL dao = DAOFactory.getDAO(DAOTypes.ITEM_DAO);
        return dao.getAll() == null ? null : Convertor.convertToItemDTOList(dao.getAll());
    }
}

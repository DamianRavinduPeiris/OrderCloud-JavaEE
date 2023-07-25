package com.damian.javee.dao.util;

import com.damian.javee.dao.impl.CustomerDAOIMPL;
import com.damian.javee.dao.impl.ItemDAOIMPL;
import com.damian.javee.dao.impl.OrderDAOIMPL;

public class DAOFactory {
    public static <T> T getDAO(DAOTypes daoType) {
        switch (daoType) {
            case CUSTOMER_DAO:
                return (T) new CustomerDAOIMPL();
            case ITEM_DAO:
                return (T) new ItemDAOIMPL();
            case ORDER_DAO:
                return (T) new OrderDAOIMPL();
            default:
                return null;
        }

    }
}

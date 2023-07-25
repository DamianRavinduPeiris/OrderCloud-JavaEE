package com.damian.javee.dao.impl;

import com.damian.javee.dao.custom.ItemDAO;
import com.damian.javee.entity.Item;
import com.damian.javee.util.FactoryConfiguration;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Optional;

public class ItemDAOIMPL implements ItemDAO {
    private static String error_Info = "";

    public static String getError_Info() {
        return error_Info;
    }

    public void setError_Info(String e) {
        error_Info = e;

    }

    @Override
    public boolean add(Item item) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        try {
            session.save(item);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            setError_Info(e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public boolean update(Item item) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        try {
            session.update(item);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            setError_Info(e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        try {
            session.createQuery("delete from Item where item_id = :s").setParameter("s", s).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            setError_Info(e.getLocalizedMessage());
            return false;
        }
        return true;


    }

    @Override
    public Optional<Item> search(String s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        try {
            Item item = session.createQuery("from Item where item_name = :s", Item.class).setParameter("s", s).getSingleResult();
            return Optional.of(item);
        } catch (Exception e) {
            session.getTransaction().rollback();
            setError_Info(e.getLocalizedMessage());
            return Optional.empty();
        }

    }

    @Override
    public ArrayList<Item> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();

        try {
            ArrayList<Item> itemsList = (ArrayList<Item>) session.createQuery("from Item ").list();
            return itemsList;

        } catch (Exception e) {
            session.getTransaction().rollback();
            setError_Info(e.getLocalizedMessage());
        }
        return null;

    }


}

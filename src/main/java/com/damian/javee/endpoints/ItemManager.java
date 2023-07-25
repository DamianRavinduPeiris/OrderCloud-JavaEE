package com.damian.javee.endpoints;

import com.damian.javee.dao.impl.ItemDAOIMPL;
import com.damian.javee.dto.Item_Dto;
import com.damian.javee.service.impl.ItemServiceIMPL;
import com.damian.javee.service.util.ServiceFactory;
import com.damian.javee.service.util.ServiceTypes;
import com.damian.javee.util.GSONConfiguration;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/item-manager"})
public class ItemManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("item_name").equals("fetchAll")) {
            fetchAll(req, resp);
            return;

        }
        setHeaders(resp);
        System.out.println("fetching item");

        ItemServiceIMPL service = ServiceFactory.getService(ServiceTypes.ITEM_SERVICE);
        Optional<Item_Dto> item = service.search(req.getParameter("item_name"));
        System.out.println(req.getParameter("item_name"));
        if (item.isPresent()) {
            String json = GSONConfiguration.getInstance().getGSON().toJson(item.get());
            resp.getWriter().println(json);

        } else {
            resp.getWriter().println(ItemDAOIMPL.getError_Info());
        }


    }

    private void fetchAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setHeaders(resp);
        ItemServiceIMPL service = ServiceFactory.getService(ServiceTypes.ITEM_SERVICE);
        List<Item_Dto> all = service.getAll();
        if (!all.isEmpty()) {
            String itemJSON = GSONConfiguration.getInstance().getGSON().toJson(all);
            try {
                resp.getWriter().println(itemJSON);
            } catch (IOException e) {
                System.out.println("An error occurred in Item-Manager endpoint while fetching items : " + e.getLocalizedMessage());
            }
        } else {
            resp.getWriter().println(ItemDAOIMPL.getError_Info());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setHeaders(resp);
        ItemServiceIMPL service = ServiceFactory.getService(ServiceTypes.ITEM_SERVICE);
        Gson gson = GSONConfiguration.getInstance().getGSON();
        Item_Dto itemDto = gson.fromJson(req.getReader(), Item_Dto.class);
        if (service.add(itemDto)) {
            resp.getWriter().println(true);
        } else {
            resp.getWriter().println(ItemDAOIMPL.getError_Info());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setHeaders(resp);
        ItemServiceIMPL service = ServiceFactory.getService(ServiceTypes.ITEM_SERVICE);
        if (service.delete(req.getParameter("item_id"))) {
            resp.getWriter().println(true);

        } else {
            resp.getWriter().println(ItemDAOIMPL.getError_Info());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setHeaders(resp);
        ItemServiceIMPL service = ServiceFactory.getService(ServiceTypes.ITEM_SERVICE);
        if (service.update(GSONConfiguration.getInstance().getGSON().fromJson(req.getReader(), Item_Dto.class))) {
            resp.getWriter().println(true);
        } else {
            resp.getWriter().println(ItemDAOIMPL.getError_Info());
        }

    }

    public void setHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Content-Type", "application/json");


    }
}

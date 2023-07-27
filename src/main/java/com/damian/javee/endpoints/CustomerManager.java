package com.damian.javee.endpoints;

import com.damian.javee.dao.impl.CustomerDAOIMPL;
import com.damian.javee.dto.Customer_DTO;
import com.damian.javee.service.impl.CustomerServiceIMPL;
import com.damian.javee.service.util.ServiceFactory;
import com.damian.javee.service.util.ServiceTypes;
import com.damian.javee.util.GSONConfiguration;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/customer-manager"})
public class CustomerManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO GET TRIGGERED" + req.getParameter("name"));
        if (req.getParameter("name").equals("fetchAll")) {
            fetchAll(req, resp);
            return;

        }

        CustomerServiceIMPL service = ServiceFactory.getService(ServiceTypes.CUSTOMER_SERVICE);
        Optional<Customer_DTO> cust = service.search(req.getParameter("name"));
        if (cust.isPresent()) {
            Gson gson = GSONConfiguration.getInstance().getGSON();
            String customerJSON = gson.toJson(cust.get());
            resp.getWriter().println(customerJSON);
        } else {
            System.out.println("CustomerManager.doGet: " + CustomerDAOIMPL.getErrorInfo());
            resp.getWriter().println(CustomerDAOIMPL.getErrorInfo());
        }


    }

    private void fetchAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("FETCH ALL TRIGGERED");

        CustomerServiceIMPL cs = ServiceFactory.getService(ServiceTypes.CUSTOMER_SERVICE);
        List<Customer_DTO> customerList = cs.getAll();
        System.out.println(customerList.isEmpty());
        if (!customerList.isEmpty()) {
            Gson gson = GSONConfiguration.getInstance().getGSON();
            String customerJSON = gson.toJson(customerList);
            try {
                resp.getWriter().println(customerJSON);
            } catch (IOException e) {
                System.out.println("An error occurred in Customer-Manager endpoint while fetching  customers : " + e.getLocalizedMessage());
            }
        } else {
            resp.getWriter().println(CustomerDAOIMPL.getErrorInfo());
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO POST TRIGGERED");

        Gson gson = GSONConfiguration.getInstance().getGSON();
        BufferedReader reader = req.getReader();
        String json = reader.readLine();
        Customer_DTO customer = gson.fromJson(json, Customer_DTO.class);

        CustomerServiceIMPL cs = ServiceFactory.getService(ServiceTypes.CUSTOMER_SERVICE);
        boolean b = cs.add(customer);
        if (b) {
            resp.getWriter().println(b);
        } else {
            resp.getWriter().println(CustomerDAOIMPL.getErrorInfo());
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = GSONConfiguration.getInstance().getGSON();
        BufferedReader reader = req.getReader();
        String customerJSON = reader.readLine();
        Customer_DTO customerDto = gson.fromJson(customerJSON, Customer_DTO.class);
        CustomerServiceIMPL service = ServiceFactory.getService(ServiceTypes.CUSTOMER_SERVICE);
        if (service.update(customerDto)) {
            resp.getWriter().println(true);

        } else {
            resp.getWriter().println(CustomerDAOIMPL.getErrorInfo());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CustomerServiceIMPL cs = ServiceFactory.getService(ServiceTypes.CUSTOMER_SERVICE);
        if (cs.delete(req.getParameter("cId"))) {
            resp.getWriter().println(true);

        } else {
            resp.getWriter().write(CustomerDAOIMPL.getErrorInfo());
        }
    }


}

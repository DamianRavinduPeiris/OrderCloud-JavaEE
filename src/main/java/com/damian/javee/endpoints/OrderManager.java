package com.damian.javee.endpoints;

import com.damian.javee.dao.impl.ItemDAOIMPL;
import com.damian.javee.dao.impl.OrderDAOIMPL;
import com.damian.javee.dto.Customer_DTO;
import com.damian.javee.dto.Item_Dto;
import com.damian.javee.dto.Order_DTO;
import com.damian.javee.service.impl.CustomerServiceIMPL;
import com.damian.javee.service.impl.ItemServiceIMPL;
import com.damian.javee.service.impl.OrderServiceIMPL;
import com.damian.javee.service.util.JSONOrder;
import com.damian.javee.service.util.ServiceFactory;
import com.damian.javee.service.util.ServiceTypes;
import com.damian.javee.util.Convertor;
import com.damian.javee.util.FactoryConfiguration;
import com.damian.javee.util.GSONConfiguration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@WebServlet(urlPatterns = {"/order-manager"})
public class OrderManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fetchAll(req, resp);


    }


    private void fetchAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        setHeaders(resp);
        OrderServiceIMPL service = ServiceFactory.getService(ServiceTypes.ORDER_SERVICE);
        ArrayList<Order_DTO> ordersList = service.getAll();

        if (!ordersList.isEmpty()) {
            ArrayList<JSONOrder> jsonOrdersList = new ArrayList<>();
            for (Order_DTO od : ordersList) {
                jsonOrdersList.add(new JSONOrder(od.getOrder_id(), od.getItem_id(), od.getCustomer_name(), od.getItem_name(), od.getItem_qty(), od.getItem_qty(), od.getTotal()));

            }

            try {
                resp.getWriter().println(GSONConfiguration.getInstance().getGSON().toJson(jsonOrdersList));
            } catch (IOException e) {
                System.out.println("An error occurred while sending the response while fetching all orders : " + e.getLocalizedMessage());
                e.printStackTrace();
            }

        } else {
            resp.getWriter().println(OrderDAOIMPL.getErrorInfo());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setHeaders(resp);

        OrderServiceIMPL service = ServiceFactory.getService(ServiceTypes.ORDER_SERVICE);
        /*Setting the customer object to order.*/
        Order_DTO orderDto = GSONConfiguration.getInstance().getGSON().fromJson(req.getReader().readLine(), Order_DTO.class);
        if (service.add(orderDto)) {
            /*Item qty updating.*/
            ItemServiceIMPL is = ServiceFactory.getService(ServiceTypes.ITEM_SERVICE);
            Optional<Item_Dto> item = is.search(orderDto.getItem_name());
            if (item.isPresent()) {
                System.out.println("item is present");
                item.get().setItem_qty(item.get().getItem_qty() - orderDto.getItem_qty());
                if (is.update(item.get())) {
                    System.out.println("Item qty updated");
                    resp.getWriter().println(true);
                } else {
                    resp.getWriter().println(ItemDAOIMPL.getError_Info());
                }
            }


        } else {
            resp.getWriter().println(OrderDAOIMPL.getErrorInfo());


        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setHeaders(resp);
        OrderServiceIMPL os = ServiceFactory.getService(ServiceTypes.ORDER_SERVICE);
        Order_DTO orderDto = GSONConfiguration.getInstance().getGSON().fromJson(req.getReader().readLine(), Order_DTO.class);
        System.out.println("iqty in odto "+orderDto.getItem_qty());
        /*Updating the order.*/
        if (os.update(orderDto)) {
            /*Updating the item QTY..*/
            ItemServiceIMPL is = ServiceFactory.getService(ServiceTypes.ITEM_SERVICE);
            Optional<Item_Dto> item = is.search(orderDto.getItem_name());
            if (item.isPresent()) {
                System.out.println("item is present");
                item.get().setItem_qty(item.get().getItem_qty() - orderDto.getItem_qty());
                if (is.update(item.get())) {
                    System.out.println("Item qty updated");
                    resp.getWriter().println(true);
                } else {
                    resp.getWriter().println(ItemDAOIMPL.getError_Info());
                }
            }

        } else {
            resp.getWriter().println(false);
            System.out.println(OrderDAOIMPL.getErrorInfo());
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setHeaders(resp);
        OrderServiceIMPL os = (OrderServiceIMPL) ServiceFactory.getService(ServiceTypes.ORDER_SERVICE);
        if(os.delete(req.getParameter("order_id"))){
            resp.getWriter().println(true);
        }else{
            resp.getWriter().println(OrderDAOIMPL.getErrorInfo());
        }
    }

    public void setHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.setHeader("Access-Control-Allow-Methods", "POST,PATCH,GET,PUT,DELETE,OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Content-Type", "application/json");


    }
}



package com.damian.javee.endpoints;

import com.damian.javee.dao.impl.OrderDAOIMPL;
import com.damian.javee.dto.Item_Dto;
import com.damian.javee.dto.Order_DTO;
import com.damian.javee.response.Response;
import com.damian.javee.service.impl.ItemServiceIMPL;
import com.damian.javee.service.impl.OrderServiceIMPL;
import com.damian.javee.service.util.JSONOrder;
import com.damian.javee.service.util.ServiceFactory;
import com.damian.javee.service.util.ServiceTypes;
import com.damian.javee.util.GSONConfiguration;
import com.damian.javee.util.ResponseConfiguration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/order-manager"})
public class OrderManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fetchAll(req, resp);


    }


    private void fetchAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        OrderServiceIMPL service = ServiceFactory.getService(ServiceTypes.ORDER_SERVICE);
        List<Order_DTO> ordersList = service.getAll();

        if (!ordersList.isEmpty()) {
            List<JSONOrder> jsonOrdersList = new ArrayList<>();
            for (Order_DTO od : ordersList) {
                System.out.println("QTY when sending : " + od.getItem_qty());
                System.out.println("PRICE when sending: " + od.getItem_price());
                System.out.println("TOTAL when sending: " + od.getTotal());
                jsonOrdersList.add(new JSONOrder(od.getOrder_id(), od.getItem_id(), od.getCustomer_name(), od.getItem_name(), od.getItem_qty(), od.getItem_qty(), od.getTotal()));

            }

            try {
                resp.getWriter().println(GSONConfiguration.getInstance().getGSON().toJson(jsonOrdersList));
            } catch (IOException e) {
                System.out.println("An error occurred while sending the response while fetching all orders : " + e.getLocalizedMessage());
                e.printStackTrace();
            }

        } else {
            /*Getting the error msg + status to an object.*/
            Response response = ResponseConfiguration.getInstance().getResponse();
            response.setResponseMessage(OrderDAOIMPL.getErrorInfo());
            response.setStatus(false);
            /*Converting it to a JSON object and sending as the response.*/
            resp.getWriter().println(GSONConfiguration.getInstance().getGSON().toJson(response));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
                    /*Getting the error msg + status to an object.*/
                    Response response = ResponseConfiguration.getInstance().getResponse();
                    response.setStatus(true);
                    /*Converting it to a JSON object and sending as the response.*/
                    resp.getWriter().println(GSONConfiguration.getInstance().getGSON().toJson(response));
                } else {
                    /*Getting the error msg + status to an object.*/
                    Response response = ResponseConfiguration.getInstance().getResponse();
                    response.setResponseMessage(OrderDAOIMPL.getErrorInfo());
                    response.setStatus(false);
                    /*Converting it to a JSON object and sending as the response.*/
                    resp.getWriter().println(GSONConfiguration.getInstance().getGSON().toJson(response));
                }
            }


        } else {
            /*Getting the error msg + status to an object.*/
            Response response = ResponseConfiguration.getInstance().getResponse();
            response.setResponseMessage(OrderDAOIMPL.getErrorInfo());
            response.setStatus(false);
            /*Converting it to a JSON object and sending as the response.*/
            resp.getWriter().println(GSONConfiguration.getInstance().getGSON().toJson(response));


        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        OrderServiceIMPL os = ServiceFactory.getService(ServiceTypes.ORDER_SERVICE);
        Order_DTO orderDto = GSONConfiguration.getInstance().getGSON().fromJson(req.getReader().readLine(), Order_DTO.class);
        System.out.println("iqty in odto " + orderDto.getItem_qty());
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
                    /*Getting the error msg + status to an object.*/
                    Response response = ResponseConfiguration.getInstance().getResponse();
                    response.setStatus(true);
                    /*Converting it to a JSON object and sending as the response.*/
                    resp.getWriter().println(GSONConfiguration.getInstance().getGSON().toJson(response));
                } else {
                    /*Getting the error msg + status to an object.*/
                    Response response = ResponseConfiguration.getInstance().getResponse();
                    response.setResponseMessage(OrderDAOIMPL.getErrorInfo());
                    response.setStatus(false);
                    /*Converting it to a JSON object and sending as the response.*/
                    resp.getWriter().println(GSONConfiguration.getInstance().getGSON().toJson(response));
                }
            }

        } else {
            /*Getting the error msg + status to an object.*/
            Response response = ResponseConfiguration.getInstance().getResponse();
            response.setResponseMessage(OrderDAOIMPL.getErrorInfo());
            response.setStatus(false);
            /*Converting it to a JSON object and sending as the response.*/
            resp.getWriter().println(GSONConfiguration.getInstance().getGSON().toJson(response));
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        OrderServiceIMPL os = ServiceFactory.getService(ServiceTypes.ORDER_SERVICE);
        if (os.delete(req.getParameter("order_id"))) {
            /*Getting the error msg + status to an object.*/
            Response response = ResponseConfiguration.getInstance().getResponse();
            response.setStatus(true);
            /*Converting it to a JSON object and sending as the response.*/
            resp.getWriter().println(GSONConfiguration.getInstance().getGSON().toJson(response));
        } else {
            /*Getting the error msg + status to an object.*/
            Response response = ResponseConfiguration.getInstance().getResponse();
            response.setResponseMessage(OrderDAOIMPL.getErrorInfo());
            response.setStatus(false);
            /*Converting it to a JSON object and sending as the response.*/
            resp.getWriter().println(GSONConfiguration.getInstance().getGSON().toJson(response));
        }
    }


}



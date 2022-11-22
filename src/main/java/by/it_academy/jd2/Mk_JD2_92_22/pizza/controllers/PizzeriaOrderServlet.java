package by.it_academy.jd2.Mk_JD2_92_22.pizza.controllers;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.controllers.util.mapper.ObjectMapperSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IOrderFullService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton.OrderFullServiceSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//CRUD controller
//IMenuRow

@WebServlet(name = "PizzeriaOrderServlet", urlPatterns = "/pizzeria/order")
public class PizzeriaOrderServlet extends HttpServlet {

    private final static String CHARSET = "UTF-8";
    private final static String CONTENT_TYPE = "application/json";
    private final static String ID = "id";
    private final static String DT_UPDATE = "update";

    private final IOrderFullService orderService;
    private final ObjectMapper mapper;


    public PizzeriaOrderServlet() {
        this.orderService = OrderFullServiceSingleton.getInstance();
        this.mapper = ObjectMapperSingleton.getInstance();
    }

    //Read POSITION
    //1) Read list
    //2) Read item (card) need id param
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setCharacterEncoding(CHARSET);
        resp.setContentType(CONTENT_TYPE);

        String queryString = req.getQueryString();
        PrintWriter writer = resp.getWriter();

        try {
            if (queryString == null) {

                List<IOrder> orders = orderService.get();
                writer.write(this.mapper.writeValueAsString(orders));

            } else {
                long id = Integer.parseInt(req.getParameter(ID));
                IOrder order = orderService.read(id);
                writer.write(this.mapper.writeValueAsString(order));

            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    //CREATE POSITION
    //body json
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setCharacterEncoding(CHARSET);
        resp.setContentType(CONTENT_TYPE);

//        try {
//            OrderDto orderDto = this.mapper.readValue(req.getInputStream(), OrderDto.class);
//
//            IOrder order = this.menuFullService.create(menuDto);
//
//            resp.getWriter().write(this.mapper.writeValueAsString(orderDto));
//
//            resp.setStatus(HttpServletResponse.SC_CREATED);

//        } catch (NotUniqServiceException e) {
//            resp.setStatus(HttpServletResponse.SC_CONFLICT);
//        } catch (ValidateException | IOException e) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        } catch (ServiceException e) {
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
    }


    //UPDATE POSITION
    //need param id
    //need param version/date_update - optimistic lock
    //body json
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setCharacterEncoding(CHARSET);
        resp.setContentType(CONTENT_TYPE);

//        try {
//            long id = Integer.parseInt(req.getParameter(ID));
//            MenuDto menuDto = this.mapper.readValue(req.getInputStream(), MenuDto.class);
//
//            LocalDateTime dtUpdate = LocalDateTime.ofInstant(
//                    Instant.ofEpochMilli(
//                            Long.parseLong(req.getParameter(DT_UPDATE))),
//                    ZoneId.of("UTC")
//            );
//            menuFullService.update(id, dtUpdate, menuDto);
//            resp.setStatus(HttpServletResponse.SC_CREATED);
//
//        } catch (ValidateException | IllegalArgumentException | IOException e) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        } catch (ServiceException e) {
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        } catch (NotUniqServiceException e) {
//            resp.setStatus(HttpServletResponse.SC_CONFLICT);
//        }
    }

    //DELETE POSITION
    //need param id
    //need param version/date_update - optimistic lock
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(CHARSET);

//        try {
//            long id = Integer.parseInt(req.getParameter(ID));
//
//            LocalDateTime dtUpdate = LocalDateTime.ofInstant(
//                    Instant.ofEpochMilli(Long.parseLong(req.getParameter(DT_UPDATE))),
//                    ZoneId.of("UTC"));
//            menuFullService.delete(id, dtUpdate);
//
//        } catch (NumberFormatException e) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        } catch (ServiceException e) {
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        } catch (IllegalArgumentException e) {
//            resp.setStatus(HttpServletResponse.SC_CONFLICT);
//        }
    }
}

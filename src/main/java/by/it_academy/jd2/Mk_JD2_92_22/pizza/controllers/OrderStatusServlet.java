package by.it_academy.jd2.Mk_JD2_92_22.pizza.controllers;


import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.SelectedItemDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton.SelectedItemServiceSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.ISelectedItemService;
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
//IOrderSt
@WebServlet(name = "OrderStatusServlet", urlPatterns = "/order-status")
public class OrderStatusServlet extends HttpServlet {

    private ISelectedItemService service;
    private final ObjectMapper mapper;
    private static final String CHARSET = "UTF-8";
    private static final String CONTENT_TYPE = "application/json";
    private final static String ID = "id";
    private final static String DT_UPDATE = "update";

    public OrderStatusServlet() {
        this.service = SelectedItemServiceSingleton.getInstance();
        this.mapper = new ObjectMapper();
    }

    //Read POSITION
    //1) Read item (card) need id param ticket
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setContentType(CONTENT_TYPE);

        String queryString = req.getQueryString();

        PrintWriter writer = resp.getWriter();


        if (queryString == null) {

            List<SelectedItemDto> selectedItemDtos = service.get();

            writer.write(this.mapper.writeValueAsString(selectedItemDtos));

        } else {

            String idParam = req.getParameter(ID);

            long id;

            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

//            SelectedItemDto selectedItemDto = service.read(id);

//            try {
//                writer.write(this.mapper.writeValueAsString(selectedItemDto));
//            } catch (IOException e) {
//                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//            }
        }
    }


    //CREATE POSITION
    //body json
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);

        SelectedItemDto dto;

        try {
            dto = this.mapper.readValue(req.getInputStream(), SelectedItemDto.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        SelectedItemDto dtoOut;

//        try {
//            dtoOut = this.service.create(dto);
//        } catch (ServiceException e) {
//            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//            return;
//        }

        PrintWriter writer = resp.getWriter();

//        writer.write(this.mapper.writeValueAsString(dtoOut));

        resp.setStatus(HttpServletResponse.SC_CREATED);


    }

    //DELETE POSITION
    //need param id
    //need param version/date_update - optimistic lock
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idParam = req.getParameter(ID);
        String updateParam = req.getParameter(DT_UPDATE);

        long id;
        long update;
        try {
            id = Integer.parseInt(idParam);
            update = Integer.parseInt(updateParam);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        service.delete(id, update);
    }
}

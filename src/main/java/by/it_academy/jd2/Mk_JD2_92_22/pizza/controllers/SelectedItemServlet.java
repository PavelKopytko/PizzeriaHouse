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
//IMenuRow
@WebServlet(name = "SelectedItemServlet", urlPatterns = "/selected-item")
public class SelectedItemServlet extends HttpServlet {

    private ISelectedItemService service;
    private final ObjectMapper mapper;
    private final String CHARSET = "UTF-8";
    private final String CONTENT_TYPE = "application/json";


    public SelectedItemServlet() {
        this.service = SelectedItemServiceSingleton.getInstance();
        this.mapper = new ObjectMapper();
    }

    //Read POSITION
    //1) Read list
    //2) Read item (card) need id param
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setCharacterEncoding(CHARSET);
        resp.setContentType(CONTENT_TYPE);

        //mapper.registerModule(new JavaTimeModule());
        //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String queryString = req.getQueryString();

        PrintWriter writer = resp.getWriter();

        if (queryString == null) {

            List<SelectedItemDto> selectedItemDtos = service.get();

            writer.write(this.mapper.writeValueAsString(selectedItemDtos));

        } else {

            String idParam = req.getParameter("id");

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

        //mapper.registerModule(new JavaTimeModule());
        //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        SelectedItemDto selectedItemDto = null;

        try {
            selectedItemDto = this.mapper.readValue(req.getInputStream(), SelectedItemDto.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        SelectedItemDto dto = null;

//        try {
//            dto = this.service.create(selectedItemDto);
//        } catch (ServiceException e) {
//            throw new RuntimeException(e);
//        }

        PrintWriter writer = resp.getWriter();

        writer.write(this.mapper.writeValueAsString(dto));

        resp.setStatus(HttpServletResponse.SC_CREATED);


    }

    //UPDATE POSITION
    //need param id
    //need param version/date_update - optimistic lock
    //body json
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        String idParam = req.getParameter("id");
//        String updateParam = req.getParameter("update");
//
//        long id;
//        long update;
//        try {
//            id = Integer.parseInt(idParam);
//            update = Integer.parseInt(updateParam);
//        } catch (NumberFormatException e) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//
//        MenuDto menuDto = this.mapper.readValue(req.getInputStream(), MenuDto.class);
//
//        service.update(id, update, menuDto);
//
//        resp.setStatus(HttpServletResponse.SC_CREATED);

    }

    //DELETE POSITION
    //need param id
    //need param version/date_update - optimistic lock
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idParam = req.getParameter("id");
        String updateParam = req.getParameter("update");

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

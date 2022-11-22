package by.it_academy.jd2.Mk_JD2_92_22.pizza.controllers;


import by.it_academy.jd2.Mk_JD2_92_22.pizza.controllers.util.mapper.ObjectMapperSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.PizzaInfoDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IPizzaInfoService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton.PizzaInfoServiceSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

//CRUD controller
//IMenuRow
@WebServlet(name = "PizzaInfoServlet", urlPatterns = "/menu/pizza-info")
public class PizzaInfoServlet extends HttpServlet {

    private IPizzaInfoService pizzaInfoService;
    private final ObjectMapper mapper;
    private final static String CHARSET = "UTF-8";
    private final static String CONTENT_TYPE = "application/json";
    private final static String ID = "id";
    private final static String DT_UPDATE = "update";


    public PizzaInfoServlet() {
        this.pizzaInfoService = PizzaInfoServiceSingleton.getInstance();
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

                List<IPizzaInfo> items = pizzaInfoService.get();
                writer.write(mapper.writeValueAsString(items));

            } else {

                long id = Integer.parseInt(req.getParameter(ID));
                IPizzaInfo pizzaInfo = pizzaInfoService.read(id);
                writer.write(mapper.writeValueAsString(pizzaInfo));

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

        try {
            PizzaInfoDto pizzaInfoDto = mapper.readValue(req.getInputStream(), PizzaInfoDto.class);

            IPizzaInfo pizzaInfo = pizzaInfoService.create(pizzaInfoDto);

            resp.getWriter().write(this.mapper.writeValueAsString(pizzaInfo));

            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (NotUniqServiceException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (ValidateException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
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

        try {
            long id = Integer.parseInt(req.getParameter(ID));
            PizzaInfoDto pizzaInfoDto = this.mapper.readValue(req.getInputStream(), PizzaInfoDto.class);

            LocalDateTime dtUpdate = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(
                            Long.parseLong(req.getParameter(DT_UPDATE))),
                    ZoneId.of("UTC")
            );
            IPizzaInfo pizzaInfo = pizzaInfoService.update(id, dtUpdate, pizzaInfoDto);
            resp.getWriter().write(this.mapper.writeValueAsString(pizzaInfo));
            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (ValidateException | IllegalArgumentException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NotUniqServiceException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    //DELETE POSITION
    //need param id
    //need param version/date_update - optimistic lock
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(CHARSET);

        try {
            long id = Integer.parseInt(req.getParameter(ID));

            LocalDateTime dtUpdate = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(Long.parseLong(req.getParameter(DT_UPDATE))),
                    ZoneId.of("UTC"));
            pizzaInfoService.delete(id, dtUpdate);

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

package by.it_academy.jd2.Mk_JD2_92_22.pizza.controllers;


import by.it_academy.jd2.Mk_JD2_92_22.pizza.controllers.util.mapper.ObjectMapperSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IMenuService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton.MenuServiceSingleton;
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
@WebServlet(name = "MenuServlet", urlPatterns = "/menu")
public class MenuServlet extends HttpServlet {

    private final IMenuService menuService;
    private final ObjectMapper mapper;
    private final static String CHARSET = "UTF-8";
    private final static String CONTENT_TYPE = "application/json";
    private final static String ID = "id";
    private final static String DT_UPDATE = "update";


    public MenuServlet() {
        this.menuService = MenuServiceSingleton.getInstance();
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

                List<IMenu> menus = menuService.get();
                writer.write(this.mapper.writeValueAsString(menus));

            } else {

                long id = Integer.parseInt(req.getParameter(ID));
                IMenu menu = menuService.read(id);
                writer.write(this.mapper.writeValueAsString(menu));

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
            MenuDto menuDto = this.mapper.readValue(req.getInputStream(), MenuDto.class);

            IMenu menu = this.menuService.create(menuDto);

            resp.getWriter().write(this.mapper.writeValueAsString(menu));

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
            MenuDto menuDto = this.mapper.readValue(req.getInputStream(), MenuDto.class);

            LocalDateTime dtUpdate = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(
                            Long.parseLong(req.getParameter(DT_UPDATE))),
                    ZoneId.of("UTC")
            );
            menuService.update(id, dtUpdate, menuDto);
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
            menuService.delete(id, dtUpdate);

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

}



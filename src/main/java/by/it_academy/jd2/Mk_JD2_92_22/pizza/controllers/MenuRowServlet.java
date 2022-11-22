package by.it_academy.jd2.Mk_JD2_92_22.pizza.controllers;


import by.it_academy.jd2.Mk_JD2_92_22.pizza.controllers.util.mapper.ObjectMapperSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuRowDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IMenuRowService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.IDServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.NotUniqServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ServiceException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception.ValidateException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton.MenuRowServiceSingleton;
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
@WebServlet(name = "MenuRowServlet", urlPatterns = "/menu/menu-row")
public class MenuRowServlet extends HttpServlet {

    private IMenuRowService service;
    private final ObjectMapper mapper;
    private final String CHARSET = "UTF-8";
    private final String CONTENT_TYPE = "application/json";
    private final String ID = "id";
    private final String DT_UPDATE = "update";


    public MenuRowServlet() {
        this.service = MenuRowServiceSingleton.getInstance();
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

                List<IMenuRow> items = service.get();

                writer.write(this.mapper.writeValueAsString(items));

            } else {


                long id = Integer.parseInt(req.getParameter(ID));

                IMenuRow item = service.read(id);
                writer.write(this.mapper.writeValueAsString(item));
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
            MenuRowDto menuRowDto = this.mapper.readValue(req.getInputStream(), MenuRowDto.class);
            IMenuRow menuRow = service.create(menuRowDto);
            resp.getWriter().write(this.mapper.writeValueAsString(menuRow));
            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (NotUniqServiceException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (ValidateException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IDServiceException e) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);//?
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
            MenuRowDto menuRowDto = this.mapper.readValue(req.getInputStream(), MenuRowDto.class);

            LocalDateTime dtUpdate = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(
                            Long.parseLong(req.getParameter(DT_UPDATE))),
                    ZoneId.of("UTC")
            );

            IMenuRow menuRow = service.update(id, dtUpdate, menuRowDto);
            resp.getWriter().write(this.mapper.writeValueAsString(menuRow));
            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (ValidateException | IllegalArgumentException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NotUniqServiceException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (IDServiceException e) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
                    ZoneId.of("UTC")
            );
            service.delete(id, dtUpdate);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

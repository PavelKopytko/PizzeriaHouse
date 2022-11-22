package by.it_academy.jd2.Mk_JD2_92_22.pizza.controllers;


import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.StageDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton.StageServiceSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IStageService;
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
@WebServlet(name = "StageServlet", urlPatterns = "/stage")
public class StageServlet extends HttpServlet {

    private IStageService stageService;
    private final ObjectMapper mapper;
    private final String CHARSET = "UTF-8";
    private final String CONTENT_TYPE = "application/json";
    private final String ID = "id";
    private final String DT_UPDATE = "update";


    public StageServlet() {
        this.stageService = StageServiceSingleton.getInstance();
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

        String queryString = req.getQueryString();

        PrintWriter writer = resp.getWriter();

        if (queryString == null) {

            List<StageDto> items = stageService.get();

            writer.write(this.mapper.writeValueAsString(items));

        } else {

            String idParam = req.getParameter(ID);

            long id;

            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

//            StageDto items = stageService.read(id);
//
//            try {
//                writer.write(this.mapper.writeValueAsString(items));
//            } catch (IOException e) {
//                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//            }
        }
    }


    //CREATE POSITION
    //body json
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(CHARSET);
        resp.setCharacterEncoding(CHARSET);
        resp.setContentType(CONTENT_TYPE);


        StageDto stageDto;
        try {
            stageDto = this.mapper.readValue(req.getInputStream(), StageDto.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        StageDto stageDtoOut;

//        try {
//            stageDtoOut = this.stageService.create(stageDto);
//        } catch (ServiceException e) {
//            throw new RuntimeException(e);
//        }

        PrintWriter writer = resp.getWriter();

//        writer.write(this.mapper.writeValueAsString(stageDtoOut));

        resp.setStatus(HttpServletResponse.SC_CREATED);


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

        StageDto itemDto = this.mapper.readValue(req.getInputStream(), StageDto.class);

        stageService.update(id, update, itemDto);

        resp.setStatus(HttpServletResponse.SC_CREATED);

    }

    //DELETE POSITION
    //need param id
    //need param version/date_update - optimistic lock
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(CHARSET);

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

        stageService.delete(id, update);
    }
}

package by.it_academy.jd2.Mk_JD2_92_22.pizza.controllers;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto.MenuRowDto;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;
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

@WebServlet(name = "MenuPositionServlet", urlPatterns = "/menu/positions")
public class MenuPositionServlet extends HttpServlet {

    //private IMenuRowService menuRowService;
    //private final ObjectMapper mapper;
//
//
    //public MenuPositionServlet() {
    //    this.menuRowService = ServiceFactory.getInstance().getMenuRowService();
    //    this.mapper = new ObjectMapper();
    //}

    //Read POSITION
    //1) Read list
    //2) Read item (card) need id param
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //req.setCharacterEncoding("UTF-8");
      //resp.setContentType("application/json");

      //String queryString = req.getQueryString();

      //PrintWriter writer = resp.getWriter();

      //if (queryString == null) {
      //    List<IMenuRow> menuRows = menuRowService.get();
      //    writer.write(this.mapper.writeValueAsString(menuRows));
      //} else {
      //    String idParam = req.getParameter("id");
      //    int id = Integer.parseInt(idParam);
      //    IMenuRow menuRow = menuRowService.get(id);
      //    writer.write(this.mapper.writeValueAsString(menuRow));
      //}
    }

    //CREATE POSITION
    //body json
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //List<IMenuRow> menuRows = menuRowService.get();
//
        //MenuRowDto menuRowDto = this.mapper.readValue(req.getInputStream(), MenuRowDto.class);
//
        //this.menuRowService.save(menuRowDto);


    }

    //UPDATE POSITION
    //need param id
    //need param version/date_update - optimistic lock
    //body json
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //String idParam = req.getParameter("id");
        //String updateParam = req.getParameter("update");
        //int id = Integer.parseInt(idParam);
        //int update = Integer.parseInt(updateParam);
//
        //MenuRowDto menuRowDto = this.mapper.readValue(req.getInputStream(), MenuRowDto.class);
//
        //menuRowService.update(id, update, menuRowDto);

    }

    //DELETE POSITION
    //need param id
    //need param version/date_update - optimistic lock
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //String idParam = req.getParameter("id");
        //String updateParam = req.getParameter("update");
        //int id = Integer.parseInt(idParam);
        //int update = Integer.parseInt(updateParam);
//
        //menuRowService.delete(id, update);
    }
}

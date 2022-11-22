package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizza;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IPizzaDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.PizzaMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PizzaDao implements IPizzaDao {

    private final static String INSERT_SQL = "INSERT INTO structure.pizza(\n" +
            "\tdt_create, dt_update, name, size, done_order)\n" +
            "\tVALUES ( ?, ?, ?, ?, ?);";

    private final static String SELECT_BY_ID_SQL = "SELECT p.id p_id, \n" +
            "\tp.dt_create p_dt_create, \n" +
            "\tp.dt_update p_dt_update, \n" +
            "\tname, \n" +
            "\tsize, \n" +
            "\tp.done_order p_done_order,\n" +
            "\tdon.id don_id,\n" +
            "\tdon.dt_create don_dt_create, \n" +
            "\tdon.dt_update don_dt_update\n" +
            "\tFROM structure.pizza p\n" +
            "\tINNER JOIN structure.done_order don ON p.done_order=don.id\n" +
            "\tWHERE id = ?;";

    private final static String SELECT_SQL = "SELECT p.id p_id, \n" +
            "\tp.dt_create p_dt_create, \n" +
            "\tp.dt_update p_dt_update, \n" +
            "\tname, \n" +
            "\tsize, \n" +
            "\tp.done_order p_done_order,\n" +
            "\tdon.id don_id,\n" +
            "\tdon.dt_create don_dt_create, \n" +
            "\tdon.dt_update don_dt_update\n" +
            "\tFROM structure.pizza p\n" +
            "\tINNER JOIN structure.done_order don ON p.done_order=don.id;";

    private static final String DELETE_SQL = "DELETE FROM structure.pizza\n" +
            "\tWHERE id = ? and dt_update = ?;";

    private static final String ID = "id";

    private final DataSource ds;

    public PizzaDao(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public IPizza create(IPizza item) throws DaoException {

        IPizza pizza;
        try {
            try (Connection con = ds.getConnection();
                 PreparedStatement stm = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

                stm.setObject(1, item.getDtCreate());
                stm.setObject(2, item.getDtUpdate());
                stm.setString(3, item.getName());
                stm.setInt(4, item.getSize());
                stm.setLong(5, item.getOrder().getId());

                int updated = stm.executeUpdate();

                ResultSet rs = stm.getGeneratedKeys();
                rs.next();
                pizza = read(rs.getLong(ID));

            } catch (SQLException e) {
                throw new RuntimeException("При сохранении данных произошла ошибка", e);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return pizza;
    }

    @Override
    public IPizza read(long id) {

        IPizza pizza;

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_BY_ID_SQL)) {

            stm.setObject(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                pizza = PizzaMapper.mapper(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("При чтении данных произошла ошибка", e);
        }
        return pizza;
    }

    @Override
    public List<IPizza> get() {

        List<IPizza> items = new ArrayList<>();

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_SQL)) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    items.add(PizzaMapper.mapper(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("При чтении данных произошла ошибка", e);
        }
        return items;
    }


    @Override
    public void delete(long id, LocalDateTime dtUpdate) {

        try (Connection conn = ds.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            stm.setLong(1, id);
            stm.setObject(2, dtUpdate);

            int countUpdatedRows = stm.executeUpdate();

            if (countUpdatedRows != 1) {
                if (countUpdatedRows == 0) {
                    throw new IllegalArgumentException("Не смогли удалить какую либо запись");
                } else {
                    throw new IllegalArgumentException("Удалили более одной записи");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("При удалении данных произошла ошибка", e);
        }
    }
}

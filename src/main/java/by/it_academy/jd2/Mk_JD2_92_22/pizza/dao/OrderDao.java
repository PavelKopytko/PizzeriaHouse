package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Order;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IOrderDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements IOrderDao {

    private final static String INSERT_SQL = "INSERT INTO structure.order_t(\n" +
            "\tdt_create, dt_update)\n" +
            "\tVALUES ( ?, ?);";
    private final static String SELECT_ORDER_BY_ID_SQL = "SELECT id o_id, dt_create o_dt_create, dt_update o_dt_update\n" +
            "\tFROM structure.order_t\n" +
            "\tWHERE id = ?;";
    private final static String SELECT_SQL = "SELECT id o_id, dt_create o_dt_create, dt_update o_dt_update\n" +
            "\tFROM structure.order_t;";
    private final static String DELETE_SQL = "DELETE FROM structure.order_t\n" +
            "\tWHERE id = ? and dt_update = ?;";
    private final static String UNIQ_ERROR_CODE = "23503";
    private final static String ORDER_FK = "selected_item_order_id_fkey";
    private final static String ID = "id";
    private final DataSource ds;

    public OrderDao(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public IOrder create(IOrder item) throws DaoException {

        IOrder order = null;

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {

            stm.setObject(1, item.getDtCreate());
            stm.setObject(2, item.getDtUpdate());

            int updated = stm.executeUpdate();

            try (ResultSet rs = stm.getGeneratedKeys()) {
                while (rs.next()) {
                    order = read(rs.getLong(ID));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("При сохранении данных произошла ошибка", e);
        }
        return order;
    }

    @Override
    public IOrder read(long id) throws DaoException {

        IOrder order = null;

        try (Connection connection = ds.getConnection();
             PreparedStatement stmOrder = connection.prepareStatement(SELECT_ORDER_BY_ID_SQL)
        ) {
            stmOrder.setLong(1, id);
            try (ResultSet rs = stmOrder.executeQuery()) {
                while (rs.next()) {

                    order = mapper(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("При чтении данных произошла ошибка", e);
        }
        return order;
    }

    @Override
    public List<IOrder> get() throws DaoException {

        List<IOrder> orders = new ArrayList<>();

        try (Connection connection = ds.getConnection();
             PreparedStatement stmOrder = connection.prepareStatement(SELECT_SQL)
        ) {
            try (ResultSet rs = stmOrder.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapper(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("При чтении данных произошла ошибка", e);
        }

        return orders;
    }

    @Override
    public IOrder update(long id, LocalDateTime dtUpdate, IOrder item) {
        return null;
    }

    @Override
    public void delete(long id, LocalDateTime dtUpdate) throws DaoException {

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
            if (UNIQ_ERROR_CODE.equals(e.getSQLState())) {
                if (e.getMessage().contains(ORDER_FK)) {
                    throw new IllegalArgumentException("Ошибка, такое меню уже существует", e);
                } else {
                    throw new DaoException("При сохранении данных произошла ошибка", e);
                }
            }
        }
    }

    private IOrder mapper(ResultSet rs) throws SQLException {
        return new Order(
                rs.getLong("o_id"),
                rs.getObject("o_dt_create", LocalDateTime.class),
                rs.getObject("o_dt_update", LocalDateTime.class)
        );
    }
}

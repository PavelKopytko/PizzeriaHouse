package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Order;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Ticket;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ITicket;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.ITicketDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.NotUniqDaoException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDao implements ITicketDao {

    private final static String INSERT_SQL = "INSERT INTO structure.ticket(\n" +
            "\tdt_create, dt_update, \"number\", order_id)\n" +
            "\tVALUES (?, ?, ?, ?);";

    private final static String SELECT_BY_ID_SQL = "SELECT tc.id tc_id,\n" +
            "\ttc.dt_create tc_dt_create, \n" +
            "\ttc.dt_update tc_dt_update, \n" +
            "\t\"number\", \n" +
            "\ttc.order_id tc_o_id,\n" +
            "\to.id o_id,\n" +
            "\to.dt_create o_dt_create,\n" +
            "\to.dt_update o_dt_update \n" +
            "\tFROM structure.ticket tc\n" +
            "\tINNER JOIN structure.order_t o ON o.id = tc.order_id" +
            "\tWHERE tc.id = ?;";

    private final static String SELECT_SQL = "SELECT tc.id tc_id,\n" +
            "\ttc.dt_create tc_dt_create, \n" +
            "\ttc.dt_update tc_dt_update, \n" +
            "\tnumber, \n" +
            "\ttc.order_id tc_o_id,\n" +
            "\to.id o_id,\n" +
            "\to.dt_create o_dt_create,\n" +
            "\to.dt_update o_dt_update \n" +
            "\tFROM structure.ticket tc\n" +
            "\tINNER JOIN structure.order_t o ON o.id = tc.order_id;";

    private final static String DELETE_SQL = "DELETE FROM structure.ticket\n" +
            "\tWHERE id = ? and dt_update = ?;";

    private final static String DELETE_ORDER_SQL = "DELETE FROM structure.order_t\n" +
            "\tWHERE  id = ?;";         ///??????????????


    private final static String ID = "id";

    private final DataSource ds;

    public TicketDao(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public ITicket create(ITicket item) throws DaoException {

        ITicket ticket = null;

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stm.setObject(1, item.getDtCreate());
            stm.setObject(2, item.getDtUpdate());
            stm.setString(3, item.getNumber());
            stm.setLong(4, item.getOrder().getId());

            int updated = stm.executeUpdate();

            try (ResultSet rs = stm.getGeneratedKeys()) {
                while (rs.next()) {
                    ticket = read(rs.getLong(ID));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("При сохранении данных произошла ошибка", e);
        }
        return ticket;
    }

    @Override
    public ITicket read(long id) throws DaoException {

        ITicket ticket = null;
        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_BY_ID_SQL)) {

            stm.setObject(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ticket = mapper(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("При чтении данных произошла ошибка", e);
        }
        return ticket;
    }

    @Override
    public List<ITicket> get() throws DaoException {

        List<ITicket> tickets = new ArrayList<>();

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_SQL)) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    tickets.add(mapper(rs));
                }
            }

        } catch (SQLException e) {
            throw new DaoException("При чтении данных произошла ошибка", e);
        }

        return tickets;
    }

    @Override
    public ITicket update(long id, LocalDateTime dtUpdate, ITicket item) throws DaoException, NotUniqDaoException {
        return null;
    }

    @Override
    public void delete(long id, LocalDateTime dtUpdate) throws DaoException {

        try (Connection conn = ds.getConnection();
             PreparedStatement stm = conn.prepareStatement(DELETE_SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmOrder = conn.prepareStatement(DELETE_ORDER_SQL);
        ) {
            conn.setAutoCommit(false);
            stm.setLong(1, id);
            stm.setObject(2, dtUpdate);

            ITicket ticket = read(id);

            stmOrder.setLong(1, ticket.getOrder().getId());


            int countUpdatedRows = stm.executeUpdate();

            stmOrder.executeUpdate();

            if (countUpdatedRows != 1) {
                if (countUpdatedRows == 0) {
                    throw new IllegalArgumentException("Не смогли удалить какую либо запись");
                } else {
                    throw new IllegalArgumentException("Удалили более одной записи");
                }
            }
            conn.commit();
        } catch (SQLException e) {
            throw new DaoException("При удалении данных произошла ошибка", e);
        }
    }

    private ITicket mapper(ResultSet rs) throws SQLException {
        Order order = new Order(
                rs.getLong("o_id"),
                rs.getObject("o_dt_create", LocalDateTime.class),
                rs.getObject("o_dt_update", LocalDateTime.class)
        );

        return new Ticket(
                rs.getLong("tc_id"),
                rs.getObject("tc_dt_create", LocalDateTime.class),
                rs.getObject("tc_dt_update", LocalDateTime.class),
                rs.getString("number"),
                order
        );
    }
}

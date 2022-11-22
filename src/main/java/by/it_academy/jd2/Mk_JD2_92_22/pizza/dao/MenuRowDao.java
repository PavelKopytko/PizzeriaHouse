package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Menu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.MenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.PizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IMenuRowDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.NotUniqDaoException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MenuRowDao implements IMenuRowDao {

    private final static String INSERT_SQL = "INSERT INTO structure.menu_row(\n" +
            "\tdt_create, dt_update, info, price, menu)\n" +
            "\tVALUES ( ?, ?, ?, ?, ?);";

    private final static String SELECT_BY_ID_SQL = "SELECT mr.id menu_row_id, \n" +
            "\tmr.dt_create mr_dt_create, \n" +
            "\tmr.dt_update mr_dt_update, \n" +
            "\tpi.id pi_id, \n" +
            "\tpi.dt_create pi_dt_create, \n" +
            "\tpi.dt_update pi_dt_update, \n" +
            "\tpi.name pi_name, \n" +
            "\tpi.description pi_descr, \n" +
            "\tpi.size pi_size, \n" +
            "\tprice, \n" +
            "\tmenu.id m_id,\n" +
            "\tmenu.name m_name,\n" +
            "\tmenu.dt_create m_dt_create, \n" +
            "\tmenu.dt_update m_dt_update, \n" +
            "\tmenu.enable m_enable\n" +
            "\tFROM structure.menu_row mr\n" +
            "\tINNER JOIN structure.pizza_info pi ON mr.info =pi.id\n" +
            "\tINNER JOIN structure.menu menu ON mr.menu=menu.id\n" +
            "\tWHERE mr.id=?;";

    private final static String SELECT_SQL = "SELECT mr.id menu_row_id, \n" +
            "\tmr.dt_create mr_dt_create, \n" +
            "\tmr.dt_update mr_dt_update, \n" +
            "\tpi.id pi_id, \n" +
            "\tpi.dt_create pi_dt_create, \n" +
            "\tpi.dt_update pi_dt_update, \n" +
            "\tpi.name pi_name, \n" +
            "\tpi.description pi_descr, \n" +
            "\tpi.size pi_size, \n" +
            "\tprice, \n" +
            "\tmenu.id m_id,\n" +
            "\tmenu.name m_name,\n" +
            "\tmenu.dt_create m_dt_create, \n" +
            "\tmenu.dt_update m_dt_update, \n" +
            "\tmenu.enable m_enable\n" +
            "\tFROM structure.menu_row mr\n" +
            "\tINNER JOIN structure.pizza_info pi ON mr.info =pi.id\n" +
            "\tINNER JOIN structure.menu menu ON mr.menu=menu.id;";

    private final static String UPDATE_SQL = "UPDATE structure.menu_row\n" +
            "\tSET dt_update=?, info=?, price=?, menu=?\n" +
            "\tWHERE id =? and dt_update = ?;";

    private final static String DELETE_SQL = "DELETE FROM structure.menu_row\n" +
            "\tWHERE id = ? and dt_update = ?;";
    private final static String UNIQ_ERROR_CODE = "23505";
    private final static String MENU_ROW_INFO_MENU_UNIQ = "menu_row_info_menu_uniq";
    private final static String ID = "id";
    private final DataSource ds;

    public MenuRowDao(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public IMenuRow create(IMenuRow item) throws DaoException, NotUniqDaoException {

        IMenuRow menuRow = null;

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stm.setObject(1, item.getDtCreate());
            stm.setObject(2, item.getDtUpdate());
            stm.setLong(3, item.getInfo().getId());
            stm.setDouble(4, item.getPrice());
            stm.setLong(5, item.getMenu().getId());

            int updated = stm.executeUpdate();

            try (ResultSet rs = stm.getGeneratedKeys()) {
                while (rs.next()) {
                    menuRow = read(rs.getLong(ID));
                }
            }

        } catch (SQLException e) {
            if (UNIQ_ERROR_CODE.equals(e.getSQLState())) {
                if (e.getMessage().contains(MENU_ROW_INFO_MENU_UNIQ)) {
                    throw new NotUniqDaoException("Ошибка, такая строка меню уже существует");
                }
            } else {
                throw new DaoException("При сохранении данных произошла ошибка", e);
            }
        }
        return menuRow;
    }

    @Override
    public IMenuRow read(long id) throws DaoException {

        IMenuRow menuRow = null;

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_BY_ID_SQL)) {

            stm.setObject(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    menuRow = mapper(rs);
                }
            }

        } catch (SQLException e) {
            throw new DaoException("При чтении данных произошла ошибка", e);
        }

        return menuRow;
    }

    @Override
    public List<IMenuRow> get() throws DaoException {

        List<IMenuRow> menuRows = new ArrayList<>();

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_SQL)) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    menuRows.add(mapper(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("При чтении данных произошла ошибка", e);
        }

        return menuRows;
    }

    @Override
    public IMenuRow update(long id, LocalDateTime dtUpdate, IMenuRow item) throws DaoException, NotUniqDaoException {

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(UPDATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stm.setObject(1, item.getDtUpdate());
            stm.setLong(2, item.getInfo().getId());
            stm.setDouble(3, item.getPrice());
            stm.setLong(4, item.getMenu().getId());

            stm.setLong(5, id);
            stm.setObject(6, dtUpdate);

            int countUpdatedRows = stm.executeUpdate();

            if (countUpdatedRows != 1) {
                if (countUpdatedRows == 0) {
                    throw new IllegalArgumentException("Не смогли обновить какую либо запись");
                } else {
                    throw new IllegalArgumentException("Обновили более одной записи");
                }
            }

        } catch (SQLException e) {
            if (UNIQ_ERROR_CODE.equals(e.getSQLState())) {
                if (e.getMessage().contains(MENU_ROW_INFO_MENU_UNIQ)) {
                    throw new NotUniqDaoException("Ошибка, такая строка меню уже существует");
                }
            } else {
                throw new DaoException("При сохранении данных произошла ошибка", e);
            }
        }

        return read(id);
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
            throw new DaoException("При удалении данных произошла ошибка", e);
        }
    }

    private IMenuRow mapper(ResultSet rs) throws SQLException {
        PizzaInfo pizzaInfo = new PizzaInfo(
                rs.getLong("pi_id"),
                rs.getObject("pi_dt_create", LocalDateTime.class),
                rs.getObject("pi_dt_update", LocalDateTime.class),
                rs.getString("pi_name"),
                rs.getString("pi_descr"),
                rs.getLong("pi_size")
        );

        Menu menu = new Menu(
                rs.getLong("m_id"),
                rs.getObject("m_dt_create", LocalDateTime.class),
                rs.getObject("m_dt_update", LocalDateTime.class),
                rs.getString("m_name"),
                rs.getBoolean("m_enable")
        );

        return new MenuRow(
                rs.getLong("menu_row_id"),
                rs.getObject("mr_dt_create", LocalDateTime.class),
                rs.getObject("mr_dt_update", LocalDateTime.class),
                pizzaInfo,
                rs.getDouble("price"),
                menu
        );
    }
}

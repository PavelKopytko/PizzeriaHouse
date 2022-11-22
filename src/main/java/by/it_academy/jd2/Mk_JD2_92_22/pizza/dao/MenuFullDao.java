package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Menu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.MenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.PizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IMenuFullDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.NotUniqDaoException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MenuFullDao implements IMenuFullDao {

    private final static String INSERT_SQL = "INSERT INTO structure.menu" +
            "(dt_create, dt_update, name, enable)\n" +
            "\tVALUES (?, ?, ?, ?);";

    private final static String INSERT_ROWS_SQL = "INSERT INTO structure.menu_row(\n" +
            "\tinfo, price, menu)\n" +
            "\tVALUES (?, ?, ?);";

    private final static String INSERT_MENU_ROW_SQL = "INSERT INTO structure.menu_row(\n" +
            "\tdt_create, dt_update, info, price, menu)\n" +
            "\tVALUES ( ?, ?, ?, ?, ?);";

    private final static String SELECT_MENU_SQL = "SELECT id, dt_create, dt_update, name, enable\n" +
            "\tFROM structure.menu\n" +
            "\tWHERE enable = true;";

    private final static String SELECT_MENU_BY_ID_SQL = "SELECT id, dt_create, dt_update, name, enable\n" +
            "\tFROM structure.menu\n" +
            "\tWHERE id = ? ;";

    private final static String SELECT_MENU_ROW_BY_MENU_ID_SQL = "SELECT mr.id menu_row_id,\n" +
            "\tmr.dt_create mr_dt_create,\n" +
            "\tmr.dt_update mr_dt_update,\n" +
            "\tpi.id pi_id,\n" +
            "\tpi.dt_create pi_dt_create,\n" +
            "\tpi.dt_update pi_dt_update,\n" +
            "\tpi.name pi_name,\n" +
            "\tpi.description pi_descr,\n" +
            "\tpi.size pi_size,\n" +
            "\tprice,\n" +
            "\tmenu.id m_id,\n" +
            "\tmenu.name m_name,\n" +
            "\tmenu.dt_create m_dt_create,\n" +
            "\tmenu.dt_update m_dt_update,\n" +
            "\tmenu.enable m_enable\n" +
            "\tFROM structure.menu_row mr\n" +
            "\tINNER JOIN structure.pizza_info pi ON mr.info =pi.id\n" +
            "\tINNER JOIN structure.menu menu ON mr.menu=menu.id\n" +
            "\tWHERE menu.id = ?;";

    private final static String UPDATE_SQL = "UPDATE structure.menu\n" +
            "\tSET dt_update = ?, name = ?, enable = ?\n" +
            "\tWHERE id = ? and dt_update = ?;";

    private static final String DELETE_ROWS_SQL = "DELETE FROM structure.menu_row WHERE menu = ?;";

    private final static String DELETE_SQL = "DELETE FROM structure.menu\n" +
            "\tWHERE id = ? and dt_update = ?;";

    private final static String UNIQ_ERROR_CODE = "23505";

    private final static String MENU_NAME_UNIQ = "menu_name_uniq";
    private final static String ID = "id";


    private final DataSource ds;

    public MenuFullDao(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public IMenu create(IMenu item) throws DaoException, NotUniqDaoException {

        IMenu menu = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement stm = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmRows = conn.prepareStatement(INSERT_MENU_ROW_SQL)
        ) {
            stm.setObject(1, item.getDtCreate());
            stm.setObject(2, item.getDtUpdate());
            stm.setString(3, item.getName());
            stm.setBoolean(4, item.isEnable());

            int updated = stm.executeUpdate();

            try (ResultSet rs = stm.getGeneratedKeys()) {
                while (rs.next()) {
                    long menuId = rs.getLong(ID);

                    for (IMenuRow row : item.getItems()) {
                        stmRows.setObject(1, row.getDtCreate());
                        stmRows.setObject(2, row.getDtUpdate());
                        stmRows.setLong(3, row.getInfo().getId());
                        stmRows.setDouble(4, row.getPrice());
                        stmRows.setDouble(5, menuId);

                        stmRows.addBatch();
                    }
                    stmRows.executeBatch();

                    menu = read(rs.getLong(ID));
                }
            }
        } catch (SQLException e) {
            if (UNIQ_ERROR_CODE.equals(e.getSQLState())) {
                if (e.getMessage().contains(MENU_NAME_UNIQ)) {
                    throw new NotUniqDaoException("Ошибка, такое меню уже существует", e);
                }
            } else {
                throw new DaoException("При сохранении данных произошла ошибка", e);
            }
        }
        return menu;

    }

    @Override
    public IMenu read(long id) throws DaoException {

        IMenu menu = null;
        try (Connection connection = ds.getConnection();
             PreparedStatement stmMenu = connection.prepareStatement(SELECT_MENU_BY_ID_SQL);
             PreparedStatement stmMenuRow = connection.prepareStatement(SELECT_MENU_ROW_BY_MENU_ID_SQL)
        ) {
            stmMenu.setLong(1, id);

            try (ResultSet rs = stmMenu.executeQuery()) {
                while (rs.next()) {
                    stmMenuRow.setLong(1, id);
                    try (ResultSet rsItem = stmMenuRow.executeQuery()) {
                        menu = mapper(rs, rsItem);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("При чтении данных произошла ошибка", e);
        }
        return menu;
    }

    @Override
    public List<IMenu> get() throws DaoException {

        List<IMenu> items = new ArrayList<>();

        try (Connection connection = ds.getConnection();
             PreparedStatement stmMenu = connection.prepareStatement(SELECT_MENU_SQL);
             PreparedStatement stmMenuRow = connection.prepareStatement(SELECT_MENU_ROW_BY_MENU_ID_SQL)
        ) {
            try (ResultSet rs = stmMenu.executeQuery()) {
                while (rs.next()) {
                    stmMenuRow.setLong(1, rs.getLong("id"));
                    try (ResultSet rsItem = stmMenuRow.executeQuery()) {
                        items.add(mapper(rs, rsItem));
                    }
                }
            }

        } catch (SQLException e) {
            throw new DaoException("При чтении данных произошла ошибка", e);
        }
        return items;
    }

    @Override
    public IMenu update(long id, LocalDateTime dtUpdate, IMenu item) throws DaoException, NotUniqDaoException {

        try (Connection conn = ds.getConnection();
             PreparedStatement stm = conn.prepareStatement(UPDATE_SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmRowsDel = conn.prepareStatement(DELETE_ROWS_SQL);
             PreparedStatement stmRowsIns = conn.prepareStatement(INSERT_MENU_ROW_SQL)
        ) {
            conn.setAutoCommit(false); //???
            stm.setObject(1, item.getDtUpdate());
            stm.setString(2, item.getName());
            stm.setBoolean(3, item.isEnable());


            stm.setLong(4, id);
            stm.setObject(5, dtUpdate);

            int countUpdatedRows = stm.executeUpdate();

            if (countUpdatedRows != 1) {
                if (countUpdatedRows == 0) {
                    throw new IllegalArgumentException("Не смогли обновить какую либо запись");
                } else {
                    throw new IllegalArgumentException("Обновили более одной записи");
                }
            }

            stmRowsDel.setLong(1, item.getId());
            stmRowsDel.executeUpdate();

            for (IMenuRow row : item.getItems()) {
                stmRowsIns.setObject(1, row.getDtCreate());
                stmRowsIns.setObject(2, row.getDtUpdate());
                stmRowsIns.setLong(3, row.getInfo().getId());
                stmRowsIns.setDouble(4, row.getPrice());
                stmRowsIns.setDouble(5, row.getMenu().getId());

                stmRowsIns.addBatch();
            }

            stmRowsIns.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            if (UNIQ_ERROR_CODE.equals(e.getSQLState())) {
                if (e.getMessage().contains(MENU_NAME_UNIQ)) {
                    throw new NotUniqDaoException("Ошибка, такое меню уже существует", e);
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


    private IMenu mapper(ResultSet rs, ResultSet rsItem) throws SQLException {
        IMenu menu = new Menu(
                rs.getLong("id"),
                rs.getObject("dt_create", LocalDateTime.class),
                rs.getObject("dt_update", LocalDateTime.class),
                rs.getString("name"),
                rs.getBoolean("enable")
        );

        List<IMenuRow> menuRows = new ArrayList<>();
        while (rsItem.next()) {
            IPizzaInfo pizzaInfo = new PizzaInfo(
                    rsItem.getLong("pi_id"),
                    rsItem.getObject("pi_dt_create", LocalDateTime.class),
                    rsItem.getObject("pi_dt_update", LocalDateTime.class),
                    rsItem.getString("pi_name"),
                    rsItem.getString("pi_descr"),
                    rsItem.getLong("pi_size")
            );

            IMenuRow menuRow = new MenuRow(
                    rsItem.getLong("menu_row_id"),
                    rsItem.getObject("mr_dt_create", LocalDateTime.class),
                    rsItem.getObject("mr_dt_update", LocalDateTime.class),
                    pizzaInfo,
                    rsItem.getDouble("price")
            );
            menuRows.add(menuRow);
        }
        menu.setItems(menuRows);

        return menu;
    }
}


package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.Menu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.MenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.PizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.SelectedItem;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ISelectedItem;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.ISelectedItemDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SelectedItemDao implements ISelectedItemDao {

    private final static String INSERT_SQL = "INSERT INTO structure.selected_item(\n" +
            "\tdt_create, dt_update, menu_row, count, order_id)\n" +
            "\tVALUES ( ?, ?, ?, ?, ?);";

    private final static String SELECT_BY_ID_SQL = "SELECT si.id si_id, \n" +
            "\tsi.dt_create si_dt_create, \n" +
            "\tsi.dt_update si_dt_update, \n" +
            "\tsi.menu_row si_menu_row, \n" +
            "\tcount, \n" +
            "\tsi.order_id si_order,\n" +
            "\to.id o_id,\n" +
            "\to.dt_create o_dt_create, \n" +
            "\to.dt_update o_dt_update,\n" +
            "\tmr.id menu_row_id,\n" +
            "\tmr.dt_create mr_dt_create, \n" +
            "\tmr.dt_update mr_dt_update, \n" +
            "\tpi.id pi_id,\n" +
            "\tpi.dt_create pi_dt_create,\n" +
            "\tpi.dt_update pi_dt_update,\n" +
            "\tpi.name pi_name, \n" +
            "\tpi.description pi_descr,\n" +
            "\tpi.size pi_size,\n" +
            "\tprice, \n" +
            "\tmenu.id m_id,\n" +
            "\tmenu.name m_name,\n" +
            "\tmenu.dt_create m_dt_create, \n" +
            "\tmenu.dt_update m_dt_update, \n" +
            "\tmenu.enable m_enable\n" +
            "\tFROM structure.selected_item si\n" +
            "\tINNER JOIN structure.menu_row mr ON si.menu_row = mr.id\n" +
            "\tINNER JOIN structure.order_t o ON si.order_id = o.id\n" +
            "\tINNER JOIN structure.pizza_info pi ON mr.info =pi.id\n" +
            "\tINNER JOIN structure.menu menu ON mr.menu=menu.id\n" +
            "\tWHERE si.id = ?;";

    private final static String SELECT_SQL = "SELECT si.id si_id, \n" +
            "\tsi.dt_create si_dt_create, \n" +
            "\tsi.dt_update si_dt_update, \n" +
            "\tsi.menu_row si_menu_row, \n" +
            "\tcount, \n" +
            "\tsi.order_id si_order,\n" +
            "\to.id o_id,\n" +
            "\to.dt_create o_dt_create, \n" +
            "\to.dt_update o_dt_update,\n" +
            "\tmr.id menu_row_id,\n" +
            "\tmr.dt_create mr_dt_create, \n" +
            "\tmr.dt_update mr_dt_update, \n" +
            "\tpi.id pi_id,\n" +
            "\tpi.dt_create pi_dt_create,\n" +
            "\tpi.dt_update pi_dt_update,\n" +
            "\tpi.name pi_name, \n" +
            "\tpi.description pi_descr,\n" +
            "\tpi.size pi_size,\n" +
            "\tprice, \n" +
            "\tmenu.id m_id,\n" +
            "\tmenu.name m_name,\n" +
            "\tmenu.dt_create m_dt_create, \n" +
            "\tmenu.dt_update m_dt_update, \n" +
            "\tmenu.enable m_enable\n" +
            "\tFROM structure.selected_item si\n" +
            "\tINNER JOIN structure.menu_row mr ON si.menu_row = mr.id\n" +
            "\tINNER JOIN structure.order_t o ON si.order_id = o.id\n" +
            "\tINNER JOIN structure.pizza_info pi ON mr.info =pi.id\n" +
            "\tINNER JOIN structure.menu menu ON mr.menu=menu.id";

    private static final String DELETE_SQL = "DELETE FROM structure.selected_item\n" +
            "\tWHERE id = ? and dt_update = ?;";

    private final static String ID = "id";

    //private static final String UNIQ_ERROR_CODE = "23505";
    //private static final String MENU_ROW_INFO_MENU_UNIQ = "menu_row_info_menu_uniq";

    private final DataSource ds;

    public SelectedItemDao(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public ISelectedItem create(ISelectedItem item) throws DaoException {

        ISelectedItem selectedItem = null;

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {

            stm.setObject(1, item.getDtCreate());
            stm.setObject(2, item.getDtUpdate());
            stm.setLong(3, item.getMenuRow().getId());
            stm.setDouble(4, item.getCount());
            stm.setLong(5, item.getOrder().getId());

            int updated = stm.executeUpdate();

            try (ResultSet rs = stm.getGeneratedKeys()) {
                while (rs.next()) {
                    selectedItem = read(rs.getLong(ID));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("При сохранении данных произошла ошибка", e);
        }
        return selectedItem;
    }

    @Override
    public ISelectedItem read(long id) throws DaoException {

        ISelectedItem selectedItem = null;

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_BY_ID_SQL)
        ) {

            stm.setObject(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    selectedItem = mapper(rs);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("При чтении данных произошла ошибка", e);
        }
        return selectedItem;
    }

    @Override
    public List<ISelectedItem> get() throws DaoException {

        List<ISelectedItem> selectedItems = new ArrayList<>();

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_SQL)) {

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    selectedItems.add(mapper(rs));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("При чтении данных произошла ошибка", e);
        }
        return selectedItems;
    }

    @Override
    public ISelectedItem update(long id, LocalDateTime dtUpdate, ISelectedItem item) {
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
            throw new DaoException("При удалении данных произошла ошибка", e);
        }

    }

    private ISelectedItem mapper(ResultSet rs) throws SQLException {
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

        MenuRow menuRow = new MenuRow(
                rs.getLong("menu_row_id"),
                rs.getObject("mr_dt_create", LocalDateTime.class),
                rs.getObject("mr_dt_update", LocalDateTime.class),
                pizzaInfo,
                rs.getDouble("price"),
                menu
        );

        return new SelectedItem(
                rs.getLong("si_id"),
                rs.getObject("si_dt_create", LocalDateTime.class),
                rs.getObject("si_dt_update", LocalDateTime.class),
                menuRow,
                rs.getInt("count")
        );
    }
}

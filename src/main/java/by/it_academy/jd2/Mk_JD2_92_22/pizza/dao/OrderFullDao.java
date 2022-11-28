package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.*;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.*;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IOrderFullDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.DaoException;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.exception.NotUniqDaoException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderFullDao implements IOrderFullDao {

    private final static String INSERT_SQL = "INSERT INTO structure.order_t(\n" +
            "\tdt_create, dt_update)\n" +
            "\tVALUES ( ?, ?);";

    private final static String INSERT_SELECTED_ITEM_SQL = "INSERT INTO structure.selected_item(\n" +
            "\tdt_create, dt_update, menu_row, count, order_id)\n" +
            "\tVALUES ( ?, ?, ?, ?, ?);";

    private final static String SELECT_ORDER_SQL = "SELECT id o_id, dt_create o_dt_create, dt_update o_dt_update\n" +
            "\tFROM structure.order_t;";

    private final static String SELECT_ORDER_BY_ID_SQL = "SELECT id o_id, dt_create o_dt_create, dt_update o_dt_update\n" +
            "\tFROM structure.order_t\n" +
            "\tWHERE id = ?;";

    private final static String SELECT_SELECTED_ITEM_BY_ORDER_ID_SQL = "SELECT si.id si_id, \n" +
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
            "\tWHERE o.id = ?;";

    private final static String UPDATE_SQL = "UPDATE structure.menu\n" +
            "\tSET dt_update = ?, name = ?, enable = ?\n" +
            "\tWHERE id = ? and dt_update = ?;";

    private static final String DELETE_ROWS_SQL = "DELETE FROM structure.menu_row WHERE menu = ?;";

    private final static String DELETE_SQL = "DELETE FROM structure.selected_item\n" +
            "\tWHERE id = ? and dt_update = ?;";

    private final static String UNIQ_ERROR_CODE = "23505";

    //    private final static String MENU_NAME_UNIQ = "menu_name_uniq";
    private final static String ID = "id";


    private final DataSource ds;

    public OrderFullDao(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public IOrder create(IOrder item) throws DaoException, NotUniqDaoException {

        IOrder order = null;

        try (Connection conn = ds.getConnection();
             PreparedStatement stm = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmItems = conn.prepareStatement(INSERT_SELECTED_ITEM_SQL)
        ) {
            stm.setObject(1, item.getDtCreate());
            stm.setObject(2, item.getDtUpdate());

            int updated = stm.executeUpdate();

            try (ResultSet rs = stm.getGeneratedKeys()) {

                while (rs.next()) {
                    long orderId = rs.getLong(ID);

                    for (ISelectedItem selectedItem : item.getSelected()) {
                        stmItems.setObject(1, selectedItem.getDtCreate());
                        stmItems.setObject(2, selectedItem.getDtUpdate());
                        stmItems.setLong(3, selectedItem.getMenuRow().getId());
                        stmItems.setLong(4, selectedItem.getCount());
                        stmItems.setLong(5, orderId);

                        stmItems.addBatch();
                    }
                    stmItems.executeBatch();

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
             PreparedStatement stmOrder = connection.prepareStatement(SELECT_ORDER_BY_ID_SQL);
             PreparedStatement stmSelItem = connection.prepareStatement(SELECT_SELECTED_ITEM_BY_ORDER_ID_SQL)
        ) {
            stmOrder.setLong(1, id);

            try (ResultSet rs = stmOrder.executeQuery()) {
                while (rs.next()) {

                    stmSelItem.setLong(1, id);

                    try (ResultSet rsItem = stmSelItem.executeQuery()) {
                        order = mapper(rs, rsItem);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("При чтении данных произошла ошибка", e);
        }
        return order;
    }

    @Override
    public List<IOrder> get() throws DaoException {

        List<IOrder> items = new ArrayList<>();

        try (Connection connection = ds.getConnection();
             PreparedStatement stmOrder = connection.prepareStatement(SELECT_ORDER_SQL);
             PreparedStatement stmSelItem = connection.prepareStatement(SELECT_SELECTED_ITEM_BY_ORDER_ID_SQL)
        ) {
            try (ResultSet rs = stmOrder.executeQuery()) {
                while (rs.next()) {
                    stmSelItem.setLong(1, rs.getLong("o_id"));
                    try (ResultSet rsItem = stmSelItem.executeQuery()) {
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
    public IOrder update(long id, LocalDateTime dtUpdate, IOrder item) throws DaoException, NotUniqDaoException {
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


    private IOrder mapper(ResultSet rs, ResultSet rsItem) throws SQLException {
        Order order = new Order(
                rs.getLong("o_id"),
                rs.getObject("o_dt_create", LocalDateTime.class),
                rs.getObject("o_dt_update", LocalDateTime.class)
        );
        List<ISelectedItem> selectedItems = new ArrayList<>();

        while (rsItem.next()) {

            PizzaInfo pizzaInfo = new PizzaInfo(
                    rsItem.getLong("pi_id"),
                    rsItem.getObject("pi_dt_create", LocalDateTime.class),
                    rsItem.getObject("pi_dt_update", LocalDateTime.class),
                    rsItem.getString("pi_name"),
                    rsItem.getString("pi_descr"),
                    rsItem.getLong("pi_size")
            );

            Menu menu = new Menu(
                    rsItem.getLong("m_id"),
                    rsItem.getObject("m_dt_create", LocalDateTime.class),
                    rsItem.getObject("m_dt_update", LocalDateTime.class),
                    rsItem.getString("m_name"),
                    rsItem.getBoolean("m_enable")
            );

            MenuRow menuRow = new MenuRow(
                    rsItem.getLong("menu_row_id"),
                    rsItem.getObject("mr_dt_create", LocalDateTime.class),
                    rsItem.getObject("mr_dt_update", LocalDateTime.class),
                    pizzaInfo,
                    rsItem.getDouble("price"),
                    menu
            );

            SelectedItem selectedItem = new SelectedItem(
                    rsItem.getLong("si_id"),
                    rsItem.getObject("si_dt_create", LocalDateTime.class),
                    rsItem.getObject("si_dt_update", LocalDateTime.class),
                    menuRow,
                    rsItem.getInt("count")
            );
            selectedItems.add(selectedItem);
        }
        order.setSelected(selectedItems);

        return order;
    }
}


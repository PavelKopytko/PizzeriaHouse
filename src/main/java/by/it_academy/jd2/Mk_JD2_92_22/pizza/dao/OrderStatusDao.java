package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrderStatus;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IOrderStatusDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.OrderStatusMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusDao implements IOrderStatusDao {

    private final static String SELECT_BY_ID_SQL = "SELECT os.id os_id,\n" +
            "\tos.dt_create os_dt_create,\n" +
            "\tos.dt_update os_dt_update,\n" +
            "\ttc.id tc_id,\n" +
            "\ttc.dt_create tc_dt_create,\n" +
            "\ttc.dt_update tc_dt_update,\n" +
            "\ttc.number tc_number,\n" +
            "\to.id o_id,\n" +
            "\to.dt_create o_dt_create,\n" +
            "\to.dt_update o_dt_update,\n" +
            "\tdone,\n" +
            "\ts.id s_id,\n" +
            "\ts.dt_create s_dt_create,\n" +
            "\ts.dt_update s_dt_update,\n" +
            "\ts.description s_description,\n" +
            "\ts.order_status s_order_status\t\n" +
            "\tFROM structure.order_status os\n" +
            "\tINNER JOIN structure.stage s ON s.order_status = os.id\n" +
            "\tINNER JOIN structure.ticket tc ON os.ticket = tc.id\n" +
            "\tINNER JOIN structure.order_t o ON tc.order_id = o.id\n" +
            "\tWHERE os.id = ?;";

    private static final String DELETE_SQL = "DELETE FROM structure.order_t\n" +
            "\tWHERE id = ? and dt_update = ?;";

    private static final String ID = "id";

    private final DataSource ds;

    public OrderStatusDao(DataSource ds) {
        this.ds = ds;
    }


    @Override
    public IOrderStatus read(long id) {

        IOrderStatus orderStatus;

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_BY_ID_SQL)) {

            stm.setObject(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                orderStatus = OrderStatusMapper.mapper(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("При чтении данных произошла ошибка", e);
        }
        return orderStatus;
    }

//    @Override
//  public void delete(long id, LocalDateTime dtUpdate)
//
//      try (Connection conn = ds.getConnection();
//           PreparedStatement stm = conn.prepareStatement(DELETE_SQL, Statement.RETURN_GENERATED_KEYS)
//      ) {
//          stm.setLong(1, id);
//          stm.setObject(2, dtUpdate
//
//          int countUpdatedRows = stm.executeUpdate(
//
//          if (countUpdatedRows != 1) {
//              if (countUpdatedRows == 0) {
//                  throw new IllegalArgumentException("Не смогли удалить какую либо запись");
//              } else {
//                  throw new IllegalArgumentException("Удалили более одной записи");
//              }
//          }
//      } catch (SQLException e) {
//          throw new RuntimeException("При удалении данных произошла ошибка", e);
//      }
//  }
}

package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IStage;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IStageDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.StageMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StageDao implements IStageDao<IStage> {

    private final static String INSERT_SQL = "INSERT INTO structure.stage(\n" +
            "\tdt_create, dt_update, description)\n" +
            "\tVALUES ( ?, ?, ?);";

    private final static String SELECT_BY_ID_SQL = "SELECT s.id s_id, \n" +
            "\ts.dt_create s_dt_create,\n" +
            "\ts.dt_update s_dt_update,\n" +
            "\ts.description s_description\n" +
            "\tFROM structure.stage s\n" +
            "\tWHERE s.id = ? ;";

    private final static String SELECT_SQL = "SELECT s.id s_id, \n" +
            "\ts.dt_create s_dt_create,\n" +
            "\ts.dt_update s_dt_update,\n" +
            "\ts.description s_description\n" +
            "\tFROM structure.stage s;";

    private static final String UPDATE_SQL = "UPDATE structure.stage\n" +
            "\tSET dt_update=?, description=?\n" +
            "\tWHERE id =? and dt_update = ?;";

    private static final String DELETE_SQL = "DELETE FROM structure.stage\n" +
            "\tWHERE id = ? and dt_update = ?;";

//    private static final String UNIQ_ERROR_CODE = "23505";
//    private static final String PIZZA_INFO_NAME_SIZE_UNIQ = "pizza_info_name_size_uniq";

    private final DataSource ds;

    public StageDao(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public IStage create(IStage item){ //throws DaoException {

        IStage stage = null;
        try {
            try (Connection con = ds.getConnection();
                 PreparedStatement stm = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

                stm.setObject(1, item.getDtCreate());
                stm.setObject(2, item.getDtUpdate());
                stm.setString(3, item.getDescription());

                int updated = stm.executeUpdate();

                ResultSet rs = stm.getGeneratedKeys();
                rs.next();
                stage = read(rs.getLong("id"));

            } catch (SQLException e) {
                throw new RuntimeException("При сохранении данных произошла ошибка", e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stage;
    }

    @Override
    public IStage read(long id) {

        IStage stage = null;

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_BY_ID_SQL);) {

            stm.setObject(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                rs.next();
                stage = StageMapper.mapper(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("При чтении данных произошла ошибка", e);
        }
        return stage;
    }

    @Override
    public List<IStage> get() {

        List<IStage> stages = new ArrayList<>();

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(SELECT_SQL);) {

            try (ResultSet rs = stm.executeQuery();) {
                while (rs.next()) {
                    stages.add(StageMapper.mapper(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("При чтении данных произошла ошибка", e);
        }


        return stages;
    }

    @Override
    public IStage update(long id, LocalDateTime dtUpdate, IStage item) {
        IStage stage = null;

        try (Connection con = ds.getConnection();
             PreparedStatement stm = con.prepareStatement(UPDATE_SQL, Statement.RETURN_GENERATED_KEYS);) {

            stm.setObject(1, item.getDtUpdate());
            stm.setString(2, item.getDescription());

            stm.setLong(3, id);
            stm.setObject(4, dtUpdate);

            int countUpdatedRows = stm.executeUpdate();

            if (countUpdatedRows != 1) {
                if (countUpdatedRows == 0) {
                    throw new IllegalArgumentException("Не смогли обновить какую либо запись");
                } else {
                    throw new IllegalArgumentException("Обновили более одной записи");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("При сохранении данных произошла ошибка", e);
        }

        return read(id);
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

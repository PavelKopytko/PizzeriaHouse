package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.DoneOrderDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.DataSourceCreator2;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IDoneOrderDao;

public class DoneOrderDaoSingleton {

    private IDoneOrderDao doneOrder;
    private volatile static DoneOrderDaoSingleton instance;

    public DoneOrderDaoSingleton() {
        try {
            this.doneOrder = new DoneOrderDao(DataSourceCreator2.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static IDoneOrderDao getInstance() {
        if (instance == null) {
            synchronized (DoneOrderDaoSingleton.class) {
                if (instance == null) {
                    instance = new DoneOrderDaoSingleton();
                }
            }
        }
        return instance.doneOrder;
    }
}

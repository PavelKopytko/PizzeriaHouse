package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.TicketDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.DataSourceCreator2;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.ITicketDao;

public class TicketDaoSingleton {

    private ITicketDao storage;
    private volatile static TicketDaoSingleton instance;

    public TicketDaoSingleton() {
        try {
            this.storage = new TicketDao(DataSourceCreator2.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static ITicketDao getInstance() {
        if (instance == null) {
            synchronized (TicketDaoSingleton.class) {
                if (instance == null) {
                    instance = new TicketDaoSingleton();
                }
            }
        }
        return instance.storage;
    }
}

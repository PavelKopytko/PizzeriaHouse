package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.SelectedItemDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.DataSourceCreator2;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.ISelectedItemDao;

public class SelectedItemDaoSingleton {

    private ISelectedItemDao selectedItemDao;
    private volatile static SelectedItemDaoSingleton instance;

    public SelectedItemDaoSingleton() {
        try {
            this.selectedItemDao = new SelectedItemDao(DataSourceCreator2.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static ISelectedItemDao getInstance() {
        if (instance == null) {
            synchronized (SelectedItemDaoSingleton.class) {
                if (instance == null) {
                    instance = new SelectedItemDaoSingleton();
                }
            }
        }
        return instance.selectedItemDao;
    }
}

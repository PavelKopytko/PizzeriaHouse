package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.MenuRowDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.DataSourceCreator2;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IMenuRowDao;

public class MenuRowDaoSingleton {

    private IMenuRowDao menuRowDao;
    private volatile static MenuRowDaoSingleton instance;

    public MenuRowDaoSingleton() {
        try {
            this.menuRowDao = new MenuRowDao(DataSourceCreator2.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static IMenuRowDao getInstance() {
        if (instance == null) {
            synchronized (MenuRowDaoSingleton.class) {
                if (instance == null) {
                    instance = new MenuRowDaoSingleton();
                }
            }
        }
        return instance.menuRowDao;
    }
}

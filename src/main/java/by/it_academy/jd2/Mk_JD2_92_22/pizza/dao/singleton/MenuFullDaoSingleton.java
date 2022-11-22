package by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.MenuFullDao;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.DataSourceCreator2;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.api.IMenuFullDao;

public class MenuFullDaoSingleton {

    private IMenuFullDao menuFullDao;
    private volatile static MenuFullDaoSingleton instance;

    public MenuFullDaoSingleton() {
        try {
            this.menuFullDao = new MenuFullDao(DataSourceCreator2.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        }
    }

    public static IMenuFullDao getInstance() {
        if (instance == null) {
            synchronized (MenuFullDaoSingleton.class) {
                if (instance == null) {
                    instance = new MenuFullDaoSingleton();
                }
            }
        }
        return instance.menuFullDao;
    }
}

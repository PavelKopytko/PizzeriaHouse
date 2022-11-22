package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.MenuFullDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.MenuFullMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.MenuFullService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IMenuFullService;

public class MenuFullServiceSingleton {

    private IMenuFullService service;
    private volatile static MenuFullServiceSingleton instance;

    public MenuFullServiceSingleton() {
            this.service = new MenuFullService(
                    MenuFullDaoSingleton.getInstance(),
                    PizzaInfoServiceSingleton.getInstance(),
                    new MenuFullMapper());
    }

    public static IMenuFullService getInstance() {
        if (instance == null) {
            synchronized (MenuFullServiceSingleton.class) {
                if (instance == null) {
                    instance = new MenuFullServiceSingleton();
                }
            }
        }
        return instance.service;
    }
}

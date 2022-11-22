package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.MenuRowDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.MenuMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.MenuRowMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.PizzaInfoMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.MenuRowService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IMenuRowService;

public class MenuRowServiceSingleton {

    private IMenuRowService menuRowService;
    private MenuRowMapper menuRowMapper;
    private PizzaInfoMapper pizzaInfoMapper;
    private volatile static MenuRowServiceSingleton instance;

    public MenuRowServiceSingleton() {

        this.menuRowService = new MenuRowService(
                MenuRowDaoSingleton.getInstance(),
                PizzaInfoServiceSingleton.getInstance(),
                MenuServiceSingleton.getInstance(),
                new MenuRowMapper(new PizzaInfoMapper(),new MenuMapper()));
    }

    public static IMenuRowService getInstance() {
        if (instance == null) {
            synchronized (MenuRowServiceSingleton.class) {
                if (instance == null) {
                    instance = new MenuRowServiceSingleton();
                }
            }
        }
        return instance.menuRowService;
    }
}

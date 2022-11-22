package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.MenuDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.helper.mapper.MenuMapper;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.MenuService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IMenuService;

public class MenuServiceSingleton {

    private IMenuService menuService;
    private volatile static MenuServiceSingleton instance;

    public MenuServiceSingleton() {
        //try {
            this.menuService = new MenuService(MenuDaoSingleton.getInstance(), new MenuMapper());
       // } catch (Exception e) {
           // throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
       // }
    }

    public static IMenuService getInstance() {
        if (instance == null) {
            synchronized (MenuServiceSingleton.class) {
                if (instance == null) {
                    instance = new MenuServiceSingleton();
                }
            }
        }
        return instance.menuService;
    }
}

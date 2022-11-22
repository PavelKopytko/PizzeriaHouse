package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.MenuRowDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.OrderDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.SelectedItemDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.TicketDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.SelectedItemService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.ISelectedItemService;

public class SelectedItemServiceSingleton {

    private ISelectedItemService selectedItemService;
    private volatile static SelectedItemServiceSingleton instance;

    public SelectedItemServiceSingleton() {
        //try {
        this.selectedItemService = new SelectedItemService(
                SelectedItemDaoSingleton.getInstance(),
                MenuRowDaoSingleton.getInstance(),
                OrderDaoSingleton.getInstance(),
                TicketDaoSingleton.getInstance());
        // } catch (Exception e) {
        // throw new RuntimeException("Возникли проблемы с созданием слоя доступа к данным", e);
        // }
    }

    public static ISelectedItemService getInstance() {
        if (instance == null) {
            synchronized (SelectedItemServiceSingleton.class) {
                if (instance == null) {
                    instance = new SelectedItemServiceSingleton();
                }
            }
        }
        return instance.selectedItemService;
    }
}

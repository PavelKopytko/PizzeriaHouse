package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.singleton;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.dao.singleton.TicketDaoSingleton;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.TicketService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.IOrderService;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.service.api.ITicketService;

public class TicketServiceSingleton {

    private IOrderService orderService;
    private ITicketService ticketService;
    private volatile static TicketServiceSingleton instance;

    public TicketServiceSingleton() {
        this.ticketService = new TicketService(TicketDaoSingleton.getInstance(), OrderServiceSingleton.getInstance());
    }

    public static ITicketService getInstance() {
        if (instance == null) {
            synchronized (TicketServiceSingleton.class) {
                if (instance == null) {
                    instance = new TicketServiceSingleton();
                }
            }
        }
        return instance.ticketService;
    }
}

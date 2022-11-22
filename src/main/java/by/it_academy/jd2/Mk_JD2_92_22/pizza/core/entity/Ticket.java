package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ITicket;

import java.time.LocalDateTime;

public class Ticket implements ITicket {

    private long id;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private String number;
    private IOrder order;

    public Ticket() {
    }

    public Ticket(LocalDateTime dtCreate, LocalDateTime dtUpdate, String number) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.number = number;
    }

    public Ticket(LocalDateTime dtCreate, LocalDateTime dtUpdate, String number, IOrder order) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.number = number;
        this.order = order;
    }

    public Ticket(long id, LocalDateTime dtCreate, LocalDateTime dtUpdate, String number, IOrder order) {
        this.id = id;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.number = number;
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    @Override
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public IOrder getOrder() {
        return order;
    }

    public void setOrder(IOrder order) {
        this.order = order;
    }
}

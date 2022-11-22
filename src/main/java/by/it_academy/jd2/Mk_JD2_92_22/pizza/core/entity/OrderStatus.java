package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrderStatus;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IStage;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ITicket;

import java.time.LocalDateTime;
import java.util.List;

public class OrderStatus implements IOrderStatus {

    private long id;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private ITicket ticket;
    private boolean isDone;
    private List<IStage> history;

    public OrderStatus() {
    }

    public OrderStatus(long id, LocalDateTime dtCreate, LocalDateTime dtUpdate, ITicket ticket, boolean isDone, List<IStage> history) {
        this.id = id;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.ticket = ticket;
        this.isDone = isDone;
        this.history = history;
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
    public ITicket getTicket() {
        return ticket;
    }

    public void setTicket(ITicket ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public List<IStage> getHistory() {
        return history;
    }

    public void setHistory(List<IStage> history) {
        this.history = history;
    }
}

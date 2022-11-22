package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IDoneOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizza;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ITicket;

import java.time.LocalDateTime;
import java.util.List;

public class DoneOrder implements IDoneOrder {

    private long id;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private ITicket ticket;
    private List<IPizza> items;

    public DoneOrder() {
    }

    public DoneOrder(long id, LocalDateTime dtCreate, LocalDateTime dtUpdate, ITicket ticket) {
        this.id = id;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.ticket = ticket;
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

    public List<IPizza> getItems() {
        return items;
    }

    public void setItems(List<IPizza> items) {
        this.items = items;
    }
}

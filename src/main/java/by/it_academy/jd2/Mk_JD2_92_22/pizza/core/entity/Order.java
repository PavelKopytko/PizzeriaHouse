package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ISelectedItem;

import java.time.LocalDateTime;
import java.util.List;

public class Order implements IOrder {

    private long id;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private List<ISelectedItem> selected;

    public Order() {
    }

    public Order(LocalDateTime dtCreate, LocalDateTime dtUpdate) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
    }

    public Order(long id, LocalDateTime dtCreate, LocalDateTime dtUpdate) {
        this.id = id;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
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

    public List<ISelectedItem> getSelected() {
        return selected;
    }

    public void setSelected(List<ISelectedItem> selected) {
        this.selected = selected;
    }
}

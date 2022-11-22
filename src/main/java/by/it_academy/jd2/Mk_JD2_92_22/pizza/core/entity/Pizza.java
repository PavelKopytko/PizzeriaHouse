package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IDoneOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizza;

import java.time.LocalDateTime;

public class Pizza implements IPizza {

    private long id;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private String name;
    private int size;
    private IDoneOrder order;

    public Pizza() {
    }

    public Pizza(LocalDateTime dtCreate, LocalDateTime dtUpdate, String name, int size, IDoneOrder order) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.size = size;
        this.order = order;
    }

    public Pizza(long id, LocalDateTime dtCreate, LocalDateTime dtUpdate, String name, int size, IDoneOrder order) {
        this.id = id;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.size = size;
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public IDoneOrder getOrder() {
        return order;
    }

    public void setOrder(IDoneOrder order) {
        this.order = order;
    }
}

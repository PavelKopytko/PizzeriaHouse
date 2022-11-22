package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrderStatus;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IStage;

import java.time.LocalDateTime;

public class Stage implements IStage {

    private long id;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private String description;

    private IOrderStatus orderStatus;

    public Stage() {
    }

    public Stage(LocalDateTime dtCreate, LocalDateTime dtUpdate, String description) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.description = description;
    }

    public Stage(long id, LocalDateTime dtCreate, LocalDateTime dtUpdate, String description) {
        this.id = id;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.description = description;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    @Override
    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    @Override
    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public IOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(IOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}

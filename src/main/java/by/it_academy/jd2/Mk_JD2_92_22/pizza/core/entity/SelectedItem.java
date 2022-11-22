package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IOrder;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.ISelectedItem;

import java.time.LocalDateTime;

public class SelectedItem implements ISelectedItem {

    private long id;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private IMenuRow menuRow;
    private int count;
    private IOrder order;

    public SelectedItem() {
    }

    public SelectedItem(LocalDateTime dtCreate, LocalDateTime dtUpdate, IMenuRow menuRow, int count, IOrder order) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.menuRow = menuRow;
        this.count = count;
        this.order = order;
    }

    public SelectedItem(LocalDateTime dtCreate, LocalDateTime dtUpdate, IMenuRow menuRow, int count) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.menuRow = menuRow;
        this.count = count;
    }

    public SelectedItem(long id, LocalDateTime dtCreate, LocalDateTime dtUpdate, IMenuRow menuRow, int count) {
        this.id = id;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.menuRow = menuRow;
        this.count = count;
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

    @Override
    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    @Override
    public IMenuRow getMenuRow() {
        return menuRow;
    }

    public void setMenuRow(IMenuRow menuRow) {
        this.menuRow = menuRow;
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public IOrder getOrder() {
        return order;
    }

    public void setOrder(IOrder order) {
        this.order = order;
    }
}

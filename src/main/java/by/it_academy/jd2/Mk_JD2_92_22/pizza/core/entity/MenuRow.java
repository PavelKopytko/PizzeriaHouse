package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IPizzaInfo;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;

import java.time.LocalDateTime;


public class MenuRow implements IMenuRow {

    private long id;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private IPizzaInfo info;
    private double price;
    private IMenu menu;

    public MenuRow() {
    }

    public MenuRow(LocalDateTime dtCreate, LocalDateTime dtUpdate, IPizzaInfo info, double price) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.info = info;
        this.price = price;
    }

    public MenuRow(LocalDateTime dtCreate, LocalDateTime dtUpdate, IPizzaInfo info, double price, IMenu menu) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.info = info;
        this.price = price;
        this.menu = menu;
    }

    public MenuRow(long id, LocalDateTime dtCreate, LocalDateTime dtUpdate, IPizzaInfo info, double price) {
        this.id = id;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.info = info;
        this.price = price;
    }

    public MenuRow(long id, LocalDateTime dtCreate, LocalDateTime dtUpdate, IPizzaInfo info, double price, IMenu menu) {
        this.id = id;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.info = info;
        this.price = price;
        this.menu = menu;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
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
    public IPizzaInfo getInfo() {
        return info;
    }

    @Override
    public void setInfo(IPizzaInfo info) {
        this.info = info;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    public IMenu getMenu() {
        return menu;
    }

    public void setMenu(IMenu menu) {
        this.menu = menu;
    }
}

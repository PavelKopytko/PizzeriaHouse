package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity;

import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenu;
import by.it_academy.jd2.Mk_JD2_92_22.pizza.core.entity.api.IMenuRow;

import java.time.LocalDateTime;
import java.util.List;

public class Menu implements IMenu {

    private long id;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private String name;
    private boolean enable;
    private List<IMenuRow> items;

    public Menu() {
    }

    public Menu(long id, LocalDateTime dtCreate, LocalDateTime dtUpdate, String name, boolean enable, List<IMenuRow> items) {
        this.id = id;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.enable = enable;
        this.items = items;
    }

    public Menu(LocalDateTime dtCreate, LocalDateTime dtUpdate, String name, boolean enable, List<IMenuRow> items) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.enable = enable;
        this.items = items;
    }

    public Menu(long id, LocalDateTime dtCreate, LocalDateTime dtUpdate, String name, boolean enable) {
        this.id = id;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.enable = enable;
    }

    public Menu(LocalDateTime dtCreate, LocalDateTime dtUpdate, String name, boolean enable) {
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.enable = enable;
    }

    public Menu(long id, LocalDateTime dtUpdate, String name, boolean enable) {
        this.id = id;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.enable = enable;
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

    @Override
    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public List<IMenuRow> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", name='" + name + '\'' +
                ", enable=" + enable +
                '}';
    }

    @Override
    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    @Override
    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setItems(List<IMenuRow> items) {
        this.items = items;
    }
}

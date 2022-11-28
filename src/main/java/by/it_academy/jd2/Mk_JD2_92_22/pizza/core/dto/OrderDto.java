package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto;

import java.util.List;

public class OrderDto {

    private long id;
    private long dtCreate;
    private long dtUpdate;
    private List<SelectedItemDto> items;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(long dtCreate) {
        this.dtCreate = dtCreate;
    }

    public long getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(long dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public List<SelectedItemDto> getItems() {
        return items;
    }

    public void setItems(List<SelectedItemDto> items) {
        this.items = items;
    }
}

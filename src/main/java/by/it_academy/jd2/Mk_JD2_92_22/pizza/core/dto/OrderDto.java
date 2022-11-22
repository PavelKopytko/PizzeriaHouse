package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto;

import java.util.List;

public class OrderDto {
    private List<SelectedItemDto> items;

    public List<SelectedItemDto> getItems() {
        return items;
    }

    public void setItems(List<SelectedItemDto> items) {
        this.items = items;
    }
}

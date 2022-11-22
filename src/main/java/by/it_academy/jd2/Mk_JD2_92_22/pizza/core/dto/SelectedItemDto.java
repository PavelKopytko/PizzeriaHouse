package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto;

public class SelectedItemDto {

    private MenuRowDto menuRowDto;
    private int count;

    public MenuRowDto getMenuRowDto() {
        return menuRowDto;
    }

    public void setMenuRowDto(MenuRowDto menuRowDto) {
        this.menuRowDto = menuRowDto;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

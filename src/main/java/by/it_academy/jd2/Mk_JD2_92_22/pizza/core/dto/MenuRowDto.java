package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto;

public class MenuRowDto {
    private long id;
    private long dt_update;
    private long infoId;
    private PizzaInfoDto info;
    private double price;
    private long menuId;
    private MenuDto menu;


    public MenuRowDto() {
    }

    public MenuRowDto(long id, long dt_update, long info, double price, long menu) {
        this.id = id;
        this.dt_update = dt_update;
        this.infoId = info;
        this.price = price;
        this.menuId = menu;
    }

    public MenuRowDto(long id, long dt_update, long infoId, PizzaInfoDto info, double price) {
        this.id = id;
        this.dt_update = dt_update;
        this.infoId = infoId;
        this.info = info;
        this.price = price;
    }

    public MenuRowDto(long id, long dt_update, PizzaInfoDto info, double price, long menuId) {
        this.id = id;
        this.dt_update = dt_update;
        this.info = info;
        this.price = price;
        this.menuId = menuId;
    }

    public MenuRowDto(long id, long dt_update, PizzaInfoDto info, double price) {
        this.id = id;
        this.dt_update = dt_update;
        this.info = info;
        this.price = price;
    }

    public MenuRowDto(long id, long dt_update, PizzaInfoDto info, double price, MenuDto menu) {
        this.id = id;
        this.dt_update = dt_update;
        this.info = info;
        this.price = price;
        this.menu = menu;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDt_update() {
        return dt_update;
    }

    public void setDt_update(long dt_update) {
        this.dt_update = dt_update;
    }

    public long getInfoId() {
        return infoId;
    }

    public void setInfoId(long infoId) {
        this.infoId = infoId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public PizzaInfoDto getInfo() {
        return info;
    }

    public void setInfo(PizzaInfoDto info) {
        this.info = info;
    }

    public MenuDto getMenu() {
        return menu;
    }

    public void setMenu(MenuDto menu) {
        this.menu = menu;
    }
}

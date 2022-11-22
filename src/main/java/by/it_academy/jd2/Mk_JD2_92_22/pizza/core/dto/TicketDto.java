package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto;

public class TicketDto {

    private long id;
    private long dtCreate;
    private long dtUpdate;
    private String number;
    private OrderDto order;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }
}

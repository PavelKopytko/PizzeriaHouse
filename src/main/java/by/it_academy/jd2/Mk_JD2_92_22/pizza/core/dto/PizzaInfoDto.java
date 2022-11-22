package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto;

import java.time.LocalDateTime;

public class PizzaInfoDto {

    private long id;
    private long dtUpdate;
    private String name;
    private String description;
    private long size;

    public PizzaInfoDto() {
    }

    public PizzaInfoDto(long id, long dtUpdate, String name, String description, long size) {
        this.id = id;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.description = description;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(long dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}

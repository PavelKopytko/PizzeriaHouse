package by.it_academy.jd2.Mk_JD2_92_22.pizza.core.dto;

import java.time.LocalDateTime;

public class StageDto {

    private long id;
    private long dtUpdate;
    private String description;

    public StageDto() {
    }

    public StageDto(long id, long dtUpdate, String description) {
        this.id = id;
        this.dtUpdate = dtUpdate;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

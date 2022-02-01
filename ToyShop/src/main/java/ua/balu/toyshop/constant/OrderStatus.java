package ua.balu.toyshop.constant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum OrderStatus {
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    CONSIDERATION("CONSIDERATION");
    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

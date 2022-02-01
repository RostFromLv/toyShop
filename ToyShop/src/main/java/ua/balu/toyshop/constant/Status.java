package ua.balu.toyshop.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public enum Status {
    ACTIVE("ACTIVE"),
    BANNED("BANNED"),
    ARCHIVED("ARCHIVED");

    private String name;

    Status(String name) { this.name = name;}

    public String getName() {
        return name;
    }

}

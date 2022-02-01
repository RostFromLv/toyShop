package ua.balu.toyshop.constant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Role {
    ADMIN("ADMIN"),
    USER("USER"),
    MANAGER("MANAGER");
    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

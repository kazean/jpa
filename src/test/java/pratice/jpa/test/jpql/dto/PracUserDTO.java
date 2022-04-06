package pratice.jpa.test.jpql.dto;

import lombok.Data;

@Data
public class PracUserDTO {
    private String username;
    private int age;

    public PracUserDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}

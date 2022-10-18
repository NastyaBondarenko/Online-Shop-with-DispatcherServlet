package entity;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String password;
    private String salt;
}

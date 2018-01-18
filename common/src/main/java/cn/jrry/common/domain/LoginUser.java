package cn.jrry.common.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class LoginUser implements java.io.Serializable {
    private static final long serialVersionUID = -8113987931597684598L;
    @NotBlank
    @Length(min = 4,max = 32)
    @Pattern(regexp="^[A-Za-z][A-Za-z0-9]{3,15}$")
    private String username;

//    @JsonIgnore
    @NotBlank
    @Length(min = 32,max = 32)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

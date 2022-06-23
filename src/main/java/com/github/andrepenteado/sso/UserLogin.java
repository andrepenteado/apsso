package com.github.andrepenteado.sso;

import lombok.Data;

@Data
public class UserLogin {

    private String username;

    private String role;

    private String ip;

}

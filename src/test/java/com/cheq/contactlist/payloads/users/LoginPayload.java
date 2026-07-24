package com.cheq.contactlist.payloads.users;

import com.cheq.contactlist.models.userrequestmodel.LoginRequest;

public class LoginPayload {

    public static LoginRequest createValidLogin() {

        LoginRequest login = new LoginRequest();
        login.setEmail("sjimena@faker.com");
        login.setPassword("12345qwert");

        return login;
    }

}
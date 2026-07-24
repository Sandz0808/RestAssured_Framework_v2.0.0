package com.cheq.contactlist.payloads.users;

import com.cheq.contactlist.models.userrequestmodel.LoginRequest;

import static com.cheq.contactlist.utils.JsonReaderUtil.getData;

public class LoginPayload {

    public static LoginRequest createValidLogin() {

        LoginRequest login = new LoginRequest();
        login.setEmail(getData("userLogin", "email"));
        login.setPassword(getData("userLogin", "password"));

        return login;
    }

}
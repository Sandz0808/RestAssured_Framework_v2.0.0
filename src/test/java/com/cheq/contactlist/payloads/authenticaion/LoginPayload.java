package com.cheq.contactlist.payloads.authenticaion;

import com.cheq.contactlist.models.userrequestmodel.LoginRequest;

import static com.cheq.contactlist.utilities.JsonReaderUtil.*;


public class LoginPayload {

    public static LoginRequest createLogin(int index) {

        LoginRequest login = new LoginRequest();
        login.setEmail(getDatalist("userLogin", index, "email"));
        login.setPassword(getDatalist("userLogin", index, "password"));

        return login;
    }

}
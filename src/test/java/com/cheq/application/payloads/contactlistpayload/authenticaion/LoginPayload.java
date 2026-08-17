package com.cheq.application.payloads.contactlistpayload.authenticaion;

import com.cheq.application.models.contactlistmodel.userrequestmodel.LoginRequest;

import static com.cheq.application.utilities.JsonReaderUtil.*;


public class LoginPayload {

    public static LoginRequest createLogin(int index) {

        LoginRequest login = new LoginRequest();
        login.setEmail(getDatalist("userLogin", index, "email"));
        login.setPassword(getDatalist("userLogin", index, "password"));

        return login;
    }

}
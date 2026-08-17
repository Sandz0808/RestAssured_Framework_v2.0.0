package com.cheq.application.tests.contactlist.reusables;

import com.cheq.application.hooks.Hooks;
import com.cheq.application.models.contactlistmodel.contactrequestmodel.CreateContact;
import com.cheq.application.models.contactlistmodel.userrequestmodel.CreateUser;
import com.cheq.application.models.contactlistmodel.userrequestmodel.LoginRequest;
import com.cheq.application.payloads.contactlistpayload.authenticaion.LoginPayload;
import com.cheq.application.payloads.contactlistpayload.contacts.AddContactPayload;
import com.cheq.application.payloads.contactlistpayload.users.CreateUserPayload;
import com.cheq.application.services.contactlistservice.AuthenticationService;
import com.cheq.application.services.contactlistservice.ContactService;
import com.cheq.application.services.contactlistservice.UserService;
import io.restassured.response.Response;


public class ReusableTest extends Hooks {

    public static Response login(int index) {

        LoginRequest payload = LoginPayload.createLogin(index);

        return AuthenticationService.loginUser(payload);
    }

    public static Response signUp(int index) {

        CreateUser payload = CreateUserPayload.createValidUser(index);

        return UserService.createUser(payload);
    }

    public static Response addContact(String token) {

        CreateContact payload = AddContactPayload.createValidContact(0);
        return ContactService.addContact(token, payload);
    }

    public static Response addContactParameterized(int index) {

        Response signUpResponse = signUp(0);
        String token = signUpResponse.jsonPath().getString("token");

        CreateContact payload = AddContactPayload.createValidContact(index);
        return ContactService.addContact(token, payload);
    }

}
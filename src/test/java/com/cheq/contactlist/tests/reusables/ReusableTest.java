package com.cheq.contactlist.tests.reusables;

import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.models.contactrequestmodel.CreateContact;
import com.cheq.contactlist.models.userrequestmodel.CreateUser;
import com.cheq.contactlist.models.userrequestmodel.LoginRequest;
import com.cheq.contactlist.payloads.authenticaion.LoginPayload;
import com.cheq.contactlist.payloads.contacts.AddContactPayload;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import com.cheq.contactlist.services.AuthenticationService;
import com.cheq.contactlist.services.ContactService;
import com.cheq.contactlist.services.UserService;
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
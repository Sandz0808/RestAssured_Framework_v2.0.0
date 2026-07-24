package com.cheq.contactlist.payloads.users;

import com.cheq.contactlist.models.userrequestmodel.CreateUser;
import com.cheq.contactlist.utils.JsonReaderUtil;

public class CreateUserPayload {

    public static CreateUser createValidUser() {

        String uniqueEmail = "SJimena_" + System.currentTimeMillis() + "@example.com";

        CreateUser user = new CreateUser();
        user.setFirstName(JsonReaderUtil.getUserData("firstName"));
        user.setLastName(JsonReaderUtil.getUserData("lastName"));
        user.setEmail(uniqueEmail);
        user.setPassword(JsonReaderUtil.getUserData("password"));

        return user;
    }

}
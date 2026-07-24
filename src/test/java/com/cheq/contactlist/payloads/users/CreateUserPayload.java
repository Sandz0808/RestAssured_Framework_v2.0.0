package com.cheq.contactlist.payloads.users;

import com.cheq.contactlist.models.userrequestmodel.CreateUser;
import com.cheq.contactlist.utils.JsonReaderUtil;

import static com.cheq.contactlist.utils.JsonReaderUtil.getData;

public class CreateUserPayload {

    public static CreateUser createValidUser() {

        String uniqueEmail = "SJimena_" + System.currentTimeMillis() + "@example.com";

        CreateUser user = new CreateUser();
        user.setFirstName(getData("userSignup", "firstName"));
        user.setLastName(getData("userSignup", "lastName"));
        user.setEmail(uniqueEmail);
        user.setPassword(getData("userSignup", "password"));

        return user;
    }

}
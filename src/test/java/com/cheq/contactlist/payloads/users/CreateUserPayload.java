package com.cheq.contactlist.payloads.users;

import com.cheq.contactlist.models.userrequestmodel.CreateUser;
import com.cheq.contactlist.utilities.TestDataRandomizer;
import static com.cheq.contactlist.utilities.JsonReaderUtil.*;

public class CreateUserPayload {

    public static CreateUser createValidUser(int index) {

        String uniqueEmail = "SJimena_" + System.currentTimeMillis() + "@example.com";

        CreateUser user = new CreateUser();

        user.setFirstName(getDatalist("userSignup", index, "firstName"));
        user.setLastName(getDatalist("userSignup", index, "lastName"));
        //user.setEmail(getDatalist("userSignup", index, "email"));
        user.setEmail(uniqueEmail);
        user.setPassword(getDatalist("userSignup", index, "password"));

        return user;
    }

}
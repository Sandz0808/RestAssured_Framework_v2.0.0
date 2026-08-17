package com.cheq.application.payloads.contactlistpayload.users;

import com.cheq.application.models.contactlistmodel.userrequestmodel.CreateUser;
import com.cheq.application.utilities.FakerDataUtil;

import static com.cheq.application.utilities.JsonReaderUtil.*;

public class CreateUserPayload {

    public static CreateUser createValidUser(int index) {

        String email = FakerDataUtil.email();

        CreateUser user = new CreateUser();

        user.setFirstName(getDatalist("userSignup", index, "firstName"));
        user.setLastName(getDatalist("userSignup", index, "lastName"));
        //user.setEmail(getDatalist("userSignup", index, "email"));
        user.setEmail(email);
        user.setPassword(getDatalist("userSignup", index, "password"));

        return user;
    }

}
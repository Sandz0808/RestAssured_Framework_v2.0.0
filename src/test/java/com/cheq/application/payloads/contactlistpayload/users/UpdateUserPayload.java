package com.cheq.application.payloads.contactlistpayload.users;

import com.cheq.application.models.contactlistmodel.userrequestmodel.UpdateUser;
import java.util.UUID;

public class UpdateUserPayload {

    public static UpdateUser updateValidUser() {

        String random = UUID.randomUUID().toString().substring(0, 4);

        UpdateUser user = new UpdateUser();

        user.setFirstName("Updated_" + random);
        user.setLastName("Updated_" + random);
        user.setEmail("Updated_" + random + "@example.com");
        user.setPassword("UpdatedPassword" + random);

        return user;
    }

}
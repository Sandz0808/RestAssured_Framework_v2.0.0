package com.cheq.contactlist.payloads.contacts;

import com.cheq.contactlist.models.contactrequestmodel.UpdateContact;
import com.cheq.contactlist.utils.JsonReaderUtil;

public class UpdateContactPayload {

    // =================================================================
    // DATA BUILDER: Generates fresh contact data for clean runs
    // =================================================================
    public static UpdateContact updateValidContact() {
        UpdateContact contact = new UpdateContact();

        String uniqueEmail = "SJimena_" + System.currentTimeMillis() + "@example.com";

        contact.setFirstName(JsonReaderUtil.getUpdatedData("firstName"));
        contact.setLastName(JsonReaderUtil.getUpdatedData("lastName"));
        contact.setBirthdate(JsonReaderUtil.getUpdatedData("birthdate"));
        contact.setBirthdate(JsonReaderUtil.getUpdatedData("birthdate"));
        contact.setEmail(uniqueEmail);
        contact.setPhone(JsonReaderUtil.getUpdatedData("phone"));
        contact.setStreet1(JsonReaderUtil.getUpdatedData("street1"));
        contact.setStreet2(JsonReaderUtil.getUpdatedData("street2"));
        contact.setCity(JsonReaderUtil.getUpdatedData("city"));
        contact.setStateProvince(JsonReaderUtil.getUpdatedData("stateProvince"));
        contact.setPostalCode(JsonReaderUtil.getUpdatedData("postalCode"));
        contact.setCountry(JsonReaderUtil.getUpdatedData("country"));

        return contact;
    }
}
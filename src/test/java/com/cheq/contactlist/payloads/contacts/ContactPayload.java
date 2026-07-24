package com.cheq.contactlist.payloads.contacts;

import com.cheq.contactlist.models.contactrequestmodel.CreateContact;
import com.cheq.contactlist.utils.JsonReaderUtil;

public class ContactPayload {

    // =================================================================
    // DATA BUILDER: Generates fresh contact data for clean runs
    // =================================================================
    public static CreateContact createValidContact() {
        CreateContact contact = new CreateContact();

        String uniqueEmail = "SJimena_" + System.currentTimeMillis() + "@example.com";

        contact.setFirstName(JsonReaderUtil.getContactData("firstName"));
        contact.setLastName(JsonReaderUtil.getContactData("lastName"));
        contact.setBirthdate(JsonReaderUtil.getContactData("birthdate"));
        contact.setEmail(uniqueEmail);
        contact.setPhone(JsonReaderUtil.getContactData("phone"));
        contact.setStreet1(JsonReaderUtil.getContactData("street1"));
        contact.setStreet2(JsonReaderUtil.getContactData("street2"));
        contact.setCity(JsonReaderUtil.getContactData("city"));
        contact.setStateProvince(JsonReaderUtil.getContactData("stateProvince"));
        contact.setPostalCode(JsonReaderUtil.getContactData("postalCode"));
        contact.setCountry(JsonReaderUtil.getContactData("country"));

        return contact;
    }
}
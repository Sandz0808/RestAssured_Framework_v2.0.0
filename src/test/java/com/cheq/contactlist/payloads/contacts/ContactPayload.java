package com.cheq.contactlist.payloads.contacts;

import com.cheq.contactlist.models.contactrequestmodel.CreateContact;
import static com.cheq.contactlist.utils.JsonReaderUtil.getData;

public class ContactPayload {

    // =================================================================
    // DATA BUILDER: Generates fresh contact data for clean runs
    // =================================================================
    public static CreateContact createValidContact() {
        CreateContact contact = new CreateContact();

        String uniqueEmail = "SJimena_" + System.currentTimeMillis() + "@example.com";

        contact.setFirstName(getData("addContact","firstName"));
        contact.setLastName(getData("addContact", "lastName"));
        contact.setBirthdate(getData("addContact","birthdate"));
        contact.setEmail(uniqueEmail);
        contact.setPhone(getData("addContact","phone"));
        contact.setStreet1(getData("addContact","street1"));
        contact.setStreet2(getData("addContact","street2"));
        contact.setCity(getData("addContact","city"));
        contact.setStateProvince(getData("addContact","stateProvince"));
        contact.setPostalCode(getData("addContact","postalCode"));
        contact.setCountry(getData("addContact","country"));

        return contact;
    }
}
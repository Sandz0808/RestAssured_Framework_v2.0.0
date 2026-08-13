package com.cheq.contactlist.payloads.contacts;

import com.cheq.contactlist.models.contactrequestmodel.UpdateContact;
import static com.cheq.contactlist.utilities.JsonReaderUtil.getData;


public class UpdateContactPayload {

    // =================================================================
    // DATA BUILDER: Generates fresh contact data for clean runs
    // =================================================================
    public static UpdateContact updateValidContact() {
        UpdateContact contact = new UpdateContact();

        String uniqueEmail = "SJimena_" + System.currentTimeMillis() + "@example.com";

        contact.setFirstName(getData("updateContact", "firstName"));
        contact.setLastName(getData("updateContact","lastName"));
        contact.setBirthdate(getData("updateContact","birthdate"));
        contact.setBirthdate(getData("updateContact","birthdate"));
        contact.setEmail(uniqueEmail);
        contact.setPhone(getData("updateContact","phone"));
        contact.setStreet1(getData("updateContact","street1"));
        contact.setStreet2(getData("updateContact","street2"));
        contact.setCity(getData("updateContact","city"));
        contact.setStateProvince(getData("updateContact","stateProvince"));
        contact.setPostalCode(getData("updateContact","postalCode"));
        contact.setCountry(getData("updateContact","country"));

        return contact;
    }
}
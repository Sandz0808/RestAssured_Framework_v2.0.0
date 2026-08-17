package com.cheq.application.payloads.contactlistpayload.contacts;

import com.cheq.application.models.contactlistmodel.contactrequestmodel.CreateContact;

import static com.cheq.application.utilities.JsonReaderUtil.getDatalist;

public class AddContactPayload {

    // =================================================================
    // DATA BUILDER: Generates fresh contact data for clean runs
    // =================================================================
    public static CreateContact createValidContact(int index) {
        CreateContact contact = new CreateContact();

        String uniqueEmail = "SJimena_" + System.currentTimeMillis() + "@example.com";

        contact.setFirstName(getDatalist("addContact",index, "firstName"));
        contact.setLastName(getDatalist("addContact", index, "lastName"));
        contact.setBirthdate(getDatalist("addContact",index, "birthdate"));
        //contact.setEmail(getDatalist("addContact",index, "email"));
        contact.setEmail(uniqueEmail);
        contact.setPhone(getDatalist("addContact",index, "phone"));
        contact.setStreet1(getDatalist("addContact",index, "street1"));
        contact.setStreet2(getDatalist("addContact",index, "street2"));
        contact.setCity(getDatalist("addContact",index, "city"));
        contact.setStateProvince(getDatalist("addContact",index, "stateProvince"));
        contact.setPostalCode(getDatalist("addContact",index, "postalCode"));
        contact.setCountry(getDatalist("addContact",index, "country"));

        return contact;
    }
}
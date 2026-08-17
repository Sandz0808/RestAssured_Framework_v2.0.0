package com.cheq.application.payloads.plaidpayload;

import com.cheq.application.models.plaidmodel.linktokenmodel.CreateLinkToken;
import com.cheq.application.models.plaidmodel.linktokenmodel.HostedLink;
import com.cheq.application.models.plaidmodel.linktokenmodel.LinkTokenUser;
import com.cheq.application.utilities.ConfigReader;

import java.util.List;

public class CreateLinkTokenPayload {

    public static CreateLinkToken createValidLinkToken() {

        LinkTokenUser user = new LinkTokenUser();
        user.setClientUserId("test-user-" + System.currentTimeMillis() );

        CreateLinkToken payload = new CreateLinkToken();
        payload.setClient_id(ConfigReader.get("plaid.client.id"));
        payload.setSecret(ConfigReader.get("plaid.client.secret") );
        payload.setClient_name( "Cheq API Automation");
        payload.setCountry_codes( List.of("US"));
        payload.setLanguage("en");
        payload.setUser(user);
        payload.setProducts(List.of("transactions"));
        payload.setAdditional_consented_products( List.of("auth"));

        return payload;
    }


    public static CreateLinkToken createHostedLinkToken() {

        // Reuse the existing valid Link Token payload
        CreateLinkToken payload = createValidLinkToken();

        // Add Hosted Link configuration
        HostedLink hostedLink = new HostedLink();

        hostedLink.setCompletion_redirect_uri("https://wonderwallet.com/redirect");

        hostedLink.setIs_mobile_app(false);

        payload.setHosted_link(hostedLink);

        return payload;
    }
}
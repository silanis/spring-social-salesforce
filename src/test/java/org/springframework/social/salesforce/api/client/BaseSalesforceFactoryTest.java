package org.springframework.social.salesforce.api.client;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.social.salesforce.api.Salesforce;
import org.springframework.social.salesforce.client.BaseSalesforceFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.RequestMatchers.content;
import static org.springframework.test.web.client.match.RequestMatchers.method;
import static org.springframework.test.web.client.match.RequestMatchers.requestTo;
import static org.springframework.test.web.client.response.ResponseCreators.withSuccess;


/**
 * @author Umut Utkan
 */
public class BaseSalesforceFactoryTest {

    @Test
    public void testClientLogin() {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo("https://login.salesforce.com/services/oauth2/token"))
                .andExpect(method(POST))
                .andExpect(content().string("grant_type=password&client_id=client-id&client_secret=client-secret&username=my-username&password=my-passwordsecurity-token"))
                .andRespond(withSuccess(new ClassPathResource("/client-token.json", getClass()), APPLICATION_JSON));

        Salesforce template = new BaseSalesforceFactory("client-id", "client-secret", restTemplate)
                .create("my-username", "my-password", "security-token");
        assertNotNull(template);
    }

}

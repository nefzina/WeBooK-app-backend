package com.wildcodeschool.webook.Auth.domain.service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.*;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import com.wildcodeschool.webook.Auth.domain.service.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    @Value("${ MJ_APIKEY_PUBLIC }")
    private String apiPublic;

    @Value("${ MJ_APIKEY_PRIVATE }")
    private String apiPrivate;

    @Override
    public void SendMail(String recipientEmail, String body) throws MailjetException {

        ClientOptions options = ClientOptions.builder()
                .apiKey(apiPublic)
                .apiSecretKey(apiPrivate)
                .build();

        MailjetClient client = new MailjetClient(options);

        TransactionalEmail message1 = TransactionalEmail
                .builder()
                .to(new SendContact(recipientEmail, "stanislav"))
                .from(new SendContact("amani-nefzi_student2023@wilder.school", "WeBooK"))
                .htmlPart("<h1>Mot de passe oublié</h1><p>"+body+"</p>")
                .subject("Mot de passe oublié")
                .trackOpens(TrackOpens.ENABLED)
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1) // you can add up to 50 messages per request
                .build();

        // act
        SendEmailsResponse response = request.sendWith(client);


    }
}

package ru.ustinov.mailsender.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ustinov.mailsender.request.RequestPayLoad;
import ru.ustinov.mailsender.request.User;
import ru.ustinov.mailsender.service.GroupResult;
import ru.ustinov.mailsender.service.MailServiseExecutor;
import ru.ustinov.mailsender.util.EmailBodyBilder;

import java.util.Set;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 15.12.2023
 */
@RestController
@RequestMapping(value = "/sendMails", consumes = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Email Controller")
public class EmailController {

    @Autowired
    private MailServiseExecutor mailServiseExecutor;

    @PostMapping
    public ResponseEntity<String> send(@RequestBody RequestPayLoad requestPayLoad) {
        final String bodyCommon = EmailBodyBilder.buildBody(requestPayLoad.getRestaurantTo());
        final Set<User> users = requestPayLoad.getUsers();
        final GroupResult votingApp = mailServiseExecutor.sendBulk(users, "Куда идем обедать", bodyCommon);
        System.out.println("Success result: " + votingApp.getSuccess());
        System.out.println("Failed: " + votingApp.getFailed().size());
        return ResponseEntity.ok().body("Begin to send emails");
    }

}

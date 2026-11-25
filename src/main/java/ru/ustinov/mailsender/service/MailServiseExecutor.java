package ru.ustinov.mailsender.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ustinov.mailsender.request.RestaurantTo;
import ru.ustinov.mailsender.request.User;
import ru.ustinov.mailsender.util.EmailBodyBilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 08.01.2024
 */
@Slf4j
@Service
public class MailServiseExecutor {

    @Autowired
    private MailSender mailSender;

    private static final String INTERRUPTED_BY_FAULTS_NUMBER = "+++ Interrupted by faults number";
    private static final String INTERRUPTED_BY_TIMEOUT = "+++ Interrupted by timeout";
    private static final String INTERRUPTED_EXCEPTION = "+++ InterruptedException";

    private static final ExecutorService mailExecutor = Executors.newFixedThreadPool(8);

    private final CompletionService<MailResult> completionService = new ExecutorCompletionService<>(mailExecutor);


    public GroupResult sendBulk(final Set<User> users, final String subject, String bodyCommon) {
        List<Future<MailResult>> futures = users.stream()
                .map(user -> completionService.submit(() -> mailSender.sendEmail(user.getEmail(), subject,
                        String.format("Добрый день %s!\n%s" ,user.getName(), bodyCommon))))
                .collect(Collectors.toList());

        return new Callable<GroupResult>() {
            private int success = 0;
            private final List<MailResult> failed = new ArrayList<>();

            @Override
            public GroupResult call() {
                while (!futures.isEmpty()) {
                    try {
                        Future<MailResult> future = completionService.poll(10, TimeUnit.SECONDS);
                        if (future == null) {
                            return cancelWithFail(INTERRUPTED_BY_TIMEOUT);
                        }
                        futures.remove(future);
                        MailResult mailResult = future.get();
                        if (mailResult.isOk()) {
                            success++;
                        } else {
                            failed.add(mailResult);
                            if (failed.size() >= 5) {
                                return cancelWithFail(INTERRUPTED_BY_FAULTS_NUMBER);
                            }
                        }
                    } catch (ExecutionException e) {
                        return cancelWithFail(e.getCause().toString());
                    } catch (InterruptedException e) {
                        return cancelWithFail(INTERRUPTED_EXCEPTION);
                    }
                }
                GroupResult groupResult = new GroupResult(success, failed, null);
                log.info("groupResult: {}", groupResult);
                return groupResult;
            }

            private GroupResult cancelWithFail(String cause) {
                futures.forEach(f -> f.cancel(true));
                return new GroupResult(success, failed, cause);
            }
        }.call();
    }

}

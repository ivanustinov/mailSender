package ru.ustinov.mailsender.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 08.01.2024
 */
@Data
@Getter
@AllArgsConstructor
public class User {

    private String name;

    private String email;
}

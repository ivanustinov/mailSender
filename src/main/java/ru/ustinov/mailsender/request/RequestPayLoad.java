package ru.ustinov.mailsender.request;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 15.12.2023
 */
@Data
public class RequestPayLoad {

    private RestaurantTo restaurantTo;

    private Set<User> users;
}

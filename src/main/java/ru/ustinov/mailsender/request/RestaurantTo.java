package ru.ustinov.mailsender.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 08.01.2024
 */
@Data
@AllArgsConstructor
public class RestaurantTo {

    private String name;

    private List<Dish> dishes;

    private int countVotes;

}

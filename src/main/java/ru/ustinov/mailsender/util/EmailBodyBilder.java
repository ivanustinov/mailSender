package ru.ustinov.mailsender.util;

import ru.ustinov.mailsender.request.Dish;
import ru.ustinov.mailsender.request.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 08.01.2024
 */
public class EmailBodyBilder {

    public static String buildBody(RestaurantTo restaurantTo) {
        final String restaurant = restaurantTo.getName();
        final int countVotes = restaurantTo.getCountVotes();
        final List<Dish> dishes = restaurantTo.getDishes();
        String menu = dishes.stream().map(dish -> dish.getName() + " - " + dish.getPrice() + " руб.")
                .collect(Collectors.joining("\n"));
        String result = """
            
            В сегодняшнем голосовании победил ресторан %s с меню:
            %s
            
            За него проголосовало %d сотрудников.
            """;
        return String.format(result, restaurant, menu, countVotes);
    }
}

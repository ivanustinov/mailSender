package ru.ustinov.mailsender.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 08.01.2024
 */
@Data
@AllArgsConstructor
public class Dish {

    private String name;

    private BigDecimal price;

}

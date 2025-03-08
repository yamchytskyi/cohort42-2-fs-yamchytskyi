package de.ait.homeworks.homework_08.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Movie {


    private final int id;

    private String title;

    private String genre;

    private int year;

}

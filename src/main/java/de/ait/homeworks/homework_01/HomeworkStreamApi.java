package de.ait.homeworks.homework_01;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HomeworkStreamApi {
    private static List<String> countries = Arrays.asList("Germany", "France", "Brazil", "Argentina", "Canada", "China", "Australia", "India");
    private static List<String> cities = Arrays.asList("Berlin", "Buenos Aires", "Paris", "Los Angeles", "New York", "London", "Beijing");
    private static List<String> rivers = Arrays.asList("Amazon", "Nile", "Yangtze", "Mississippi", "Danube", "Main", "Ganges");
    private static List<String> continents = Arrays.asList("Europe", "Asia", "Africa", "Australia", "Antarctica", "South America", "North America");
    private static List<String> countriesTwo = Arrays.asList("Mexico", "Sweden", "Brazil", "USA", "Canada", "France", "Norway");

    public static void main(String[] args) {
        filteringCountriesByFirstLetter();
        filteringCitiesByNameLength();
        filteringRiversByEvenLetterCount();
        filteringContinentsByNameLength();
        filteringCountriesWithSixLetterNames();
        findingCountriesContainingTheLetterA();
        filteringCitiesByLastLetter();
        findingRiversWithMoreThatSevenLetters();
        filteringContinentsByFirsLetter();
    }

    public static void filteringCountriesByFirstLetter() {
//        1. Filtering Countries by First Letter
//        You have a list of countries:
//        Using Stream API, filter the countries whose names start with the letter "C".
        List<String> result = countries.stream().filter(country -> country.charAt(0) == 'C').collect(Collectors.toList());
        System.out.println("Task 1: " + result);
    }

    public static void filteringCitiesByNameLength() {
//        You have a list of cities:
//        Using Stream API, filter the cities whose names are longer than 6 characters.
        List<String> result = cities.stream().filter(city -> city.replace(" ", "").length() > 6).collect(Collectors.toList());
        System.out.println("Task 2: " + result);
    }

    public static void filteringRiversByEvenLetterCount() {
//        Given a list of rivers:
//        Using Stream API, filter only the rivers whose names have an even number of letters.
        List<String> result = rivers.stream().filter(river -> river.replace(" ", "").length() % 2 == 0).collect(Collectors.toList());
        System.out.println("Task 3: " + result);
    }

    public static void filteringContinentsByNameLength() {
//        Given a list of continents:
//        Using Stream API, filter the continents whose names are shorter than 7 characters.
        List<String> result = continents.stream().filter(continent -> continent.replace(" ", "").length() < 7).collect(Collectors.toList());
        System.out.println("Task 4: " + result);
    }

    public static void filteringCountriesWithSixLetterNames() {
//        Given a list of countries:
//        Using Stream API, filter the countries whose names consist of 6 letters.
        List<String> result = countriesTwo.stream().filter(country -> country.replace(" ", "").length() == 6).collect(Collectors.toList());
        System.out.println("Task 5:" + result);
    }

    public static void findingCountriesContainingTheLetterA() {
//        Using Stream API, filter the countries whose names contain the letter "a".
        List<String> result = countries.stream().filter(country -> country.toLowerCase().contains("a")).collect(Collectors.toList());
        System.out.println("Task 6:" + result);
    }

    public static void filteringCitiesByLastLetter() {
//        Using Stream API, filter the cities whose names end with "o".
        List<String> result = cities.stream().filter(city -> city.toLowerCase().endsWith("o")).collect(Collectors.toList());
        System.out.println("Task 7:" + result);
    }

    public static void findingRiversWithMoreThatSevenLetters() {
//        Using Stream API, filter the rivers whose names contain more than 7 letters.
        List<String> result = rivers.stream().filter(river -> river.replace(" ", "").length() > 7).collect(Collectors.toList());
        System.out.println("Task 8:" + result);
    }

    public static void filteringContinentsByFirsLetter() {
//        Using Stream API, filter the continents whose names start with "A".
        List<String> result = continents.stream().filter(continent -> continent.toLowerCase().startsWith("a")).collect(Collectors.toList());
        System.out.println("Task 9:" + result);
    }
}

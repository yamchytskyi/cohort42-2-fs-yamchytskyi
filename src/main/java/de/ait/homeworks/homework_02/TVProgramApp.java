package de.ait.homeworks.homework_02;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class TVProgramApp {
    public static void main(String[] args) {
    }

//Найдите все передачи, рейтинг которых выше заданного порога (например, > 8.0).
    public static List<TVProgram> findProgramsAboveRating(List<TVProgram> tvPrograms, double rating) {
        return tvPrograms.stream().filter(tvProgram -> tvProgram.getRating() > rating).collect(Collectors.toList());
    }

    //Преобразуйте объекты TVProgram в удобные для вывода строки. Например, сформируйте строку в формате:

    public static List<String> getTVProgramsStrings(List<TVProgram> tvPrograms) {
        return  tvPrograms.stream().map(TVProgram::toString).collect(Collectors.toList());
    }

    public static boolean isLive(List<TVProgram> tvPrograms) {
        return tvPrograms.stream().anyMatch(tvProgram -> tvProgram.isLive());
    }

    //Определите, какая из передач самая длительная (максимальное значение поля duration).
    public static TVProgram getTheLongestTVProgram(List<TVProgram> tvPrograms) {
        Optional<TVProgram> longestTVProgram = tvPrograms.stream().max(Comparator.comparingInt(TVProgram::getDuration));
        return longestTVProgram.orElse(null);
    }

    //Используйте mapToDouble и average для вычисления среднего рейтинга всех передач.
    public static double getAverageTVProgramDuration(List<TVProgram> tvPrograms) {
        return tvPrograms.stream().mapToDouble(TVProgram::getRating).average().orElse(0.0);
    }

    //Группировка по каналу
    public static Map<String, List<TVProgram>> getGroupsByChannel(List<TVProgram> tvPrograms) {
        return tvPrograms.stream().collect(Collectors.groupingBy(TVProgram::getChannel));
    }

    // Возвращает топ-3 передачи, отсортированные по рейтингу в убывающем порядке.
    public static List<TVProgram> getTop3TVProgramsByDescendingRating(List<TVProgram> tvPrograms) {
        List<TVProgram> top3Programs = tvPrograms.stream()
                .sorted(Comparator.comparingDouble(TVProgram::getRating).reversed())
                .limit(3)
                .collect(Collectors.toList());
        return top3Programs;
    }

}

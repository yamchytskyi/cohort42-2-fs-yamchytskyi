package de.ait.consultations.streamapi;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class YouTubeAnalyzer {
    // Список видео для анализа / List of videos for analysis
    private static List<YoutubeVideo> videos = List.of(
            new YoutubeVideo("Как научиться программировать", "IT Channel", 1500000, 12000, 720, "Образование", true),
            new YoutubeVideo("Лучшие моменты матча", "Sports Channel", 500000, 8000, 600, "Спорт", false),
            new YoutubeVideo("Новый трек 2025", "Music Channel", 3000000, 25000, 240, "Музыка", true),
            new YoutubeVideo("Обзор новой игры", "Gaming Channel", 2000000, 15000, 900, "Игры", true),
            new YoutubeVideo("Как приготовить пиццу", "Cooking Channel", 800000, 10000, 1200, "Кулинария", false),
            new YoutubeVideo("Как приготовить кофе", "Cooking Channel", 100_000, 70000, 1400, "Кулинария", true)
    );

    public static void main(String[] args) {
        getTop3MostLikelyVideos().forEach(System.out::println);
    }

    public static List<YoutubeVideo> getVideosMore1MViews() {
        List<YoutubeVideo> videosMore1MViews = videos.stream()
                .filter(v -> v.getViews() > 1_000_000)
                .collect(Collectors.toList());
        return videosMore1MViews;
    }

    public static List<YoutubeVideo> getVideosMore10Minutes() {
        int minutes = 60;
        List<YoutubeVideo> videosMore10Minutes = videos.stream()
                .filter(video -> video.getDuration() > 10 * minutes)
                .collect(Collectors.toList());

        return videosMore10Minutes;
    }

    public static List<YoutubeVideo> getVideosOfCategory(String category) {
        List<YoutubeVideo> videosOfTheCategory = videos.stream()
                .filter(video -> video.getCategory()
                        .equals(category.toLowerCase()))
                .collect(Collectors.toList());
        return videosOfTheCategory;
    }
//todo make a method, that returns list of videos with uniques categories
    public static List<String> getUniqueCategories() {
        List<String> uniqueCategories = videos.stream()
                .map(YoutubeVideo::getCategory)
                .distinct()
                .collect(Collectors.toList());

        return uniqueCategories;
    }

    public static List<String> getTitlesUpperCase() {
        List<String> titlesUpperCase = videos.stream()
                .map(YoutubeVideo::getTitle)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        return titlesUpperCase;
    }

    public static List<YoutubeVideo> getTop3MostLikelyVideos() {
        List<YoutubeVideo> top3MostLikelyVideos = videos.stream()
                .sorted(Comparator.comparingInt(YoutubeVideo::getLikes).reversed())
                .limit(3).collect(Collectors.toList());

        return top3MostLikelyVideos;
    }

    public static boolean isMore1M() {
        boolean isThereMore10MViews = videos.stream()
                .anyMatch(video -> video.getViews() > 1_000_000);
        return isThereMore10MViews;
    }
}

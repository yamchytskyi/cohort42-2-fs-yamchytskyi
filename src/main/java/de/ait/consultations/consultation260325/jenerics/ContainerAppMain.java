package de.ait.consultations.consultation260325.jenerics;

import de.ait.homeworks.homework_02.TVProgram;

public class ContainerAppMain {

    public static void main(String[] args) {
        ContainerApp<String> stringContainerApp = new ContainerApp<>();

        stringContainerApp.add("Hello");
        stringContainerApp.add("World");
        System.out.println(stringContainerApp.get(0));
        System.out.println(stringContainerApp.get(1));

        ContainerApp<Integer> integerContainerApp = new ContainerApp<>();
        integerContainerApp.add(10);
        integerContainerApp.add(20);
        System.out.println(integerContainerApp.get(0));
        System.out.println(integerContainerApp.get(1));

        ContainerApp<TVProgram> tvProgramContainerApp = new ContainerApp<>();
        tvProgramContainerApp.add(new TVProgram("TV-5", "Sport News", 100, true, 4));
        tvProgramContainerApp.add(new TVProgram("TV-5", "Sport News", 100, true, 4));
        System.out.println(tvProgramContainerApp.get(0));
        System.out.println(tvProgramContainerApp.get(1));

    }
}

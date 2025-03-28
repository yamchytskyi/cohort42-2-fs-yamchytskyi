package de.ait.consultations.consultation260325.jenerics;

public class MainBoxApp {

    public static void main(String[] args) {
        Box<String> stringBox = new Box<>("Hello");
        System.out.println(stringBox.getValue());
        stringBox.setValue("World");
        System.out.println(stringBox.getValue());

        Box<Integer> intBox = new Box<>(10);
        System.out.println(intBox.getValue());
        intBox.setValue(20);
        System.out.println(intBox.getValue());
    }
}

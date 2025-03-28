package de.ait.consultations.consultation260325.jenerics;

public interface Container<T> {
    void add(T element);
    T get(int index);
}

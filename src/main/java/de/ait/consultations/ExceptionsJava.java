package de.ait.consultations;

import de.ait.consultations.consultation260325.exceptions.MyCustomException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionsJava {

    public static void main(String[] args) {
        try {
            divide(10, 0);
        } catch (MyCustomException exception) {
            log.error("MyCustomException", exception.getMessage());
        }
    }

    public static int divide(int a , int b) throws MyCustomException {
        return a / b;
    }
}

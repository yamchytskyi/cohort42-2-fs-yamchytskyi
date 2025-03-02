package de.ait.homeworks.homework_07;

//import lombok.extern.slf4j.Slf4j;
// todo - what an annotation
//@Slf4j
public class MathStringUtil {
    public int add(int numberOne, int numberTwo) {
        return numberOne + numberTwo;
    }

    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    public int divide(int numberOne, int numberTwo) {
        if(numberTwo == 0) {
            System.out.println("Division by zero");
//            log.error("Division by zero. NumberOne: {}, NumberTwo: {}", numberOne, numberTwo);
            return -100;
        }
        return  numberOne / numberTwo;
    }

    public int getLength(String str) {
        if(str == null) {
            return -1;
        }
        return str.length();
    }

    public boolean containsWord(String text, String word) {
        if(text == null || word == null ) {
            System.out.println("Text or word is null");
            return false;
        }
        if(text.isEmpty() || word.isEmpty()) {
            System.out.println("Text or word is empty");
            return false;
        }
        return text.contains(word);
    }
}

package org.example;

// Основной класс для работы с массивом
public class ArrayPI {
    // Одномерный массив с размером 35
    private int[] array = new int[5];

    // Метод для ввода массива с клавиатуры
    public void inputArray() {
        System.out.println("Введите 5 целых чисел (нажмите Enter после каждого):");
        for (int i = 0; i < 5; i++) {
            array[i] = new java.util.Scanner(System.in).nextInt(); // Ввод числа
        }
    }

    // Метод для получения массива
    public int[] getArray() {
        return array;
    }
}


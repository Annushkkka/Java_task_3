package org.example;

import java.util.Scanner;

public class ArrayPI {
    // Одномерный массив с размером 5
    private int[] array = new int[5];

    // Метод для ввода массива с клавиатуры
    public void inputArray() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите 5 целых чисел, разделенных пробелами или нажимая Enter после каждого (например: 1 2 3 4 5):");

        // Ввод через пробел
        String input = scanner.nextLine(); // Считываем всю строку
        String[] numbers = input.split(" "); // Разбиваем строку по пробелам

        int count = 0;

        // Преобразуем строковые значения в числа и сохраняем их в массив
        for (String number : numbers) {
            if (count < 5) {
                array[count++] = Integer.parseInt(number);
            }
        }

        // Если пользователь не ввел все 5 чисел, можно продолжить ввод построчно
        while (count < 5) {
            System.out.println("Введите число " + (count + 1) + ":");
            array[count++] = scanner.nextInt(); // Ввод числа
        }
    }

    // Метод для получения массива
    public int[] getArray() {
        return array;
    }
}

package org.example;

import java.util.Scanner;

public class ArrayPI {
    // Одномерный массив с размером 5
    private int[] array = new int[35];

    // Метод для ввода массива с клавиатуры
    public void inputArray() {
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput;

        do {
            System.out.println("Введите 35 целых чисел, разделенных пробелами или нажимая Enter после каждого");
            System.out.println("(например: 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35):");


            // Ввод через пробел
            String input = scanner.nextLine(); // Считываем всю строку
            String[] numbers = input.split(" "); // Разбиваем строку по пробелам

            isValidInput = true; // Предполагаем, что ввод корректный
            int count = 0;

            // Преобразуем строковые значения в числа и сохраняем их в массив
            for (String number : numbers) {
                try {
                    if (count < 35) {
                        array[count++] = Integer.parseInt(number);
                    }
                } catch (NumberFormatException e) {
                    isValidInput = false; // Ввод некорректный
                    break;
                }
            }

            // Если пользователь не ввел все 5 чисел, можно продолжить ввод построчно
            while (count < 35 && isValidInput) {
                System.out.println("Введите число " + (count + 1) + ":");
                try {
                    array[count++] = scanner.nextInt(); // Ввод числа
                } catch (NumberFormatException e) {
                    isValidInput = false; // Ввод некорректный
                    break;
                }
            }

            // Проверка на корректность ввода
            if (!isValidInput) {
                System.out.println("Массив должен начинаться с цифры и состоять только из целых чисел и пробелов.");
            }

        } while (!isValidInput); // Запрашиваем ввод до тех пор, пока он не будет корректным
    }

    // Метод для получения массива
    public int[] getArray() {
        return array;
    }
}

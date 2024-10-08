package org.example;

import java.util.Scanner;

public class ArrayPI {
    // Одномерный массив с размером 5
    private int[] array = new int[5];

    // Метод для ввода массива с клавиатуры
    public void inputArray() {
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput;

        do {
            System.out.println("Введите 5 целых чисел, разделенных пробелами или нажимая Enter после каждого (например: 1 2 3 4 5):");

            // Ввод через пробел
            String input = scanner.nextLine(); // Считываем всю строку
            String[] numbers = input.split(" "); // Разбиваем строку по пробелам

            isValidInput = true; // Предполагаем, что ввод корректный
            int count = 0;

            // Преобразуем строковые значения в числа и сохраняем их в массив
            for (String number : numbers) {
                try {
                    if (count < 5) {
                        array[count++] = Integer.parseInt(number);
                    }
                } catch (NumberFormatException e) {
                    isValidInput = false; // Ввод некорректный
                    break;
                }
            }

            // Если пользователь не ввел все 5 чисел, можно продолжить ввод построчно
            while (count < 5 && isValidInput) {
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

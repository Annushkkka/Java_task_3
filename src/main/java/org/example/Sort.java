package org.example;

// Класс для сортировки массива
public final class Sort extends ArrayPI {
    // Метод пузырьковой сортировки по возрастанию
    public int[] bubbleSortAscending(int[] array) {
        int n = array.length;
        // Создаем копию массива для сортировки
        int[] sortedArray = array.clone();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sortedArray[j] > sortedArray[j + 1]) { // Если элемент больше следующего
                    // Обмен местами
                    int temp = sortedArray[j];
                    sortedArray[j] = sortedArray[j + 1];
                    sortedArray[j + 1] = temp;
                }
            }
        }
        return sortedArray; // Возвращаем отсортированный массив
    }

    // Метод пузырьковой сортировки по убыванию
    public int[] bubbleSortDescending(int[] array) {
        int n = array.length;
        // Создаем копию массива для сортировки
        int[] sortedArray = array.clone();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sortedArray[j] < sortedArray[j + 1]) { // Если элемент меньше следующего
                    // Обмен местами
                    int temp = sortedArray[j];
                    sortedArray[j] = sortedArray[j + 1];
                    sortedArray[j + 1] = temp;
                }
            }
        }
        return sortedArray; // Возвращаем отсортированный массив
    }
}

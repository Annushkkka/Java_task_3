package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    // Метод для отображения меню и обработки выбора пользователя
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        DatabaseOperations dbOps = new DatabaseOperations(); // Создаем объект для работы с БД
        ArrayPI arrayPI = new ArrayPI(); // Создаем объект для работы с массивом
        int choice;

        do {
            System.out.println("Выберите действие:");
            System.out.println("1. Вывести все таблицы");
            System.out.println("2. Создать таблицу");
            System.out.println("3. Ввести массив и сохранить в БД, затем вывести массив");
            System.out.println("4. Отсортировать массив и сохранить в БД, затем вывести массив");
            System.out.println("5. Сохранить данные из БД в Excel");
            System.out.println("6. Выход из программы");
            System.out.print("Введите число от 1 до 6: "); // Просим ввести число в этой же строке
            choice = scanner.nextInt(); // Считываем выбор пользователя

            switch (choice) {
                case 1:
                    dbOps.printAllTables(); // Вызов метода для вывода таблиц
                    break;
                case 2:
                    String tableName;
                    do {
                        System.out.println("Введите имя таблицы для создания:");
                        tableName = scanner.next(); // Считываем имя таблицы
                    } while (!dbOps.isValidTableName(tableName)); // Проверяем корректность имени таблицы

                    dbOps.createTable(tableName); // Вызов метода для создания таблицы
                    break;
                case 3:
                    String saveTableName;
                    do {
                        System.out.println("Введите имя таблицы для сохранения массива:");
                        saveTableName = scanner.next();
                        if (!dbOps.checkTableExists(saveTableName)) {
                            System.out.println("Ошибка: Таблица не существует. Пожалуйста, попробуйте снова.");
                        }
                    } while (!dbOps.checkTableExists(saveTableName)); // Проверяем, существует ли таблица

                    arrayPI.inputArray(); // Ввод массива
                    dbOps.saveArrayToDatabase(arrayPI.getArray(), saveTableName); // Сохраняем массив в БД
                    dbOps.printAllRecords(saveTableName); // Выводим записи из таблицы
                    break;
                case 4:
                    String sortTableName;
                    do {
                        System.out.println("Введите имя таблицы для сортировки массива:");
                        sortTableName = scanner.next();
                        if (!dbOps.checkTableExists(sortTableName)) {
                            System.out.println("Ошибка: Таблица не существует. Пожалуйста, попробуйте снова.");
                        }
                    } while (!dbOps.checkTableExists(sortTableName)); // Проверяем, существует ли таблица

                    System.out.println("Введите ID записи для сортировки:");
                    int id = scanner.nextInt(); // Считываем ID
                    dbOps.sortAndUpdateArray(id, sortTableName); // Сортируем и обновляем массив
                    dbOps.printAllRecords(sortTableName); // Выводим записи из таблицы
                    break;
                case 5:
                    String exportTableName;
                    do {
                        System.out.println("Введите название таблицы для экспорта:");
                        exportTableName = scanner.next(); // Считываем название таблицы
                        if (!dbOps.checkTableExists(exportTableName)) {
                            System.out.println("Ошибка: таблица не существует. Пожалуйста, попробуйте снова.");
                        }
                    } while (!dbOps.checkTableExists(exportTableName)); // Повторяем, пока таблица не будет найдена

                    ExcelExporter excelExporter = new ExcelExporter(); // Создаем объект ExcelExporter
                    excelExporter.exportToExcel(exportTableName); // Вызываем метод для экспорта данных в Excel
                    dbOps.printAllRecords(exportTableName); // Выводим записи из таблицы
                    break;

                case 6:
                    System.out.println("Выход из программы...");
                    break;
                default:
                    System.out.println("Такого действия не существует, пожалуйста, введите число от 1 до 6."); // Сообщение об ошибке
            }
        } while (choice != 6); // Зацикливаем меню до выхода
    }
}

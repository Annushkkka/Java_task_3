package org.example;

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
            choice = scanner.nextInt(); // Считываем выбор пользователя

            switch (choice) {
                case 1:
                    dbOps.printAllTables(); // Вызов метода для вывода таблиц
                    break;
                case 2:
                    System.out.println("Введите имя таблицы для создания:");
                    String tableName = scanner.next(); // Считываем имя таблицы
                    dbOps.isValidTableName(tableName);
                    dbOps.createTable(tableName); // Вызов метода для создания таблицы
                    break;
                case 3:
                    System.out.println("Введите имя таблицы для сохранения массива:");
                    String saveTableName = scanner.next();
                    dbOps.checkTableExists(saveTableName); // Проверяем, существует ли таблица
                    arrayPI.inputArray(); // Ввод массива
                    dbOps.saveArrayToDatabase(arrayPI.getArray(), saveTableName); // Сохраняем массив в БД
                    dbOps.printAllRecords(saveTableName); // Выводим записи из таблицы
                    break;
                case 4:
                    System.out.println("Введите имя таблицы для сортировки массива:");
                    String sortTableName = scanner.next();
                    dbOps.checkTableExists(sortTableName); // Проверяем, существует ли таблица
                    System.out.println("Введите ID записи для сортировки:");
                    int id = scanner.nextInt(); // Считываем ID
                    dbOps.sortAndUpdateArray(id, sortTableName); // Сортируем и обновляем массив
                    dbOps.printAllRecords(sortTableName); // Выводим записи из таблицы
                    break;
                case 5:
                    System.out.println("Введите название таблицы для экспорта:");
                    String exportTableName = scanner.next(); // Считываем название таблицы
                    dbOps.checkTableExists(exportTableName); // Проверяем, существует ли таблица
                    ExcelExporter excelExporter = new ExcelExporter(); // Создаем объект ExcelExporter
                    excelExporter.exportToExcel(exportTableName); // Вызываем метод для экспорта данных в Excel
                    break;
                case 6:
                    System.out.println("Выход из программы...");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        } while (choice != 6); // Зацикливаем меню до выхода
    }
}

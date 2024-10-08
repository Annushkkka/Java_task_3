package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Класс для операций с базой данных
public class DatabaseOperations {
    // Метод для получения соединения с базой данных
    private Connection connection;

    // Конструктор класса, устанавливающий соединение с базой данных
    public DatabaseOperations() {
        try {
            connection = DatabaseConnection.getConnection(); // Получаем соединение
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
    }

    // Метод для вывода всех таблиц в базе данных
    public void printAllTables() {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='public'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Таблицы в базе данных:");
            while (rs.next()) {
                System.out.println(rs.getString("table_name")); // Выводим имя таблицы
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении таблиц: " + e.getMessage());
        }
    }

    // Метод для создания таблицы в базе данных
    public void createTable(String tableName) {
        String sql = "CREATE TABLE " + tableName + " ("
                + "id SERIAL PRIMARY KEY, "
                + "original_array VARCHAR, "
                + "array_asc VARCHAR, "
                + "array_desc VARCHAR)";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql); // Выполняем SQL-запрос на создание таблицы
            System.out.println("Таблица '" + tableName + "' успешно создана.");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    // Метод для сохранения массива в базу данных
    public void saveArrayToDatabase(int[] array, String tableName) {
        StringBuilder arrayString = new StringBuilder(); // Строка для хранения массива
        for (int i = 0; i < array.length; i++) {
            arrayString.append(array[i]); // Добавляем элемент массива
            if (i < array.length - 1) {
                arrayString.append(","); // Добавляем запятую между элементами
            }
        }

        String sql = "INSERT INTO " + tableName + " (original_array, array_asc, array_desc) VALUES (?, NULL, NULL)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, arrayString.toString()); // Устанавливаем значение для оригинального массива
            pstmt.executeUpdate(); // Выполняем вставку
            System.out.println("Массив успешно сохранен в базе данных.");
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении массива: " + e.getMessage());
        }
    }

    // Метод для получения всех записей из таблицы
    public void printAllRecords(String tableName) {
        String sql = "SELECT * FROM " + tableName + " ORDER BY id ASC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Записи из таблицы '" + tableName + "':");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", "
                        + "Original Array: " + rs.getString("original_array") + ", "
                        + "Sorted Ascending: " + rs.getString("array_asc") + ", "
                        + "Sorted Descending: " + rs.getString("array_desc"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении записей: " + e.getMessage());
        }
    }

    // Метод для сортировки массива и обновления в базе данных
    public void sortAndUpdateArray(int id, String tableName) {
        String sqlSelect = "SELECT original_array, array_asc, array_desc FROM " + tableName + " WHERE id = " + id;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)) {

            if (rs.next()) {
                // Проверка, отсортирован ли уже массив
                String arrayAsc = rs.getString("array_asc");
                String arrayDesc = rs.getString("array_desc");

                if (arrayAsc != null && !arrayAsc.isEmpty() && arrayDesc != null && !arrayDesc.isEmpty()) {
                    System.out.println("Этот массив уже отсортирован.");
                    return; // Завершаем действие, если массив уже отсортирован
                }

                // Получение и преобразование оригинального массива
                String[] stringArray = rs.getString("original_array").split(",");
                int[] intArray = new int[stringArray.length];
                for (int i = 0; i < stringArray.length; i++) {
                    intArray[i] = Integer.parseInt(stringArray[i].trim());
                }

                // Сортировка массива
                Sort sorter = new Sort();
                int[] sortedAsc = sorter.bubbleSortAscending(intArray);
                int[] sortedDesc = sorter.bubbleSortDescending(intArray);

                // Обновление массива в базе данных
                String sqlUpdate = "UPDATE " + tableName + " SET array_asc = '" + arrayToString(sortedAsc)
                        + "', array_desc = '" + arrayToString(sortedDesc)
                        + "' WHERE id = " + id;
                stmt.executeUpdate(sqlUpdate);
                System.out.println("Массив успешно отсортирован и обновлен в базе данных.");
            } else {
                System.out.println("Запись с таким ID не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при сортировке и обновлении массива: " + e.getMessage());
        }
    }



    // Вспомогательный метод для преобразования массива в строку
    public String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) { // Добавляем запятую только если это не последний элемент
                sb.append(",");
            }
        }
        return sb.toString(); // Возвращаем строку без лишней запятой
    }

    // Метод для проверки существования таблицы в базе данных
    public boolean checkTableExists(String tableName) {
        String sql = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ? AND table_schema = 'public')";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, tableName); // Устанавливаем параметр
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1); // Возвращаем true, если таблица существует
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при проверке существования таблицы: " + e.getMessage());
        }
        return false; // Возвращаем false, если произошла ошибка или таблица не найдена
    }


    // Метод для проверки корректности имени таблицы
    public boolean isValidTableName(String tableName) {
        // Проверка длины имени
        if (tableName.length() < 1 || tableName.length() > 63) {
            System.out.println("Имя таблицы должно быть от 1 до 63 символов.");
            return false;
        }

        // Проверка на соответствие формату (латиница и кириллица)
        if (!tableName.matches("^[a-zA-Zа-яА-ЯёЁ_][a-zA-Zа-яА-ЯёЁ0-9_]*$")) {
            System.out.println("Имя таблицы должно начинаться с буквы (латиницы или кириллицы) и содержать только буквы, цифры и символы подчеркивания.");
            return false;
        }

        return true; // Имя таблицы корректно
    }

}


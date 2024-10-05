package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

    // Метод для экспорта данных в Excel
    public void exportToExcel(String tableName) {
        // Создаем новую книгу Excel
        Workbook workbook = new XSSFWorkbook();
        // Создаем лист в книге
        Sheet sheet = workbook.createSheet("Data");

        // Создаем заголовки колонок
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Original Array");
        headerRow.createCell(2).setCellValue("Array Ascending");
        headerRow.createCell(3).setCellValue("Array Descending");

        // Подключаемся к базе данных и получаем данные
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {

            int rowCount = 1; // Номер строки для записи данных
            while (resultSet.next()) {
                // Создаем новую строку для каждой записи
                Row row = sheet.createRow(rowCount++);
                // Заполняем ячейки значениями из базы данных
                row.createCell(0).setCellValue(resultSet.getInt("id"));
                row.createCell(1).setCellValue(resultSet.getString("original_array"));
                row.createCell(2).setCellValue(resultSet.getString("array_asc"));
                row.createCell(3).setCellValue(resultSet.getString("array_desc"));
            }

            // Сохраняем книгу в файл
            try (FileOutputStream fileOut = new FileOutputStream("data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Данные успешно экспортированы в Excel файл data.xlsx");
            } catch (IOException e) {
                System.out.println("Ошибка при сохранении Excel файла: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при извлечении данных из базы: " + e.getMessage());
        } finally {
            // Закрываем книгу
            try {
                workbook.close();
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии книги: " + e.getMessage());
            }
        }
    }
}


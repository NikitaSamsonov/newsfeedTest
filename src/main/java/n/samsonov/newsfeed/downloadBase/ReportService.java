package n.samsonov.newsfeed.downloadBase;


import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import n.samsonov.newsfeed.entity.LogsEntity;
import n.samsonov.newsfeed.repository.LogsRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final Path directory = Paths.get("uploads").toAbsolutePath();
    private final LogsRepository logsRepository;

    private static void createList(LogsEntity logs, Row row) {

        var date = logs.getCreatedAt()
                .format(DateTimeFormatter
                        .ofPattern("EEEE, MMMM dd, yyyy hh:mm:ss a"));
        Cell cell = row.createCell(0);
        cell.setCellValue(logs.getId().toString());
        cell = row.createCell(1);
        cell.setCellValue(date);
        cell = row.createCell(2);
        cell.setCellValue(logs.getMethod());
        cell = row.createCell(3);
        cell.setCellValue(logs.getStatusCode());
    }

    public ByteArrayResource getXls() {

        var bdList = logsRepository.findAll();
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Users");
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 6000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("createdAt");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("method");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("statusCode");
        headerCell.setCellStyle(headerStyle);

        try {
            int rownum = 1;
            for (LogsEntity log : bdList) {
                Row row = sheet.createRow(rownum++);
                createList(log, row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = bos.toByteArray();
        return new ByteArrayResource(bytes);
    }

    public ByteArrayResource getXlsx() {

        var bdList = logsRepository.findAll();
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Users");
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 8000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 6000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("createdAt");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("method");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("statusCode");
        headerCell.setCellStyle(headerStyle);
        try {
            int rownum = 1;
            for (LogsEntity log : bdList) {

                Row row = sheet.createRow(rownum++);
                createList(log, row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = bos.toByteArray();
        return new ByteArrayResource(bytes);

    }

    public File getCsv() throws IOException {
        File csvOutputFile = new File("efef");
        FileWriter outPutFile = new FileWriter(csvOutputFile);

        CSVWriter writer = new CSVWriter(outPutFile);
        String[] header = {"id", "createdAt", "method", "statusCode"};
        writer.writeNext(header);
        int rownum = 1;
        var bdList = logsRepository.findAll();
        for (LogsEntity log : bdList) {
            writer.writeNext(new String[]{log.toString()});
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        return
    }
}


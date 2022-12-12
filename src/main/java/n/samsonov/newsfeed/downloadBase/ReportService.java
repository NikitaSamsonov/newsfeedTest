package n.samsonov.newsfeed.downloadBase;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

import n.samsonov.newsfeed.entity.LogsEntity;
import n.samsonov.newsfeed.repository.LogsRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

    public StreamResult getXml() throws ParserConfigurationException, TransformerException {

        final String xmlFilePath = "/Users/dunice/Downloads/newsfeedTest/xmlfile.xml";

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        var bdList = logsRepository.findAll();

        Element root = document.createElement("Logs_List");
        document.appendChild(root);

        for (LogsEntity log : bdList) {

            Element logEl = document.createElement("Log");
            root.appendChild(logEl);
            Attr attr = document.createAttribute("id");
            attr.setValue(String.valueOf(log.getId()));

            var date = log.getCreatedAt()
                    .format(DateTimeFormatter
                            .ofPattern("EEEE, MMMM dd, yyyy hh:mm:ss a"));


            Element createdAt = document.createElement("createdAt");
            createdAt.appendChild(document.createTextNode(date));
            logEl.appendChild(createdAt);

            Element method = document.createElement("method");
            method.appendChild(document.createTextNode(log.getMethod()));
            logEl.appendChild(method);

            Element statusCode = document.createElement("statusCode");
            statusCode.appendChild(document.createTextNode(String.valueOf(log.getStatusCode())));
            logEl.appendChild(statusCode);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);


           StreamResult streamResult = new StreamResult(new File(xmlFilePath));
           transformer.transform(domSource, streamResult);

            return null;
        }


    public  ByteArrayInputStream logsReport(List<LogsEntity> logs) throws DocumentException {

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();


        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(80);
        table.setWidths(new int[]{1, 4, 2, 3});

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        PdfPCell hcell;
        hcell = new PdfPCell(new Phrase("Id", headFont));
        hcell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("createdAt", headFont));
        hcell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("method",  headFont));
        hcell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("statusCode", headFont));
        hcell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        table.addCell(hcell);

        for (LogsEntity log : logs) {
            var date = log.getCreatedAt()
                    .format(DateTimeFormatter
                            .ofPattern("EEEE, MMMM dd, yyyy hh:mm:ss a"));


            table.addCell(String.valueOf(log.getId()));
            table.addCell(date);
            table.addCell(log.getMethod());
            table.addCell(String.valueOf(log.getStatusCode()));
        }

        PdfWriter.getInstance(document, out);
        document.open();
        document.add(table);

        document.close();

        return new ByteArrayInputStream(out.toByteArray());

    }


    public  List<String> getTxt() throws IOException {

        var bdList = logsRepository.findAll().stream().map((l) -> l.toString()).toList();
        return bdList;
//
//        FileWriter myWriter = new FileWriter("txtReport.txt");
//        for(String log : bdList) {
//            myWriter.write(log + "/n");
//        }
//
//        myWriter.close();
//    }
    }
}


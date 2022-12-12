package n.samsonov.newsfeed.downloadBase;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.entity.LogsEntity;
import n.samsonov.newsfeed.services.LogService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UploadController {

    private final ReportService reportService;
    private final LogService logService;


    @GetMapping("/xls")
    public ResponseEntity getXls(){
        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename("bd.xls")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(reportService.getXls());
    }

    @GetMapping("/xlsx")
    public ResponseEntity getXlsx(){
        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename("bd.xlsx")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(reportService.getXlsx());
    }

    @GetMapping("/xml")
    public ResponseEntity getXml() throws IOException, ParserConfigurationException, TransformerException {
        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename("bd.xml")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(reportService.getXml());
    }


    @RequestMapping(value = "/pdf", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport() throws DocumentException {

        var logs = (List<LogsEntity>) logService.findAll();
        ByteArrayInputStream bis = reportService.logsReport(logs);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/txtx")
    public ResponseEntity getTxt() throws IOException, ParserConfigurationException, TransformerException {
        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename("bd.txt")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(reportService.getXml());

    }

    @GetMapping("/txt")
    public ResponseEntity getTES(HttpServletResponse response) throws IOException {
        response.setContentType("text/txt");
        response.addHeader("Content-Disposition", "attachment; filename=\"report.txt\"");
        return ResponseEntity.ok(reportService.getTxt());

    }
}





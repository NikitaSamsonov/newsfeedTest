package n.samsonov.newsfeed.downloadBase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UploadController {

    private final ReportService reportService;


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

    @GetMapping("/csv")
    public ResponseEntity getCsv() throws IOException {
        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename("bd.csv")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(reportService.getCsv());
    }
}


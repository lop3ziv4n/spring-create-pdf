package ar.org.example.createpdf.controller;

import ar.org.example.createpdf.model.Person;
import ar.org.example.createpdf.model.Product;
import ar.org.example.createpdf.service.FileGeneratorITextService;
import ar.org.example.createpdf.service.FileGeneratorTemplateService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/file-generator")
public class FileGeneratorController {

    private FileGeneratorTemplateService fileGeneratorTemplateService;
    private FileGeneratorITextService fileGeneratorITextService;

    @Autowired
    public FileGeneratorController(FileGeneratorTemplateService fileGeneratorTemplateService, FileGeneratorITextService fileGeneratorITextService) {
        this.fileGeneratorTemplateService = fileGeneratorTemplateService;
        this.fileGeneratorITextService = fileGeneratorITextService;
    }

    @GetMapping(value = "/pdf-template", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> downloadPdfTemplate() {

        Map<String, Object> data = new HashMap<>();
        data.put("welcome", "Welcome to PDF Generation");

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(new Product(String.valueOf(i), "Product " + String.valueOf(i), String.valueOf(1 + i), String.valueOf(2 + i)));
        }

        data.put("products", products);

        Person person = new Person("John", "Doe", "Example Street 1", "12345", "Example City");
        data.put("person", person);

        ByteArrayInputStream bis;
        try {
            bis = this.fileGeneratorTemplateService.createPdf("template", data);
        } catch (DocumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=template-report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/pdf-itext", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> downloadPdfIText() {

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(new Product(String.valueOf(i), "Product " + String.valueOf(i), String.valueOf(1 + i), String.valueOf(2 + i)));
        }

        ByteArrayInputStream bis;
        try {
            bis = this.fileGeneratorITextService.createPdf(products);
        } catch (DocumentException e) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=itext-report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}

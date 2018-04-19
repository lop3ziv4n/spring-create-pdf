package ar.org.example.createpdf.service;

import ar.org.example.createpdf.SpringCreatePdfApplication;
import ar.org.example.createpdf.model.Person;
import ar.org.example.createpdf.model.Product;
import com.itextpdf.text.DocumentException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCreatePdfApplication.class)
public class FileGeneratorTemplateServiceTest {

    @Autowired
    private FileGeneratorTemplateService fileGeneratorTemplateService;

    @Test
    public void createPdf() {
        Map<String, Object> data = new HashMap<>();
        data.put("welcome", "Welcome to PDF Generation");

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(new Product(String.valueOf(i), "Product " + String.valueOf(i), String.valueOf(1 + i), String.valueOf(2 + i)));
        }

        data.put("products", products);

        Person person = new Person("John", "Doe", "Example Street 1", "12345", "Example City");
        data.put("person", person);
        try {
            ByteArrayInputStream bis = this.fileGeneratorTemplateService.createPdf("template", data);
            Assert.assertNotNull(bis);
        } catch (DocumentException e) {
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }
}
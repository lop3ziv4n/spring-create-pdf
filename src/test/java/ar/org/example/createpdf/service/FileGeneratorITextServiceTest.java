package ar.org.example.createpdf.service;

import ar.org.example.createpdf.SpringCreatePdfApplication;
import ar.org.example.createpdf.model.Product;
import com.itextpdf.text.DocumentException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCreatePdfApplication.class)
public class FileGeneratorITextServiceTest {

    @Autowired
    private FileGeneratorITextService fileGeneratorITextService;

    @Test
    public void createPdf() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(new Product(String.valueOf(i), "Product " + String.valueOf(i), String.valueOf(1 + i), String.valueOf(2 + i)));
        }

        try {
            ByteArrayInputStream bis = this.fileGeneratorITextService.createPdf(products);
            Assert.assertNotNull(bis);
        } catch (DocumentException e) {
            Assert.fail(e.getMessage());
        }
    }
}
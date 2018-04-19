package ar.org.example.createpdf.service;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class FileGeneratorTemplateService {

    private TemplateEngine templateEngine;

    @Autowired
    public FileGeneratorTemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public ByteArrayInputStream createPdf(String template, Map<String, Object> component) throws IOException, DocumentException {
        String processedHtml = this.build(template, component);
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml);
            renderer.layout();
            renderer.createPDF(bos, false);
            renderer.finishPDF();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) { /*ignore*/ }
            }
        }

        return new ByteArrayInputStream(bos.toByteArray());
    }

    private String build(String template, Map<String, Object> component) {
        Assert.notNull(template, "The template can not be null");
        Context ctx = new Context();

        for (String keys : component.keySet()) {
            ctx.setVariable(keys, component.get(keys));
        }

        return this.templateEngine.process(template, ctx);
    }
}

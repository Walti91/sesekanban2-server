package sese.services.utils;

import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;
import sese.exceptions.SeseError;
import sese.exceptions.SeseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class PdfGenerationUtil {

    private PdfGenerationUtil() {
    }

    public static byte[] createPdf(String template, Map<String, Object> variables) {

        String html = TemplateUtil.processTemplate(template, variables);
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (IOException | DocumentException e) {
            throw new SeseException(SeseError.PDF_GENERATION_ERROR);
        }
    }

}
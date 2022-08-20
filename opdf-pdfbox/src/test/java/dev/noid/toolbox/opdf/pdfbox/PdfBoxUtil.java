package dev.noid.toolbox.opdf.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfBoxUtil {

  public static int getPageCount(byte[] source) {
    try (PDDocument document = PDDocument.load(source)) {
      return document.getNumberOfPages();
    } catch (Exception cause) {
      throw new RuntimeException(cause);
    }
  }
}

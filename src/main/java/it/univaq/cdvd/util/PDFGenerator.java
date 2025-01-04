package it.univaq.cdvd.util;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import it.univaq.cdvd.model.Transazione;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class PDFGenerator {

    public void generatePDF(List<Transazione> transazioni, String categoria, LocalDate dataInizio, LocalDate dataFine) {
        String fileName = "Report_" + categoria + "_" + dataInizio + "_to_" + dataFine + ".pdf";
        // Creazione del documento
        Document document = new Document();

        try {
            // Creazione di PdfWriter per scrivere nel file
            PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(fileName)));

            // Aprire il documento
            document.open();

            // Aggiungere contenuto al documento
            document.add(new Paragraph("Report delle Transazioni"));
            document.add(new Paragraph("Categoria: " + categoria));
            document.add(new Paragraph("Periodo: " + dataInizio + " - " + dataFine));
            document.add(new Paragraph("\n"));

            // Aggiungere le transazioni
            for (Transazione transazione : transazioni) {
                document.add(new Paragraph(transazione.toString()));
            }

            System.out.println("PDF generato: " + fileName);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            // Chiudere il documento
            document.close();
        }
    }
}

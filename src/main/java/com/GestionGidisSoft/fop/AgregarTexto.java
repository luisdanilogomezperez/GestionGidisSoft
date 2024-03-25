package com.GestionGidisSoft.fop;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.io.IOException;

public class AgregarTexto {

    PDDocument document;
    PDPageContentStream contentStream;

    public AgregarTexto(PDDocument document, PDPageContentStream contentStream) {
        this.document = document;
        this.contentStream = contentStream;
    }

    void addSIngleLineText(String text, int xPosition, int yPosition, PDFont font, float fontSize, Color color) throws IOException {

        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.newLineAtOffset(xPosition, yPosition);
        contentStream.showText(text);
        contentStream.endText();
        contentStream.moveTo(0,0);

    }

    void addMultipleLineText(String[] text, float leading, int xPosition, int yPosition, PDFont font, float fontSize, Color color) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.setLeading(leading);
        contentStream.newLineAtOffset(xPosition, yPosition);
        for (int i = 0; i < text.length; i++) {
            contentStream.showText(text[i]);
            contentStream.newLine();
        }

        contentStream.endText();
        contentStream.moveTo(0,0);
    }

    float getTextWidth(String text, PDFont font, float fontSize) throws IOException {
        return font.getStringWidth(text)/1000 * fontSize;
    }
}

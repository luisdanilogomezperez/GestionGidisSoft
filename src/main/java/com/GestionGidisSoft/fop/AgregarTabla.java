package com.GestionGidisSoft.fop;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.io.IOException;

public class AgregarTabla {
    PDDocument document;
    PDPageContentStream contentStream;
    private int[] colWidths;
    private int cellHeight;
    private int yPosition;
    private int xPosition;
    private int colPosition = 0;
    private int xInicialPosition;
    private float fontSize;
    private PDFont font;
    private Color color;

    public AgregarTabla(PDDocument document, PDPageContentStream contentStream) {
        this.document = document;
        this.contentStream = contentStream;
    }

    public AgregarTabla(PDDocument document, PDPageContentStream contentStream, int[] colWidths, int cellHeight,
                        int yPosition, int xPosition, int colPosition, int xInicialPosition, float fontSize,
                        PDFont font, Color color) {
        this.document = document;
        this.contentStream = contentStream;

    }

    void setTable(int[] colWidths, int cellHeight, int yPosition, int xPosition, int xInicialPosition) {
        this.colWidths = colWidths;
        this.cellHeight = cellHeight;
        this.yPosition = yPosition;
        this.xPosition = xPosition;
        this.xInicialPosition = xInicialPosition;
    }

    void setTableFont(float fontSize, PDFont font, Color color) {
        this.font = font;
        this.fontSize = fontSize;
        this.color = color;
    }

    void addCell(String text, Color fillColor) throws IOException {
        contentStream.setStrokingColor(1f);

        if (fillColor != null) {
            contentStream.setNonStrokingColor(fillColor);
        }

        contentStream.addRect(xPosition, yPosition, colWidths[colPosition], cellHeight);

        if (fillColor == null) {
            contentStream.stroke();
        } else {
            contentStream.fillAndStroke();
        }

        contentStream.beginText();
        contentStream.setNonStrokingColor(color);

        if (colPosition == 4 || colPosition == 2) {
            contentStream.newLineAtOffset(xPosition+colWidths[colPosition]-20-fontSize, yPosition+10);
        } else {
            contentStream.newLineAtOffset(xPosition+20, yPosition+10);
        }

        contentStream.showText(text);
        contentStream.endText();

        xPosition = xPosition + colWidths[colPosition];
        colPosition++;

        if (colPosition == colWidths.length) {
            colPosition =0;
            xPosition = xInicialPosition;
            yPosition -= cellHeight;
        }
    }
}

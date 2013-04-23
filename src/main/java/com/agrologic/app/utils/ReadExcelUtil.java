
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.utils;

//~--- non-JDK imports --------------------------------------------------------

import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcelUtil {
    private String inputFile;

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void read() throws IOException {
        File     inputWorkbook = new File(inputFile);
        Workbook w;

        try {
            w = Workbook.getWorkbook(inputWorkbook);

            // Get the first sheet
            Sheet sheet = w.getSheet(0);

            // Loop over first 10 column and lines

            for (int j = 0; j < sheet.getColumns(); j++) {
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell     cell = sheet.getCell(j, i);
                    CellType type = cell.getType();

                    if (cell.getType() == CellType.LABEL) {
                        System.out.println("I got a label " + cell.getContents());
                    }

                    if (cell.getType() == CellType.NUMBER) {
                        System.out.println("I got a number " + cell.getContents());
                    }
                }
            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ReadExcelUtil test = new ReadExcelUtil();

        test.setInputFile("c:/temp/lars.xls");
        test.read();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

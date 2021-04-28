package importing;

import logic.Klasse;
import logic.Pupil;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Importer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Importer.class);

    private static Klasse extract(File file) {
        Klasse klasse = new Klasse();
        try {
            LOGGER.info("Beginning importing");
            Workbook wb = WorkbookFactory.create(file);
            Sheet sheet = wb.getSheetAt(0);
            PositionHandler position = readPositionValues(sheet);
            int rowPos = position.getBeginIndex();
            while (true) {
                try {
                    Row row = sheet.getRow(rowPos);
                    if (!"".equalsIgnoreCase(row.getCell(position.getForeNameColumnPos()).getStringCellValue())) {
                        String foreName = getValue(row, position.getForeNameColumnPos());
                        String name = getValue(row, position.getNameColumnPos());
                        double numberOfBooks = getValue(row, position.getNumberOfBooksPos());
                        double points = getValue(row, position.getPointsPos());
                        double percentage = getValue(row, position.getPercentagePos());
                        String username = getValue(row, position.getUserNamePos());
                        Pupil pupil = new Pupil(foreName, name);
                        pupil.setBooks((int) numberOfBooks);
                        pupil.setPoints((int) points);
                        pupil.setPercentage(String.valueOf(percentage));
                        pupil.setUserName(username);
                        klasse.addPupil(pupil);
                        LOGGER.info("Added pupil {} {} to current class", foreName, name);
                        rowPos++;
                    } else {
                        LOGGER.info("Finished importing");
                        break;
                    }
                } catch (NullPointerException npe){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return klasse;
    }

    /***
     * Method to determine in which columns which values are to be found for easier access in the main import method
     * @param sheet
     * @return
     */
    private static PositionHandler readPositionValues(Sheet sheet) {
        PositionHandler handler = new PositionHandler();
        int foreNameColumnPos = 0;
        int nameColumnPos = 0;
        int numberOfBooksPos = 0;
        int pointsPos = 0;
        int percentagePos = 0;
        int userNamePos = 0;
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                if (cell.getCellType() == CellType.STRING) {
                    String cellValue = cell.getStringCellValue();
                    int cellColumn = cell.getColumnIndex();
                    if ("Vorname".equalsIgnoreCase(cellValue)) {
                        foreNameColumnPos = cellColumn;
                        handler.setForeNameColumnPos(foreNameColumnPos);
                    }
                    if ("Nachname".equalsIgnoreCase(cellValue)) {
                        nameColumnPos = cellColumn;
                        handler.setNameColumnPos(nameColumnPos);
                    }
                    if ("Anzahl Bücher".equalsIgnoreCase(cellValue)) {
                        numberOfBooksPos = cellColumn;
                        handler.setNumberOfBooksPos(numberOfBooksPos);
                    }
                    if ("Punkte".equalsIgnoreCase(cellValue)) {
                        pointsPos = cellColumn;
                        handler.setPointsPos(pointsPos);
                    }
                    if ("Leistung".equalsIgnoreCase(cellValue)) {
                        percentagePos = cellColumn;
                        handler.setPercentagePos(percentagePos);
                    }
                    if ("Benutzername".equalsIgnoreCase(cellValue)) {
                        userNamePos = cellColumn;
                        handler.setUserNamePos(userNamePos);
                        handler.setBeginIndex(cell.getRowIndex() + 1);
                        return handler;
                    }
                }
            }
        }
        return handler;
    }

    public static Klasse getFile(File file) {
        if (file != null) {
            if (file.getPath().matches(".+\\.xls") || file.getPath().matches(".+\\.xlsx")) {
                Klasse res = extract(file);
                if (res.getPupils().isEmpty()){
                    throw new IllegalArgumentException("Was not able to construct the necessary data from this file");
                } else {
                    return res;
                }
            }
        }
        throw new IllegalArgumentException("Was not able to construct the necessary data from this file");
    }

    private static <R> R getValue(Row row, int pos) {
        Cell cell = row.getCell(pos);
        if (cell.getCellType() == CellType.STRING) {
            return (R) cell.getStringCellValue();
        } else {
            return (R) (Double) cell.getNumericCellValue();
        }
    }


}

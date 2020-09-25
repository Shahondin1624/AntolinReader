package importing;

import logic.Pupil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importer {

    private static List<Pupil> getFileDataXLS(File file) {
        ArrayList<Pupil> pupils = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file);) {
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            int counter = 0;
            while (true) {
                Row row = sheet.getRow(counter);
                if (row != null && "Vorname".equals(row.getCell(0).getStringCellValue())) {
                    counter++;
                    break;
                } else counter++;
            }
            while (true) {
                Row row = sheet.getRow(counter);
                if (row == null || "".equals(row.getCell(0).getStringCellValue())) {
                    break;
                }
                String forename = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                int points = (int) row.getCell(3).getNumericCellValue();
                Pupil pupil = new Pupil(forename, name);
                pupil.setPoints(points);
                pupils.add(pupil);
                counter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException se){

        }
        return pupils;
    }

    private static List<Pupil> getFileDataXLSX(File file) {
        ArrayList<Pupil> pupils = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file);) {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int counter = 3;
            while (true) {
                Row row = sheet.getRow(counter);
                if ("".equals(row.getCell(0).getStringCellValue())) {
                    break;
                }
                String forename = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                int points = (int) row.getCell(3).getNumericCellValue();
                Pupil pupil = new Pupil(forename, name);
                pupil.setPoints(points);
                pupils.add(pupil);
                counter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pupils;
    }

    public static List<Pupil> getFile(File file) {
        if (file != null) {
            if (file.getPath().matches(".+\\.xls")) {
                return getFileDataXLS(file);
            } else if (file.getPath().matches(".+\\.xlsx")) {
                return getFileDataXLSX(file);
            }
        }
        return new ArrayList<>();
    }


}

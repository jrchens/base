package apache.poi;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HSSFWorkbookTest {

    @Test
    public void testRead() throws Exception {
        Workbook wb = null;
        List<String> error = Lists.newArrayList();
        try {
            File file = new File("/Users/sheng/Documents/students.xls");
            wb = WorkbookFactory.create(file);
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();

            String sheetName = sheet.getSheetName();
            List<String> configColumnViewames = Lists.newArrayList("年级", "班级", "身份证", "学生姓名", "缴费项目1", "金额", "缴费项目2", "金额", "联系方式", "备注", "收费截止日期");
            List<String> configColumnames = Lists.newArrayList("col001", "col002", "col003", "col004", "col005", "col006", "col007", "col008", "col009", "col010", "col011");
            List<String> configColumnCheck = Lists.newArrayList(null, null, null, null, null, null, null, null, null, null, null);
            List<String> columnames = Lists.newArrayList();
            List<String> sheetData = Lists.newArrayList();

            // nullable,regex

            Row tableHeader = sheet.getRow(1);
            short cellNum = tableHeader.getLastCellNum();
            for (int i = 0; i < cellNum; i++) {
                Cell cell = tableHeader.getCell(i);
                if (null != cell && Cell.CELL_TYPE_STRING == cell.getCellType()) {
                    columnames.add(cell.getStringCellValue());
                }
            }

            if (configColumnViewames.equals(columnames)) {
                for (int i = 2; i <= rowNum; i++) {
                    boolean emptyRow = true;
                    Row row = sheet.getRow(i);
                    List<String> rowData = Lists.newArrayList();
                    for (int j = 0; j < cellNum; j++) {
                        Cell cell = row.getCell(j);
                        if (cell == null || Cell.CELL_TYPE_BLANK == cell.getCellType()) {
                            rowData.add("");
                            // cell.getColumnIndex();
                        } else {
                            if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                                rowData.add(cell.getStringCellValue());
                                if (emptyRow) emptyRow = false;
                            } else {
                                String cellRef = new CellReference(cell.getRowIndex(), cell.getColumnIndex()).formatAsString();
                                error.add(String.format("cell (%s) format err", cellRef));
                            }
                        }
                    }
                    if (emptyRow) continue;
                    sheetData.add(Arrays.toString(rowData.toArray()));
                    sheetData.add("\n");
                }
            } else {
                error.add("table header format err");
            }

            System.err.println(Arrays.toString(error.toArray()));
            System.out.println(sheetData);

        } catch (Exception e) {
            throw e;
        } finally {
            wb.close();
        }
    }

    @Test
    public void testXLSRead() throws FileNotFoundException, IOException {
        HSSFWorkbook hssfWorkbook = null;
        FileInputStream is = null;
        try {
            // C:/Users/Chen/git/simple/simple-test/src/test/resources
            // /Users/chensheng/git/simple/simple-test/src/test/resources
            is = new FileInputStream("resources/read.xls");
            hssfWorkbook = new HSSFWorkbook(is);

            FormulaEvaluator evaluator = hssfWorkbook.getCreationHelper().createFormulaEvaluator();

            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rows; i++) {
                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                int cells = row.getLastCellNum(); //row.getPhysicalNumberOfCells();
                for (int j = 0; j < cells; j++) {
//		    HSSFCell cell = row.getCell(j);
//		    if (cell == null) {
//			continue;
//		    }
                    Cell cell = row.getCell(j);
                    CellValue cellValue = evaluator.evaluate(cell);
                    if (cellValue == null) {
                        continue;
                    }
                    String value = null;
                    String formatString = null;
                    // CELL_TYPE_STRING
                    // CELL_TYPE_NUMERIC
                    // CELL_TYPE_FORMULA
                    // CELL_TYPE_BOOLEAN
                    // CELL_TYPE_ERROR

                    // cell.getCellType()
                    switch (cellValue.getCellType()) {

                        case HSSFCell.CELL_TYPE_BLANK:
                            value = "";
                            break;

                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            value = "" + cell.getBooleanCellValue();
                            break;

                        case HSSFCell.CELL_TYPE_ERROR:
                            value = "" + cell.getErrorCellValue();
                            break;
//		    case HSSFCell.CELL_TYPE_FORMULA:
//			value = "" + cell.getCellFormula() + "=" + evaluator.evaluateFormulaCell(cell);
//			break;

                        case HSSFCell.CELL_TYPE_NUMERIC:
                            CellStyle cellStyle = cell.getCellStyle();
                            if (cellStyle != null) {
                                formatString = cellStyle.getDataFormatString();
                                // formatString = HSSFDataFormat.getBuiltinFormat(cell.getCellStyle().getDataFormat());
                            }
                            if (HSSFDateUtil.isCellDateFormatted(cell) || "reserved-0x1f".equals(formatString)) {
                                // reserved-0x1f (international and undocumented)
                                value = "" + new java.sql.Date(cell.getDateCellValue().getTime());
                            } else {
                                value = "" + cell.getNumericCellValue();
                            }

                            break;

                        case HSSFCell.CELL_TYPE_STRING:
                            value = "" + cell.getStringCellValue();
                            break;

                        default:
                    }

                    System.out.print(String.format("%s,", value));

                }
                System.out.println();
            }

        } finally {
            is.close();
            hssfWorkbook.close();
        }
    }

    @Test
    public void testReadXLSFormats() {
        Object[] formats = HSSFDataFormat.getBuiltinFormats().toArray();
        for (Object object : formats) {
            System.out.println(object);
        }

        for (int i = 0x17; i <= 0x24; i++) {
            // reserved-0x
            System.out.println(Integer.toHexString(i)); // 35~54
        }
//	General
//	0
//	0.00
//	#,##0
//	#,##0.00
//	"$"#,##0_);("$"#,##0)
//	"$"#,##0_);[Red]("$"#,##0)
//	"$"#,##0.00_);("$"#,##0.00)
//	"$"#,##0.00_);[Red]("$"#,##0.00)
//	0%
//	0.00%
//	0.00E+00
//	# ?/?
//	# ??/??
//	m/d/yy
//	d-mmm-yy
//	d-mmm
//	mmm-yy
//	h:mm AM/PM
//	h:mm:ss AM/PM
//	h:mm
//	h:mm:ss
//	m/d/yy h:mm
//	reserved-0x17
//	reserved-0x18
//	reserved-0x19
//	reserved-0x1a
//	reserved-0x1b
//	reserved-0x1c
//	reserved-0x1d
//	reserved-0x1e
//	reserved-0x1f
//	reserved-0x20
//	reserved-0x21
//	reserved-0x22
//	reserved-0x23
//	reserved-0x24
//	#,##0_);(#,##0)
//	#,##0_);[Red](#,##0)
//	#,##0.00_);(#,##0.00)
//	#,##0.00_);[Red](#,##0.00)
//	_("$"* #,##0_);_("$"* (#,##0);_("$"* "-"_);_(@_)
//	_(* #,##0_);_(* (#,##0);_(* "-"_);_(@_)
//	_(* #,##0.00_);_(* (#,##0.00);_(* "-"??_);_(@_)
//	_("$"* #,##0.00_);_("$"* (#,##0.00);_("$"* "-"??_);_(@_)
//	mm:ss
//	[h]:mm:ss
//	mm:ss.0
//	##0.0E+0
//	@

    }
}

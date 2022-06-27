package cn.info.libre.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.Test;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlowLimitServiceImplTest {

    @Test
    public void testWordToText() throws Exception {
        InputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.docx");
        XWPFDocument wordDocument = new XWPFDocument(is);
        XWPFWordExtractor wordExtractor = new XWPFWordExtractor(wordDocument);
        List<XWPFTable> tables = wordDocument.getTables();
        for (XWPFTable table : tables) {
            List<XWPFTableRow> rows = table.getRows();
            for (XWPFTableRow row : rows) {
                for (int i = 0; i < row.getTableCells().size(); i++) {
                    XWPFTableCell cell = row.getCell(i);
                    System.out.println(cell.getText());
                }
            }
        }
        String text = wordExtractor.getText();
        System.out.println(text);
        Writer writer = new FileWriter(new File("C:\\Users\\Administrator\\Desktop\\converter.txt"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        //是否添加空格
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "text");
        /*transformer.transform(
                new DOMSource(wordExtractor),
                new StreamResult(writer));*/
    }

    @Test
    public void testWord() throws Exception {
        InputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.docx");
        XWPFDocument wordDocument = new XWPFDocument(is);
        for (XWPFTable table : wordDocument.getTables()) {
            List<XWPFTableRow> rows = table.getRows();
            for (XWPFTableRow row : rows) {
                for (int i = 0; i < row.getTableCells().size(); i++) {
                    XWPFTableCell cell = row.getCell(i);
                    System.out.println(cell.getText());
                }
            }
        }
    }

    @Test
    public void main() throws Exception {
        InputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.doc");
        HWPFDocument doc = new HWPFDocument(is);
        Range range = doc.getRange();
        List<List<Integer>> tblList = new ArrayList<>();
        List<String> tables = new ArrayList<>();
        for (TableIterator tableIterator = new TableIterator(new Range(81, range.getEndOffset(), doc)); tableIterator.hasNext(); ) {
            Table next = tableIterator.next();
            int runs = next.numCharacterRuns();
            List<Integer> list = new ArrayList<>();
            int start = -1;
            int end = -1;
            for (int i = 0; i < runs; i++) {
                CharacterRun characterRun = next.getCharacterRun(i);
                if (i == 0) {
                    start = characterRun.getStartOffset();
                } else if (i == runs - 1) {
                    end = characterRun.getEndOffset();
                }
            }
            list.add(start);
            list.add(end);
            tblList.add(list);
            StringBuilder sb1 = new StringBuilder();
            for (int i = 0; i < next.numRows(); i++) {
                TableRow row = next.getRow(i);
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < row.numCells(); j++) {
                    TableCell cell = row.getCell(j);
                    String text = cell.text();
                    sb.append("|").append(text.trim().replaceAll("[\r\n]", ""));
                }
                sb1.append(sb).append("\n");
            }
            tables.add(sb1.toString());
        }
        List<Integer> startList = new ArrayList<>();
        List<Integer> endList = new ArrayList<>();
        for (List<Integer> list : tblList) {
            startList.add(list.get(0));
            endList.add(list.get(1));
        }
        List<String> texts = new ArrayList<>();
        for (int i = 0; i < range.numParagraphs(); i++) {
            Paragraph paragraph = range.getParagraph(i);
            String text = paragraph.text().replaceAll("\\s", "");
            int startOffset = paragraph.getStartOffset();
            boolean isIn = false;
            for (List<Integer> list : tblList) {
                Integer start = list.get(0);
                Integer end = list.get(1);
                if (startOffset > start && startOffset < end) {
                    isIn = true;
                    break;
                }
            }
            //表格
            if (startList.contains(startOffset)) {
                int index = startList.indexOf(startOffset);
                texts.add(tables.get(index));
            } else if (isIn) {
                continue;
            } else {
                String trim = text.trim();
                if (StringUtils.isNotBlank(trim)) {
                    texts.add(trim);
                }
            }
        }
        System.out.println(texts);
    }

    @Test
    public void testSet() {
        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(22);
        set1.add(25);
        set1.add(5);

        Set<Integer> set2 = new HashSet<>();
        set2.add(1);
        set2.add(25);

        Set<Integer> set3 = new HashSet<>();
        set3.add(2);
        set3.add(22);
        set3.add(66);

        List<Set<Integer>> list = new ArrayList<>();
        list.add(set1);
        list.add(set2);
        list.add(set3);

        Set<Integer> integers = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            integers.retainAll(list.get(i));
        }
        System.out.println(integers);

    }

    @Test
    public void testStockId() {
        String str = "（股票代码：002410）";
        Pattern compile = Pattern.compile("(60[0135]|900|00[02]|200|300|688)\\d{3}|60\\d{4}");
        Matcher matcher = compile.matcher(str);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
    }

}














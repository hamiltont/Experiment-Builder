package edu.vanderbilt.psychology;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Boolean;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import edu.vanderbilt.psychology.Card.Color;
import edu.vanderbilt.psychology.Card.Shape;
import edu.vanderbilt.psychology.Card.Size;

public class ExcelWriter {
	WritableWorkbook workbook_;
	WritableSheet sheet_;
	int currentRow_ = 0;

	public ExcelWriter(String filename) {
		try {
			workbook_ = Workbook.createWorkbook(new File("output.xls"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet_ = workbook_.createSheet("Data", 0);

		setup();

	}

	private void setup() {
		currentRow_ = 2;
		Label game = new Label(0, 1, "Game");
		Label trial = new Label(1, 1, "Trial");
		Label correct = new Label(2, 1, "Correct");
		Label resp = new Label(3, 1, "Resp. Time(ms)");
		Label trialCard = new Label(4, 0, "                    Trial Card     ");
		Label selectedCard = new Label(7, 0, "              Selected Card     ");
		Label target1 = new Label(11, 0, "                Target 1       ");
		Label target2 = new Label(14, 0, "                Target 2       ");
		Label target3 = new Label(17, 0, "                Target 3       ");
		Label target4 = new Label(20, 0, "                Target 4       ");

		try {
			sheet_.addCell(game);
			sheet_.addCell(trial);
			sheet_.addCell(correct);
			sheet_.addCell(resp);
			sheet_.addCell(trialCard);
			sheet_.addCell(selectedCard);
			sheet_.addCell(target1);
			sheet_.addCell(target2);
			sheet_.addCell(target3);
			sheet_.addCell(target4);

		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

		Label s1 = new Label(4, 1, "Size");
		Label c1 = new Label(5, 1, "Color");
		Label sh1 = new Label(6, 1, "Shape");

		Label s2 = new Label(7, 1, "Size");
		Label c2 = new Label(8, 1, "Color");
		Label sh2 = new Label(9, 1, "Shape");

		Label s3 = new Label(11, 1, "Size");
		Label c3 = new Label(12, 1, "Color");
		Label sh3 = new Label(13, 1, "Shape");

		Label s4 = new Label(14, 1, "Size");
		Label c4 = new Label(15, 1, "Color");
		Label sh4 = new Label(16, 1, "Shape");

		Label s5 = new Label(17, 1, "Size");
		Label c5 = new Label(18, 1, "Color");
		Label sh5 = new Label(19, 1, "Shape");

		Label s6 = new Label(20, 1, "Size");
		Label c6 = new Label(21, 1, "Color");
		Label sh6 = new Label(22, 1, "Shape");

		try {
			sheet_.addCell(s1);
			sheet_.addCell(s2);
			sheet_.addCell(s3);
			sheet_.addCell(s4);
			sheet_.addCell(s5);
			sheet_.addCell(s6);

			sheet_.addCell(c1);
			sheet_.addCell(c2);
			sheet_.addCell(c3);
			sheet_.addCell(c4);
			sheet_.addCell(c5);
			sheet_.addCell(c6);

			sheet_.addCell(sh1);
			sheet_.addCell(sh2);
			sheet_.addCell(sh3);
			sheet_.addCell(sh4);
			sheet_.addCell(sh5);
			sheet_.addCell(sh6);

		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	public void finish() {

		WritableCellFormat format = new WritableCellFormat();
		try {
			format.setBackground(Colour.AQUA);
		} catch (WriteException e1) {
			e1.printStackTrace();
		}
		for (int col = 4; col <= 6; col++)
			for (int row = 1; row <= 20; row++) {
				WritableCell c = sheet_.getWritableCell(col, row);
				c.setCellFormat(format);
			}

		WritableCellFormat format2 = new WritableCellFormat();
		try {
			format2.setBackground(Colour.GOLD);
		} catch (WriteException e1) {
			e1.printStackTrace();
		}
		for (int col = 7; col <= 10; col++)
			for (int row = 1; row <= 20; row++) {
				WritableCell c = sheet_.getWritableCell(col, row);
				c.setCellFormat(format2);
			}
		
		
		try {
			workbook_.write();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			workbook_.close();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addRow(String gameName, int trial, boolean correct,
			long responseTime, Card test, Card selected, List<Card> targets) {

		Label gameLabel = new Label(0, currentRow_, gameName);
		jxl.write.Number trialNum = new jxl.write.Number(1, currentRow_,
				(double) trial);
		WritableCellFormat color = new WritableCellFormat();
		try {

			if (correct)
				color.setBackground(Colour.BRIGHT_GREEN);
			else
				color.setBackground(Colour.RED);
		} catch (WriteException we) {
			we.printStackTrace();
		}

		Boolean correctBool = new Boolean(2, currentRow_, correct, color);
		Number respNumber = new Number(3, currentRow_, responseTime);

		try {
			sheet_.addCell(gameLabel);
			sheet_.addCell(trialNum);
			sheet_.addCell(correctBool);
			sheet_.addCell(respNumber);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

		int col = 4;
		writeCard(4, test);

		col += 3;
		writeCard(col, selected);

		++col;

		for (Card target : targets) {
			col += 3;
			writeCard(col, target);
		}

		++currentRow_;
	}

	private void writeCard(int col, Card test) {
		Color c = test.getColor_();
		Shape s = test.getShape_();
		Size si = test.getSize_();

		Label sizeLabel = new Label(col, currentRow_, si.toString());
		Label colorLabel = new Label(col + 1, currentRow_, c.toString());
		Label shapeLabel = new Label(col + 2, currentRow_, s.toString());

		try {
			sheet_.addCell(sizeLabel);
			sheet_.addCell(colorLabel);
			sheet_.addCell(shapeLabel);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

	}
}

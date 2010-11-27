/**
 * 
 */
package edu.vanderbilt.psychology.gui.sideBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Delineates a grouping of {@link Section}s in the sidebar
 * 
 * <p>
 * It is important to remember that the {@link SectionDivider} is contained
 * within the {@link SectionedPanel} (built using the {@link BoxLayout}). The
 * {@link BoxLayout} will stretch expand components to fill all available room
 * as much as possible, but will also respect each components declared maximum
 * size and will not stretch components beyond that size. This means that it is
 * critical that this {@link SectionDivider} specify a maximum size in order to
 * not be expanded vertically by the {@link BoxLayout}. The tallest we would
 * ever want the {@link SectionDivider} to be is it's preferred height, so our
 * maximum size is reported to be our preferred height.
 * </p>
 * 
 * @author Hamilton Turner
 */
public class SectionDivider extends JPanel {
	private String text_;
	private Font font;
	private static final int PREFERRED_HEIGHT = 30;
	private static final Dimension PREFERRED_SIZE = new Dimension(
			Short.MAX_VALUE, PREFERRED_HEIGHT);

	private static final int OFFSET = 30;

	public SectionDivider(String text) {
		super();
		text_ = text;
		font = new Font(Font.SERIF, Font.BOLD, 14);
		setPreferredSize(PREFERRED_SIZE);
		setMaximumSize(PREFERRED_SIZE);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int h = getHeight();
		int w = getWidth();

		Color color1 = getBackground();
		Color color2 = color1.darker().darker();

		// Paint a gradient from top to bottom
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);

		g2.setPaint(gp);
		g2.fillRect(0, 0, w, h);

		g2.setFont(font);
		g2.setPaint(Color.BLACK);
		FontRenderContext frc = g2.getFontRenderContext();
		LineMetrics lm = font.getLineMetrics(text_, frc);
		float height = lm.getAscent() + lm.getDescent();
		float x = OFFSET;
		float y = (h + height) / 2 - lm.getDescent();
		g2.drawString(text_, x, y);

		setOpaque(true);
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = 4019493616706873287L;

}
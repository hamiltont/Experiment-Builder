/**
 * 
 */
package edu.vanderbilt.psychology.gui.sideBar;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Defines a standard look and feel for {@link Section}s. All {@link Section}s
 * have a header bar, which contains a title string and is clickable to
 * expand/collapse the {@link Section}. When expanded, the {@link Section}
 * displays a content {@link JPanel}.
 * 
 * Sections are only created by the {@link SectionedPanel}.
 * {@link SectionedPanel} accepts objects that implement {@link ISectionable}
 * and adds those objects to a {@link Section}
 * 
 * @author Hamilton Turner
 * 
 */
// TODO If the code base gets to the optimize level, then we should attempt to
// avoid re-creating all of these Section object. We are using a Prototype model
// for the Propertys, so something similar should work here. Do a check to see
// if something matches the default and avoid creating a new object if it does
public final class Section extends JPanel {
	private boolean selected_;
	private JPanel contentPanel_;
	private HeaderPanel headerPanel_;

	public Section(String text, JPanel panel) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		selected_ = false;
		headerPanel_ = new HeaderPanel(text);

		contentPanel_ = panel;

		add(headerPanel_);
		add(panel);
		panel.setVisible(false);

	}

	public void toggleSelection() {
		selected_ = !selected_;

		if (contentPanel_.isShowing())
			contentPanel_.setVisible(false);
		else
			contentPanel_.setVisible(true);

		validate();
		headerPanel_.repaint();
	}

	private class HeaderPanel extends JPanel implements MouseListener {

		String text_;
		Font font;
		BufferedImage open, closed;
		final int OFFSET = 30, PAD = 5;

		public HeaderPanel(String text) {
			super();
			addMouseListener(this);
			text_ = text;
			font = new Font(Font.SERIF, Font.BOLD, 14);

			try {
				open = ImageIO.read(new File("images/arrow_down_mini.png"));
				closed = ImageIO.read(new File("images/arrow_right_mini.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			int w = getWidth();
			int h = getHeight();

			Color color1 = getBackground();
			Color color2 = color1.darker();

			// Paint a gradient from top to bottom
			GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);

			g2.setPaint(gp);

			g2.fillRect(0, 0, w, h);

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			if (selected_)
				g2.drawImage(open, PAD, 0, h, h, this);
			else
				g2.drawImage(closed, PAD, 0, h, h, this);

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

		public void mouseClicked(MouseEvent e) {
			toggleSelection();
		}

		public void mouseEntered(MouseEvent e) {
			if (getVisibleRect().contains(e.getPoint())) {
				setBackground(Color.CYAN);
			}
		}

		public void mouseExited(MouseEvent e) {

			if (!getVisibleRect().contains(e.getPoint())) {
				setBackground(Color.LIGHT_GRAY.brighter());
			}
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		/** Provide a Universal ID for serialization */
		private static final long serialVersionUID = 4019493616706873287L;
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -7231084537225793590L;
}

/**
 * 
 */
package edu.vanderbilt.psychology.gui.sideBar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.gui.main.MainFrame;
import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;

/**
 * Defines the preview panel that shows in the top of the application's sidebar.
 * To see how the main application layout is created, see {@link MainFrame}
 * 
 * <p>
 * The sidebar internally uses a {@link BorderLayout} that is broken apart as
 * follows:
 * </p>
 * <img src="../../../../../../doc-source/diagrams/sidebar-border-layout.jpg" />
 * 
 * <p>
 * The top panel (teal color) is this {@link PreviewPanel} section, and the
 * bottom panel is the {@link SectionedPanel}. The height of the top component
 * is set using the preferred size of the internal {@link PreviewPanel}. In the
 * future this may be a resizable property. The preferred width of the
 * {@link PreviewPanel} is ignored, and the top component expands to fill all
 * available space. However, the available space is limited to
 * {@link Builder#SIDEBAR_WIDTH}, so the width of the top component will always
 * be equal to {@link Builder#SIDEBAR_WIDTH}.
 * </p>
 * 
 * <p>
 * This layout implies that the {@link PreviewPanel} must always define a
 * preferred height, and that it will always receive that value as its height
 * </p>
 * 
 * 
 * @author Hamilton Turner
 * 
 * @see MainFrame
 * @see SectionedPanel
 */
// TODO Allow an individual element to override the default PreviewPanel
public class PreviewPanel extends JPanel {

	private static final int PREFERRED_HEIGHT = 200;

	private SlideElement currentElement_ = null;

	private JLabel name_;

	private StageWrapper stage_;

	public PreviewPanel(StageWrapper stage) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		Border insideBorder = BorderFactory.createEmptyBorder(0, 10, 0, 0);
		Border outsideBorder = BorderFactory.createMatteBorder(0, 2, 0, 0,
				Color.GRAY);
		setBorder(BorderFactory.createCompoundBorder(outsideBorder,
				insideBorder));

		setPreferredSize(new Dimension(1, PREFERRED_HEIGHT));

		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		add(nameLabel);

		stage_ = stage;

		name_ = new JLabel("Test");
		name_.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		add(name_);

		JButton remove = new JButton("Remove Element");
		remove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (currentElement_ == null)
					return;

				Rectangle bounds = currentElement_.getBounds();
				stage_.remove(currentElement_);
				currentElement_ = null;
				SelectionManager.getInstance().clearSelection();

				stage_.repaint(bounds);
				System.gc();
			}
		});
		add(remove);

		setVisible(true);
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int w = getWidth();
		int h = getHeight();

		Color color1 = getBackground().brighter();
		Color color2 = color1.brighter();

		// Paint a gradient from top to bottom
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);

		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);

		setOpaque(true);
	}

	public void updatePreview(SlideElement element) {
		if (element == null) {
			// TODO - remove the remove button, reset all of the fields to
			// default values

			name_.setText("Nothing Selected");
			name_.invalidate();

			return;
		}
		currentElement_ = element;
		name_.setText(element.getElementName());
		name_.invalidate();
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -2812933657086452761L;
}

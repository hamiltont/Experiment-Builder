/**
 * 
 */
package edu.vanderbilt.psychology.gui.slideElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import sun.awt.image.ImageFormatException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.model.EBList;
import edu.vanderbilt.psychology.model.ListDatabase;
import edu.vanderbilt.psychology.model.elements.ImageElementModel;
import edu.vanderbilt.psychology.model.elements.ModelElement;
import edu.vanderbilt.psychology.model.properties.Appearance;
import edu.vanderbilt.psychology.model.properties.DataSource;
import edu.vanderbilt.psychology.model.properties.Movement;
import edu.vanderbilt.psychology.model.properties.Position;
import edu.vanderbilt.psychology.model.properties.Property;
import edu.vanderbilt.psychology.model.properties.DataSource.Type;

/**
 * @author Hamilton Turner
 * 
 */
public class ImageElement extends SlideElement {

	private JLabel bottomRightHandler_;
	private JLabel imageLabel_;
	private ImageIcon image_;
	private String name_;

	private static final String Handle_Up_Text = "+";
	private static final String Handle_Pressed_Text = "-";
	private static final int Handle_Size = 10;

	private ArrayList<Property> properties_;

	private ImageElementModel mModel;

	@SuppressWarnings("unchecked")
	public ImageElement(File imageFileSelected) throws ImageFormatException {
		super();

		// Allows resize handles without an entire JLayeredPane
		setLayout(null);

		properties_ = new ArrayList<Property>();

		// Add properties
		properties_.add(new Appearance());
		properties_.add(new Movement());
		properties_.add(new Position());
		// properties_.add(new DataSource(imageFileSelected.getAbsolutePath(),
		// Type.Single_File));

		EBList<File> files = new EBList<File>("mylist");
		files.add(imageFileSelected);
		ListDatabase.getInstance().addFileReferenceList(files);
		
		properties_.add(new DataSource(ListDatabase.getInstance().getByName(
				"mylist"), Type.Multiple_Files));

		name_ = imageFileSelected.getName();

		BufferedImage image = null;
		try {
			image = ImageIO.read(imageFileSelected);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (image == null) {
			// TODO - report error to user!

			SelectionManager.getInstance().remove(this);
			throw new ImageFormatException(
					"Image file was not readable by any ImageReader contained in this system's "
							+ "Java Runtime Environment. Please be sure your JRE is up to date, "
							+ "or convert your image to a different format");
		}

		image_ = new ImageIcon(image);
		imageLabel_ = new JLabel(image_);
		imageLabel_.setSize(image_.getIconWidth(), image_.getIconHeight());

		// Set the bounds, so the container expands and we can correctly place
		// handles
		setBounds(0, 0, image_.getIconWidth(), image_.getIconHeight());

		// Add the handles first, so they display on top of the image
		bottomRightHandler_ = new JLabel(Handle_Up_Text);
		bottomRightHandler_.setSize(Handle_Size, Handle_Size);
		bottomRightHandler_.setOpaque(true);
		bottomRightHandler_.setLocation(this.getWidth() - Handle_Size, this
				.getHeight()
				- Handle_Size);
		bottomRightHandler_.addMouseListener(genericListener);
		add(bottomRightHandler_);

		// Add the imageLabel
		add(imageLabel_);

		setBackground(Color.RED);

		mModel = new ImageElementModel(this);

	}

	@Override
	public void initializeWithModel(ModelElement model) {
		// TODO - implement
		throw new NotImplementedException();
	}

	@Override
	public List<Property> getProperties() {
		// TODO this is a hack for now. Later on store the properties in the
		// model
		return properties_;
	}

	@Override
	public ModelElement getModel() {
		return mModel;
	}

	@Override
	public String getElementName() {
		return name_;
	}

	private void updateLayout() {

		int width = getWidth(), height = getHeight();

		bottomRightHandler_.setLocation(this.getWidth() - Handle_Size, this
				.getHeight()
				- Handle_Size);

		imageLabel_.setBounds(0, 0, width, height);

		// TODO - re-read the image from the file before scaling
		image_.setImage(getScaledImage(image_.getImage(), width, height));

		ImageElement.this.repaint();
	}

	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	private MouseListener genericListener = new MouseListener() {

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
			bottomRightHandler_.setText(Handle_Pressed_Text);
		}

		public void mouseExited(MouseEvent e) {
			bottomRightHandler_.setText(Handle_Up_Text);
		}

		public void mousePressed(MouseEvent e) {
			motionListener_.setStart(e.getPoint(), getBounds());
			e.getComponent().addMouseMotionListener(motionListener_);
		}

		public void mouseReleased(MouseEvent e) {
			e.getComponent().removeMouseMotionListener(motionListener_);
			updateLayout();
		}

	};

	private DragMouseMotionListener motionListener_ = new DragMouseMotionListener();

	/**
	 * This class keeps track of the starting X and Y. These starting points are
	 * measured relative to the container that the event originally fired from.
	 * For this use case, that will always be the resize handles. That means
	 * that startX and startY will be between [0, Handle_Size], and that the
	 * current drag location can be found by measuring the current event X,Y
	 * values relative to the start X,Y
	 * 
	 * 
	 * I am using the {@link MouseListener} mousePressed() and mouseReleased()
	 * events to register/remove this class, so we will never see mouse move
	 * events, only mouseDrag events (because the mouse will be pressed the
	 * entire time we are receiving events)
	 * 
	 * @author Hamilton Turner
	 * 
	 */
	private class DragMouseMotionListener implements MouseMotionListener {
		private Point startPoint_;
		private Rectangle startBounds_;

		/**
		 * 
		 * @param startPoint
		 *            The point (relative to the parent container aka the resize
		 *            handle for us) that the mouse was originally pressed
		 * @param startBounds
		 *            The original bounds of the object being resized within its
		 *            parent. This gives us the original location, as well as
		 *            the original width and height. Depending on the resize
		 *            handle being used, the object's bounds will be adjusted
		 *            differently
		 */
		public void setStart(Point startPoint, Rectangle startBounds) {
			startPoint_ = startPoint;
			startBounds_ = startBounds;
		}

		public void mouseDragged(MouseEvent e) {
			int motionHorizontal = e.getX() - startPoint_.x;
			int motionVertical = e.getY() - startPoint_.y;

			ImageElement.this.setSize(startBounds_.width + motionHorizontal,
					startBounds_.height + motionVertical);

			// TODO - there are some bugs here not allowing this
			// updateLayout();
		}

		public void mouseMoved(MouseEvent e) {
		}
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = 6583957254094683348L;

}

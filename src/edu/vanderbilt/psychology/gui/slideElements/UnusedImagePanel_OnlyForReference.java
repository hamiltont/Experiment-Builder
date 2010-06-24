/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package edu.vanderbilt.psychology.gui.slideElements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.java.swingfx.jdraggable.Draggable;

/**
 * @author Hamilton Turner {@link http
 *         ://java.sun.com/docs/books/tutorial/uiswing
 *         /examples/components/IconDemoProject/src/components/IconDemoApp.java}
 */
// TODO - Add a lot of the "boilerplate" code here into the SlideElement,
// removing any code specific to handling images, and keeping code that sets up
// the handles and the re-sizing. If rotation code is to be added, it should be
// added in SlideElement. The main complication right now to adding stuff to the
// SlideElement object is the resize handles - in order to put the resize handle
// on top of the element being rendered, I have set the LayoutManager to null.
// This causes all sorts of lovely problems. Re-factoring this code and figuring
// out another way to put the handles on the top would 1) probably make this
// code simpler by removing some of the layout maintenance boilerplate and 2)
// make it much easier to shift all of the interesting stuff into SlideELement

// TODO - at some point, adding in a rotation to this would be really nice, and
// adding a respective Property to the SlideElement
public class UnusedImagePanel_OnlyForReference extends JPanel implements
		Draggable {
	// private JLabel topRightHandler_;
	private JLabel bottomRightHandler_;
	// private JLabel topLeftHandler_;
	// private JLabel bottomLeftHandler_;

	private JLabel imageLabel_;
	private ImageIcon image_;

	private static final String Handle_Up_Text = "+";
	private static final String Handle_Pressed_Text = "-";
	private static final int Handle_Size = 10;

	public UnusedImagePanel_OnlyForReference() {
		super();

		// Allows resize handles without an entire JLayeredPane
		setLayout(null);

		setFocusable(true); // TODO - figure out how to actually get focus on
		// these events

		// TODO - actually pull the file they opened
		image_ = new ImageIcon("images/film_add.png");
		imageLabel_ = new JLabel(image_);
		imageLabel_.setSize(image_.getIconWidth(), image_.getIconHeight());

		// Set the bounds, so the container expands and we can correctly place
		// handles
		setBounds(0, 0, image_.getIconWidth(), image_.getIconHeight());

		// Add the handles first, so they display on top of the image
		addHandles();

		// Add the imageLabel
		add(imageLabel_);

		setBackground(Color.RED);
	}

	private void updateLayout() {

		int width = getWidth(), height = getHeight();

		updateHandles();
		imageLabel_.setBounds(0, 0, width, height);

		// TODO - re-read the image from the file before scaling
		image_.setImage(getScaledImage(image_.getImage(), width, height));

		UnusedImagePanel_OnlyForReference.this.repaint();
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
			// Component c = e.getComponent();
			// if (c.equals(bottomRightHandler_))
			bottomRightHandler_.setText(Handle_Pressed_Text);
			// else if (c.equals(bottomLeftHandler_))
			// bottomLeftHandler_.setText(Handle_Pressed_Text);
			// else if (c.equals(topRightHandler_))
			// topRightHandler_.setText(Handle_Pressed_Text);
			// else if (c.equals(topLeftHandler_))
			// topLeftHandler_.setText(Handle_Pressed_Text);
		}

		public void mouseExited(MouseEvent e) {
			// Component c = e.getComponent();
			// if (c.equals(bottomRightHandler_))
			bottomRightHandler_.setText(Handle_Up_Text);
			// else if (c.equals(bottomLeftHandler_))
			// bottomLeftHandler_.setText(Handle_Up_Text);
			// else if (c.equals(topRightHandler_))
			// topRightHandler_.setText(Handle_Up_Text);
			// else if (c.equals(topLeftHandler_))
			// topLeftHandler_.setText(Handle_Up_Text);
		}

		public void mousePressed(MouseEvent e) {
			Component c = e.getComponent();
			System.out.println("Handle Press, size (" + getWidth() + ","
					+ getHeight() + ")");
			motionListener_.setStart(e.getPoint(), getBounds());
			c.addMouseMotionListener(motionListener_);
		}

		public void mouseReleased(MouseEvent e) {
			Component c = e.getComponent();
			System.out.println("Handle Release, size (" + getWidth() + ","
					+ getHeight() + ")");
			c.removeMouseMotionListener(motionListener_);
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

			UnusedImagePanel_OnlyForReference.this.setSize(startBounds_.width
					+ motionHorizontal, startBounds_.height + motionVertical);

			// TODO - there are some bugs here not allowing this
			// updateLayout();
		}

		public void mouseMoved(MouseEvent e) {
		}
	}

	private void updateHandles() {
		bottomRightHandler_.setLocation(this.getWidth() - Handle_Size, this
				.getHeight()
				- Handle_Size);
	}

	private void addHandles() {
		bottomRightHandler_ = new JLabel(Handle_Up_Text);
		bottomRightHandler_.setSize(Handle_Size, Handle_Size);
		bottomRightHandler_.setOpaque(true);
		bottomRightHandler_.setLocation(this.getWidth() - Handle_Size, this
				.getHeight()
				- Handle_Size);
		bottomRightHandler_.addMouseListener(genericListener);
		add(bottomRightHandler_);

		/*
		 * bottomLeftHandler_ = new JLabel(Handle_Up_Text);
		 * bottomLeftHandler_.setSize(Handle_Size, Handle_Size);
		 * bottomLeftHandler_.setOpaque(true); bottomLeftHandler_.setLocation(0,
		 * this.getHeight() - Handle_Size);
		 * bottomLeftHandler_.addMouseListener(genericListener);
		 * add(bottomLeftHandler_);
		 * 
		 * topLeftHandler_ = new JLabel(Handle_Up_Text);
		 * topLeftHandler_.setSize(Handle_Size, Handle_Size);
		 * topLeftHandler_.setOpaque(true); topLeftHandler_.setLocation(0, 0);
		 * topLeftHandler_.addMouseListener(genericListener);
		 * add(topLeftHandler_);
		 * 
		 * topRightHandler_ = new JLabel(Handle_Up_Text);
		 * topRightHandler_.setSize(Handle_Size, Handle_Size);
		 * topRightHandler_.setOpaque(true);
		 * topRightHandler_.setLocation(this.getWidth() - Handle_Size, 0);
		 * topRightHandler_.addMouseListener(genericListener);
		 * add(topRightHandler_);
		 */
	}

	public Component getComponent() {
		return this;
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -2612324265115948486L;
}

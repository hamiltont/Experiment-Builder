
package edu.vanderbilt.psychology;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Card {

	public enum Size {
		SMALL, MEDIUM, MEDIUM_LARGE, LARGE
	}

	public enum Color {
		RED, YELLOW, BLUE, BLACK
	}

	public enum Shape {
		BIRD, DOG, FISH, SNAKE
	}

	private static final int SCALE_FACTOR = 2;

	private Shape shape_;
	private Color color_;
	private Size size_;
	private String imagePath_;
	private ImageIcon icon_;

	public Card(String imagePath) {
		imagePath_ = imagePath;
	}

	public String getImagePath_() {
		return imagePath_;
	}

	public ImageIcon getImage() {
		if (icon_ == null) {
			icon_ = new ImageIcon("images/" + getImagePath_());
			Image srcImg = icon_.getImage();

			int newWidth = icon_.getIconWidth() / SCALE_FACTOR;
			int newHeight = icon_.getIconHeight() / SCALE_FACTOR;

			BufferedImage resizedImg = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = resizedImg.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(srcImg, 0, 0, newWidth, newHeight, null);
			g2.dispose();
			icon_ = new ImageIcon(resizedImg);
		}
		return icon_;
	}

	public Dimension getImagePreferredSize() {
		final Dimension d = new Dimension(icon_.getIconWidth(), icon_
				.getIconHeight());
		return d;
	}

	public void setImagePath_(String image) {
		imagePath_ = image;
	}

	public void setShape_(Shape shape_) {
		this.shape_ = shape_;
	}

	public Shape getShape_() {
		return shape_;
	}

	public void setColor_(Color color_) {
		this.color_ = color_;
	}

	public Color getColor_() {
		return color_;
	}

	public void setSize_(Size size_) {
		this.size_ = size_;
	}

	public Size getSize_() {
		return size_;
	}

	public void prettyPrint() {
		System.out.println("\tColor: " + getColor_());
		System.out.println("\tSize: " + getSize_());
		System.out.println("\tShape: " + getShape_());
	}

	private String me_;

	public String toString() {
		if (me_ == null) {
			StringBuffer sb = new StringBuffer("[");
			sb.append(getSize_());
			sb.append(",");
			sb.append(getColor_());
			sb.append(",");
			sb.append(getShape_());
			sb.append("]");
			me_ = sb.toString();
		}
		return me_;
	}
}

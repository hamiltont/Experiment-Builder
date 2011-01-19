/**
 * 
 */
package edu.vanderbilt.psychology.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.vanderbilt.psychology.gui.sideBar.PreviewPanel;
import edu.vanderbilt.psychology.gui.sideBar.SectionedPanel;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;

/**
 * Adds itself as a mouse click listener on all of the SlideElements, and
 * notifies the {@link SideBar} when the selection changes. Specifically,
 * notifies {@link PreviewPanel} whenever the mouse hovers over a different
 * {@link SlideElement}, and notifies the {@link SectionedPanel} when a
 * different element is clicked
 * 
 * @author Hamilton Turner
 * 
 */
public class SelectionManager implements MouseListener {
	private static SelectionManager instance_;
	private PreviewPanel previewPanel_;
	private SectionedPanel propertyPanel_;

	// We need two b/c they can have different elements in focus
	private SlideElement currentPreviewSelection_;
	private SlideElement currentRealSelection_; 

	private SelectionManager() {
	}

	public SelectionManager(PreviewPanel previewPanel,
			SectionedPanel propertyPanel) {
		instance_ = new SelectionManager();
		instance_.previewPanel_ = previewPanel;
		instance_.propertyPanel_ = propertyPanel;
	}

	public static SelectionManager getInstance() {
		if (instance_ == null)
			throw new RuntimeException("SelectionManager was never created");
		return instance_;
	}

	public void add(SlideElement se) {
		se.addMouseListener(this);
	}

	/** Call when you need to make sure nothing is selected */
	public void clearSelection() {
		previewPanel_.updatePreview(null);
		propertyPanel_.updatePropertiesList(null);
	}

	public void remove(SlideElement se) {
		se.removeMouseListener(this);
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		SlideElement se = (SlideElement) e.getSource();

		if ((currentPreviewSelection_ != null)
				&& (currentPreviewSelection_.equals(se)))
			return;

		currentPreviewSelection_ = se;
		previewPanel_.updatePreview(se);
	}
	
	public SlideElement getRealSelection() {
		return currentRealSelection_;
	}

	public void mouseClicked(MouseEvent e) {
		SlideElement se = (SlideElement) e.getSource();

		if ((currentRealSelection_ != null)
				&& (currentRealSelection_.equals(se)))
			return;

		currentRealSelection_ = se;
		propertyPanel_.updatePropertiesList(se);
	}

}

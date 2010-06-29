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

package edu.vanderbilt.psychology.gui.main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import edu.vanderbilt.psychology.model.BuilderState;

import net.java.swingfx.jdraggable.DefaultDraggableManager;
import net.java.swingfx.jdraggable.DragPolicy;
import net.java.swingfx.jdraggable.Draggable;
import net.java.swingfx.jdraggable.DraggableManager;

/**
 * Entry point for the entire application. MainFrame defines the entire
 * application frame. It contains all other application components, such as the
 * {@link StageWrapper}, {@link SideBar}, {@link ToolBar}, etc.
 * 
 * @author Hamilton Turner
 * 
 * 
 * @see <a
 *      href="http://java.sun.com/docs/books/tutorial/uiswing/components/rootpane.html">The
 *      Java Tutorials on RootPane</a>
 */
// TODO - this should setup the SelectionManager. Also, the SelectionManager
// should be initialized with nothing, and then should have an
// addSelectionListener method to add/remove observers
public class MainFrame extends JFrame {

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Make sure we have nice window decorations.
				JFrame.setDefaultLookAndFeelDecorated(true);

				// Create and set up the window.
				MainFrame frame = new MainFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Display the window.
				frame.setVisible(true);
			}
		});
	}

	@SuppressWarnings("unused")
	private ToolBar toolBar_;
	@SuppressWarnings("unused")
	private SideBar sideBar_;
	@SuppressWarnings("unused")
	private StageWrapper stage_;

	public static final String APP_WINDOW_TITLE = "Experiment Builder";

	/**
	 * Sets the size of the application window. Sets up the stage wrapper as a
	 * {@link DraggableManager} (allowing anything inside of it to become
	 * draggable by simply implementing {@link Draggable}). Sets up the
	 * {@link ToolBar} and the {@link SideBar}. The app window is broken up as
	 * follows:
	 * 
	 * <pre>
	 * --------------------------
	 * |  Toolbar               |
	 * |---------------|--------|
	 * |  Stage        | Side   |
	 * |     Wrapper   |    Bar |
	 * |               |        |
	 * --------------------------
	 * </pre>
	 * 
	 * Also performs initialization of the model used by the builder component.
	 * Does so by setting up the {@link BuilderState}
	 */
	public MainFrame() {
		super(APP_WINDOW_TITLE);

		// Make the big window be indented 50 pixels from each edge
		// of the screen.
		int inset = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height
				- inset * 2);

		setLayout(new GridBagLayout());

		// Stage constraints
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.CENTER;

		// Setup stage as a Draggable Container, allowing us to place Draggable
		// components inside of it
		StageWrapper stageWrapper = new StageWrapper(this);
		DraggableManager manager = new DefaultDraggableManager();
		manager.registerDraggableContainer(stageWrapper);
		manager.setDragPolicy(DragPolicy.STRICT);

		add(stageWrapper, c);

		// Toolbar constraints
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.gridwidth = 2;

		ToolBar toolBar = new ToolBar(stageWrapper);
		toolBar_ = toolBar;
		add(toolBar, c);

		// Sidebar constraints
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 1;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.LINE_END;

		SideBar sideBar = new SideBar(stageWrapper);
		sideBar_ = sideBar;
		add(sideBar, c);

		// Change the title
		setTitle(getTitle() + " - Slide 1 of 1");

		// Initialize the model
		new BuilderState(stageWrapper);

		// TODO Setup left/right arrows on the stage later. For now we have
		// icons in the toolbar for next / previous, which is good enough. If
		// you uncomment this, it will work but apparently the arrows will not
		// change position when the window resizes. The stage resizing as a
		// whole is buggy, so for now im just gonna ignore it and focus on
		// making everything else work
		/*
		 * ImageIcon leftArrow = new ImageIcon("images/left_arrow.png"); JButton
		 * leftArrowBtn = new JButton(leftArrow); desktop.add(leftArrowBtn);
		 * 
		 * ImageIcon rightArrow = new ImageIcon("images/right_arrow.png");
		 * JButton rightArrowBtn = new JButton(rightArrow);
		 * desktop.add(rightArrowBtn);
		 */
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -1261510591458713599L;
}

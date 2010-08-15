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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import net.java.swingfx.jdraggable.DefaultDraggableManager;
import net.java.swingfx.jdraggable.DragPolicy;
import net.java.swingfx.jdraggable.Draggable;
import net.java.swingfx.jdraggable.DraggableManager;
import edu.vanderbilt.psychology.model.BuilderState;

/**
 * <p>
 * Entry point for the entire application. MainFrame defines the layout for the
 * entire application frame. It uses the {@link Builder} to create the four main
 * elements that will go into the {@link MainFrame}, the: sidebar, toolbar,
 * stage, and slide switcher.
 * </p>
 * <p>
 * The application window is defined using a {@link BorderLayout} as follows:<br>
 * 
 * <img src=
 * "../../../../../../doc-source/diagrams/main-frame-border-layout.jpg"
 * alt="Application main BorderLayout" /><br />
 * The toolbar and the slide switcher are essentially allowed to declare the
 * height they wish to be. The sidebar is allowed to declare the width it would
 * like to be. All otehr dimensions automatically stretch to fill all available
 * space.
 * </p>
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

				// TODO Change this once we can fully resize stuff within the
				// StageWrapper
				frame.setResizable(false);

				// Display the window.
				frame.setVisible(true);
			}
		});
	}

	public static final String APP_WINDOW_TITLE = "Experiment Builder";

	/**
	 * Sets the size of the application window. Sets up the stage wrapper as a
	 * {@link DraggableManager} (allowing anything inside of it to become
	 * draggable by simply implementing {@link Draggable}). Sets up the
	 * {@link ToolBar} and the {@link SideBar}.
	 * 
	 * <p>
	 * The application window is defined using a {@link BorderLayout} as
	 * follows:<br>
	 * 
	 * <img src=
	 * "../../../../../../doc-source/diagrams/main-frame-border-layout.jpg"
	 * alt="Application main BorderLayout" /><br />
	 * The toolbar and the slide switcher are essentially allowed to declare the
	 * height they wish to be. The sidebar is allowed to declare the width it
	 * would like to be. All otehr dimensions automatically stretch to fill all
	 * available space.
	 * </p>
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

		setLayout(new BorderLayout());

		// Setup stage as a Draggable Container, allowing us to place Draggable
		// components inside of it
		StageWrapper stageWrapper = new StageWrapper(this);
		DraggableManager manager = new DefaultDraggableManager();
		manager.registerDraggableContainer(stageWrapper);
		manager.setDragPolicy(DragPolicy.STRICT);

		JToolBar tb = Builder.buildToolBar(stageWrapper);
		add(tb, BorderLayout.NORTH);

		JPanel sidebar = Builder.buildSideBar(stageWrapper);
		add(sidebar, BorderLayout.EAST);

		JPanel slideSwitcher = Builder.buildSlideSwitcher();
		add(slideSwitcher, BorderLayout.SOUTH);

		add(stageWrapper, BorderLayout.CENTER);

		// Initialize the model
		new BuilderState(stageWrapper);
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -1261510591458713599L;
}

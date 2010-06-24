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
package edu.vanderbilt.psychology.controller.toolbarActions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.filechooser.FileFilter;

import sun.awt.image.ImageFormatException;
import edu.vanderbilt.psychology.gui.main.MainStageWrapper;
import edu.vanderbilt.psychology.gui.slideElements.ImageElement;

/**
 * @author Hamilton Turner
 * 
 * @TODO check out {@link http
 *       ://java.sun.com/docs/books/tutorial/uiswing/examples
 *       /components/index.html#FileChooserDemo2} and perhaps show image file
 *       previews
 * 
 */
public class AddImageAction extends AbstractAction {

	private MainStageWrapper stage_;

	public AddImageAction(MainStageWrapper stage) {
		super("Add Image");
		stage_ = stage;
	}

	public void actionPerformed(ActionEvent e) {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new ImageFilter());

		int returnVal = fc.showOpenDialog(stage_);

		switch (returnVal) {
		case JFileChooser.APPROVE_OPTION:
			approve(fc.getSelectedFile());
			break;
		case JFileChooser.ERROR_OPTION:
			error(null);
			break;
		default:
		case JFileChooser.CANCEL_OPTION:
			break;
		}
	}

	private void approve(File file) {
		ImageElement ie;
		try {
			ie = new ImageElement(file);
		} catch (ImageFormatException e) {
			error(e);
			return;
		}

		stage_.add(ie, JLayeredPane.PALETTE_LAYER); // TODO -
		// decide
		// which layer
		// we would like
		// to be adding stuff on here

	}

	private void error(ImageFormatException e) {
		if (e == null) {
			// TODO Report Unknown error

		} else {
			// TODO report e.getMessage()
			e.printStackTrace();
		}
	}

	private class ImageFilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}

			String extension = Utils.getExtension(f);

			if (extension != null) {
				if (extension.equals(Utils.tiff) || extension.equals(Utils.tif)
						|| extension.equals(Utils.gif)
						|| extension.equals(Utils.jpeg)
						|| extension.equals(Utils.jpg)
						|| extension.equals(Utils.png)) {
					return true;
				} else {
					return false;
				}
			}

			return false;
		}

		@Override
		public String getDescription() {
			return "Supported Images";
		}

	}
	private static final long serialVersionUID = -5791447787901655445L;
}

/**
 * 
 */
package edu.vanderbilt.psychology.gui.main;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import edu.vanderbilt.psychology.controller.toolbarActions.AddContainerAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddImageAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddSoundAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddTextAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddVideoAction;
import edu.vanderbilt.psychology.controller.toolbarActions.ExportExperimentAction;
import edu.vanderbilt.psychology.controller.toolbarActions.NextSlideAction;

/**
 * @author Hamilton Turner
 * 
 */
public class MainToolBar extends JToolBar {

	public final static int TOOLBAR_HEIGHT = 70;
	public final static int TOOLBAR_FONT_SIZE = 12;

	public MainToolBar(MainStageWrapper stage) {
		super();

		FlowLayout toolbarLayout = new FlowLayout(FlowLayout.LEFT, 10, 5);
		setLayout(toolbarLayout);

		// Create Actions and add JButtons
		AddImageAction addImageAction = new AddImageAction(stage);
		JButton addImageBtn = add(addImageAction);
		addImageBtn.setToolTipText("Add Image");
		addImageBtn.setText("Image");
		addImageBtn
				.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, TOOLBAR_FONT_SIZE));
		addImageBtn.setBorderPainted(false);
		addImageBtn.setOpaque(false);
		ImageIcon addImageIcon = new ImageIcon("images/picture_add.png");
		addImageBtn.setIcon(addImageIcon);
		add(addImageBtn);

		AddVideoAction addVideoAction = new AddVideoAction(stage);
		JButton addVideoBtn = add(addVideoAction);
		addVideoBtn.setToolTipText("Add Video");
		addVideoBtn.setText("Video");
		addVideoBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,
				TOOLBAR_FONT_SIZE));
		addVideoBtn.setBorderPainted(false);
		addVideoBtn.setOpaque(false);
		ImageIcon addVideoIcon = new ImageIcon("images/film_add.png");
		addVideoBtn.setIcon(addVideoIcon);
		add(addVideoBtn);

		AddSoundAction addSoundAction = new AddSoundAction(stage);
		JButton addSoundBtn = add(addSoundAction);
		addSoundBtn.setToolTipText("Add Sound");
		addSoundBtn.setText("Sound");
		addSoundBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,
				TOOLBAR_FONT_SIZE));
		addSoundBtn.setBorderPainted(false);
		addSoundBtn.setOpaque(false);
		ImageIcon addSoundIcon = new ImageIcon("images/sound_add.png");
		addSoundBtn.setIcon(addSoundIcon);
		add(addSoundBtn);

		AddTextAction addTextAction = new AddTextAction(stage);
		JButton addTextBtn = add(addTextAction);
		addTextBtn.setToolTipText("Add Text");
		addTextBtn.setText("Text");
		addTextBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, TOOLBAR_FONT_SIZE));
		addTextBtn.setBorderPainted(false);
		addTextBtn.setOpaque(false);
		ImageIcon addTextIcon = new ImageIcon("images/font_add.png");
		addTextBtn.setIcon(addTextIcon);
		add(addTextBtn);

		AddContainerAction addContainerAction = new AddContainerAction(stage);
		JButton addContainerBtn = add(addContainerAction);
		addContainerBtn.setToolTipText("Add Container");
		addContainerBtn.setText("Container");
		addContainerBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,
				TOOLBAR_FONT_SIZE));
		addContainerBtn.setBorderPainted(false);
		addContainerBtn.setOpaque(false);
		ImageIcon addContainerIcon = new ImageIcon("images/container_add.png");
		addContainerBtn.setIcon(addContainerIcon);
		add(addContainerBtn);

		NextSlideAction nextSlideAction = new NextSlideAction(stage);
		JButton nextSlideBtn = add(nextSlideAction);
		nextSlideBtn.setToolTipText("Next Slide");
		nextSlideBtn.setText("Next Slide");
		nextSlideBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,
				TOOLBAR_FONT_SIZE));
		nextSlideBtn.setBorderPainted(false);
		nextSlideBtn.setOpaque(false);
		ImageIcon nextSlideIcon = new ImageIcon("images/next_slide.png");
		nextSlideBtn.setIcon(nextSlideIcon);
		add(nextSlideBtn);

		ExportExperimentAction exportAction = new ExportExperimentAction(stage);
		JButton exportBtn = add(exportAction);
		exportBtn.setToolTipText("Export");
		exportBtn.setText("Export");
		exportBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,
				TOOLBAR_FONT_SIZE));
		exportBtn.setBorderPainted(false);
		exportBtn.setOpaque(false);
		ImageIcon exportIcon = new ImageIcon("images/export_icon.png");
		exportBtn.setIcon(exportIcon);
		add(exportBtn);
		
		
		// TODO Add Record Action

		setBounds(0, 0, stage.getWidth(), TOOLBAR_HEIGHT);
		setVisible(true);
		setFloatable(false);

		stage.addComponentListener(new ComponentListener() {

			public void componentResized(ComponentEvent e) {
				MainToolBar.this.setBounds(0, 0, e.getComponent().getWidth(),
						TOOLBAR_HEIGHT);
			}

			public void componentHidden(ComponentEvent e) {
			}

			public void componentMoved(ComponentEvent e) {
				MainToolBar.this.setBounds(0, 0, e.getComponent().getWidth(),
						TOOLBAR_HEIGHT);
			}

			public void componentShown(ComponentEvent e) {
			}
		});
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -263912033533453658L;
}

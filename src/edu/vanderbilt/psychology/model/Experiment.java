/**
 * 
 */
package edu.vanderbilt.psychology.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import edu.vanderbilt.psychology.model.elements.ImageElementModel;
import edu.vanderbilt.psychology.model.elements.TextElementModel;
import edu.vanderbilt.psychology.model.properties.Appearance;
import edu.vanderbilt.psychology.model.properties.DataSource;
import edu.vanderbilt.psychology.model.properties.Movement;
import edu.vanderbilt.psychology.model.properties.Position;

/**
 * <p>
 * An {@link Experiment} is a storage container for all data needed to play a
 * psychology experiment. When saving a psychology experiment to a file, all
 * required data is packed into an {@link Experiment} and then the
 * {@link Experiment} is serialized to disk.
 * </p>
 * 
 * <p>
 * At the time of this writing (SVN version 35) the speed of saving an
 * {@link Experiment} to disk is not a priority requirement. An
 * {@link Experiment} is simply serialized to XML using the {@link XStream}
 * package.
 * </p>
 * 
 * @author Hamilton Turner
 * 
 */
public class Experiment {
	private List<Slide> slides_ = new ArrayList<Slide>();

	// TODO - make this save a slide object permanently
	public void saveSlide(Slide s) {
		slides_.add(s);
		System.out.println("Saved a slide!");
	}

	public void saveExperiment() {
		XStream xs = new XStream();

		xs.alias("Experiment", Experiment.class);
		xs.alias("Slide", Slide.class);
		xs.alias("ImageElement", ImageElementModel.class);
		xs.alias("TextElement", TextElementModel.class);
		xs.alias("DataSource", DataSource.class);
		xs.alias("Appearance", Appearance.class);
		xs.alias("Position", Position.class);
		xs.alias("Movement", Movement.class);

		try {
			FileWriter fw = new FileWriter("test.xml");
			fw.write(xs.toXML(this));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Exported!");
		System.exit(0);
	}
}

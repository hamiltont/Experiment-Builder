/**
 * Contains all of the user interface components.
 * 
 * A main limitation of the gui components currently is that they are tied
 * to a specific aspect ratio. This will likely continue for a while. This
 * shows up when saving slides to the model (as you have to save the 
 * position of SlideElements), re-writing slides to the model, playing an
 * Experiment, etc. If the aspect ratio on the computer playing the 
 * Experiment is incorrect, then the Experiment will be framed by a solid
 * color. TODO - allow researchers to pick that color   
 * 
 * @author Hamilton Turner
 */
package edu.vanderbilt.psychology.gui;
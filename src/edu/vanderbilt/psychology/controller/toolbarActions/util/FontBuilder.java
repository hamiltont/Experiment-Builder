package edu.vanderbilt.psychology.controller.toolbarActions.util;

import java.awt.Font;


/*
 * Copyright (C) 2001-2003 Colin Bell colbell@users.sourceforge.net
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

class FontBuilder {

	private static final String DEFAULT_FAMILY = "Monospaced";

	protected static String family = DEFAULT_FAMILY;
	protected static boolean bold = false;
	protected static boolean italic = false;
	protected static int size = 12;

	private static int generateStyle() {
		int style = Font.PLAIN;

		if (bold)
			style |= Font.BOLD;

		if (italic)
			style |= Font.ITALIC;

		return style;
	}

	public static Font createFont() {
		return new Font(family, generateStyle(), size);
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("[");
		buf.append(FontBuilder.class.getCanonicalName()).append(": ");

		buf.append(family);
		buf.append(", ").append(size).append("pt");
		buf.append(", bold=").append(bold);
		buf.append(", italic=").append(italic);
		buf.append("]");
		
		return buf.toString();
	}
}

/*
 *  Copyright (C) 2002-2005  The Jmol Development Team
 *
 *  Contact: jmol-developers@lists.sf.net
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *  All we ask is that proper credit is given for our work, which includes
 *  - but is not limited to - adding the above copyright notice to the beginning
 *  of your source code files, and to any copyright notice that you may distribute
 *  with programs based on this work.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.openscience.cdk.applications.jchempaint.applet;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JScrollPane;

import org.openscience.cdk.applications.jchempaint.JChemPaintViewerOnlyPanel;

/**
 * The
 * 
 * @cdk.module jchempaint.applet
 * @author dirk49
 * @created 04. Mai 2005
 */
public class JChemPaintViewerOnlyApplet extends JChemPaintAbstractApplet {

	/**
	 * ugly method to remove scrollbars from the applet
	 * @param comp
	 */
	private void removeScrollBars(Container theCont) {
		Component comps[] = theCont.getComponents();
		for (int i=0; i < comps.length; i++) {
			if (theCont.getComponent(i) instanceof JScrollPane) {
				JScrollPane theJSP = (JScrollPane) theCont.getComponent(i);
				theJSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				theJSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			}
			if (theCont.getComponent(i) instanceof Container)
				removeScrollBars((Container) theCont.getComponent(i));
		}
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	public void init() {
		JChemPaintViewerOnlyPanel jcpvop = new JChemPaintViewerOnlyPanel();
		jcpvop.setShowMenuBar(false);
		jcpvop.setShowStatusBar(false);
		jcpvop.setShowToolBar(false);
		// ugly ...
		removeScrollBars(jcpvop);
		Component comps[] = jcpvop.getComponents();
		setTheJcpp(jcpvop);
	}
	
	/* (non-Javadoc)
	 * @see java.applet.Applet#start()
	 */
	public void start() {
		super.start();
	}
}

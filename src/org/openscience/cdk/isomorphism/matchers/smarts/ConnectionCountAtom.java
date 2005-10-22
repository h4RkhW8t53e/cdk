/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 * 
 * Copyright (C) 2004-2005  The Chemistry Development Kit (CDK) project
 *
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.isomorphism.matchers.smarts;

import org.openscience.cdk.interfaces.Atom;

/**
 * This matcher checks the total valency of the Atom.
 * This cannot be matched with a unpreprocessed Atom!
 *
 * @cdk.module extra
 */
public class ConnectionCountAtom extends SMARTSAtom {
    
    private int count;
    
    public ConnectionCountAtom(int count) {
        this.count = count;
    }
    
	public boolean matches(Atom atom) {
        int count = ((Integer)atom.getProperty("org.openscience.cdk.Atom.connectionCount")).intValue();
        return (count == this.count);
    };

    public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("ConnectionCountAtom(");
        s.append(this.hashCode() + ", ");
		s.append("CC:" + count);
		s.append(")");
		return s.toString();
    }
}


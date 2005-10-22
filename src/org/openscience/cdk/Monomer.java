/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 1997-2005  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *  */
package org.openscience.cdk;


/**
 * A Monomer is an AtomContainer which stores additional monomer specific 
 * informations for a group of Atoms.
 *
 * @cdk.module data
 *
 * @author     Edgar Luttmann <edgar@uni-paderborn.de>
 * @cdk.created    2001-08-06 
 *
 * @cdk.keyword    monomer
 *
 */
public class Monomer extends AtomContainer implements java.io.Serializable, org.openscience.cdk.interfaces.Monomer
{

    /**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is imcompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = -6084164963937650703L;

	/** The name of this monomer (e.g. Trp42). */
    private String monomerName;
    /** The type of this monomer (e.g. TRP). */
    private String monomerType;

	/**
	 *
	 * Contructs a new Monomer.
	 *
	 */	
	public Monomer () {
		super();
	}
	
	/**
	 *
	 * Retrieve the monomer name.
	 *
	 * @return The name of the Monomer object
	 *
     * @see    #setMonomerName
	 */
	public String getMonomerName() {
		return monomerName;
	}

	/**
	 *
	 * Retrieve the monomer type.
	 *
	 * @return The type of the Monomer object
	 *
     * @see    #setMonomerType
	 */
	public String getMonomerType() {
		return monomerType;
	}
	
	/**
	 *
	 * Set the name of the Monomer object.
	 *
	 * @param cMonomerName  The new name for this monomer
	 *
     * @see    #getMonomerName
	 */
	public void setMonomerName(String cMonomerName) {
		monomerName = cMonomerName;
		notifyChanged();
	}
	
	/**
	 *
	 * Set the type of the Monomer object.
	 *
	 * @param cMonomerType  The new type for this monomer
	 *
     * @see    #getMonomerType
	 */
	public void setMonomerType(String cMonomerType) {
		monomerType = cMonomerType;
		notifyChanged();
	}

    /**
     * Clones this Monomer object.
     *
     * @return    The cloned object
     */
    public Object clone() {
        Monomer clone = null;
        try {
            clone = (Monomer) super.clone();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return clone;
    }

}

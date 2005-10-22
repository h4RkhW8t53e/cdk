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
package org.openscience.cdk;

/** 
 * Class representing a ring structure in a molecule.
 * A ring is a linear sequence of
 * N atoms interconnected to each other by covalent bonds,
 * such that atom i (1 < i < N) is bonded to
 * atom i-1 and atom i + 1 and atom 1 is bonded to atom N and atom 2.
 *
 * @cdk.module data
 *
 * @cdk.keyword    ring
 */
public class Ring extends AtomContainer implements java.io.Serializable, org.openscience.cdk.interfaces.Ring
{

	/**
     * Determines if a de-serialized object is compatible with this class.
     *
     * This value must only be changed if and only if the new version
     * of this class is imcompatible with the old version. See Sun docs
     * for <a href=http://java.sun.com/products/jdk/1.1/docs/guide
     * /serialization/spec/version.doc.html>details</a>.
	 */
	private static final long serialVersionUID = 6604894792331865990L;

	/**
	 * Constructs an empty ring.
	 *
	 */
	public Ring() {
		super();
	}
	
	public Ring(org.openscience.cdk.interfaces.AtomContainer ac)
	{
		super(ac);
	}
	
	/**
	 * Constructs a ring that will have a certain number of atoms of the given elements.
	 *
	 * @param   ringSize   The number of atoms and bonds the ring will have
	 * @param   elementSymbol   The element of the atoms the ring will have
	 */
	public Ring(int ringSize, String elementSymbol) {
		this(ringSize);
		Atom[] atoms = new Atom[ringSize];
		atoms[0] = new Atom(elementSymbol);
		for (int i = 1; i < ringSize; i++) {
			atoms[i] = new Atom(elementSymbol);
			addElectronContainer(new Bond(atoms[i - 1], atoms[i], 1));
		}
		addElectronContainer(new Bond(atoms[ringSize - 1], atoms[0], 1));
		setAtoms(atoms);
	}
	
		
	/**
	 * Constructs an empty ring that will have a certain size.
	 *
	 * @param   ringSize  The size (number of atoms) the ring will have
	 */

	public Ring(int ringSize) {
		super(ringSize, ringSize);
	}
	

	/**
	 * Returns the number of atoms\edges in this ring.
	 *
	 * @return   The number of atoms\edges in this ring   
	 */

	public int getRingSize() {
		return this.atomCount;
	}
	

	/**
	 * Returns the next bond in order, relative to a given bond and atom.
	 * Example: Let the ring be composed of 0-1, 1-2, 2-3 and 3-0. A request getNextBond(1-2, 2)
	 * will return Bond 2-3.
	 *
	 * @param   bond  A bond for which an atom from a consecutive bond is sought
	 * @param   atom  A atom from the bond above to assign a search direction
	 * @return  The next bond in the order given by the above assignment   
	 */
	public org.openscience.cdk.interfaces.Bond getNextBond(org.openscience.cdk.interfaces.Bond bond, org.openscience.cdk.interfaces.Atom atom)
	{
		Bond tempBond;
		for (int f = 0; f < getElectronContainerCount(); f++) {
			org.openscience.cdk.interfaces.ElectronContainer electronContainer = getElectronContainerAt(f);
            if (electronContainer instanceof org.openscience.cdk.interfaces.Bond) {
                tempBond = (Bond)electronContainer;
                if (tempBond.contains(atom) && bond != tempBond) {
                    return tempBond;
                }
            }
		}
		return null;
	}

	/**
	 * Returns the sum of all bond orders in the ring.
	 *
	 * @return the sum of all bond orders in the ring
	 */
	public int getOrderSum()
	{
		int orderSum = 0;
		Bond tempBond;
		for (int i = 0; i < getElectronContainerCount(); i++) {
			org.openscience.cdk.interfaces.ElectronContainer electronContainer = getElectronContainerAt(i);
            if (electronContainer instanceof org.openscience.cdk.interfaces.Bond) {
                tempBond = (Bond)electronContainer;
                orderSum += tempBond.getOrder();
            }
 		}
		return orderSum;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Ring(");
		buffer.append(super.toString());
		buffer.append(")");
		return buffer.toString();
	}

}

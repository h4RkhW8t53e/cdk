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
 * 
 */
package org.openscience.cdk.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.Locale;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.ChemObject;
import org.openscience.cdk.Reaction;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.io.formats.ChemFormat;
import org.openscience.cdk.io.formats.MDLFormat;
import org.openscience.cdk.tools.LoggingTool;



/**
 * Writes a reaction to a MDL rxn or SDF file. Attention: Stoichiometric
 * coefficients have to be natural numbers.
 *
 * <pre>
 * MDLRXNWriter writer = new MDLRXNWriter(new FileWriter(new File("output.mol")));
 * writer.write((Molecule)molecule);
 * writer.close();
 * </pre>
 *
 * See {@cdk.cite DAL92}.
 *
 * @cdk.module io
 *
 * @cdk.keyword file format, MDL RXN file
 */
public class MDLRXNWriter extends DefaultChemObjectWriter {

    private BufferedWriter writer;
    private LoggingTool logger;

    /**
     * Contructs a new MDLWriter that can write an array of
     * Molecules to a given OutputStream.
     *
     * @param   out  The OutputStream to write to
     */
    public MDLRXNWriter(OutputStream out) throws Exception {
        this(new BufferedWriter(new OutputStreamWriter(out)));
    }

    public ChemFormat getFormat() {
        return new MDLFormat();
    }
    
    /**
     * Contructs a new MDLWriter that can write an array of 
     * Molecules to a Writer.
     *
     * @param   out  The Writer to write to
     */
    public MDLRXNWriter(Writer out) throws Exception {
        writer = new BufferedWriter(out);
        logger = new LoggingTool(this);
    }

    /**
     * Flushes the output and closes this object.
     */
    public void close() throws IOException {
        writer.close();
    }

    /**
     * Writes a ChemObject to the MDL RXN file formated output. 
     * It can only output ChemObjects of type Reaction
     *
     * @param object class must be of type Molecule or SetOfMolecules.
     *
     * @see org.openscience.cdk.ChemFile
     */
	public void write(ChemObject object) throws CDKException
	{
		if (object instanceof Reaction)
		{
		    writeReaction((Reaction)object);
		}
		else
		{
		    throw new CDKException("Only supported is writing Reaction objects.");
		}
	}
	
	/**
	 * Writes a Reaction to an OutputStream in MDL sdf format.
	 *
	 * @param   reaction  A Reaction that is written to an OutputStream 
	 */
	private void writeReaction(Reaction reaction) throws CDKException
	{
		int reactantCount = reaction.getReactantCount();
        int productCount = reaction.getProductCount();
        if (reactantCount <= 0 || productCount <= 0) {
            System.out.println("HUHU1");
            throw new CDKException("Either no reactants or no products present.");
        }
        
        try {
            writer.write("$RXN\n");
            // reaction name
            String line = (String)reaction.getProperty(CDKConstants.TITLE);
            if(line == null) line = "";
            if(line.length() > 80) line = line.substring(0,80);
            writer.write(line + "\n");
            // user/program/date&time/reaction registry no. line
            writer.newLine();
            // comment line
            line = (String)reaction.getProperty(CDKConstants.REMARK);
            if(line == null) line = "";
            if(line.length() > 80) line = line.substring(0,80);
            writer.write(line + "\n");
            
            line = "";
            line += formatMDLInt(reactantCount, 3);
            line += formatMDLInt(productCount, 3);
            writer.write(line + "\n");
            
            writeSetOfMolecules(reaction.getReactants());
            writeSetOfMolecules(reaction.getProducts());
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            logger.debug(ex);
            System.out.println("HUHU2");
            throw new CDKException("Exception while writing MDL file: " + ex.getMessage());
        }
	}
	
    /**
	 * Writes a SetOfMolecules to an OutputStream for the reaction.
	 *
	 * @param   som  The SetOfMolecules that is written to an OutputStream 
	 */
	private void writeSetOfMolecules(org.openscience.cdk.interfaces.SetOfMolecules som) throws IOException, CDKException {
        
        for (int i = 0; i < som.getMoleculeCount(); i++) {
        	org.openscience.cdk.interfaces.Molecule mol = som.getMolecule(i);
            for (int j = 0; j < som.getMultiplier(i); j++) {
                StringWriter sw = new StringWriter();
                writer.write("$MOL\n");
                MDLWriter mdlwriter = null;
                try {
                    mdlwriter = new MDLWriter(sw);
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    logger.debug(ex);
                    throw new CDKException("Exception while creating MDLWriter: " + ex.getMessage());
                }
                mdlwriter.write(mol);
                writer.write(sw.toString());
            }
        }
    }
    
    
	/**
	 * Formats an int to fit into the connectiontable and changes it 
     * to a String.
	 *
	 * @param   i  The int to be formated
	 * @param   l  Length of the String
	 * @return     The String to be written into the connectiontable
	 */
    private String formatMDLInt(int i, int l) {
        String s = "", fs = "";
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        nf.setParseIntegerOnly(true);
        nf.setMinimumIntegerDigits(1);
        nf.setMaximumIntegerDigits(l);
        nf.setGroupingUsed(false);
        s = nf.format(i);
        l = l - s.length();
        for (int f = 0; f < l; f++)
            fs += " ";
        fs += s;
        return fs;
    }
	
	

}



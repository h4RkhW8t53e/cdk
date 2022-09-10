/*
 * Copyright (c) 2013 European Bioinformatics Institute (EMBL-EBI)
 *                    John May <jwmay@users.sf.net>
 *               2022 John Mayfield (né May)
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version. All we ask is that proper credit is given
 * for our work, which includes - but is not limited to - adding the above
 * copyright notice to the beginning of your source code files, and to any
 * copyright notice that you may distribute with programs based on this work.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 U
 */

package org.openscience.cdk.isomorphism;

import java.util.BitSet;
import java.util.function.Predicate;

/**
 * A predicate for filtering atom-mapping results.
 *
 * <pre>{@code
 * [0, 1, 2]  => accept
 * [0, 1, 3]  => accept
 * [2, 1, 3]  => reject (no new mappings)
 * </pre>
 *
 * This class is intended for use with {@link Pattern}.
 *
 * <blockquote><pre>{@code
 *     Pattern     pattern = Ullmann.findSubstructure(query);
 *     List<int[]> unique  = FluentIterable.of(patter.matchAll(target))
 *                                         .filter(new UniqueAtomMatches())
 *                                         .toList();
 * }</pre></blockquote>
 *
 * @author John Mayfield
 * @cdk.module isomorphism
 */
final class UniqueAtomMatches implements Predicate<int[]> {

    /**
     * Which atoms have we seen in a mapping already.
     */
    private final BitSet visit = new BitSet();

    /**
     * Create filter for unique matches.
     */
    public UniqueAtomMatches() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean test(int[] mapping) {
        if (some(mapping))
            return add(mapping);
        return false;
    }

    // If some (at least one) has not been seen yet
    boolean some(int[] mapping) {
        for (int atomIdx : mapping)
            if (!this.visit.get(atomIdx))
                return true;
        return false;
    }

    boolean add(int[] mapping) {
        for (int atomIdx : mapping)
            this.visit.set(atomIdx);
        return true;
    }

    /**
     * Backwards compatible method from when we used GUAVA predicates.
     *
     * @param ints atom index bijection
     * @return true/false
     * @see #test(int[])
     */
    public boolean apply(int[] ints) {
        return test(ints);
    }
}

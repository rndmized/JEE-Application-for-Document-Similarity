package ie.gmit.sw;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Contains Calculations for Jaccard Index
 * 
 * @author RnDMizeD
 * @version 1.0b
 */
public class JaccardComputation {

	/**
	 * Given two list of integers, returns the calcualtion of the similatity index
	 * for those lists.
	 * 
	 * @param m1
	 *            List of Integers
	 * @param m2
	 *            List of Integers
	 * @return Jaccard Similarity Index as double
	 */
	public double computeJaccard(final List<Integer> m1, final List<Integer> m2) {

		// Create set and add both lists to it
		Set<Integer> union = new HashSet<Integer>();
		union.addAll(m1);
		union.addAll(m2);

		// calculate intersection
		int intersection = m1.size() + m2.size() - union.size();

		// return index (1.0 * intersection / union.size()) * 100 (for percentage)
		return (1.0 * intersection / union.size()) * 100;
	}
}

package ie.gmit.sw;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JaccardComputation {

	
	public double computeJaccard(final List<Integer> m1, final List<Integer> m2) {
//		if (m1.equals(m2)) {
//            return 1.0;
//		}
		
		Set<Integer> union = new HashSet<Integer>();
		union.addAll(m1);
		union.addAll(m2);
		
		int intersection = m1.size() + m2.size()
				- union.size();
		//**********************  TESTING   ********************************//
//		System.out.println("union: " + union.size());
//		System.out.println("m1: " + m1.size());
//		System.out.println("m2: " + m2.size());
//		System.out.println("Intersection: " + intersection);
		
		 return (1.0 * intersection / union.size())*100;
	}
}

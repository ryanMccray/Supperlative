package Supperlative;

import java.math.*;

public class SupplierNode {
	
	String name;
	int x;
	int y;
	int perish;
	int nonperish;

	public SupplierNode(String title, int perish1, int nonperish1, int x1, int y1) {
		name = title;
		x = x1;
		y = y1;
		perish = perish1;
		nonperish = nonperish1;
		
	}
	
	public double SupplierDistance(int x1, int y1) {
		
		return Math.sqrt((double)( ((x-x1)*(x-x1))+((y-y1)*(y-y1))));
	}
}

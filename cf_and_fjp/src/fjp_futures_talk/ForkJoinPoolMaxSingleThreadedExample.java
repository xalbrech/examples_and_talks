/**
 * ---Begin Copyright Notice---
 *
 * NOTICE
 *
 * THIS SOFTWARE IS THE PROPERTY OF AND CONTAINS CONFIDENTIAL INFORMATION OF
 * INFOR AND/OR ITS AFFILIATES OR SUBSIDIARIES AND SHALL NOT BE DISCLOSED
 * WITHOUT PRIOR WRITTEN PERMISSION. LICENSED CUSTOMERS MAY COPY AND ADAPT
 * THIS SOFTWARE FOR THEIR OWN USE IN ACCORDANCE WITH THE TERMS OF THEIR
 * SOFTWARE LICENSE AGREEMENT. ALL OTHER RIGHTS RESERVED.
 *  
 * (c) COPYRIGHT 2016 INFOR. ALL RIGHTS RESERVED. THE WORD AND DESIGN MARKS
 * SET FORTH HEREIN ARE TRADEMARKS AND/OR REGISTERED TRADEMARKS OF INFOR
 * AND/OR ITS AFFILIATES AND SUBSIDIARIES. ALL RIGHTS RESERVED. ALL OTHER
 * TRADEMARKS LISTED HEREIN ARE THE PROPERTY OF THEIR RESPECTIVE OWNERS.
 *  
 * ---End Copyright Notice---
 */
package fjp_futures_talk;

/**
 * Search for a maximum value in a tree. Single-threaded version, for reference purposes only. 
 *  
 */
public class ForkJoinPoolMaxSingleThreadedExample {
	
	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) { }
	}
	
	private interface Node {
		public void accept(Visitor v);
	}
	
	private interface Visitor {
		void visitLeaf(Leaf l);
		void visitBranch(Branch b);
	}
	
	private static class Leaf implements Node {
		private double value;

		public Leaf(double value) {
			super();
			this.value = value;
		}
		
		double getValue() {
			sleep(100);
			return value;
		}

		@Override
		public void accept(Visitor v) {
			v.visitLeaf(this);
		}
		
	}
	
	private static class Branch implements Node {

		Node leftNode, rightNode;

		public Branch(Node leftNode, Node rightNode) {
			super();
			this.leftNode = leftNode;
			this.rightNode = rightNode;
		}

		@Override
		public void accept(Visitor v) {
			v.visitBranch(this);
		}
		
	}
	
	private static class MaxVisitor implements Visitor {
		double max = Double.NEGATIVE_INFINITY;
		
		@Override
		public void visitLeaf(Leaf l) {
			this.max = Math.max(max, l.getValue());
		}

		
		@Override
		public void visitBranch(Branch b) {
			b.leftNode.accept(this);
			b.rightNode.accept(this);			
		}

		public double getMax() {
			return max;
		}
	}
	
	public static void main(String[] args) {
		
		Node theTreeRoot = new Branch(  
				new Branch(new Leaf(1D), 
						new Branch(new Leaf(100D), 
								new Branch(new Leaf(200D), new Leaf(200.3D)))),
				new Branch(new Leaf(3D), 
						new Branch(new Leaf(4D), 
								new Branch(new Leaf(300D), 
										new Branch(new Leaf(351D), new Leaf(490.8D))))
						)
				);		
		
		long start = System.nanoTime();
		
		MaxVisitor v = new MaxVisitor();
		theTreeRoot.accept(v);
		
		System.out.printf("The maximum is: %f\n", v.getMax());		
		System.out.printf("Single threaded search completed in: %d\n", (System.nanoTime() - start) / 1_000_000);
	}

}

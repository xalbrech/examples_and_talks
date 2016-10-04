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

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicReference;

import javax.naming.NoPermissionException;

/**
 * Search for a maximum value in a tree. 
 * Asynchronous version that uses FJ tasks and fork/join to parallelize the processing.
 *
 */
public class ForkJoinPoolMaxFJPoolExample {
	
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

		AtomicReference<Double> max = new AtomicReference<>(Double.NEGATIVE_INFINITY);
		
		@Override
		public void visitLeaf(Leaf l) {	
			double value = l.getValue();	
			synchronized (this) {
				max.set(Math.max(max.get(), value));
			}	
		}
		
		@Override
		public void visitBranch(Branch b) {
			RecursiveAction leftAction = new RecursiveAction() {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				protected void compute() {
					b.leftNode.accept(MaxVisitor.this);					
				}
			};
			leftAction.fork();
			
			RecursiveAction rightAction = new RecursiveAction() {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				protected void compute() {
					b.rightNode.accept(MaxVisitor.this);	
				}
			};			
			rightAction.fork();
			
			leftAction.join();
			rightAction.join();
		}

		public double getMax() {
			return max.get();
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
		
		ForkJoinPool pool = ForkJoinPool.commonPool();
		
		MaxVisitor v = new MaxVisitor();
		
		pool.submit(new RecursiveAction() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void compute() {
				theTreeRoot.accept(v);				
			}
			
		}).join();

		System.out.printf("The maximum is: %f\n", v.getMax());		
		System.out.printf("Search with FJ Pool in: %d ms, FJP size: %d\n", (System.nanoTime() - start) / 1_000_000, pool.getPoolSize());
	}

}

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

import static org.junit.Assert.assertEquals;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import org.junit.Test;

/**
 * @author dalbrecht
 *
 */
public class CompletableFutureExamples {
	
	// Basic uses
	
	@Test public void showThenApply() {
		
		CompletableFuture<Integer> cf = CompletableFuture
											.supplyAsync(() -> 2)
											.thenApply( n -> n * 3);
		assertEquals(6, cf.join().intValue());
		
	}
	
	@Test public void showThenCombine() {
		
		CompletableFuture<Integer> cf = CompletableFuture
				.supplyAsync(() -> 2)
				.thenCombine( CompletableFuture
									.supplyAsync(() -> 3),						
							(n1, n2) -> n1 * n2);
		
		assertEquals(6, cf.join().intValue());
		
	}	
	
	@Test public void showThenCompose() {
		
		CompletableFuture<Integer> cf = CompletableFuture
				.supplyAsync(() -> 2)
				.thenCompose( n -> CompletableFuture.supplyAsync(() -> n * 3));			
							
		
		assertEquals(6, cf.join().intValue());
		
	}
	
	static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test public void showAnyOf() {
		
		CompletableFuture<Object> anyOfThem = CompletableFuture.anyOf(
					new CompletableFuture[] {
							CompletableFuture.supplyAsync( () -> {
										sleep(50);
										return 1;
									}), 
							CompletableFuture.supplyAsync( () -> {
										sleep(100);
										return 2;
							})
					});
		
		assertEquals((Integer) 1, anyOfThem.join());
		
	}	
	
	// Let's throw some exceptions
	
	@Test public void showAnyOfWithException() {
		
		CompletableFuture<Object> anyOfThem = CompletableFuture.anyOf(
					new CompletableFuture[] {
							CompletableFuture.supplyAsync( () -> {
										sleep(50);
										return 1;
									}), 
							CompletableFuture.supplyAsync( () -> {
										sleep(100); // depending on how long this takes, the behavior is actually quite different...
										System.out.println("about to throw ");
										throw new RuntimeException("I'm an exception!");
							})
					});
		
		
	sleep(200);	
		assertEquals((Integer) 1, anyOfThem.join());
		
	}		
		
	/* Shows the use of thenRun in conjunction with an exception thrown in preceeding CF code. 
	 * Result: the exception is just "eaten" by the CF framework. It is not propagated to the client thread (that would be understandable), 
	 * but it is also not processed by thread default exception handler, though we have set one.   
	 */  
	@Test public void showThenRun() {
		
		ForkJoinPool pool = new ForkJoinPool(1, ForkJoinPool.defaultForkJoinWorkerThreadFactory, new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				e.printStackTrace();
			}
		}, false);
		
		
		CompletableFuture
		.supplyAsync(() -> {
			System.out.println("Running...");
			throw new NullPointerException();
		}, pool)
		.thenRun(() -> {
			System.out.println("completed!");
		});		
				
		sleep(100);
	}
	
	/* Because of the exception problem above, it is better to use whenComplete instead of thenRun */
	@Test public void showWhenComplete() {
		
		
		CompletableFuture
		.supplyAsync(() -> {
			System.out.println("Running...");
			throw new NullPointerException();
		})
		.whenComplete( (vd, e) -> {
			if (e != null) {
				e.printStackTrace();
			} else {
				System.out.println("completed!");
			}	
		});			
		
		sleep(100);
	}			
	
	// Determining the thread used to execute a chained CF can be tricky 
	@Test public void showThreadsUsed() {
		
		System.out.println("Main test thread: " + Thread.currentThread().getName());
		
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {			
			sleep(300);
			System.out.println("Thread used to complete CF: " + Thread.currentThread().getName());
			return 1;
		});
		
		//sleep(500);
		
		cf.thenApply(n -> {
					System.out.println("Thread used to complete CF: " + Thread.currentThread().getName());
					return n;
		}).join();		
	}				

}

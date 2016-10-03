package fjp_futures_talk;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

/**
 * This shows different the influence of different thread pools on running the Completable futures.   
 * 
 * @author dalbrecht
 *
 */
public class CompletableFutureInCompletableFuture {
	

	/*
	 * Influence of CF behavior by different pools.
	 * Cached or FJP - if all threads are blocked, create another thread.
	 * Fixed pool - never changes its size --> starvation deadlock 
	 *
	 */
	
	@Test
	public void testWithFixedThreadPool() {

		ExecutorService pool = Executors.newFixedThreadPool(1);		
		
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
			return CompletableFuture.supplyAsync(() -> 1, pool).join();
		}, pool);
		
		assertEquals(1, cf.join().intValue());

		System.out.println("Fixed thread pool size after run: " + ((ThreadPoolExecutor) pool).getPoolSize());
		
	}
	
	@Test
	public void testWithCachedThreadPool() {		

		ExecutorService pool = Executors.newCachedThreadPool();
		
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
			return CompletableFuture.supplyAsync(() -> 1, pool).join();
		}, pool);
		
		assertEquals(1, cf.join().intValue());		
				
		System.out.println("Cached thread pool size after run: " + ((ThreadPoolExecutor) pool).getPoolSize());
		
	}
	
	@Test
	public void testWithForkJoinPool() {
		
		ForkJoinPool pool = new ForkJoinPool(1);
		
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
			return CompletableFuture.supplyAsync(() -> 1, pool).join();
		}, pool);
		
		assertEquals(1, cf.join().intValue());
		
		System.out.println("Fork/join pool size after run: " + pool.getPoolSize());
		
	}
	

}

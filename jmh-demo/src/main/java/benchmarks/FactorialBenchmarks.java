package benchmarks;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)    
public class FactorialBenchmarks {
	
	private static long factorial(int n) {
		long result = 1;
		for(int i=1; i<=n; i++) {
			result = result * i;
		}
		return result;
	}	

    @Benchmark    
    public void testFactorial(Blackhole bh) {
    	bh.consume(factorial(5_000));
    }
    
    @Benchmark
    public void testFactorial_NoBlackhole() {
    	factorial(5_000);
    }
    
    @Benchmark
    public void testFactorial_15(Blackhole bh) {
    	bh.consume(factorial(15));
    }
    
    @Benchmark
    public void testFactorial_1500(Blackhole bh) {
    	bh.consume(factorial(1_500));
    }    

}

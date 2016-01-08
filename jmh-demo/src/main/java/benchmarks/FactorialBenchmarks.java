package benchmarks;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import simple_tests.ClassUnderTest;

@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class FactorialBenchmarks {
	
    @Benchmark    
    public void testFactorial(Blackhole bh) {
    	bh.consume(ClassUnderTest.factorial(5_000));
    }
    
    @Benchmark
    public void testFactorial_NoBlackhole() {
    	ClassUnderTest.factorial(5_000);
    }
    
    @Benchmark
    public void testFactorial_15(Blackhole bh) {
    	bh.consume(ClassUnderTest.factorial(15));
    }
    
    @Benchmark
    public void testFactorial_1500(Blackhole bh) {
    	bh.consume(ClassUnderTest.factorial(1_500));
    }    

}

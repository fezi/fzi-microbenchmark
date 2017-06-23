package fzi.microbenchmark;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;


/**
 * @see examples http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
 * @see comprehensive documentation http://java-performance.info/jmh/
 */
public class MinimalBenchmark {

   public static void main( String[] args ) throws RunnerException {
      String regex = MethodHandles.lookup().lookupClass().getSimpleName();
      ChainedOptionsBuilder options = new OptionsBuilder().include(regex);
      options.mode(Mode.Throughput);
      options.timeUnit(TimeUnit.SECONDS);
      options.forks(1);
      options.warmupIterations(1);
      options.measurementIterations(1);
      options.measurementTime(TimeValue.seconds(1));
      new Runner(options.build()).run();
   }

   @Benchmark
   public void myTestMethod() throws InterruptedException {
      Thread.sleep(1000);
   }

   @Benchmark
   public void myTestMethod2() {
      Blackhole.consumeCPU(500_000_000);
   }
}

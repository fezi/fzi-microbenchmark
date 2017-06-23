package fzi.microbenchmark;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;


/** @see comprehensive documentation http://java-performance.info/jmh/ */
public class CompareStringConcatenations {

   public static void main( String[] args ) throws RunnerException {
      String regex = MethodHandles.lookup().lookupClass().getSimpleName();
      ChainedOptionsBuilder options = new OptionsBuilder().include(regex);
      options.mode(Mode.SingleShotTime);
      options.mode(Mode.AverageTime);
      options.timeUnit(TimeUnit.NANOSECONDS);
      options.forks(1);
      options.threads(1);
      options.warmupIterations(1);
      options.measurementIterations(1);
      options.measurementTime(TimeValue.seconds(1));
      new Runner(options.build()).run();
   }

   @Benchmark
   public String plus( MyState state ) {
      return state.a + state.b;
   }

   @Benchmark
   public StringBuffer stringBuffer( MyState state ) {
      return state.sbf.append(state.a).append(state.b);
   }

   @Benchmark
   public StringBuilder stringBuilder( MyState state ) {
      return state.sb.append(state.a).append(state.b);
   }


   @State(Scope.Thread)
   public static class MyState {

      StringBuilder sb;
      StringBuffer  sbf;

      String a = "a";
      String b = "b";


      @Setup(Level.Invocation)
      public void setup() {
         sb = new StringBuilder();
         sbf = new StringBuffer();
      }

   }

}
/* RESULT:
# Run complete. Total time: 00:00:06

Benchmark                                  Mode  Cnt   Score   Error  Units
CompareStringConcatenations.plus           avgt       49,316          ns/op
CompareStringConcatenations.stringBuffer   avgt       45,817          ns/op
CompareStringConcatenations.stringBuilder  avgt       26,662          ns/op

Benchmark                                  Mode  Cnt     Score   Error  Units
CompareStringConcatenations.plus           avgt         49,518          ns/op
CompareStringConcatenations.stringBuffer   avgt         45,969          ns/op
CompareStringConcatenations.stringBuilder  avgt         27,010          ns/op
CompareStringConcatenations.plus             ss       2711,000          ns/op
CompareStringConcatenations.stringBuffer     ss       1506,000          ns/op
CompareStringConcatenations.stringBuilder    ss       1807,000          ns/op
*/

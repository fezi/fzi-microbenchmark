mvn archetype:generate ^
          -DinteractiveMode=false ^
          -DarchetypeGroupId=org.openjdk.jmh ^
          -DarchetypeArtifactId=jmh-java-benchmark-archetype ^
          -DgroupId=fzi^
          -DartifactId=fzi-microbenchmark^
          -Dversion=1.0-SNAPSHOT
incr:
	mvn install -pl flink-clients,flink-dist -DskipTests -Dfast
full:
	mvn clean package -DskipTests -Dfast -Dcheckstyle.skip=true -Dscala-2.11
showcase:
	flink run -c org.apache.flink.streaming.examples.join.WindowJoin flink-examples-streaming_2.11-1.10-SNAPSHOT-WindowJoin.jar
cluster:
	$(sh ./build-target/bin/start-scala-shell.sh local)

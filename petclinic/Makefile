.PHONY: build, rebuild, run

build:
	sbt clean 
	sbt assembly  
	# cp target/scala-3.1.3/*.jar dist/.

docker-build:

docker-run:

run:
	@open http://localhost:8080/greet/kyle
	java -jar dist/restful-server-assembly-0.0.1.jar

test:
	sbt Test/testOnly
.PHONY: build, rebuild, run

build:
	sbt clean 
	sbt assembly  
	cp target/scala-3.1.3/*.jar dist/.

run:
	java -jar dist/hello-assembly-0.0.1.jar

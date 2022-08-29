.PHONY: build, check, rebuild, run

check:
	java -version
	sbt -version 
	scala -version
	cs version 
	sdk version
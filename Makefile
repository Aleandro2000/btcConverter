build:
	echo "Main-Class: btcConverter" > MANFIEST.MF
	javac btcConverter.java
	jar cvmf MANFIEST.MF btcConverter.jar *.class
run:
	bash btcConverter.sh
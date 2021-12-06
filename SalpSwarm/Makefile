DATA_DIR = ./data

delete-bin:
	rm -rf bin

.SILENT:
run: delete-bin
	javac -d bin SalpSwarm/Main.java && cd bin && java SalpSwarm.Main

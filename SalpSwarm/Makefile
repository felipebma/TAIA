DATA_DIR = ./data

delete-bin:
	rm -rf bin

.SILENT:
run: delete-bin
	javac -d bin SalpSwarm/Main.java && cd bin && java SalpSwarm.Main

plot: $(DATA_DIR)/*_fit.csv
	cd charts && \
	for file in $^ ; do \
		python3 plot_fit.py $(shell pwd) $$(basename $${file%_fit.csv}); \
	done

DATA_DIR = ./data

delete-bin:
	rm -rf bin

.SILENT:
haberman: delete-bin
	javac -d bin SalpSwarm/Haberman.java && cd bin && java SalpSwarm.Haberman < ../data/haberman.data

.SILENT:
cancer: delete-bin
	javac -d bin SalpSwarm/Cancer.java && cd bin && java SalpSwarm.Cancer < ../data/cancer.data


plot: $(DATA_DIR)/*_fit.csv
	cd charts && \
	for file in $^ ; do \
		python3 plot_fit.py $(shell pwd) $$(basename $${file%_fit.csv}); \
	done

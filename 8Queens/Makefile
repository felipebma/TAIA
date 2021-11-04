DATA_DIR = ./data

delete-bin:
	rm -rf bin

.SILENT:
run: delete-bin
	javac -d bin Queens/Main.java && cd bin && java Queens.Main

# run2: delete-bin
	# javac -d bin QueensBits/MainBits.java && cd bin && java QueensBits.MainBits

plot: $(DATA_DIR)/*_fit.csv
	cd charts && \
	for file in $^ ; do \
		python3 plot_fit.py $(shell pwd) $$(basename $${file%_fit.csv}); \
	done

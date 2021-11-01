delete-bin:
	rm -rf bin

.SILENT:
run: delete-bin
	javac -d bin Queens/Main.java && cd bin && java Queens.Main

run2: delete-bin
	javac -d bin QueensBits/MainBits.java && cd bin && java QueensBits.MainBits

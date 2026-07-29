CC = gcc
FILES = main.c mlib.c db.c sqlite/sqlite3.c
OUT_EXE = exec
build: $(FILES) 
	$(CC) -o $(OUT_EXE) $(FILES) -lpthread -ldl
clean:rm -f *.o core
rebuild: clean build

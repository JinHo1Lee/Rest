
CXX=c++

.SUFFIXES: .cpp .o

.cpp.o:
	$(CXX) $(OPT) -c $<

all: main 

OPT=-g -I./
LIBS= -lcurl

main: client

client : client.o
	$(CXX) client.o -o client $(INC) $(LIBS)

clean :
	rm -f client *.o

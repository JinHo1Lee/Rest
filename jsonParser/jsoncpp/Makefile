include $(GW_SRC)/imds/include.mk

JSONLIBNAME = libjsoncpp.a

OBJS = jsoncpp.o

INCLUDE = -I./json

.SUFFIXES: .c .cpp .o

.c.o:
	$(CC) -g -c $<

.cpp.o:
	$(CXX) $(OPT) $(INCLUDE) -c $<

all: $(JSONLIBNAME)

$(JSONLIBNAME): $(OBJS)
	$(AR) r $(JSONLIBNAME) $?
	ranlib $(JSONLIBNAME)

clean:
	rm -f *.o
	rm -f $(JSONLIBNAME)

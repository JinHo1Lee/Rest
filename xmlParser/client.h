#include <iostream>
#include <curl/curl.h>
#include <stdio.h>
#include <string.h>

using namespace std;

class RestClient
{
public :
	RestClient() {};
	~RestClient() {};
	int connectSocket();
	int disconnectSocket();
	int setIp(string ip) { m_sIp = ip; };
	int setPort(string port) { m_iPort = port; };
	int sendMessage(string msg);
	static size_t AckResPrint(void *ptr, size_t size, size_t count, void *stream);
	static int OnDebug(CURL *, curl_infotype itype, char * pData, size_t size, void *);
private :
	CURL *m_pCurl;
	string m_sIp;
	string m_iPort;
};

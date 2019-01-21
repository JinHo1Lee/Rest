#include <iostream>
#include <curl/curl.h>
#include <json/json.h>
#include <stdio.h>
#include <string.h>

using namespace std;

class RestClient
{
public :
	RestClient() {m_sUserId = "1234";};
	~RestClient() {};

	static size_t AckResPrint(void *ptr, size_t size, size_t count, void *stream);
	static int OnDebug(CURL *, curl_infotype itype, char * pData, size_t size, void *);
	string jsonToVar(Json::Value root, string key);

	int init();
	int connectSocket();
	int disconnectSocket();
	int sendMessage(string msg);
private :
	CURL *m_pCurl;

	string m_sIp;
	string m_iPort;
	string m_sUserId;
};

#include "client.h"

int RestClient::init()
{
	m_pCurl = NULL;

	m_sIp = "172.16.2.55";
	m_iPort = "8080";
	m_sUserId = "testId";

	return 0;
}

size_t RestClient::AckResPrint(void *ptr, size_t size, size_t count, void *stream) {
    ((string*) stream)->append((char*) ptr, 0, size * count);
    return size * count;
}

int RestClient::OnDebug(CURL *, curl_infotype itype, char * pData, size_t size, void *) {
    if (itype == CURLINFO_TEXT) {
        printf("[TEXT]%s\n", pData);
    } else if (itype == CURLINFO_HEADER_IN) {
        cout << "sent in header[" << pData << "]" << endl;
    } else if (itype == CURLINFO_DATA_IN) {
        cout << "sent in data[" << pData << "]" << endl;
    } else if (itype == CURLINFO_HEADER_OUT) {
        cout << "sent out header[" << pData << "]" << endl;
    } else if (itype == CURLINFO_DATA_OUT) {
        cout << "sent out data[" << pData << "]" << endl;
    }
    return 0;
}

int RestClient::connectSocket()
{
	if (m_pCurl == NULL)
	{
		curl_global_init (CURL_GLOBAL_ALL);
		m_pCurl = curl_easy_init();
		if (m_pCurl == NULL)
		{
			printf("fail, initialize curl\n");
			return 1;
		}
	}
	return 0;
}

int RestClient::disconnectSocket()
{
	if (m_pCurl != NULL)
	{
		curl_easy_cleanup(m_pCurl);
		curl_global_cleanup();
	}
	return 0;
}

int RestClient::sendMessage(string msg)
{
	string strURL;
	string strBody;
	string id = m_sUserId;

	//m_sUserId = "1234";
	strURL = "http://"+m_sIp+":"+m_iPort+"/json/"+id+"?";
	strBody = "message="+msg;

	struct curl_slist *Headers = NULL;
	Headers = curl_slist_append(Headers, "Content-Type: application/x-www-form-urlencoded");

	if (Headers == NULL)
	{
		printf ("fail, NOT Working Header Appending\n");
	}

	string sRes;

	curl_easy_setopt(m_pCurl, CURLOPT_URL, strURL.c_str());
    curl_easy_setopt(m_pCurl, CURLOPT_WRITEFUNCTION, AckResPrint);
    curl_easy_setopt(m_pCurl, CURLOPT_WRITEDATA, &sRes);
    curl_easy_setopt(m_pCurl, CURLOPT_POSTFIELDS, strBody.c_str());
    curl_easy_setopt(m_pCurl, CURLOPT_POSTFIELDSIZE, strBody.length());
    curl_easy_setopt(m_pCurl, CURLOPT_POST, 1);

	curl_easy_setopt(m_pCurl, CURLOPT_VERBOSE, 1);
	curl_easy_setopt(m_pCurl, CURLOPT_DEBUGFUNCTION, OnDebug);

	const CURLcode rc = curl_easy_perform(m_pCurl);
    if (CURLE_OK != rc) {
        printf ("fail, Error from cURL: %d:%s", rc, curl_easy_strerror(rc));
        curl_slist_free_all(Headers);
        disconnectSocket();
        return 1;
    } else {
        // get some info about the xfer:
        long statLong = 0;
        // HTTP 응답코드를 얻어온다.
        if (CURLE_OK == curl_easy_getinfo(m_pCurl, CURLINFO_HTTP_CODE, &statLong)) {
            // 200 성공
            if (statLong != 200) {
                printf ("Return Header ERR CODE:%d", statLong);
                curl_slist_free_all(Headers);
                disconnectSocket();
                return 1;
            }
        }
    }

	Json::Reader reader;
	Json::Value root;

	bool parserRet = reader.parse(sRes, root);

	if (parserRet == false || root.type() != Json::objectValue)
	{
		printf("fail, Json Parser..\n");
		return 1;
	}
	string result = root["result"].asString();

	cout<<result<<endl;

	return 0;
}

int main (int argc, char **argv)
{
	RestClient clnt;

	clnt.init();
	clnt.connectSocket();

	clnt.sendMessage("hi");

	clnt.disconnectSocket();

	return 0;
}

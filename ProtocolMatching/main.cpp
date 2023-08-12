#include <iostream>
#include <winsock2.h>
#include <windows.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

using namespace std;

int main() {
	
	SOCKET socketfd = socket(AF_INET, SOCK_STREAM, 0);

	return socketfd;

}
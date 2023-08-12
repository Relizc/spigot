#include <bits/stdc++.h>

using namespace std;

bool s[19][19];
bool t[19][19];

string S;
string T;

int main() {
	cin >> S;
	cin >> T;
	
	for (int a = 97; a <= 122; a ++) {
		for (int b = 97; b <= 122; b ++) {
			
			int found = 0;
			
			for (int i = 0; i < S.length(); i ++) {
				if (S.at(i) == a && found == 0) found ++; 
				else if (S.at(i) == b) {
					found ++;
					break;
				}
			}
			
			if (found == 2) {
				s[a][b] = true;
			} else {
				s[a][b] = false;
			}
			
		}
	}
}

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
			
			for (unsigned int i = 0; i < S.length(); i ++) {
				
				if (S.at(i) == a && found == 0) found ++; 
				else if (S.at(i) == b) {
					found ++;
					break;
				}

			}
			
			if (found == 2) {
				s[a - 97][b - 97] = true;
			} else {
				s[a - 97][b - 97] = false;
			}
			
		}
	}
	
	cout << "X a b c d e f g h i j k l m n o p q r " << endl;
	
	for (int i = 0; i < 18; i ++) {
		
		cout << char(97 + i) << " ";
		
		for (int j = 0; j < 18; j ++) {
			cout << s[i][j] << " ";
		}
		
		cout << endl;
	}
}

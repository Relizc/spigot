#include <bits/stdc++.h>

using namespace std;

int N;

int main() {
	cin >> N;
	
	int pattern0 = 0;
	int pattern1 = 0;
	
	int up = 0;
	
	for (int i = 0; i < N; i ++) {
		for (int j = 0; j < N; j ++) {
			int a;
			cin >> a;
			
			if (up < 2) pattern0 += a;
			else pattern1 += a;
			
			up ++;
			if (up == 4) up = 0;
		}
		
		if (i % 2 == 0) {
			up = 2;
		} else {
			up = 0;
		}
	}
	
	cout << max(pattern0, pattern1);
}

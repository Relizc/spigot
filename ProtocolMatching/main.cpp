#include <iostream>

using namespace std;

int main() {
	int n;
	cin >> n;

	int p = 0;
	for (int i = 0; i < n; i++) {
		p += i;
	}

	cout << p << endl;
}
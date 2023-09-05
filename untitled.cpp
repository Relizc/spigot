#include <bits/stdc++.h>

using namespace std;

int expcow[31][100001];

int ans[100001];

int main() {
	int n, m, k;
	
	cin >> n >> m >> k;
	
	for (int i = 0; i < n; i ++) {
		expcow[0][i] = i;
		ans[i] = i;
	}
	
	for (int i = 0; i < m; i ++) {
		int l, r;
		cin >> l >> r;
		
		reverse(expcow[0] + l, expcow[0] + r + 1);
	}
	
	for (int i = 1; (1 << i) <= k; i ++) {
		for (int j = 0; j < n; j ++) {
			int t = expcow[i - 1][j];
			expcow[i][j] = expcow[i - 1][t];
		}
	}
}

#include<iostream>
#include<vector>
#include<algorithm>

#define MAX_WIDTH 500

using namespace std;

struct Result {
	int ID;
	int height;
	int used;
};

struct Tank {
	int id;
	int height;
	int floor;
	vector<pair<int, int>> shapes;//높이(1부터), 모양
	vector<int> hights;
	vector<int> prefixSum;

	void update() {//구조물 높이 정렬
		hights.clear();
		for (int i = 0; i < shapes.size(); i++) hights.push_back(shapes[i].first);
		sort(hights.begin(), hights.end());

		prefixSum.assign(hights.size()+1, 0);//누적합 다시 넣어주기
		for (int i = 0; i < shapes.size(); i++) {
			prefixSum[i + 1] = prefixSum[i] + hights[i];
		}
		floor = hights[0];
	}
};

bool cmp(Tank& a, Tank& b) {
	return a.id < b.id;
}

int h;
int w;

vector<Tank> tanks;

void init(int N, int mWidth, int mHeight, int mIDs[], int mLengths[][MAX_WIDTH], int mUpShapes[][MAX_WIDTH])
{
	tanks.clear();
	w = mWidth;
	h = mHeight;

	for (int i = 0; i < N; i++) {
		vector<pair<int, int>> sh;
		int maxi = 0, mini = 1501;
		for (int j = 0; j < mWidth; j++) {
			sh.push_back({ mLengths[i][j], mUpShapes[i][j] });
			maxi = max(maxi, mLengths[i][j]);
			mini = min(mini, mLengths[i][j]);
		}
		Tank tank;
		tank.id = mIDs[i];
		tank.height = maxi;
		tank.floor = mini;
		tank.shapes = sh;
		tank.update();
		tanks.push_back(tank);
	}
	sort(tanks.begin(), tanks.end(), cmp);
}

int checkStructures(int mLengths[], int mUpShapes[], int mDownShapes[])
{
	int res = 0;
	for (const Tank &t : tanks) {//어항마다 탐색
		for (int i = 0; i < w - 2; i++) {//한 줄씩 확인
			int prev = t.shapes[i].first;
			int prev_low = prev + 1, prev_high;//인접 구조물 높이

			bool find = true;
			for (int j = 0; j < 3; j++) {//3개의 구조물 확인
				if (t.shapes[i + j].second != mDownShapes[j]) {//모양이 다르면
					find = false;
					break;
				}
				//셀의 면이 닿아있어야 하고(높이 비교)
				int now_low = t.shapes[i + j].first + 1;
				int now_high = t.shapes[i + j].first + mLengths[j];
				if (now_high > h) {//어항보다 높으면 안됨
					find = false;
					break;
				}
				if (j == 0) prev_high = now_high;//처음엔 비교 X
				else {
					if (now_low > prev_high || now_high < prev_low) {//셀의 면이 닿는지 확인
						find = false;
						break;
					}
					prev_low = now_low;
					prev_high = now_high;
				}
			}
			if (find) res++;
		}
	}

	//cout << "check ans " << res << "\n";
	return res;
}

int addStructures(int mLengths[], int mUpShapes[], int mDownShapes[])
{
	int id = 0, row = 0;
	bool find = true;
	for (Tank &t : tanks) {//어항마다 탐색
		for (int i = 0; i < w - 2; i++) {//한 줄씩 확인
			find = true;
			int prev = t.shapes[i].first;
			int prev_low = prev + 1, prev_high;//인접 구조물 높이
			
			for (int j = 0; j < 3; j++) {//3개의 구조물 확인
				//if (t.id == 40) cout << "here " << i << " " << j << "\n";
				//cout << "tshape.second " << t.shapes[i + j].second << "\n";
				if (t.shapes[i + j].second != mDownShapes[j]) {//모양이 다르면
					find = false;
					break;
				}
				//셀의 면이 닿아있어야 하고(높이 비교)
				int now_low = t.shapes[i + j].first + 1;
				int now_high = t.shapes[i + j].first + mLengths[j];
				if (now_high > h) {//어항보다 높으면 안됨
					find = false;
					break;
				}
				if (j == 0) prev_high = now_high;//처음엔 비교 X
				else {
					if (now_low > prev_high || now_high < prev_low) {//셀의 면이 닿는지 확인
						find = false;
						break;
					}
					prev_low = now_low;
					prev_high = now_high;
				}
			}
			if (find) {
				id = t.id;
				row = i;
				//cout << "find id : " << id << " row " << i << "\n";
				//구조물 3개 추가
				for (int j = 0; j < 3; j++) {
					//cout << "rownum " << i + j;
					t.shapes[i + j].first += mLengths[j];
					t.shapes[i + j].second = mUpShapes[j];
					t.height = max(t.height, t.shapes[i + j].first);//어항의 높이 갱신
					//cout << " len " << t.shapes[i + j].first << " shape " << t.shapes[i + j].second << "\n";
				}
				t.update();//높이 정렬
				break;
			}
		}
		if (find) break;
	}
	if (id == 0) return 0;
	int res = id * 1000 + row + 1;
	//cout << "add ans " << res << "\n";
	return res;
}

Result pourIn(int mWater)
{
	Result ret;
	ret.ID = ret.height = ret.used = 0;

	for (const Tank &t : tanks) {//어항마다 진행
		int now_h = 0, now_used = 0, now_id = t.id;

		int st = t.floor+1, en = h;
		
		while (st <= en) {//이진탐색
			int mid = (st + en) / 2;

			auto it = lower_bound(t.hights.begin(), t.hights.end(), mid);
			int count = (int)distance(t.hights.begin(), it);

			int tmp = mid * count - t.prefixSum[count];

			if (tmp <= mWater) {
				now_h = mid;
				now_used = tmp;
				st = mid + 1;
			}
			else en = mid-1;
		}

		if (now_used == 0) continue;
		//cout << "id " << t.id << " high " << high << " used " << used << "\n";
		if (now_h > ret.height || (now_h==ret.height && now_used>ret.used)) {
			ret.height = now_h;
			ret.used = now_used;
			ret.ID = t.id;
		}
	}
	//cout << "ret\n" << "ID : " << ret.ID << " height : " << ret.height << " used : " << ret.used << "\n";
	return ret;
}

import java.util.*;

class UserSolution {
	
	int n, k;
	final int max = 200;
	int[] queue = new int[max];
	int[] match = new int[max];
	int[] distance = new int[max];
	int[][] train = new int[max][4];
	boolean[] use = new boolean[max];
	boolean[] visit = new boolean[max];
	boolean[][] move = new boolean[max][max];
	
	public boolean connect(int a, int b) { // shot out to gemini
		int start = Math.max(train[a][0], train[b][0]);
		int end = Math.min(train[a][1], train[b][1]);
		if (start > end) return false;
		int now = start;
		int left = (now - train[a][0]) % train[a][2];
		if (left > 0) now += (train[a][2] - left);
		while (now <= end) {
			if ((now - train[b][0]) % train[b][2] == 0) return true;
			now += train[a][2];
		}
		return false;
	}
	
	public void input(int mId, int sId, int eId, int mInterval) {
		use[k] = true;
		match[k] = mId;
		train[k][0] = sId;
		train[k][1] = eId;
		train[k][2] = mInterval;
		for (int i = 0; i <= k; i++) {
			if (use[i] && connect(i, k)) {
				move[k][i] = true;
				move[i][k] = true;
			} else {
				move[k][i] = false;
				move[i][k] = false;
			}
		}
		k++;
	}
	
	public void init(int N, int K, int mId[], int sId[], int eId[], int mInterval[]) {
		n = N;
		k = 0;
		Arrays.fill(match, 0);
		Arrays.fill(use, false);
		for (int i = 0; i < max; i++) {
			Arrays.fill(move[i], false);
		}
		for (int i = 0; i < K; i++) {
			input(mId[i], sId[i], eId[i], mInterval[i]);
		}
		return;
	}

	public void add(int mId, int sId, int eId, int mInterval) {
		input(mId, sId, eId, mInterval);
		return;
	}

	public void remove(int mId) {
		int idx = 0;
		for (; idx < k; idx++) {
			if (match[idx] == mId) {
				use[idx] = false;
				break;
			}
		}
		return;
	}
	
	public boolean stop(int idx, int station) {
		if (train[idx][0] > station || train[idx][1] < station) return false;
		return Math.abs(train[idx][0] - station) % train[idx][2] == 0;
	}

	public int calculate(int sId, int eId) { // BFS
		int head = 0;
		int tail = 0;
		Arrays.fill(queue, 0);
		Arrays.fill(distance, Integer.MAX_VALUE);
		Arrays.fill(visit, false);
		for (int i = 0; i < k; i++) {
			if (use[i] && stop(i, sId)) {
				if (stop(i, eId)) return 0;
				queue[tail++] = i;
				distance[i] = 0;
				visit[i] = true;
			}
		}
		while (head < tail) {
			int pop = queue[head++];
			for (int i = 0; i < k; i++) {
				if(use[i] && !visit[i] && move[pop][i]) {
					if (stop(i, eId)) return distance[pop] + 1;
					queue[tail++] = i;
					distance[i] = distance[pop] + 1;
					visit[i] = true;
				}
			}
		}
		return -1;
	}
    
}

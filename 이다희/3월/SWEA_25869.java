import java.util.*;

class UserSolution {
	
	class Student {
		int id;
		int[] score;
		public Student(int id) {
			super();
			this.id = id;
			this.score = new int[30];
		}
	}
	
	int n, m, c;
	int[] match = new int[20001];
	int[][] weights = new int[30][5];
	Student[] students = new Student[20001];
	TreeSet<Student>[] select = new TreeSet[30];
	TreeSet<Student>[] wait = new TreeSet[30];
	
	public void init(int N, int M, int[][] mWeights) {
		n = N;
		m = M;
		c = 0;
		Arrays.fill(match, -2);
		for (int i = 1; i <= 20000; i++) {
			students[i] = new Student(i);
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < 5; j++) {
				weights[i][j] = mWeights[i][j];
			}
			final int idx = i; // lambda
			select[idx] = new TreeSet<Student>((a, b)->{
				if (a.score[idx] == b.score[idx]) return a.id - b.id;
				return b.score[idx] - a.score[idx];
			});
			wait[idx] = new TreeSet<Student>((a, b)->{
				if (a.score[idx] == b.score[idx]) return a.id - b.id;
				return b.score[idx] - a.score[idx];
			});
		}
		return;
	}

	public void add(int mID, int[] mScores) {
		// 점수 계산
		int score = 0;
		for (int i = 0; i < m; i++) {
			score = 0;
			for (int j = 0; j < 5; j++) {
				score += mScores[j] * weights[i][j];
			}
			students[mID].score[i] = score;
		}
		// 순위 계산
		int id = mID;
		for (int i = 0; i < m; i++) {
			match[id] = i;
			select[i].add(students[id]);
			if (select[i].size() <= n) break;
			id = select[i].pollLast().id;
			match[id] = -2;
			wait[i].add(students[id]);
		}
		return;
	}

	public void erase(int mID) {
		int idx = match[mID];
		if (idx == -2) idx = m;
		for (int i = 0; i < idx; i++) {
			wait[i].remove(students[mID]);
		}
		if (match[mID] == -2) return;
		match[mID] = -2;
		select[idx].remove(students[mID]);
		int id = mID;
		for (int i = idx; i < m;) {
			if (wait[i].isEmpty()) break;
			id = wait[i].pollFirst().id;
			int before = idx = match[id];
			if (idx == -2) idx = m;
			for (int j = i + 1; j < idx; j++) {
				wait[j].remove(students[id]);
			}
			select[i].add(students[id]);
			match[id] = i;
			if (before == -2) break;
			select[before].remove(students[id]);
			i = before;
		}
		return;
	}

	public int suggest(int mID) {
		return match[mID]+1; // 1~30
	}
}

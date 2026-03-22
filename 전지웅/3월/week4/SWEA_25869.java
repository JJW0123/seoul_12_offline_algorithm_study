package week4;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.TreeSet;

class UserSolution {

    class Student implements Comparable<Student> {
        int id, score;

        Student(int id, int score) {
            this.id = id;
            this.score = score;
        }

        @Override
        public int compareTo(Student o) {
            // 점수가 같으면 ID 오름차순
            if (this.score == o.score) {
                return Integer.compare(this.id, o.id);
            }
            // 점수 내림차순
            return Integer.compare(o.score, this.score);
        }
    }

    static int N, M;
    static TreeSet<Student>[] univ;
    static PriorityQueue<Student>[] students;
    static int[] res;
    static int[][] weight, allScores;

    public void init(int N, int M, int[][] mWeights) {
        UserSolution.N = N;
        UserSolution.M = M;

        res = new int[20_001];
        Arrays.fill(res, -1);

        allScores = new int[20_001][M + 1];

        univ = new TreeSet[M + 1];
        students = new PriorityQueue[M + 1];
        for (int i = 0; i <= M; i++) {
            univ[i] = new TreeSet<>();
            students[i] = new PriorityQueue<>();
        }

        weight = mWeights;
        return;
    }

    public void add(int mID, int[] mScores) {
        Student st = null;

        // allScores 배열에 점수 저장
        for (int i = 0; i < M; i++) {
            int score = 0;
            for (int j = 0; j < 5; j++) {
                score += mScores[j] * weight[i][j];
            }
            allScores[mID][i + 1] = score;
        }

        res[mID] = 0;
        st = new Student(mID, 0);
        for (int n = 1; n <= M; n++) {
            st = new Student(st.id, allScores[st.id][n]);

            // 대학 인원미달(합격 확정)
            if (univ[n].size() < N) {
                univ[n].add(st);
                res[st.id] = n;
                break;
            }

            // 기존 꼴찌보다 우선순위가 높으면 합격(꼴등은 뒤로 밀어내기)
            if (st.compareTo(univ[n].last()) < 0) {
                univ[n].add(st);
                res[st.id] = n;

                st = univ[n].pollLast();
                res[st.id] = 0;
            }
            students[n].add(st);
        }
        return;
    }

    public void erase(int mID) {
        // 입학생이었다면 대학에서 삭제
        if (res[mID] > 0) {
            univ[res[mID]].remove(new Student(mID, allScores[mID][res[mID]]));

            int curr_univ = res[mID];

            while (curr_univ <= M) {
                Student st = null;

                while (!students[curr_univ].isEmpty()) {
                    st = students[curr_univ].poll();

                    // 삭제된 학생이거나 더 좋은 대학에 합격한 학생이면 패스
                    if (res[st.id] == -1 || (res[st.id] > 0 && res[st.id] <= curr_univ)) {
                        st = null;
                        continue;
                    }
                    break;
                }

                // 큐 비었을 때 예외처리
                if (st == null) {
                    break;
                }

                // 땡겨올 학생이 원래 다니던 하위 대학 번호
                int univNum = res[st.id];

                // 원래 다니던 대학에서 삭제
                if (univNum > 0) {
                    univ[univNum].remove(new Student(st.id, allScores[st.id][univNum]));
                }

                // 현재 구멍 난 대학(curr_univ)에 입학시키기
                univ[curr_univ].add(new Student(st.id, allScores[st.id][curr_univ]));
                res[st.id] = curr_univ;

                // 미입학 학생이었다면 더 땡겨올 필요가 없으니 종료
                if (univNum == 0) {
                    break;
                }

                // 하위 대학에 난 빈자리 계속해서 채우기
                curr_univ = univNum;
            }
        }

        res[mID] = -1;
        return;
    }

    public int suggest(int mID) {
        return res[mID] > 0 ? res[mID] : -1;
    }
}
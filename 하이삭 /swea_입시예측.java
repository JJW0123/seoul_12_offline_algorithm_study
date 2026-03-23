import java.util.*;

class UserSolution
{
    // =========================
    // 기본 정보
    // =========================
    static int capacity;      // 대학 정원
    static int univCount;     // 대학 수

    // 대학별 가중치
    static int[][] weights;

    // 대학별 후보자 (max heap)
    static PriorityQueue<Student>[] candidatePQ;

    // 대학별 합격자 (꼴찌 top)
    static PriorityQueue<Student>[] admittedPQ;

    // 학생 -> 배정된 대학 (-1이면 미배정)
    static int[] assigned;

    // 학생 점수
    static int[][] scores;

    // 삭제된 학생 (lazy deletion)
    static HashSet<Integer> deleted;

    // 대학별 현재 인원
    static int[] count;


    public void init(int N, int M, int[][] mWeights)
    {
        capacity = N;
        univCount = M;
        weights = mWeights;

        candidatePQ = new PriorityQueue[M];
        admittedPQ = new PriorityQueue[M];
        count = new int[M];

        for(int i = 0; i < M; i++) {
            candidatePQ[i] = new PriorityQueue<>();
            admittedPQ[i] = new PriorityQueue<>(Collections.reverseOrder());
        }

        assigned = new int[20001];
        scores = new int[20001][5];
        deleted = new HashSet<>();
    }


    public void add(int id, int[] inputScores)
    {
        scores[id] = inputScores.clone();

        int curId = id;

        for(int u = 0; u < univCount; u++) {

            assigned[curId] = -1;

            int score = calcScore(curId, u);
            Student me = new Student(curId, score);

            // 1. 자리 있으면 합격
            if(count[u] < capacity) {
                assigned[curId] = u;
                count[u]++;
                admittedPQ[u].add(me);
                return;
            }

            // 2. 유효하지 않은 합격자 제거
            cleanTop(u);

            // 3. 꼴찌와 비교
            Student worst = admittedPQ[u].peek();

            if(isBetter(score, curId, worst)) {

                // 교체
                Student removed = admittedPQ[u].poll();

                assigned[curId] = u;
                admittedPQ[u].add(me);

                candidatePQ[u].add(removed);

                curId = removed.id;
            }
            else {
                candidatePQ[u].add(me);
            }
        }

        assigned[curId] = -1;
    }
    
    int calcScore(int id, int u) {
        int sum = 0;
        for(int i = 0; i < 5; i++)
            sum += scores[id][i] * weights[u][i];
        return sum;
    }

    void cleanTop(int u) {
        while(!admittedPQ[u].isEmpty()) {
            Student top = admittedPQ[u].peek();

            if(deleted.contains(top.id) || assigned[top.id] != u)
                admittedPQ[u].poll();
            else break;
        }
    }
    
    boolean isBetter(int score, int id, Student worst) {
        return (worst.score < score) ||
               (worst.score == score && worst.id > id);
    }

    public void erase(int mID)
    {
        deleted.add(mID);

        int start = assigned[mID];
        if(start < 0) return;

        // 빈자리 생성
        count[start]--;
        assigned[mID] = -1;

        // 뒤로 전달
        for(int u = start; u < univCount; u++) {
            fill(u);
        }
    }
    
    void fill(int u) {

        if(count[u] >= capacity) return;

        while(!candidatePQ[u].isEmpty()) {

            Student s = candidatePQ[u].poll();

            if(deleted.contains(s.id)) continue;

            if(assigned[s.id] < u && assigned[s.id] >= 0) continue;

            if(assigned[s.id] >= 0) {
                count[assigned[s.id]]--;
            }

            assigned[s.id] = u;
            count[u]++;
            admittedPQ[u].add(s);

            return;
        }
    }
    
    public int suggest(int mID)
    {
        if (assigned[mID] == -1) return -1;
        return assigned[mID] + 1;
    }
}


// =========================
// Student
// =========================
class Student implements Comparable<Student> {

    int id;
    int score;

    public Student(int id, int score) {
        this.id = id;
        this.score = score;
    }

    @Override
    public int compareTo(Student o) {
        int ret = Integer.compare(o.score, this.score);
        return (ret != 0) ? ret : Integer.compare(this.id, o.id);
    }
}

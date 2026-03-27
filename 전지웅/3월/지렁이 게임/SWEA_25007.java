import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class UserSolution {
    // =========================================================
    // 지렁이 클래스
    // =========================================================
    class Snake implements Comparable<Snake> {
        int dir, id, length, grow;
        Deque<int[]> body;

        Snake(int id, int dir, int length, int grow, Deque<int[]> body) {
            this.id = id; // id
            this.dir = dir; // 진행 방향
            this.length = length; // 길이
            this.grow = grow; // 성장 잠재력
            this.body = body; // 머리~꼬리까지의 {x좌표, y좌표}를 Deque에 저장
        }

        @Override
        public int compareTo(Snake o) {
            // 1. 길이 내림차순, 2. id 내림차순
            if (o.length == this.length) {
                return Integer.compare(o.id, this.id);
            } else {
                return Integer.compare(o.length, this.length);
            }
        }
    }

    // =========================================================
    int[][] map, nMap; // 게임판
    int curTime; // 현재 시간
    int snakeCnt; // 지렁이 수
    Map<Integer, Snake> snakeMap = new HashMap<>(); // 살아있는 지렁이를 {id, 지렁이 클래스}로 저장
    Set<Integer> deadSnake = new HashSet<>(); // 죽은 지렁이를 {id}로 저장

    // 방향은 상, 우, 하, 좌
    int[] dx = { 0, 1, 0, -1 };
    int[] dy = { -1, 0, 1, 0 };

    public void init(int N) {

        // 변수 초기화
        map = new int[N][N];
        nMap = new int[N][N];
        snakeMap.clear();
        deadSnake.clear();
        curTime = 0;
        snakeCnt = 0;

        return;
    }

    public void join(int mTime, int mID, int mX, int mY, int mLength) {

        // mTime까지 시간 진행
        move(mTime);

        // 지렁이 추가
        add(mID, mX, mY, mLength);

        return;
    }

    // 지렁이 추가
    void add(int id, int x, int y, int length) {
        Deque<int[]> deque = new ArrayDeque<>();
        for (int n = y; n < y + length; n++) {
            deque.add(new int[] { n, x });
            map[n][x] = id;
        }
        snakeMap.put(id, new Snake(id, 0, length, 0, deque));
    }

    // time까지 이동하기
    void move(int time) {
        while (curTime < time) {
            deadSnake.clear();

            for (Snake nSnake : snakeMap.values()) {

                // 회전하기(만약 뱀이 직선 형태라면)
                if (nSnake.body.peekFirst()[0] == nSnake.body.peekLast()[0]
                        || nSnake.body.peekFirst()[1] == nSnake.body.peekLast()[1]) {
                    nSnake.dir = (nSnake.dir + 1) % 4;
                }

                // 성장 잠재력이 없으면 꼬리 떼기
                if (nSnake.grow == 0) {
                    int[] tail = nSnake.body.pollLast();
                    map[tail[0]][tail[1]] = 0;
                } else {
                    nSnake.length++;
                    nSnake.grow--;
                }
            }

            // 머리 이동
            for (Snake nSnake : snakeMap.values()) {

                // 머리에 dir 방향으로 한 칸 추가
                int nx = nSnake.body.peekFirst()[1] + dx[nSnake.dir];
                int ny = nSnake.body.peekFirst()[0] + dy[nSnake.dir];
                nSnake.body.offerFirst(new int[] { ny, nx });

                if (ny < 0 || ny >= map.length || nx < 0 || nx >= map.length) { // 벽에 부딪힘(죽음)
                    deadSnake.add(nSnake.id);

                } else if (nMap[ny][nx] != 0) { // 다른 뱀 머리랑 부딪힘(둘 다 죽음)
                    deadSnake.add(nSnake.id);
                    deadSnake.add(nMap[ny][nx]);

                } else if (map[ny][nx] != 0) { // 다른 뱀 몸통에 부딪힘(죽고 성장력 늘려주기)
                    deadSnake.add(nSnake.id);

                    int[] head = snakeMap.get(nSnake.id).body.peekFirst();
                    Snake growSnake = snakeMap.get(map[head[0]][head[1]]);
                    growSnake.grow += snakeMap.get(nSnake.id).length;

                } else {
                    nMap[ny][nx] = nSnake.id;
                }
            }

            // nMap 초기화
            for (Snake nSnake : snakeMap.values()) {
                int[] head = nSnake.body.peekFirst();
                if (head[0] >= 0 && head[0] < map.length && head[1] >= 0 && head[1] < map.length) {
                    nMap[head[0]][head[1]] = 0;
                }
            }

            // 뱀 죽이기
            for (int snakeId : deadSnake) {
                Snake dead = snakeMap.remove(snakeId);
                // map에서 죽은 뱀 치우기
                for (int[] body : dead.body) {
                    if (body[0] >= 0 && body[0] < map.length && body[1] >= 0 && body[1] < map.length) {
                        if (map[body[0]][body[1]] == snakeId) {
                            map[body[0]][body[1]] = 0;
                        }
                    }
                }
            }

            // 살아있는 뱀 머리 옮기기
            for (Snake nSnake : snakeMap.values()) {
                int[] head = nSnake.body.peekFirst();
                map[head[0]][head[1]] = nSnake.id;
            }

            curTime++;
        }
    }

    public Solution.RESULT top5(int mTime) {
        move(mTime);
        PriorityQueue<Snake> pq = new PriorityQueue<>(snakeMap.values());

        Solution.RESULT res = new Solution.RESULT();

        // 상위 5마리
        int count = Math.min(5, pq.size());
        res.cnt = count;
        for (int i = 0; i < count; i++) {
            res.IDs[i] = pq.poll().id;
        }
        return res;
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main {
    static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } }; // 우, 하, 좌, 상
    static char[][] map; // 지도
    static int N, M; // 지도의 크기

    // 다리 정보를 저장할 클래스
    static class Bridge {
        int x, y; // 시작점 좌표
        int length;
        int d; // 방향

        public Bridge(int x, int y, int length, int d) {
            this.x = x;
            this.y = y;
            this.length = length;
            this.d = d;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];

        // 지도 input
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            st = new StringTokenizer(line);
            for (int j = 0; j < M; j++) {
                map[i][j] = st.nextToken().charAt(0);
            }
        }

        int res = 0;
        char island_cnt = 'A'; // 섬 이름

        // bfs로 섬 구분하기
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                if (map[x][y] == '1') {
                    bfs(x, y, island_cnt++);
                }
            }
        }

        // 모든 가능한 다리 찾아서 우선순위 큐에 넣기(다리 길이순 정렬)
        PriorityQueue<Bridge> pq = new PriorityQueue<>((o1, o2) -> o1.length - o2.length);

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                if (map[x][y] != '0') { // 땅이라면 4방향으로 다리 쏴보기
                    findBridges(x, y, pq);
                }
            }
        }

        // 짧은 다리부터 꺼내서 연결 및 맵 갱신
        while (!pq.isEmpty()) {
            Bridge b = pq.poll();

            char startIsland = map[b.x][b.y];

            // 다리 끝에 닿은 섬의 좌표 계산
            int nx = b.x + dir[b.d][0] * (b.length + 1);
            int ny = b.y + dir[b.d][1] * (b.length + 1);

            char endIsland = map[nx][ny];

            // 두 섬이 서로 다른 이름이라면 통일시키기
            if (startIsland != endIsland) {
                res += b.length;
                mergeIslands(endIsland, startIsland);
            }
        }

        // 맵 전체 돌면서 섬이 하나로 통일되었는지 확인
        if (isAllConnected()) {
            System.out.println(res);
        } else {
            System.out.println("-1");
        }
    }

    // 연결된 섬은 섬 이름 통일하기
    static void mergeIslands(char target, char goal) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == target) {
                    map[i][j] = goal;
                }
            }
        }
    }

    // 모든 섬이 같은 이름인지 확인
    static boolean isAllConnected() {
        char standard = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] != '0') {
                    // standard : 처음 발견한 섬의 이름
                    if (standard == 0) {
                        standard = map[i][j];
                    } else if (map[i][j] != standard) {
                        // 다른 섬이 남아있으면 실패
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static void findBridges(int x, int y, PriorityQueue<Bridge> pq) {
        char current = map[x][y];

        for (int i = 0; i < 4; i++) {
            int length = 0;
            int nx = x;
            int ny = y;

            while (true) {
                nx += dir[i][0];
                ny += dir[i][1];

                if (nx < 0 || nx >= N || ny < 0 || ny >= M)
                    break; // 맵 밖

                if (map[nx][ny] == current)
                    break; // 같은 섬 만남

                if (map[nx][ny] != '0') { // 다른 섬 만남
                    if (length >= 2) {
                        // 시작좌표, 길이, 방향 저장
                        pq.add(new Bridge(x, y, length, i));
                    }
                    break;
                }
                length++; // 바다라면 마저 건너기
            }
        }
    }

    static void bfs(int x, int y, char cnt) {
        Deque<int[]> deque = new ArrayDeque<>();
        deque.push(new int[] { x, y });
        map[x][y] = cnt;

        boolean[][] visited = new boolean[N][M];
        visited[x][y] = true;

        while (!deque.isEmpty()) {
            int[] cur = deque.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dir[i][0];
                int ny = cur[1] + dir[i][1];
                if (nx >= 0 && nx < N && ny >= 0 && ny < M && map[nx][ny] == '1' && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    map[nx][ny] = cnt;
                    deque.push(new int[] { nx, ny });
                }
            }
        }
    }
}
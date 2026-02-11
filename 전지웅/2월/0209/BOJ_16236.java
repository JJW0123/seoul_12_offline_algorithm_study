import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_16236 {
    static int[] dx = { 1, -1, 0, 0 };
    static int[] dy = { 0, 0, 1, -1 };
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 맵의 크기
        N = Integer.parseInt(br.readLine());

        // 상어 좌표
        int x = 0;
        int y = 0;

        // 맵 채우기
        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 9) {
                    x = i;
                    y = j;
                    map[i][j] = 0;
                }
            }
        }

        // 크기 저장하는 int 하나 선언하기
        // 길찾기 알고리즘 만들기(크기 이하인 길은 지나가기 가능, 크기 작아야만 먹을 수 있음)
        // 최단거리 찾아야 하니까 bfs?
        // 거리 확정되면 바로 현재 위치랑 크기, 걸린시간 리턴하고 다시 길찾기 실행
        // 물고기 먹을 때마다 크기 갱신하고 루트 갱신하기
        Shark shark = new Shark(x, y, 2, 0);

        int time = 0; // 걸린 시간
        while (true) {

            // 거리, x좌표, y좌표
            PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {
                if (o1[0] != o2[0])
                    return Integer.compare(o1[0], o2[0]); // 1. 거리 순
                if (o1[1] != o2[1])
                    return Integer.compare(o1[1], o2[1]); // 2. 위쪽(x작음) 순
                return Integer.compare(o1[2], o2[2]); // 3. 왼쪽(y작음) 순
            });
            boolean[][] visited = new boolean[N][N];

            pq.offer(new int[] { 0, shark.x, shark.y });
            visited[shark.x][shark.y] = true;
            boolean ate = false;

            while (!pq.isEmpty()) {
                int[] cur = pq.poll();
                int curDist = cur[0];
                int curX = cur[1];
                int curY = cur[2];

                // 먹을 수 있는 물고기가 있다면
                if (map[curX][curY] != 0 && map[curX][curY] < shark.size) {
                    map[curX][curY] = 0; // 물고기 제거
                    // 상어 위치 갱신
                    shark.x = curX;
                    shark.y = curY;
                    shark.full++;
                    // 상어 크기 갱신
                    if (shark.full == shark.size) {
                        shark.size++;
                        shark.full = 0;
                    }
                    // 걸린 시간 갱신
                    time += curDist;
                    ate = true;
                    break; // 이번 턴 종료, 새로 BFS 시작
                }

                for (int i = 0; i < 4; i++) {
                    int nx = curX + dx[i];
                    int ny = curY + dy[i];

                    if (nx >= 0 && ny >= 0 && nx < N && ny < N && !visited[nx][ny]) {
                        if (map[nx][ny] <= shark.size) {
                            visited[nx][ny] = true;
                            pq.offer(new int[] { curDist + 1, nx, ny });
                        }
                    }
                }
            }

            if (!ate)
                break; // 더 이상 먹을 물고기가 없으면 종료
        }

        System.out.println(time);
    }

    static class Shark {
        int x, y, size, full;

        public Shark(int x, int y, int size, int full) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.full = full;
        }
    }
}
import java.util.*;

class UserSolution {
    private static final int MAX_N = 100;
    private int N;
    
    // 배열 메모리 재사용
    private int[][] map = new int[MAX_N][MAX_N];
    private int[][] glacierDir = new int[MAX_N][MAX_N];
    private int[][] nextMap = new int[MAX_N][MAX_N];
    private boolean[][] visited = new boolean[MAX_N][MAX_N];
    private int[][] movedMap = new int[MAX_N][MAX_N];
    private boolean[][] visitedFinal = new boolean[MAX_N][MAX_N];
    private int[][] nextDir = new int[MAX_N][MAX_N];
    private List<Integer>[][] landed = new ArrayList[MAX_N][MAX_N];

    private int[] dy = {-1, 0, 1, 0}; // 0:상, 1:우, 2:하, 3:좌
    private int[] dx = {0, 1, 0, -1};

    static class RESULT {
        int[][] heights;
        RESULT() { heights = new int[MAX_N][MAX_N]; }
    }

    class Piece implements Comparable<Piece> {
        int id, vol, area, minRow, minCol, dir;
        List<int[]> cells = new ArrayList<>();

        Piece(int id, int dir) {
            this.id = id;
            this.dir = dir;
            this.minRow = 200;
            this.minCol = 200;
        }

        void add(int r, int c, int h) {
            cells.add(new int[]{r, c, h});
            vol += h;
            area++;
            // 최상단, 최좌측 좌표 계산
            if (r < minRow || (r == minRow && c < minCol)) {
                minRow = r; minCol = c;
            }
        }

        @Override
        public int compareTo(Piece o) {
            if (this.vol != o.vol) return o.vol - this.vol;      // 1순위: 부피 큰 순
            if (this.area != o.area) return this.area - o.area;  // 2순위: 면적 작은 순
            if (this.minRow != o.minRow) return this.minRow - o.minRow; // 3순위: Y 작은 순
            return this.minCol - o.minCol;                       // 4순위: X 작은 순
        }
    }

    public UserSolution() {
        for (int i = 0; i < MAX_N; i++) {
            for (int j = 0; j < MAX_N; j++) {
                landed[i][j] = new ArrayList<>();
            }
        }
    }

    public void init(int N, int M, int mIceBlock[][], int mIceGroup[][]) {
        this.N = N;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = mIceBlock[i][j];
                glacierDir[i][j] = -1;
            }
        }

        for (int i = 0; i < M; i++) {
            bfs(mIceGroup[i][1] % N, mIceGroup[i][0] % N, mIceGroup[i][2]);
        }
    }

    private void bfs(int r, int c, int d) {
        if (map[r][c] == 0 || glacierDir[r][c] != -1) return;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{r, c});
        glacierDir[r][c] = d;
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            for (int i = 0; i < 4; i++) {
                int nr = (curr[0] + dy[i] + N) % N;
                int nc = (curr[1] + dx[i] + N) % N;
                if (map[nr][nc] > 0 && glacierDir[nr][nc] == -1) {
                    glacierDir[nr][nc] = d;
                    q.add(new int[]{nr, nc});
                }
            }
        }
    }

    public RESULT oneYearLater() {
        // 매년 시작 전 초기화 처리
        for (int i = 0; i < N; i++) {
            Arrays.fill(nextMap[i], 0);
            Arrays.fill(visited[i], false);
            Arrays.fill(movedMap[i], 0);
            Arrays.fill(visitedFinal[i], false);
            Arrays.fill(nextDir[i], -1);
            for (int j = 0; j < N; j++) {
                landed[i][j].clear();
            }
        }

        // 인접한 바다가 하나라도 있으면 1만큼만 감소
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (map[r][c] == 0) continue;
                int sea = 0;
                for (int i = 0; i < 4; i++) {
                    if (map[(r + dy[i] + N) % N][(c + dx[i] + N) % N] == 0) sea++;
                }
                
                // 바다와 접해있으면 1만 감소, 아니면 그대로 유지
                if (sea > 0) {
                    nextMap[r][c] = Math.max(0, map[r][c] - 1);
                } else {
                    nextMap[r][c] = map[r][c];
                }
            }
        }
        
        for(int i = 0; i < N; i++) System.arraycopy(nextMap[i], 0, map[i], 0, N);

        // 빙하 이동 전 상태 수집
        List<Piece> pieceList = new ArrayList<>();
        int pieceIdCounter = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] > 0 && !visited[i][j]) {
                    Piece p = new Piece(pieceIdCounter++, glacierDir[i][j]);
                    Queue<int[]> q = new ArrayDeque<>();
                    q.add(new int[]{i, j});
                    visited[i][j] = true;
                    while (!q.isEmpty()) {
                        int[] cur = q.poll();
                        p.add(cur[0], cur[1], map[cur[0]][cur[1]]);
                        for (int d = 0; d < 4; d++) {
                            int nr = (cur[0] + dy[d] + N) % N;
                            int nc = (cur[1] + dx[d] + N) % N;
                            if (map[nr][nc] > 0 && !visited[nr][nc]) {
                                visited[nr][nc] = true;
                                q.add(new int[]{nr, nc});
                            }
                        }
                    }
                    pieceList.add(p);
                }
            }
        }

        // 이동 및 겹침 처리
        for (Piece p : pieceList) {
            for (int[] cell : p.cells) {
                int nr = (cell[0] + dy[p.dir] + N) % N;
                int nc = (cell[1] + dx[p.dir] + N) % N;
                // 겹칠 경우 최고 높이만 남김
                movedMap[nr][nc] = Math.max(movedMap[nr][nc], cell[2]);
                // 어떤 조각이 도달했는지 ID 저장
                landed[nr][nc].add(p.id);
            }
        }
        
        for(int i = 0; i < N; i++) System.arraycopy(movedMap[i], 0, map[i], 0, N);

        // 병합 후 방향 결정 및 결과 저장
        RESULT res = new RESULT();
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                res.heights[i][j] = map[i][j]; // 결과 반환용 맵 업데이트
                if (map[i][j] > 0 && !visitedFinal[i][j]) {
                    List<int[]> groupCells = new ArrayList<>();
                    PriorityQueue<Piece> pq = new PriorityQueue<>();
                    Set<Integer> pIds = new HashSet<>();
                    
                    Queue<int[]> q = new LinkedList<>();
                    q.add(new int[]{i, j});
                    visitedFinal[i][j] = true;
                    
                    while (!q.isEmpty()) {
                        int[] cur = q.poll();
                        groupCells.add(cur);
                        
                        // 현재 좌표가 포함된 덩어리에 도달했던 이전 빙하 조각들을 수집
                        for (int pid : landed[cur[0]][cur[1]]) {
                            if (pIds.add(pid)) pq.add(pieceList.get(pid));
                        }
                        
                        for (int d = 0; d < 4; d++) {
                            int nr = (cur[0] + dy[d] + N) % N;
                            int nc = (cur[1] + dx[d] + N) % N;
                            if (map[nr][nc] > 0 && !visitedFinal[nr][nc]) {
                                visitedFinal[nr][nc] = true;
                                q.add(new int[]{nr, nc});
                            }
                        }
                    }
                    // 가장 강력했던 빙하의 이동방향 계승
                    int winnerDir = pq.peek().dir;
                    for (int[] cell : groupCells) nextDir[cell[0]][cell[1]] = winnerDir;
                }
            }
        }
        
        for(int i = 0; i < N; i++) System.arraycopy(nextDir[i], 0, glacierDir[i], 0, N);
        
        return res;
    }
}
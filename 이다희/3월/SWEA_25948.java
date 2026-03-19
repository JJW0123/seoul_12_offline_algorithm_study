import java.util.*;
 
class UserSolution { // 빙하의 이동
     
    private final static int MAX_N = 100;
     
    class RESULT {
        int[][] heights;
        RESULT() {
            heights = new int[MAX_N][MAX_N];
        }
    }
     
    class Info { // 빙하
        int dir; // 방향
        int volume; // 부피
        int area; // 면적
        int[] min; // 위치
        ArrayList<int[]> ice; // 얼음덩어리
        public Info(int dir) {
            super();
            this.dir = dir;
            this.volume = 0;
            this.area = 0;
            this.min = new int[] {100, 100};
            this.ice = new ArrayList<>();
        }
    }
 
    int n;
    int[][] map;
    int[][] temp;
    boolean[][] visited;
    HashSet<Integer>[][] group;
    HashSet<Integer> set = new HashSet<>();
    Queue<int[]> queue = new ArrayDeque<>();
    ArrayList<Info> list = new ArrayList<>(); // 빙하 리스트
    int[][] move = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
     
    void init(int N, int M, int mIceBlock[][], int mIceGroup[][]) {
        n = N;
        map = new int[n][n];
        temp = new int[n][n];
        visited = new boolean[n][n];
        group = new HashSet[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
            	map[i][j] = mIceBlock[i][j];
                group[i][j] = new HashSet<>();
            }
        }
        list.clear();
        for (int i = 0; i < M; i++) {
            list.add(bfs(mIceGroup[i][1], mIceGroup[i][0], mIceGroup[i][2], map));
        }
    }
     
    Info bfs(int y, int x, int dir, int[][] arr) {
        Info info = new Info(dir);
        visited[y][x] = true;
        queue.clear();
        queue.add(new int[] {y, x});
        while (!queue.isEmpty()) {
            int[] pop = queue.poll();
            if (!group[pop[0]][pop[1]].isEmpty()) { // 빙하 인덱스
                for (int g : group[pop[0]][pop[1]]) {
                    set.add(g);
                }
            }
            info.volume += arr[pop[0]][pop[1]];
            info.area++;
            info.ice.add(new int[] {pop[0], pop[1]});
            for (int[] m : move) {
                int dy = (pop[0] + m[0] + n) % n;
                int dx = (pop[1] + m[1] + n) % n;
                if (arr[dy][dx] == 0 || visited[dy][dx]) continue;
                visited[dy][dx] = true;
                queue.add(new int[] {dy, dx});
            }
        }
        return info;
    }
     
    boolean check(int a, int b) {
        // [1] 부피가 큰 빙하
        if (list.get(a).volume != list.get(b).volume) return list.get(a).volume > list.get(b).volume;
        // [2] 면적이 작은 빙하
        if (list.get(a).area != list.get(b).area) return list.get(a).area < list.get(b).area;
        // [3] Y 좌표가 작은 위치에 있는 빙하
        if (list.get(a).min[0] != list.get(b).min[0]) return list.get(a).min[0] < list.get(b).min[0];
        // [4] X 좌표가 작은 위치에 있는 빙하
        return list.get(a).min[1] < list.get(b).min[1];
    }
 
    RESULT oneYearLater() {
        // [1] 융해
    	ArrayList<Info> after = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Arrays.fill(visited[i], false);
            Arrays.fill(temp[i], 0);
        }
        for (Info info : list) {
            for (int[] ice : info.ice) {
                temp[ice[0]][ice[1]] = map[ice[0]][ice[1]];
                for (int[] m : move) {
                    int dy = (ice[0] + m[0] + n) % n;
                    int dx = (ice[1] + m[1] + n) % n;
                    if (map[dy][dx] == 0) {
                        temp[ice[0]][ice[1]]--;
                        info.volume--;
                        break;
                    }
                }
            }
        }
        for (Info info : list) {
            for (int[] ice : info.ice) {
                if (visited[ice[0]][ice[1]] || temp[ice[0]][ice[1]] == 0) continue;
                after.add(bfs(ice[0], ice[1], info.dir, temp));
            }
        }
        list = after;
        // [2] 이동
        RESULT res = new RESULT();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
            	map[i][j] = 0;
                group[i][j].clear();
            }
        }
        int idx = 0;
        for (Info info : list) {
        	ArrayList<int[]> update = new ArrayList<int[]>(); // remove (X)
            info.min[0] = 100;
            info.min[1] = 100;
            for (int[] ice : info.ice) {
                if (info.min[0] > ice[0] || (info.min[0] == ice[0] && info.min[1] > ice[1])) {
                    info.min[0] = ice[0];
                    info.min[1] = ice[1];
                }
                int dy = (ice[0] + move[info.dir][0] + n) % n;
                int dx = (ice[1] + move[info.dir][1] + n) % n;
                update.add(new int[] {dy, dx});
                group[dy][dx].add(idx);
                if (map[dy][dx] == 0 || map[dy][dx] < temp[ice[0]][ice[1]]) {
                    map[dy][dx] = temp[ice[0]][ice[1]];
                    res.heights[dy][dx] = temp[ice[0]][ice[1]];
                }
            }
            info.ice = update;
            idx++;
        }
        // [3] 병합
        ArrayList<Info> last = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Arrays.fill(visited[i], false);
        }
        for (Info info : list) {
            set.clear();
            for (int[] ice : info.ice) {
                if (visited[ice[0]][ice[1]] || map[ice[0]][ice[1]] == 0) continue;
                last.add(bfs(ice[0], ice[1], info.dir, map));
                if (set.size() > 1) {
                    idx = -1;
                    int size = set.size() - 1;
                    for (int s : set) {
                        if (idx == -1) {
                            idx = s;
                            continue;
                        }
                        if (check(s, idx)) {
                            idx = s;
                        }
                    }
                    last.get(last.size() - 1).dir = list.get(idx).dir;
                }
            }
        }
        list = last;
        return res;
    }
     
}

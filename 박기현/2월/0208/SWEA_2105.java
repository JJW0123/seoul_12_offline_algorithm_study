import java.io.*;
import java.util.*;

public class SWEA_2105 {
    static int N, max; // 디저트 맵 크기, 최대 디저트 개수
    static int[][] map; // 디저트 맵
    static boolean[] visited; // 먹은 디저트 체크
    static int startR, startC; // 시작 좌표

    // 방향 배열
    static int[] dr = {1, 1, -1, -1};
    static int[] dc = {1, -1, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            map = new int[N][N];
            max = -1; 
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
             for(int i=0; i < N -2; i++) {
                for(int j=1; j < N-1; j++) {
                    visited = new boolean[101];
                    startR = i;
                    startC = j;
                    visited[map[i][j]] = true;
                    dfs(i, j, 0, 1);
                }
             }
             System.out.println("#" + test_case + " " + max);
        }
    }
    static void dfs(int r, int c, int dir, int count) {

        // 현재 방향부터 다음 방향 탐색색
        for(int d= dir; d < dir+2 && d <4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];

            // 시작점 돌아왔을 때때
            if(nr==startR && nc == startC && count >= 3) {
                max = Math.max(max, count);
                return;
            }

            // 범위 내에, 아직 먹지 않은 디저트일때때
            if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                if(!visited[map[nr][nc]]) {
                    visited[map[nr][nc]] = true;
                    dfs(nr, nc, d, count + 1);
                    visited[map[nr][nc]] = false;
                }
            }
        }
    }
}
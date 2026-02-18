import java.io.*;
import java.util.*;
// 파이프 옮기기 문제
public class BOJ_17070 {
  static int N;
  static int[][] map;
  static int count;
  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    map = new int[N+1][N+1];
    for (int i = 1; i <= N; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 1; j <= N; j++) {
        map[i][j] = Integer.parseInt(st.nextToken());
      }
    }
    count = 0;
    // 0: 가로, 1: 세로, 2: 대각선
    dfs(1, 2, 0);
    System.out.println(count);
  }
  static void dfs(int r, int c, int state) {
    if(r == N && c == N) {
      count++;
      return;
    }

    // 가로 또는 대각선일 때 가로 c 방향 벽이 아니고, 갔던 곳이 아닌지 확인 후 dfs 다음 c+1로 호출
    if(state == 0 || state == 2) {
      if(c + 1 <= N && map[r][c+1] == 0) {
        dfs(r, c+1, 0);
      }
    }
    // 세로 또는 대각선일 때 세로 r 방향 벽이 아니고, 갔던 곳이 아닌지 확인 후 dfs 다음 r+1로 호출
    if(state == 1 || state == 2) {
      if(r + 1 <= N && map[r+1][c] == 0) {
        dfs(r+1, c, 1);
      }
    }
    // 대각선 일때는 대각선, 가로, 세로 벽이 아니고, 갔던 곳이 아닌지 확인 후 dfs 다음 r+1, c+1로 호출
    if (r + 1 <= N && c + 1 <= N) {
      if(map[r+1][c+1] == 0 && map[r][c+1] == 0 && map[r+1][c] == 0) {
        dfs(r+1, c+1, 2);
      }
    }
  }
}

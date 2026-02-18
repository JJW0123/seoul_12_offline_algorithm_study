import java.io.*;
import java.util.*;
// 키순서 문제

public class SWEA_5643 {
  static int N, M;
  // 나보다 큰 사람, 나보다 작은 사람 추적을 위한 map
  static List<Integer>[] map, reverseMap;
  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine().trim());
    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine().trim());
      M = Integer.parseInt(br.readLine().trim());
      
      // 리스트 초기화
      map = new ArrayList[N+1];
      reverseMap = new ArrayList[N+1];
      for(int i=1; i<=N; i++) {
        map[i] = new ArrayList<>();
        reverseMap[i] = new ArrayList<>();
      }
      
      for(int i=0; i<M; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        map[a].add(b); // a보다 b가 크다.
        reverseMap[b].add(a); // b보다 a가 작다.
      }
      
      int ans = 0;
      for(int i=1; i<=N; i++) {
        // 나보다 큰 사람 + 나보다 작은 사람 = N-1 이면 내 키순서를 알 수 있다.
        if(bfs(i, map) + bfs(i, reverseMap) == N-1) {
          ans++;
        }
      }
      System.out.println("#" + tc + " " + ans);
    }
  }
  // 나보다 큰 사람 또는 작은 사람 추적을 위한 bfs
  // 한 학생을 기준으로 map 또는 reverseMap 중 하나를 탐색하여(모두 방문함) 나보다 큰 사람 또는 작은 사람의 수를 반환한다.
  static int bfs(int start, List<Integer>[] graph) {
    Queue<Integer> queue = new LinkedList<>();
    boolean[] visited = new boolean[N+1];

    queue.add(start);
    visited[start] = true;

    int count = 0;
    while(!queue.isEmpty()) {
      int cur = queue.poll();
      for(int next : graph[cur]) {
        if(!visited[next]) {
          visited[next] = true;
          queue.add(next);
          count++;
        }
      }
    }
    return count;
  }
}

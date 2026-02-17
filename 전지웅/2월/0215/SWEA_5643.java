import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class SWEA_5643 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            int M = Integer.parseInt(br.readLine());

            // 정방향 그래프
            List<List<Integer>> graph = new ArrayList<>();

            // 역방향 그래프
            List<List<Integer>> graph_reverse = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                graph.add(new ArrayList<>());
                graph_reverse.add(new ArrayList<>());
            }

            // 그래프 input
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int num1 = Integer.parseInt(st.nextToken()) - 1;
                int num2 = Integer.parseInt(st.nextToken()) - 1;
                graph.get(num1).add(num2);
                graph_reverse.get(num2).add(num1);
            }

            int res = 0;
            for (int i = 0; i < N; i++) {
                // i 노드부터 시작해서 BFS로 탐색하기
                boolean[] visited = new boolean[N];
                Deque<Integer> deque = new ArrayDeque<>();

                // 자신보다 큰 사람 탐색
                deque.push(i);
                visited[i] = true;
                while (!deque.isEmpty()) {
                    int next = deque.pop();
                    for (int n : graph.get(next)) {
                        if (!visited[n]) {
                            visited[n] = true;
                            deque.push(n);
                        }
                    }
                }

                // 자신보다 작은 사람 탐색
                deque.push(i);
                while (!deque.isEmpty()) {
                    int next = deque.pop();
                    for (int n : graph_reverse.get(next)) {
                        if (!visited[n]) {
                            visited[n] = true;
                            deque.push(n);
                        }
                    }
                }

                // 만약 모든 노드를 탐색했다면 res++
                boolean flag = false;
                for (int j = 0; j < N; j++) {
                    if (!visited[j]) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    res++;
                }
            }
            sb.append("#" + tc + " " + res + "\n");
        }
        System.out.println(sb);
    }
}
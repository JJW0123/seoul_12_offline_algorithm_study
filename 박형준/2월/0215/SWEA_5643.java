import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class SWEA_5643 {
    static int[][] graph;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            int n = Integer.parseInt(br.readLine());
            int m = Integer.parseInt(br.readLine());
            graph = new int[n + 1][n + 1];

            for (int i = 0; i < m; i++) {
                int[] line = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                graph[line[0]][line[1]] = 1;
                graph[line[1]][line[0]] = 2;
            }

            int result = 0;
            for (int i = 1; i <= n; i++) {
                int count = 0;
                int searchA = search(n, i, 1);
                count += searchA;
                int searchB = search(n, i, 2);
                count += searchB;

                if (count == n - 1) {
                    result++;
                }
            }
            sb.append("#").append(tc).append(" ").append(result).append("\n");
        }
        System.out.print(sb);
    }

    private static int search(int n, int student, int condition) {
        visited = new boolean[n + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        deque.add(student);
        visited[student] =true;

        int count = 0;
        while (!deque.isEmpty()) {
            int cur = deque.pollLast();

            for (int i = 1; i <= n; i++) {
                if (!visited[i] && graph[cur][i] == condition) {
                    visited[i] = true;
                    count++;
                    deque.add(i);
                }
            }
        }
        return count;
    }

}

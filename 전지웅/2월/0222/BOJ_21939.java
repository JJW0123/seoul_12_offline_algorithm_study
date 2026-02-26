import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());

        // 난이도순 오름차순, 문제번호 오름차순
        PriorityQueue<int[]> pq_up = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0];
            } else {
                return o1[1] - o2[1];
            }
        });

        // 난이도순 내림차순, 문제번호 내림차순
        PriorityQueue<int[]> pq_down = new PriorityQueue<>((o1, o2) -> {
            if (o2[0] != o1[0]) {
                return o2[0] - o1[0];
            } else {
                return o2[1] - o1[1];
            }
        });

        int[] level = new int[100_001];

        // 레벨, 문제번호 input
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int P = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());
            pq_up.add(new int[] { L, P });
            pq_down.add(new int[] { L, P });
            level[P] = L;
        }

        int M = Integer.parseInt(br.readLine());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            String order = st.nextToken();
            switch (order) {
                case "recommend":
                    int n = Integer.parseInt(st.nextToken());
                    if (n == 1) {
                        while (pq_down.peek()[0] != level[pq_down.peek()[1]]) {
                            pq_down.poll();
                        }
                        sb.append(pq_down.peek()[1]).append("\n");

                    } else {
                        while (pq_up.peek()[0] != level[pq_up.peek()[1]]) {
                            pq_up.poll();
                        }
                        sb.append(pq_up.peek()[1]).append("\n");
                    }
                    break;

                case "add":
                    int P = Integer.parseInt(st.nextToken());
                    int L = Integer.parseInt(st.nextToken());
                    level[P] = L;
                    pq_up.add(new int[] { L, P });
                    pq_down.add(new int[] { L, P });
                    break;

                case "solved":
                    int num = Integer.parseInt(st.nextToken());
                    level[num] = 0;
                    break;
            }
        }
        System.out.println(sb);
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < T; tc++) {
            st = new StringTokenizer(br.readLine(), " ");
            int N = Integer.parseInt(st.nextToken()); // A 크기
            int M = Integer.parseInt(st.nextToken()); // B 크기

            int[] A = new int[N];
            int[] B = new int[M];

            // A input
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
            }

            // B input
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < M; i++) {
                B[i] = Integer.parseInt(st.nextToken());
            }

            // 정렬
            Arrays.sort(A);
            Arrays.sort(B);

            // B보다 큰 A 쌍의 개수
            int sum = 0;

            // b 저장할 포인터
            int next_b = M - 1;

            for (int a = N - 1; a >= 0; a--) {
                for (int b = next_b; b >= 0; b--) {
                    // A가 B보다 크다면 나머지 작은 B들을 sum에 더해주고
                    // A[]는 a-1 인덱스부터, B[]는 b 인덱스부터 다시 탐색
                    if (A[a] > B[b]) {
                        sum += b + 1;
                        next_b = b;
                        break;
                    }
                }
            }
            sb.append(sum + "\n");
        }
        System.out.println(sb);
    }
}

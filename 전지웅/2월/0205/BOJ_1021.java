import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1021 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] arr = new int[M];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            queue.offer(i);
        }

        int length = N;
        int cnt, n, result;
        cnt = n = result = 0;
        while (n < M) {
            int next = queue.peek();
            if (next == arr[n]) {
                result += cnt > length / 2 ? length - cnt : cnt;
                n++;
                length--;
                queue.poll();
                cnt = 0;
            } else {
                queue.offer(queue.poll());
                cnt++;
            }
        }

        System.out.println(result);
    }
}

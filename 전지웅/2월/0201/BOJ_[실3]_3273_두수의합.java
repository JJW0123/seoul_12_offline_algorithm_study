import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 수열 길이
        int N = Integer.parseInt(br.readLine());

        // int 배열에 수열 넣기
        int[] arr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 배열 정렬
        Arrays.sort(arr);

        // 목표값
        int M = Integer.parseInt(br.readLine());

        // 투포인터 알고리즘
        int s, e, sum, cnt;
        s = sum = cnt = 0;
        e = N - 1;
        while (s < e) {
            sum = arr[s] + arr[e];
            if (sum == M) {
                cnt++;
                s++;
                e--;
            } else if (sum > M) {
                e--;
            } else {
                s++;
            }
        }
        System.out.println(cnt);
    }
}
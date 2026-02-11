import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_1475 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 필요한 숫자판을 저장해둘 배열
        int[] arr = new int[10];

        // 암호 입력받기
        int[] input = Arrays.stream(br.readLine().split("")).mapToInt(Integer::parseInt).toArray();
        for (int n : input) {
            arr[n]++;
        }

        int max = 0;

        // 6과 9는 뒤집어서 사용 가능
        arr[6] = (arr[6] + arr[9] + 1) / 2;

        // 필요한 세트 수 구하기
        for (int i = 0; i < 9; i++) {
            max = Math.max(max, arr[i]);
        }
        System.out.println(max);
    }
}
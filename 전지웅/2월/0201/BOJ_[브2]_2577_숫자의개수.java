import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int a, b, c;
        a = Integer.parseInt(br.readLine());
        b = Integer.parseInt(br.readLine());
        c = Integer.parseInt(br.readLine());

        // 문자열 변환하기
        String result = a * b * c + "";

        // 숫자 개수 세기
        int[] arr = new int[10];
        for (int i = 0; i < result.length(); i++) {
            arr[result.charAt(i) - '0']++;
        }

        // 하나씩 출력
        for (int n : arr) {
            System.out.println(n);
        }
    }
}
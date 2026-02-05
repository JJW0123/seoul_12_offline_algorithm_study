import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 인원수
        int N = Integer.parseInt(st.nextToken());

        // 제거 순서
        int K = Integer.parseInt(st.nextToken());

        // 리스트에 인원수만큼 추가
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            list.add(i);
        }

        sb.append("<");

        int cnt = 0; // 제거 순서 세는 변수
        int len = list.size();
        while (!list.isEmpty()) {
            for (int i = 0; i < len; i++) {
                // 제거 순서라면
                if (++cnt % K == 0) {
                    // 리스트에서 인원 제거하기
                    sb.append(list.remove(i) + ", ");

                    // 반복문에 쓰이는 리스트 크기 재조정
                    len = list.size();

                    // 리스트는 제거하면 앞으로 땡겨지니까 i 줄여주기
                    --i;
                }
            }
        }
        sb.delete(sb.length() - 2, sb.length()).append(">");
        System.out.println(sb);
    }
}
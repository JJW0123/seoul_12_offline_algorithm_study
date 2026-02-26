import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken()); // 구역의 개수
        int Q = Integer.parseInt(st.nextToken()); // 쿼리의 개수

        // 명소가 몇 번인지 저장
        TreeSet<Integer> set = new TreeSet<>();

        // 명소 input
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i <= N; i++) {
            if (st.nextToken().equals("1")) {
                set.add(i);
            }
        }

        // 현재 위치
        int where = 1;

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            String order = st.nextToken();
            switch (order) {
                case "1":
                    int n = Integer.parseInt(st.nextToken());
                    // 이미 명소라면 명소 해제, 명소가 아니라면 명소 지정
                    if (set.contains(n)) {
                        set.remove(n);
                    } else {
                        set.add(n);
                    }
                    break;

                case "2":
                    // 현재 위치 이동
                    int x = Integer.parseInt(st.nextToken());
                    where = (x + where - 1) % N + 1;
                    break;

                case "3":
                    // 명소가 없다면 -1 리턴
                    if (set.isEmpty()) {
                        sb.append(-1 + "\n");
                        continue;
                    }
                    // 현재 위치보다 큰 숫자에 명소가 있다면
                    if (set.ceiling(where) != null) {
                        // 명소 - 현재위치 리턴
                        sb.append(set.ceiling(where) - where + "\n");
                    } else { // 현재 위치보다 작은 숫자에 명소가 있다면
                        // 현재 위치에서 1번 위치까지 가는 거리 + 첫 번째 명소 위치 리턴
                        sb.append(N - where + set.first() + "\n");
                    }
                    break;
            }
        }
        System.out.println(sb);
    }
}
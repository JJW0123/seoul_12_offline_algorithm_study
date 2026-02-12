import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class BOJ_5430 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Deque<Integer> deque = new ArrayDeque<>();
        StringBuilder sb;

        int tc = Integer.parseInt(br.readLine());

        for (int i = 0; i < tc; i++) {
            try {
                boolean R = false;
                String[] next = br.readLine().split("");
                br.readLine();

                String[] nums = br.readLine().replaceAll("[\\[\\]]", "").split(",");
                for (String str : nums) {
                    if (!str.equals("")) {
                        deque.offer(Integer.parseInt(str));
                    }
                }

                for (String str : next) {
                    if (str.equals("R")) {
                        R = !R;
                    } else {
                        int a = R ? deque.removeLast() : deque.removeFirst();
                    }
                }

                sb = new StringBuilder("[");
                while (!deque.isEmpty()) {
                    sb.append(R ? deque.removeLast() : deque.removeFirst()).append(",");
                }

                if (sb.length() != 1) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append("]");
                System.out.println(sb);

            } catch (Exception e) {
                System.out.println("error");
            }
        }
    }
}

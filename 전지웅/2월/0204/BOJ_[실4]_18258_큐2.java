import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        String s, last = null;
        Queue<String> queue = new LinkedList<>();

        int n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            s = st.nextToken();

            if (s.equals("push")) {
                last = st.nextToken();
                queue.offer(last);

            } else if (s.equals("pop")) {
                sb.append(queue.isEmpty() ? "-1\n" : queue.poll() + "\n");

            } else if (s.equals("size")) {
                sb.append(queue.size() + "\n");

            } else if (s.equals("empty")) {
                sb.append(queue.isEmpty() ? "1\n" : "0\n");

            } else if (s.equals("front")) {
                sb.append(queue.isEmpty() ? "-1\n" : queue.peek() + "\n");

            } else if (s.equals("back")) {
                sb.append(queue.isEmpty() ? "-1\n" : last + "\n");
            }
        }
        System.out.println(sb);
    }
}
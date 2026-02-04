import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class BOJ_18258_큐2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        Deque<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            String command = st.nextToken();
            switch (command) {
                case "push":
                    queue.addLast(Integer.valueOf(st.nextToken()));
                    break;
                case "pop":
                    Integer value = queue.pollFirst();
                    sb.append(value == null ? -1 : value).append("\n");
                    break;
                case "size":
                    sb.append(queue.size()).append("\n");
                    break;
                case "empty":
                    sb.append(queue.isEmpty() ? 1 : 0).append("\n");
                    break;
                case "front":
                    Integer peek = queue.peekFirst();
                    sb.append(peek == null ? -1 : peek).append("\n");
                    break;
                case "back":
                    Integer peekLast = queue.peekLast();
                    sb.append(peekLast == null ? -1 : peekLast).append("\n");
                    break;
            }
        }
        System.out.println(sb);
    }
}

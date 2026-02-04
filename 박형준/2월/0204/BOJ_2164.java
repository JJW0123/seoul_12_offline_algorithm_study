import java.util.*;

public class BOJ_2164 {
    public static void main(String[] args) {
        int n = new Scanner(System.in).nextInt();

        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            deque.add(i);
        }

        while (deque.size() > 1) {
            deque.pollFirst();
            deque.add(deque.pollFirst());
        }

        System.out.println(deque.peek());
    }
}

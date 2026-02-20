package feb._0205;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Boj_1021 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            deque.add(i);
        }

        int current = deque.peekFirst();
        int result = 0;
        for (int i = 0; i < m; i++) {
            int number = sc.nextInt();
            int count = 0;
            int value = 0;
            while ((value = deque.pollFirst()) != number) {
                count++;
                deque.add(value);
            }

            if (count > deque.size() + 1 - count) {
                count = deque.size() + 1 - count;
            }

            result += count;
        }

        System.out.println(result);
    }
}

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        LinkedList<Integer> deque = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            deque.add(i);
        }

        st = new StringTokenizer(br.readLine());
        int totalCount = 0;

        for (int i = 0; i < m; i++) {
            int target = Integer.parseInt(st.nextToken());

            int targetIdx = deque.indexOf(target);
            int halfIdx = deque.size() / 2;

            if (targetIdx <= halfIdx) {
                for (int j = 0; j < targetIdx; j++) {
                    deque.addLast(deque.pollFirst());
                    totalCount++;
                }
            } 
            else {
                for (int j = 0; j < deque.size() - targetIdx; j++) {
                    deque.addFirst(deque.pollLast());
                    totalCount++;
                }
            }
            
            deque.pollFirst();
        }

        System.out.println(totalCount);
    }
}
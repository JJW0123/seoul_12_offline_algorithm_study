import java.io.*;
import java.util.*;

public class BOJ_2493 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        Stack<int[]> stack = new Stack<>();
        for (int i = 1; i <= n; i++) {
            int h = Integer.parseInt(st.nextToken());
            while (!stack.isEmpty() && stack.peek()[0] < h) {
                stack.pop();
            }
            sb.append(stack.isEmpty() ? 0 : stack.peek()[1]).append(" ");
            stack.push(new int[]{h, i});
        }
        System.out.println(sb.toString().trim());
    }
}
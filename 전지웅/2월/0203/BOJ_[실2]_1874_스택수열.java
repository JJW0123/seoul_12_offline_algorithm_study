import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        Stack<Integer> stack = new Stack<>();
        int[] seq = new int[N];

        for (int i = 0; i < N; i++) {
            seq[i] = Integer.parseInt(br.readLine());
        }

        int stack_num = 0;
        int seq_num = 0;

        while (stack_num < N) {
            stack.push(++stack_num);
            sb.append("+\n");
            while (!stack.isEmpty() && (stack.peek() == seq[seq_num])) {
                sb.append("-\n");
                stack.pop();
                seq_num++;
            }
        }

        String res = stack.empty() ? sb.toString() : "NO";
        System.out.println(res);
    }
}
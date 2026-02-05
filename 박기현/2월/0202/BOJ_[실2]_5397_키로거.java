import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            result.append(solution(str)).append("\n");
        }
        System.out.print(result);
        br.close();
    }

    public static String solution(String str) {
        Stack<Character> left = new Stack<>();
        Stack<Character> right = new Stack<>();
        
        for (char c : str.toCharArray()) {
            switch (c) {
                case '<':
                    if (!left.isEmpty()) right.push(left.pop());
                    break;
                case '>':
                    if (!right.isEmpty()) left.push(right.pop());
                    break;
                case '-':
                    if (!left.isEmpty()) left.pop();
                    break;
                default:
                    left.push(c);
                    break;
            }
        }

        StringBuilder sb = new StringBuilder();
        
        while (!left.isEmpty()) {
            right.push(left.pop());
        }
        
        while (!right.isEmpty()) {
            sb.append(right.pop());
        }
        
        return sb.toString();
    }
}
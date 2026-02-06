import java.io.*;
import java.util.*;

public class BOJ_1874 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        int n = Integer.parseInt(br.readLine());
        Stack<Integer> stack = new Stack<>();
        
        int start = 0;
        
        while (n-- > 0) {
            int value = Integer.parseInt(br.readLine());
            
            if (value > start) {
                for (int i = start + 1; i <= value; i++) {
                    stack.push(i);
                    sb.append("+\n");
                }
                start = value;
            } 
            
            else if (stack.peek() != value) {
                System.out.println("NO");
                return;
            }
            
           
            stack.pop();
            sb.append("-\n");
        }
        
        System.out.println(sb.toString());
    }
}
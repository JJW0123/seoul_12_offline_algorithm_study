import java.io.*;
import java.util.*;
public class BOJ_1874 {
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());
        int[] command = new int[n];
        for(int i = 0; i < n; i++){
            command[i]= Integer.parseInt(br.readLine());
        }
        int cur = 1;
        int idx = 0;
        Stack<Integer> stacks = new Stack<>();
        int last= 0;
        int p = 0;
        boolean possible = true;
        while(p < n){
            for(;cur<=command[idx];cur++){
                stacks.push(cur);
                sb.append("+\n");
            }
            last = stacks.pop();
            if(last == command[idx]){
                idx+=1;
                sb.append("-\n");
                p+=1;
            }else{
                possible = false;
                break;
            }
        }
        if(possible){
            System.out.println(sb.toString());
        }else{
            System.out.println("NO");
        }
    }
}

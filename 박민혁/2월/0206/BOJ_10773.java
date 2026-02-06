import java.util.*;
import java.io.*;

public class BOJ_10773 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Stack<Integer> stacks = new Stack<>(); 
        int minus = 0, sum = 0;
        for(int i = 0; i < n; i++){
            int command = Integer.parseInt(br.readLine());
            if(command==0 && !stacks.isEmpty()){
                minus+= stacks.pop();
            }else{
                stacks.push(command);
                sum+= command;
            }
        }
        System.out.println(sum-minus);
    }   
}

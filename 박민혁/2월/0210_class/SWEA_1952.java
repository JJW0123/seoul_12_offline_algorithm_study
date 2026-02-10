import java.io.*;
import java.util.*;
public class SWEA_1952 {
    static int best;
    static int[] costs= new int[4];
    static int[] plans = new int[12];
    static void dfs(int month, int sum){
        if(month>11){
            best= Math.min(best,sum);
            return;
        }
        if(costs[0]* plans[month] > costs[1]) dfs(month+1, sum+ costs[1]);
        else dfs(month+1, sum + costs[0]* plans[month]);
        dfs(month+3, sum + costs[2]);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        int t = Integer.parseInt(br.readLine());
        for(int tc = 1; tc<= t; tc++){
            st= new StringTokenizer(br.readLine());
            for(int i = 0; i< 4; i++){
                costs[i]= Integer.parseInt(st.nextToken());
            }
            st= new StringTokenizer(br.readLine());
            for(int i = 0; i <12; i++){
                plans[i]= Integer.parseInt(st.nextToken());
            }
            best= costs[3];
            dfs(0,0);
            System.out.printf("#%d %d\n", tc, best);

        }
    }
}

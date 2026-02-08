import java.io.*;
import java.util.*;
public class SWEA_5215 {

    static int N, L, Best;
    static int[] Scores, Cals;
    static void dfs(int idx, int scoreSum, int calSum){
        if(calSum > L) return;
        if(idx == N){
            if(scoreSum > Best) Best = scoreSum;
            return;
        }
        dfs(idx+1, scoreSum + Scores[idx], calSum + Cals[idx]);
        dfs(idx+1, scoreSum, calSum);
    }
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t;
        t = Integer.parseInt(br.readLine());
        for(int tc = 1; tc<=t; tc++){
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());
            Scores= new int[N];
            Cals = new int[N];
            for(int i = 0; i < N; i++){
                st= new StringTokenizer(br.readLine());
                Scores[i]= Integer.parseInt(st.nextToken());
                Cals[i]= Integer.parseInt(st.nextToken());
            }
            Best = 0;
            dfs(0, 0, 0);
            System.out.printf("#%d %d\n",tc, Best);
        }
    }
}

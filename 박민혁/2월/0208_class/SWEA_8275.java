import java.util.*;
import java.io.*;
public class SWEA_8275 {
    static class Cond {
        int l, r, s; // 0-indexed로 저장
        Cond(int l, int r, int s) { this.l = l; this.r = r; this.s = s; }
    }
    static int N, X, M;
    static Cond[] conds;

    static int[] cur, best;
    static int bestSum;
    
    static boolean isit(int[] arr, Cond[] conds){
        for(Cond c : conds){
            int s = 0;
            for(int i = c.l; i <= c.r ; i++){
                s += arr[i];
            }
            if(s != c.s) return false;
        }
        return true;
    }
    
    static boolean isItBetter(int[] arr){
        int sum = 0;
        for(int v :arr) sum += v;
        if(sum > bestSum){
            bestSum = sum;
            best = arr.clone();
            return true;
        }
        return false;
    }

    static void dfs(int idx){
        if(idx == N){
            if(isit(cur, conds)){
                isItBetter(cur);
            }
            return;
        }
        for(int i = 0; i<= X; i++){
            cur[idx] = i;
            dfs(idx+1);
        }
        return;
    }


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());
        for(int tc= 1; tc <= t; tc++){
            st= new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            conds = new Cond[M];
            for(int i= 0; i < M;i++){
                st= new StringTokenizer(br.readLine());
                int l = Integer.parseInt(st.nextToken()) - 1;
                int r = Integer.parseInt(st.nextToken()) - 1;
                int s = Integer.parseInt(st.nextToken());
                conds[i] = new Cond(l, r, s);
            }
            cur = new int[N];
            best = new int[N];
            bestSum = -1;
            dfs(0);
            System.out.print("#" + tc + " ");
            if(bestSum== -1){
                System.out.println("-1");
            }else{
                for(int i = 0; i< N; i++){
                    System.out.printf("%d ", best[i]);
                }
                System.out.println();
            }
        }
        
    }
}

import java.io.*;
import java.util.*;
public class SWEA_3421 {
    static boolean[] visited;
    static boolean[][] dontMix;
    static int N, M;
    static int totalCases;

    static void mix(int idx){
        if(idx == N){
            totalCases++;
            return;
        }

        boolean dontMixFlag = false;
        for(int i = 0; i< idx; i++){
            if(visited[i] && dontMix[idx][i]){
                dontMixFlag = true;
                break;
            }
        }
        if(!dontMixFlag){
            visited[idx]= true;
            mix(idx+1);
            visited[idx]= false;
        }
        mix(idx+1);
    }
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());
        for(int tc = 1; tc <= t; tc++){
            st= new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            visited = new boolean[N];
            dontMix = new boolean[N][N];
            for(int i = 0; i< M; i++){
                st= new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken())-1;
                int b = Integer.parseInt(st.nextToken())-1;
                dontMix[a][b]= true;
                dontMix[b][a]= true;
            }
            totalCases = 0;
            mix(0);
            System.out.printf("#%d %d\n", tc, totalCases);
        }
    }
}

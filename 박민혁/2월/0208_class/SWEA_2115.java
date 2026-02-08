import java.io.*;
import java.util.*;

public class SWEA_2115 {
    static int N, M, C;
    static int[][] honeys;
    static int[][] Best;

    static void findMaxHoney(int row, int startCol, int cur_col, int sum, int score){
        if(cur_col== startCol + M){
            if(score > Best[row][startCol]){
                Best[row][startCol]= score;
            }
            return;
        }
        findMaxHoney(row, startCol, cur_col+1, sum, score);
        if(sum + honeys[row][cur_col] <= C) findMaxHoney(row, startCol, cur_col+1, sum + honeys[row][cur_col], score + honeys[row][cur_col]*honeys[row][cur_col]);
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t= Integer.parseInt(br.readLine());
        for(int tc = 1; tc<= t; tc++){
            int answer = 0;
            st= new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            honeys  = new int[N][N];
            for(int i = 0; i< N; i++){
                st= new StringTokenizer(br.readLine());
                for(int j = 0; j< N; j++){
                    honeys[i][j]= Integer.parseInt(st.nextToken());
                }
            }
            Best= new int[N][N-M+1];
            for(int i = 0; i< N; i++){
                for(int j = 0; j< N-M+1; j++){
                    findMaxHoney(i,j,j,0,0);
                }
            }
            int max = 0;
            for(int i = 0; i< N; i++){
                for(int j = i+1; j< N; j++){
                    for(int c1 = 0; c1< N-M+1; c1++){
                        for(int c2 = 0; c2< N-M+1; c2++){
                            max = Math.max(max, Best[i][c1] + Best[j][c2]);
                        }
                    }
                }
            }
            for(int i = 0; i< N; i++){
                for(int c1 = 0; c1 < N-M+1; c1++){
                    for(int c2= c1+ M; c2< N-M+1; c2++){
                        max = Math.max(max, Best[i][c1]+ Best[i][c2]);
                    }
                }
            }
            System.out.printf("#%d %d\n", tc, max);
        }
    }
}

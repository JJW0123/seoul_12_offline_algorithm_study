import java.util.*;
import java.io.*;
public class SWEA_4130 {
    static int[][] magnets = new int[4][8];
    static int[] cur_idx;
    static int getLeftIdx(int magIdx){
        return (cur_idx[magIdx] + 6) % 8;
    }
    static int getRightIdx(int magIdx){
        return (cur_idx[magIdx] +2) % 8;
    }
    static void rotate(int magIdx, int dir){
        cur_idx[magIdx] = (cur_idx[magIdx] -dir + 8) % 8;
    }
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());
        for(int tc = 1; tc <= t; tc++){
            int k = Integer.parseInt(br.readLine());
            cur_idx = new int[4];
            for(int i = 0; i< 4; i++){
                st= new StringTokenizer(br.readLine());
                for(int j = 0; j< 8; j++){
                    magnets[i][j]= Integer.parseInt(st.nextToken());
                }
            }
            for(int j = 0; j< k; j++){
                st= new StringTokenizer(br.readLine());
                int magIdx = Integer.parseInt(st.nextToken())-1;
                int dir = Integer.parseInt(st.nextToken());
                int[] rotateDirs = new int[4];
                rotateDirs[magIdx] = dir;
                for(int i = magIdx-1; i>=0; i--){
                    if(magnets[i][getRightIdx(i)] != magnets[i+1][getLeftIdx(i+1)]){
                        rotateDirs[i]= -rotateDirs[i+1];
                    }else{
                        break;
                    }
                } 
                for(int i = magIdx+1; i< 4; i++){
                    if(magnets[i][getLeftIdx(i)] != magnets[i-1][getRightIdx(i-1)]){
                        rotateDirs[i]= -rotateDirs[i-1];
                    }else{
                        break;
                    }
                }
                for(int i = 0; i<4; i++){
                    if(rotateDirs[i] !=0){
                        rotate(i, rotateDirs[i]);
                    }
                }
            }
            int answer = 0;
            int score = 1;
            for(int i = 0; i<4; i++){
                int topIdx = cur_idx[i];
                answer+= magnets[i][topIdx]*score;
                score*=2;
            }
            System.out.printf("#%d %d\n", tc, answer);
        }
    }
}

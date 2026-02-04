import java.util.*;
import java.io.*;
public class SWEA_6808 {
    static int[] gyu = new int[9];
    static int[] iny = new int[9];
    static boolean[] used = new boolean[9];
    static int win, lose;
    static void dfs(int idx, int gyuScore, int inyScore){
        if(idx== 9){
            if(gyuScore > inyScore)win++;
            else if(gyuScore < inyScore)lose++;
            return;
        }
        for(int i = 0; i< 9; i++){
            if(used[i]==true) continue;
            used[i]= true;
            int g = gyu[idx];
            int in = iny[i];
            int sum = g + in;
            if(g > in) dfs(idx+1, gyuScore + sum, inyScore);
            else if(g < in) dfs(idx+1, gyuScore, inyScore+sum);
            used[i]= false;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());
        
        for(int tc = 1; tc<= t; tc++){
            st= new StringTokenizer(br.readLine());
            
            boolean[] has = new boolean[19];
            for(int i = 0; i < 9; i++){
                gyu[i]= Integer.parseInt(st.nextToken());
                used[i]= false;
                has[gyu[i]]=true;
            }
            int idx= 0;
            for(int x = 1; x<= 18; x++){
                if(!has[x]){
                    iny[idx++]= x;
                }
            }
            win= 0;
            lose = 0;
            dfs(0, 0, 0);
            System.out.printf("#%d %d %d\n",tc,win,lose);
        }
    }
}

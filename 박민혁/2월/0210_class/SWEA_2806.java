import java.io.*;

public class SWEA_2806 {
    static boolean[] col, left, right;
    static int n;
    static int can;
    static void dfs(int x, int count){
        if(count == n){
            can+= 1;
            return;
        }
        for(int c = 0; c < n; c++){
            if(!col[c]&& !left[x+c]&& !right[x-c+n-1]){
                col[c]= left[x+c]= right[x-c+n-1]= true;
                dfs(x+1, count+1);
                col[c]= left[x+c]= right[x-c+n-1]= false;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        for(int tc= 1; tc<=t; tc++){
            n = Integer.parseInt(br.readLine());
            col = new boolean[n];
            left = new boolean[2*n -1]; // 0 0 -> 0
            right = new boolean[2*n -1]; // 0 n-1 -> 0, 0 0 -> n-1 
            can = 0;
            dfs(0,0);
            System.out.printf("#%d %d\n", tc, can);
        }
    }
}

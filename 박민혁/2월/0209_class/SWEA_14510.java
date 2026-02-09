import java.io.*;
import java.util.*;
public class SWEA_14510 {
    static int N;
    static int[] trees;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());
        for(int tc = 1; tc <= t; tc++){
            N = Integer.parseInt(br.readLine());
            st= new StringTokenizer(br.readLine());
            trees= new int[N];
            int mx= 0;
            for(int i = 0; i < N; i++){
                trees[i]= Integer.parseInt(st.nextToken());
                if(trees[i]> mx) mx= trees[i];
            }
            int two = 0;
            int one = 0;
            for(int i = 0; i < N; i++){
                trees[i]= mx- trees[i];
                if(trees[i]>0){
                    two+= trees[i]/2;
                    one+= trees[i]%2;
                }
            }
            int min_day = 10000000;
            while(one<two){
                min_day = Math.min(min_day, one*2 + (two-one)*2);
                two--;
                one+=2;
            }
            min_day = Math.min(min_day, two*2 + (one-two)*2-1);
            System.out.printf("#%d %d\n", tc, min_day);
        }
    }
}

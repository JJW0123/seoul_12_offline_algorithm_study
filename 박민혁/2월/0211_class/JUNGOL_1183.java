import java.io.*;
import java.util.*;
public class JUNGOL_1183 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int w =Integer.parseInt(br.readLine());
        StringTokenizer st;
        int[] coins = new int[6];
        int[] costs = {500, 100, 50, 10, 5, 1};
        int total = 0;
        st= new StringTokenizer(br.readLine());
        for(int i = 0; i< 6; i++){
            coins[i]= Integer.parseInt(st.nextToken());
            total+= coins[i]*costs[i];
        }
        total-= w;
        int[] ans = new int[6];
        int idx =0;
        int q = 0;
        int cnt =0;
        int[] origin = coins.clone();
        while(total>0){
            q=0;
            if(coins[idx]>0){
                q= Math.min( total/ costs[idx], coins[idx]); //total/ costs[idx] -> 이거 내림인가?
            }
            total-= q*costs[idx];
            ans[idx]= q;
            coins[idx]-= q;
            idx+=1;
        }
        for(int i = 0; i < 6; i++){
            cnt+= origin[i]- ans[i];
        }
        System.out.println(cnt);
        for(int i = 0; i < 6; i++){
            System.out.printf("%d ", origin[i]- ans[i]);
        }
    }
}

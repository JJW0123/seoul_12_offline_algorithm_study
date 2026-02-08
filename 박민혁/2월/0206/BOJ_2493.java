import java.io.*;
import java.util.*;

public class BOJ_2493 {
    static class Tower{
        int idx;
        int h;
        public Tower(int idx, int h){
            this.idx= idx;
            this.h= h;
        }
    }
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] buildings = new int[n];
        StringTokenizer st= new StringTokenizer(br.readLine());
        int[] answer= new int[n];
        for(int i = 0;i <n; i++){
            buildings[i]= (Integer.parseInt(st.nextToken()));
        }
        int cur = 0;
        int idx = 1;
        if(n==1){
            answer[0]=0;
            System.out.println(Arrays.toString(answer));
            return;
        }
        while(cur < n){
            if(idx== n){
                break;
            }
            if(buildings[cur]< buildings[idx]){
                cur+=1;
                idx+=1;
            }
            else{
                answer[idx]= cur+1;
                idx+=1;
            }
        }
        System.out.println(Arrays.toString(answer));
    }
}

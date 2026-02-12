import java.io.*;
import java.util.*;
public class SWEA_4796 {
    static int[] hs;
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int t= sc.nextInt();
        int n;
        for(int tc = 1; tc <= t; tc++){
            n = sc.nextInt();
            hs = new int[n];
            for(int i = 0; i < n ; i++){
                hs[i]= sc.nextInt();
            }
            int ans = 0;
            int[] left = new int[n];
            int[] right = new int[n];
            for(int i = 0; i< n; i++){
                left[i]= 1;
                right[i]= 1;
            }
            for(int i = 1; i < n; i++){
                if(hs[i]> hs[i-1]){
                    left[i]+=left[i-1];
                }
            }
            for(int i = n-2; i >-1; i--){
                if(hs[i] > hs[i+1]){
                    right[i]+= right[i+1];
                }
            }
            for(int i = 0; i < n;i++){
                System.out.printf("%d ", left[i]);
            }
            System.out.println();
            for(int i = 0; i < n;i++){
                System.out.printf("%d ", right[i]);
            }
            System.out.println();
            for(int i = 1; i < n-1; i++){
                if(left[i]>1 && right[i]> 1){
                    ans+= (left[i]-1)* (right[i]-1);
                }
            }
            System.out.printf("#%d %d\n", tc, ans);
        }
        sc.close();
    }
}

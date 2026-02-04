import java.util.*;
import java.io.*;
public class BOJ_3273 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        int [] arr= new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i<n; i++){
            arr[i]= Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);
        int m = Integer.parseInt(br.readLine());
        int cnt = 0;
        int start = 0;
        int end = n-1;
        while(start < end){
            int sum = arr[start]+ arr[end];
            if(sum == m){
                cnt+=1;
                start++;
                end--;
            }
            else if(sum > m){
                end-=1;
            }
            else{
                start+=1;
            }
        }
        System.out.println(cnt);
    }
}

import java.io.*;
public class SWEA2805 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n, cut, dir;
        int t= Integer.parseInt(br.readLine());
        for(int tc= 1; tc<=t; tc++){
            n= Integer.parseInt(br.readLine());
            int ans = 0;
            cut = n/2;
            dir = -1;
            for(int i = 0; i < n; i++){
                String line = br.readLine();
                for(int a = cut; a < n - cut; a++){
                    ans+= line.charAt(a)- '0';
                }  
                if(cut-1 < 0) dir =1; 
                cut+= dir;
            }
            System.out.printf("#%d %d\n",tc,ans);
        }
    }
}

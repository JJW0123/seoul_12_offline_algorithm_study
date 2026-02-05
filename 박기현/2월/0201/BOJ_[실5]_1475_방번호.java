import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] count = new int[10];
        String N = br.readLine();
        for(int i =0; i < N.length(); i++) {
            int num = N.charAt(i) - '0';
            count[num]++;
        }
        int sn = count[6] + count[9];
        count[6] = (sn + 1) / 2;
        count[9] = 0;
        int maxSet = 0;
        for(int i=0; i<9; i++){
            maxSet = Math.max(maxSet, count[i]);
        }
        System.out.println(maxSet);
    }
}
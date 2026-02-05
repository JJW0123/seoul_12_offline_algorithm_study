import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        int A = Integer.parseInt(br.readLine());
        int B = Integer.parseInt(br.readLine());
        int C = Integer.parseInt(br.readLine());
        int countZero;
        
        int sum = A*B*C;
        int[] count = new int[10];
        while (sum > 0) {
            int d = sum % 10; // 마지막 자리 숫자 추출
            count[d]++; // 해당 숫자 카운트 증가
            sum /= 10; // 마지막 자리 제거
        }
        for(int i=0; i<10; i++) {
            sb.append(count[i]).append("\n");
        }
        System.out.println(sb.toString());
    }
}
import java.util.Scanner;
public class BOJ_1475 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] record = new int[10];
        while(n>0){
            int idx = n%10;
            if(idx== 9) idx= 6;
            record[idx]+=1;
            n/=10;
        }
        
        record[6]= (record[6]+1)/2;
        
        int answer= 0;
        for(int i : record){
            if(answer < i){
                answer= i;
            }
        }
        System.out.print(answer);
        sc.close();

    }    
}

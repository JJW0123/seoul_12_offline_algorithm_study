import java.util.Scanner;
public class BOJ_2577 {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        int[] record = new int[10];
        int x = a*b*c;
        while(x > 0){
            record[x%10]+=1;
            x/=10;
        }
        for(int i = 0; i<10; i++){
            System.out.println(record[i]);
        }
        sc.close();
    }
}

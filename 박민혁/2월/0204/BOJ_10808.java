import java.util.Scanner;
public class BOJ_10808 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String in = sc.nextLine();
        int[] record = new int [26];
        for (int i = 0; i < in.length(); i++){
            record[(int)(in.charAt(i))-(int)'a']+=1;
        } 
        for(int i = 0; i< 26; i++){
            System.out.print(record[i]+" ");
        }


        sc.close();
    }
}

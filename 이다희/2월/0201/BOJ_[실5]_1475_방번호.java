import java.util.Scanner;

class Main {
	
	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] cnt = new int[9];
		int answer = 0;
		
		while (n > 0) {
			if (n % 10 == 9) cnt[6]++;
			else cnt[n % 10]++;
			n /= 10;
		}
		
		for (int i = 0; i < 9; i++) {
			if (i == 6) cnt[i] = (cnt[i] / 2) + (cnt[i] % 2);
			if (answer < cnt[i]) answer = cnt[i];
		}
		
		System.out.println(answer);
		
	}
	
}

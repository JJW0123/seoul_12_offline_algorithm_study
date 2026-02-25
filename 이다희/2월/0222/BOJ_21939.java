import java.io.*;
import java.util.*;

class Problem implements Comparable<Problem> {
	
	int P, L;
	
	public Problem(int P, int L) {
		this.P = P;
		this.L = L;
	}

	@Override
	public int compareTo(Problem o) {
		if (this.L == o.L) {
			return this.P - o.P;
		}
		return this.L - o.L;
	}
	
}

public class Main {
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        
        int N = Integer.parseInt(br.readLine());
        TreeSet<Problem> set = new TreeSet<>();
        int P, L;
        int[] arr = new int[100001];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            P = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());
            set.add(new Problem(P, L));
            arr[P] = L;
        }
        int M = Integer.parseInt(br.readLine());
        String str;
        int x;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            str = st.nextToken();
            switch (str) {
            case "recommend":
                x = Integer.parseInt(st.nextToken());
                if (x == 1) {
                	sb.append(set.last().P);
                } else {
                	sb.append(set.first().P);
                }
                sb.append("\n");
                break;
            case "add":
                P = Integer.parseInt(st.nextToken());
                L = Integer.parseInt(st.nextToken());
                set.add(new Problem(P, L));
                arr[P] = L;
                break;
            case "solved":
                P = Integer.parseInt(st.nextToken());
                L = arr[P];
                set.remove(new Problem(P, L));
                arr[P] = 0;
                break;
            }
        }
        
        System.out.println(sb);
        
    }

}

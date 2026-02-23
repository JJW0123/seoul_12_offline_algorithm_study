import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main{

	public static void main(String[] args) throws IOException {
		// 한 전화번호가 다른 전화번호에 포함되어 있다면 NO, 아니라면 YES
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int t=0; t<T; t++) {
			int N = Integer.parseInt(br.readLine());
			PriorityQueue<String> pq = new PriorityQueue<>((o1, o2) -> o1.compareTo(o2));
			for(int i=0; i<N; i++) {
				pq.add(br.readLine());
			}
			boolean flag = false;
			
			while(pq.size() > 1) {
				String str = pq.poll();
				for(String s : pq) {
					if(s.startsWith(str)) {
						flag = true;
					}else {
						break;
					}
				}
				if(flag) break;
			}
			if(flag) {
				sb.append("NO\n");
			}else {
				sb.append("YES\n");
			}
		}
		System.out.println(sb);
	}
}

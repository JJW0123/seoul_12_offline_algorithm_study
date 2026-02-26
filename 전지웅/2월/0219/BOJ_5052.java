import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int t = 0; t < T; t++) {

			int N = Integer.parseInt(br.readLine());

			// 우선순위큐로 문자열 정렬
			PriorityQueue<String> pq = new PriorityQueue<>((o1, o2) -> o1.compareTo(o2));

			// input
			for (int i = 0; i < N; i++) {
				pq.add(br.readLine());
			}

			// 일관성 판단할 boolean 변수
			boolean flag = false;

			while (pq.size() > 1) {
				String str = pq.poll();
				for (String s : pq) {
					// 만약 일관성에 위배된다면 반복문 탈출
					if (s.startsWith(str)) {
						flag = true;
					} else {
						break;
					}
				}
				if (flag)
					break;
			}

			if (flag) {
				sb.append("NO\n");
			} else {
				sb.append("YES\n");
			}
		}
		System.out.println(sb);
	}
}

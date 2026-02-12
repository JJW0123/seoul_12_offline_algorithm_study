import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
        	st = new StringTokenizer(br.readLine());
        	int N = Integer.parseInt(st.nextToken()); // 맵 크기
        	String dir = st.nextToken(); // 이동 방향

        	// 맵 저장
        	int[][] map = new int[N][N];
        	for(int i=0; i<N; i++) {
        		st = new StringTokenizer(br.readLine(), " ");
        		for(int j=0; j<N; j++) {
        			map[i][j] = Integer.parseInt(st.nextToken());
        		}
        	}
        	
        	boolean[] visited;
        	switch (dir) {
			case "left":
				// 0이 있다면 뒤쪽으로 밀고나서 합치기
				for(int i=0; i<N; i++) {
					int[] arr = new int[N];
					int cnt = 0;
					visited = new boolean[N];
					for(int j=0; j<N; j++) {
						if(map[i][j] != 0) {
							arr[cnt++] = map[i][j];
							if(cnt>1 && arr[cnt-2] == arr[cnt-1] && !visited[cnt-2]) {
								arr[cnt-2] *= 2;
								arr[cnt-1] = 0;
								visited[cnt-2] = true;
								cnt--;
							}
						}
					}
					map[i] = arr;
				}
				break;
			case "right":
				// 0이 있다면 뒤쪽으로 밀고나서 합치기
				for(int i=0; i<N; i++) {
					int[] arr = new int[N];
					int cnt = N-1;
					visited = new boolean[N];
					for(int j=N-1; j>=0; j--) {
						if(map[i][j] != 0) {
							arr[cnt--] = map[i][j];
							if(cnt<N-2 && arr[cnt+2] == arr[cnt+1] && !visited[cnt+2]) {
								arr[cnt+2] *= 2;
								arr[cnt+1] = 0;
								visited[cnt+2] = true;
								cnt++;
							}
						}
					}
					map[i] = arr;
				}
				break;
			case "up":
				// 0이 있다면 뒤쪽으로 밀고나서 합치기
				for(int i=0; i<N; i++) {
					int[] arr = new int[N];
					int cnt = 0;
					visited = new boolean[N];
					for(int j=0; j<N; j++) {
						if(map[j][i] != 0) {
							arr[cnt++] = map[j][i];
							if(cnt>1 && arr[cnt-2] == arr[cnt-1] && !visited[cnt-2]) {
								arr[cnt-2] *= 2;
								arr[cnt-1] = 0;
								visited[cnt-2] = true;
								cnt--;
							}
						}
					}
					for(int x=0; x<N; x++) {
						map[x][i] = arr[x];
					}
				}
				break;
			case "down":
				// 0이 있다면 뒤쪽으로 밀고나서 합치기
				for(int i=0; i<N; i++) {
					int[] arr = new int[N];
					int cnt = N-1;
					visited = new boolean[N];
					for(int j=N-1; j>=0; j--) {
						if(map[j][i] != 0) {
							arr[cnt--] = map[j][i];
							if(cnt<N-2 && arr[cnt+2] == arr[cnt+1] && !visited[cnt+2]) {
								arr[cnt+2] *= 2;
								arr[cnt+1] = 0;
								visited[cnt+2] = true;
								cnt++;
							}
						}
					}
					for(int x=0; x<N; x++) {
						map[x][i] = arr[x];
					}
				}
				break;
			}
        	sb.append("#"+tc+"\n");
        	for(int[] n_arr : map) {
        		for(int n : n_arr) {
        			sb.append(n+" ");
        		}
        		sb.append("\n");
        	}
        }
        System.out.println(sb);
    }
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
	static int[][] map;
	static int res, N;
	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, 1, -1};
	static List<List<int[]>> warp;
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
        	// 게임판 크기
        	N = Integer.parseInt(br.readLine());
        	map = new int[N][N];
        	
        	warp = new ArrayList<>();
        	for(int i=0; i<5; i++) {
            	warp.add(new ArrayList<>());
        	}
        	// 게임판 입력
        	for(int i=0; i<N; i++) {
        		st = new StringTokenizer(br.readLine(), " ");
        		for(int j=0; j<N; j++) {
        			map[i][j] = Integer.parseInt(st.nextToken());
        			if(map[i][j] > 5) {
        				warp.get(map[i][j]-6).add(new int[] {i, j});
        			}
        		}
        	}
        	
        	res = 0;
        	for(int x=0; x<N; x++) {
        		for(int y=0; y<N; y++) {
        			if(map[x][y] != 0) continue;
    				for(int i=0; i<4; i++) {
            			def(x, y, i);
        			}
        		}
        	}
        	sb.append("#"+tc+" "+res+"\n");
        }
        System.out.println(sb);
    }
    // dir : {우, 좌, 상, 하}
    static void def(int x, int y, int dir) {
    	int nx = x;
    	int ny = y;
    	int score = 0;
    	while(true) {
    		nx += dx[dir];
    		ny += dy[dir];

    		if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
                score++;
                dir = (dir % 2 == 0) ? dir + 1 : dir - 1;
                continue; 
            }
    		
    		if ((nx == x && ny == y) || map[nx][ny] == -1) {
                res = Math.max(score, res);
                return;
            }
    		
    		if(map[nx][ny] == 0) continue;
    		
    		switch (map[nx][ny]) {
			case 1:
				score++;
				// 방향 전환
				if(dir == 0) {
					dir = 2;
				}else if(dir == 2) {
					dir = 0;
				}else if(dir == 1) {
					dir = 3;
				}else {
					dir = 1;
				}
				break;
			case 2:
				score++;
				// 방향 전환
				if(dir == 0) {
					dir = 3;
				}else if(dir == 3) {
					dir = 0;
				}else if(dir == 1) {
					dir = 2;
				}else {
					dir = 1;
				}
				break;
			case 3:
				score++;
				// 방향 전환
				if(dir == 1) {
					dir = 3;
				}else if(dir == 3) {
					dir = 1;
				}else if(dir == 0) {
					dir = 2;
				}else {
					dir = 0;
				}
				break;
			case 4:
				score++;
				// 방향 전환
				if(dir == 1) {
					dir = 2;
				}else if(dir == 2) {
					dir = 1;
				}else if(dir == 0) {
					dir = 3;
				}else {
					dir = 0;
				}
				break;
			case 5:
				// 방향 전환
				score++;
				if(dir == 0) {
					dir = 1;
				}else if(dir == 1){
					dir = 0;
				}else if(dir == 2){
					dir = 3;
				}else if(dir == 3){
					dir = 2;
				}
				break;
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				List<int[]> list = warp.get(map[nx][ny]-6);
				int[] warpTo = list.get(0);
				if(nx == warpTo[0] && ny == warpTo[1]) {
					warpTo = list.get(1);
				}
				nx = warpTo[0];
				ny = warpTo[1];
				break;
			}
    	}
    }
}

import java.io.*;
import java.util.*;
import java.lang.Math;

public class SWEA_2117 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int[][] map = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // k는 서비스 영역의 크기
            // 모든 좌표를 순회하면서 집 리스트에서 거리 계산 
            // 거리가 k-1이하 이면 방범 서비스 안에 포함함
            int max = 0;
            for (int k = 1; k < N + 2; k++) {
                int cost = k * k + (k - 1) * (k - 1);
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        int count = 0; // 마름모 안에 집 개수수
                        for (int x = 0; x < N; x++) {
                            for (int y = 0; y < N; y++) {
                                if (Math.abs(i - x) + Math.abs(j - y) < k) {
                                    if (map[x][y] == 1) {
                                        count++;
                                    }
                                }
                            }
                        }
                        // 손해 여부 판단
                        if (count * M - cost >= 0) {
                            max = Math.max(max, count);
                        }
                    }
                }
            }
            System.out.println("#" + test_case + " " + max);
        }
    }
}
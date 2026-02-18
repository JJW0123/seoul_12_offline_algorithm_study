import java.io.*;
import java.util.*;

public class SWEA_1873 {
    static int H, W, r, c;
    static char[][] map;
    // 상, 하, 좌, 우 순서 (dr, dc)
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            map = new char[H][W];

            for (int i = 0; i < H; i++) {
                String line = br.readLine();
                for (int j = 0; j < W; j++) {
                    map[i][j] = line.charAt(j);
                    // 탱크 위치 찾기
                    if (map[i][j] == '^' || map[i][j] == 'v' || map[i][j] == '<' || map[i][j] == '>') {
                        r = i;
                        c = j;
                    }
                }
            }

            int N = Integer.parseInt(br.readLine());
            String commands = br.readLine();

            for (int i = 0; i < N; i++) {
                process(commands.charAt(i));
            }

            // 결과 출력
            StringBuilder sb = new StringBuilder();
            sb.append("#").append(tc).append(" ");
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    sb.append(map[i][j]);
                }
                sb.append("\n");
            }
            System.out.print(sb.toString());
        }
    }

    static void process(char cmd) {
        if (cmd == 'S') {
            shoot();
        } else {
            // 방향 전환 및 이동
            int dir = -1;
            char tankChar = ' ';

            if (cmd == 'U') { dir = 0; tankChar = '^'; }
            else if (cmd == 'D') { dir = 1; tankChar = 'v'; }
            else if (cmd == 'L') { dir = 2; tankChar = '<'; }
            else if (cmd == 'R') { dir = 3; tankChar = '>'; }

            map[r][c] = tankChar; // 방향 전환
            int nr = r + dr[dir];
            int nc = c + dc[dir];

            // 평지라면 이동
            if (nr >= 0 && nr < H && nc >= 0 && nc < W && map[nr][nc] == '.') {
                map[r][c] = '.';
                r = nr;
                c = nc;
                map[r][c] = tankChar;
            }
        }
    }

    static void shoot() {
        int dir = -1;
        if (map[r][c] == '^') dir = 0;
        else if (map[r][c] == 'v') dir = 1;
        else if (map[r][c] == '<') dir = 2;
        else if (map[r][c] == '>') dir = 3;

        int currR = r;
        int currC = c;

        while (true) {
            currR += dr[dir];
            currC += dc[dir];

            // 범위를 벗어나거나 강철벽을 만나면 종료
            if (currR < 0 || currR >= H || currC < 0 || currC >= W || map[currR][currC] == '#') break;
            
            // 벽돌벽을 만나면 평지로 만들고 종료
            if (map[currR][currC] == '*') {
                map[currR][currC] = '.';
                break;
            }
        }
    }
}
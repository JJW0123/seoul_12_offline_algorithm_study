import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SWEA_1873 {

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            String[] arr = br.readLine().split(" ");
            int H = Integer.parseInt(arr[0]);
            int W = Integer.parseInt(arr[1]);

            int next = -1;
            int x = 0;
            int y = 0;
            char[] move = new char[] { '^', 'v', '<', '>' };
            char[][] map = new char[H][W];
            for (int i = 0; i < H; i++) {
                String str = br.readLine();
                for (int j = 0; j < W; j++) {
                    map[i][j] = str.charAt(j);
                    for (int k = 0; k < 4; k++) {
                        if (map[i][j] == move[k]) {
                            x = i;
                            y = j;
                            next = k;
                        }
                    }
                }
            }

            int N = Integer.parseInt(br.readLine());
            char[] play = br.readLine().toCharArray();

            for (int i = 0; i < N; i++) {
                switch (play[i]) {
                    case 'U':
                        next = 0;
                        map[x][y] = '^';
                        if (x - 1 >= 0 && map[x - 1][y] == '.') {
                            map[x--][y] = '.';
                            map[x][y] = '^';
                        }
                        break;
                    case 'D':
                        next = 1;
                        map[x][y] = 'v';
                        if (x + 1 < H && map[x + 1][y] == '.') {
                            map[x++][y] = '.';
                            map[x][y] = 'v';
                        }

                        break;
                    case 'L':
                        next = 2;
                        map[x][y] = '<';
                        if (y - 1 >= 0 && map[x][y - 1] == '.') {
                            map[x][y--] = '.';
                            map[x][y] = '<';
                        }

                        break;
                    case 'R':
                        next = 3;
                        map[x][y] = '>';
                        if (y + 1 < W && map[x][y + 1] == '.') {
                            map[x][y++] = '.';
                            map[x][y] = '>';
                        }

                        break;
                    case 'S':
                        switch (next) {
                            case 0:
                                for (int j = x - 1; j >= 0; j--) {
                                    if (map[j][y] == '*') {
                                        map[j][y] = '.';
                                        break;
                                    } else if (map[j][y] == '#') {
                                        break;
                                    }
                                }
                                break;
                            case 1:
                                for (int j = x + 1; j < H; j++) {
                                    if (map[j][y] == '*') {
                                        map[j][y] = '.';
                                        break;
                                    } else if (map[j][y] == '#') {
                                        break;
                                    }
                                }
                                break;
                            case 2:
                                for (int j = y - 1; j >= 0; j--) {
                                    if (map[x][j] == '*') {
                                        map[x][j] = '.';
                                        break;
                                    } else if (map[x][j] == '#') {
                                        break;
                                    }
                                }
                                break;
                            case 3:
                                for (int j = y + 1; j < W; j++) {
                                    if (map[x][j] == '*') {
                                        map[x][j] = '.';
                                        break;
                                    } else if (map[x][j] == '#') {
                                        break;
                                    }
                                }
                                break;
                        }
                        break;
                }
            }
            sb.append("#" + tc + " ");
            for (char[] ch_arr : map) {
                for (char ch : ch_arr) {
                    sb.append(ch);
                }
                sb.append("\n");
            }
        }
        System.out.println(sb);
    }
}
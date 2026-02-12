import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SWEA_1873 {
    static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static char[][] map;
    static int directionIdx;
    static int x;
    static int y;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            int[] array = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int height = array[0];
            int width = array[1];

            map = new char[height][width];
            for (int i = 0; i < height; i++) {
                char[] line = br.readLine().toCharArray();

                for (int j = 0; j < width; j++) {
                    switch (line[j]) {
                        case '<':
                            directionIdx = 2;
                            x = i;
                            y = j;
                            break;
                        case '>':
                            directionIdx = 3;
                            x = i;
                            y = j;
                            break;
                        case '^':
                            directionIdx = 0;
                            x = i;
                            y = j;
                            break;
                        case 'v':
                            directionIdx = 1;
                            x = i;
                            y = j;
                            break;
                    }
                }

                map[i] = line;
            }

            int num = Integer.parseInt(br.readLine());
            char[] commands = br.readLine().toCharArray();
            for (char command : commands) {
                switch (command) {
                    case 'S':
                        shoot(height,width);
                        break;
                    case 'U':
                        moveUp();
                        break;
                    case 'D':
                        moveDown(height);
                        break;
                    case 'L':
                        moveLeft();
                        break;
                    case 'R':
                        moveRight(width);
                        break;
                }
            }

            sb.append("#").append(tc).append(" ");
            for (int i = 0; i < height; i++) {
                sb.append(new String(map[i])).append("\n");
            }
        }
        System.out.println(sb);
    }

    private static void moveUp() {
        directionIdx = 0;
        if (x - 1 >= 0 && map[x-1][y] == '.') {
            map[x][y] = '.';
            x -= 1;
        }
        map[x][y] = '^';
    }
    private static void moveDown(int height) {
        directionIdx = 1;
        if (x +1 < height && map[x + 1][y] == '.') {
            map[x][y] = '.';
            x += 1;
        }
        map[x][y] = 'v';
    }
    private static void moveLeft() {
        directionIdx = 2;
        if (y - 1 >= 0 && map[x][y -1] == '.') {
            map[x][y] = '.';
            y -= 1;
        }
        map[x][y] = '<';
    }
    private static void moveRight(int width) {
        directionIdx = 3;
        if (y + 1 < width && map[x][y + 1] == '.') {
            map[x][y] = '.';
            y += 1;
        }
        map[x][y] = '>';
    }

    private static void shoot(int height, int width) {
        int bombX = x;
        int bombY = y;
        while (true) {
            int[] direction = directions[directionIdx];
            int dx = direction[0];
            int dy = direction[1];

            if (!(bombX + dx >= 0 && bombX + dx < height && bombY + dy >= 0 && bombY + dy < width)) {
                return;
            }

            if (map[bombX + dx][bombY + dy] == '*') {
                map[bombX + dx][bombY + dy] = '.';
                return;
            }

            if (map[bombX + dx][bombY + dy] == '#') {
                return;
            }

            bombX += dx;
            bombY += dy;
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    static int N, M, K, min = Integer.MAX_VALUE;
    static int[][] turn, arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 배열 input
        arr = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 회전 input
        turn = new int[K][3];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                turn[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, new boolean[K], new int[K]);
        System.out.println(min);
    }

    static void dfs(int cnt, boolean[] visit, int[] nums) {
        // 순열을 전부 찾았다면
        if (cnt == K) {

            // 깊은복사로 원본 배열 가져오기
            int[][] temp_arr = new int[N][M];
            for (int i = 0; i < N; i++) {
                temp_arr[i] = arr[i].clone();
            }

            for (int i = 0; i < nums.length; i++) {
                // 인덱스가 (1, 1) 부터 시작하므로 배열에 맞게 1씩 감소
                int X = turn[nums[i]][0] - 1;
                int Y = turn[nums[i]][1] - 1;
                int L = turn[nums[i]][2];

                // 회전 연산
                int[][] dir = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

                // 중심에 가까운 사각형부터 회전시키기
                for (int depth = 1; depth <= L; depth++) {
                    // 좌상단(nx, ny) 부터 시작해서 한 칸씩 땡기기
                    int nx = X - depth;
                    int ny = Y - depth;
                    int temp = temp_arr[nx][ny];
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < depth * 2; k++) {
                            temp_arr[nx][ny] = temp_arr[nx + dir[j][0]][ny + dir[j][1]];
                            nx += dir[j][0];
                            ny += dir[j][1];

                        }
                    }
                    temp_arr[nx][ny + 1] = temp;
                }
            }
            // 같은 행 더하고 최솟값 갱신
            for (int j = 0; j < temp_arr.length; j++) {
                int sum = 0;
                for (int num : temp_arr[j]) {
                    sum += num;
                }
                min = Math.min(min, sum);
            }
            return;
        }

        // 순열 찾아서 배열 nums에 저장
        for (int i = 0; i < K; i++) {
            if (!visit[i]) {
                nums[cnt] = i;
                visit[i] = true;
                dfs(cnt + 1, visit, nums);
                visit[i] = false;
            }
        }
    }
}

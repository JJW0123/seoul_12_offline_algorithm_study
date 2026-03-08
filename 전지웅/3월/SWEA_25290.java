import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class UserSolution {
    static int width, height;
    static Tank[] tank_arr;
    static List<List<int[]>> list;
    static int n1, n2, n3;

    // 어항 클래스
    class Tank {
        int id;
        // row[N] = { N번 열의 높이, N번 열의 결합판 }
        int[][] row = new int[width + 1][2];

        Tank(int id, int[][] row) {
            this.id = id;
            this.row = row;
        }
    }

    public void init(int N, int mWidth, int mHeight, int mIDs[], int mLengths[][], int mUpShapes[][]) {
        width = mWidth;
        height = mHeight;

        tank_arr = new Tank[N + 1];

        for (int i = 1; i < tank_arr.length; i++) {
            int[][] row = new int[mWidth + 1][2];

            for (int j = 1; j < row.length; j++) {
                row[j] = new int[] { mLengths[i - 1][j - 1], mUpShapes[i - 1][j - 1] };
            }
            tank_arr[i] = new Tank(mIDs[i - 1], row);
        }

        list = new ArrayList<>();

        for (int i = 0; i < 64; i++) {
            list.add(new ArrayList<>());
        }

        // TODO: 결합판 리스트 채우기

        n1 = n2 = n3 = -1;
        // n: 어항 번호s
        for (int n = 1; n <= N; n++) {
            int[][] row = tank_arr[n].row;
            // w: 열 번호
            for (int w = 1; w <= mWidth - 2; w++) {
                n1 = row[w][1];
                n2 = row[w + 1][1];
                n3 = row[w + 2][1];

                list.get(n1 * 16 + n2 * 4 + n3).add(new int[] { n, w });
            }
        }
    }

    public int checkStructures(int mLengths[], int mUpShapes[], int mDownShapes[]) {
        /*
         * checkStructures() 호출 횟수가 10,000번이라 시간초과 발생할 듯
         * 
         * 미리 어항에서 3칸 단위로 결합판 캐싱?
         * 결합판 경우의 수는 {0,0,0} ~ {3,3,3} 총 64가지임
         * List<List<int[]>> 27칸 만들고 결합판이 {a,b,c} 일때 index = a*9+b*3+c인 칸에
         * {어항 번호, 열 번호} 넣어두면 될 듯?
         * 
         * 여기까지 init에서 처리
         * 
         * -- 설치 조건 체크 --
         * 1. 연결부 결합판 체크
         * 2. 구조물이 서로 맞닿는 높이인지 체크
         * 3. 어항 높이를 안 넘는지 체크
         * 
         * 리턴값은 설치 가능한 위치의 개수
         */
        int cnt = 0;
        int idx = mDownShapes[0] * 16 + mDownShapes[1] * 4 + mDownShapes[2];
        List<int[]> nextList = list.get(idx);
        int w;
        Tank nTank;
        for (int i = 0; i < nextList.size(); i++) {
            nTank = tank_arr[nextList.get(i)[0]];
            w = nextList.get(i)[1];

            // 구조물 설치 조건 체크
            if (check(nTank, w, mLengths)) {
                cnt++;
            }
        }
        return cnt;
    }

    public int addStructures(int mLengths[], int mUpShapes[], int mDownShapes[]) {
        /*
         * -- 구조물 설치하기 --
         * mDownShapes에 맞는 list 가져오기
         * list로 check 반복문 돌리기
         * check == true 일때, 어항 방문체크하고 tank_arr, list 갱신
         * 
         * id 오름차순 정렬이 필요함 -> PQ로 구현
         * 
         * 리턴값은 (ID * 1,000 + 열 위치) 없다면 0
         */

        // int[] arr = new int[]{id, 어항 번호, 열 번호}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                Comparator.comparingInt((int[] o1) -> o1[0])
                        .thenComparingInt(o1 -> o1[2]));

        List<int[]> nextList = list.get(mDownShapes[0] * 16 + mDownShapes[1] * 4 + mDownShapes[2]);
        for (int i = 0; i < nextList.size(); i++) {
            if (check(tank_arr[nextList.get(i)[0]], nextList.get(i)[1], mLengths)) {
                pq.add(new int[] { tank_arr[nextList.get(i)[0]].id, nextList.get(i)[0], nextList.get(i)[1], i });
            }
        }

        if (pq.isEmpty()) {
            return 0;
        } else {
            int id = pq.peek()[0];
            int n = pq.peek()[1];
            int w = pq.peek()[2];

            for (int i = w - 2; i <= w + 2; i++) {
                // 어항 넓이 벗어나면 continue
                if (i < 1 || i + 2 > width) {
                    continue;
                }

                List<int[]> target = list
                        .get(tank_arr[n].row[i][1] * 16 + tank_arr[n].row[i + 1][1] * 4 + tank_arr[n].row[i + 2][1]);
                for (int j = 0; j < target.size(); j++) {
                    if (target.get(j)[0] == n && target.get(j)[1] == i) {
                        target.remove(j);
                        break;
                    }
                }
            }

            for (int i = 0; i < 3; i++) {
                tank_arr[n].row[w + i][0] += mLengths[i];
                tank_arr[n].row[w + i][1] = mUpShapes[i];
            }

            for (int i = w - 2; i <= w + 2; i++) {
                // 어항 넓이 벗어나면 continue
                if (i < 1 || i + 2 > width) {
                    continue;
                }

                List<int[]> target = list
                        .get(tank_arr[n].row[i][1] * 16 + tank_arr[n].row[i + 1][1] * 4 + tank_arr[n].row[i + 2][1]);
                target.add(new int[] { n, i });
            }

            return id * 1_000 + w;
        }
    }

    public Solution.Result pourIn(int mWater) {
        Solution.Result ret = new Solution.Result();
        ret.ID = ret.height = ret.used = 0;

        /*
         * 정렬조건
         * 1. 물을 채운 후의 높이
         * 2. 현재 높이까지 사용한 물의 양
         * 3. 어항 ID
         */
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                Comparator.comparingInt((int[] o1) -> -o1[0])
                        .thenComparingInt(o1 -> -o1[1])
                        .thenComparingInt(o1 -> o1[2]));

        for (int n = 1; n < tank_arr.length; n++) {
            int top = 1, water = mWater, usedWater = 0, prevWater = water;
            while (water > 0 && top <= height) {
                for (int i = 1; i <= width; i++) {
                    if (tank_arr[n].row[i][0] < top) {
                        water--;
                    }
                }
                if (water >= 0) {
                    top++;
                    usedWater += prevWater - water;
                    prevWater = water;
                }
            }
            if (usedWater > 0) {
                pq.add(new int[] { top - 1, usedWater, tank_arr[n].id });
            }
        }

        if (!pq.isEmpty()) {
            ret.ID = pq.peek()[2];
            ret.height = pq.peek()[0];
            ret.used = pq.peek()[1];
        }

        return ret;
    }

    boolean check(Tank tank, int idx, int[] lengths) {
        // 어항 높이 넘어가면 false
        for (int i = 0; i < 3; i++) {
            if (tank.row[idx + i][0] + lengths[i] > height) {
                return false;
            }
        }

        // 구조물이 서로 붙어있으면 true;
        if (tank.row[idx][0] + lengths[0] > tank.row[idx + 1][0]
                && tank.row[idx + 1][0] + lengths[1] > tank.row[idx][0]
                && tank.row[idx + 1][0] + lengths[1] > tank.row[idx + 2][0]
                && tank.row[idx + 2][0] + lengths[2] > tank.row[idx + 1][0]) {
            return true;
        }

        return false;
    }
}
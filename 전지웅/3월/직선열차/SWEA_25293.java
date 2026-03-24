import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

class UserSolution {

    // 열차 ID 배열 (인덱스: 내부 고유 번호)
    int[] trainIds = new int[200];
    int trainCount;

    // 열차 운행 정보: {시작역, 종착역, 정차 간격}
    int[][] trainInfos;

    // 삭제된 열차 ID 관리 (지연 삭제용)
    Set<Integer> deletedTrains = new HashSet<>();

    // BFS 탐색 시 열차 탑승 여부 체크
    boolean[] visited;

    public void init(int N, int K, int[] mId, int[] sId, int[] eId, int[] mInterval) {
        deletedTrains.clear();
        trainCount = 0;
        trainInfos = new int[200][3];
        Arrays.fill(trainIds, 0);

        for (int i = 0; i < K; i++) {
            trainIds[trainCount] = mId[i];
            trainInfos[trainCount++] = new int[] { sId[i], eId[i], mInterval[i] };
        }
    }

    public void add(int mId, int sId, int eId, int mInterval) {
        trainIds[trainCount] = mId;
        trainInfos[trainCount++] = new int[] { sId, eId, mInterval };
    }

    public void remove(int mId) {
        deletedTrains.add(mId);
    }

    public int calculate(int sId, int eId) {
        // pq: {탑승한 열차 인덱스, 누적 환승 횟수}
        // 환승 횟수 오름차순 정렬
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        visited = new boolean[trainCount];

        // 1. 출발지(sId)에서 탈 수 있는 열차 탐색
        for (int i = 0; i < trainCount; i++) {
            /*
             * 1. 열차 범위 내에 출발지가 있어야 함
             * - 시작역은 sId 이하여야 함
             * - 종착역은 sId 이상이어야 함
             * 2. (sId - 시작역)은 간격으로 나누어 떨어져야 함
             */
            if (trainInfos[i][0] <= sId
                    && trainInfos[i][1] >= sId
                    && (sId - trainInfos[i][0]) % trainInfos[i][2] == 0
                    && !deletedTrains.contains(trainIds[i])) {
                pq.add(new int[] { i, 0 });
                visited[i] = true;
            }
        }

        // 2. BFS 탐색 (최소 환승 횟수 찾기)
        while (!pq.isEmpty()) {
            int currentTrain = pq.peek()[0];
            int currentCost = pq.poll()[1];

            /*
             * 만약 목적지(eId)에 도달할 수 있는 상태라면 함수 종료
             * 1. 열차 범위 내에 목적지가 있어야 함
             * - 시작역은 eId 이하여야 함
             * - 종착역은 eId 이상이어야 함
             * 2. (eId - 시작역)은 간격으로 나누어 떨어져야 함
             */
            if (trainInfos[currentTrain][0] <= eId
                    && trainInfos[currentTrain][1] >= eId
                    && (eId - trainInfos[currentTrain][0]) % trainInfos[currentTrain][2] == 0
                    && !deletedTrains.contains(trainIds[currentTrain])) {
                return currentCost;
            }

            // 현재 열차에서 환승 가능한 다음 열차 탐색
            for (int nextTrain = 0; nextTrain < trainCount; nextTrain++) {
                int[] earlyTrain = trainInfos[currentTrain];
                int[] lateTrain = trainInfos[nextTrain];

                // 계산의 편의를 위해 시작역이 앞서는 열차를 earlyTrain으로 정렬
                if (earlyTrain[0] > lateTrain[0]) {
                    int[] temp = earlyTrain;
                    earlyTrain = lateTrain;
                    lateTrain = temp;
                }

                /**
                 * 가지치기
                 * 1. lateTrain의 시작역은 earlyTrain의 종착역 이하여야 함
                 * 2. (lateTrain의 시작역 - earlyTrain의 시작역) % 두 간격의 최대공약수 == 0
                 * 3. 방문하지 않은 열차인지
                 * 4. 삭제되지 않은 열차인지
                 */
                if (earlyTrain[1] >= lateTrain[0]
                        && (lateTrain[0] - earlyTrain[0]) % gcd(earlyTrain[2], lateTrain[2]) == 0
                        && !visited[nextTrain]
                        && !deletedTrains.contains(trainIds[nextTrain])) {

                    // 두 열차가 겹치는 범위 내에 환승역이 있는지 확인
                    boolean flag = false;
                    int startStation = lateTrain[0];
                    int endStation = Math.min(earlyTrain[1], lateTrain[1]);

                    for (int station = startStation; station <= endStation; station += lateTrain[2]) {
                        if ((station - earlyTrain[0]) % earlyTrain[2] == 0) {
                            flag = true;
                            break;
                        }
                    }

                    // 조건 만족하면 환승하고 열차 방문처리
                    if (flag) {
                        pq.add(new int[] { nextTrain, currentCost + 1 });
                        visited[nextTrain] = true;
                    }
                }
            }
        }
        return -1;
    }

    // 최대공약수 리턴
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
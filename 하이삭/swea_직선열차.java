import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
N개의 열차 역
K개의 왕복 운행하는 열차 정보
	 각 열차의 ID, 시작역과 종착역, 그리고 정차 역의 간격
============================================
# 시간 복잡도 분석
N: 열차 역의 개수 ( 20 ≤ N ≤ 100,000 )
K: 열차의 개수 ( 3 ≤ K ≤ 50 )
( 1 ≤ mId[i] ≤ 1,000,000,000 )
3 ≤ mInterval[i] ≤ 50

# 알고리즘
1. 조합 + BFS
2. 멀티소스 BFS
 */

import java.util.*;

class UserSolution {

    static int N;
    static Map<Integer, int[]> trainMap;	// 열차 ID -> 열차 정보 저장
    static List<Integer>[] station;			// 각 역에 정차하는 열차 리스트
    static Map<Integer, List<Integer>> graph; 
    static Set<Integer> removed;

    public void init(int N, int K, int[] mId, int[] sId, int[] eId, int[] mInterval) {
        this.N = N;

        trainMap = new HashMap<>();
        graph = new HashMap<>();
        removed = new HashSet<>();

        station = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            station[i] = new ArrayList<>();
        }

        // 열차 등록
        for (int i = 0; i < K; i++) {
            add(mId[i], sId[i], eId[i], mInterval[i]);
        }
    }

    public void add(int mId, int sId, int eId, int mInterval) {
        trainMap.put(mId, new int[]{sId, eId, mInterval});
        graph.put(mId, new ArrayList<>());

        for (int i = sId; i <= eId; i += mInterval) {

            // 현재 역에 있는 기존 열차들과 연결
            for (int other : station[i]) {
                if (other == mId) continue;

                graph.get(mId).add(other);
                graph.get(other).add(mId);
            }

            // 마지막에 추가 (중요 )
            station[i].add(mId);
        }
    }

    public void remove(int mId) {
        removed.add(mId); // lazy 삭제
    }

    public int calculate(int sId, int eId) {

        if (sId == eId) return 0;

        List<Integer> startList = station[sId];
        Set<Integer> endSet = new HashSet<>(station[eId]);

        return bfs(startList, endSet);
    }

    private int bfs(List<Integer> startList, Set<Integer> endSet) {

        Queue<int[]> q = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        // 시작점 여러 개 (멀티 소스)
        for (int train : startList) {
            if (removed.contains(train)) continue;

            q.offer(new int[]{train, 0});
            visited.add(train);
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int train = cur[0];
            int cnt = cur[1];

            // 도착 조건
            if (endSet.contains(train)) return cnt;

            List<Integer> list = graph.get(train);
            if (list == null) continue;

            for (int next : list) {
                if (visited.contains(next)) continue;
                if (removed.contains(next)) continue;

                visited.add(next);
                q.offer(new int[]{next, cnt + 1});
            }
        }

        return -1;
    }
}

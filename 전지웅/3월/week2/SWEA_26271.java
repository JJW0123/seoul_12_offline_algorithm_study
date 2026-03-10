import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class UserSolution {
    static List<List<int[]>> list;
    static int[] length;

    public void init(int N, int K, int sBuilding[], int eBuilding[], int mDistance[]) {
        length = new int[N];
        list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add(new ArrayList<int[]>());
        }

        // list.get().get() = { 건물번호, 거리 }
        for (int i = 0; i < K; i++) {
            list.get(sBuilding[i]).add(new int[] { eBuilding[i], mDistance[i] });
            list.get(eBuilding[i]).add(new int[] { sBuilding[i], mDistance[i] });
        }

        return;
    }

    public void add(int sBuilding, int eBuilding, int mDistance) {

        list.get(sBuilding).add(new int[] { eBuilding, mDistance });
        list.get(eBuilding).add(new int[] { sBuilding, mDistance });

        return;
    }

    public int calculate(int M, int mCoffee[], int P, int mBakery[], int R) {
        // 각 건물에서 카페까지의 거리
        int[] cafeLength = Arrays.copyOf(dijkstra(mCoffee, M), length.length);
        // 각 건물에서 베이커리까지의 거리
        int[] bakeryLength = Arrays.copyOf(dijkstra(mBakery, P), length.length);

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < length.length; i++) {
            int n1 = cafeLength[i];
            int n2 = bakeryLength[i];

            if (n1 == 0 || n2 == 0 || n1 > R || n2 > R)
                continue;
            min = Math.min(min, n1 + n2);
        }

        min = min == Integer.MAX_VALUE ? -1 : min;
        return min;
    }

    // 다익스트라
    public static int[] dijkstra(int[] building, int N) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        Arrays.fill(length, Integer.MAX_VALUE);
        for (int i = 0; i < N; i++) {
            pq.offer(new int[] { building[i], 0 });
            length[building[i]] = 0;
        }
        while (!pq.isEmpty()) {
            int[] next = pq.poll();

            if (length[next[0]] < next[1])
                continue;

            for (int i = 0; i < list.get(next[0]).size(); i++) {
                int[] nextNode = list.get(next[0]).get(i);
                if (length[nextNode[0]] > length[next[0]] + nextNode[1]) {
                    length[nextNode[0]] = length[next[0]] + nextNode[1];
                    pq.offer(new int[] { nextNode[0], length[nextNode[0]] });
                }
            }
        }
        return length;
    }
}
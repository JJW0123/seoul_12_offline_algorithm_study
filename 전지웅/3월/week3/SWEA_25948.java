import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class UserSolution {
    private final static int MAX_N = 100;
    static int N;
    static Block[][] map;
    
    // 문제의 방향: 0(상), 1(우), 2(하), 3(좌)
    static int[] dx = { -1, 0, 1, 0 };
    static int[] dy = { 0, 1, 0, -1 };

    static List<Island> list; // 빙하 그룹(Island)들의 정보를 담는 리스트

    // 1. 빙하 그룹(Island) 클래스
    // 여러 얼음 덩어리가 뭉쳐진 하나의 빙하를 표현합니다.
    static class Island {
        int id, dir, vol, area, minX, minY;

        Island(int id, int dir, int vol, int area, int minX, int minY) {
            this.id = id;
            this.dir = dir;
            this.vol = vol;
            this.area = area;
            this.minX = minX;
            this.minY = minY;
        }
    }

    // 2. 개별 얼음 칸(Block) 클래스
    static class Block {
        int h, id;
        boolean flag; // BFS 탐색 방문 여부 OR 방금 녹은 얼음 표시용으로 다목적 활용

        Block(int id, int h, boolean flag) {
            this.id = id;
            this.h = h;
            this.flag = flag;
        }
    }

    class RESULT {
        int[][] heights;
        RESULT() { heights = new int[MAX_N][MAX_N]; }
    }

    // 3. 빙하 병합 시 승자를 결정하는 우선순위 큐 (문제의 병합 조건 완벽 반영)
    // 부피(내림차순) -> 면적(오름차순) -> 행(오름차순) -> 열(오름차순)
    static PriorityQueue<Island> pq = new PriorityQueue<>(
            Comparator.comparingInt((Island i) -> -i.vol)
                    .thenComparingInt(i -> i.area)
                    .thenComparingInt(i -> i.minX)
                    .thenComparingInt(i -> i.minY));

    // =========================================================================
    // 초기화 및 공통 유틸리티 메서드
    // =========================================================================

    void init(int N, int M, int mIceBlock[][], int mIceGroup[][]) {
        UserSolution.N = N;
        list = new ArrayList<>();
        map = new Block[N][N];

        // 1. 맵 초기화 (초기 바다의 id는 -1로 설정)
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                map[x][y] = new Block(-1, mIceBlock[x][y], false);
            }
        }

        // 2. 초기 빙하 그룹 설정
        for (int i = 0; i < M; i++) {
            int x = mIceGroup[i][0]; // 행(Y좌표)
            int y = mIceGroup[i][1]; // 열(X좌표)
            int dir = mIceGroup[i][2];
            int id = i; 

            // 시작점을 바탕으로 BFS를 돌아 하나의 빙하 그룹으로 묶어줌
            exploreAndRegisterIsland(x, y, id, dir);
        }

        clearVisitedFlags();
    }

    // BFS를 통해 연결된 얼음들을 찾아 하나의 Island 객체로 스펙을 측정하고 등록하는 함수
    public static void exploreAndRegisterIsland(int x, int y, int id, int dir) {
        int vol = 0, area = 0;
        int minX = x, minY = y;

        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[] { x, y });
        map[x][y].flag = true;
        map[x][y].id = id;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int cx = cur[0], cy = cur[1];

            vol += map[cx][cy].h; // 부피 누적
            area++;               // 면적 누적
            
            // 기준 좌표(가장 위쪽, 그리고 왼쪽) 갱신
            if (cx < minX || (cx == minX && cy < minY)) {
                minX = cx; minY = cy;
            }

            for (int d = 0; d < 4; d++) {
                int nx = getWrappedCoord(cx + dx[d]);
                int ny = getWrappedCoord(cy + dy[d]);

                // 연결된 다른 얼음 탐색
                if (map[nx][ny].h > 0 && !map[nx][ny].flag) {
                    map[nx][ny].flag = true;
                    map[nx][ny].id = id;
                    q.offer(new int[] { nx, ny });
                }
            }
        }

        // 측정된 스펙으로 Island 객체를 만들어 리스트에 저장
        list.add(new Island(id, dir, vol, area, minX, minY));
    }

    // 융해 등으로 인해 빙하가 쪼개졌을 수 있으므로 전체 빙하를 다시 그룹화하는 함수
    public static void recalculateIslands() {
        clearVisitedFlags();
        List<Island> oldList = list; // 기존 방향(dir)을 기억하기 위해 백업
        list = new ArrayList<>();    // 새 리스트 준비

        int newIdCounter = 0;

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                if (map[x][y].h > 0 && !map[x][y].flag) {
                    // 이전 id를 참조해 빙하가 원래 가던 방향 유지
                    int preservedDir = oldList.get(map[x][y].id).dir;
                    
                    // 새 ID를 부여하며 스펙 다시 계산
                    exploreAndRegisterIsland(x, y, newIdCounter, preservedDir);
                    newIdCounter++;
                }
            }
        }
    }

    public static void clearVisitedFlags() {
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                map[x][y].flag = false;
            }
        }
    }

    // 지구가 둥글듯, 격자의 양 끝을 이어주는(Torus) 모듈러 연산 함수
    public static int getWrappedCoord(int a) {
        return (a == -1) ? N - 1 : (a == N) ? 0 : a;
    }

    // =========================================================================
    // 메인 시뮬레이션: 1년 후의 변화 (융해 -> 재계산 -> 이동 -> 인접 병합)
    // =========================================================================

    RESULT oneYearLater() {
        RESULT res = new RESULT();

        // 1단계: 바다와 닿은 빙하 융해
        meltIcebergs();

        // 2단계: 융해로 인해 빙하가 여러 개로 쪼개졌을 수 있으므로 재계산
        recalculateIslands();

        // 3단계: 모든 빙하를 방향에 맞게 1칸씩 이동 (같은 칸 충돌 병합 포함)
        moveIcebergs();

        // 4단계: 이동 후 상하좌우로 새롭게 인접해진 빙하들 병합
        mergeAdjacentIcebergs();

        // 결과 저장
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                res.heights[x][y] = map[x][y].h;
            }
        }
        return res;
    }

    // ------------------- 단계별 세부 구현 메서드 -------------------

    private void meltIcebergs() {
        // [최적화 포인트] 
        // 융해 시 배열을 새로 만들지 않고, 방금 녹은 얼음의 flag를 true로 만들어
        // "이건 원래 바다가 아니라 방금 녹은 거니까 옆 얼음을 또 녹이면 안 돼!" 라고 방어합니다.
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                Block block = map[x][y];
                if (block.h == 0) continue;

                for (int i = 0; i < 4; i++) {
                    int nx = getWrappedCoord(x + dx[i]);
                    int ny = getWrappedCoord(y + dy[i]);

                    // 진짜 원래 바다(높이 0 && flag 미방문)와 닿아있다면
                    if (map[nx][ny].h == 0 && !map[nx][ny].flag) {
                        block.h--;
                        block.flag = true; // 방금 깎인 얼음임을 표시
                        break;             // 1년엔 1번만 깎임
                    }
                }
            }
        }
    }

    private void moveIcebergs() {
        // 동시 이동을 구현하기 위해 새로운 도화지(nMap)를 준비합니다.
        Block[][] nMap = new Block[N][N];
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                nMap[x][y] = new Block(-1, 0, false);
            }
        }

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                Block block = map[x][y];
                if (block.h == 0) continue;

                int dir = list.get(block.id).dir;
                int nx = getWrappedCoord(x + dx[dir]);
                int ny = getWrappedCoord(y + dy[dir]);
                Block nBlock = nMap[nx][ny];

                // [충돌 병합] 이동하려는 칸에 이미 다른 얼음이 도착해 있다면
                if (nBlock.h > 0) {
                    nBlock.h = Math.max(nBlock.h, block.h); // 높이는 더 높은 것으로

                    // 두 빙하의 원본 스펙을 PQ에 넣어 승자(best ID)를 가림
                    pq.offer(list.get(block.id));
                    pq.offer(list.get(nBlock.id));
                    nBlock.id = pq.poll().id;
                    pq.clear();
                } else {
                    nBlock.h = block.h;
                    nBlock.id = block.id;
                }
            }
        }
        
        // 이동이 모두 끝난 새 맵으로 원본 맵 교체
        map = nMap;
    }

    private void mergeAdjacentIcebergs() {
        // map은 이미 nMap으로 교체되었으므로, nMap 생성 시 초기화된 flag=false를 그대로 활용합니다.
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                
                // 얼음을 발견하면 연결된 모든 얼음을 탐색
                if (map[x][y].h > 0 && !map[x][y].flag) {
                    Queue<int[]> q = new ArrayDeque<>();
                    List<int[]> groupCoords = new ArrayList<>(); // 병합될 얼음들의 좌표 모음

                    q.offer(new int[] { x, y });
                    map[x][y].flag = true; 

                    while (!q.isEmpty()) {
                        int[] cur = q.poll();
                        int cx = cur[0], cy = cur[1];
                        groupCoords.add(cur);

                        // 인접한 모든 얼음들의 원래 소속(ID)을 스펙 경쟁(PQ)에 올림
                        pq.offer(list.get(map[cx][cy].id));

                        for (int i = 0; i < 4; i++) {
                            int nx = getWrappedCoord(cx + dx[i]);
                            int ny = getWrappedCoord(cy + dy[i]);

                            if (map[nx][ny].h > 0 && !map[nx][ny].flag) {
                                map[nx][ny].flag = true;
                                q.offer(new int[] { nx, ny });
                            }
                        }
                    }

                    // 치열한 스펙 경쟁에서 이긴 최고 스펙 빙하의 ID 획득
                    int bestId = pq.poll().id;
                    pq.clear();

                    // 덩어리로 묶인 모든 얼음에게 대장의 ID를 부여하여 하나로 통일
                    for (int[] pos : groupCoords) {
                        map[pos[0]][pos[1]].id = bestId;
                    }
                }
            }
        }
    }
}

package 빙하의이동;

import java.util.*;

import 빙하의이동.UserSolution.Glacier;

class UserSolution {
    private final static int MAX_N = 100;
    private final static int[] dy = {-1, 0, 1, 0};
    private final static int[] dx = {0, 1, 0, -1};

    private static int n , m;
    private static int[][][] sea;//0 : 빙하 번호, 1 : 얼음 높이
    private static HashMap<Integer, Glacier> glaciers;
    private static int[] root;
    private static HashMap<Integer, Glacier> snapshot;//좌표, 빙하번호


    class RESULT {
        int[][] heights;
        RESULT() {
            heights = new int[MAX_N][MAX_N];
        }
    }
    
    static class Glacier {
    	int id, area, volume, dir;
    	int minY, minX;
    	
    	public Glacier(int id, int minY, int minX, int area, int volume, int dir) {
			this.id = id;
			this.minY = minY;
			this.minX = minX;
			this.area = area;
			this.volume = volume;
			this.dir = dir;
		}

		void merge(Glacier other) {
    		this.area += other.area;
    		this.volume += other.volume;
    		//위치 갱신
    		if(this.minY>other.minY || (this.minY==other.minY && this.minX>other.minX)) {
            	this.minY = other.minY;
            	this.minX = other.minX;
            }
    	}
    }

    void init(int N, int M, int mIceBlock[][], int mIceGroup[][]) {
        n = N;
        m = M;
        sea = new int[N][N][2];
        glaciers = new HashMap<>();
        root = new int[30000];

        //BFS
        boolean[][] isvisit = new boolean[N][N];
        //빙하의 개수만큼 반복, 면적, 부피 계산할 것
        for(int i=1;i<=m;i++) {
        	root[i] = i;
            Queue<int[]> q = new ArrayDeque<>();
            int cx = mIceGroup[i-1][0], cy = mIceGroup[i-1][1];
            sea[cy][cx][0] = i;
            sea[cy][cx][1] = mIceBlock[cy][cx];
            int area = 1, volume = mIceBlock[cy][cx];//면적은 1, 부피는 지금의 얼음높이
            int minY = cy, minX = cx;
            q.add(new int[] {cy, cx});
            isvisit[cy][cx] = true;


            while(!q.isEmpty()) {
                int[] cur = q.poll();
                for(int j=0;j<4;j++) {
                    int ny = cur[0] + dy[j];
                    int nx = cur[1] + dx[j];
                    //지구는 둥글다..
                    ny = checkPoint(ny);
                    nx = checkPoint(nx);
                    if(isvisit[ny][nx] || mIceBlock[ny][nx]==0) continue;
                    isvisit[ny][nx] = true;
                    //빙하 번호 지정 및 면적, 부피 계산
                    sea[ny][nx][0] = i;
                    sea[ny][nx][1] = mIceBlock[ny][nx];
                    area++;
                    volume += mIceBlock[ny][nx];
                    //위치 갱신
                    if(ny<minY) {
                    	minY = ny;
                    	minX = nx;
                    }
                    else if(ny==minY && nx<minX) minX = nx;
                    q.add(new int[] {ny, nx});
                }
            }
            glaciers.put(i, new Glacier (i, minY, minX, area, volume, mIceGroup[i-1][2]));
        }
    }

    RESULT oneYearLater() {
        RESULT res = new RESULT();
        snapshot = new HashMap<>();
        
        //융해
        HashSet<Integer> deleted = new HashSet<>();
        HashMap<Integer, Integer> meltCnt = new HashMap<>();
        //일단 1씩 깎아
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if(sea[i][j][1]>0) {
                    if(nextToSea(i, j)) {
                    	sea[i][j][1]--;
                    	int id = sea[i][j][0];
                    	meltCnt.put(id, meltCnt.getOrDefault(id, 0) + 1);
                    }
                    //만약 높이가 0이라면 deleted set에 저장
                    if(sea[i][j][1]==0) {
                        deleted.add(sea[i][j][0]);
                    }
                }
            }
        }
        //배열 다시 순회하면서 0이면 BFS
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if(deleted.contains(sea[i][j][0])) {
                    m++;
                    root[m] = m;
                    makeNewGlacier(i, j, sea[i][j][0]);
                }
            }
        }
        
        //면적만큼 volume 빼거나 지우기
        for(int key : glaciers.keySet()) {
        	if(!deleted.contains(key)) glaciers.get(key).volume -= meltCnt.getOrDefault(key, 0);
        }        

        for(int key : deleted) glaciers.remove(key);
        //한 칸 옮기기
        //[y,x] -> 빙하번호들
        for (Integer id : glaciers.keySet()) {
            Glacier g = glaciers.get(id);
            // 비교에 필요한 데이터만 복사 (id, minY, minX, area, volume)
            snapshot.put(id, new Glacier(g.id, g.minY, g.minX, g.area, g.volume, g.dir));
        }
        
        int[][][] nextSea = new int[n][n][2];
        //이동
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if(sea[i][j][1]==0) continue;
                int gID = sea[i][j][0];
                Glacier glacier = glaciers.get(gID);
                int ny = i + dy[glacier.dir];
                int nx = j + dx[glacier.dir];
                ny = checkPoint(ny);
                nx = checkPoint(nx);

                //빈 바다
                if(nextSea[ny][nx][0]==0) {
                	nextSea[ny][nx][0] = gID;
                	nextSea[ny][nx][1] = sea[i][j][1];
                }
                //이미 빙하가 있다면 우선순위 확인
                else {
                	int oID = nextSea[ny][nx][0];
                	if(gIsWinner(gID, oID)) {
                		//빙하 줄이기
                		Glacier o = glaciers.get(oID);
                		o.area--;
                		o.volume -= nextSea[ny][nx][1];
                		//우선 빙하 입력
                		nextSea[ny][nx][0] = gID;
                		nextSea[ny][nx][1] = sea[i][j][1];
                		
                		union(gID, oID);
                	}
                	else {
                		Glacier g = glaciers.get(gID);
                		g.area--;
                		g.volume -= sea[i][j][1];
                		union(oID, gID);
                	}
                }
            }
        }

        //병합
        for(int i=0;i<n;i++) {
        	for(int j=0;j<n;j++) {
        		if(nextSea[i][j][0]==0) continue;
        		for(int k=0;k<4;k++) {
        			int ny = i + dy[k];
        			int nx = j + dx[k];
        			ny = checkPoint(ny);
                    nx = checkPoint(nx);
                    if(nextSea[ny][nx][0]!=0 && nextSea[ny][nx][0] != nextSea[i][j][0]) {
                    	int gID = nextSea[i][j][0];
                    	int oID = nextSea[ny][nx][0];
                    	if(find(gID)==find(oID)) continue;
                    	if(gIsWinner(gID, oID)) {
                    		union(gID, oID);
                    	}
                    	else union(oID, gID);
                    }
        		}
        	}
        }
        
        //합쳐진 빙하들 크기를 갱신
        for(int key : glaciers.keySet()) {
        	int parent = find(key);
        	if(find(key)!=key) {
        		Glacier par = glaciers.get(parent);
        		Glacier chi = glaciers.get(key);

        		par.merge(chi);
        		chi.area = 0;
        	}
        }
        //면적 0인 빙하 다 지우기
        glaciers.entrySet().removeIf(entry -> entry.getValue().area <= 0);
        
        //sea, res 갱신
        for(int i=0;i<n;i++) {
        	for(int j=0;j<n;j++) {
        		sea[i][j][0] = find(nextSea[i][j][0]);
        		sea[i][j][1] = res.heights[i][j] = nextSea[i][j][1];
        		System.out.print(sea[i][j][0] + " ");
        	}
        	System.out.println();
        }
        
        System.out.println();
        for(int i=0;i<n;i++) {
        	for(int j=0;j<n;j++) {
        		System.out.print(sea[i][j][1] + " ");
        	}
        	System.out.println();
        }
        
        return res;
    }
  
    private static int checkPoint(int np) {
        if(np==-1) return n-1;
        if(np==n) return 0;
        return np;
    }
    
    private static boolean nextToSea(int y, int x) {
    	for(int i=0;i<4;i++) {
    		int ny = y + dy[i];
    		int nx = x + dx[i];
    		ny = checkPoint(ny);
            nx = checkPoint(nx);
            if(sea[ny][nx][0]==0) return true;
    	}
    	return false;
    }

    private static void makeNewGlacier(int y, int x, int gID) {
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] isvisit = new boolean[n][n];
        isvisit[y][x] = true;

        sea[y][x][0] = m;
        int area = 1, volume = sea[y][x][1];//면적은 1, 부피는 지금의 얼음높이
        int minY = y, minX = x;

        q.add(new int[] {y, x});

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            for(int j=0;j<4;j++) {
                int ny = cur[0] + dy[j];
                int nx = cur[1] + dx[j];
                //지구는 둥글다..
                ny = checkPoint(ny);
                nx = checkPoint(nx);
                if(isvisit[ny][nx] || sea[ny][nx][0]!=gID) continue;
                isvisit[ny][nx] = true;
                //빙하 번호 지정 및 면적 계산
                sea[ny][nx][0] = m;
                area++;
                volume += sea[ny][nx][1];
                q.add(new int[] {ny, nx});
                //위치 갱신
                if(ny<minY) {
                	minY = ny;
                	minX = nx;
                }
                else if(ny==minY && nx<minX) minX = nx;
            }
        }
        glaciers.put(m, new Glacier(m, minY, minX, area, volume, glaciers.get(gID).dir));
    }

    //두 빙하가 닿았을 때 어디가 이길 것인가
    private static boolean gIsWinner(int gID, int oID) {
        Glacier g = snapshot.get(gID);
        Glacier o = snapshot.get(oID);
        if(g.volume!=o.volume) return g.volume>o.volume;//부피
        if(g.area!=o.area) return g.area<o.area;//면적
        if(g.minY!=o.minY) return g.minY<o.minY;//y좌표
        return g.minX<o.minX;//x좌표
    }
    
    //UnionFind
    private static int find(int num) {
    	if(root[num]==num) return num;
    	return root[num] = find(root[num]);
    }
    
    private static void union(int gID, int oID) {
    	root[find(oID)] = find(gID);
    }
}

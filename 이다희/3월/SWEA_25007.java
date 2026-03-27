import java.util.*;

class UserSolution { // 지렁이 게임
    
    class Worm implements Comparable<Worm>{
        
        int id;
        int dir;
        int len;
        int pot;
        int head;
        int tail;
        int bump;
        
        public Worm() {
            super();
            this.id = -1;
            this.dir = -1;
            this.len = -1;
            this.pot = -1;
            this.head = -1;
            this.tail = -1;
            this.bump = -1;
        }

        @Override
        public int compareTo(Worm o) {
            if (this.len == o.len) return Integer.compare(o.id, this.id);
            return Integer.compare(o.len, this.len);
        }
        
    }
    
    int n, w, l, time;
    int[] alive = new int[1001];
    int[][] map = new int[2000][2000];
    int[][] head = new int[2000][2000];
    int[][] stamp = new int[2000][2000];
    int[][] dir = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    Worm[] worms = new Worm[1001];
    Worm[] sort = new Worm[1001];
    
    public void init(int N) {
        n = N;
        w = 1;
        l = 0;
        time = 0;
        Arrays.fill(alive, 0);
        if (worms[1] == null) {
            for (int i = 1; i <= 1000; i++) {
                worms[i] = new Worm();
            }
        }
        for (int i = 0; i < n; i++) {
            Arrays.fill(map[i], 0);
            Arrays.fill(stamp[i], 0);
        }
    }
    
    public void remove(int idx) {
        int y, x, dy, dx;
        y = worms[idx].tail / 2000;
        x = worms[idx].tail % 2000;
        boolean next = true;
        while (next) {
            map[y][x] = 0;
            next = false;
            for (int i = 0; i < 4; i++) {
                dy = y + dir[i][0];
                dx = x + dir[i][1];
                if (dy >= 0 && dy < n && dx >= 0 && dx < n && map[dy][dx] == idx) {
                    y = dy;
                    x = dx;
                    next = true;
                    break;
                }
            }
            if (!next) break;
        }
        return;
    }

    public void move() {
        int idx, hy, hx, ty, tx, dy, dx;
        time++;
        // [1] tail
        for (int i = 0; i < l; i++) {
            idx = alive[i];
            worms[idx].bump = 0;
        	hy = worms[idx].head / 2000;
            hx = worms[idx].head % 2000;
        	ty = worms[idx].tail / 2000;
            tx = worms[idx].tail % 2000;
            if (hy == ty || hx == tx) {
            	worms[idx].dir = (worms[idx].dir + 1) % 4;
            }
            if (worms[idx].pot > 0) {
                worms[idx].pot--;
                worms[idx].len++;
            } else {
                map[ty][tx] = 0;
                for (int j = 0; j < 4; j++) {
                    dy = ty + dir[j][0];
                    dx = tx + dir[j][1];
                    if (dy >= 0 && dy < n && dx >= 0 && dx < n && map[dy][dx] == idx) {
                        worms[idx].tail = dy * 2000 + dx;
                        break;
                    }
                }
            }
        }
        // [2] head
        for (int i = 0; i < l; i++) {
        	idx = alive[i];
        	hy = worms[idx].head / 2000;
            hx = worms[idx].head % 2000;
        	ty = worms[idx].tail / 2000;
            tx = worms[idx].tail % 2000;
            dy = hy + dir[worms[idx].dir][0];
            dx = hx + dir[worms[idx].dir][1];
            if (dy < 0 || dy >= n || dx < 0 || dx >= n) {
            	worms[idx].bump = -1;
            	continue;
            }
            if (map[dy][dx] > 0) { 
                worms[idx].bump = map[dy][dx];
            } else if (stamp[dy][dx] == time) {
                worms[idx].bump = -1;
                worms[head[dy][dx]].bump = -1;
            } else {
                stamp[dy][dx] = time; 
                head[dy][dx] = idx;
            }
            worms[idx].head = dy * 2000 + dx;
        }
        // [3] remove
        for (int i = 0; i < l; i++) {
        	idx = alive[i];
        	if (worms[idx].bump != 0) {
        		if (worms[idx].bump > 0 && worms[worms[idx].bump].bump == 0) {
        			worms[worms[idx].bump].pot += worms[idx].len;
        		}
        		remove(idx);
        		alive[i] = alive[--l];
        		i--;
        	} else {
        		hy = worms[idx].head / 2000;
                hx = worms[idx].head % 2000;
        		map[hy][hx] = idx;
        	}
        }
        return;
    }
    
    public void join(int mTime, int mID, int mX, int mY, int mLength) {
    	while (time < mTime) {
        	move();
        }
        worms[w].id = mID;
        worms[w].dir = 0;
        worms[w].len = mLength;
        worms[w].pot = 0;
        worms[w].head = mY * 2000 + mX;
        worms[w].tail = (mY + mLength - 1) * 2000 + mX;
        worms[w].bump = 0;
        for (int i = mY; i < mY + mLength; i++) {
            map[i][mX] = w;
        }
        alive[l++] = w;
        w++;
        return;
    }

    public Solution.RESULT top5(int mTime) {
        while (time < mTime) {
        	move();
        }
        Solution.RESULT res = new Solution.RESULT();
        for (int i = 0; i < l; i++) {
            sort[i] = worms[alive[i]];
        }
        Arrays.sort(sort, 0, l);
        res.cnt = Math.min(l, 5);
        for (int i = 0; i < res.cnt; i++) {
            res.IDs[i] = sort[i].id;
        }
        return res;
    }
    
}

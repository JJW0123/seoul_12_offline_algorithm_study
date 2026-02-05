import java.util.*;
import java.io.*;
public class SWEA_1226 {
    static int bfs(String[] grid, int sr, int sc, int tr, int tc){
        int n = grid.length;
        int m = grid[0].length();

        int[][] dist = new int[n][m];
        for (int i = 0; i < n; i++) Arrays.fill(dist[i], -1);
        ArrayDeque<int[]> q = new ArrayDeque<>();
        dist[sr][sc]= 0;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        q.add(new int[]{sr,sc});
        while(!q.isEmpty()){
            int[] cur = q.poll();
            int r = cur[0], c= cur[1];
            //도착하면 바로 종료
            if(r == tr && c == tc) return 1;
            for(int k = 0; k < 4; k++){
                int nr = r+ dr[k];
                int nc = c+ dc[k];
                if(0<= nr && nr < n && 0<= nc && nc < m){
                    if(grid[nr].charAt(nc)!= '1' && dist[nr][nc] == -1){
                        dist[nr][nc]= dist[r][c]+1;
                        q.add(new int[]{nr,nc});
                    }
                }
            }
        }
        return 0;
    }
    public static void main(String args[]) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        for(int i = 0; i < 10; i++){
            int test_case = Integer.parseInt(br.readLine());
            int sr= 0, sc= 0, tr = 0, tc= 0;
            String [] graph= new String[16];
            for(int j = 0; j< 16; j++){
                graph[j] = br.readLine();
                for(int x = 0; x < 16; x++){
                    if(graph[j].charAt(x)=='2'){
                        sr= j;
                        sc= x;
                    }
                    else if(graph[j].charAt(x)=='3'){
                        tr= j;
                        tc= x;
                    }
                }
            }
            int result = bfs(graph, sr, sc, tr, tc);
            System.out.printf("#%d %d\n",test_case, result);
        }
    }
}

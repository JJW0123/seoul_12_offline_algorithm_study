public class BOJ_5430 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            StringTokenizer st = new StringTokenizer(str);
            int m = Integer.parseInt(st.nextToken());
            String[] arr = new String[m];
            for (int j = 0; j < m; j++) {
                arr[j] = st.nextToken();
            }
        }
    }
}
import java.io.*;
import java.util.*;
public class BOJ_1158 {
    static class Node{
        int x;
        Node prev;
        Node next;
        public Node(int x){
            this.x = x;
        }
    }    
    static void Delete(Node node){
        Node p = node.prev;
        Node n = node.next;
        p.next = n;
        n.prev = p;
    }
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        StringBuilder out = new StringBuilder();
        Node[] nodes= new Node[n];
        for(int i = 0;i <n; i++){
            nodes[i]= new Node(i+1);
        }
        if(n==1){
            nodes[0].prev = nodes[0];
            nodes[0].next = nodes[0];
        }else{
            for(int i = 0; i< n; i++){
                nodes[i].prev = nodes[(i+n-1)%n];
                nodes[i].next = nodes[(i+1)%n];
            }
        }

        Node cur = nodes[0];
        out.append("<");
        while( n > 0){
            for(int s = 1; s < k; s++){ cur = cur.next;}
            out.append(cur.x);
            n--;
            if(n > 0){out.append(", ");}
            cur= cur.next;
            Delete(cur.prev);
        }
        out.append(">");
        System.out.println(out.toString());
    }
}

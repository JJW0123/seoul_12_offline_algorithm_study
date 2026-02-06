import java.io.*;
public class BOJ_5397 {
    static class Node{
        char c;
        Node prev;
        Node next;
        public Node(char c){
            this.c = c;
        }
    }    
    static void InsertBefore(Node node, Node newNode){
        Node p = node.prev;
        p.next= newNode;
        newNode.prev = p;
        newNode.next = node;
        node.prev = newNode;
    }
    static void Delete(Node node){
        Node p = node.prev;
        Node n = node.next;
        p.next = n;
        n.prev = p;
    }
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringBuilder out = new StringBuilder();

        for(int i = 0;i <n; i++){
            String input = br.readLine();
            Node head = new Node('\0');
            Node tail = new Node('\0');
            Node cur = tail;
            head.next = tail;
            tail.prev = head;
            for(int j = 0; j< input.length(); j++){
                char command = input.charAt(j);
                if(command!= '<' && command!='>' && command!='-'){
                    InsertBefore(cur, new Node(command));
                }
                else if(command== '<'){
                    if(cur.prev!= head){
                        cur=cur.prev;
                    }
                }
                else if(command == '>'){
                    if(cur!= tail){
                        cur= cur.next;
                    }
                }
                else if(command == '-'){  
                    if(cur.prev!= head){
                        Delete(cur.prev);
                    }
                }
            }
            Node p = head.next;
            while(p!=tail){
                out.append(p.c);
                p=p.next;
            }
            out.append('\n');
        }
        System.out.println(out.toString());
    }
}

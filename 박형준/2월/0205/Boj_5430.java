import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Boj_5430 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int tc = 1; tc <= T; tc++) {
            char[] commands = sc.next().toCharArray();
            int n = sc.nextInt();

            int rotate = 0;
            String replace = sc.next().replace("[", "").replace("]", "");
            List<Integer> list;
            if (replace.isEmpty()) {
                list = new ArrayList<>();
            } else {
                list = Arrays.stream(replace.split(","))
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
            }

            boolean success = true;
            for (char command : commands) {
                if (command == 'R') {
                    rotate = rotate == 0 ? list.size() - 1 : 0;
                } else if (command == 'D') {
                    try {
                        list.remove(rotate);
                        if (rotate > 0) {
                            rotate--;
                        }
                    } catch (RuntimeException e) {
                        success = false;
                        break;
                    }
                }
            }

            if (!success) {
                System.out.println("error");
                continue;
            }

            StringBuilder sb = new StringBuilder("[");
            if (rotate > 0) {
                Collections.reverse(list);
            }
            String collect = list.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            sb.append(collect).append("]");

            System.out.println(sb);
        }
    }
}

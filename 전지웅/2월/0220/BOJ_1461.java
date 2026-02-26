import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // input
        List<Integer> positiveBooks = new ArrayList<>();
        List<Integer> negativeBooks = new ArrayList<>();

        st = new StringTokenizer(br.readLine(), " ");

        for (int i = 0; i < N; i++) {
            int input = Integer.parseInt(st.nextToken());
            if (input >= 0) {
                positiveBooks.add(input);
            } else {
                negativeBooks.add(input);
            }
        }
        // 절댓값이 작은 순서로 정렬
        positiveBooks.sort(Comparator.naturalOrder());
        negativeBooks.sort(Comparator.reverseOrder());

        int sum = 0;

        int min = 0;
        int max = 0;
        if (!negativeBooks.isEmpty()) {
            min = negativeBooks.get(negativeBooks.size() - 1);
        }
        if (!positiveBooks.isEmpty()) {
            max = positiveBooks.get(positiveBooks.size() - 1);
        }

        // 최대 거리가 짧은 방향부터(인덱스 없을 때 예외처리 해야함)
        if (!negativeBooks.isEmpty() && !positiveBooks.isEmpty()) {
            if (-min < max) {
                while (!negativeBooks.isEmpty()) {
                    int last_index = negativeBooks.size() - 1;
                    sum -= negativeBooks.get(last_index) * 2;
                    for (int i = 0; i < M; i++) {
                        negativeBooks.remove(last_index--);
                        if (negativeBooks.isEmpty()) {
                            break;
                        }
                    }
                }
                while (!positiveBooks.isEmpty()) {
                    int last_index = positiveBooks.size() - 1;
                    sum += positiveBooks.get(last_index) * 2;
                    for (int i = 0; i < M; i++) {
                        positiveBooks.remove(last_index--);
                        if (positiveBooks.isEmpty()) {
                            sum -= max;
                            break;
                        }
                    }
                }
            } else {
                while (!positiveBooks.isEmpty()) {
                    int last_index = positiveBooks.size() - 1;
                    sum += positiveBooks.get(last_index) * 2;
                    for (int i = 0; i < M; i++) {
                        positiveBooks.remove(last_index--);
                        if (positiveBooks.isEmpty()) {
                            break;
                        }
                    }
                }
                while (!negativeBooks.isEmpty()) {
                    int last_index = negativeBooks.size() - 1;
                    sum -= negativeBooks.get(last_index) * 2;
                    for (int i = 0; i < M; i++) {
                        negativeBooks.remove(last_index--);
                        if (negativeBooks.isEmpty()) {
                            sum += min;
                            break;
                        }
                    }
                }
            }
        } else if (positiveBooks.isEmpty()) {
            while (!negativeBooks.isEmpty()) {
                int last_index = negativeBooks.size() - 1;
                sum -= negativeBooks.get(last_index) * 2;
                for (int i = 0; i < M; i++) {
                    negativeBooks.remove(last_index--);
                    if (negativeBooks.isEmpty()) {
                        sum += min;
                        break;
                    }
                }
            }
        } else {
            while (!positiveBooks.isEmpty()) {
                int last_index = positiveBooks.size() - 1;
                sum += positiveBooks.get(last_index) * 2;
                for (int i = 0; i < M; i++) {
                    positiveBooks.remove(last_index--);
                    if (positiveBooks.isEmpty()) {
                        sum -= max;
                        break;
                    }
                }
            }
        }
        System.out.println(sum);
    }
}
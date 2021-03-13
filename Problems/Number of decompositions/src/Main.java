import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        printPartitions(input, input, "");
    }

    public static void printPartitions(int target, int max, String prefix) {
        if (target == 0) {
            System.out.println(prefix.trim());
        } else {
            for (int i = 1; i <= max && i <= target; i++) {
                printPartitions(target - i, i, prefix + " " + i);
            }
        }
    }
}
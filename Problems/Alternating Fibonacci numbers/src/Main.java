import java.util.Scanner;

public class Main {

    public static long fib(long n){
        // write your code here
        if (n == 0) return 0;
        else if (n == 1) return 1;
        else return -(fib(n - 1) - fib(n - 2));
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(fib(n));
    }
}
package simple;

import java.util.Scanner;

/**
 * des: Calculate a + b
 * input: The input will consist of a series of pairs of integers a and b,separated by a space, one pair of integers per line.
 * output: For each pair of input integers a and b you should output the sum of a and b in one line,and with one line of output for each line in input.
 * analysis: 简单的求和
 */
public class APlusBProblem {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()) {
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(a + b);
        }
    }
}

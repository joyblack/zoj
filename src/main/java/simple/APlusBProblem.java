package simple;

import java.util.Scanner;

/**
 * title: A + B Problem
 * url: https://zoj.pintia.cn/problem-sets/91827364500/problems/91827364500
 * description: 简单的求和
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

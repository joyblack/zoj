package simple;

import java.util.Scanner;
/**
 * title: Do the Untwist
 * url: https://zoj.pintia.cn/problem-sets/91827364500/problems/91827364505
 * description: 就是一套机密规则：ciphercode[i] = (plaincode[ki mod n] - i) mod 28
 *  ciphercode: 密文
 *  plaincode：明文
 */
public class DoTheUntwist {
    // 字符参考字典 0 = '_', ..., 27 = '.'
    private static final char[] CODE_ARRAY = {
            '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', '.'
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String[] line = scanner.nextLine().split(" ");
            int k = Integer.parseInt(line[0]);
            if (k == 0) {
                break;
            }
            // 解密
            System.out.println(String.valueOf(solve(line[1].toCharArray(), k)));
        }
    }

    /**
     * @description 解析密文：ciphercode[i] = (plaincode[ki mod n] - i) mod 28
     * @author JoyBlack
     * @date 2021/2/3
     */
    private static char[] solve(char[] cipherText, int k) {
        char[] result = new char[cipherText.length];
        int n = cipherText.length;
        // 依次解析
        for (int i = 0; i < cipherText.length; i++) {
            // 1. 计算ciphercode[i]
            int cipherCodeI = find(CODE_ARRAY, cipherText[i]);
            // 计算 ki mod n
            int kiModN = mod(k * i, n);
            // 求解plaincode[ki mod n]的合适值
            for (int j = 0; j <= 27; j++) {
                if (mod(j - i, 28) == cipherCodeI) {
                    result[kiModN] = CODE_ARRAY[j];
                }
            }
        }
        return result;
    }
    
    /**
     * @description 该方法中的求余计算，如果结果为负数，结果+y
     * @author JoyBlack
     * @date 2021/2/3
     */
    private static int mod(int x, int y){
        int result = x % y;
        return result < 0 ? (result + y) : result;
    }


    /**
     * @description 从char数组中寻找第一个出现的字符的下标，找不到返回-1
     * @author JoyBlack
     * @date 2021/1/28
     */
    private static int find(char[] array, char c) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }
        return index;
    }

}

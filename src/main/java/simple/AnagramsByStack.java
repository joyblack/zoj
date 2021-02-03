package simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
/**
 * title: Anagrams by Stack
 * url: https://zoj.pintia.cn/problem-sets/91827364500/problems/91827364503
 * description: 第一行给定字符串，对其进行入栈出栈操作之后，获得第二行的字符串。i代表入栈，o代表出栈，结果一般有多种方式，输出的时候需要按照字符串的字典序进行排列。
 *  显然采用深度优先进行遍历生成即可。
 */
public class AnagramsByStack {
    // 合法并且能够生成目标序列的指令序列
    private static List<String> ORDER_LIST;

    // 目标字符串
    private static char[] TARGET;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            String source = in.nextLine();
            TARGET = in.nextLine().toCharArray();
            ORDER_LIST = new ArrayList<>();
            if (source.length() == TARGET.length) {
                // 深度优先搜索
                dfs(source, "", "", "");
            }
            // 打印结果
            printResult();
        }
    }

    /**
     * @description 深度优先搜索：order: 当前操作的指令序列，target：当前生成的目标字符串
     * @author JoyBlack
     * @date 2021/1/28
     */
    public static void dfs(String source, String order, String target, String stack) {
        // 检测当前的结果串是否是目标字符串的子串，如果不是，则没有进行下去的必要
        if (target.length() != 0 && !isParentBegin(target.toCharArray(), TARGET)) {
            return ;
        }
        // 若测试结果序列长度已经等于目标序列长度，说明完全匹配，将指令集加到结果集中
        if (target.length() == TARGET.length) {
            ORDER_LIST.add(order);
            return ;
        }
        // 出栈：取栈的最后一个元素，附加到当前的target, source保持不变
        if (stack.length() > 0) {
            dfs(source, formatOrderStyle(order, 'o'), target + stack.charAt(stack.length() - 1), stack.substring(0, stack.length() - 1));
        }
        // 入栈：取source的第一个元素，附加到栈中，target保持不变
        if (source.length() > 0) {
            dfs(source.substring(1), formatOrderStyle(order, 'i'), target, stack + source.charAt(0));
        }
    }

    /**
     * @description 按字典序打印结果
     * @author JoyBlack
     * @date 2021/1/28
     */
    private static void printResult() {
        System.out.println("[");
        if (ORDER_LIST.size() > 0) {
            Collections.sort(ORDER_LIST);
            ORDER_LIST.forEach(System.out::println);
        }
        System.out.println("]");
    }

    /**
     * @description 检测两个child是否是parent从0开始的串
     * @author JoyBlack
     * @date 2021/1/28
     */
    private static boolean isParentBegin(char[] child, char[] parent) {
        int childLength = child.length;
        if (childLength > parent.length) {
            return false;
        } else {
            for (int i = 0; i < childLength; i++) {
                if (child[i] != parent[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * @description 格式化组合生成的i o 字符
     * @author JoyBlack
     * @date 2021/1/28
     */
    private static String formatOrderStyle(String order, char io) {
        // 若是第一个元素，则无需添加空格
        if (order.length() == 0) {
            return io + " ";
        } else { // 否则添加
            return order + io + " ";
        }
    }
}

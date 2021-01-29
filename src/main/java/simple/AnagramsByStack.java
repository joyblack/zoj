package simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 How can anagrams result from sequences of stack operations? There are two sequences of stack operators which can convert TROT to TORT:

 [
 i i i i o o o o
 i o i i o o i o
 ]
 where i stands for Push and o stands for Pop. Your program should, given pairs of words produce sequences of stack operations which convert the first word to the second.

 Input
 The input will consist of several lines of input. The first line of each pair of input lines is to be considered as a source word (which does not include the end-of-line character). The second line (again, not including the end-of-line character) of each pair is a target word. The end of input is marked by end of file.

 Output
 For each input pair, your program should produce a sorted list of valid sequences of i and o which produce the target word from the source word. Each list should be delimited by

 [
 ]
 and the sequences should be printed in "dictionary order". Within each sequence, each i and o is followed by a single space and each sequence is terminated by a new line.
 Process
 A stack is a data storage and retrieval structure permitting two operations:

 Push - to insert an item and
 Pop - to retrieve the most recently pushed item
 We will use the symbol i (in) for push and o (out) for pop operations for an initially empty stack of characters. Given an input word, some sequences of push and pop operations are valid in that every character of the word is both pushed and popped, and furthermore, no attempt is ever made to pop the empty stack. For example, if the word FOO is input, then the sequence:

 i i o i o o	is valid, but
 i i o	is not (it's too short), neither is
 i i o o o i	(there's an illegal pop of an empty stack)
 Valid sequences yield rearrangements of the letters in an input word. For example, the input word FOO and the sequence i i o i o o produce the anagram OOF. So also would the sequence i i i o o o. You are to write a program to input pairs of words and output all the valid sequences of i and o which will produce the second member of each pair from the first.

 Sample Input
 madam
 adamm
 bahama
 bahama
 long
 short
 eric
 rice
 Sample Output
 [
 i i i i o o o i o o
 i i i i o o o o i o
 i i o i o i o i o o
 i i o i o i o o i o
 ]
 [
 i o i i i o o i i o o o
 i o i i i o o o i o i o
 i o i o i o i i i o o o
 i o i o i o i o i o i o
 ]
 [
 ]
 [
 i i o i o i o o
 ]
 分析：第一行给定字符串，对其进行入栈出栈操作之后，获得第二行的字符串。i代表入栈，o代表出栈，结果一般有多种方式，输出的时候需要按照字符串的字典序进行排列。
 显然采用深度优先进行遍历生成即可。
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

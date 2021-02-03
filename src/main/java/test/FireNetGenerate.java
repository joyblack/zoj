package test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @description 生成FireNet的所有可能的测试数据
 * @author JoyBlack
 * @date 2021/1/27
 */
public class FireNetGenerate {
    // 空地
    private static final char CITY_EMPTY = '.';

    // 墙
    private static final char CITY_WALL = 'X';

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        queue.offer(6);

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}

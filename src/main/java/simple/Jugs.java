package simple;

import java.util.*;
/**
 * title: Fire Jugs
 * url: https://zoj.pintia.cn/problem-sets/91827364500/problems/91827364504
 * description: 通过几种倾倒方式，使得A或者B中拥有N加仑的油。
 *     思路1：看起来是个广度优先可以解决的问题，关键是搜索过程中的及时剪枝,但是这个题目的例子又是B中有N加仑，和题目描述明显不一致
 */
public class Jugs {
    // A,B分别代表当前A,B容器的容量, N代表目标加仑数
    private static int A, B, N;

    // 可使用的指令集
    private static final String[] ORDER = {"fill A", "fill B", "empty A", "empty B", "pour A B", "pour B A"};

    // 实现BFS的队列
    private static Queue<Scene> queue;

    // 是否已经找到合适的方式
    private static boolean find;

    // 符合要求的结果集
    private static List<String> ordersResult;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            A = in.nextInt();
            B = in.nextInt();
            N = in.nextInt();
            ordersResult = new ArrayList<>();
            // 初始化队列
            queue = new LinkedList<>();
            queue.offer(new Scene(new ArrayList<>(), 0, 0));
            // 初始化终止标识
            find = false;
            // 开始处理
            solve();
            // 打印结果
            printResult(ordersResult);
        }
    }

    /**
     * @description 处理数据
     * @author JoyBlack
     * @date 2021/2/2
     */
    private static void solve() {
        if (find) {
            return ;
        }
        Scene scene = queue.poll();
        // 一般而言，这里不会出现空值的
        assert scene != null;
        for (String order : ORDER) {
            int a = scene.getA();
            int b = scene.getB();
            // 计算a和b的新值
            switch (order) {
                // 由于B容器是结果容器，因此先填充他以获取最优解
                case "fill B":  // fill B: 只有在B容器不满的时候，此操作有意义
                    if (b != B) {
                        b = B;
                    } else {
                        continue;
                    }
                    break;
                case "fill A":   // fill A: 只有在A容器不满的时候，此操作有意义
                    if (a != A) {
                        a = A;
                    } else {
                        continue;
                    }
                    break;
                case "empty A":  // empty A: 只有在A容器不空的时候，此操作有意义
                    if (a != 0) {
                        a = 0;
                    } else {
                        continue;
                    }
                    break;
                case "empty B":  // empty B: 只有在B容器不空的时候，此操作有意义
                    if (b != 0) {
                        b = 0;
                    } else {
                        continue;
                    }
                    break;
                case "pour A B":  // pour A B: 只有在A容器不空并且B容器不满的时候，此操作有意义
                    // 求解本次操作会A->B倾倒的加仑数
                    if (a != 0 && b != B) {
                        int offset = (B - b) > (a) ? (a) : (B - b);
                        a = a - offset;
                        b = b + offset;
                    } else {
                        continue;
                    }

                    break;
                case "pour B A":  // pour A B: 只有在A容器不满并且B容器不空的时候，此操作有意义
                    // 求解本次操作会B->A倾倒的加仑数
                    if (a != A && b != 0) {
                        int offset = (A - a) > (b) ? (b) : (A - a);
                        a = a + offset;
                        b = b - offset;
                    } else {
                        continue;
                    }
                    break;
            }
            List<String> tempOrderList = new ArrayList<>(scene.getOrderList());
            tempOrderList.add(order);
            // 达成目标，有个例子以B为N才停止，因此这里的逻辑改为只有b==n而不是a == N || b == N
            if (b == N) {
                ordersResult = tempOrderList;
                find = true;
                return;
            } else { // 未达成目标，添加进队列，保留现场环境
                queue.offer(new Scene(tempOrderList, a, b));
            }
        }
        // 继续搜索
        solve();
    }

    /**
     * @description 打印指令集
     * @author JoyBlack
     * @date 2021/2/2
     */
    private static void printResult(List<String> ordersResult) {
        ordersResult.add("success");
        ordersResult.forEach(System.out::println);
    }
}

/**
 * @description 存储现场环境的类
 * @author JoyBlack
 * @date 2021/2/2
 */
class Scene {
    // 当前现场的指令序列
    private List<String> orderList;

    // 当前现场的A容器、B容器的加仑数量
    private int a;
    private int b;

    public Scene(List<String> orderList, int a, int b) {
        this.orderList = orderList;
        this.a = a;
        this.b = b;
    }

    public List<String> getOrderList() {
        return orderList;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}

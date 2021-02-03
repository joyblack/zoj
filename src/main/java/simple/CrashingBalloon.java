package simple;

import java.util.Scanner;
/**
 * title: Crashing Balloon
 * url: https://zoj.pintia.cn/problem-sets/91827364500/problems/91827364502
 * description: 大致解释：两个分数。如果第二个分数比较低，那么看分数高的是否不是1-100乘起来某个数得到的，若不是，则分数低的获胜。
 *  特殊条件：如果两个都是假的，挑战不成立，则取最大值显示。
 */
public class CrashingBalloon {
    // 低分玩家说的是否为真话
    private static boolean MIN_PLAYER_IS_TRUE;

    // 高分玩家说的是否为真话
    private static boolean MAX_PLAYER_IS_TRUE;


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()) {
            int a = in.nextInt();
            int b = in.nextInt();
            // 较大的一个数
            int max = a > b ? a : b;
            // 较小的一个数
            int min = a > b ? b : a;
            // 初始化
            MIN_PLAYER_IS_TRUE = false;
            MAX_PLAYER_IS_TRUE = false;

            // 测试说谎者
            battle(max, min, 100);

            // 只有高分玩家说谎、低分玩家说真话的时候才会输出低分玩家的值
            if (MIN_PLAYER_IS_TRUE && !MAX_PLAYER_IS_TRUE) {
                System.out.println(min);
            } else {
                System.out.println(max);
            }
        }
    }

    /**
     * @description 挑战测试: max：大数， min：小数，factor：当前测试的因子
     * @author JoyBlack
     * @date 2021/1/28
     */
    public static void battle(int max, int min, int factor) {
        // 若已经得到答案证明高分玩家没有说谎，退出检索
        if (MAX_PLAYER_IS_TRUE) {
            return ;
        }
        // 高分玩家可以进行因式分解的同时，低分玩家也可以进行因式分解，说明高分玩家能进行因式分解的同时，也没有使用低分玩家所需要的因子作为支撑
        if (max == 1 && min == 1) {
            MAX_PLAYER_IS_TRUE = true;
        }
        // 低分玩家可以进行因式分解，说明低分玩家并未说谎，但在此条因式分解的枝上，高分玩家还未达到完全分解，还需继续进行分解
        if (min == 1) {
            MIN_PLAYER_IS_TRUE = true;
        }
        while(factor > 1) {
            // factor是max的一个因子
            if (max % factor == 0) {
                // 继续搜索
                battle(max / factor, min, factor - 1);
            }
            // factor是min的一个因子
            if (min % factor == 0) {
                // 继续搜索
                battle(max, min / factor, factor - 1);
            }
            // 若没有，则往下递减测试
            factor --;
        }
    }
}

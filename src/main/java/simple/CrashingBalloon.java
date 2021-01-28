package simple;

import java.util.Scanner;

/**
 On every June 1st, the Children's Day, there will be a game named "crashing balloon" on TV.   The rule is very simple.  On the ground there are 100 labeled balloons, with the numbers 1 to 100.  After the referee shouts "Let's go!" the two players, who each starts with a score of  "1", race to crash the balloons by their feet and, at the same time, multiply their scores by the numbers written on the balloons they crash.  After a minute, the little audiences are allowed to take the remaining balloons away, and each contestant reports his\her score, the product of the numbers on the balloons he\she's crashed.  The unofficial winner is the player who announced the highest score.

 Inevitably, though, disputes arise, and so the official winner is not determined until the disputes are resolved.  The player who claims the lower score is entitled to challenge his\her opponent's score.  The player with the lower score is presumed to have told the truth, because if he\she were to lie about his\her score, he\she would surely come up with a bigger better lie.  The challenge is upheld if the player with the higher score has a score that cannot be achieved with balloons not crashed by the challenging player.  So, if the challenge is successful, the player claiming the lower score wins.

 So, for example, if one player claims 343 points and the other claims 49, then clearly the first player is lying; the only way to score 343 is by crashing balloons labeled 7 and 49, and the only way to score 49 is by crashing a balloon labeled 49.  Since each of two scores requires crashing the balloon labeled 49, the one claiming 343 points is presumed to be lying.

 On the other hand, if one player claims 162 points and the other claims 81, it is possible for both to be telling the truth (e.g. one crashes balloons 2, 3 and 27, while the other crashes balloon 81), so the challenge would not be upheld.

 By the way, if the challenger made a mistake on calculating his/her score, then the challenge would not be upheld. For example, if one player claims 10001 points and the other claims 10003, then clearly none of them are telling the truth. In this case, the challenge would not be upheld.

 Unfortunately, anyone who is willing to referee a game of crashing balloon is likely to get over-excited in the hot atmosphere that he\she could not reasonably be expected to perform the intricate calculations that refereeing requires.  Hence the need for you, sober programmer, to provide a software solution.

 Input
 Pairs of unequal, positive numbers, with each pair on a single line, that are claimed scores from a game of crashing balloon.
 Output
 Numbers, one to a line, that are the winning scores, assuming that the player with the lower score always challenges the outcome.
 Sample Input
 343 49
 3599 610
 62 36

 Sample Output
 49
 610
 62

 大致解释：两个分数。如果第二个分数比较低，那么看分数高的是否不是1-100乘起来某个数得到的，若不是，则分数低的获胜。
 特殊条件：如果两个都是假的，挑战不成立，则取最大值显示。
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

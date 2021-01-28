package simple;

import java.util.Scanner;

/**
 * 使用回朔法求解FireNet
 */
public class FireNet2 {
    // 空地
    private static final char CITY_EMPTY = '.';

    // 碉堡
    private static final char CITY_HOUSE_BOARD = 'O';

    // 墙
    private static final char CITY_WALL = 'X';

    // 可以放置碉堡的最大值答案
    private static int maxNum;

    // 城市地图
    private static char[][] city = new char[4][4];

    // 城市大小
    private static int size;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 读取城市地图
        while((size = scanner.nextInt()) != 0) {
            maxNum = 0;
            scanner.nextLine();
            for (int i = 0; i < size; i++) {
                String line = scanner.nextLine();
                char[] charArray = line.toCharArray();
                if (size >= 0) System.arraycopy(charArray, 0, city[i], 0, size);
            }
            // 搜索地图
            search(0,0);
            System.out.println(maxNum);
        }
    }

    /**
     * @description 搜索地图: curNum是当前可回溯方式下的碉堡数量
     * @author JoyBlack
     * @date 2021/1/27
     */
    private static void search(int k, int curNum) {
        int x,y;
        if (k== size * size) {//到最后一个了
            if(curNum>maxNum) {//保存当前遍历路径找到的最大值
                maxNum=curNum;
            }
        }
        else {
            x = k / size;
            y = k % size;
            //当前点是空白处，并且可以放置碉堡
            if(city[x][y] == CITY_EMPTY && canSetHouseBoard(x,y)) {
                city[x][y] = CITY_HOUSE_BOARD;//放置碉堡
                // 递归进入下一个位置
                search(k + 1,curNum + 1);
                // 回溯
                city[x][y] = CITY_EMPTY;
            }
            //当前点不能放置或回溯回来
            search(k+1,curNum);
        }
    }

    /**
     * @description 测试指定位置是否可以放置碉堡，由于我们是从左上到左下遍历，因此只需检测测试点左上方的坐标点即可
     * @author JoyBlack
     * @date 2021/1/27
     */
    private static boolean canSetHouseBoard(int row, int col) {
        int i;
        //测试col列上是否有面对面的碉堡
        for (i = row - 1; i >= 0; --i)
        {
            if (city[i][col] == CITY_HOUSE_BOARD)
                return false;
            if (city[i][col] == CITY_WALL)
                break;
        }
        //测试row行上是否有面对面的碉堡
        for (i = col - 1; i >= 0; --i) {
            if (city[row][i] == CITY_HOUSE_BOARD)
                return false;
            if (city[row][i] == CITY_WALL)
                break;
        }
        return true;
    }

}




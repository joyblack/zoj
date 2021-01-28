package simple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Suppose that we have a square city with straight streets. A map of a city is a square board with n rows and n columns, each representing a street or a piece of wall.
 *
 * A blockhouse is a small castle that has four openings through which to shoot. The four openings are facing North, East, South, and West, respectively. There will be one machine gun shooting through each opening.
 *
 * Here we assume that a bullet is so powerful that it can run across any distance and destroy a blockhouse on its way. On the other hand, a wall is so strongly built that can stop the bullets.
 *
 * The goal is to place as many blockhouses in a city as possible so that no two can destroy each other. A configuration of blockhouses is legal provided that no two blockhouses are on the same horizontal row or vertical column in a map unless there is at least one wall separating them. In this problem we will consider small square cities (at most 4x4) that contain walls through which bullets cannot run through.
 *
 * The following image shows five pictures of the same board. The first picture is the empty board, the second and third pictures show legal configurations, and the fourth and fifth pictures show illegal configurations. For this board, the maximum number of blockhouses in a legal configuration is 5; the second picture shows one way to do it, but there are several other ways.
 *
 *
 * Your task is to write a program that, given a description of a map, calculates the maximum number of blockhouses that can be placed in the city in a legal configuration.
 *
 * The input file contains one or more map descriptions, followed by a line containing the number 0 that signals the end of the file. Each map description begins with a line containing a positive integer n that is the size of the city; n will be at most 4. The next n lines each describe one row of the map, with a '.' indicating an open space and an uppercase 'X' indicating a wall. There are no spaces in the input file.
 *
 * For each test case, output one line containing the maximum number of blockhouses that can be placed in the city in a legal configuration.
 *
 * Sample input:
 *
 * 4
 * .X..
 * ....
 * XX..
 * ....
 * 2
 * XX
 * .X
 * 3
 * .X.
 * X.X
 * .X.
 * 3
 * ...
 * .XX
 * .XX
 * 4
 * ....
 * ....
 * ....
 * ....
 * 0
 * Sample output:
 *
 * 5
 * 1
 * 5
 * 2
 * 4
 *
 * analysis: 在一个N*N图中，黑色方块代表墙（X），白色方块代表街道（.），现在要在其中放置碉堡（黑色大圆），放置的规则是两个碉堡之间东南西北正向不可达（X可以阻挡），求能放置碉堡的最大数量。
 * 思路1. 我们假设：矩阵的阶为N，碉堡数量为B, 街道数量为S，墙的数量为W。显然，B为我们所要求的结果，那么有：
 * （1）N * N = B + W；
 * （2）B∈[0, S]；
 * （3）Max_B=S，每有两个S横或者纵相邻，就会有Max_B - 1. （有待验证，最终验证错误）
 * 
 * 思路2. 使用贪心算法，依次尝试每个可能的街道称为碉堡，直到遍历完所有街道。关键是以哪里为起点，哪些点有资格作为碉堡的点。也就是权重比较高的点，假设点的旁边空地越少，权重越大。
 * 为了实现这个思路，我们需要求解一个权重矩阵。初始大家的权重都为4+4，旁边毎有一处空地，权重-1
 */
public class FireNet {
    // 空地
    private static final char CITY_EMPTY = '.';

    // 墙
    private static final char CITY_WALL = 'X';

    // 碉堡
    private static final char CITY_HOUSE_BOARD = 'O';



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 当前地图的大小（矩阵的阶）
        int size;

        // 读取输入矩阵
        while((size = scanner.nextInt()) != 0) {
            // 读取城市地图
            char[][] city = readCity(scanner, size);
            // 计算权重数组
            List<WeightMatrix> weights = getWeightList(city, size);
            // 尽可能的设置碉堡
            setHouseBoard(weights, city, size);
            // 输出结果
            System.out.println(getHouseBoardNum(weights));
            // 计算碉堡数量
            // System.out.println("碉堡的数量为：" + getHouseBoardNum(weights));
            // 输出地图
            // System.out.println("最终生成的地图为:");
//            for (int i = 0; i < size; i++) {
//                for (int j = 0; j < size; j++) {
//                    System.out.print(city[i][j] + " ");
//                }
//                System.out.println("");
//            }

        }
    }

    /**
     * @description 读取初始化地图
     * @author JoyBlack
     * @date 2021/1/26
     */
    private static char[][] readCity(Scanner scanner, int size) {
        char[][] city = new char[size][size];
        scanner.nextLine();
        for (int i = 0; i < size; i++) {
            char[] line = scanner.nextLine().toCharArray();
            System.arraycopy(line, 0, city[i], 0, size);
        }
        return city;
    }

    /**
     * @description 获取可放置碉堡的空地的权重数组，并按从大到小排序
     * @author JoyBlack
     * @date 2021/1/27
     */
    private static List<WeightMatrix> getWeightList(char[][] city, int size) {
        List<WeightMatrix> result = new ArrayList<>();
        // 初始化权重数组
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result.add(new WeightMatrix(i, j, city[i][j] == 'X' ? -1 : 1000, city[i][j]));
            }
        }
        // 设置权重数组的数据
        for (WeightMatrix r : result) {
            // 忽略城墙
            if (r.type == CITY_WALL) {
                continue;
            }
            // 检测每个方向的数据，若未溢出并且为街道都要将权重值-1
            // 北
            for (int i = 1; i <= 3; i++) {
                if (inBound(r.x - i, size) && city[r.x - i][r.y] == CITY_EMPTY) {
                    r.weight = r.weight - 1;
                }
            }

            // 南
            for (int i = 1; i <= 3; i++) {
                if (inBound(r.x + i, size) && city[r.x + i][r.y] == CITY_EMPTY) {
                    r.weight = r.weight - 1;
                }
            }
            // 西
            for (int i = 1; i <= 3; i++) {
                if (inBound(r.y - i, size) && city[r.x][r.y - i] == CITY_EMPTY) {
                    r.weight = r.weight - 1;
                }
            }
            // 东
            for (int i = 1; i <= 3; i++) {
                if (inBound(r.y + i, size) && city[r.x][r.y + i] == CITY_EMPTY) {
                    r.weight = r.weight - 1;
                }
            }
        }
        result.sort(Comparator.comparing(WeightMatrix::getWeight).reversed());
        return result;
    }

    /**
     * @description 检查下标是否越界
     * @author JoyBlack
     * @date 2021/1/27
     */
    private static boolean inBound(int pos, int size) {
        return pos >= 0 && pos < size;
    }

    /**
     * @description 计算设置为碉堡的数量
     * @author JoyBlack
     * @date 2021/1/27
     */
    private static long getHouseBoardNum(List<WeightMatrix> weights) {
        return weights.stream().filter(WeightMatrix::isO).count();
    }

    /**
     * @description 尝试设置碉堡
     * @author JoyBlack
     * @date 2021/1/27
     */
    private static void setHouseBoard(List<WeightMatrix> weights, char[][] city, int size) {
        for (WeightMatrix weight : weights) {
            if ('.' == weight.type) {
                // 是否可以将当前点设置为碉堡
                boolean success = true;
                // 检查联通的所有空地（直到遇到边界或者城墙）是否有碉堡: 若遇到空地，则继续；若遇到城墙，则直接跳过，说明此方向上合法；
                // 最后一种情况，遇到碉堡，该点不适合作为碉堡
                // 往北查询
                for(int x = weight.x - 1; x >=0; x -- ) {
                    if (city[x][weight.y] == CITY_WALL) {
                        break;
                    } else if (city[x][weight.y] == CITY_HOUSE_BOARD) {
                        success = false;
                        break;
                    }
                }
                if (!success) {
                    continue;
                }

                // 往南
                for(int x = weight.x + 1; x < size; x ++ ) {
                    if (city[x][weight.y] == CITY_WALL) {
                        break;
                    } else if (city[x][weight.y] == CITY_HOUSE_BOARD) {
                        success = false;
                        break;
                    }
                }
                if (!success) {
                    continue;
                }


                // 往西
                for(int y = weight.y - 1; y >=0; y -- ) {
                    if (city[weight.x][y] == CITY_WALL) {
                        break;
                    } else if (city[weight.x][y] == CITY_HOUSE_BOARD) {
                        success = false;
                        break;
                    }
                }
                if (!success) {
                    continue;
                }


                // 往东
                for(int y = weight.y + 1; y < size; y ++) {
                    if (city[weight.x][y] == CITY_WALL) {
                        break;
                    } else if (city[weight.x][y] == CITY_HOUSE_BOARD) {
                        success = false;
                        break;
                    }
                }
                if (!success) {
                    continue;
                }

                // 说明没有遇到碉堡，可设置为碉堡
                weight.type = CITY_HOUSE_BOARD;
                city[weight.x][weight.y] = CITY_HOUSE_BOARD;
            }
        }
    }
}

/**
 * @description 权重对象，记录坐标信息、权重值以及是否被置为碉堡
 * @author JoyBlack
 * @date 2021/1/27
 */
class WeightMatrix {
    // X坐标点
    public int x;
    // Y坐标点
    public int y;
    // 权重值
    public int weight;
    // 当前点类别（. - 空地；X - 城墙；O - 碉堡）
    public char type;

    public WeightMatrix() {
    }

    public WeightMatrix(int x, int y, int weight, char type) {
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.type = type;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isO() {
        return type == 'O';
    }

    @Override
    public String toString() {
        return "WeightMatrix{" +
                "x=" + x +
                ", y=" + y +
                ", weight=" + weight +
                ", type=" + type +
                '}';
    }
}




package simple;

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
 * analysis: 在一个N*N图中，黑色方块代表墙（X），白色方块代表街道（.），现在要在其中放置碉堡（黑色大圆），放置的规则是两个碉堡之间东南西北正向不可达（X可以阻挡）
 * 1. 整道题如果使用穷举法，那么最多可能需要模拟2^16种地图模拟；如果通过剪枝的方式
 *
 */
public class FireNet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 城市地图
        char[][] city = null;
        // 当前地图的大小（矩阵的阶）
        int size = 0;

        // 读取输入矩阵
        while((size = scanner.nextInt()) != 0) {
            city = new char[size][size];
            scanner.nextLine();
            for (int i = 0; i < size; i++) {
                char[] line = scanner.nextLine().toCharArray();
                System.arraycopy(line, 0, city[i], 0, size);
            }
        }

        System.out.println(city.length);


    }
}

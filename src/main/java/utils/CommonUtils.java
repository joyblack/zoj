package utils;

public class CommonUtils {
    /**
     * @description 检测两个char数组是否相等
     * @author JoyBlack
     * @date 2021/1/28
     */
    private static boolean isMatch(char[] a, char[] b) {
        if (a.length == b.length) {
            int length = a.length;
            for (int i = 0; i < length; i++) {
                if (a[i] != b[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
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

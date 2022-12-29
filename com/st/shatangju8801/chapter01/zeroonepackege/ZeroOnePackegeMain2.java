package com.st.shatangju8801.chapter02.zeroonepackege;

/**
 * 对于技术面试，你还在死记硬背么?
 * 快来“播”沙糖橘吧，
 * 用视频案例为你实战解读技术难点
 * 聚焦Java技术疑难点，实战视频为你答疑解惑
 * 越“播”越甜的沙糖橘，期待你的到来和参与 
 */
public class ZeroOnePackegeMain2 {
    public static void main(String[] args) {
        // 定义物品的个数
        int n = 5;
        // 定义背包的容量
        int m = 10;
        // 定义物品的重量数组
        int w[] = {0, 2, 2, 6, 5, 4};
        // 定义物品的价值数组
        int v[] = {0, 6, 3, 5, 4, 6};
        // 定义状态转移一维数组--进行了空间优化
        // 变换的原因：算法描述中下标从1开始，java编码中下标从0开始，为了算法描述和算法java代码实现中保持一致，
        // 在下标为0的位置添加一个重量和价值都为0的物品进行填充
        int dp[] = new int[m + 1];

        // 状态转移一维数组初始化
        // 根据不同的背包算法进行初始化
        // 0-1背包问题的状态转移方程组的初始化为：全部初始化为0
        // int数组默认全部初始化为0，满足0-1背包的初始化条件，故，不再显式的进行初始化处理
        // 调用动态规划-背包算法
        // TODO 调用子算法
        test01(m, w, v, dp);

        // 打印背包算法的结果
        System.out.println("动态规划-背包算法的最优解：" + dp[dp.length - 1]);
        // 打印动态规划算法过程
        printArr(dp);
    }

    /**
     * 公众号沙糖橘：shatangju8801，请留意QQ交流群：798470346，沙糖橘社区倾心锻造~_~
     * <p>
     * 动态规划算法-0-1背包问题，空间优化算法 【j 只能进行逆向遍历】
     *
     * @param m  背包的容量
     * @param w  选取物品的重量数组
     * @param v  选取物品的价值数组
     * @param dp 动态规划的过程记录一维数组
     */
    public static void test01(int m, int w[], int v[], int dp[]) {
        // i表示可以选择的物品个数， i 表示当前动态规划到的第i个物品
        for (int i = 1; i < w.length; i++) {
            // j 表示当前背包的可用容量
            // 在使用一维数组dp进行0-1背包算法实现时，背包可用容量j的循环一定要从大到小逆序遍历。
            // j循环的下标还可以进行优化：int bound = max(w[i],m - sum{w[i + 1]...w[n]}) // m 是背包的总容量
            // 在大数据量下是可以大大提升算法效率的
            // 逆序遍历
            for (int j = m; j >= w[i]; j--) { // j 使用逆序遍历，就是0-1背包算法
                // 需要在装入背包和不装入背包两种状态下的值进行比较，选取最值作为当前步的最优解
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }
            // 打印中间临时过程，运行结束之后，通过dp是无法获取中间结果的
            // printArr(dp);
        }
    }

    /**
     * 公众号沙糖橘：shatangju8801，请留意QQ交流群：798470346，沙糖橘社区倾心锻造~_~
     * <p>
     * 打印数组内容（一维数组）
     *
     * @param dp 被打印的数组
     */
    public static void printArr(int dp[]) {
        if (dp != null) {
            for (int item : dp) {
                System.out.print("  " + item);
            }
            // 换行
            System.out.println();
        }
    }
}

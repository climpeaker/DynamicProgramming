# DynamicProgramming
# 动态规划-背包问题详解系列
## 动态规划系列

### 动态规划基础概念

动态规划问题是一系类的问题，这一系列问题核心代表是背包问题，此外还有矩阵最小路径和、最长公共子序列问题、最长上升子序列问题、台阶问题（斐波那契数列及扩展）等等。

本文选取背包问题作为动态规划思想的讲解案例。关于背包问题的概念，先看一下百度百科和维基百科的相关介绍。

百度百科：

背包问题(Knapsack problem)是一种组合优化的[NP完全问题](https://baike.baidu.com/item/NP完全问题)。问题可以描述为：给定一组物品，每种物品都有自己的重量和价格，在限定的总重量内，我们如何选择，才能使得物品的总价格最高。问题的名称来源于如何选择最合适的物品放置于给定背包中。相似问题经常出现在商业、组合数学，计算复杂性理论、密码学和应用数学等领域中。也可以将背包问题描述为决定性问题，即在总重量不超过W的前提下，总价值是否能达到V？它是在1978年由R.Merkle和M.Hellman出的。

背包问题已经研究了一个多世纪，早期的作品可追溯到1897年数学家托比亚斯·丹齐格（Tobias Dantzig，1884-1956）的早期作品  ，并指的是包装你最有价值或有用的物品而不会超载你的行李的常见问题。

维基百科：

根据维基百科，背包问题（Knapsack problem）是一种组合优化的NP完全（NP-Complete，NPC）问题。问题可以描述为：给定一组物品，每种物品都有自己的重量和价格，在限定的总重量内，我们如何选择，才能使得物品的总价格最高。NPC问题是没有多项式时间复杂度的解法的，但是利用动态规划，我们可以以伪多项式时间复杂度求解背包问题。一般来讲，背包问题有以下几种分类：

* 0-1背包问题

* 完全背包问题

* 多重背包问题

此外，还存在一些其他的算法要求，例如恰好装满、求方案总数、求所有的方案等。

### 2 0-1背包问题

#### 2.1 题目描述

最基本的背包问题就是0-1背包问题（0-1 knapsack problem）：

问题描述：

给定n种物品和一固定容量m的背包。物品i的体积【或者重量】是wi，其价值为vi，背包的容量为W。问应如何选择装入背包的物品，使得装入背包中物品的总价值最大?

在0-1背包问题中，对于一种物品，要么装入背包，要么不装。

对于一种物品的装入状态可以取0和1，设物品i的装入状态为xi，xi∈ (0,1)，此问题称为0-1背包问题。

#### 2.2 解题思路分析

如果采用暴力穷举的方式，每件物品都存在装入和不装入两种情况，所以总的时间复杂度是O(2^N)，这是不可接受的。而使用动态规划可以将复杂度降至O(NW)。我们的目标是背包内物品的总价值，而变量是物品和背包的限重，所以定义结果状态数组dp:

```text
dp[i][j] 表示将前i件物品装进容量为j的背包可以获得的最大价值, 0<=i<=N, 0<=j<=W
补充：j表示在选择“装入”物品i时背包可用的容量
```

那么可以将动态规划的状态结果数组`dp[i][j]`的第一列：`dp[0][0...W]`初始化为0，表示将前0个物品（即没有物品）装入容量为0...W的背包中依次对应的最大价值为0。那么当 i > 0 时，`dp[i][j]`有两种情况：

(1) 不装入第i件物品，即当前背包容量为j的情况下，背包中最大价值为：`dp[i−1][j]`；

(2) 装入第i件物品（前提是能装下），当前背包价值为：`dp[i−1][j−w[i]] + v[i]`。

在装入物品i之后，背包的价值不一定时最大价值，此时需要和不装入物品i时的背包的价值作比较，选择二者中的max作为当前的背包的最大价值，所有需要使用状态转移方程确认最大价值，状态转移方程为：

```text
dp[i][j] = max(dp[i−1][j], dp[i−1][j−w[i]]+v[i]) // j >= w[i]
```

下面解析一下这个状态转换方程，这个方程非常重要，是所有动态规划背包问题的基础，其他的相关背包问题都是在次基础上衍生出来的。

详细解读一下状态转换方程：

“将前 i 件物品放入容量为 j 的背包中”这个子问题，若只考虑第 i 件物品的策略（放或不放），那么就可以转化为一个只牵扯前 i−1 件物品的问题：

如果不放第 i 件物品，那么问题就转化为“前 i−1 件物品放入容量为 j 的背包中”，价值为 `dp[i−1][j]` ；

如果放入第 i 件物品，那么问题就转化为“前 i−1 件物品放入剩下的容量为 `j−w[i]`的背包中”，其中 w[i] 是第 i 件物品所占用的背包的容量，此时能获得的最大价值就是 `dp[i−1][j−w[i]]`，再加上通过放入第 i 件物品获得的价值 v[i]。

该算法的时间和空间复杂度均为O(m*n)，其中时间复杂度已经不能再优化了，但空间复杂度却可以优化到 O(n)，后面会介绍0-1背包的空间优化算法。

由上述状态转移方程可知，`dp[i][j]`的值只与`dp[i-1][0,...,j-1]`有关，所以我们可以采用多重循环完成动态规划算法，伪代码如下：

```java
// 公众号沙糖橘：shatangju8801，请留意QQ交流群：798470346，沙糖橘社区倾心锻造~_~
// 01背包问题伪代码（二维数组未优化版）
dp[0][0,...,W] = 0
for i = 1,...,N // N个物品可选 N=w.length
    for j = 1,...,w // 遍历每一个背包可用容量下的最优解
    //for j = W,...,min(w) // 注意，此处j循环的右边界值为数组w中的最小元素的值，或者直接取值为1
                         // 另外，j的循环可以从max-->min   也可以从 min-->max 二者等价
        // 装不下
        if w[i]>j
            dp[i][j] = dp[i−1][j]
        else // 装的下
            dp[i][j] = max(dp[i−1][j], dp[i−1][j−w[i]]+v[i])

```

编程实现代码如下：

```java
	/**
	 * 公众号沙糖橘：shatangju8801，请留意QQ交流群：798470346，沙糖橘社区倾心锻造~_~
	 * 公众号沙糖橘：shatangju8801，请留意QQ交流群：798470346，沙糖橘社区倾心锻造~_~
     * 动态规划算法——空间优化算法
     *
     * @param m 总容量
     * @param w 物品体积数组
     * @param v 物品价值数组
     * @return 动态规划的结果数组
     */
private static int[][] test(int m, int[] w, int[] v) {
        // 动态规划数组，为了方便动态规划条件从1开始，数组维数+1
        int[][] dp = new int[w.length][m + 1];        
        // 初始化
        for (int i = 0; i < w.length; i++) {
            for (int j = 1; j < m + 1; j++) {
                dp[i][j] = 0;
            }
        }
        // 获取放入背包物品中，体积最小的物品的体积值        
        // 填充动态规划数组
        // 变量物品个数，尝试每个物品进行规划处理
        for (int i = 1; i < w.length; i++) {
            // 背包容量的遍历规划
            for (int j = 1; j < m + 1; j++) { // 下标可以从1--m+1 进行遍历，可以正向枚举遍历，也可以逆向枚举遍历
                //当背包里的物品为i件重量为j时，如果第i件的重量为weight【i-1】小于重量j时，dp【i】【j】有下列两种情况
                //(1)物品i不放入背包，所以dp[i][j]为dp[i-1][j]的值
                //(2)物品i放入背包，则背包剩余重量为j-weight[i-1]，所以从dp[i][j]为dp[i-1][j-weight[i-1]]的值加上当前物品i的值
                if (w[i] > j) { // (1)
                    dp[i][j] = dp[i - 1][j];
                } else { // (2)
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w[i]] + v[i]);
                }
            }
        }
        return dp;
    }
```

**空间和价值数组w和v的预处理：**

注意在算法理论描述中，本算法为了i的下标都从1开始处理物品，需要对数组w和v进行预处理，预处理方法如下，在调用本方法之前需要自行对数组w和v进行预处理：

```java
// 公众号沙糖橘：shatangju8801，请留意QQ交流群：798470346，沙糖橘社区倾心锻造~_~
// 改造w和v数组，是i和数组小标从1开始,下标为0的元素填充0
        // 例如：[2,3,4] ---> [0,2,3,4]
        int[] w = new int[w0.length+1];
        System.arraycopy(w0,0, w, 1, w0.length);
        int[] v = new int[v0.length+1];
        System.arraycopy(v0,0, v, 1, v0.length);
```

对于第二层for循环遍历下标的问题说明：

```java
// 公众号沙糖橘：shatangju8801，请留意QQ交流群：798470346，沙糖橘社区倾心锻造~_~
// 获取放入背包物品中，体积最小的物品的体积值
        int wMin = getMinVal(w);
        // 填充动态规划数组
        // 变量物品个数，尝试每个物品进行规划处理
        for (int i = 1; i < w.length; i++) {
            // 背包容量的遍历规划
            for (int j = m; j > wMin; j--) { // 注意此处，见代码后面的说明：【循环下标说明】
                //当背包里的物品为i件重量为j时，如果第i件的重量为weight【i-1】小于重量j时，dp【i】【j】有下列两种情况
                //(1)物品i不放入背包，所以dp[i][j]为dp[i-1][j]的值
                //(2)物品i放入背包，则背包剩余重量为j-weight[i-1]，所以从dp[i][j]为dp[i-1][j-weight[i-1]]的值加上当前物品i的值
                if (j < w[i]) { // (1)
                    dp[i][j] = dp[i - 1][j];
                } else { //(2)
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w[i]] + v[i]);
                }
            }
        }

```

**【循环下标说明】**

此处的下标可以从最大的容量m开始倒序遍历，但是遍历终止边界值不是 w[i]，而是数组w中的最小值，或者直接设置终止边界值为1；具体为什么请课下自行思考说明。

在使用二维数组dp进行0-1背包算法实现时，背包的可用容量 j 变量在for循环中可以正序遍历也可以逆序遍历，计算结果是一样的。

**0-1背包空间优化算法**

对前面的采用二维状态转换数组的算法进行如下分析：

采用二维状态转换数组的算法中的主循环 i=1...N，每次算出来二维数组 `dp[i][0...W]`的所有值。那么，如果只用一个数组`dp[0...W]`，能不能保证第 i 次循环结束后`dp[j]`中表示的就是之前定义的状态`dp[i][j]`呢？`dp[i][j]`是由`dp[i-1][j]`和`dp[i−1][j−w[i]]`两个子问题域递推而来，能否保证在推导`dp[i][j]`时（也即在第 i 次主循环中推导`dp[j]`时）能够得到`dp[i-1][j]`和`dp[i−1][j−w[i]]`的值呢？事实上，这要求在每次主循环中以`j=W...0`的顺序推`dp[j]`，这样才能保证推导`dp[j]`时`dp[j−w[i]]`保存的是状态`dp[i-1][j-w[i]]`的值。

经过前面分析发现，由上述状态转移方程可知，`dp[i][j]`的值只与`dp[i-1][0,...,j-1]`有关，所以可以采用动态规划常用的方法（滚动数组）对空间进行优化（即去掉dp的第一维）。需要注意的是，为了防止上一层循环的`dp[0,...,j-1]`被覆盖，循环的时候 j 只能**逆向枚举**（空间优化前没有这个限制，可以正向也可以逆向），伪代码为：

```java
// 公众号沙糖橘：shatangju8801，请留意QQ交流群：798470346，沙糖橘社区倾心锻造~_~
// 01背包问题伪代码(空间优化版)
dp[0,...,W] = 0
for i = 1,...,N // N个物品可选 N=w.length
    for j = W,...,w[i] // 必须逆向枚举，否则无法实现最优解；逆向枚举之前的值相当于是上一次物品选取的最优解，
                       //在此基础上进行本次的最优解的选取；注意，必须逆向遍历。
        dp[j] = max(dp[j], dp[j−w[i]]+v[i]) // dp[j] 等价于未放入物品i时的最大值；dp[j−w[i]]+v[i]等价于放入物品i时的最大值
```

结合上面的伪代码可以分析空间优化算法的缘由：

空间优化算法中的`dp[j]=max(dp[j],dp[j−w[i]])`恰好就等价于转移方程`dp[i][j]=max(dp[i−1][j],dp[i−1][j−w[i]])`，因为现在的`dp[j−w[i]]`就相当于原来的`dp[i−1][j−w[i]]`。如果将W的循环顺序从上面的逆序改成顺序的话，那么则成了`dp[i][j]`由`dp[i][j−w[i]]`推知，与本题意不符，但它却是另一个重要的背包问题（完全背包）最简捷的解决方案，所以熟练掌握一维数组解0-1背包问题是非常必要和重要的。

空间优化算法的时间复杂度为O(NW), 空间复杂度为O(W)，相对于二维数组的算法，时间负责度没有在优化的余地，对空间复杂度进行了优化。由于W的值是W的位数的幂，所以这个时间复杂度是伪多项式时间。

动态规划的核心思想**避免重复计算**在0-1背包问题中体现得淋漓尽致。第 i 件物品装入或者不装入而获得的最大价值完全可以由前面 i-1 件物品的最大价值决定，这样可以保证每次获取的都是最优解。

具体实现代码如下：

```java
	/**
	 * 公众号沙糖橘：shatangju8801，请留意QQ交流群：798470346，沙糖橘社区倾心锻造~_~
     * 动态规划算法——空间优化算法
     *
     * @param m 总容量
     * @param w 物品体积数组
     * @param v 物品价值数组
     * @return 动态规划的结果数组
     */
    private static int[] test(int m, int[] w, int[] v) {
        // 动态规划数组，为了方便动态规划条件从1开始，数组维数+1
        int[] dp = new int[m + 1];
        // 初始化
        for (int i = 0; i < m + 1; i++) {
            dp[i] = 0;
        }
        // 获取放入背包物品中，体积最小的物品的体积值        
        // 填充动态规划数组
        // 变量物品个数，尝试每个物品进行规划处理
        for (int i = 1; i < w.length; i++) {
            // 背包容量的遍历规划
            for (int j = m; j >= w[i]; j--) { // 必须逆向枚举遍历！！！  注意此处，见代码后面的说明：【循环下标说明】
                //当背包里的物品为i件重量为j时，如果第i件的重量为weight【i-1】小于重量j时，dp【i】【j】有下列两种情况
                //(1)物品i不放入背包，所以dp[i][j]为dp[i-1][j]的值
                //(2)物品i放入背包，则背包剩余重量为j-weight[i-1]，所以从dp[i][j]为dp[i-1][j-weight[i-1]]的值加上当前物品i的值
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }            
        }
        return dp;
    }

```

**【循环下标说明】**

此处的下标必须最大的容量m开始倒序遍历，且遍历终止边界值是 w[i]；具体为什么请课下自行思考说明。

另外注意，此算法依然需要对数组w和v进行预处理，预处理方式见上文：**空间和价值数组w和v的预处理**。

补充：此处下标只能逆向枚举遍历，不能正向枚举遍历。

因为：逆向枚举遍历是0-1背包动态规划算法；

​           正向枚举遍历是完全背包动态规划算法；

#### 2.3 循环因子的优化【数据量较大时优势】

上面的空间优化算法代码中的内层循环 ：

```java
......
// 公众号沙糖橘：shatangju8801，请留意QQ交流群：798470346，沙糖橘社区倾心锻造~_~
// 变量物品个数，尝试每个物品进行规划处理
        for (int i = 1; i < w.length; i++) {
            // 背包容量的遍历规划
            // 此处循环因子 j 的边界值可以进行优化
            for (int j = m; j >= w[i]; j--) { // 必须逆向枚举遍历！！！  注意此处，见代码后面的说明：【循环下标说明】
                //当背包里的物品为i件重量为j时，如果第i件的重量为weight【i-1】小于重量j时，dp【i】【j】有下列两种情况
                //(1)物品i不放入背包，所以dp[i][j]为dp[i-1][j]的值
                //(2)物品i放入背包，则背包剩余重量为j-weight[i-1]，所以从dp[i][j]为dp[i-1][j-weight[i-1]]的值加上当前物品i的值
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }            
        }
 ......       
```

对于`for (int j = m; j >= w[i]; j--)`来说，可以将这个循环因子的下限进行改进。由于只需要求解最后`dp[j]`的值，而求解`dp[j]`的值需要倒推前一个物品，其实只要知道 `dp[j−w[n]]`即可。以此类推，对于空用容量为 j 的背包，其实只需要求得 `dp[j−sum(w[i...n])]`即可，上面的空间优化算法的代码可以优化为：

```java
......
// 公众号沙糖橘：shatangju8801，请留意QQ交流群：798470346，沙糖橘社区倾心锻造~_~
// 变量物品个数，尝试每个物品进行规划处理
        for (int i = 1; i < w.length; i++) {
            // 背包容量的遍历规划
            // 此处循环因子 j 的边界值可以进行优化
            // 循环因子 j 下限值
            int bound = max(w[i],m - sum{w[i + 1]...w[n]}) // m 是背包的总容量
            // 上式等价于
            int bound = max(w[i], m - (s[n] - s[i])); // m 是背包的总容量，s[n] - s[i] 表示 w 数组中 i+1 到n的和
            for (int j = m; j >= w[i]; j--) { // 必须逆向枚举遍历！！！  注意此处，见代码后面的说明：【循环下标说明】
                //当背包里的物品为i件重量为j时，如果第i件的重量为weight【i-1】小于重量j时，dp【i】【j】有下列两种情况
                //(1)物品i不放入背包，所以dp[i][j]为dp[i-1][j]的值
                //(2)物品i放入背包，则背包剩余重量为j-weight[i-1]，所以从dp[i][j]为dp[i-1][j-weight[i-1]]的值加上当前物品i的值
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }            
        }
 ......       
```

对于求 sum 可以用前缀和，这对于 V 比较大时是有用的。这种优化处理对于处理大数据量时，可以提升算法的性能。

**对于0-1背包问题的总结：**

0-1背包问题是最基本的背包问题，它包含了背包问题中如何设计状态转换方程的最基本思想，其他的类型的背包问题往往也可以转换成0-1背包问题求解。仔细体会0-1背包问题的基本思路，状态转移方程的意义，以及最后怎样优化的空间复杂度。这是学习所有背包问题的基础和出发点。


---

后续继续更新中。。。，敬请期待...



package cn.info.libre.service.impl;

import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Administrator
 * @date
 * @version 1.0
 */
public class Test {
    /*public static void main(String[] args) {
        System.out.println("请使用空格输入多个参数：例如：4 a ab aab aaabb");
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < t; i++) {
            String s = sc.next();
            if (s != null && !"".equals(s)) {
                list.add(s);
            } else {
                break;
            }
        }
        sc.close();
        System.out.println(list);
        for (String str : list) {
            int need = 0;
            int len = str.length();
            if (len == 1) {
                need = 0;
            } else {
                for (int i = 0; i < (len - 1); i++) {
                    if (str.charAt(i) == str.charAt(i + 1)) {
                        need++;
                    }
                }
            }
            int finalN = len + need;
            System.out.println(finalN);
        }
    }*/
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        long arr[] = new long[n + 1];
        for (int i = 0; i < n; ++i) {
            arr[i] = sc.nextLong();
        }
        long dp[][] = new long[n + 1][k + 1];
        for (int i = 0; i <= n; ++i) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j < k; ++j) {
                if (dp[i - 1][j] != -1) {
                    dp[i][j] = dp[i - 1][j];
                }
                int tmp = (int) (j - arr[i - 1] % k + k) % k;
                if (dp[i - 1][tmp] != -1) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][tmp] + arr[i - 1]);
                }
            }
        }
        System.out.println(dp[n][0] == 0 ? -1 : dp[n][0]);
    }

}

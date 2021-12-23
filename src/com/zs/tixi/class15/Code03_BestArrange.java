package com.zs.tixi.class15;

import java.util.Arrays;
import java.util.Comparator;

/*
 * 3. 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
 *      给你每一个项目开始的时间和结束的时间。
 *      你来安排宣讲的日程，要求会议室进行的宣讲场次最多。
 *      返回最多的宣讲场次。
 */
public class Code03_BestArrange {
    public static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // 暴力！所有情况都尝试！
    public static int bestArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs, 0);
    }

    /**
     * 遍历场次数组：
     *      如果当前场次开始时间小于timeLine,跳过
     *      进行当前宣讲：
     *          收集其余宣讲数组，递归获取后续最大场次后加1.
     * 求循环中的最大值。
     * @param programs 剩余需要宣讲的场次
     * @param timeLine 本次可以开始的时间。
     * @return 最多场次
     */
    public static int process(Program[] programs, int timeLine) {
        int max = 0;
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start<timeLine) continue;
            Program[] next = copyButExcept(programs, i);
            max = Math.max(max, process(next, programs[i].end)+1);
        }
        return max;
    }

    public static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }

    // 会议的开始时间和结束时间，都是数值，不会 < 0

    /**
     * 早结束的排在前面。
     * @param programs
     * @return
     */
    public static int bestArrange2(Program[] programs) {
        Arrays.sort(programs, new ProgramComparator());
        int timeLine = 0;
        int result = 0;
        // 依次遍历每一个会议，结束时间早的会议先遍历
        for (int i = 0; i < programs.length; i++) {
            if (timeLine <= programs[i].start) {
                result++;
                timeLine = programs[i].end;
            }
        }
        return result;
    }

    public static class ProgramComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }

    }

    // for test
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1);
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 1000000;
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (bestArrange1(programs) != bestArrange2(programs)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}

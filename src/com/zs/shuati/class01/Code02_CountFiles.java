package com.zs.shuati.class01;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 给定一个文件目录的路径，写一个函数统计这个目录下所有的文件数量并返回，隐藏文件也算，但是文件夹不算
 */
public class Code02_CountFiles {
    /**
     * 如果root既不是文件，又不是目录，返回0.
     * 如果root是文件，返回1.
     * 创建一个队列。root加入队列。
     * 循环：队列不为空
     *      取出队首文件夹poll。
     *      循环poll下的所有文件：cur
     *          如果cur为文件，结果加1
     *          否则，cur加入队列。
     * 返回结果
     * @param folderPath
     * @return
     */
    public static int getFileNumber(String folderPath) {
        File root = new File(folderPath);
        if(!root.isDirectory() && !root.isFile()) return 0;
        if(root.isFile()) return 1;
        int ans = 0;
        Queue<File> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            File poll = queue.poll();
            File[] files = poll.listFiles();
            if(files==null) continue;
            for (File cur : files) {
                if(cur.isFile()){
                    ans++;
                }
                if (cur.isDirectory()){
                    queue.add(cur);
                }
            }
        }
        return ans;
    }

    /**
     * 如果root不是文件也不是目录，返回0.
     * 如果时文件返回1.
     *
     * @param folderPath
     * @return
     */
    public static int getFileNumber2(String folderPath) {
        File root = new File(folderPath);
        if (!root.isDirectory() && !root.isFile()) {
            return 0;
        }
        if (root.isFile()) {
            return 1;
        }
        Stack<File> stack = new Stack<>();
        stack.add(root);
        int files = 0;
        while (!stack.isEmpty()) {
            File folder = stack.pop();
            File[] nexts = folder.listFiles();
            if(nexts==null) continue;
            for (File next : nexts) {
                if (next.isFile()) {
                    files++;
                }
                if (next.isDirectory()) {
                    stack.push(next);
                }
            }
        }
        return files;
    }


    public static void main(String[] args) {
        // 你可以自己更改目录
        String path = "D:/";
        System.out.println(getFileNumber(path));
        System.out.println(getFileNumber2(path));
    }
}

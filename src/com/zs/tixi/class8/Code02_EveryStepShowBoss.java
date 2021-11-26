package com.zs.tixi.class8;

import java.util.*;
import java.util.stream.Collectors;

/*
 * 3. 改写堆练习题：给消费最高的客户发奖。要求每次消费发生后,实时展示获奖人和消费信息。
 *      给定一个整型数组int[] arr，和一个布尔型数组boolean[] op。两个数组一定等长，arr[i]表示客户编号, op[i]表示客户发生了一次操作
 *      arr = [3, 3, 1, 2, 1, 2, 5, ...]
 *      op = [T, T, T, T, F, T, F, ...]
 *      依次表示：3用户购买了一件商品，3用户购买了一件商品，1用户购买了一件商品，2用户购买了一件商品， 1用户退货了一件商品， 2用户购买了一件商品， 5用户退货了一件商品...
 *      得奖的规则：
 *          1) 用户购买0件商品时，发生的退货事件无效。
 *          2) 购买事件发生一次，用户购买商品数加1，反之，减一。
 *          3) 获奖池大小为K，如果消费人数不够K，就按不够的情况输出。
 *          4) 得奖系统分为得奖区和候选区，用户只要购买数>0，必在这两个区域的一个。
 *          5) 购买数最大的前K名用户进入得奖区。如果得奖区没有满，新购买用户直接进入得奖区
 *          6) 得奖区已满，购买数不足以进入得奖区的用户，进入候选区
 *          7) 候选区购买数最多的用户，有资格评选是否进入得奖区:
 *              该用户大于得奖区购买最少的用户，替换该用户进入得奖区。
 *              得奖区购买最少的用户有多个，替换最早进入得奖区的用户
 *              候选区购买最多的用户有多个，机会优先给最早进入候选区的用户
 *          8) 用户进入候选区或得奖区时，需要记录当前购买时间，只要一直保持在当前区域，购买时间不变。否则购买时间删除。
 *          9) 如果用户购买数变为0，将用户消费记录删除（不论在候选区还是得奖区）。下次用户再次购买，视为新用户.
 *      要求，根据系统给定的arr数组，op数组和得奖区大小K，遍历arr，每i位置输出当前得奖名单，要求每次输出时间复杂度O(1)（实际需求是要求实时展示得奖区的情况）
 */
public class Code02_EveryStepShowBoss {
    /**
     * 创建候选区的加强堆candHeap。比较器逻辑：消费最高在前，消费最高的取最先消费的在前。
     * 创建得奖区的加强堆winHeap。比较逻辑：消费最低的在前，消费最低的取最先消费的在前。
     * 创建map<id, Customer>记录对应id的客户消费情况。
     * 创建List<List<Customer>> everStepWinList作为每一步得奖名单列表的集合
     * 循环事件数组arr：
     *      查询map中是否存在当前客户，如果不存在：
     *          消费为退货：无效消费，不做任何操作。
     *          消费为买货：将当前客户初始化后放入候选区，在map中登记客户
     *      如果存在，map中取出信息更新客户的消费情况，
     *          如果更新后消费为0，将客户从map中删除，分别判断得奖区和候选区中是否存在该客户并删除。
     *          如果更新后消费不为0，分别判断候选区和得奖区是否包含客户并更新加强堆。
     *      如果候选区为空：【直接返回得奖区进入下次循环】。
     *      如果得奖区size小于K，从候选区弹出客户直到得奖区size等于K。（得奖区客户被删除情况）
     *      如果候选区堆顶消费金额大于得奖区的堆顶，互相弹出一个客户到对方堆。（候选区消费增加，或得奖区消费减少导致）
     *      返回得奖区进入下次循环, 从winHeap获取所有元素组成新列表加入everStepWinList。（此处应该不能直接存入对象，而是重新创建Customer，否则历史得奖信息会发生变化）
     */
    public static List<List<Integer>> everyStepShowBoss(int[] arr, boolean[] op, int K){
        HeapGreater<Customer> candHeap = new HeapGreater<>(new CandComparator());
        HeapGreater<Customer> winHeap = new HeapGreater<>(new WinComparator());
        Map<Integer, Customer> customerMap = new HashMap<>();
        List<List<Integer>> winListList = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean isBuy = op[i];

            Customer customer = customerMap.get(id);
            if (customer == null){
                if (isBuy){
                    customer = new Customer(id, 1, i);
                    candHeap.add(customer);
                    customerMap.put(id, customer);
                }
            } else {
                customer.count = isBuy?customer.count+1:customer.count-1;
                if (customer.count == 0 ){
                    customerMap.remove(id);
                    if (candHeap.contains(customer)) {
                        candHeap.remove(customer);
                    } else if (winHeap.contains(customer)){
                        winHeap.remove(customer);
                    } else
                    {
                        throw new RuntimeException("系统异常，用户在两个区域都找不到...");
                    }
                } else {
                    if(candHeap.contains(customer)){
                        candHeap.resign(customer);
                    } else {
                        winHeap.resign(customer);
                    }
                }
            }

            if (candHeap.isEmpty()) {
                winListList.add(getHeapElements(winHeap));
                continue;
            }

            if (winHeap.size()<K){
                while (!candHeap.isEmpty()){
                    if (winHeap.size() == K){
                        break;
                    }
                    Customer cand = candHeap.poll();
                    cand.time = id;
                    winHeap.add(cand);
                }
            }

            if(!candHeap.isEmpty()){
                Customer candPeek = candHeap.peek();
                Customer winPeek = winHeap.peek();
                if (winPeek.count<candPeek.count){
                    winPeek.time = id;
                    candPeek.time = id;
                    winHeap.poll();
                    candHeap.poll();
                    winHeap.add(candPeek);
                    candHeap.add(winPeek);
                }
            }

            winListList.add(getHeapElements(winHeap));
        }

        return winListList;
    }

    private static List<Integer> getHeapElements(HeapGreater<Customer> heap){
        return heap.getAllElements().stream().map(i->i.id).collect(Collectors.toList());
    }

    /**
     * 消费者
     */
    public static class Customer{
        public int id; // 消费者编号
        public int count; // 消费数量
        public int time; // 消费时点

        public Customer(int id, int count, int time){
            this.id = id;
            this.count = count;
            this.time = time;
        }
    }

    /**
     * 候选区比较器，
     * 消费最高在前，消费最高的取最先消费的在前。
     */
    public static class CandComparator implements Comparator<Customer>{

        @Override
        public int compare(Customer o1, Customer o2) {
            return o2.count == o1.count ? o1.time-o2.time : o2.count-o1.count;
        }
    }

    /**
     * 得奖区比较器
     * 消费最低的在前，消费最低的取最先消费的在前。
     */
    public static class WinComparator implements Comparator<Customer>{

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.count == o2.count ? o1.time-o2.time : o1.count-o2.count;
        }
    }


    // ===================拷贝的代码
    // 干完所有的事，模拟，不优化
    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        HashMap<Integer, Customer> map = new HashMap<>();
        ArrayList<Customer> cands = new ArrayList<>();
        ArrayList<Customer> daddy = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            if (!buyOrRefund && !map.containsKey(id)) {
                ans.add(getCurAns(daddy));
                continue;
            }
            // 没有发生：用户购买数为0并且又退货了
            // 用户之前购买数是0，此时买货事件
            // 用户之前购买数>0， 此时买货
            // 用户之前购买数>0, 此时退货
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }
            // 买、卖
            Customer c = map.get(id);
            if (buyOrRefund) {
                c.count++;
            } else {
                c.count--;
            }
            if (c.count == 0) {
                map.remove(id);
            }
            // c
            // 下面做
            if (!cands.contains(c) && !daddy.contains(c)) {
                if (daddy.size() < k) {
                    c.time = i;
                    daddy.add(c);
                } else {
                    c.time = i;
                    cands.add(c);
                }
            }
            cleanZeroBuy(cands);
            cleanZeroBuy(daddy);
            cands.sort(new CandComparator());
            daddy.sort(new WinComparator());
            move(cands, daddy, k, i);
            ans.add(getCurAns(daddy));
        }
        return ans;
    }

    public static List<Integer> getCurAns(ArrayList<Customer> daddy) {
        List<Integer> ans = new ArrayList<>();
        for (Customer c : daddy) {
            ans.add(c.id);
        }
        return ans;
    }

    public static void cleanZeroBuy(ArrayList<Customer> arr) {
        List<Customer> noZero = new ArrayList<Customer>();
        for (Customer c : arr) {
            if (c.count != 0) {
                noZero.add(c);
            }
        }
        arr.clear();
        for (Customer c : noZero) {
            arr.add(c);
        }
    }

    public static void move(ArrayList<Customer> cands, ArrayList<Customer> daddy, int k, int time) {
        if (cands.isEmpty()) {
            return;
        }
        // 候选区不为空
        if (daddy.size() < k) {
            Customer c = cands.get(0);
            c.time = time;
            daddy.add(c);
            cands.remove(0);
        } else { // 等奖区满了，候选区有东西
            if (cands.get(0).count > daddy.get(0).count) {
                Customer oldDaddy = daddy.get(0);
                daddy.remove(0);
                Customer newDaddy = cands.get(0);
                cands.remove(0);
                newDaddy.time = time;
                oldDaddy.time = time;
                daddy.add(newDaddy);
                cands.add(oldDaddy);
            }
        }
    }

    public static void main(String[] args) {
        int maxValue = 10;
        int maxLen = 100;
        int maxK = 6;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Data testData = randomData(maxValue, maxLen);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = testData.arr;
            boolean[] op = testData.op;
            List<List<Integer>> ans1 = everyStepShowBoss(arr, op, k);
            List<List<Integer>> ans2 = compare(arr, op, k);
            if (!sameAnswer(ans1, ans2)) {
                for (int j = 0; j < arr.length; j++) {
                    System.out.println(arr[j] + " , " + op[j]);
                }
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

    // 为了测试
    public static class Data {
        public int[] arr;
        public boolean[] op;

        public Data(int[] a, boolean[] o) {
            arr = a;
            op = o;
        }
    }

    // 为了测试
    public static Data randomData(int maxValue, int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[len];
        boolean[] op = new boolean[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
            op[i] = Math.random() < 0.5 ? true : false;
        }
        return new Data(arr, op);
    }

    // 为了测试
    public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        if (ans1.size() != ans2.size()) {
            return false;
        }
        for (int i = 0; i < ans1.size(); i++) {
            List<Integer> cur1 = ans1.get(i);
            List<Integer> cur2 = ans2.get(i);
            if (cur1.size() != cur2.size()) {
                return false;
            }
            cur1.sort((a, b) -> a - b);
            cur2.sort((a, b) -> a - b);
            for (int j = 0; j < cur1.size(); j++) {
                if (!cur1.get(j).equals(cur2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }


}

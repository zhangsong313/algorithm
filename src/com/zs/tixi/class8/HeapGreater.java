package com.zs.tixi.class8;

import java.util.*;

/*
 * 2. 改写堆:系统提供的堆无法试下以下两个功能：
 *      1) 元素更新：任意位置元素排序大小发生变化，重新调整堆。要求O(logN)的时间复杂度
 *      2) 删除任意元素,删除堆内任意元素,要求O(logN)时间内完成。
 *      说明：核心是无法快速找到指定元素的位置，可以使用建立反向索引表解决
 */
public class HeapGreater<T> {
    private int size; // 堆大小
    private List<T> heap; // 堆数组
    private Map<T, Integer> map; // 反向索引表，找到指定对象所在位置。
    private Comparator<T> comp; // 对象的比较器。

    // 构造方法
    // 说明：小根堆。
    public HeapGreater(Comparator<T> comp){
        this.size = 0;
        this.heap = new ArrayList<T>();
        this.map = new HashMap<>();
        this.comp = comp;
    }

    /**
     * 堆大小
     * @return
     */
    public int size(){
        return this.size;
    }
    /**
     * 堆是否为空
     */
    public boolean isEmpty(){
        return size() == 0;
    }
    /**
     * 堆添加一个元素
     */
    public void add(T data){
        // 当前元素加入heap列表。
        heap.add(data);
        // size加一。
        // 将当前元素登记到map中。
        map.put(data, size);
        // 对size位置执行heapInsert。
        heapInsert(size++);
    }

    /**
     * 对index位置执行heapInsert
     * 找到父节点位置，
     * 比较当前节点与父节点大小，如果小，交换当前节点与父节点。直到不满足判断条件
     * @param index
     */
    private void heapInsert(int index) {
        while (comp.compare(heap.get(index), heap.get((index-1)/2)) < 0){
            swap(index, (index-1)/2);
            index = (index-1)/2;
        }
    }

    /**
     * 交换两个指定位置的对象。
     * @param index1
     * @param index2
     */
    private void swap(int index1, int index2) {
        T t1 = heap.get(index1);
        T t2 = heap.get(index2);
        heap.set(index1, t2);
        heap.set(index2, t1);
        map.put(t2, index1);
        map.put(t1, index2);
    }

    /**
     * 堆删除并返回堆顶元素
     *
     * 记录堆顶元素
     * 交换堆顶元素与堆底元素
     * 将map中的堆顶元素记录删除，并修改堆底元素的位置为0.
     * 堆大小减一。
     * 对堆顶执行heapify。
     * 返回记录的堆顶元素。
     */
    public T poll(){
        if(isEmpty()) throw new RuntimeException("heap is empty!");

        T ans = heap.get(0);
        swap(0, --size);
        heap.remove(size);
        map.remove(ans);
        heapify(0);
        return ans;
    }

    /**
     * 对指定位置元素执行heapify操作。
     * 找到当前元素左子节点。
     * 循环：左子节点不超出堆范围
     *      从两个子节点中选出一个较小节点。
     *      当前节点小于等于较小子节点：break
     *      交换当前节点与较小子节点。
     *      更新当前节点变量为较小子节点。
     *      更新左子节点变量。
     * @param index
     */
    private void heapify(int index) {
        int left = index*2+1;
        while(left < size ){
            int lessest = left+1<size && comp.compare(heap.get(left+1), heap.get(left)) < 0?left+1:left;
            if(comp.compare(heap.get(index), heap.get(lessest)) <=0) break;
            swap(index, lessest);
            index = lessest;
            left = index*2+1;
        }
    }
    /**
     * 返回堆顶元素
     */
    public T peek(){
        if(isEmpty()) throw new RuntimeException("heap is empty!");
        return heap.get(0);
    }

    /**
     * 由于指定元素发生了变化，需要重新调整指定元素的位置。
     *
     * map中找到当前元素位置
     * 对当前元素位置执行heapInsert
     * 对当前元素位置执行heapify
     */
    public void resign(T data){
        Integer index = map.get(data);
        if (index == null) throw new RuntimeException("this element is not exist");

        heapify(index);
        heapInsert(index);
    }

    /**
     * 从堆中删除指定元素
     *
     * map中找到指定元素位置
     * 交换最后一个元素与当前元素，
     * size--, heap删除堆底数据,map中删除指定元素
     * 对当前元素位置执行heapify
     */
    public void remove(T obj){
        Integer index = map.get(obj);
        swap(index, --size);
        heap.remove(size);
        map.remove(obj);
        if (index != size){
            heapify(index);
            heapInsert(index);
        }
    }

    /**
     * 返回堆中的所有元素
     */
    public List<T> getAllElements(){
        List<T> list = new ArrayList<>();
        heap.forEach(i->list.add(i));
        return list;
    }

    /**
     * 返回是否包含某元素
     */
    public boolean contains(T obj){
        return heap.contains(obj);
    }

    /**
     * 打印当前map的情况
     */
    public void printMap(){
        System.out.println(map);
    }

    public static void main(String[] args) {
        HeapGreater<Person> heap = new HeapGreater<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.age== o2.age?o1.time-o2.time:o1.age-o2.age;
            }
        });
        for (int i = 0; i < 20; i++) {
            heap.add(new Person((int)(Math.random()*50)));
            heap.printMap();
            System.out.println(heap.getAllElements());

        }
        System.out.println("=22===");
        while (!heap.isEmpty()){
            System.out.println();
            heap.printMap();
            int removeIndex = 0;
//            int removeIndex = (int)(Math.random()*heap.size);
            System.out.println("remove : "+heap.getAllElements().get(removeIndex));
            heap.remove(heap.getAllElements().get(removeIndex));
            System.out.println(heap.getAllElements());
        }
    }

    public static class Person{
        public int age;
        public int time;
        public Person(int age){
            this.age = age;
        }

        @Override
        public String toString() {
            return ""+age;
        }
    }
}

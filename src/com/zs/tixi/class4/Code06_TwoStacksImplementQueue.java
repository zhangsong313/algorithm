package com.zs.tixi.class4;

import java.util.Stack;

/**
 * 栈实现队列.
 */
public class Code06_TwoStacksImplementQueue {
    class TwoStacksImplementQueue{
        Stack stackPush; //用来从队尾添加数据
        Stack stackPop; //用来从开始取数据.

        public TwoStacksImplementQueue(){
            stackPush = new Stack();
            stackPop = new Stack();
        }

        /**
         * 每次添加或取出时,如果stackPop为空,将stackPush数据放入stackPop中.
         */
        private void pushToPop(){
            if (stackPop.size() ==0){
                for (int i = 0; i < stackPush.size(); i++) {
                    stackPop.push(stackPush.pop());
                }
            }
        }

        public void add(Object data){
            pushToPop();
            stackPush.push(data);
        }

        public Object poll(){
            pushToPop();
            return stackPop.pop();
        }
    }

}

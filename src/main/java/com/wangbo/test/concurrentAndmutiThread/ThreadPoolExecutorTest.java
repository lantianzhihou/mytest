package com.wangbo.test.concurrentAndmutiThread;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * 位运算符主要针对二进制,包括了与（&）、非（~）、或（|）、异或（^）,针对两个二进制数的位进行逻辑运算：
 *      1.与运算符“&”的两个操作数对应的位数都为1，结果为1，否则结果为0；
 *      2.或运算符“|”的两个操作数对应的位数只要有一个为1，结果为1，否则就为0；
 *      3.非运算符“~”将操作数的位数由0改为1，1改为0；
 *      4.异或运算符“^”的两个操作数对应的位数，相同则结果为0，不同则结果为1。
 * 任意数与-1进行&运算，结果为本身；任意数与0进行&运算，结果为0;
 * 任意数与0进行|运算，结果为本身；任意数与-1进行|运算，结果为-1;
 * 任意数与-1进行^运算，结果等于本身的非运算值;
 * 
 * 计算机中存储的都是数的补码,所有的位运算符都是针对补码而言的,
 * 正数的原码、反码、补码都是相同的；而负数的原码、反码、补码是不一样的，补码=原码取反+1（符号位不变）。
 * 注意：x的非运算值加上1再符号取反等于原值，即~x = -x - 1  导出： x = -(~x) - 1(适用于通过负值的补码求原值)
 * 例如 127的非运算值为-128，-34的非运算值为33
 * 
 * >>>为逻辑移位符，向右移n位，高位补0；
 * >> 算数移位符，也是向右移n位，不同的是：正数高位补0，负数高位补1；
 * << 移位符，向左移n位，低位补0
 * 
 * 利用“移位”操作实现或者调用API函数 将十进制数转换成二进制数
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2019年3月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ThreadPoolExecutorTest {

    // runState is stored in the high-order bits
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;
    private static final int RUNNING = -1 << COUNT_BITS;
//    private static final int SHUTDOWN   =  0 << COUNT_BITS;
//    private static final int STOP       =  1 << COUNT_BITS;
//    private static final int TIDYING    =  2 << COUNT_BITS;
//    private static final int TERMINATED =  3 << COUNT_BITS;

    /**
     *  int c = oldState | oldWorkerCount;
     *  ~CAPACITY = -CAPACITY - 1 = -1 << COUNT_BITS = RUNNING;
     *  
     *  所以c & ~CAPACITY <==> oldState & RUNNING | oldWorkerCount & RUNNING 
     *  <==> 其中oldState & RUNNING始终为oldState, 而oldWorkerCount & RUNNING
     *  在oldWorkerCount为正数且<CAPACITY时，oldWorkerCount & CAPACITY的值始终为0
     *  <==>  c & ~CAPACITY = oldState
     */
    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    /**
     *  int c = oldState | oldWorkerCount;
     *  
     *  c & CAPACITY <==> oldState & CAPACITY | oldWorkerCount & CAPACITY 
     *  <==> 其中oldState & CAPACITY始终为0，而oldWorkerCount & CAPACITY
     *  在oldWorkerCount为正数且<CAPACITY时，oldWorkerCount & CAPACITY的值为oldWorkerCount
     *  <==>  oldWorkerCount
     */
    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    
    @Test
    public void testWorkerCount() {
        System.out.println(~2567);
        System.out.println((-1) << COUNT_BITS);
        System.out.println(-(1 << COUNT_BITS));
        int c = ctl.get();
        System.out.println(workerCountOf(c));
        System.out.println(runStateOf(c));
        System.out.println(runStateOf(c + 1));
    }
    
    @Test
    public void testRunState() {
        ctl.addAndGet(27);
        int i = ctl.get(); // ctlOf(RUNNING, 0) => rs | wc
        int target = RUNNING;
        // 其中&的优先级大于|，而当i 为正数且i<CAPACITY时，i & CAPACITY的值为i
        int a = target | i & CAPACITY;
        int b = target | (i & CAPACITY);
        int d = i & CAPACITY | target;
        
        int c = target & CAPACITY | i;
        System.out.println("i=" + i + "&a=" + a + "&b=" + b + "&c=" + c + "&d=" + d);
//        int workerCount = workerCountOf(i); // c & CAPACITY;
//        int newState = ctlOf(target, workerCount); // target | workerCount
        System.out.println(i & CAPACITY);
        System.out.println((target | i & CAPACITY) & CAPACITY);
    }
    
    /**
     * 优先级S：（~ 位非） 优先级A：<<、>>和>>> 优先级B：（＆ 位与） 优先级C：（^ 位异或） shift+6 优先级D：（| 位或） 
     * <一句话功能简述> <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    @Test
    public void testPriority() {
        int RUNNING = 1;
        int workerCount = 23;
        int CAPACITY = -1;
        int a = RUNNING | workerCount ^ CAPACITY;
        int b = RUNNING | (workerCount ^ CAPACITY);
        int c = (RUNNING | workerCount) ^ CAPACITY;
        int d = RUNNING ^ CAPACITY | workerCount;
        System.out.println("a=" + a + "&b=" + b + "&c=" + c + "&d=" + d);
    }
    
    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }
    
    public void advanceRunState(int targetState) {
        for (;;) {
            int c = ctl.get();
            /**
             *  int c = oldState | oldWorkerCount;
             *  workerCountOf(c) = c & CAPACITY <==> oldState & CAPACITY | oldWorkerCount & CAPACITY 
             *  <==> 其中oldState & CAPACITY始终为0，而oldWorkerCount & CAPACITY
             *  在oldWorkerCount为正数且<CAPACITY时，oldWorkerCount & CAPACITY的值为oldWorkerCount
             *  <==>  oldWorkerCount
             */
            if (runStateAtLeast(c, targetState) ||
                ctl.compareAndSet(c, targetState | workerCountOf(c)));
                break;
        }
    }

}

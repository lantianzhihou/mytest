package com.wangbo.test.util.math;

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
 * 1. 负数的>>运算 <==> 算数/2的n次方 - 算数%2的n次方 != 0 ? 1 : 0; 而0和正数的>>运算 <==> 算数/2的n次方
 * 2. 负数的>>运算最终值为-1; 而0和正数的>>运算最终值为0
 * 
 * << 移位符，向左移n位，低位补0 (等价于 算数乘以2的n次方)
 * 
 * 利用“移位”操作实现或者调用API函数 将十进制数转换成二进制数
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2019年3月4日]
 * @see  [相关类/方法] com.wangbo.test.concurrentAndmutiThread.ThreadPoolExecutorTest
 * @since  [产品/模块版本]
 */
public class BitOperationTest {

	private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;
    
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
    
    
}

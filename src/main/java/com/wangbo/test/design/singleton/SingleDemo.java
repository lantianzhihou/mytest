package com.wangbo.test.design.singleton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SingleDemo {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        System.out.println("===================DoubleCheckedSingle===================");
        DoubleCheckedSingle doubleCheckedSingle = DoubleCheckedSingle.getInstance();
        DoubleCheckedSingle cloneDoubleChecked = cloneObject(doubleCheckedSingle);
        System.out.println(doubleCheckedSingle.hashCode());
        System.out.println(cloneDoubleChecked.hashCode());
        System.out.println(doubleCheckedSingle.equals(cloneDoubleChecked));

        System.out.println("====================HolderSingle==================");
        HolderSingle holderSingle = HolderSingle.getInstance();
        HolderSingle cloneholderSingle = cloneObject(holderSingle);
        System.out.println(holderSingle.hashCode());
        System.out.println(cloneholderSingle.hashCode());
        System.out.println(holderSingle.equals(cloneholderSingle));

        System.out.println("=====================EnumSingle=================");
        EnumSingle cloneEnumSingle = cloneObject(EnumSingle.INSTANCE);
        System.out.println(EnumSingle.INSTANCE.hashCode());
        System.out.println(cloneEnumSingle.hashCode());
        System.out.println(EnumSingle.INSTANCE.equals(cloneEnumSingle));
    }

    /**
     * 利用序列化实现深拷贝
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneObject(T t) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(t);

        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(is);
        Object obj = ois.readObject();
        return (T) obj;
    }

}

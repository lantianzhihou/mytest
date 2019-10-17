package com.wangbo.test.clazz.innerClazz;

import java.util.Comparator;

public class DeclaringClassA {
    
    /**
     *  getName：
     *  成员类memberClass的命名规则     ---enclosingClass&memberClass
     *  局部类localClass的命名规则        ---enclosingClass& + "\$[0-9]*" + localClass
     *  匿名累anonymousClass的命名规则     ---enclosingClass& + "\$[0-9]*"
     *  
     *  getCanonicalName--defined by the Java Language Specification:
     *  a.局部类和匿名类没有规范名,b.而顶级类的规范名getCanonicalName等于getName,c.成员类的规范名=enclosingClass.memberClass
     *  
     *  getSimpleBinaryName--the binary name without the leading enclosing class name:
     *  顶级类没有简单法名，而被封装类的简单法名SimpleBinaryName =  getName() - enclosingClass.getName()
     *  
     *  getSimpleName:
     *  1.顶级类的SimpleName = getName - 包名; 2.成员类和局部类的SimpleName = memberClass或localClass; 3.匿名类的SimpleName为空字符串""
     */
    public DeclaringClassA() {
        class LocalClassD {
            
        }
        System.out.println("==================LocalClassD=================");
        LocalClassD d = new LocalClassD();
        Class<? extends LocalClassD> classD = d.getClass();
        System.out.println(classD.getSimpleName());
        // isMemberClass :
        System.out.println(classD.isMemberClass());
        // class com.wangbo.test.clazz.innerClazz.DeclaringClassA
        System.out.println(classD.getEnclosingClass());
        // null
        System.out.println(classD.getDeclaringClass());
        // null
        System.out.println(classD.getEnclosingMethod());
        // public com.wangbo.test.clazz.innerClazz.DeclaringClassA()
        System.out.println(classD.getEnclosingConstructor());
    }
    
    {
        class LocalClassE {
            
        }
        System.out.println("==================LocalClassE=================");
        Class<? extends LocalClassE> classE = LocalClassE.class;
        // isMemberClass :
        System.out.println(classE.isMemberClass());
        // class com.wangbo.test.clazz.innerClazz.DeclaringClassA
        System.out.println(classE.getEnclosingClass());
        // null
        System.out.println(classE.getDeclaringClass());
        // null
        System.out.println(classE.getEnclosingMethod());
        // null
        System.out.println(classE.getEnclosingConstructor());
    }
    
    public static void main(String[] args) {
        Comparator<String> anonymousObj = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        };
        Class<? extends Comparator> anonymousClass = anonymousObj.getClass();
        // null
        System.out.println(anonymousClass.getCanonicalName());
        // com.wangbo.test.clazz.innerClazz.DeclaringClassA$1
        System.out.println(anonymousClass.getName());
        // ""
        System.out.println(anonymousClass.getSimpleName());
        DeclaringClassA a = new DeclaringClassA();
        DeclaredClassB b = a.new DeclaredClassB();
        System.out.println("==================DeclaredClassB=================");
        Class<? extends DeclaredClassB> classB = b.getClass();
        System.out.println(classB.getSimpleName());
        // isMemberClass :
        System.out.println(classB.isMemberClass());
        // class com.wangbo.test.clazz.innerClazz.DeclaringClassA
        System.out.println(classB.getEnclosingClass());
        // class com.wangbo.test.clazz.innerClazz.DeclaringClassA
        System.out.println(classB.getDeclaringClass());
        // null
        System.out.println(classB.getEnclosingMethod());
        // null
        System.out.println(classB.getEnclosingConstructor());
        
        System.out.println("==================DeclaredClassC=================");
        DeclaredClassC c = new DeclaredClassC();
        Class<? extends DeclaredClassC> classC = c.getClass();
        // isMemberClass :
        System.out.println(classC.isMemberClass());
        // class com.wangbo.test.clazz.innerClazz.DeclaringClassA
        System.out.println(classC.getEnclosingClass());
        // class com.wangbo.test.clazz.innerClazz.DeclaringClassA
        System.out.println(classC.getDeclaringClass());
        // null
        System.out.println(classC.getEnclosingMethod());
        // null
        System.out.println(classC.getEnclosingConstructor());
    }
    
    public class DeclaredClassB {
        
    }
    
    public static class DeclaredClassC {
        
    }

    static {
        class LocalClassF {
            
        }
        System.out.println("==================LocalClassF=================");
        // com.wangbo.test.clazz.innerClazz.DeclaringClassA$1LocalClassF
        Class<? extends LocalClassF> classF = LocalClassF.class;
        // isMemberClass :false
        System.out.println(classF.isMemberClass());
        // isMemberClass :true
        System.out.println(classF.isLocalClass());
        // class com.wangbo.test.clazz.innerClazz.DeclaringClassA
        System.out.println(classF.getEnclosingClass());
        // null
        System.out.println(classF.getDeclaringClass());
        // null
        System.out.println(classF.getEnclosingMethod());
        // null
        System.out.println(classF.getEnclosingConstructor());
    }
    
}

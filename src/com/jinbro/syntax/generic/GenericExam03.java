package com.jinbro.syntax.generic;

public class GenericExam03 {
    public static void main(String[] args) {
        Container<String> str = new Container<>();
        str.set("데이터");
        String res = str.get();

        Container<Integer> integer = new Container<>();
        integer.set(1);
        int res2 = integer.get();
    }
}

class Container<T>{

    T inst;

    public void set(T data){
        inst = data;
    }

    public T get(){
        return inst;
    }
}

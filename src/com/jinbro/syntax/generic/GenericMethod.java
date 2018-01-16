package com.jinbro.syntax.generic;

public class GenericMethod {

    public static void main(String[] args) {
        Data<Integer> intData = setData(1); //컴파일러가 알아서 파라미터를 가지고 데이터타입 추론
        System.out.println(intData.getData());

        Data<String> strData = setData("String");
        System.out.println(strData.getData());
    }


    /* 파라미터, 리턴타입 : 인스턴스 생성할 때 치환 */
    static <T> Data<T> setData(T data){
        return new Data<>(data);
    }
}

class Data<T> {

    private T data;

    public Data(T data) {
        this.data = data;
    }

    public T getData(){
        return data;
    }
}





class Util {

    public static void main(String[] args) {
        //String은 equals가 같은 값 -> 같은 객체참조 하도록 되어있어서
        Pair<String, String> data = new Pair<>("userId1", "1q2w3e4r");
        Pair<String, String> data2 = new Pair<>("userId1", "1q2w3e4r");

        System.out.println(Util.compare(data, data2));
    }


    public static <K,V> boolean compare(Pair<K,V> db, Pair<K,V> userInput){
        boolean key = db.getKey().equals(userInput.getKey());
        boolean value = db.getValue().equals(userInput.getValue());

        return key && value;
    }
}


class Pair<K,V> {
    private K key;
    private V value;

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

}
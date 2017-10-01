package com.jinbro.source.stdapi.object;

import java.util.Date;
import java.util.HashMap;

public class ObjectTest {

    public static void main(String[] args) {
        String str1 = new String("aaa");
        String str2 = new String("aaa");
        /* equals : 객체가 가진 데이터가 동일한지 비교 */
        System.out.println(str1.equals(str2));

        Member m1 = new Member("1234");
        Member m2 = new Member("1234");
        /* hashCode : 객체 식별값 리턴, HashSet/HashMap/HashTable은 hashCode -> equals 차례대로 두 객체 동등비교 */
        System.out.println(m1.hashCode() + ", " + m2.hashCode());

        /* hashCode - String : 오버라이딩(값은 값을 가지면 같은 해쉬코드를 리턴하도록, 원래는 메모리 주소값을 가지고)  */
        System.out.println(str1.hashCode() + ", " + str2.hashCode());

        /* toString - 객체정보(클래스 풀네임@16진수해쉬코드), String, Date는 오버라이딩 */
        System.out.println(m1.toString());
        System.out.println(str1.toString());
        System.out.println(new Date().toString());

        /*
            clone - 얕은 복사를 할 때 사용 - 얕은복사는 reference type가 한쪽이라도 변경되면 복사된 쪽도 변경됨
            clone 오버라이딩 - reference type도 복제되도록(같은 객체주소 저장X)
        */
        Member m3 = m2.getClone();
        System.out.println(m2 == m3); // false, 얕은 복사(필드 - primitive: 값, reference : 주소 복사)
        System.out.println(m3.getId() + ", " + m2.getId());


        /* finalize : 가비지컬렉터가 객체 소멸시키기 전 실행시킴  */
        m1 = null;
        System.gc(); //가비지컬렉터 실행 요청 : 실행 시점을 정확히 알 수 없으므로 finalize보다 별도 메서드 사용하는 것이
    }
}


class Member implements Cloneable {
    private String id;
    private int[] scores;

    public Member(String id){
        this.id = id;
        this.scores = new int[]{1,2,3};
    }

    /***** getter / setter *****/
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int[] getScores() {
        return scores;
    }
    public void setScores(int[] scores) {
        this.scores = scores;
    }

    /******************/

    public Member getClone(){
        Member cloned = null;
        try{
            cloned = (Member)this.clone(); //return type : Object
        }catch(CloneNotSupportedException e){
            System.out.println(e.getMessage());
        }
        return cloned;
    }



    /* 최상위 Object 클래스 equals를 오버라이딩 */
    @Override
    public boolean equals(Object obj) {

        boolean result = false;

        if(obj instanceof Member){
            Member member = (Member)obj;
            if(this.getId() == member.getId()){
                result = !result;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                '}';
    }


    /* clone 오버라이딩 : deep copy, reference 필드값 복제
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Member cloned = (Member)super.clone(); //primitive 값 복사는 그대로
        cloned.scores = Arrays.copyOf(this.scores, this.scores.length);

        return cloned;
    } */

    @Override
    protected void finalize() throws Throwable {
        System.out.println(this.id + "라는 id값을 가진 객체가 소멸됩니다");
    }
}

/* HashMap Key 사용 : hashCode, equals 오버라이딩(같은 해쉬코드, 같은 데이터를 가지고 있으면 같은 객체) */
class KeyExample{
    public static void main(String[] args) {
        HashMap<Key, String> hashMap = new HashMap<Key, String>();
        hashMap.put(new Key(1), "안녕");
        hashMap.put(new Key(1), "안녕");

        System.out.println(hashMap.get(new Key(1)));

        System.out.println(new Key(1).hashCode()); // 1이 해쉬코드
    }
}

class Key{

    private int key;

    public Key(int num){
        key = num;
    }

    public int getKey() {
        return key;
    }

    //new Key(1)은 각각 따로이지만 같은 hashCode를 반환하도록하여 동일 취급하도록 함
    @Override
    public int hashCode() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Key){
            Key comparekey = (Key)obj;

            if(this.getKey() == comparekey.getKey()){
                return true;
            }
        }
        return false;
    }
}


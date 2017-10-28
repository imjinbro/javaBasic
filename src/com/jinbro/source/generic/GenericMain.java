package com.jinbro.source.generic;

/*
    [제네릭, 파라미터와 타입 파라미터]
    - java5부터

    1) 파라미터 : 값(인자, argument)을 받아 파라미터 변수와 바인딩하고 메서드 내부에서 사용함
    2) 타입 파라미터
    - 파라미터인데, 그 값이 타입인 것
    - 객체를 생성할 때 타입을 받아서 그 타입으로 만듬
    - 인자를 넘겨받을 때에는 <타입> 으로

    3) 그 타입인 데이터 자체에 관심이 있다는 것
    - List<Integer> = new ArrayList<>() 라면 <Integer> 타입인 데이터 자체에 관심을 가짐


    [제네릭의 장점]
    1) 강력한 타입 체킹 : 인자로 받은 타입과 일치하지않는 데이터를 입력받으면 컴파일러가 에러를 냄
    2) 간편한 캐스팅
    - 인자로 받은 타입으로 컴파일러 자동변환되기때문에 list.get(idx) 할 때 원하는 타입으로 캐스팅하지않아도됨
    - 모든 타입을 받아들일 때 간편하게 className<T> 혹은 <T> int getScore와 같이 해놓으면 편하지!


    [제네릭 설정 레벨]
    1) 클래스(+생성자), 인터페이스
    - 클래스 레벨에서 제네릭이 설정되어있으면 static 메서드에서는 사용X : 인스턴스가 만들어질 때 type argument를 바당옴
    - 인스턴스 타입 파라미터

    2) 메서드
    - 파라미터, 리턴 타입 파라미터
    - static, instace method 상관없음 : 인스턴스 생성할 떄 type argument를 받아와 타입이 설정되기때문에
    - 메서드 인자값의 타입으로 추론함


    [제네릭 알아둘 것]
    1) Number > Integer이지만 List<Number>와 List<Integer>는 관계없음 : 타입파라미터의 상속관계와 List 상속관계는 상관없음
    - ArrayList(List 구현체)를 사용하고 같은 타입파라미터면 상관없겠지만

    2) java7부터 인스턴스 생성할 때 타입 인자를 2번 주지않아도됨 : 타입추론
    - List<Integer> list = new ArrayList<Integer>(); : java6까지
    - List<Integer> list = new ArrayList<>(); : java7부터

    3) Collections.emptyList() - static 메서드 사용할 때 인자로 넘기는 것 없는데 <T> 타입추론은 어떻게?
    - List<T> list <- Collections.emptyList() 리턴되는 리스트가 사용되면서 타입추론
    - 명시적으로 메서드 앞에 <타입> 타입 파라미터를 넘겨줄 수도 있음

    4) List<T> 라면 List를 구현하는 MyList<T, S> 가능함


    [와일드카드]
    - 타입 파라미터가 의미있게 사용되는가? 놉 사용되지않는다면 와일드카드 사용
    (1) 무엇이라도 들어가도된다 : 모른다, 몰라도된다, 무엇이 들어올지 알 필요없다
    (2) 데이터(타입)보다 데이터가 있으니깐 무엇을 한다에 관심


    1) List<?> list = new ArrayList<>() == List<? extends Object> list = new ArrayList<>();
    - 두가지 같은 코드 : 왼쪽을 사용하면됨, 오른쪽에서 생략된 것
    - 데이터에 중점을 두기보다 인자로 넘어오는 타입의 메서드에 중점을 둠
    - 혹은 List가 가진 메서드 사용에 중점을 둠


    2) 적용 레벨
    - 자료구조 인스턴스 생성 : 클래스 지정x
    - 메서드 적용 : static, instance 노상관


    3) extends 말고도 super 적용가능 : Down Bounded
    - List<? super B> : ? 타입파라미터가 B의 슈퍼클래스여야함
    - ? 만 가능함


    4) ? 와일드카드 제한
    - <T>로 선언하는 것과 달리 ?로 선언했을 때는 제한이 있음 : 좁은 의미로 사용, 데이터보다 메서드
    - API를 잘 살펴보면 <T, E, S, V> 등으로 선언된 것도 있고 <?>로 선언된 것도 있음

    (1) ? (unbound) : 자료구조 인스턴스 생성할 때 파라미터가 ? 라면 null만
    - ?는 타입 인자를 받지도 변환하지도않음 : 타입 인자를 받아 변환하지않음(타입이 뭔지 모르겠고), 기능 사용에만 중점을 둘 때 ?로
    => 리스트에 저장된 자료의 크기가 얼마인가?
    => 헷갈리면 T로 짰다가 ?로 고치기 : 데이터 관련 연산 이런 것들이 없다면
    => 보통 Object의 메서드를 사용함 : <?> 가 <? extends Object> 생략된 것, 비교만 한다면 Object 인 상태에서


    5) 알아둬야할 점 : 캡쳐 에러
    - 캡처(capture) 에러 : ? 와일드카드를 사용하는데, 구체적인 타입이 필요할 때 / 컴파일러가 타입추론이 필요한데 하지 못할 경우
    (1) 제네릭으로 변경
    (2) 강제적으로 캡쳐하도록 : 헬퍼메서드 사용
    (3) 제네릭, 와일드카드 사용없이 Raw type : List list = new ArrayList()


    [제네릭 vs 와일드카드]
    - 타입 파라미터가 의미있게 사용되는가?
*/

import java.util.*;

public class GenericMain {

    static <T> void test(T data, List<T> dataList){ };

    public static void main(String[] args) {

        List<String> strList= Collections.emptyList(); // Collections.<String>emptyList();

        /* 제네릭타입 : 인스턴스 생성할 떄 지정한 타입으로 T가 변경됨  */
        YesGeneric<Integer> data1 = new YesGeneric<>();
        data1.setData(1);
        int result = data1.getData();

        YesGeneric<String> data2 = new YesGeneric<>(); // 타입을 알아서 유추(java7부터)
        data2.setData("안녕하세요");
        String result1 = data2.getData();


        /* MyList가 List를 구현했기 때문에 : 타입파라미터 개수는 상관x */
        List<String> list = new MyList<String, Integer>();
    }
}


class YesGeneric<T>{
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

class NonGeneric {
    /* 모든 데이터를 받기위해서 Object(최상위 클래스)로 변수 타입 지정 */
    private Object data;

    public Object getData() {
        /* 리턴되는 타입에따라 캐스팅해서 내보내려면 instanceof를 사용하던지, getData 결과값을 받는 변수에서 캐스트해줘야함 */
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

class MyList<T, S> implements List<T> {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}
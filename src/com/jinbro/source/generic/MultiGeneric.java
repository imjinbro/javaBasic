package com.jinbro.source.generic;

public class MultiGeneric {
    public static void main(String[] args) {
        Product<Tv, String> product1 = new Product<>();
        Product<Car, String> product2 = new Product<>();

        product1.setCategory(new Tv());
        product1.setModelNumber("T12324123");
        System.out.println(product1.getModelNumber());

        product2.setCategory(new Car());
        product2.setModelNumber("C-1235123");
        System.out.println(product2.getModelNumber());
    }
}

class Product<T,M> {
    private T category;
    private M modelNumber;

    void setCategory(T category){
        this.category = category;
    }

    void setModelNumber(M modelNumber){
        this.modelNumber = modelNumber;
    }

    public T getCategory() {
        return category;
    }

    public M getModelNumber() {
        return modelNumber;
    }
}

class Tv{

}

class Car{

}
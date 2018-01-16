package com.jinbro.syntax.fp.stream;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

public class StreamParallelGoodCase {
    static final String[] priceStrings = {"1.0", "100.99", "35.75", "32.30", "99.00"};
    static final Random random = new Random(123);

    static final List<Item> items;

    static{
        final int length = 4_000_000;
        //final List<Item> list = new ArrayList<>(length); 초기 capacity 16 늘리고 복사하는 작업;;
        final Item[] list = new Item[length];

        for(int i=1; i<=length; i++){
            list[i-1] = new Item((long)i, "Product" + i, new BigDecimal(priceStrings[random.nextInt(5)]));
        }

        items = Arrays.asList(list);
    }

    static BigDecimal imperativeSum(final List<Item> items, final Predicate<Item> predicate){
        BigDecimal sum = BigDecimal.ZERO;

        for(final Item item : items){
            if(predicate.test(item)){
                sum = sum.add(item.getPrice());
            }
        }

        return sum;
    }

    static BigDecimal streamSum(final List<Item> items, final Predicate<Item> predicate){
        return items.stream()
                     .filter(predicate)
                     .map(item -> item.getPrice())
                     .reduce(BigDecimal.ZERO, (item1, item2) -> item1.add(item2));
    }

    static BigDecimal parallelStreamSum(final List<Item> items, final Predicate<Item> predicate){
        return items.parallelStream()
                .filter(predicate)
                .map(item -> item.getPrice())
                .reduce(BigDecimal.ZERO, (item1, item2) -> item1.add(item2));
    }


    static void imperativeTest(final BigDecimal std){
        final long start = System.currentTimeMillis();
        System.out.println(imperativeSum(items, item -> item.getPrice().compareTo(std) >= 0));
        System.out.println("imperative 걸린 시간 : " + (System.currentTimeMillis() - start) + "ms");
    }

    static void streamTest(final BigDecimal std){
        final long start = System.currentTimeMillis();
        System.out.println(streamSum(items, item -> item.getPrice().compareTo(std) >= 0));
        System.out.println("stream 걸린 시간 : " + (System.currentTimeMillis() - start) + "ms");
    }

    static void parallelStreamTest(final BigDecimal std){
        final long start = System.currentTimeMillis();
        System.out.println(parallelStreamSum(items, item -> item.getPrice().compareTo(std) >= 0));
        System.out.println("parallel stream 걸린 시간 : " + (System.currentTimeMillis() - start) + "ms");
    }

    public static void main(String[] args) {

        final BigDecimal std = new BigDecimal("30");


        imperativeTest(std);
        streamTest(std);
        parallelStreamTest(std);

        //jvm 설정 - 시동
        System.out.println("\n Ignore Test Above");
        System.out.println("\n Start");

        BigDecimal std2 = new BigDecimal("40.00");

        imperativeTest(std2);
        streamTest(std2);
        parallelStreamTest(std2);
    }
}

class Item{
    private Long id;
    private String name;
    private BigDecimal price;

    public Item() {
    }

    public Item(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != null ? !id.equals(item.id) : item.id != null) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;
        return price != null ? price.equals(item.price) : item.price == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            '}';
    }
}
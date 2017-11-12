package com.jinbro.source.fp.stream;

/*
    [Stream 정리]
    1) 이미 컬렉션에 저장된 것을 Stream 타입으로 변경하거나 of()로 데이터 여러개를 가지고 Stream으로 만들거나
    2) Stream 메서드 적용
    - intermediate operation method : 리턴타입 Stream
    - terminate operation method : 리턴타입 Stream X
 */

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

public class StreamExam {
    public static void main(String[] args) {
        final List<Product> products =
            Arrays.asList(
                new Product(1L, "A", new BigDecimal("100.50")),
                new Product(2L, "B", new BigDecimal("80.25")),
                new Product(3L, "C", new BigDecimal("76.39")),
                new Product(4L, "D", new BigDecimal("41.96")),
                new Product(5L, "E", new BigDecimal("54.76")),
                new Product(6L, "D", new BigDecimal("37.32"))
            );


        System.out.println( "Product.price > 50 : " +
            products.stream()
                    .filter( product -> product.getPrice().compareTo(new BigDecimal("50")) > 0)
                    .collect(toList())
        );


        System.out.println( "[Product.price > 50] \n" +
            products.stream()
                    .filter( product -> product.getPrice().compareTo(new BigDecimal("50")) > 0)
                    .map(product -> product.toString())
                    .collect(joining("\n"))
        );


        System.out.println( "[Product.price Sum] : " +
            products.stream()
                    .map(product ->  product.getPrice())
                    .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2))
                    //reduce(initial value, 2Arity Function) -> 0, product1.price -> sum, product2.price
        );


        System.out.println( "[Product.price Sum > 40] : " +
                products.stream()
                    .filter(product -> product.getPrice().compareTo(new BigDecimal(40)) > 0)
                    .map(product ->  product.getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    //.reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2))
        );

        System.out.println( "[Product Num price > 40] : " +
                products.stream()
                    .filter(product -> product.getPrice().compareTo(new BigDecimal(40)) > 0)
                    .count()
        );


        final OrderedItem item1 = new OrderedItem(10L, products.get(0),1);
        final OrderedItem item2 = new OrderedItem(20L, products.get(1),7);
        final OrderedItem item3 = new OrderedItem(30L, products.get(2),4);

        final OrderList myOrderList = new OrderList(201711121158L, Arrays.asList(item1, item2,item3));
        System.out.println("총 주문 금액 : " + myOrderList.totalPrice());
    }
}

class Product{
    private Long id;
    private String name;
    private BigDecimal price;

    public Product() {
    }
    public Product(Long id, String name, BigDecimal price) {
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

        Product product = (Product) o;

        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        return price != null ? price.equals(product.price) : product.price == null;
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
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            '}';
    }
}

/* 특정 프로덕트 몇개 주문했는지 */
class OrderedItem {
    private Long id;
    private Product product;
    private int quantity;

    public OrderedItem() {
    }
    public OrderedItem(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedItem that = (OrderedItem) o;

        if (quantity != that.quantity) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return product != null ? product.equals(that.product) : that.product == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "OrderedItem{" +
            "id=" + id +
            ", product=" + product +
            ", quantity=" + quantity +
            '}';
    }

    public BigDecimal getTotalPrice(){
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}

/* 최종 주문 리스트 : 어떤 아이템 몇개를 주문했는지 리스트 */
class OrderList {
    private Long id;
    private List<OrderedItem> items;

    public OrderList() {
    }
    public OrderList(Long id, List<OrderedItem> items) {
        this.id = id;
        this.items = items;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderedItem> getItems() {
        return items;
    }
    public void setItems(List<OrderedItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderList orderList = (OrderList) o;

        if (id != null ? !id.equals(orderList.id) : orderList.id != null) return false;
        return items != null ? items.equals(orderList.items) : orderList.items == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", items=" + items +
            '}';
    }

    public BigDecimal totalPrice(){
        return items.stream()
                    .map(item -> item.getTotalPrice())
                    .reduce(BigDecimal.ZERO, (total1, total2) -> total1.add(total2));
    }
}



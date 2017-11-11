package com.jinbro.source.fp.lambda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/*
    [함수형인터페이스 - 람다식 이럴 때 쓴다, 이렇게 쓴다]
    1) 람다식을 사용할 수 있는 여지를 만들어준다 : 함수형인터페이스 메서드 동작까지 구현
    - 예를 들어 Function<T, R> 이라면 function.apply(T t) 해놓으면 호출부에서 람다식으로 구현
    - 실제 함수몸통은 람다식, 이럴 때 호출된다는 것은 그 인터페이스 타입 객체가 구현된 곳에


    2) 4가지 API 함수형 인터페이스 적절하게 사용
    - Function<T, R> : 작업으로 타입 변환할 때
    - Consumer<T> : 작업은 하되 딱히 리턴되는 것이 없을 때
    - Predicate<T> : 작업하면서 true, false 작업이 필요할 때
    - Supplier<T> : 작업을 지연시켜야할 때 혹은 특정 시점에만 작업될 수 있도록 할 때


    [추가적인 내용]
    1) Predicate<? super T>
    - T가 ?의 super 타입이면 됨
    - 작업에서 T가 가진 메서드를 사용하면 될 때
 */


public class FuncInterfaceExamples {
    public static void main(String[] args) {

        Product productA = new Product(1L, "A", new BigDecimal("85.00"));
        Product productB = new Product(2L, "B", new BigDecimal("69.00"));
        Product productC = new Product(3L, "C", new BigDecimal("123.00"));
        Product productD = new Product(4L, "D", new BigDecimal("42.00"));
        Product productE = new Product(5L, "E", new BigDecimal("37.00"));

        final List<Product> products = Arrays.asList(
                productA, productB, productC, productD, productE
        );


        /* 람다식 : product가 <Product> 타입이라는 것을 추론함 */
        System.out.println(filter(products, product -> product.getPrice().compareTo(new BigDecimal("70")) <= 0));
        BigDecimal std = new BigDecimal("60");
        final List<Product> exprensiveProducts = filter(products, product -> product.getPrice().compareTo(std) >= 0);


        System.out.println("할인대상 : " + exprensiveProducts);
        final List<DiscountedProduct> discountedProducts = map(exprensiveProducts, product -> new DiscountedProduct(product.getId(), product.getName(), product.getPrice().multiply(new BigDecimal("0.5"))));
        System.out.println("할인 후(50%) : " + discountedProducts);


        /* Predicate<? super T>라서 둘다 적용 : 슈퍼타입의 메서드로만 작업 가능(getPrice) */
        final Predicate<Product> lessThanOrEqual40 = product -> product.getPrice().compareTo(new BigDecimal("40")) < 0;
        filter(discountedProducts,lessThanOrEqual40);
        filter(products,lessThanOrEqual40);


        apply(products, product -> product.setName("[우리집 상품] - " + product.getName()));
        System.out.println(products);


        /*
        List<BigDecimal> prices = map(products, product -> product.getPrice());
        BigDecimal total = BigDecimal.ZERO;

        for(final BigDecimal price : prices){
            total = total.add(price);
        }
        */

        System.out.println("상품 총 가격 : " + total(products, product -> product.getPrice()));
        System.out.println("할인상품 총 가격 : " + total(discountedProducts, product -> product.getPrice()));



        Order order = new Order(1L, "on-1234", Arrays.asList(
                new OrderedItem(1L, productA, 4),
                new OrderedItem(1L, productC, 3),
                new OrderedItem(1L, productD, 7),
                new OrderedItem(1L, productE, 1)
        ));

        /* 일일이 이렇게 계속 만들어줘야하는데, total을 사용하면 계속 갈아끼울 수 있음 */
        BigDecimal orderTotal = BigDecimal.ZERO;
        for(final OrderedItem item : order.getItems()){
            orderTotal = orderTotal.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        System.out.println("주문 총 금액(old way) : " + orderTotal);

    }

    private static <T> BigDecimal total(List<T> list, Function<T,BigDecimal> mapper){
        BigDecimal total = BigDecimal.ZERO;

        for(final T t : list){
            total = total.add(mapper.apply(t)); //mapper.apply(t) 람다식 대체, <T> 타입을 가져와서 연산을 할 수 있도록 1개씩
        }

        return total;
    }


    private static <T> void apply(List<T> list, Consumer<T> consumer){
        for(T t : list){
            consumer.accept(t);
        }
    }

    /*
        final List<DiscountedProduct> discountedProducts = new ArrayList<>();
        for(final Product p : exprensiveProduct){
            int dcPrice = (int)(p.getPrice().doubleValue()*0.8);
            discountedProducts.add(new DiscountedProduct(p.getId(), p.getName(), new BigDecimal(dcPrice)));
        }
        System.out.println("할인 후 : " + discountedProducts); //toString()을 오버라이딩 해줘야하긴 함(Product toString 사용중)
    */
    private static <T, R> List<R> map(List<T> list, Function<T, R> function){
        final List<R> result = new ArrayList<>();
        for(final T t : list){
            result.add(function.apply(t));
        }

        return result;
    }


    /* 가격이 20인 것만 필터링 : 가격이 언제든 바뀔 수 있으니 갈아끼워보자(아래코드)
        final List<Product> result = new ArrayList<>();
        final BigDecimal std = new BigDecimal("80.00");
        for(final Product p : products){
            if(p.getPrice().compareTo(std) >= 0){
                result.add(p);
            }
        }
        System.out.println(result);
    */
    /* 함수형 인터페이스의 메서드는 표준을 쓸 때는 타입만 생각하면 됨 : <T> 타입을 넣었을 때 boolean */
    private static <T> List<T> filter(List<T> list, Predicate<? super T> predicate){
        final List<T> result = new ArrayList<>();

        for(T t : list){
            if(predicate.test(t)){
                result.add(t);
            }
        }
        return result;
    }

}



//@Data : lombok을 사용하면 보일러플레이트 코드를 없앨 수 있는데 적용이 안됨ㅠ(왜 안되지)
class Order {
    private Long id; // db
    private String orderNumber; // 비즈니스 키

    List<OrderedItem> items;

    public Order() {
    }

    public Order(Long id, String orderNumber, List<OrderedItem> items) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

        Order order = (Order) o;

        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (orderNumber != null ? !orderNumber.equals(order.orderNumber) : order.orderNumber != null) return false;
        return items != null ? items.equals(order.items) : order.items == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orderNumber != null ? orderNumber.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", orderNumber='").append(orderNumber).append('\'');
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }
}

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
        final StringBuilder sb = new StringBuilder("OrderedItem{");
        sb.append("id=").append(id);
        sb.append(", product=").append(product);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}

class Product {
    private  Long id;
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
        return new StringBuilder("Product{")
            .append("id=").append(id)
            .append(", name='").append(name).append('\'')
            .append(", price=").append(price)
            .append('}').toString();
    }
}

class DiscountedProduct extends Product{
    public DiscountedProduct(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return new StringBuilder("DiscountedProduct{")
                .append("id=").append(getId())
                .append(", name='").append(getName()).append('\'')
                .append(", price=").append(getPrice())
                .append('}').toString();
    }
}


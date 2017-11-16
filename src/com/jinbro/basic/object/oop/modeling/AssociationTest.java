package com.jinbro.basic.object.oop.modeling;

import java.time.LocalDate;
import java.util.Vector;

public class AssociationTest {
    public static void main(String[] args) {
        Book designPattern = new Book("designPattern");
        Member jinbro = new Member(true,"jinbro");

        Borrowing borrowing = new Borrowing(jinbro, designPattern);
        System.out.println(borrowing.getBorrowInfo());
    }
}

class Member{
//    private Vector<Book> books;
    private Area area;
    private String name;
    private Vector<Borrowing> borrowings;

    public Member(boolean isLocal, String name) {
        //books = new Vector<>();
        if(isLocal){
            area = new Local();
        } else {
            area = new NonLocal();
        }

        this.name = name;
        borrowings = new Vector<>();
    }

//    public void borrowBook(Book book){
//        books.add(book);
//    }
//
//    public void returnBook(Book book){
//        books.remove(book);
//    }

    public void addBorrwing(Borrowing borrowing){
        borrowings.add(borrowing);
    }

    public String getName() {
        return name;
    }
}


class Borrowing{
    private Member member;
    private Book book;

    private String date; //history

    public Borrowing(Member member, Book book) {
        this.member = member;
        this.member.addBorrwing(this);

        this.book = book;
        this.book.addBorrowing(this);

        date = LocalDate.now().toString();
    }

    public String getBorrowInfo(){
        return "["+ date + " 대여 기록] " + member.getName() + " : " + book.getName();
    }
}


class Book{
    private String name;
    private Vector<Borrowing> borrowings;

    public Book(String name) {
        this.name = name;
        borrowings = new Vector<>();
    }

    public void addBorrowing(Borrowing borrowing){
        borrowings.add(borrowing);
    }

    public String getName() {
        return name;
    }
}


abstract class Area{

}

class Local extends Area{

}

class NonLocal extends Area{

}



class Worker{
    private String name;
    //private Vector<Work> works;
    private Vector<Task> tasks;

    public Worker(String name) {
        this.name = name;
        tasks = new Vector<>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public Vector<Work> getWorks(){
        Vector<Work> workList = new Vector<>();

        for(final Task task : tasks){
            workList.add(task.getWork());
        }

        return workList;
    }
}

class Task{
    private Worker worker;
    private Work work;

    private String limitDate; //작업 일시로 관리 : 여러 워커, 워크들

    public Task(Worker worker, Work work) {
        this.worker = worker;
        this.worker.addTask(this);
        this.work = work;
        this.work.addTask(this);
    }

    public Worker getWorker() {
        return worker;
    }

    public Work getWork() {
        return work;
    }
}


class Work{
    private String name;
    //private Vector<Worker> workers;
    private Vector<Task> tasks;

    public Work(String name) {
        this.name = name;
        tasks = new Vector<>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public Vector<Worker> getWorkers(){
        Vector<Worker> workerList = new Vector<>();

        for(final Task task : tasks){
            workerList.add(task.getWorker());
        }

        return workerList;
    }
}



class Customer{
    private String name;
    private Payment payment; //리스트나 배열이 될 수 있음
    private Vector<Order> orders;

    public Customer(String name, Payment payment) {
        this.name = name;
        this.payment = payment;
        orders = new Vector<>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public String getName() {
        return name;
    }

    public Payment getPayment() {
        return payment;
    }
}

class Order{
    private Customer customer;
    private Item item;
    private int quantity;

    public Order(Customer customer, Item item, int quantity) {
        this.customer = customer;
        this.customer.addOrder(this);

        this.item = item;
        this.quantity = quantity;
    }

    public String getOrderInfo(){
        return "[ "+ customer.getName() +"님의 주문 정보] : " + item.getName() + " - " + quantity + "개";
    }
}

class Item{
    private String name;
    private Vector<Order> orders;

    public Item(String name) {
        this.name = name;
        orders = new Vector<>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public String getName() {
        return name;
    }
}


abstract class Payment{
    abstract void pay();
}

abstract class Card extends Payment{

}

class CreditCard extends Card{

    @Override
    void pay() {

    }
}

class Debitcard extends Card{

    @Override
    void pay() {

    }
}

class Cash extends Payment{

    @Override
    void pay() {

    }
}
package com.atguigu.spring6.iocxml.di;

/**
 * @Author: Admin
 * @Create: 2024/7/4 - 下午4:27
 * @Version: v1.0
 * ClassName: Bok
 * Package: com.atguigu.spring6.iocxml.di
 * Description: 描述
 */
public class Book {
    private String name;
    private String author;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
        System.out.println("book构造器");
    }

    public Book() {
        System.out.println("book无参构造器");
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static void main(String[] args) {
        // 1. set 方法注入
        Book book = new Book();
        book.setName("java");
        book.setAuthor("atguigu");
        System.out.println(book);

        // 2. 通过构造器注入
        Book book1 = new Book("java", "atguigu");
        System.out.println(book1);

    }
}

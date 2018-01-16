package com.jinbro.syntax.collection;

import java.util.ArrayList;
import java.util.List;

public class CollectionTest1 {
    public static void main(String[] args) {
        PostDAO dao = new PostDAO();
        List<Post> postList = dao.getPostList();

        for(Post p : postList){
            System.out.println("| 제목 : " + p.getTitle() + " | 내용 : " + p.getContent());
        }
    }
}


class PostDAO {
    private List<Post> postList = new ArrayList<>();

    public List<Post> getPostList() {
        /*
            [원래는 DB에 쿼리 날려서 리턴 받은 데이터를 List에 저장함]
            - 없으니깐 생성해서 집어넣기
        */
        postList.add(new Post("제목1", "A"));
        postList.add(new Post("제목2", "B"));
        postList.add(new Post("제목3", "C"));
        postList.add(new Post("제목4", "D"));
        postList.add(new Post("제목5", "E"));

        return postList;
    }


}

class Post{
    private String title;
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
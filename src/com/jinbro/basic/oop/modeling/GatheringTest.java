package com.jinbro.basic.object.oop.modeling;

import java.util.Vector;

public class GatheringTest {
}


class Club{
    private Vector<Student> memberList;

    public Club() {
        memberList = new Vector<>();
    }

    public void addClubMember(Student student){
        memberList.add(student);
    }
}
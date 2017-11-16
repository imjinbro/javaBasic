package com.jinbro.basic.object.oop.modeling;

/*
    [연관관계]
    - (1:1 / 1:N / M:N) + (단방향/양방향) - 각 객체가 서로 알고 있는지 없는지
    - M:N 연관관계와 연관클래스 : M:N 연관 관계에 있는 두 클래스 사이에 필요한 클래스
    (1) OOO학생이 A+다 - x
    (2) OOO과목이 A+다 - x
    (3) OOO학생이 2017년 OOO과목에서 A+다
    - 학생과 과목정보 두 객체가 같이 존재할 때(성적표 클래스)
    - 학생 : 자신(학생)의 여러과목 성적표
    - 과목 : 자신(과목)의 여러학생 성적표
    - 성적표 : 학생, 과목 정보를 모두 가지고 있고, 성적표 생성 때 각각 여러과목, 여러학생 추가되도록
    - 연관클래스 : 1:N으로 단순화

    - 클래스 내부에서 사용함으로서 관계표현
 */

import java.util.Vector;

public class Association {
    public static void main(String[] args) {
        Student jinbro = new Student("jinbro");
        Student jinhyung = new Student("jinhyung");

        Course designPattern = new Course("designPattern");
        Course oop = new Course("oop");

        Transcript dpTranscript = new Transcript(jinbro, designPattern);
        Transcript oopTranscript = new Transcript(jinbro, oop);
        Transcript oopTranscript2 = new Transcript(jinhyung, oop);

        System.out.println("[jinbro 수강목록]");
        for(Course c : jinbro.getCourses()){
            System.out.println(c.getName());
        }

        System.out.println("[디자인패턴 수강학생]");
        for(Student s : designPattern.getStudents()) {
            System.out.println(s.getName());
        }
    }
}

class Professor{
    private Vector<Student> students;
}

class Student{
    private Professor advisor;
    private Club club;

    private String name;
    private Vector<Transcript> transcripts;

    public Student(String name) {
        this.name = name;
        transcripts = new Vector<>();
    }

    public String getName() {
        return name;
    }

    public void addTransripts(Transcript transcript){
        transcripts.add(transcript);
    }

    public Vector<Course> getCourses(){
        Vector<Course> courses = new Vector<>();
        for(Transcript transcript : transcripts){
            courses.add(transcript.getCourse());
        }

        return courses;
    }
}

class Transcript{
    private Student student;
    private Course course;

    private String grade;
    private String date;

    public Transcript(Student student, Course course) {
        this.student = student;
        this.student.addTransripts(this);
        this.course = course;
        this.course.addTranscript(this);
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

class Course{
    private String name;
    private Vector<Transcript> transcripts;

    public Course(String name) {
        this.name = name;
        transcripts = new Vector<>();
    }

    public String getName() {
        return name;
    }

    public void addTranscript(Transcript transcript){
        transcripts.add(transcript);
    }

    public Vector<Student> getStudents(){
        Vector<Student> students = new Vector<>();
        for(Transcript t : transcripts){
            students.add(t.getStudent());
        }
        return students;
    }
}




class Person{
    private Phone homePhone;
    private Phone officePhone;

    public void setHomePhone(Phone homePhone) {
        this.homePhone = homePhone;
    }

    public void setOfficePhone(Phone officePhone) {
        this.officePhone = officePhone;
    }

    public Phone getHomePhone() {
        return homePhone;
    }

    public Phone getOfficePhone() {
        return officePhone;
    }
}

abstract class Phone{
    abstract void call();
}

class homePhone extends Phone{
    @Override
    void call() {

    }
}

class officePhone extends Phone{
    @Override
    void call() {

    }
}




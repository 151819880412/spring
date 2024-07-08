package com.atguigu.spring6.iocxml.dimap;

import java.util.List;
import java.util.Map;

/**
 * @Author: Admin
 * @Create: 2024/7/5 - 下午4:01
 * @Version: v1.0
 * ClassName: Student
 * Package: com.atguigu.spring6.iocxml.dimap
 * Description: 描述
 */
public class Student {
    private String name;
    private String id;
    private Map<String,Teacher> teacherMap;
    private List<Lesson> lessonList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Teacher> getTeacherMap() {
        return teacherMap;
    }

    public void setTeacherMap(Map<String, Teacher> teacherMap) {
        this.teacherMap = teacherMap;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    public void run (){
        System.out.println("学生的id"+id+"  学生的name"+name);
        System.out.println(teacherMap);
        System.out.println(lessonList);
    }
}

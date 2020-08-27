/*
 * This file is generated by jOOQ.
 */
package io.github.mzdluo123.timetablebot.gen.timetable.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OtherCourse implements Serializable {

    private static final long serialVersionUID = 1007575993;

    private Integer id;
    private String  name;
    private Double  score;
    private Byte    week;
    private String  teacher;

    public OtherCourse() {}

    public OtherCourse(OtherCourse value) {
        this.id = value.id;
        this.name = value.name;
        this.score = value.score;
        this.week = value.week;
        this.teacher = value.teacher;
    }

    public OtherCourse(
        Integer id,
        String  name,
        Double  score,
        Byte    week,
        String  teacher
    ) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.week = week;
        this.teacher = teacher;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return this.score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Byte getWeek() {
        return this.week;
    }

    public void setWeek(Byte week) {
        this.week = week;
    }

    public String getTeacher() {
        return this.teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("OtherCourse (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(score);
        sb.append(", ").append(week);
        sb.append(", ").append(teacher);

        sb.append(")");
        return sb.toString();
    }
}

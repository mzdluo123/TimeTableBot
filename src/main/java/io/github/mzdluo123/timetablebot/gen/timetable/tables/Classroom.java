/*
 * This file is generated by jOOQ.
 */
package io.github.mzdluo123.timetablebot.gen.timetable.tables;


import io.github.mzdluo123.timetablebot.gen.timetable.Keys;
import io.github.mzdluo123.timetablebot.gen.timetable.Timetable;
import io.github.mzdluo123.timetablebot.gen.timetable.tables.records.ClassroomRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Classroom extends TableImpl<ClassroomRecord> {

    private static final long serialVersionUID = 1928066641;

    /**
     * The reference instance of <code>timetable.classroom</code>
     */
    public static final Classroom CLASSROOM = new Classroom();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ClassroomRecord> getRecordType() {
        return ClassroomRecord.class;
    }

    /**
     * The column <code>timetable.classroom.id</code>.
     */
    public final TableField<ClassroomRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>timetable.classroom.location</code>. 教室位置
     */
    public final TableField<ClassroomRecord, String> LOCATION = createField(DSL.name("location"), org.jooq.impl.SQLDataType.VARCHAR(10).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "教室位置");

    /**
     * Create a <code>timetable.classroom</code> table reference
     */
    public Classroom() {
        this(DSL.name("classroom"), null);
    }

    /**
     * Create an aliased <code>timetable.classroom</code> table reference
     */
    public Classroom(String alias) {
        this(DSL.name(alias), CLASSROOM);
    }

    /**
     * Create an aliased <code>timetable.classroom</code> table reference
     */
    public Classroom(Name alias) {
        this(alias, CLASSROOM);
    }

    private Classroom(Name alias, Table<ClassroomRecord> aliased) {
        this(alias, aliased, null);
    }

    private Classroom(Name alias, Table<ClassroomRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Classroom(Table<O> child, ForeignKey<O, ClassroomRecord> key) {
        super(child, key, CLASSROOM);
    }

    @Override
    public Schema getSchema() {
        return Timetable.TIMETABLE;
    }

    @Override
    public Identity<ClassroomRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CLASSROOM;
    }

    @Override
    public UniqueKey<ClassroomRecord> getPrimaryKey() {
        return Keys.KEY_CLASSROOM_PRIMARY;
    }

    @Override
    public List<UniqueKey<ClassroomRecord>> getKeys() {
        return Arrays.<UniqueKey<ClassroomRecord>>asList(Keys.KEY_CLASSROOM_PRIMARY, Keys.KEY_CLASSROOM_CLASSROOM_LOCATION_UNIQUE);
    }

    @Override
    public Classroom as(String alias) {
        return new Classroom(DSL.name(alias), this);
    }

    @Override
    public Classroom as(Name alias) {
        return new Classroom(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Classroom rename(String name) {
        return new Classroom(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Classroom rename(Name name) {
        return new Classroom(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}

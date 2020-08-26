/*
 * This file is generated by jOOQ.
 */
package io.github.mzdluo123.timetablebot.gen.timetable;


import io.github.mzdluo123.timetablebot.gen.timetable.tables.*;
import io.github.mzdluo123.timetablebot.gen.timetable.tables.records.*;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>timetable</code> schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<ClassroomRecord, Integer> IDENTITY_CLASSROOM = Identities0.IDENTITY_CLASSROOM;
    public static final Identity<CourseRecord, Integer> IDENTITY_COURSE = Identities0.IDENTITY_COURSE;
    public static final Identity<CoursetimeRecord, Integer> IDENTITY_COURSETIME = Identities0.IDENTITY_COURSETIME;
    public static final Identity<UserRecord, Integer> IDENTITY_USER = Identities0.IDENTITY_USER;
    public static final Identity<UsercourseRecord, Integer> IDENTITY_USERCOURSE = Identities0.IDENTITY_USERCOURSE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ClassroomRecord> KEY_CLASSROOM_PRIMARY = UniqueKeys0.KEY_CLASSROOM_PRIMARY;
    public static final UniqueKey<ClassroomRecord> KEY_CLASSROOM_CLASSROOM_LOCATION_UNIQUE = UniqueKeys0.KEY_CLASSROOM_CLASSROOM_LOCATION_UNIQUE;
    public static final UniqueKey<CourseRecord> KEY_COURSE_PRIMARY = UniqueKeys0.KEY_COURSE_PRIMARY;
    public static final UniqueKey<CoursetimeRecord> KEY_COURSETIME_PRIMARY = UniqueKeys0.KEY_COURSETIME_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_USER_ACCOUNT_UNIQUE = UniqueKeys0.KEY_USER_USER_ACCOUNT_UNIQUE;
    public static final UniqueKey<UserRecord> KEY_USER_USER_STUDENT_ID_UNIQUE = UniqueKeys0.KEY_USER_USER_STUDENT_ID_UNIQUE;
    public static final UniqueKey<UsercourseRecord> KEY_USERCOURSE_PRIMARY = UniqueKeys0.KEY_USERCOURSE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<CoursetimeRecord, CourseRecord> FK_COURSETIME_COURSE_ID = ForeignKeys0.FK_COURSETIME_COURSE_ID;
    public static final ForeignKey<CoursetimeRecord, ClassroomRecord> FK_COURSETIME_CLASS_ROOM_ID = ForeignKeys0.FK_COURSETIME_CLASS_ROOM_ID;
    public static final ForeignKey<UsercourseRecord, UserRecord> FK_USERCOURSE_USER_ID = ForeignKeys0.FK_USERCOURSE_USER_ID;
    public static final ForeignKey<UsercourseRecord, CourseRecord> FK_USERCOURSE_COURSE_ID = ForeignKeys0.FK_USERCOURSE_COURSE_ID;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<ClassroomRecord, Integer> IDENTITY_CLASSROOM = Internal.createIdentity(Classroom.CLASSROOM, Classroom.CLASSROOM.ID);
        public static Identity<CourseRecord, Integer> IDENTITY_COURSE = Internal.createIdentity(Course.COURSE, Course.COURSE.ID);
        public static Identity<CoursetimeRecord, Integer> IDENTITY_COURSETIME = Internal.createIdentity(Coursetime.COURSETIME, Coursetime.COURSETIME.ID);
        public static Identity<UserRecord, Integer> IDENTITY_USER = Internal.createIdentity(User.USER, User.USER.ID);
        public static Identity<UsercourseRecord, Integer> IDENTITY_USERCOURSE = Internal.createIdentity(Usercourse.USERCOURSE, Usercourse.USERCOURSE.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<ClassroomRecord> KEY_CLASSROOM_PRIMARY = Internal.createUniqueKey(Classroom.CLASSROOM, "KEY_classroom_PRIMARY", new TableField[] { Classroom.CLASSROOM.ID }, true);
        public static final UniqueKey<ClassroomRecord> KEY_CLASSROOM_CLASSROOM_LOCATION_UNIQUE = Internal.createUniqueKey(Classroom.CLASSROOM, "KEY_classroom_classroom_location_unique", new TableField[] { Classroom.CLASSROOM.LOCATION }, true);
        public static final UniqueKey<CourseRecord> KEY_COURSE_PRIMARY = Internal.createUniqueKey(Course.COURSE, "KEY_course_PRIMARY", new TableField[] { Course.COURSE.ID }, true);
        public static final UniqueKey<CoursetimeRecord> KEY_COURSETIME_PRIMARY = Internal.createUniqueKey(Coursetime.COURSETIME, "KEY_coursetime_PRIMARY", new TableField[] { Coursetime.COURSETIME.ID }, true);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = Internal.createUniqueKey(User.USER, "KEY_user_PRIMARY", new TableField[] { User.USER.ID }, true);
        public static final UniqueKey<UserRecord> KEY_USER_USER_ACCOUNT_UNIQUE = Internal.createUniqueKey(User.USER, "KEY_user_user_account_unique", new TableField[] { User.USER.ACCOUNT }, true);
        public static final UniqueKey<UserRecord> KEY_USER_USER_STUDENT_ID_UNIQUE = Internal.createUniqueKey(User.USER, "KEY_user_user_student_id_unique", new TableField[] { User.USER.STUDENT_ID }, true);
        public static final UniqueKey<UsercourseRecord> KEY_USERCOURSE_PRIMARY = Internal.createUniqueKey(Usercourse.USERCOURSE, "KEY_usercourse_PRIMARY", new TableField[] { Usercourse.USERCOURSE.ID }, true);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<CoursetimeRecord, CourseRecord> FK_COURSETIME_COURSE_ID = Internal.createForeignKey(Keys.KEY_COURSE_PRIMARY, Coursetime.COURSETIME, "fk_coursetime_course_id", new TableField[] { Coursetime.COURSETIME.COURSE }, true);
        public static final ForeignKey<CoursetimeRecord, ClassroomRecord> FK_COURSETIME_CLASS_ROOM_ID = Internal.createForeignKey(Keys.KEY_CLASSROOM_PRIMARY, Coursetime.COURSETIME, "fk_coursetime_class_room_id", new TableField[] { Coursetime.COURSETIME.CLASS_ROOM }, true);
        public static final ForeignKey<UsercourseRecord, UserRecord> FK_USERCOURSE_USER_ID = Internal.createForeignKey(Keys.KEY_USER_PRIMARY, Usercourse.USERCOURSE, "fk_usercourse_user_id", new TableField[] { Usercourse.USERCOURSE.USER }, true);
        public static final ForeignKey<UsercourseRecord, CourseRecord> FK_USERCOURSE_COURSE_ID = Internal.createForeignKey(Keys.KEY_COURSE_PRIMARY, Usercourse.USERCOURSE, "fk_usercourse_course_id", new TableField[] { Usercourse.USERCOURSE.COURSE }, true);
    }
}
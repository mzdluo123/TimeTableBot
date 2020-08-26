/*
 * This file is generated by jOOQ.
 */
package io.github.mzdluo123.timetablebot.gen.information_schema.tables;


import io.github.mzdluo123.timetablebot.gen.information_schema.InformationSchema;
import io.github.mzdluo123.timetablebot.gen.information_schema.tables.records.InnodbFtDefaultStopwordRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class InnodbFtDefaultStopword extends TableImpl<InnodbFtDefaultStopwordRecord> {

    private static final long serialVersionUID = -1490540478;

    /**
     * The reference instance of <code>information_schema.INNODB_FT_DEFAULT_STOPWORD</code>
     */
    public static final InnodbFtDefaultStopword INNODB_FT_DEFAULT_STOPWORD = new InnodbFtDefaultStopword();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<InnodbFtDefaultStopwordRecord> getRecordType() {
        return InnodbFtDefaultStopwordRecord.class;
    }

    /**
     * The column <code>information_schema.INNODB_FT_DEFAULT_STOPWORD.value</code>.
     */
    public final TableField<InnodbFtDefaultStopwordRecord, String> VALUE = createField(DSL.name("value"), org.jooq.impl.SQLDataType.VARCHAR(18).nullable(false).defaultValue(org.jooq.impl.DSL.inline("''", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * Create a <code>information_schema.INNODB_FT_DEFAULT_STOPWORD</code> table reference
     */
    public InnodbFtDefaultStopword() {
        this(DSL.name("INNODB_FT_DEFAULT_STOPWORD"), null);
    }

    /**
     * Create an aliased <code>information_schema.INNODB_FT_DEFAULT_STOPWORD</code> table reference
     */
    public InnodbFtDefaultStopword(String alias) {
        this(DSL.name(alias), INNODB_FT_DEFAULT_STOPWORD);
    }

    /**
     * Create an aliased <code>information_schema.INNODB_FT_DEFAULT_STOPWORD</code> table reference
     */
    public InnodbFtDefaultStopword(Name alias) {
        this(alias, INNODB_FT_DEFAULT_STOPWORD);
    }

    private InnodbFtDefaultStopword(Name alias, Table<InnodbFtDefaultStopwordRecord> aliased) {
        this(alias, aliased, null);
    }

    private InnodbFtDefaultStopword(Name alias, Table<InnodbFtDefaultStopwordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> InnodbFtDefaultStopword(Table<O> child, ForeignKey<O, InnodbFtDefaultStopwordRecord> key) {
        super(child, key, INNODB_FT_DEFAULT_STOPWORD);
    }

    @Override
    public Schema getSchema() {
        return InformationSchema.INFORMATION_SCHEMA;
    }

    @Override
    public InnodbFtDefaultStopword as(String alias) {
        return new InnodbFtDefaultStopword(DSL.name(alias), this);
    }

    @Override
    public InnodbFtDefaultStopword as(Name alias) {
        return new InnodbFtDefaultStopword(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public InnodbFtDefaultStopword rename(String name) {
        return new InnodbFtDefaultStopword(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public InnodbFtDefaultStopword rename(Name name) {
        return new InnodbFtDefaultStopword(name, null);
    }

    // -------------------------------------------------------------------------
    // Row1 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row1<String> fieldsRow() {
        return (Row1) super.fieldsRow();
    }
}
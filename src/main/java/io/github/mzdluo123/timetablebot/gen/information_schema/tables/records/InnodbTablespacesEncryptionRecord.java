/*
 * This file is generated by jOOQ.
 */
package io.github.mzdluo123.timetablebot.gen.information_schema.tables.records;


import io.github.mzdluo123.timetablebot.gen.information_schema.tables.InnodbTablespacesEncryption;

import org.jooq.Field;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.TableRecordImpl;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class InnodbTablespacesEncryptionRecord extends TableRecordImpl<InnodbTablespacesEncryptionRecord> implements Record10<UInteger, String, UInteger, UInteger, UInteger, UInteger, ULong, ULong, UInteger, UInteger> {

    private static final long serialVersionUID = -1223782524;

    /**
     * Setter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.SPACE</code>.
     */
    public void setSpace(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.SPACE</code>.
     */
    public UInteger getSpace() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.NAME</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.NAME</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.ENCRYPTION_SCHEME</code>.
     */
    public void setEncryptionScheme(UInteger value) {
        set(2, value);
    }

    /**
     * Getter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.ENCRYPTION_SCHEME</code>.
     */
    public UInteger getEncryptionScheme() {
        return (UInteger) get(2);
    }

    /**
     * Setter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.KEYSERVER_REQUESTS</code>.
     */
    public void setKeyserverRequests(UInteger value) {
        set(3, value);
    }

    /**
     * Getter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.KEYSERVER_REQUESTS</code>.
     */
    public UInteger getKeyserverRequests() {
        return (UInteger) get(3);
    }

    /**
     * Setter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.MIN_KEY_VERSION</code>.
     */
    public void setMinKeyVersion(UInteger value) {
        set(4, value);
    }

    /**
     * Getter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.MIN_KEY_VERSION</code>.
     */
    public UInteger getMinKeyVersion() {
        return (UInteger) get(4);
    }

    /**
     * Setter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.CURRENT_KEY_VERSION</code>.
     */
    public void setCurrentKeyVersion(UInteger value) {
        set(5, value);
    }

    /**
     * Getter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.CURRENT_KEY_VERSION</code>.
     */
    public UInteger getCurrentKeyVersion() {
        return (UInteger) get(5);
    }

    /**
     * Setter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.KEY_ROTATION_PAGE_NUMBER</code>.
     */
    public void setKeyRotationPageNumber(ULong value) {
        set(6, value);
    }

    /**
     * Getter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.KEY_ROTATION_PAGE_NUMBER</code>.
     */
    public ULong getKeyRotationPageNumber() {
        return (ULong) get(6);
    }

    /**
     * Setter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.KEY_ROTATION_MAX_PAGE_NUMBER</code>.
     */
    public void setKeyRotationMaxPageNumber(ULong value) {
        set(7, value);
    }

    /**
     * Getter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.KEY_ROTATION_MAX_PAGE_NUMBER</code>.
     */
    public ULong getKeyRotationMaxPageNumber() {
        return (ULong) get(7);
    }

    /**
     * Setter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.CURRENT_KEY_ID</code>.
     */
    public void setCurrentKeyId(UInteger value) {
        set(8, value);
    }

    /**
     * Getter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.CURRENT_KEY_ID</code>.
     */
    public UInteger getCurrentKeyId() {
        return (UInteger) get(8);
    }

    /**
     * Setter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.ROTATING_OR_FLUSHING</code>.
     */
    public void setRotatingOrFlushing(UInteger value) {
        set(9, value);
    }

    /**
     * Getter for <code>information_schema.INNODB_TABLESPACES_ENCRYPTION.ROTATING_OR_FLUSHING</code>.
     */
    public UInteger getRotatingOrFlushing() {
        return (UInteger) get(9);
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row10<UInteger, String, UInteger, UInteger, UInteger, UInteger, ULong, ULong, UInteger, UInteger> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    @Override
    public Row10<UInteger, String, UInteger, UInteger, UInteger, UInteger, ULong, ULong, UInteger, UInteger> valuesRow() {
        return (Row10) super.valuesRow();
    }

    @Override
    public Field<UInteger> field1() {
        return InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION.SPACE;
    }

    @Override
    public Field<String> field2() {
        return InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION.NAME;
    }

    @Override
    public Field<UInteger> field3() {
        return InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION.ENCRYPTION_SCHEME;
    }

    @Override
    public Field<UInteger> field4() {
        return InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION.KEYSERVER_REQUESTS;
    }

    @Override
    public Field<UInteger> field5() {
        return InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION.MIN_KEY_VERSION;
    }

    @Override
    public Field<UInteger> field6() {
        return InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION.CURRENT_KEY_VERSION;
    }

    @Override
    public Field<ULong> field7() {
        return InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION.KEY_ROTATION_PAGE_NUMBER;
    }

    @Override
    public Field<ULong> field8() {
        return InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION.KEY_ROTATION_MAX_PAGE_NUMBER;
    }

    @Override
    public Field<UInteger> field9() {
        return InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION.CURRENT_KEY_ID;
    }

    @Override
    public Field<UInteger> field10() {
        return InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION.ROTATING_OR_FLUSHING;
    }

    @Override
    public UInteger component1() {
        return getSpace();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public UInteger component3() {
        return getEncryptionScheme();
    }

    @Override
    public UInteger component4() {
        return getKeyserverRequests();
    }

    @Override
    public UInteger component5() {
        return getMinKeyVersion();
    }

    @Override
    public UInteger component6() {
        return getCurrentKeyVersion();
    }

    @Override
    public ULong component7() {
        return getKeyRotationPageNumber();
    }

    @Override
    public ULong component8() {
        return getKeyRotationMaxPageNumber();
    }

    @Override
    public UInteger component9() {
        return getCurrentKeyId();
    }

    @Override
    public UInteger component10() {
        return getRotatingOrFlushing();
    }

    @Override
    public UInteger value1() {
        return getSpace();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public UInteger value3() {
        return getEncryptionScheme();
    }

    @Override
    public UInteger value4() {
        return getKeyserverRequests();
    }

    @Override
    public UInteger value5() {
        return getMinKeyVersion();
    }

    @Override
    public UInteger value6() {
        return getCurrentKeyVersion();
    }

    @Override
    public ULong value7() {
        return getKeyRotationPageNumber();
    }

    @Override
    public ULong value8() {
        return getKeyRotationMaxPageNumber();
    }

    @Override
    public UInteger value9() {
        return getCurrentKeyId();
    }

    @Override
    public UInteger value10() {
        return getRotatingOrFlushing();
    }

    @Override
    public InnodbTablespacesEncryptionRecord value1(UInteger value) {
        setSpace(value);
        return this;
    }

    @Override
    public InnodbTablespacesEncryptionRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public InnodbTablespacesEncryptionRecord value3(UInteger value) {
        setEncryptionScheme(value);
        return this;
    }

    @Override
    public InnodbTablespacesEncryptionRecord value4(UInteger value) {
        setKeyserverRequests(value);
        return this;
    }

    @Override
    public InnodbTablespacesEncryptionRecord value5(UInteger value) {
        setMinKeyVersion(value);
        return this;
    }

    @Override
    public InnodbTablespacesEncryptionRecord value6(UInteger value) {
        setCurrentKeyVersion(value);
        return this;
    }

    @Override
    public InnodbTablespacesEncryptionRecord value7(ULong value) {
        setKeyRotationPageNumber(value);
        return this;
    }

    @Override
    public InnodbTablespacesEncryptionRecord value8(ULong value) {
        setKeyRotationMaxPageNumber(value);
        return this;
    }

    @Override
    public InnodbTablespacesEncryptionRecord value9(UInteger value) {
        setCurrentKeyId(value);
        return this;
    }

    @Override
    public InnodbTablespacesEncryptionRecord value10(UInteger value) {
        setRotatingOrFlushing(value);
        return this;
    }

    @Override
    public InnodbTablespacesEncryptionRecord values(UInteger value1, String value2, UInteger value3, UInteger value4, UInteger value5, UInteger value6, ULong value7, ULong value8, UInteger value9, UInteger value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached InnodbTablespacesEncryptionRecord
     */
    public InnodbTablespacesEncryptionRecord() {
        super(InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION);
    }

    /**
     * Create a detached, initialised InnodbTablespacesEncryptionRecord
     */
    public InnodbTablespacesEncryptionRecord(UInteger space, String name, UInteger encryptionScheme, UInteger keyserverRequests, UInteger minKeyVersion, UInteger currentKeyVersion, ULong keyRotationPageNumber, ULong keyRotationMaxPageNumber, UInteger currentKeyId, UInteger rotatingOrFlushing) {
        super(InnodbTablespacesEncryption.INNODB_TABLESPACES_ENCRYPTION);

        set(0, space);
        set(1, name);
        set(2, encryptionScheme);
        set(3, keyserverRequests);
        set(4, minKeyVersion);
        set(5, currentKeyVersion);
        set(6, keyRotationPageNumber);
        set(7, keyRotationMaxPageNumber);
        set(8, currentKeyId);
        set(9, rotatingOrFlushing);
    }
}

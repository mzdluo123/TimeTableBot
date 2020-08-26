/*
 * This file is generated by jOOQ.
 */
package io.github.mzdluo123.timetablebot.gen.information_schema.tables.records;


import io.github.mzdluo123.timetablebot.gen.information_schema.tables.UserVariables;
import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserVariablesRecord extends TableRecordImpl<UserVariablesRecord> implements Record4<String, String, String, String> {

    private static final long serialVersionUID = 1047511179;

    /**
     * Setter for <code>information_schema.user_variables.VARIABLE_NAME</code>.
     */
    public void setVariableName(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>information_schema.user_variables.VARIABLE_NAME</code>.
     */
    public String getVariableName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>information_schema.user_variables.VARIABLE_VALUE</code>.
     */
    public void setVariableValue(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>information_schema.user_variables.VARIABLE_VALUE</code>.
     */
    public String getVariableValue() {
        return (String) get(1);
    }

    /**
     * Setter for <code>information_schema.user_variables.VARIABLE_TYPE</code>.
     */
    public void setVariableType(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>information_schema.user_variables.VARIABLE_TYPE</code>.
     */
    public String getVariableType() {
        return (String) get(2);
    }

    /**
     * Setter for <code>information_schema.user_variables.CHARACTER_SET_NAME</code>.
     */
    public void setCharacterSetName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>information_schema.user_variables.CHARACTER_SET_NAME</code>.
     */
    public String getCharacterSetName() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<String, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<String, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return UserVariables.USER_VARIABLES.VARIABLE_NAME;
    }

    @Override
    public Field<String> field2() {
        return UserVariables.USER_VARIABLES.VARIABLE_VALUE;
    }

    @Override
    public Field<String> field3() {
        return UserVariables.USER_VARIABLES.VARIABLE_TYPE;
    }

    @Override
    public Field<String> field4() {
        return UserVariables.USER_VARIABLES.CHARACTER_SET_NAME;
    }

    @Override
    public String component1() {
        return getVariableName();
    }

    @Override
    public String component2() {
        return getVariableValue();
    }

    @Override
    public String component3() {
        return getVariableType();
    }

    @Override
    public String component4() {
        return getCharacterSetName();
    }

    @Override
    public String value1() {
        return getVariableName();
    }

    @Override
    public String value2() {
        return getVariableValue();
    }

    @Override
    public String value3() {
        return getVariableType();
    }

    @Override
    public String value4() {
        return getCharacterSetName();
    }

    @Override
    public UserVariablesRecord value1(String value) {
        setVariableName(value);
        return this;
    }

    @Override
    public UserVariablesRecord value2(String value) {
        setVariableValue(value);
        return this;
    }

    @Override
    public UserVariablesRecord value3(String value) {
        setVariableType(value);
        return this;
    }

    @Override
    public UserVariablesRecord value4(String value) {
        setCharacterSetName(value);
        return this;
    }

    @Override
    public UserVariablesRecord values(String value1, String value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserVariablesRecord
     */
    public UserVariablesRecord() {
        super(UserVariables.USER_VARIABLES);
    }

    /**
     * Create a detached, initialised UserVariablesRecord
     */
    public UserVariablesRecord(String variableName, String variableValue, String variableType, String characterSetName) {
        super(UserVariables.USER_VARIABLES);

        set(0, variableName);
        set(1, variableValue);
        set(2, variableType);
        set(3, characterSetName);
    }
}
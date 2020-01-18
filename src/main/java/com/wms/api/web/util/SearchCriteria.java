package com.wms.api.web.util;

import java.sql.Timestamp;

public class SearchCriteria {

    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;

    public SearchCriteria() {

    }

    public SearchCriteria(final String key, final String operation, final Object value) {
        super();
        this.orPredicate = false;
        this.key = key;
        this.operation = SearchOperation.getSimpleOperation(operation.charAt(0));
        this.value = value;
    }

    public SearchCriteria(final String orPredicate, final String key, final String operation, final Object value) {
        super();
        this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = SearchOperation.getSimpleOperation(operation.charAt(0));
        try {
            this.value = new java.text.SimpleDateFormat("dd_MM_yyyy").parse((String) value);
        } catch(Exception e) {
            this.value = value;
        }
        //this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getOperation() {
        return SearchOperation.getSimpleOperationString(operation);
    }

    public void setOperation(final SearchOperation operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public boolean isOrPredicate() {
        return orPredicate;
    }

    public void setOrPredicate(boolean orPredicate) {
        this.orPredicate = orPredicate;
    }

}
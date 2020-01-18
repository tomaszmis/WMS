package com.wms.api.web.util;

import java.util.regex.Pattern;

public enum SearchOperation {
    EQUALITY, NEGATION, GREATER_THAN, LESS_THAN, LIKE, STARTS_WITH, ENDS_WITH, CONTAINS;

    public static final String OR_PREDICATE_FLAG = ",";

    public static final Pattern filterPattern = Pattern.compile("(\\w+?)(:|<|>|!)(\\w+)(,|\\.)", Pattern.UNICODE_CHARACTER_CLASS);

    public static SearchOperation getSimpleOperation(final char input) {
        switch (input) {
            case ':':
                return EQUALITY;
            case '!':
                return NEGATION;
            case '>':
                return GREATER_THAN;
            case '<':
                return LESS_THAN;
            case '~':
                return LIKE;
            default:
                return null;
        }
    }

    public static String getSimpleOperationString(final SearchOperation input) {
        switch (input) {
            case EQUALITY:
                return ":";
            case NEGATION:
                return "!";
            case GREATER_THAN:
                return ">";
            case LESS_THAN:
                return "<";
            case LIKE:
                return "~";
            default:
                return null;
        }
    }
}
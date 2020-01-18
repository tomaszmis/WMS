package com.wms.api.helpers;

import com.wms.api.web.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpecificationsBuilder<T> {

    private final List<SearchCriteria> params;

    public SpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public SpecificationsBuilder with(String key, String operation, Object value, String predicate) {
        params.add(new SearchCriteria(predicate, key, operation, value));
        return this;
    }

    public Specification<T> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(FilterSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specifications.where(result)
                    .or(specs.get(i))
                    : Specifications.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
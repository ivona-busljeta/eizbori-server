package com.fer.infsus.eizbori.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PagingService {

    public <T> Map<String, Object> getPageConfig(T searchEntity, List<T> entities, int size) {
        int index = entities.indexOf(searchEntity);
        int page = Math.floorDiv(index, size);
        int totalItems = entities.size();

        int lowerBound = page * size;
        int upperBound = (page + 1) * size;

        if (upperBound > totalItems) {
            upperBound = totalItems;
        }

        Map<String, Object> pageConfig = new HashMap<>(3);
        pageConfig.put("page", page);
        pageConfig.put("lowerBound", lowerBound);
        pageConfig.put("upperBound", upperBound);

        return pageConfig;
    }
}

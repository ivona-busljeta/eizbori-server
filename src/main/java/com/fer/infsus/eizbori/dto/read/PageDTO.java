package com.fer.infsus.eizbori.dto.read;

import lombok.Getter;

import java.util.List;

@Getter
public class PageDTO<T> {
    private final List<T> items;
    private final int currentPage;
    private final int currentPageSize;
    private final long totalItems;
    private final int totalPages;

    public PageDTO(List<T> items, int page, int size, long totalSize) {
        this.items = items;
        this.currentPage = page;
        this.currentPageSize = size;
        this.totalItems = totalSize;
        this.totalPages = (int) Math.ceil((double) totalSize / size);
    }
}

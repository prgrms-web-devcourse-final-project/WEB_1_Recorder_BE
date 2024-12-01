package com.revup.page;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtil {
    public static final int SIZE = 10;
    public static final int GROUP_SIZE = 10;

    public static  <T> Page<T> createPage(List<T> content, int currentPage, long totalItems) {
        int totalPages = calculateTotalPages(totalItems);
        int startPage = calculateStartPage(currentPage);
        int endPage = calculateEndPage(startPage, totalPages);
        boolean prev = hasPrevious(startPage);
        boolean next = hasNext(endPage, totalItems);

        return new Page<>(
                content,
                currentPage,
                content.size(),
                startPage,
                endPage,
                prev,
                next
        );
    }


    private static int calculateTotalPages(long totalItems) {
        return (int) Math.ceil((double) totalItems / SIZE);
    }

    private static int calculateStartPage(int currentPage) {
        int currentGroup = currentPage / GROUP_SIZE;
        return currentGroup * GROUP_SIZE + 1;
    }

    private static int calculateEndPage(int startPage, int totalPages) {
        return Math.min(startPage + GROUP_SIZE - 1, totalPages);
    }


    private static boolean hasPrevious(int startPage) {
        return startPage > 1;
    }


    private static boolean hasNext(int endPage, long totalItems) {
        return (long) endPage * SIZE < totalItems;
    }


    public static long calculateOffset(int page) {
        return (long) page * SIZE;
    }


}

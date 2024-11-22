package com.revup.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Page <T> {
    private List<T> content;       // 현재 페이지 데이터
    private int currentPage;       // 현재 페이지 번호 (0부터 시작)
    private int size;              // 한 페이지의 크기
    private int startPage;         // 현재 그룹의 첫 페이지 번호
    private int endPage;           // 현재 그룹의 마지막 페이지 번호
    private boolean prev;          // 이전 그룹 존재 여부
    private boolean next;          // 다음 그룹 존재 여부


}

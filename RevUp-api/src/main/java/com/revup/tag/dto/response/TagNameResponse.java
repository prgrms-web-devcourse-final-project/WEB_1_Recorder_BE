package com.revup.tag.dto.response;

import com.revup.tag.entity.Tag;

public record TagNameResponse(String name) {
    public static TagNameResponse of(Tag tag) {
        return new TagNameResponse(tag.getName());
    }
}

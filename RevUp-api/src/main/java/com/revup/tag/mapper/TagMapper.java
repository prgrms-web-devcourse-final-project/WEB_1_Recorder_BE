package com.revup.tag.mapper;

import com.revup.tag.dto.request.TagRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TagMapper {
    public List<String> toNameList(List<TagRequest> tags) {
        if (tags == null || tags.isEmpty()) {
            return Collections.emptyList();
        }

        return tags.stream()
                .map(TagRequest::name)
                .toList();
    }
}

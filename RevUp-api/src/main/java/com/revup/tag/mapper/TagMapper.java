package com.revup.tag.mapper;

import com.revup.tag.dto.request.TagRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagMapper {
    public List<String> toString(List<TagRequest> tags) {
        return tags.stream()
                .map(TagRequest::name)
                .toList();
    }
}

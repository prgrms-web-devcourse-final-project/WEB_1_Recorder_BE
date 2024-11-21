package com.revup.tag.adaptor;

import com.revup.question.repository.TagRepository;
import com.revup.tag.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TagAdaptor {
    private final TagRepository tagRepository;

    public List<Tag> getTags(List<String> names) {
        List<Tag> tags = new ArrayList<>();
        for (String name : names) {
            Tag tag = tagRepository.findByName(name)
                    .orElseGet(() -> tagRepository.save(Tag.builder()
                            .name(name)
                            .build()
                    ));
            tags.add(tag);
        }
        return tags;
    }
}

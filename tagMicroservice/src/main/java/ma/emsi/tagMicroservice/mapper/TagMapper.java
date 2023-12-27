package ma.emsi.tagMicroservice.mapper;

import ma.emsi.tagMicroservice.dto.TagRequest;
import ma.emsi.tagMicroservice.dto.TagResponse;
import ma.emsi.tagMicroservice.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public TagResponse TagToTagResponse(Tag tag){
            return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
    public Tag TagRequestToTag(TagRequest tagRequest){
        return Tag.builder()
                .name(tagRequest.getName())
                .build();
    }
}

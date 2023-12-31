package ma.emsi.tagmicroservice.mapper;

import ma.emsi.tagmicroservice.dto.TagRequest;
import ma.emsi.tagmicroservice.dto.TagResponse;
import ma.emsi.tagmicroservice.entity.Tag;
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

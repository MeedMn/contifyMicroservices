package ma.emsi.tagmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.tagmicroservice.dto.TagRequest;
import ma.emsi.tagmicroservice.dto.TagResponse;
import ma.emsi.tagmicroservice.exceptions.NotFoundException;
import ma.emsi.tagmicroservice.mapper.TagMapper;
import ma.emsi.tagmicroservice.entity.Tag;
import ma.emsi.tagmicroservice.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    public TagResponse create(TagRequest tagRequest){
        Tag tag = tagMapper.tagRequestToTag(tagRequest);
        tagRepository.save(tag);
        return tagMapper.tagToTagResponse(tag);
    }
    public TagResponse update(Long id,TagRequest tagRequest){
        return tagRepository.findById(id)
                .map(t->{
                    t.setName(tagRequest.getName());
                    tagRepository.save(t);
                    return tagMapper.tagToTagResponse(t);
                }).orElseThrow(()->new NotFoundException("Tag Not Found"));
    }
    public String delete(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag Not found with this ID : " + id));
        if(tag != null)
            tagRepository.deleteById(id);
        return "Tag Deleted" ;
    }
    public List<TagResponse> getAll(){
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(tagMapper::tagToTagResponse).toList();
    }
    public TagResponse getById(long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag not found"));
        return tagMapper.tagToTagResponse(tag);
    }
    @Transactional(readOnly = true)
    public List<TagResponse> getByName(List<String> name){
        List<Tag> tags = tagRepository.findByNameIn(name);
        return tags.stream().map(tagMapper::tagToTagResponse).toList();
    }
}

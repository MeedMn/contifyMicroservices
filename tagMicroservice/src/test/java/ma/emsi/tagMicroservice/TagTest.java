package ma.emsi.tagMicroservice;

import ma.emsi.tagMicroservice.dto.TagRequest;
import ma.emsi.tagMicroservice.dto.TagResponse;
import ma.emsi.tagMicroservice.entity.Tag;
import ma.emsi.tagMicroservice.exceptions.NotFoundException;
import ma.emsi.tagMicroservice.mapper.TagMapper;
import ma.emsi.tagMicroservice.repository.TagRepository;
import ma.emsi.tagMicroservice.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    private Tag tag1;
    private Tag tag2;
    private TagRequest tagRequest1;
    private TagRequest tagRequest2;
    private TagResponse tagResponse1;
    private TagResponse tagResponse2;

    @BeforeEach
    void setUp() {
        tag1 = new Tag(1L, "Friend");
        tag2 = new Tag(2L, "Family");
        tagRequest1 = new TagRequest("Friend");
        tagRequest2 = new TagRequest("Family");
        tagResponse1 = new TagResponse(1L, "Friend");
        tagResponse2 = new TagResponse(2L, "Family");
    }

    @Test
    void testCreate() {

        when(tagMapper.TagRequestToTag(tagRequest1)).thenReturn(tag1);
        when(tagMapper.TagToTagResponse(tag1)).thenReturn(tagResponse1);

        TagResponse result = tagService.create(tagRequest1);

        verify(tagRepository, times(1)).save(tag1);

        assertEquals(tagResponse1, result);
    }

    @Test
    void testUpdate() {

        when(tagRepository.findById(1L)).thenReturn(java.util.Optional.of(tag1));

        when(tagMapper.TagToTagResponse(tag1)).thenReturn(tagResponse1);

        TagResponse result = tagService.update(1L, tagRequest2);

        verify(tagRepository, times(1)).save(tag1);

        assertEquals(tagResponse1, result);

        assertEquals(tagRequest2.getName(), tag1.getName());
    }

    @Test
    void testUpdateNotFound() {

        when(tagRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> tagService.update(1L, tagRequest2));

        verify(tagRepository, never()).save(any());
    }

    @Test
    void testDelete() {

        when(tagRepository.findById(1L)).thenReturn(java.util.Optional.of(tag1));

        String result = tagService.delete(1L);

        verify(tagRepository, times(1)).deleteById(1L);

        assertEquals("Tag Deleted", result);
    }

    @Test
    void testDeleteNotFound() {

        when(tagRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> tagService.delete(1L));

        verify(tagRepository, never()).deleteById(any());
    }

    @Test
    void testGetAll() {

        when(tagRepository.findAll()).thenReturn(List.of(tag1, tag2));

        when(tagMapper.TagToTagResponse(tag1)).thenReturn(tagResponse1);
        when(tagMapper.TagToTagResponse(tag2)).thenReturn(tagResponse2);

        List<TagResponse> result = tagService.getAll();

        assertEquals(List.of(tagResponse1, tagResponse2), result);
    }

    @Test
    void testGetById() {

        when(tagRepository.findById(1L)).thenReturn(java.util.Optional.of(tag1));

        when(tagMapper.TagToTagResponse(tag1)).thenReturn(tagResponse1);

        TagResponse result = tagService.getById(1L);

        assertEquals(tagResponse1, result);
    }

    @Test
    void testGetByIdNotFound() {

        when(tagRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> tagService.getById(1L));
    }

    @Test
    void testGetByName() {

        when(tagRepository.findByNameIn(List.of("Friend", "Family"))).thenReturn(List.of(tag1, tag2));

        when(tagMapper.TagToTagResponse(tag1)).thenReturn(tagResponse1);
        when(tagMapper.TagToTagResponse(tag2)).thenReturn(tagResponse2);

        List<TagResponse> result = tagService.getByName(List.of("Friend", "Family"));

        assertEquals(List.of(tagResponse1, tagResponse2), result);
    }
}
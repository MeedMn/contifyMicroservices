package ma.emsi.contactMicroservice.external;


import ma.emsi.contactMicroservice.dto.TagResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "tag-service/api/tag/")
public interface TagService {
    @GetMapping("getTagById/{id}")
    public Optional<TagResponse> getById(@PathVariable Long id);
}

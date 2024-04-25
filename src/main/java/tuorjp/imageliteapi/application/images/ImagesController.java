package tuorjp.imageliteapi.application.images;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tuorjp.imageliteapi.domain.entity.Images;
import tuorjp.imageliteapi.domain.enums.ImageExtension;
import tuorjp.imageliteapi.domain.service.ImageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("v1/images")
@Slf4j //logs
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService service;
    /*
    RequiredArgsConstructor faz essa implementação automaticamente
    public ImagesController(ImageService service) {
        this.service = service;
    }
     */

    @PostMapping
    public ResponseEntity save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("tags")List<String> tags
            ) throws IOException {
        log.info("Imagem recebida: name: {} size: {}", file.getOriginalFilename(), file.getSize());
        log.info("Content Type: {} ", file.getContentType());//image/png, image/jpeg
        log.info("Media Type: {} ", MediaType.valueOf(file.getContentType()));

        Images image = Images.builder()
                .name(name)
                .tags(String.join(",", tags)) //[tag1, tag2] -> "tag1, tag2"
                .size(file.getSize())
                .extension(ImageExtension.valueOf(MediaType.valueOf(file.getContentType())))
                .file(file.getBytes())
                .build();

        service.save(image);

        return ResponseEntity.ok().build();
    }
}

package tuorjp.imageliteapi.application.images;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tuorjp.imageliteapi.domain.entity.Images;
import tuorjp.imageliteapi.domain.enums.ImageExtension;
import tuorjp.imageliteapi.domain.service.ImageService;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1/images")
@Slf4j //logs
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService service;
    private final ImageMapper mapper;
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

        Images image = mapper.mapToImage(file, name, tags);

        Images savedImage = service.save(image);

        URI imageURI = buildImageURL(savedImage);

        return ResponseEntity.created(imageURI).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        var possiblieImage = service.findById(id);

        if (possiblieImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var image = possiblieImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());
        headers.setContentDispositionFormData(
                "inline; filename = \"" + image.getFileName() +"\"",
                image.getFileName()
        );

        return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
    }

    //método que cria a uri da imagem
    //http://localhost:8080/v1/images/idDaImagem
    private URI buildImageURL(Images image) {
        String imagePath = "/" + image.getId();

        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(imagePath)
                .build().toUri();
    }
}

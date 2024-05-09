package tuorjp.imageliteapi.application.images;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import tuorjp.imageliteapi.domain.entity.Images;
import tuorjp.imageliteapi.domain.enums.ImageExtension;

import java.io.IOException;
import java.util.List;

@Component
public class ImageMapper {
    public Images mapToImage(MultipartFile file, String name, List<String> tags) throws IOException {
        return Images.builder()
                .name(name)
                .tags(String.join(",", tags)) //[tag1, tag2] -> "tag1, tag2"
                .size(file.getSize())
                .extension(ImageExtension.valueOf(MediaType.valueOf(file.getContentType())))
                .file(file.getBytes())
                .build();
    }

    public ImageDTO imageToDTO(Images image, String url) {
        return ImageDTO.builder()
                .url(url)
                .extension(image.getExtension().name())
                .name(image.getName())
                .size(image.getSize())
                .uploadDate(image.getUploadDate().toLocalDate())
                .build();
    }
}

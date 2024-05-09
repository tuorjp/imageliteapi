package tuorjp.imageliteapi.infra.repository.specs;

import org.springframework.data.jpa.domain.Specification;
import tuorjp.imageliteapi.domain.entity.Images;
import tuorjp.imageliteapi.domain.enums.ImageExtension;

public class ImageSpecs {
    //todos os métodos vão ser estáticos, não faz sentido alguém instanciar essa classe
    //basta importar tudo de forma estática
    //import tuorjp.imageliteapi.infra.repository.specs
    private ImageSpecs(){};

    public static Specification<Images> extensionEqual(ImageExtension extension) {
        return (root, query1, criteriaBuilder) -> criteriaBuilder.equal(root.get("extension"), extension);
    }

    public static Specification<Images> nameLike(String name) {
        return (root, query1, cb) -> cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");
    }

    public static Specification<Images> tagsLike(String tags) {
        return (root, query1, cb) -> cb.like(cb.upper(root.get("tags")), "%" + tags.toUpperCase() + "%");
    }
}

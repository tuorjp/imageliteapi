package tuorjp.imageliteapi.infra.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;
import tuorjp.imageliteapi.domain.entity.Images;
import tuorjp.imageliteapi.domain.enums.ImageExtension;

import java.util.List;

/*JpaRepository recebe a classe que vai ser manipulada
*e o tipo do Id que no caso é String. JpaRepository tem vários
* métodos como findAll, saveAndFlush, dentre outros. Basta
* declarar a interface, sem criar nenhum método
*/

public interface ImageRepository extends JpaRepository<Images, String>, JpaSpecificationExecutor<Images> {
    /*
    * SELECT * FROM IMAGE WHERE 1 = 1 AND EXTENSION = 'PNG' AND (NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY')
    */
    default List<Images> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {
        //SELECT * FROM IMAGE WHERE 1 = 1
        Specification<Images> conjunction = (root, q, criteriaBuilder) -> criteriaBuilder.conjunction();
        Specification<Images> spec = Specification.where(conjunction);

        if(extension != null) {
            //AND EXTENSION = 'PNG'
            Specification<Images> extensionEqual = (root, query1, criteriaBuilder) -> criteriaBuilder.equal(root.get("extension"), extension);
            spec = spec.and(extensionEqual);
        }

        if (StringUtils.hasText(query)) {
            //AND (NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY')
            //RIVER => %RI%
            //root.get recebe o nome do atributo da entidade e não o nome da coluna no banco de dados
            Specification<Images> nameLike = (root, query1, cb) -> cb.like(cb.upper(root.get("name")), "%" + query.toUpperCase() + "%");
            Specification<Images> tagsLike = (root, query1, cb) -> cb.like(cb.upper(root.get("tags")), "%" + query.toUpperCase() + "%");

            Specification<Images> nameOrTagsLike = Specification.anyOf(nameLike, tagsLike);
            spec = spec.and(nameOrTagsLike);
        }

        return findAll(spec);
    }
}

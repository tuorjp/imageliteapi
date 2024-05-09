package tuorjp.imageliteapi.infra.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;
import tuorjp.imageliteapi.domain.entity.Images;
import tuorjp.imageliteapi.domain.enums.ImageExtension;
import tuorjp.imageliteapi.infra.repository.specs.GenericSpecs;
import tuorjp.imageliteapi.infra.repository.specs.ImageSpecs;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.*;
import static tuorjp.imageliteapi.infra.repository.specs.GenericSpecs.conjunction;
import static tuorjp.imageliteapi.infra.repository.specs.ImageSpecs.*;

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
        Specification<Images> spec = conjunction();

        if(extension != null) {
            //AND EXTENSION = 'PNG'
            spec = spec.and(extensionEqual(extension));
        }

        if (StringUtils.hasText(query)) {
            //AND (NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY')
            //RIVER => %RI%
            //root.get recebe o nome do atributo da entidade e não o nome da coluna no banco de dados

            spec = spec.and(anyOf(nameLike(query), tagsLike(query)));
        }

        return findAll(spec);
    }
}

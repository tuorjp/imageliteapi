package tuorjp.imageliteapi.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tuorjp.imageliteapi.domain.entity.Images;

/*JpaRepository recebe a classe que vai ser manipulada
*e o tipo do Id que no caso é String. JpaRepository tem vários
* métodos como findAll, saveAndFlush, dentre outros. Basta
* declarar a interface, sem criar nenhum método
*/

public interface ImageRepository extends JpaRepository<Images, String> {
}

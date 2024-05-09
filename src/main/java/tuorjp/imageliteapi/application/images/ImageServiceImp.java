package tuorjp.imageliteapi.application.images;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuorjp.imageliteapi.domain.entity.Images;
import tuorjp.imageliteapi.domain.enums.ImageExtension;
import tuorjp.imageliteapi.domain.service.ImageService;
import tuorjp.imageliteapi.infra.repository.ImageRepository;

import java.util.List;
import java.util.Optional;

/*
* O repository extende o JpaRepository que apenas modifica os dados no banco de dados
* O service é a camada que manipula a entidade (responsável por toda a lógica de negócio)
* Dentro do repository é só a lógica de modificação dos dados no banco
*/

@Service
@RequiredArgsConstructor
//construtor com os argumentos obrigatórios (o lombok modifica a classe com base nessas anotações em tempo de execução, olhar em target o código compilado)
public class ImageServiceImp implements ImageService {
    @Override
    public List<Images> search(ImageExtension extension, String query) {
        return repository.findByExtensionAndNameOrTagsLike(extension, query);
    }

    //injetando o repository para implementar os métodos
    private final ImageRepository repository;

    //Transactional abre uma transação com o banco de dados
    @Override
    @Transactional
    public Images save(Images image) {
        return repository.save(image);
    }

    @Override
    public Optional<Images> findById(String id) {
        return repository.findById(id);
    }
}

package tuorjp.imageliteapi.domain.service;

import tuorjp.imageliteapi.domain.entity.Images;

import java.util.Optional;

//camada de regras de negócio
//interface com declaração dos métodos a serem implementados em ImageServiceImp (implementação)
public interface ImageService {
    Images save(Images image);

    Optional<Images> findById(String id);
}

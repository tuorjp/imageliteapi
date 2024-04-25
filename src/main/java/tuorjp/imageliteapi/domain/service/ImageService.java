package tuorjp.imageliteapi.domain.service;

import tuorjp.imageliteapi.domain.entity.Images;

//camada de regras de negócio
//interface com declaração dos métodos a serem implementados em ImageServiceImp (implementação)
public interface ImageService {
    Images save(Images image);
}

package tuorjp.imageliteapi.infra.repository.specs;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecs {
    GenericSpecs(){}

    public static <T>Specification<T> conjunction () {
        return (root, q, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
}

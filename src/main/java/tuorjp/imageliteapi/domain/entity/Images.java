package tuorjp.imageliteapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tuorjp.imageliteapi.domain.enums.ImageExtension;

import java.time.LocalDateTime;

/*MAPEAMENTO OBJETO RELACIONAL:

@Entity, @Table, @Column podem ou não receber argumentos, se não o nome da Entidade, tabela e coluna vão ser os mesmos da Classe ou atributo a que se referem
@EntityListeners audita a data que foi feita a inserção no banco, precisa ativar na classe main pra funcionar
@Data cria os getters, setters, toString, equalsAndHashCode e um construtor dos campos obrigatórios junto dos @NoArgsConstructor e @AllArgsConstructor
@Builder precisa do @AllArgsConstructor para funcionar
*/

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column
    private Long size;

    @Enumerated(EnumType.STRING)//grava o nome que está dentro do enum
    //@Enumerated(EnumType.ORDINAL)//grava o índice que está dentro do enum
    private ImageExtension extension;

    @Column
    @CreatedDate
    private LocalDateTime uploadDate;

    @Column
    private String tags;

    @Column
    @Lob //diz que é um arquivo
    private byte[] file;

    public String getFileName() {
        return getName().concat(".").concat(getExtension().name());
    }
}

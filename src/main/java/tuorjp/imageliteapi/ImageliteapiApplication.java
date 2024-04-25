package tuorjp.imageliteapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import tuorjp.imageliteapi.domain.entity.Images;
import tuorjp.imageliteapi.domain.enums.ImageExtension;
import tuorjp.imageliteapi.infra.repository.ImageRepository;

@SpringBootApplication
@EnableJpaAuditing
public class ImageliteapiApplication {
	/*CommandLineRunner roda um trecho de código ao iniciar a aplicação*/
	@Bean
	public CommandLineRunner commandLineRunner(@Autowired ImageRepository repository) {
		return args -> {
			Images image = Images.builder()
					.extension(ImageExtension.PNG)
					.name("Imagem teste")
					.tags("teste")
					.size(1000L)
					.build();
		repository.save(image);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ImageliteapiApplication.class, args);
	}

}

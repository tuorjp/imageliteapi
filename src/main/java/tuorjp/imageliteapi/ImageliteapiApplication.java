package tuorjp.imageliteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImageliteapiApplication {
	/*
CommandLineRunner roda um trecho de código ao iniciar a aplicação, nesse caso uma inserção teste
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
*/

    public static void main(String[] args) {
		SpringApplication.run(ImageliteapiApplication.class, args);
	}

}

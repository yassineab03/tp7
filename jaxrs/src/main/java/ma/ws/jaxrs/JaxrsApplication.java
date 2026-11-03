package ma.ws.jaxrs;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ma.ws.jaxrs.entities.*;
import ma.ws.jaxrs.repositories.CompteRepository;
import java.util.Date;
@SpringBootApplication
public class JaxrsApplication {
 public static void main(String[] args) { SpringApplication.run(JaxrsApplication.class,args); }
 @Bean CommandLineRunner start(CompteRepository repository) {
  return args -> {
   repository.save(new Compte(null,Math.random()*9000,new Date(),TypeCompte.EPARGNE));
   repository.save(new Compte(null,Math.random()*9000,new Date(),TypeCompte.COURANT));
   repository.save(new Compte(null,Math.random()*9000,new Date(),TypeCompte.EPARGNE));
   repository.findAll().forEach(System.out::println);
  };
 }
}

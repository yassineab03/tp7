package ma.ws.jaxrs.controllers;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import ma.ws.jaxrs.entities.Compte;
import ma.ws.jaxrs.repositories.CompteRepository;
import org.springframework.stereotype.Component;
import java.util.List;
@Component @Path("/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CompteRestJaxRSAPI {
 private final CompteRepository compteRepository;
 public CompteRestJaxRSAPI(CompteRepository compteRepository) { this.compteRepository=compteRepository; }
 @GET @Path("/comptes")
 public List<Compte> getComptes() { return compteRepository.findAll(); }
 @GET @Path("/comptes/{id}")
 public Compte getCompte(@PathParam("id") Long id) {
  return compteRepository.findById(id).orElseThrow(NotFoundException::new);
 }
 @POST @Path("/comptes") @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
 public Response addCompte(Compte compte, @Context UriInfo uriInfo) {
  compte.setId(null);
  Compte saved=compteRepository.save(compte);
  return Response.created(uriInfo.getAbsolutePathBuilder().path(saved.getId().toString()).build()).entity(saved).build();
 }
 @PUT @Path("/comptes/{id}") @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
 public Compte updateCompte(@PathParam("id") Long id, Compte compte) {
  Compte existing=getCompte(id);
  existing.setSolde(compte.getSolde());
  existing.setDateCreation(compte.getDateCreation());
  existing.setType(compte.getType());
  return compteRepository.save(existing);
 }
 @DELETE @Path("/comptes/{id}")
 public Response deleteCompte(@PathParam("id") Long id) {
  compteRepository.delete(getCompte(id));
  return Response.noContent().build();
 }
}


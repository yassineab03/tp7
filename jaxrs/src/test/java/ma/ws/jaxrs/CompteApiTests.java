package ma.ws.jaxrs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.net.URI;
import java.net.http.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompteApiTests {
 @LocalServerPort int port;
 private final HttpClient client=HttpClient.newHttpClient();
 private HttpResponse<String> call(String method,String path,String media,String body) throws Exception {
  return client.send(HttpRequest.newBuilder(URI.create("http://localhost:"+port+path))
   .header("Accept",media).header("Content-Type",media)
   .method(method,body==null?HttpRequest.BodyPublishers.noBody():HttpRequest.BodyPublishers.ofString(body))
   .build(),HttpResponse.BodyHandlers.ofString());
 }
 @Test void crudJsonEtXml() throws Exception {
  String base="/banque/comptes";
  var list=call("GET",base,"application/json",null);
  assertEquals(200,list.statusCode()); assertTrue(list.body().contains("EPARGNE"));
  var xmlList=call("GET",base,"application/xml",null);
  assertEquals(200,xmlList.statusCode()); assertTrue(xmlList.body().contains("<compte>"));
  for (String media:new String[]{"application/json","application/xml"}) {
   String body=media.endsWith("json")?"{\"solde\":1200,\"dateCreation\":\"2026-11-03\",\"type\":\"COURANT\"}":
    "<compte><solde>1200</solde><dateCreation>2026-11-03</dateCreation><type>COURANT</type></compte>";
   var created=call("POST",base,media,body);
   assertEquals(201,created.statusCode(),created.body());
   String location=URI.create(created.headers().firstValue("Location").orElseThrow()).getPath();
   assertEquals(200,call("GET",location,media,null).statusCode());
   var updated=call("PUT",location,media,body.replace("1200","2500"));
   assertEquals(200,updated.statusCode()); assertTrue(updated.body().contains("2500"));
   assertEquals(204,call("DELETE",location,media,null).statusCode());
   assertEquals(404,call("GET",location,media,null).statusCode());
   assertEquals(404,call("PUT",location,media,body).statusCode());
   assertEquals(404,call("DELETE",location,media,null).statusCode());
  }
  assertEquals(200,call("GET","/h2-console/","text/html",null).statusCode());
 }
}

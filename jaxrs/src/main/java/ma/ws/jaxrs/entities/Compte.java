package ma.ws.jaxrs.entities;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
@Entity @Data @NoArgsConstructor @AllArgsConstructor
@XmlRootElement(name="compte") @XmlAccessorType(XmlAccessType.FIELD)
public class Compte {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private double solde;
 @Temporal(TemporalType.DATE) @JsonFormat(pattern="yyyy-MM-dd") @XmlSchemaType(name="date")
 private Date dateCreation;
 @Enumerated(EnumType.ORDINAL) private TypeCompte type;
}

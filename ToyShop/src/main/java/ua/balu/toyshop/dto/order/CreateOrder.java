package ua.balu.toyshop.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.dto.post.PostProfile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrder implements Convertible {
    @NotNull
     private Set<Long> postsId;
    @NotBlank(message = "Address cannot be empty")
     private String address;
     private String note;
}

package ua.balu.toyshop.dto.category;

import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoryProfile implements Convertible {
    private int id;
    @NotBlank(message = "Category cannot be empty")
    @Size(min = 4,max =50,message = "Category name must be between 5 and 50 chars ")
    private String type;
}

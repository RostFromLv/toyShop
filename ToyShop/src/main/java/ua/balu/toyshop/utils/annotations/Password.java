package ua.balu.toyshop.utils.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "Password should have english Upper,down case letter,digits,1 special(!@#$%^&*()_-+=`;:'\") symbol and have length more than 8";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

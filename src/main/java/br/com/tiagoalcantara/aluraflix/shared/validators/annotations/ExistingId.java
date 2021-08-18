package br.com.tiagoalcantara.aluraflix.shared.validators.annotations;

import br.com.tiagoalcantara.aluraflix.shared.validators.ExistingIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { ExistingIdValidator.class })
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingId {
    String message() default "id inexistente";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String fieldName() default "id";

    Class<?> domainClass();
}

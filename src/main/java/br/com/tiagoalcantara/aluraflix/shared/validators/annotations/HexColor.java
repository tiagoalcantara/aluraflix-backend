package br.com.tiagoalcantara.aluraflix.shared.validators.annotations;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$")
@ConstraintComposition(CompositionType.AND)
@ReportAsSingleViolation
public @interface HexColor {
    String message() default "deve ser uma cor hexadecimal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

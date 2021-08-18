package br.com.tiagoalcantara.aluraflix.shared.validators;

import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$", message = "deve ser uma cor hexadecimal")
public @interface HexColor {}

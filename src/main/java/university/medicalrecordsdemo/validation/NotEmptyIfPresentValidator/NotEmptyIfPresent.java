package university.medicalrecordsdemo.validation.NotEmptyIfPresentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotEmptyIfPresentValidator.class)
public @interface NotEmptyIfPresent {
    String message() default "Field must not be empty if present";

    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};

    String field();

    String minParam();

    String maxParam();
}

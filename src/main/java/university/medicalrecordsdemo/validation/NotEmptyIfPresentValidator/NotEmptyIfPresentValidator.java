package university.medicalrecordsdemo.validation.NotEmptyIfPresentValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyIfPresentValidator implements ConstraintValidator<NotEmptyIfPresent, String> {
    
    private String minParam;
    private String maxParam;

    @Override
    public void initialize(NotEmptyIfPresent constraintAnnotation) {
        this.minParam = constraintAnnotation.minParam();
        this.maxParam = constraintAnnotation.maxParam();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        int minLength = getMinLengthFromEntity();
        int maxLength = getMaxLengthFromEntity();
        int length = value.length();

        return length == 0 || (length >= minLength && length <= maxLength);
    }

    private int getMinLengthFromEntity() {
        return Integer.parseInt(minParam);
    }

    private int getMaxLengthFromEntity() {
        return Integer.parseInt(maxParam);
    }
}

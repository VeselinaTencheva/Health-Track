package university.medicalrecordsdemo.validation;

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
            return true;  // If the value is null, it's considered valid
        }

        // Perform validation based on the parameters provided
        // Retrieve the min and max values dynamically from the entity
        // Compare the length of the value with the dynamically obtained min and max values
        int minLength = getMinLengthFromEntity();
        int maxLength = getMaxLengthFromEntity();
        int length = value.length();

        return length == 0 || (length >= minLength && length <= maxLength);
    }

    // Method to dynamically retrieve the min value from the entity
    private int getMinLengthFromEntity() {
        // Retrieve the min value from the entity using the parameter provided
        // Replace this with your actual logic to fetch the min value from the entity
        return Integer.parseInt(minParam);
    }

    // Method to dynamically retrieve the max value from the entity
    private int getMaxLengthFromEntity() {
        // Retrieve the max value from the entity using the parameter provided
        // Replace this with your actual logic to fetch the max value from the entity
        return Integer.parseInt(maxParam);
    }
}

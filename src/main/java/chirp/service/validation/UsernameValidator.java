package chirp.service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {

	@Override
	public void initialize(Username annotation) {
	}

	@Override
	public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
		return (object != null && object.matches("[a-zA-Z_.]+"));
	}

}

package com.mpxds.mpbasic.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import org.primefaces.validate.bean.ClientConstraint;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ClientConstraint(resolvedBy = MpSkuClientValidationConstraint.class)
@Pattern(regexp = "([a-zA-Z]{2}\\d{4,18})?")
public @interface MpSKU {

	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "{com.mpxds.constraints.MpSKU.message}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
}
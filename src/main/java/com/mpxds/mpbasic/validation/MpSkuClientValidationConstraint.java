package com.mpxds.mpbasic.validation;

import java.util.HashMap;
import java.util.Map;

import javax.validation.metadata.ConstraintDescriptor;

import org.primefaces.validate.bean.ClientValidationConstraint;

public class MpSkuClientValidationConstraint implements ClientValidationConstraint {

	public static final String MESSAGE_ID = "{com.mpxds.constraints.MpSKU.message}";
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> getMetadata(ConstraintDescriptor constraintDescriptor) {
		Map<String, Object> metadata = new HashMap<String, Object>();
		Map attrs = constraintDescriptor.getAttributes();
		
		Object message = attrs.get("message");
		
		if (!message.equals(MESSAGE_ID)) {
			metadata.put("data-msg-sku", message);
		}
		
		return metadata;
	}

	@Override
	public String getValidatorId() {
		return MpSKU.class.getSimpleName();
	}

}

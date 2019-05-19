package com.kitchen.view;

import com.vaadin.data.validator.RegexpValidator;

@SuppressWarnings("serial")
public class NumberValidator extends RegexpValidator{

	private static final String PATTERN = "[-]?[0-9]*\\.?,?[0-9]+";

    /**
     * Creates a validator for checking that a string is a syntactically valid
     * e-mail address.
     *
     * @param errorMessage
     *            the message to display in case the value does not validate.
     */
    public NumberValidator(String errorMessage) {
        super(errorMessage, PATTERN, true);
    }

}

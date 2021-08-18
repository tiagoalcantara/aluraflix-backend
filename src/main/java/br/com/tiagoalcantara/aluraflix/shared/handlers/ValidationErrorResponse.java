package br.com.tiagoalcantara.aluraflix.shared.handlers;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {
    private List<String> globalErrors = new ArrayList<>();
    private List<FieldErrorResponse> fieldsErrors = new ArrayList<>();
    private Integer count = 0;

    public void addGlobalError(String message){
        globalErrors.add(message);
        count = this.count + 1;
    }

    public void addFieldError(String field, String message){
        fieldsErrors.add(new FieldErrorResponse(field, message));
        count = this.count + 1;
    }

    public List<String> getGlobalErrors() {
        return globalErrors;
    }

    public List<FieldErrorResponse> getFieldsErrors() {
        return fieldsErrors;
    }

    public Integer getCount() {
        return count;
    }
}

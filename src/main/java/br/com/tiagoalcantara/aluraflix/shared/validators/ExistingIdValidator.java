package br.com.tiagoalcantara.aluraflix.shared.validators;

import br.com.tiagoalcantara.aluraflix.shared.validators.annotations.ExistingId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistingIdValidator implements ConstraintValidator<ExistingId, Object> {
    private String domainAttribute;
    private Class<?> aClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(ExistingId constraintAnnotation) {
        domainAttribute = constraintAnnotation.fieldName();
        aClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if(object == null) return true;

        Query query = manager.createQuery(String.format("SELECT 1 FROM %s o where %s = :value", aClass.getName(), domainAttribute));
        query.setParameter("value", object);

        List<?> list = query.getResultList();
        return !list.isEmpty();
    }
}

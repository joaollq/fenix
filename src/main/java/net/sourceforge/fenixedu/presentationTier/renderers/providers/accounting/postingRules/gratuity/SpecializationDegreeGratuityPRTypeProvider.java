package net.sourceforge.fenixedu.presentationTier.renderers.providers.accounting.postingRules.gratuity;

import java.util.Arrays;

import net.sourceforge.fenixedu.domain.accounting.postingRules.gratuity.SpecializationDegreeGratuityByAmountPerEctsPR;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.BiDirectionalConverter;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class SpecializationDegreeGratuityPRTypeProvider implements DataProvider {

    @Override
    public Object provide(Object source, Object currentValue) {
        return Arrays.asList(SpecializationDegreeGratuityByAmountPerEctsPR.class);
    }

    @Override
    public Converter getConverter() {
        return new BiDirectionalConverter() {

            /**
	     * 
	     */
            private static final long serialVersionUID = 1L;

            @Override
            public String deserialize(Object target) {
                return ((Class<?>) target).getName();
            }

            @Override
            public Object convert(Class type, Object target) {
                try {
                    return Class.forName((String) target);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

}

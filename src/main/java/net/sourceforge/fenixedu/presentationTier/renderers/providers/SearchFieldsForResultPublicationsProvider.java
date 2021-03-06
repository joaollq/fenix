package net.sourceforge.fenixedu.presentationTier.renderers.providers;

import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;
import pt.utl.ist.fenix.tools.file.FileSearchCriteria.SearchField;

public class SearchFieldsForResultPublicationsProvider implements DataProvider {

    @Override
    public Converter getConverter() {
        return new Converter() {
            @Override
            public Object convert(Class type, Object value) {
                return SearchField.valueOf((String) value);
            }

        };
    }

    @Override
    public Object provide(Object source, Object currentValue) {
        return SearchField.getSearchFieldsInResearchPublications();
    }

}

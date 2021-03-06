package net.sourceforge.fenixedu.presentationTier.renderers.providers.coordinator.tutor;

import net.sourceforge.fenixedu.dataTransferObject.coordinator.tutor.TutorshipManagementByEntryYearBean;
import net.sourceforge.fenixedu.domain.Teacher;
import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyArrayConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class StudentsGivenTutorAndEntryYearDataProvider implements DataProvider {

    @Override
    public Object provide(Object source, Object currentValue) {
        TutorshipManagementByEntryYearBean bean = (TutorshipManagementByEntryYearBean) source;

        Teacher teacher = bean.getTeacher();

        return teacher.getActiveTutorshipsByStudentsEntryYear(bean.getExecutionYear());
    }

    @Override
    public Converter getConverter() {
        return new DomainObjectKeyArrayConverter();
    }
}

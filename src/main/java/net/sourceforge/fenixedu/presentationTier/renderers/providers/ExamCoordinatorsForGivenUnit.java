package net.sourceforge.fenixedu.presentationTier.renderers.providers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.fenixedu.domain.ExecutionYear;
import net.sourceforge.fenixedu.domain.organizationalStructure.Unit;
import net.sourceforge.fenixedu.domain.vigilancy.ExamCoordinator;
import net.sourceforge.fenixedu.domain.vigilancy.VigilantGroup;
import net.sourceforge.fenixedu.presentationTier.Action.vigilancy.VigilantGroupBean;

import org.apache.commons.beanutils.BeanComparator;

import pt.ist.fenixWebFramework.rendererExtensions.converters.DomainObjectKeyArrayConverter;
import pt.ist.fenixWebFramework.renderers.DataProvider;
import pt.ist.fenixWebFramework.renderers.components.converters.Converter;

public class ExamCoordinatorsForGivenUnit implements DataProvider {

    @Override
    public Object provide(Object source, Object currentValue) {
        VigilantGroupBean bean = (VigilantGroupBean) source;
        ExecutionYear currentYear = ExecutionYear.readCurrentExecutionYear();
        Unit unit = bean.getSelectedUnit();
        List<ExamCoordinator> coordinators = new ArrayList<ExamCoordinator>();
        if (unit != null) {
            coordinators.addAll(unit.getExamCoordinatorsForGivenYear(currentYear));
        }
        VigilantGroup group = bean.getSelectedVigilantGroup();
        if (group != null) {
            coordinators.removeAll(group.getExamCoordinators());
        }

        Collections.sort(coordinators, new BeanComparator("person.name"));
        return coordinators;

    }

    @Override
    public Converter getConverter() {
        return new DomainObjectKeyArrayConverter();
    }

}

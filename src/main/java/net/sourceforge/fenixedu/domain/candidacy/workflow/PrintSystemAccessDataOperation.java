package net.sourceforge.fenixedu.domain.candidacy.workflow;

import java.util.Set;

import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.candidacy.Candidacy;
import net.sourceforge.fenixedu.domain.candidacy.CandidacyOperationType;
import net.sourceforge.fenixedu.domain.person.RoleType;

public class PrintSystemAccessDataOperation extends CandidacyOperation {

    public PrintSystemAccessDataOperation(Set<RoleType> roleTypes, Candidacy candidacy) {
        super(roleTypes, candidacy);
    }

    @Override
    protected void internalExecute() {
        // nothing to be done

    }

    @Override
    public CandidacyOperationType getType() {
        return CandidacyOperationType.PRINT_SYSTEM_ACCESS_DATA;
    }

    @Override
    public boolean isInput() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public boolean isAuthorized(Person person) {
        // Disable operation
        return false;// super.isAuthorized(person) && person ==
        // getCandidacy().getPerson();
    }

}
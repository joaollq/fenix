package net.sourceforge.fenixedu.domain.personnelSection.contracts;

import net.sourceforge.fenixedu.domain.RootDomainObject;
import pt.ist.fenixframework.Atomic;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class ContractSituation extends ContractSituation_Base {

    public ContractSituation(final String giafId, final MultiLanguageString name, final Boolean endSituation,
            final Boolean serviceExemption) {
        super();
        setRootDomainObject(RootDomainObject.getInstance());
        setGiafId(giafId);
        setName(name);
        setEndSituation(endSituation);
        setServiceExemption(serviceExemption);
        setInExercise(!endSituation);
        setHasMandatoryCredits(Boolean.TRUE);
        setGiveCredits(Boolean.FALSE);
        setMustHaveAssociatedExemption(Boolean.FALSE);
    }

    @Atomic
    public void edit(final MultiLanguageString name, final Boolean endSituation) {
        setName(name);
        setEndSituation(endSituation);
    }

}

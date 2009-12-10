package net.sourceforge.fenixedu.domain.serviceRequests.documentRequests;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sourceforge.fenixedu.dataTransferObject.serviceRequests.AcademicServiceRequestBean;
import net.sourceforge.fenixedu.dataTransferObject.serviceRequests.DocumentRequestCreateBean;
import net.sourceforge.fenixedu.domain.accounting.EventType;
import net.sourceforge.fenixedu.domain.accounting.events.serviceRequests.RegistryDiplomaRequestEvent;
import net.sourceforge.fenixedu.domain.degreeStructure.CycleType;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.domain.serviceRequests.AcademicServiceRequestSituationType;

public class RegistryDiplomaRequest extends RegistryDiplomaRequest_Base {

    public RegistryDiplomaRequest() {
	super();
    }

    public RegistryDiplomaRequest(final DocumentRequestCreateBean bean) {
	this();
	super.init(bean);
	super.setRequestedCycle(bean.getRequestedCycle());
	checkParameters(bean);
	if (isPayedUponCreation() && !isFree()) {
	    RegistryDiplomaRequestEvent.create(getAdministrativeOffice(), getRegistration().getPerson(), this);
	}
	// setDiplomaSupplement(new DiplomaSupplementRequest(bean));
    }

    // public String getFamilyNames() {
    // return getDiplomaSupplement().getFamilyNames();
    // }
    //
    // public String getGivenNames() {
    // return getDiplomaSupplement().getGivenNames();
    // }

    @Override
    protected void checkParameters(DocumentRequestCreateBean bean) {
	if (bean.getRequestedCycle() == null)
	    throw new DomainException("error.registryDiploma.requestedCycleMustBeGiven");
	if (!getDegreeType().getCycleTypes().contains(bean.getRequestedCycle()))
	    throw new DomainException("error.registryDiploma.requestedCycleTypeIsNotAllowedForGivenStudentCurricularPlan");
	if (getRegistration().getDiplomaRequest(bean.getRequestedCycle()) != null)
	    throw new DomainException("error.registryDiploma.alreadyHasDiplomaRequest");
	if (getRegistration().getRegistryDiplomaRequest(bean.getRequestedCycle()) != this)
	    throw new DomainException("error.registryDiploma.alreadyRequested");
    }

    @Override
    public DocumentRequestType getDocumentRequestType() {
	return DocumentRequestType.REGISTRY_DIPLOMA_REQUEST;
    }

    @Override
    public String getDocumentTemplateKey() {
	return getClass().getName();
    }

    @Override
    public EventType getEventType() {
	switch (getDegreeType()) {
	case BOLONHA_DEGREE:
	    return EventType.BOLONHA_DEGREE_REGISTRY_DIPLOMA_REQUEST;
	case BOLONHA_MASTER_DEGREE:
	    return EventType.BOLONHA_MASTER_DEGREE_REGISTRY_DIPLOMA_REQUEST;
	case BOLONHA_INTEGRATED_MASTER_DEGREE:
	    return (getRequestedCycle() == CycleType.FIRST_CYCLE) ? EventType.BOLONHA_DEGREE_REGISTRY_DIPLOMA_REQUEST
		    : EventType.BOLONHA_MASTER_DEGREE_REGISTRY_DIPLOMA_REQUEST;
	case BOLONHA_ADVANCED_FORMATION_DIPLOMA:
	    return EventType.BOLONHA_ADVANCED_FORMATION_REGISTRY_DIPLOMA_REQUEST;
	default:
	    throw new DomainException("error.registryDiploma.notAvailableForGivenDegreeType");
	}
    }

    @Override
    protected List<AcademicServiceRequestSituationType> getConcludedSituationAcceptedSituationsTypes() {
	return Collections.unmodifiableList(Arrays.asList(AcademicServiceRequestSituationType.DELIVERED,
		AcademicServiceRequestSituationType.SENT_TO_EXTERNAL_ENTITY));
    }

    @Override
    protected List<AcademicServiceRequestSituationType> getProcessingSituationAcceptedSituationsTypes() {
	return Collections.unmodifiableList(Arrays.asList(AcademicServiceRequestSituationType.CANCELLED,
		AcademicServiceRequestSituationType.REJECTED, AcademicServiceRequestSituationType.CONCLUDED));
    }

    @Override
    protected List<AcademicServiceRequestSituationType> getReceivedFromExternalEntitySituationAcceptedSituationsTypes() {
	return Collections.unmodifiableList(Arrays.asList(AcademicServiceRequestSituationType.DELIVERED));
    }

    @Override
    public boolean hasPersonalInfo() {
	return true;
    }

    @Override
    protected void internalChangeState(AcademicServiceRequestBean academicServiceRequestBean) {
	super.internalChangeState(academicServiceRequestBean);
	if (academicServiceRequestBean.isToProcess()) {
	    if (!getRegistration().isRegistrationConclusionProcessed(getRequestedCycle())) {
		throw new DomainException("error.registryDiploma.registrationNotSubmitedToConclusionProcess");
	    }
	    if (!getFreeProcessed()) {
		assertPayedEvents();
	    }
	    if (isPayable() && !isPayed()) {
		throw new DomainException("AcademicServiceRequest.hasnt.been.payed");
	    }
	    if (getRegistryCode() == null) {
		getRootDomainObject().getInstitutionUnit().getRegistryCodeGenerator().createRegistryFor(this);
		getAdministrativeOffice().getCurrentRectorateSubmissionBatch().addDocumentRequest(this);
	    }
	    if (getLastGeneratedDocument() == null) {
		generateDocument();
	    }
	} else if (academicServiceRequestBean.isToConclude() && !isFree() && !hasEvent() && !isPayedUponCreation()) {
	    RegistryDiplomaRequestEvent.create(getAdministrativeOffice(), getRegistration().getPerson(), this);
	} else if (academicServiceRequestBean.isToCancelOrReject() && hasEvent()) {
	    getEvent().cancel(academicServiceRequestBean.getEmployee());
	}
    }

    @Override
    public boolean isAvailableForTransitedRegistrations() {
	return false;
    }

    @Override
    public boolean isPagedDocument() {
	return false;
    }

    @Override
    public boolean isPayedUponCreation() {
	return true;
    }

    @Override
    public boolean isPossibleToSendToOtherEntity() {
	return true;
    }

    @Override
    public boolean isToPrint() {
	return !isDelivered();
    }

    @Override
    public void setRequestedCycle(CycleType requestedCycle) {
	throw new DomainException("error.registryDiploma.cannotModifyRequestedCycle");
    }
}
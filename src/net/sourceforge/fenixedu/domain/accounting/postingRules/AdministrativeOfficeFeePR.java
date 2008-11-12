package net.sourceforge.fenixedu.domain.accounting.postingRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.fenixedu.dataTransferObject.accounting.EntryDTO;
import net.sourceforge.fenixedu.domain.accounting.AccountingTransaction;
import net.sourceforge.fenixedu.domain.accounting.EntryType;
import net.sourceforge.fenixedu.domain.accounting.Event;
import net.sourceforge.fenixedu.domain.accounting.EventType;
import net.sourceforge.fenixedu.domain.accounting.ServiceAgreementTemplate;
import net.sourceforge.fenixedu.domain.accounting.events.AdministrativeOfficeFeeAndInsuranceEvent;
import net.sourceforge.fenixedu.domain.accounting.events.AdministrativeOfficeFeeAndInsurancePenaltyExemption;
import net.sourceforge.fenixedu.util.Money;

import org.joda.time.DateTime;
import org.joda.time.YearMonthDay;

public class AdministrativeOfficeFeePR extends AdministrativeOfficeFeePR_Base {

    protected AdministrativeOfficeFeePR() {
	super();
    }

    public AdministrativeOfficeFeePR(DateTime startDate, DateTime endDate, ServiceAgreementTemplate serviceAgreementTemplate,
	    Money fixedAmount, Money fixedAmountPenalty, YearMonthDay whenToApplyFixedAmountPenalty) {
	this();
	init(EntryType.ADMINISTRATIVE_OFFICE_FEE, EventType.ADMINISTRATIVE_OFFICE_FEE, startDate, endDate,
		serviceAgreementTemplate, fixedAmount, fixedAmountPenalty, whenToApplyFixedAmountPenalty);

    }

    @Override
    protected boolean hasPenalty(Event event, DateTime when) {
	if (event.hasAnyPenaltyExemptionsFor(AdministrativeOfficeFeeAndInsurancePenaltyExemption.class)) {
	    return false;
	}

	final AdministrativeOfficeFeeAndInsuranceEvent administrativeOfficeFeeAndInsuranceEvent = (AdministrativeOfficeFeeAndInsuranceEvent) event;

	final YearMonthDay paymentEndDate = administrativeOfficeFeeAndInsuranceEvent.getPaymentEndDate() != null ? administrativeOfficeFeeAndInsuranceEvent
		.getPaymentEndDate()
		: getWhenToApplyFixedAmountPenalty();

	final Money amountPayedUntilEndDate = calculateAmountPayedUntilEndDate(administrativeOfficeFeeAndInsuranceEvent,
		paymentEndDate);

	if (!when.toYearMonthDay().isAfter(paymentEndDate)) {
	    return false;
	}

	return amountPayedUntilEndDate.lessThan(getFixedAmount());

    }

    private Money calculateAmountPayedUntilEndDate(AdministrativeOfficeFeeAndInsuranceEvent event, YearMonthDay paymentEndDate) {
	Money result = Money.ZERO;

	for (final AccountingTransaction transaction : event.getNonAdjustingTransactions()) {
	    if (transaction.getToAccountEntry().getEntryType() == getEntryType()
		    && !transaction.getWhenRegistered().toYearMonthDay().isAfter(paymentEndDate)) {
		result = result.add(transaction.getAmountWithAdjustment());
	    }
	}

	return result;
    }

    @Override
    public AdministrativeOfficeFeePR edit(Money fixedAmount, Money penaltyAmount, YearMonthDay whenToApplyFixedAmountPenalty) {

	deactivate();

	return new AdministrativeOfficeFeePR(new DateTime().minus(1000), null, getServiceAgreementTemplate(), fixedAmount,
		penaltyAmount, whenToApplyFixedAmountPenalty);
    }

    @Override
    public Money calculateTotalAmountToPay(Event event, DateTime when, boolean applyDiscount) {

	if (!applyDiscount) {
	    return super.calculateTotalAmountToPay(event, when, applyDiscount);
	}

	final AdministrativeOfficeFeeAndInsuranceEvent administrativeOfficeFeeAndInsuranceEvent = (AdministrativeOfficeFeeAndInsuranceEvent) event;
	return administrativeOfficeFeeAndInsuranceEvent.hasAdministrativeOfficeFeeAndInsuranceExemption() ? Money.ZERO : super
		.calculateTotalAmountToPay(event, when, applyDiscount);

    }

    @Override
    public List<EntryDTO> calculateEntries(Event event, DateTime when) {
	final List<EntryDTO> result = new ArrayList<EntryDTO>(super.calculateEntries(event, when));
	final Iterator<EntryDTO> iterator = result.iterator();
	while (iterator.hasNext()) {
	    final EntryDTO entryDTO = iterator.next();

	    if (!entryDTO.getAmountToPay().isPositive()) {
		iterator.remove();
	    }
	}

	return result;
    }

}

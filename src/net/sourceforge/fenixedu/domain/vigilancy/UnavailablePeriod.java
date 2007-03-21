package net.sourceforge.fenixedu.domain.vigilancy;

import java.util.List;

import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.domain.ExecutionYear;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.RootDomainObject;
import net.sourceforge.fenixedu.domain.WrittenEvaluation;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.injectionCode.AccessControl;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class UnavailablePeriod extends UnavailablePeriod_Base {

    public UnavailablePeriod() {
        super();
        setRootDomainObject(RootDomainObject.getInstance());
    }

    public UnavailablePeriod(DateTime beginDate, DateTime endDate, String justification) {
        this();
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setJustification(justification);
    }

    public UnavailablePeriod(DateTime beginDate, DateTime endDate, String justification, Vigilant vigilant) {
    	this();
    	this.setVigilant(vigilant);
    	this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setJustification(justification);
    }
    
    public void setBeginDate(DateTime begin) {
    	DateTime currentTime = new DateTime();
    	if((isExamCoordinatorRequesting(this.getVigilant()) || begin.isAfter(currentTime)) && (this.getEndDate()==null || this.getEndDate().isAfter(begin))) {
    		super.setBeginDate(begin);
    	}
    	else { 
    		throw new DomainException("label.vigilancy.error.invalidBeginDate");
    	}
    }
    
    public void setEndDate(DateTime end) {
    	if(this.getBeginDate() == null || this.getBeginDate().isBefore(end)) {
    		super.setEndDate(end);
    	}
    	else {
    		throw new DomainException("label.vigilancy.error.invalidEndDate");
    	}
    }
    
    public Boolean containsDate(DateTime date) {
        Interval interval = new Interval(this.getBeginDate(), this.getEndDate());
        return interval.contains(date);
    }

    public Boolean containsInterval(DateTime begin, DateTime end) {
        Interval interval = new Interval(this.getBeginDate(), this.getEndDate());
        return (interval.contains(begin) || interval.contains(end));
    }

    private void doActualDelete() {
        removeRootDomainObject();
        this.getVigilant().removeUnavailablePeriods(this);
        super.deleteDomainObject();
    }

    public void delete() {
        if (isExamCoordinatorRequesting(this.getVigilant())) {
            this.doActualDelete();
        } else {
            if (this.canChangeUnavailablePeriodFor(this.getVigilant())) {
                this.doActualDelete();
            }
        }
    }

    private void doActualEdit(DateTime begin, DateTime end, String justification) {
        this.setBeginDate(begin);
        this.setEndDate(end);
        this.setJustification(justification);
    }

    public void edit(DateTime begin, DateTime end, String justification) {

        if (isExamCoordinatorRequesting(this.getVigilant())) {
            this.doActualEdit(begin, end, justification);
        } else {
            if (this.canChangeUnavailablePeriodFor(this.getVigilant())) {
                this.doActualEdit(begin, end, justification);
            }
        }
    }

    @Override
    public void setVigilant(Vigilant vigilant) {
        if (isExamCoordinatorRequesting(vigilant) || canChangeUnavailablePeriodFor(vigilant)) {
            super.setVigilant(vigilant);
        } else {
            throw new DomainException("vigilancy.error.cannotAddUnavailablePeriodDueToExistingConvoke");
        }
    }

    private boolean canChangeUnavailablePeriodFor(Vigilant vigilant) {
        if (vigilant != null) {
            if (!vigilant.isAllowedToSpecifyUnavailablePeriod()) {
                throw new DomainException("vigilancy.error.outOutPeriodToSpecifyUnavailablePeriods");
            }
            if(this.getEndDate()!=null && this.getEndDate().isBeforeNow()) {
        	throw new DomainException("vigilancy.error.cannotEditClosedUnavailablePeriod");
            }
            List<Vigilancy> convokes = vigilant.getVigilancies();
            for (Vigilancy convoke : convokes) {
                WrittenEvaluation writtenEvaluation = convoke.getWrittenEvaluation();
                DateTime begin = writtenEvaluation.getBeginningDateTime();
                DateTime end = writtenEvaluation.getEndDateTime();
                if (this.containsInterval(begin, end)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean isExamCoordinatorRequesting(Vigilant vigilant) {

        IUserView userview = AccessControl.getUserView();

        if (userview != null) {
            Person person = userview.getPerson();
            ExecutionYear executionYear = ExecutionYear.readCurrentExecutionYear();
            List<VigilantGroup> vigilantGroups = vigilant.getVigilantGroups();

            if (person != null) {
                ExamCoordinator coordinator = person
                        .getExamCoordinatorForGivenExecutionYear(executionYear);
                if (coordinator == null) {
                    return false;
                } else {
                    for (VigilantGroup group : vigilantGroups) {
                        if (coordinator.managesGivenVigilantGroup(group)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String getUnavailableAsString() {
        DateTime begin = this.getBeginDate();
        DateTime end = this.getEndDate();

        return String.format("%02d/%02d/%d (%02d:%02d) - %02d/%02d/%d (%02d:%02d): %s", begin
                .getDayOfMonth(), begin.getMonthOfYear(), begin.getYear(), begin.getHourOfDay(), begin
                .getMinuteOfHour(), end.getDayOfMonth(), end.getMonthOfYear(), end.getYear(), end
                .getHourOfDay(), end.getMinuteOfHour(), this.getJustification());
    }

}

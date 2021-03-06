/*
 * Created on 21/Ago/2003
 */
package net.sourceforge.fenixedu.presentationTier.Action.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.fenixedu.applicationTier.Servico.commons.ReadExecutionPeriods;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.ExistingServiceException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.NonExistingServiceException;
import net.sourceforge.fenixedu.applicationTier.Servico.manager.EditCurricularCourseScope;
import net.sourceforge.fenixedu.applicationTier.Servico.manager.ReadBranchesByDegreeCurricularPlan;
import net.sourceforge.fenixedu.applicationTier.Servico.manager.ReadCurricularCourseScope;
import net.sourceforge.fenixedu.dataTransferObject.InfoBranch;
import net.sourceforge.fenixedu.dataTransferObject.InfoCurricularCourseScope;
import net.sourceforge.fenixedu.dataTransferObject.InfoCurricularCourseScopeEditor;
import net.sourceforge.fenixedu.dataTransferObject.InfoCurricularSemester;
import net.sourceforge.fenixedu.dataTransferObject.InfoExecutionPeriod;
import net.sourceforge.fenixedu.domain.Branch;
import net.sourceforge.fenixedu.domain.CurricularSemester;
import net.sourceforge.fenixedu.presentationTier.Action.base.FenixDispatchAction;
import net.sourceforge.fenixedu.presentationTier.Action.exceptions.ExistingActionException;
import net.sourceforge.fenixedu.presentationTier.Action.exceptions.FenixActionException;
import net.sourceforge.fenixedu.presentationTier.Action.exceptions.NonExistingActionException;
import net.sourceforge.fenixedu.util.Data;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;

import pt.ist.fenixWebFramework.struts.annotations.ExceptionHandling;
import pt.ist.fenixWebFramework.struts.annotations.Exceptions;
import pt.ist.fenixWebFramework.struts.annotations.Forward;
import pt.ist.fenixWebFramework.struts.annotations.Forwards;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;
import pt.ist.fenixWebFramework.struts.annotations.Tile;
import pt.ist.fenixframework.FenixFramework;

/**
 * @author lmac1
 */
@Mapping(module = "manager", path = "/editCurricularCourseScope",
        input = "/editCurricularCourseScope.do?method=prepareEdit&page=0", attribute = "curricularCourseScopeForm",
        formBean = "curricularCourseScopeForm", scope = "request", parameter = "method")
@Forwards(value = {
        @Forward(name = "readCurricularCourse", path = "/readCurricularCourse.do"),
        @Forward(name = "readDegree", path = "/readDegree.do"),
        @Forward(name = "editCurricularCourseScope", path = "/manager/editCurricularCourseScope_bd.jsp", tileProperties = @Tile(
                navLocal = "/manager/curricularCourseNavLocalManager.jsp")) })
@Exceptions(value = {
        @ExceptionHandling(type = net.sourceforge.fenixedu.presentationTier.Action.exceptions.NonExistingActionException.class,
                key = "resources.Action.exceptions.NonExistingActionException",
                handler = net.sourceforge.fenixedu.presentationTier.config.FenixErrorExceptionHandler.class, scope = "request"),
        @ExceptionHandling(type = net.sourceforge.fenixedu.presentationTier.Action.exceptions.ExistingActionException.class,
                key = "resources.Action.exceptions.ExistingActionException",
                handler = net.sourceforge.fenixedu.presentationTier.config.FenixErrorExceptionHandler.class, scope = "request") })
public class EditCurricularCourseScopeDA extends FenixDispatchAction {

    public ActionForward prepareEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws FenixActionException {

        DynaActionForm dynaForm = (DynaActionForm) form;

        String degreeCurricularPlanId = request.getParameter("degreeCurricularPlanId");
        String curricularCourseScopeId = request.getParameter("curricularCourseScopeId");
        InfoCurricularCourseScope oldInfoCurricularCourseScope = null;

        try {
            oldInfoCurricularCourseScope = ReadCurricularCourseScope.run(curricularCourseScopeId);
        } catch (NonExistingServiceException ex) {
            throw new NonExistingActionException("message.nonExistingCurricularCourseScope",
                    mapping.findForward("readCurricularCourse"));
        } catch (FenixServiceException fenixServiceException) {
            throw new FenixActionException(fenixServiceException.getMessage());
        }

        if (oldInfoCurricularCourseScope.getBeginDate() != null) {
            dynaForm.set("beginDate", Data.format2DayMonthYear(oldInfoCurricularCourseScope.getBeginDate().getTime(), "/"));
        }
        if (oldInfoCurricularCourseScope.getAnotation() != null) {
            dynaForm.set("anotation", oldInfoCurricularCourseScope.getAnotation());
        }

        dynaForm.set("branchId", oldInfoCurricularCourseScope.getInfoBranch().getExternalId().toString());
        dynaForm.set("curricularSemesterId", oldInfoCurricularCourseScope.getInfoCurricularSemester().getExternalId().toString());

        List result = null;
        try {
            result = ReadBranchesByDegreeCurricularPlan.run(degreeCurricularPlanId);
        } catch (NonExistingServiceException ex) {
            throw new NonExistingActionException("message.nonExistingDegreeCurricularPlan", mapping.findForward("readDegree"));
        } catch (FenixServiceException e) {
            throw new FenixActionException(e);
        }

        if (result == null) {
            throw new NonExistingActionException("message.insert.degreeCurricularCourseScope.error",
                    mapping.findForward("readCurricularCourse"));
        }

        // creation of bean of InfoBranches for use in jsp
        List branchesList = new ArrayList();
        InfoBranch infoBranch;
        Iterator iter = result.iterator();
        String label, value;
        while (iter.hasNext()) {
            infoBranch = (InfoBranch) iter.next();
            value = infoBranch.getExternalId().toString();
            label = infoBranch.getCode() + " - " + infoBranch.getName();
            branchesList.add(new LabelValueBean(label, value));
        }

        // obtain execution periods to show in jsp
        List<InfoExecutionPeriod> infoExecutionPeriods = null;
        infoExecutionPeriods = ReadExecutionPeriods.run();

        if (infoExecutionPeriods == null) {
            throw new NonExistingActionException("message.insert.executionPeriods.error",
                    mapping.findForward("readCurricularCourse"));
        }

        List executionPeriodsLabels = new ArrayList();
        String labelExecutionPeriod = Data.format2DayMonthYear(oldInfoCurricularCourseScope.getBeginDate().getTime(), "/");
        executionPeriodsLabels.add(new LabelValueBean(labelExecutionPeriod, labelExecutionPeriod));
        for (final InfoExecutionPeriod infoExecutionPeriod : infoExecutionPeriods) {
            labelExecutionPeriod = Data.format2DayMonthYear(infoExecutionPeriod.getBeginDate(), "/");
            executionPeriodsLabels.add(new LabelValueBean(labelExecutionPeriod, labelExecutionPeriod));
        }

        request.setAttribute("executionPeriodsLabels", executionPeriodsLabels);
        request.setAttribute("branchesList", branchesList);

        return mapping.findForward("editCurricularCourseScope");
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws FenixActionException {

        DynaActionForm dynaForm = (DynaValidatorForm) form;

        String oldCurricularCourseScopeId = request.getParameter("curricularCourseScopeId");

        InfoCurricularCourseScopeEditor newInfoCurricularCourseScope = new InfoCurricularCourseScopeEditor();

        String curricularSemesterIdString = (String) dynaForm.get("curricularSemesterId");
        String branchIdString = (String) dynaForm.get("branchId");
        String beginDateString = (String) dynaForm.get("beginDate");
        String anotationString = (String) dynaForm.get("anotation");

        String curricularSemesterId = curricularSemesterIdString;

        InfoCurricularSemester infoCurricularSemester =
                InfoCurricularSemester.newInfoFromDomain(FenixFramework.<CurricularSemester> getDomainObject(curricularSemesterId));
        newInfoCurricularCourseScope.setInfoCurricularSemester(infoCurricularSemester);

        InfoBranch infoBranch = new InfoBranch(FenixFramework.<Branch> getDomainObject(branchIdString));
        newInfoCurricularCourseScope.setInfoBranch(infoBranch);
        newInfoCurricularCourseScope.setExternalId(oldCurricularCourseScopeId);

        if (beginDateString.compareTo("") != 0) {
            Calendar beginDateCalendar = Calendar.getInstance();
            beginDateCalendar.setTime(Data.convertStringDate(beginDateString, "/"));
            newInfoCurricularCourseScope.setBeginDate(beginDateCalendar);
        }
        newInfoCurricularCourseScope.setAnotation(anotationString);

        try {
            EditCurricularCourseScope.run(newInfoCurricularCourseScope);
        } catch (NonExistingServiceException ex) {
            throw new NonExistingActionException(ex.getMessage(), mapping.findForward("readCurricularCourse"));
        } catch (ExistingServiceException e) {
            throw new ExistingActionException("message.manager.existing.curricular.course.scope");
        } catch (FenixServiceException fenixServiceException) {
            throw new FenixActionException(fenixServiceException.getMessage());
        }

        request.setAttribute("infoCurricularCourseScope", newInfoCurricularCourseScope);

        return mapping.findForward("readCurricularCourse");
    }
}
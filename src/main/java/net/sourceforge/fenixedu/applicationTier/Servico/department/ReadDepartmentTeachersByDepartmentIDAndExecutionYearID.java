package net.sourceforge.fenixedu.applicationTier.Servico.department;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.fenixedu.applicationTier.Filtro.DepartmentMemberAuthorizationFilter;
import net.sourceforge.fenixedu.applicationTier.Filtro.TeacherAuthorizationFilter;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.NotAuthorizedException;
import net.sourceforge.fenixedu.domain.Department;
import net.sourceforge.fenixedu.domain.ExecutionYear;
import net.sourceforge.fenixedu.domain.Teacher;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;

/**
 * 
 * @author naat
 * 
 */
public class ReadDepartmentTeachersByDepartmentIDAndExecutionYearID {

    public List<Teacher> run(String departmentID, String executionYearID) throws FenixServiceException {
        Department department = FenixFramework.getDomainObject(departmentID);

        List<Teacher> teachersFromDepartment = new ArrayList<Teacher>();

        if (executionYearID != null) {
            ExecutionYear executionYear = FenixFramework.getDomainObject(executionYearID);

            teachersFromDepartment =
                    department.getAllTeachers(executionYear.getBeginDateYearMonthDay(), executionYear.getEndDateYearMonthDay());

        } else {
            teachersFromDepartment = department.getAllTeachers();
        }

        return teachersFromDepartment;
    }

    // Service Invokers migrated from Berserk

    private static final ReadDepartmentTeachersByDepartmentIDAndExecutionYearID serviceInstance =
            new ReadDepartmentTeachersByDepartmentIDAndExecutionYearID();

    @Atomic
    public static List<Teacher> runReadDepartmentTeachersByDepartmentIDAndExecutionYearID(String departmentID,
            String executionYearID) throws FenixServiceException, NotAuthorizedException {
        try {
            DepartmentMemberAuthorizationFilter.instance.execute();
            return serviceInstance.run(departmentID, executionYearID);
        } catch (NotAuthorizedException ex1) {
            try {
                TeacherAuthorizationFilter.instance.execute();
                return serviceInstance.run(departmentID, executionYearID);
            } catch (NotAuthorizedException ex2) {
                throw ex2;
            }
        }
    }

}
/*
 * Created on 4/Ago/2003
 *
 */
package net.sourceforge.fenixedu.dataTransferObject;

import java.util.List;
import java.util.ListIterator;

/**
 * @author asnr and scpo
 * 
 */

public class InfoSiteStudentsWithoutGroup extends DataTranferObject implements ISiteComponent {

    private List infoStudentList;
    private Integer groupNumber;
    private InfoStudent infoUserStudent;
    private InfoGrouping infoGrouping;

    @Override
    public boolean equals(Object objectToCompare) {
        boolean result = false;
        if (objectToCompare instanceof InfoSiteStudentsWithoutGroup) {

            result = (this.getGroupNumber().equals(((InfoSiteStudentsWithoutGroup) objectToCompare).getGroupNumber()));

        }

        if (((InfoSiteStudentsWithoutGroup) objectToCompare).getInfoStudentList() == null && this.getInfoStudentList() == null
                && result == true) {

            return true;
        }
        if (((InfoSiteStudentsWithoutGroup) objectToCompare).getInfoStudentList() == null
                || this.getInfoStudentList() == null
                || ((InfoSiteStudentsWithoutGroup) objectToCompare).getInfoStudentList().size() != this.getInfoStudentList()
                        .size()) {

            return false;
        }

        ListIterator iter1 = ((InfoSiteStudentsWithoutGroup) objectToCompare).getInfoStudentList().listIterator();
        ListIterator iter2 = this.getInfoStudentList().listIterator();
        while (result && iter1.hasNext()) {

            InfoStudent infoStudent1 = (InfoStudent) iter1.next();
            InfoStudent infoStudent2 = (InfoStudent) iter2.next();
            if (!infoStudent1.equals(infoStudent2)) {

                result = false;
            }
        }
        return result;
    }

    /**
     * @return
     */
    public List getInfoStudentList() {
        return infoStudentList;
    }

    /**
     * @param list
     */
    public void setInfoStudentList(List infoStudentList) {
        this.infoStudentList = infoStudentList;
    }

    /**
     * @return
     */
    public Integer getGroupNumber() {
        return groupNumber;
    }

    /**
     * @param integer
     */
    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    /**
     * @return
     */
    public InfoStudent getInfoUserStudent() {
        return infoUserStudent;
    }

    /**
     * @param list
     */
    public void setInfoUserStudent(InfoStudent infoUserStudent) {
        this.infoUserStudent = infoUserStudent;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String result = "[INFO_SITE_STUDENTS_WITHOUT";
        result += ", infoStudentList=" + getInfoStudentList();
        result += ", groupNumber=" + getGroupNumber();
        result += "]";
        return result;
    }

    public InfoGrouping getInfoGrouping() {
        return infoGrouping;
    }

    public void setInfoGrouping(InfoGrouping infoGrouping) {
        this.infoGrouping = infoGrouping;
    }
}

package net.sourceforge.fenixedu.presentationTier.Action.commons;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.fenixedu.domain.PendingRequest;
import net.sourceforge.fenixedu.domain.PendingRequestParameter;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;

public class LoginRedirectAction extends Action {

    public static String addToUrl(String url, String param, String value) {
        if (url.contains("?")) {
            if (url.contains("&")) {
                return url + "&" + param + "=" + value;
            } else if (url.charAt(url.length() - 1) != '?') {
                return url + "&" + param + "=" + value;
            } else {
                return url + param + "=" + value;
            }
        } else {
            return url + "?" + param + "=" + value;
        }
    }

    @Atomic
    public Boolean reconstructURL(HttpServletRequest request) {
        final PendingRequest pendingRequest = FenixFramework.getDomainObject(request.getParameter("pendingRequest"));
        if (pendingRequest.getBuildVersion().equals(PendingRequest.buildVersion)) {
            String url = pendingRequest.getUrl();

            final List<PendingRequestParameter> attributes = new ArrayList<PendingRequestParameter>();
            for (PendingRequestParameter pendingRequestParameter : pendingRequest.getPendingRequestParameter()) {
                if (pendingRequestParameter.getAttribute()) {
                    attributes.add(pendingRequestParameter);
                } else {
                    url = addToUrl(url, pendingRequestParameter.getParameterKey(), pendingRequestParameter.getParameterValue());
                }
            }
            request.setAttribute("url", url);
            request.setAttribute("body_param_list", attributes);

            request.setAttribute("method", pendingRequest.getPost() ? "post" : "get");

            pendingRequest.delete();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (reconstructURL(request)) {
                String url = (String) request.getAttribute("url");
                if (url.contains("oauth")) {
                    response.sendRedirect(url);
                    return null;
                }
                return mapping.findForward("show-redirect-page");
            }
        } catch (Exception e) {
            System.out.println("Login: Catched " + e.getClass().getName() + " OID with pendingRequest  "
                    + request.getParameter("pendingRequest"));
            response.sendRedirect("/home.do");
            return null;
        }
        response.sendRedirect("/home.do");
        return null;
    }
}
package com.aatrox.web.base.session;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class HttpSessionSidWrapper extends HttpSessionWrapper {
    private HttpServletRequestWrapper requestWrapper;
    private String sid = "";
    private Map<String, Object> map = null;

    public HttpSessionSidWrapper(HttpServletRequestWrapper requestWrapper, HttpSession session) {
        super(session);
        this.requestWrapper = requestWrapper;
        this.sid = requestWrapper.sid;
        this.map = SessionService.getInstance().getSession(this.sid);
    }

    @Override
    public Object getAttribute(String arg0) {
        return this.map.get(arg0);
    }

    @Override
    public Enumeration getAttributeNames() {
        return new Enumerator(this.map.keySet(), true);
    }

    public void saveMapToCache() {
        SessionService.getInstance().saveSession(this.sid, this.map);
    }

    @Override
    public void invalidate() {
        this.map.clear();
        SessionService.getInstance().removeSession(this.sid);
    }

    @Override
    public void removeAttribute(String arg0) {
        this.map.remove(arg0);
        if (this.sessionToCacheImmediately()) {
            SessionService.getInstance().saveSession(this.sid, this.map);
        }

    }

    @Override
    public void setAttribute(String arg0, Object arg1) {
        this.map.put(arg0, arg1);
        if (this.sessionToCacheImmediately()) {
            SessionService.getInstance().saveSession(this.sid, this.map);
        }

    }

    private boolean sessionToCacheImmediately() {
        return this.requestWrapper.getAttribute("REQUEST_SESSION_TO_CACHE_IMMEDIATELY") != null;
    }

    @Override
    public String getId() {
        return this.sid;
    }
}
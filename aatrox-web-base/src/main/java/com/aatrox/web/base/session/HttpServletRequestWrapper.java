package com.aatrox.web.base.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/2
 */
public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {
    protected String sid = "";
    private HttpSessionSidWrapper session;

    public HttpServletRequestWrapper(String sid, HttpServletRequest request) {
        super(request);
        this.sid = sid;
    }

    public void saveSessionToCache() {
        if (this.session != null) {
            this.session.saveMapToCache();
        }

    }

    public void refreshSid(String sid) {
        this.sid = sid;
        this.session = new HttpSessionSidWrapper(this, super.getSession());
    }

    @Override
    public HttpSession getSession(boolean create) {
        if (this.session == null) {
            this.session = new HttpSessionSidWrapper(this, super.getSession(create));
        } else {
            this.session.setSession(super.getSession(create));
        }

        return this.session;
    }

    @Override
    public HttpSession getSession() {
        if (this.session == null) {
            this.session = new HttpSessionSidWrapper(this, super.getSession());
        } else {
            this.session.setSession(super.getSession());
        }

        return this.session;
    }
}

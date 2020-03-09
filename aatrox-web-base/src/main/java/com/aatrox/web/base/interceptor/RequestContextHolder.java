package com.aatrox.web.base.interceptor;

import com.aatrox.apilist.form.ValidationForm;

/**
 * @author aatrox
 * @desc
 * @date 2019/9/2
 */
public class RequestContextHolder {
        private static final ThreadLocal<RequestContext> digestContextHolder = new ThreadLocal();
        private static final ThreadLocal<ExtendedParameter> extendedParameterHolder = new ThreadLocal();

        public RequestContextHolder() {
        }

        public static void setExtendedParameter(ExtendedParameter extendedParameter) {
            extendedParameterHolder.set(extendedParameter);
        }

        public static RequestContext init(boolean debug, String appName) {
            RequestContext ctx = (RequestContext)digestContextHolder.get();
            if (ctx == null) {
                ctx = new RequestContext();
                if (appName != null) {
                    ctx.setClientAppName(appName);
                }

                ctx.setDebug(debug);
                ExtendedParameter extendedParameter = (ExtendedParameter)extendedParameterHolder.get();
                ctx.setLoginAccount(extendedParameter != null ? extendedParameter.getLoginAccount() : null);
                digestContextHolder.set(ctx);
            }

            return ctx;
        }

        public static RequestContext getDigestContext() {
            return (RequestContext)digestContextHolder.get();
        }

        public static void clear() {
            extendedParameterHolder.remove();
            digestContextHolder.remove();
        }

        public static void setErrMsg(String errMsg) {
            RequestContext ctx = (RequestContext)digestContextHolder.get();
            if (ctx != null) {
                ctx.setErrMsg(errMsg);
            }

        }

        public static void setValidationForm(ValidationForm form) {
            RequestContext ctx = (RequestContext)digestContextHolder.get();
            if (ctx != null) {
                ctx.setForm(form);
            }

        }

        public static void setOperatorDesc(String operatorDesc) {
            RequestContext ctx = (RequestContext)digestContextHolder.get();
            if (ctx != null) {
                ctx.setOperatorDesc(operatorDesc);
            }

        }
}

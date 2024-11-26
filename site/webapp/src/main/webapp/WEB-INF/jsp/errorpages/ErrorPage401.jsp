<!doctype html>
<%@ include file="/WEB-INF/jspf/htmlTags.jspf" %>
<%@ include file="/WEB-INF/jspf/init.jspf" %>

<fmt:setBundle basename="org.hippoecm.hst.security.servlet.LoginServlet"/>

<%
  String destination = "";
  HttpSession session = pageContext.getSession();
  if (session != null) {
    destination = (String) session.getAttribute("org.hippoecm.hst.security.servlet.destination");
    if (destination == null) destination = "";
  }
  int autoRedirectSeconds = 2;
%>

<%
	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	ResourceBundle bundle = resourceBundle.getResourceBundle(request, Constants.RESOURCE_LOGIN_PAGE);
	String loginPageHeader = "" ;
	String copyRight = "";
	String forbid = "";
	String pageTitle = "";
	String messageAuthRequired ="";
	String messagePageAutoRedirectInSecond = "";
	String messageTryAgain = "";
	String labelAuthRequire = "";
	if (bundle != null) {
		loginPageHeader = resourceBundle.getPropertyValue(bundle, Constants.LOGIN_PAGE_HEADER);
		copyRight = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_COPY_RIGHT);
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		copyRight = copyRight.replace("{0}", String.valueOf(year));
		messageAuthRequired = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_AUTH_REQUIRED);
		messagePageAutoRedirectInSecond = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_PAGE_AUTO_REDIRECT_IN_SECOND).replace("{0}", String.valueOf(autoRedirectSeconds));
		messageTryAgain = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_TRY_AGAIN);
		labelAuthRequire = resourceBundle.getPropertyValue(bundle, Constants.LABEL_AUTH_REQ);
	}
%>

<hst:link var="loginFormUrl" path="/login/form">
  <hst:param name="destination" value="<%=destination%>"/>
</hst:link>
<hst:link var="loginProxyUrl" path="/login/proxy">
  <hst:param name="destination" value="<%=destination%>"/>
</hst:link>

<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title><%=labelAuthRequire %></title>
  <meta http-equiv="refresh" content='<%=autoRedirectSeconds%>;url=${loginFormUrl}'/>
  <link rel="shortcut icon" href="/hst/security/skin/images/hippo-cms.ico" type="image/x-icon"/>
  <link rel="stylesheet" type="text/css" href="/hst/security/skin/screen.css"/>
</head>
<body class="hippo-root">
<div>
  <div class="hippo-login-panel">
    <form class="hippo-login-panel-form" name="signInForm" method="post" action="${loginProxyUrl}">
      <h2><div class="hippo-global-hideme"><span>${loginPageHeader}</span></div></h2>
      <div class="hippo-login-form-container">
        <table>
          <tr>
            <td>
              <p>
                <%=messageAuthRequired%>
            </td>
          </tr>
          <tr>
            <td>
              <p>
                <a href="${loginFormUrl}"><%=messageTryAgain %></a>
                <br/><br/>
                <%=messagePageAutoRedirectInSecond%>
              </p>
            </td>
          </tr>
        </table>
      </div>
    </form>
    <div class="hippo-login-panel-copyright">
      <%=copyRight %>
    </div>
  </div>
</div>
</body>
</html>
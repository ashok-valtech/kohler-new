<!doctype html>
<%@ include file="/WEB-INF/jspf/htmlTags.jspf" %>
<%@ include file="/WEB-INF/jspf/init.jspf" %>
<% response.setStatus(403); %>
<%
	ResourceBundleCustom resourceBundle = new ResourceBundleCustom();
	ResourceBundle bundle = resourceBundle.getResourceBundle(request, Constants.RESOURCE_LOGIN_PAGE);
	String loginPageHeader = "" ;
	String copyRight = "";
	String forbid = "";
	String pageTitle = "";
	if (bundle != null) {
		loginPageHeader = resourceBundle.getPropertyValue(bundle, Constants.LOGIN_PAGE_HEADER);
		copyRight = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_COPY_RIGHT);
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		copyRight = copyRight.replace("{0}", String.valueOf(year));
		forbid = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_ACC_FORBID);
		pageTitle = resourceBundle.getPropertyValue(bundle, Constants.MESSAGE_ERROR_403_PAGE_TITLE);
	}
%>

<html lang="<%= request.getLocale().getLanguage() %>">
<head>
  <meta charset="utf-8"/>
  <title><%=pageTitle %></title>
  <link rel="shortcut icon" href="/hst/security/skin/images/hippo-cms.ico" type="image/x-icon"/>
  <link rel="stylesheet" type="text/css" href="/hst/security/skin/screen.css"/>
</head>
<body class="hippo-root">
	<div>
	  <div class="hippo-login-panel">
	      <h2>
	        <div class="hippo-global-hideme"><span><%=loginPageHeader %></span></div>
	      </h2>
	      <div class="hippo-login-form-container">
	      		<h1><%=forbid %></h1>
	      </div>	
	      <div class="hippo-login-panel-copyright">
	      	<%=copyRight %>
	      </div>
	  </div>
	</div>		
</body>
</html>
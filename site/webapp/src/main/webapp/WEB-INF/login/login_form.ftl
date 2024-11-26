<#--
  Copyright 2008-2016 Hippo B.V. (http://www.onehippo.com)

  Licensed under the Apache License, Version 2.0 (the  "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS"
  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<html>
  <head>
    <title>${loginPageTitle}</title>
    <link rel="shortcut icon" href="/hst/security/skin/images/hippo-cms.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="/hst/security/skin/screen.css" />
  </head>
  <body class="hippo-root" onload="return document.signInForm.username.focus();" style="text-align:center">
    <div class="hippo-body-back-hack">
      <div class="hippo-login-panel">
        <form id="hippo-login-panel-form" class="hippo-login-panel-form" name="signInForm" method="post" action="..${servletPath}/proxy">
          <h2><div class="hippo-global-hideme"><span>${loginPageHeader}</span></div></h2>
          <div class="hippo-login-form-container">
            <table>
              <tr>
                <td width="30%"><label>${userNameLabel}&nbsp;</label></td>
                <td><input class="hippo-form-text" type="text" value="" name="username" id="username"/></td>
              </tr>
              <tr>
                <td><label>${passwordLabel}&nbsp;</label></td>
                <td><input class="hippo-form-password" type="password" value="" name="password" id="password"/></td>
              </tr>
              <tr>
              	<td colspan="2">
              		<div class="form-group">
	              		<div class="col-sm-offset-5 col-sm-4 captcha-container"> 
	                    	<div class="g-recaptcha" id="g-recaptcha" name="koh-contact-google-captcha" data-sitekey="${captchaClientSecreKey}"></div>
	                    	<input type="hidden" id="captchaError" name="captchaError" value="${captchaError?html}" />
	                    	<input type="hidden" id="captchaErrorHideIn" name="captchaErrorHideIn" value="${captchaErrorHideIn}" />
	                    </div>
	                </div>    	
                </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td class="hippo-global-alignright">
                  <input type="hidden" id="destination" name="destination" value="${destination?html}" />
                  <input id="hippo-form-submit" class="hippo-form-submit" type="submit" value="${loginLabel}"/>
                  <input class="hippo-form-submit" type="button" value="${cancelLabel}" onclick="if (document.getElementById('destination').value) location.href = document.getElementById('destination').value; return false;" />
                </td>
              </tr>
            </table>
          </div>
        </form>
        <div class="hippo-login-panel-copyright">
          ${copyRight}
        </div>
      </div>
    </div>
    <script type="text/javascript" src="/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/hst/security/skin/screen.js"></script>
  </body>
</html>

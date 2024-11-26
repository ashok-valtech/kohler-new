<#include "../include/imports.ftl">
<@fmt.message key="contact-form-template-first-name" var="firstName"/>
<@fmt.message key="contact-form-template-email-address" var="emailAddress"/>
<@fmt.message key="contact-form-submit" var="submit"/>
<@fmt.message key="captcha_client_secret_Key" var="clientSeceretKey"/>
<@fmt.message key="contact-form-success-message" var="successMessage"/>
<@fmt.message key="contact-form-error-message" var="errorMessage"/>
<@fmt.message key="contact-form-google-captcha-error-message" var="captchaErrorMessage"/>
<@fmt.message key="contact-form-email-error-message2" var="emailErrorMessage2"/>
<@fmt.message key="contact-form-select" var="select"/>
<@fmt.message key="contact-form-privacy-policy-title" var="privacyPolicyTitle"/>
<@fmt.message key="contact-form-privacy-policy-link" var="privacyPolicyLink"/>
<@fmt.message key="mu-contact-form-header" var="contactForm"/>
<@fmt.message key="contact-form-landing-select" var="select"/>
<@fmt.message key="mu-contact-form-first-name-error-message" var="firstNameErrorMessage"/>
<@fmt.message key="rsg-contact-form-preferrdstore-error-message" var="scoreErrorMessage"/>
<@fmt.message key="mu-contact-form-interested-error-message" var="interestedErrorMessage"/>
<@fmt.message key="mu-contact-form-email-error-message" var="emailErrorMessage"/>
<@fmt.message key="mu-contact-form-mobile-error-message" var="mobileErrorMessage"/>


<@fmt.message key="mu-contact-form-country-title" var="mucountry"/>
<@fmt.message key="mu-contact-form-email" var="muemail"/>
<@fmt.message key="mu-contact-form-name" var="muname"/>
<@fmt.message key="rsg-contact-form-preferredstore" var="preferredstore"/>
<@fmt.message key="mu-contact-form-intprods" var="muintprods"/>
<@fmt.message key="mu-contact-form-mobileno" var="mobileNumber"/>
<@fmt.message key="mu-checkbox-statement" var="checkboxStatement"/>
<@fmt.message key="rsg-preferred-drop-down-value1" var="prefstr1"/>
<@fmt.message key="rsg-intrestedproducts-drop-down-value1" var="intp1"/>
<@fmt.message key="rsg-contact-form-intprods" var="rsgintprods"/>




<#assign englishSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
    <#assign englishSite="/en" />
</#if>

<@hst.include ref="container"/>

<input type="hidden" id="firstNameErrorMessageLink" value="${firstNameErrorMessage}" />
<input type="hidden" id="scoreErrorMessageLink" value="${scoreErrorMessage}" />
<input type="hidden" id="interestedErrorMessageLink" value="${interestedErrorMessage}" />
<input type="hidden" id="countryErrorMessageLink" value="${countryErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink" value="${emailErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink2" value="${emailErrorMessage2}" />
<input type="hidden" id="mobileErrorMessageLink" value="${mobileErrorMessage}" />
<input type="hidden" id="mobileErrorMessageLink2" value="${mobileErrorMessage}" />
<input type="hidden" id="captchaErrorMessageLink" value="${captchaErrorMessage}" />
<input type="hidden" id="checkboxerrorlink" value="${checkboxerrorlink}" />


<section class="c-koh-contact-form">
        <div class="koh-mu-contact-container">
            <div class="koh-contact-header">
                <span class="koh-mu-contact-title">${contactForm}</span>
            </div>
            <div id="successMessage" class="hide">
                ${successMessage}
            </div>
            <div id="failureMessage" class="hide">
                ${errorMessage}
            </div>
            <div class="koh-contact-form-wrapper">
               <form class="form-horizontal"  action="<@hst.actionURL escapeXml=false />" method="post" name="${form.name!}" id="mucontactUsForm"
                 <#if form.multipart>enctype="multipart/form-data"</#if> autocomplete="off" >
                   
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-first-name">${muname}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-first-name" maxlength="40" placeholder="" name="name" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-email-address">${muemail}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-email-address" maxlength="60" placeholder="" name="email" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-mobile-number">${mobileNumber}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-mobile-number" placeholder="" name="mobileno" maxlength="20">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-score">${preferredstore}</label>
                        <div class="col-sm-4">          
                             <select class="form-control" id="koh-contact-score" name="preferredstore">
                                <option value="">${select}</option>
                              <#list prefstr1?split(",") as preferredStoreValue>
							     <option value="${preferredStoreValue}" >${preferredStoreValue}</option>
							  </#list>
                            </select>
                        </div>
                    </div>
                    
                     <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-interestedproducts">${rsgintprods}</label>
                        <div class="col-sm-4">
                           <select class="form-control" id="koh-contact-interestedproducts" name="interestedproducts">
                           			<option value="">${select}</option>
                                  <#list intp1?split(",") as intpValue>
							          <option value="${intpValue}" >${intpValue}</option>
							      </#list>
                            </select>
                        </div>
                    </div>
                    
                      <input type="hidden" class="form-control" id="gfg" maxlength="60" placeholder="" name="ipaddress">
                     <#--  <li>
                      <input type="checkbox" id="koh-contact-check-box" placeholder="" name="interestedToBeContacted">
                      <label for="koh-contact-check-box"><p style="font-size:16px;"> &nbsp; ${checkboxStatement}</p></label>
                      <p id="koh-contact-check-box1"></p>
                      </li>  -->
                    <div class="form-group">
                        <div class="col-sm-offset-5 col-sm-4 captcha-container">   
                            <div class="g-recaptcha" id="g-recaptcha" name="koh-contact-google-captcha" data-sitekey="${clientSeceretKey}"></div>
                        </div>
                    </div>
                    
                    <div class="form-group">        
                        <div class="col-sm-offset-5 col-sm-4">
                        	<a class="koh-privacy-policy-link-cf" onclick="window.open('${privacyPolicyLink}', '_blank', 'location=yes,height=600,width=1000,scrollbars=yes,status=yes');">${privacyPolicyTitle}</a>
                            <button id="koh-contact-form-btn" type="submit" class="btn btn-default">${submit} </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
</section>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-rsg-contact-form.min.js"></script>
</@hst.headContribution>

<script src= "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"> 
    </script> 
    <script> 
        $.getJSON("https://api.ipify.org?format=json", 
           function(data) { 
            $("#gfg").val(data.ip);
        }) 
 </script>

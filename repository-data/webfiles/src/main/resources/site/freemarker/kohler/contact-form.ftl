<#include "../include/imports.ftl">
<@fmt.message key="contact-form-drop-down-value1" var="dropDown1"/>
<@fmt.message key="contact-form-drop-down-value2" var="dropDown2"/>
<@fmt.message key="contact-form-drop-down-value3" var="dropDown3"/>
<@fmt.message key="contact-form-drop-down-value4" var="dropDown4"/>
<@fmt.message key="contact-form-drop-down-value5" var="dropDown5"/>
<@fmt.message key="contact-form-drop-down-value6" var="dropDown6"/>
<@fmt.message key="contact-form-template-first-name" var="firstName"/>
<@fmt.message key="contact-form-template-last-name" var="lastName"/>
<@fmt.message key="contact-form-template-email-address" var="emailAddress"/>
<@fmt.message key="contact-form-template-mobile" var="mobileNumber"/>
<@fmt.message key="contact-form-request-title" var="requestTitle"/>
<@fmt.message key="contact-form-description-title" var="descriptionTitle"/>
<@fmt.message key="contact-form-upload-file-title" var="uploadTitle"/>
<@fmt.message key="contact-form-attach-file-tile" var="attachTitle"/>
<@fmt.message key="contact-form-submit" var="submit"/>
<@fmt.message key="contact-form-optional" var="optional"/>
<@fmt.message key="contact-form-header" var="contactForm"/>
<@fmt.message key="captcha_client_secret_Key" var="clientSeceretKey"/>
<@fmt.message key="contact-form-success-message" var="successMessage"/>
<@fmt.message key="contact-form-error-message" var="errorMessage"/>
<@fmt.message key="contact-form-request-type-error-message" var="requestTypeErrorMessage"/>
<@fmt.message key="contact-form-first-name-error-message" var="firstNameErrorMessage"/>
<@fmt.message key="contact-form-last-name-error-message" var="lastNameErrorMessage"/>
<@fmt.message key="contact-form-email-error-message" var="emailErrorMessage"/>
<@fmt.message key="contact-form-mobile-error-message" var="mobileErrorMessage"/>
<@fmt.message key="contact-form-description-error-message" var="descriptionErrorMessage"/>
<@fmt.message key="contact-form-google-captcha-error-message" var="captchaErrorMessage"/>
<@fmt.message key="contact-form-email-error-message2" var="emailErrorMessage2"/>
<@fmt.message key="contact-form-mobile-error-message2" var="mobileErrorMessage2"/>
<@fmt.message key="contact-form-file-error-message" var="fileErrorMessage"/>
<@fmt.message key="contact-form-file-size-error-message" var="fileSizeErrorMessage"/>
<@fmt.message key="contact-form-upload-note-message" var="fileUploadNoteMessage"/>
<@fmt.message key="contact-form-message-timing" var="messageTiming"/>
<@fmt.message key="contact-form-message-fading-timing" var="messageFadingTiming"/>
<@fmt.message key="contact-form-select" var="select"/>
<@fmt.message key="contact-form-privacy-policy-title" var="privacyPolicyTitle"/>
<@fmt.message key="contact-form-privacy-policy-link" var="privacyPolicyLink"/>
<#assign englishSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
    <#assign englishSite="/en" />
</#if>

<@hst.include ref="container"/>
<@hst.link var="thankyoupage" siteMapItemRefId="thankyoupage"/>


<input type="hidden" id="requestTypeErrorMessageLink" value="${requestTypeErrorMessage}" />
<input type="hidden" id="firstNameErrorMessageLink" value="${firstNameErrorMessage}" />
<input type="hidden" id="lastNameErrorMessageLink" value="${lastNameErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink" value="${emailErrorMessage}" />
<input type="hidden" id="mobileErrorMessageLink" value="${mobileErrorMessage}" />
<input type="hidden" id="captchaErrorMessageLink" value="${captchaErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink2" value="${emailErrorMessage2}" />
<input type="hidden" id="mobileErrorMessageLink2" value="${mobileErrorMessage2}" />
<input type="hidden" id="descriptionErrorMessageLink" value="${descriptionErrorMessage}" />
<input type="hidden" id="fileErrorMessageLink" value="${fileErrorMessage}" />
<input type="hidden" id="fileSizeErrorMessageLink" value="${fileSizeErrorMessage}" />
<input type="hidden" id="messageTimingLink" value="${messageTiming}" />
<input type="hidden" id="messageFadingTimingLink" value="${messageFadingTiming}" />
<section class="c-koh-contact-form">
        <div class="koh-contact-container">
            <div class="koh-contact-header">
                <span class="koh-contact-title">${contactForm}</span>
            </div>
            <div id="successMessage" class="hide">
                ${successMessage}
            </div>
            <div id="failureMessage" class="hide">
                ${errorMessage}
            </div>
            <div class="koh-contact-form-wrapper">
                <form method="POST" class="form-horizontal" action="${englishSite}/api/contactform" enctype="multipart/form-data" id="contactUsForm" autocomplete="off">
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="request-type">${requestTitle}</label>
                        <div class="col-sm-4">
                           <select class="form-control" id="koh-contact-request-type" name="koh-contact-request-type">
                                <option value="">${select}</option>
                                <option value="contact-form-drop-down-value1">${dropDown1}</option>
                                <option value="contact-form-drop-down-value2" >${dropDown2}</option>
                                <option value="contact-form-drop-down-value3">${dropDown3}</option>
                                <option value="contact-form-drop-down-value4">${dropDown4}</option>
                                <#if dropDown6?? && dropDown6?has_content>
                                	<option value="contact-form-drop-down-value6">${dropDown6}</option>
                            	</#if>
								<option value="contact-form-drop-down-value5">${dropDown5}</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-first-name">${firstName}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-first-name" maxlength="40" placeholder="" name="koh-contact-first-name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-last-name">${lastName}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-last-name" maxlength="40" placeholder="" name="koh-contact-last-name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-email-address">${emailAddress}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-email-address" maxlength="60" placeholder="" name="koh-contact-email-address">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-mobile-number">${mobileNumber}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-mobile-number" placeholder="" name="koh-contact-mobile-number" maxlength="20">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-description">${descriptionTitle}</label>
                        <div class="col-sm-4">   
                            <textarea class="form-control" rows="5" id="koh-contact-description" name="koh-contact-description" maxlength="4000"></textarea>
                        </div>
                    </div>
                    <#--<div class="form-group">
                       <label class="control-label col-sm-5 koh-upload-label" for="koh-contact-upload-file">${attachTitle} <span>${optional}</span></label>
                        <div class="col-sm-4">   
                            <div class="file btn koh-upload-wrapper col-sm-6">
                                ${uploadTitle}
                                <input type="file" name="file" id="koh-contact-upload-file"/>
                            </div>
                            <div id="koh-contact-upload-file-name"></div>
                            <div class="koh-contact-upload-note">${fileUploadNoteMessage}</div>
                        </div>
                    </div>-->
                    <div class="form-group">
                        <div class="col-sm-offset-5 col-sm-4 captcha-container">   
                            <div class="g-recaptcha" id="g-recaptcha" name="koh-contact-google-captcha" data-callback="recaptchaCallback" data-sitekey="${clientSeceretKey}"></div>
                        </div>
                    </div>
                    <div class="form-group">        
                        <div class="col-sm-offset-5 col-sm-4">
                        	<a class="koh-privacy-policy-link-cf" onclick="window.open('${privacyPolicyLink}', '_blank', 'location=yes,height=600,width=1000,scrollbars=yes,status=yes');">${privacyPolicyTitle}</a>
                            <button id="koh-contact-form-btn" type="submit" class="btn btn-default">${submit} </button>
                        </div>
                    </div>
                     <input type="hidden" class="form-control" id="koh-contact-ThankyouPage" name="koh-contact-ThankyouPage" value=${thankyoupage}>
                    
                </form>
            </div>
        </div>
</section>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-contact-form.min.js"></script>
</@hst.headContribution>

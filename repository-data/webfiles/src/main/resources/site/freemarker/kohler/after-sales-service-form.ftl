<#include "../include/imports.ftl">

<@fmt.message key="after-sales-service-form-main-title" var="mainTitle"/>
<@fmt.message key="after-sales-service-form-required" var="required"/>
<@fmt.message key="after-sales-service-form-name" var="firstName"/>
<@fmt.message key="after-sales-service-form-last-name" var="lastName"/>
<@fmt.message key="after-sales-service-form-email" var="emailAddress"/>
<@fmt.message key="after-sales-service-form-mobile" var="mobileNumber"/>
<@fmt.message key="after-sales-service-form-address" var="homeAddress"/>
<@fmt.message key="after-sales-service-form-purchase" var="purchasing"/>
<@fmt.message key="after-sales-service-form-community" var="community"/>
<@fmt.message key="after-sales-service-form-distributor-dealer" var="distributorOrDealer"/>
<@fmt.message key="after-sales-service-form-ecommerce" var="ecommerce"/>
<@fmt.message key="after-sales-service-form-sku" var="sku"/>
<@fmt.message key="after-sales-service-form-request" var="request"/>
<@fmt.message key="after-sales-service-form--source-purchase-placeholder" var="placeholder"/>
<@fmt.message key="after-sales-service-form-note" var="sourcePurchaseNote"/>



<@fmt.message key="contact-form-submit" var="submit"/>
<@fmt.message key="contact-form-optional" var="optional"/>
<@fmt.message key="contact-form-header" var="contactForm"/>
<@fmt.message key="captcha_client_secret_Key" var="clientSeceretKey"/>
<@fmt.message key="contact-form-success-message" var="successMessage"/>
<@fmt.message key="contact-form-error-message" var="errorMessage"/>

<@fmt.message key="contact-form-first-name-error-message" var="firstNameErrorMessage"/>
<@fmt.message key="contact-form-last-name-error-message" var="lastNameErrorMessage"/>
<@fmt.message key="contact-form-email-error-message" var="emailErrorMessage"/>
<@fmt.message key="contact-form-mobile-error-message" var="mobileErrorMessage"/>
<@fmt.message key="contact-form-google-captcha-error-message" var="captchaErrorMessage"/>
<@fmt.message key="contact-form-email-error-message2" var="emailErrorMessage2"/>
<@fmt.message key="contact-form-mobile-error-message2" var="mobileErrorMessage2"/>
<@fmt.message key="contact-form-message-timing" var="messageTiming"/>
<@fmt.message key="contact-form-message-fading-timing" var="messageFadingTiming"/>
<@fmt.message key="contact-form-select" var="select"/>
<@fmt.message key="contact-form-privacy-policy-title" var="privacyPolicyTitle"/>
<@fmt.message key="contact-form-privacy-policy-link" var="privacyPolicyLink"/>
<@fmt.message key="contact-form-address-error-message" var="homeAddressErrorMessage"/>
<@fmt.message key="contact-form-source-error-message" var="sourcePurchaseErrorMessage"/>
<@fmt.message key="contact-form-sku-error-message" var="skuErrorMessage"/>

<#assign englishSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
    <#assign englishSite="/en" />
</#if>

<@hst.include ref="container"/>
<@hst.link var="thankyoupage" siteMapItemRefId="thankyou-after-sales"/>


<input type="hidden" id="firstNameErrorMessageLink" value="${firstNameErrorMessage}" />
<input type="hidden" id="lastNameErrorMessageLink" value="${lastNameErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink" value="${emailErrorMessage}" />
<input type="hidden" id="mobileErrorMessageLink" value="${mobileErrorMessage}" />
<input type="hidden" id="captchaErrorMessageLink" value="${captchaErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink2" value="${emailErrorMessage2}" />
<input type="hidden" id="mobileErrorMessageLink2" value="${mobileErrorMessage2}" />
<input type="hidden" id="messageTimingLink" value="${messageTiming}" />
<input type="hidden" id="messageFadingTimingLink" value="${messageFadingTiming}" />
<input type="hidden" id="homeAddressErrorMessageLink" value="${homeAddressErrorMessage}" />
<input type="hidden" id="sourcePurchaseErrorMessageLink" value="${sourcePurchaseErrorMessage}" />
<input type="hidden" id="formErrorMessage" value="${errorMessage}" />
<input type="hidden" id="skuErrorMessage" value="${skuErrorMessage}" />


<section class="c-koh-warranty-registration-form">
        <div class="koh-contact-container">
            <div class="koh-contact-header">
                <span class="koh-contact-title">${mainTitle}</span>
                <span class="koh-contact-require">${required}</span>
            </div>
            <div id="failureMessage" class="hide">
                ${errorMessage}
            </div>
            <div class="koh-contact-form-wrapper">
                <form method="POST" class="form-horizontal" action="${englishSite}/api/afterSalesService" enctype="multipart/form-data" id="afterSalesServiceForm" autocomplete="off">
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-contact-first-name">${firstName}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-first-name" placeholder="" name="koh-contact-first-name">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-contact-last-name">${lastName}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-last-name" placeholder="" name="koh-contact-last-name">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-contact-email-address">${emailAddress}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-email-address" placeholder="" name="koh-contact-email-address">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-contact-mobile-number">${mobileNumber}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-mobile-number" placeholder="" name="koh-contact-mobile-number" maxlength="20">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-contact-address">${homeAddress}</label>
                        <div class="col-sm-4">   
                            <textarea class="form-control" rows="5" id="koh-contact-address" name="koh-contact-address"></textarea>
                        </div>
                    </div>
                    
                    <div class="form-group">
						<label class="control-label col-sm-5 koh-required" for="koh-contact-multiple-choice">${purchasing}</label>
						<div class="col-sm-4"  id="koh-source-purchase-id">
							<label class="checkbox-about-kohler"><input type="radio" class="about-kohler" id="koh-contact-community-checkbox" name="koh-source-of-purchasing" value="${community}" checked>${community}</label>
							<label class="checkbox-about-kohler"><input type="radio" class="about-kohler" id="koh-contact-distributorOrDealer-checkbox" name="koh-source-of-purchasing" value="${distributorOrDealer}">${distributorOrDealer}</label>
							<label class="checkbox-about-kohler"><input type="radio" class="about-kohler" id="koh-contact-ecommerce-checkbox" name="koh-source-of-purchasing" value="${ecommerce}">${ecommerce}</label>
                            <input type="text" class="form-control" id="koh-source-purchase-name" placeholder="${placeholder}" name="koh-source-purchase-name">
                            <div class="koh-wheretobuy-note">${sourcePurchaseNote}</div>
                        </div>
					</div>
					
					<div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-contact-description">${sku}</label>
                        <div class="col-sm-4">   
                            <input type="text" class="form-control" rows="5" id="koh-contact-sku" name="koh-contact-sku">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-description">${request}</label>
                        <div class="col-sm-4">   
                            <textarea class="form-control" rows="5" id="koh-contact-request" name="koh-contact-request"></textarea>
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
    <script type="text/javascript" src="${link}/js/custom/koh-after-sales-service-form.min.js"></script>
</@hst.headContribution>

<#include "../include/imports.ftl">

<@fmt.message key="contact-form-landing-template-first-name" var="firstName"/>
<@fmt.message key="contact-form-landing-template-email-address" var="emailAddress"/>
<@fmt.message key="contact-form-landing-template-mobile" var="mobileNumber"/>
<@fmt.message key="contact-form-landing-request-title" var="districtOfResidence"/>
<@fmt.message key="contact-form-landing-submit" var="submit"/>
<@fmt.message key="contact-form-landing-header" var="contactForm"/>
<@fmt.message key="captcha_client_secret_Key" var="clientSeceretKey"/>
<@fmt.message key="contact-form-landing-success-message" var="successMessage"/>
<@fmt.message key="contact-form-landing-error-message" var="errorMessage"/>
<@fmt.message key="contact-form-landing-first-name-error-message" var="firstNameErrorMessage"/>
<@fmt.message key="contact-form-landing-email-error-message" var="emailErrorMessage"/>
<@fmt.message key="contact-form-landing-mobile-error-message" var="mobileErrorMessage"/>
<@fmt.message key="contact-form-landing-google-captcha-error-message" var="captchaErrorMessage"/>
<@fmt.message key="contact-form-landing-email-error-message2" var="emailErrorMessage2"/>
<@fmt.message key="contact-form-landing-mobile-error-message2" var="mobileErrorMessage2"/>
<@fmt.message key="contact-form-landing-message-timing" var="messageTiming"/>
<@fmt.message key="contact-form-landing-message-fading-timing" var="messageFadingTiming"/>
<@fmt.message key="contact-form-landing-select" var="select"/>
<@fmt.message key="contact-form-landing-privacy-policy-title" var="privacyPolicyTitle"/>
<@fmt.message key="contact-form-landing-privacy-policy-link" var="privacyPolicyLink"/>
<@fmt.message key="contact-form-landing-promo-package-title" var="promoPackageTitle"/>
<@fmt.message key="contact-form-landing-store-error-message" var="selectStoreErrorMessage"/>
<@fmt.message key="contact-form-landing-request-type-error-message" var="selectRamadanPackageErrorMessage"/>
<@fmt.message key="promo-home-address-tiltle" var="homeAddressTitle"/>

<#assign englishSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
    <#assign englishSite="/en" />
</#if>

<@hst.link var="promoThankYouPage" siteMapItemRefId="bathtubPromoThankYou"/>

<input type="hidden" id="firstNameErrorMessageLink" value="${firstNameErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink" value="${emailErrorMessage}" />
<input type="hidden" id="mobileErrorMessageLink" value="${mobileErrorMessage}" />
<input type="hidden" id="captchaErrorMessageLink" value="${captchaErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink2" value="${emailErrorMessage2}" />
<input type="hidden" id="mobileErrorMessageLink2" value="${mobileErrorMessage2}" />
<input type="hidden" id="messageTimingLink" value="${messageTiming}" />
<input type="hidden" id="messageFadingTimingLink" value="${messageFadingTiming}" />
<input type="hidden" id="selectStoreErrorMessage" value="${selectStoreErrorMessage}" />
<input type="hidden" id="selectRamadanPackageErrorMessage" value="${selectRamadanPackageErrorMessage}" />
<#if promoFormComponent??>
	<section class="c-koh-contact-form">
	    <div class="koh-contact-container">
	    <div class = "ramadan-promo-underline"></div>
	        <div id="successMessage" class="hide">
	            ${successMessage}
	        </div>
	        <div id="failureMessage" class="hide">
	            ${errorMessage}
	        </div>
	        <div class="koh-contact-form-wrapper">
	            <form method="POST" class="form-horizontal" action="${englishSite}/api/landingpageservice/bathtubpromotion" enctype="multipart/form-data" id="bathtubcontactform" autocomplete="off">
	            	 	<div class="form-group">
	                        <label class="control-label col-sm-5" for="koh-contact-first-name">${firstName}</label>
	                        <div class="col-sm-4">          
	                            <input type="text" class="form-control" id="koh-contact-first-name" maxlength="40" placeholder="" name="koh-contact-first-name">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label col-sm-5" for="koh-contact-mobile-number">${mobileNumber}</label>
	                        <div class="col-sm-4">          
	                            <input type="text" class="form-control" id="koh-contact-mobile-number" placeholder="" name="koh-contact-mobile-number" maxlength="20">
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label col-sm-5" for="koh-contact-email-address">${emailAddress}</label>
	                        <div class="col-sm-4">          
	                            <input type="text" class="form-control" id="koh-contact-email-address" maxlength="60" placeholder="" name="koh-contact-email-address">
	                        </div>
	                    </div>
	                <#if storeAndPackageBean??>
	            		<#if storeAndPackageBean.kohler_storecompound?? && storeAndPackageBean.kohler_storecompound?has_content>
		                    <div class="form-group">
		                        <label class="control-label col-sm-5" for="districts-request-type">${districtOfResidence}</label>
		                        <div class="col-sm-4">
		                           <select class="form-control" id="koh-districts-request-type" name="koh-districts-request-type">
		                                <option value="">${select}</option>
		                                <#list storeAndPackageBean.kohler_storecompound as storeList>
	    									<#if storeList.storeNames?? && storeList.storeNames?has_content>
	                        					<option value="${storeList.storeNames}">${storeList.storeNames}</option>
	                    					</#if>
	            						</#list>
		                            </select>
		                        </div>
		                    </div>
	                	</#if>
	                </#if>
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
	                 <input type="hidden" class="form-control" id="koh-contact-ThankyouPage" name="koh-contact-ThankyouPage" value=${promoThankYouPage}>
	            </form>
	        </div>
	    </div>
	</section>
</#if>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-bathtub-contact-form.min.js"></script>
</@hst.headContribution>

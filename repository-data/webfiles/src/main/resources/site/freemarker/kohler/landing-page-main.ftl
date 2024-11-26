<#include "../include/imports.ftl">

<@hst.include ref="container"/>
<@fmt.message key="contact-form-landing-drop-down-value1" var="dropDown1"/>
<@fmt.message key="contact-form-landing-drop-down-value2" var="dropDown2"/>
<@fmt.message key="contact-form-landing-drop-down-value3" var="dropDown3"/>
<@fmt.message key="contact-form-landing-drop-down-value4" var="dropDown4"/>
<@fmt.message key="contact-form-landing-drop-down-value5" var="dropDown5"/>
<@fmt.message key="contact-form-landing-drop-down-value6" var="dropDown6"/>
<@fmt.message key="contact-form-landing-drop-down-value7" var="dropDown7"/>
<@fmt.message key="contact-form-landing-district1" var="district1"/>
<@fmt.message key="contact-form-landing-district2" var="district2"/>
<@fmt.message key="contact-form-landing-district3" var="district3"/>
<@fmt.message key="contact-form-landing-district4" var="district4"/>
<@fmt.message key="contact-form-landing-district5" var="district5"/>
<@fmt.message key="contact-form-landing-district6" var="district6"/>
<@fmt.message key="contact-form-landing-district7" var="district7"/>
<@fmt.message key="contact-form-landing-district8" var="district8"/>
<@fmt.message key="contact-form-landing-district9" var="district9"/>
<@fmt.message key="contact-form-landing-district10" var="district10"/>
<@fmt.message key="contact-form-landing-district11" var="district11"/>
<@fmt.message key="contact-form-landing-district12" var="district12"/>
<@fmt.message key="contact-form-landing-district13" var="district13"/>
<@fmt.message key="contact-form-landing-district14" var="district14"/>
<@fmt.message key="contact-form-landing-district15" var="district15"/>

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

<#assign englishSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
    <#assign englishSite="/en" />
</#if>

<@hst.link var="promoThankYouPage" siteMapItemRefId="promoThankYouPage"/>


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
	                <form method="POST" class="form-horizontal" action="${englishSite}/api/contactpromotionform" enctype="multipart/form-data" id="contactUsPromoForm" autocomplete="off">
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
	                    <div class="form-group">
	                        <label class="control-label col-sm-5" for="districts-request-type">${districtOfResidence}</label>
	                        <div class="col-sm-4">
	                           <select class="form-control" id="koh-districts-request-type" name="koh-districts-request-type">
	                                <option value="">${select}</option>
	                                <option value="contact-form-landing-district1">${district1}</option>
	                                <option value="contact-form-landing-district2">${district2}</option>
	                                <option value="contact-form-landing-district3">${district3}</option>
	                                <option value="contact-form-landing-district4">${district4}</option>
	                                <option value="contact-form-landing-district5">${district5}</option>
	                                <option value="contact-form-landing-district6">${district6}</option>
	                                <option value="contact-form-landing-district7">${district7}</option>
	                                <option value="contact-form-landing-district8">${district8}</option>
	                                <option value="contact-form-landing-district9">${district9}</option>
	                                <option value="contact-form-landing-district10">${district10}</option>
	                                <option value="contact-form-landing-district11">${district11}</option>
	                                <option value="contact-form-landing-district12">${district12}</option>
	                                <option value="contact-form-landing-district13">${district13}</option>
	                                <option value="contact-form-landing-district14">${district14}</option>
	                                <option value="contact-form-landing-district15">${district15}</option>
	                            </select>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label col-sm-5" for="request-type">${promoPackageTitle}</label>
	                        <div class="col-sm-4">
	                           <select class="form-control" id="koh-contact-request-type" name="koh-contact-request-type">
	                                <option value="">${select}</option>
	                                <option value="contact-form-landing-drop-down-value1">${dropDown1}</option>
	                                <option value="contact-form-landing-drop-down-value2">${dropDown2}</option>
	                                <option value="contact-form-landing-drop-down-value3">${dropDown3}</option>
	                                <option value="contact-form-landing-drop-down-value4">${dropDown4}</option>
	                                <option value="contact-form-landing-drop-down-value5">${dropDown5}</option>
	                                <option value="contact-form-landing-drop-down-value6">${dropDown6}</option>
	                                <option value="contact-form-landing-drop-down-value7">${dropDown7}</option>
	                            </select>
	                        </div>
	                    </div>
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
<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-landing-page-contact-form.min.js"></script>
</@hst.headContribution>

 <#if pageable?? && pageable.items?has_content>
	<section class = "c-koh-ramadan-store-list">
		<div class = "koh-ramadan-store-list-container">
			<#list pageable.items as item>
				<#if item.imagePath?? && item.imagePath?has_content>
					<div class = "koh-top-static-image">
						<img src="${item.imagePath}">
					</div>
				</#if>
				<#if item.title?? && item.title?has_content>
					<div class = "koh-ramadan-store-header">
		                <span class="koh-ramadan-store-list-title">${item.title}</span>
		            </div>
	            </#if>
				<div class = "row koh-ramadan-store-list-row">
					<#if item.kohler_promolandingpagestorelistcompound??>
				 		<#list item.kohler_promolandingpagestorelistcompound as storeList>
					 			<#if storeList.address.content?? && storeList.address.content?has_content>
				                    <div class = "col-lg-4 col-md-4 col-sm-4 col-xs-12 koh-ramadan-list-col">
				                       <@hst.html hippohtml=storeList.address/>
				                    </div>
			                    </#if>
	                		</li>
	            		</#list>
	        		</#if>
    			</div>
	        </#list>   
		</div>
	</section>
</#if>
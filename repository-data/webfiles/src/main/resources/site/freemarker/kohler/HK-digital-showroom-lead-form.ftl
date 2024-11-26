<#include "../include/imports.ftl">

<@fmt.message key="sfmc-campaign-form-template-name" var="name"/>
<@fmt.message key="sfmc-campaign-email-label" var="emailAddress"/>
<@fmt.message key="sfmc-campaign-form-template-mobile" var="mobileNumber"/>
<@fmt.message key="sfmc-campaign-form-heading" var="formHeading"/>
<@fmt.message key="contact-form-submit" var="submit"/>
<@fmt.message key="contact-form-success-message" var="successMessage"/>
<@fmt.message key="contact-form-error-message" var="errorMessage"/>
<@fmt.message key="contact-form-request-type-error-message" var="requestTypeErrorMessage"/>
<@fmt.message key="sfmc-digital-form-name-error-message" var="nameErrorMessage"/>
<@fmt.message key="contact-form-email-error-message" var="emailErrorMessage"/>
<@fmt.message key="contact-form-mobile-error-message" var="mobileErrorMessage"/>
<@fmt.message key="contact-form-email-error-message2" var="emailErrorMessage2"/>
<@fmt.message key="contact-form-mobile-error-message2" var="mobileErrorMessage2"/>
<@fmt.message key="contact-form-message-timing" var="messageTiming"/>
<@fmt.message key="contact-form-message-fading-timing" var="messageFadingTiming"/>
<@fmt.message key="contact-form-privacy-policy-title" var="privacyPolicyTitle"/>
<@fmt.message key="contact-form-privacy-policy-link" var="privacyPolicyLink"/>
<@fmt.message key="warranty-tc-checkbox-1stpart" var="Part1"/>
<@fmt.message key="warranty-tc-checkbox-2ndpart" var="Part2"/>
<@fmt.message key="warranty-tc-checkbox-and" var="and"/>
<@fmt.message key="warranty-form-tclink" var="tcLink"/>
<@fmt.message key="warranty-privacy-error-message" var="privacyErrorMessage"/>
<@fmt.message key="warranty-choose-label" var="choose"/>
<@fmt.message key="sfmc-campaing-bathroom-fixture-label" var="bathroomFixtureLabel"/>
<@fmt.message key="sfmc-campaing-bathroom-fixture-options" var="bathroomFixtureDropdownOptions"/>
<@fmt.message key="sfmc-campaing-interested-product-label" var="interestedProductsLabel"/>
<@fmt.message key="sfmc-campaign-interested-product-dropdown-options" var="interestDropdownOptions"/>
<@fmt.message key="sfmc-campaign-bathroom-fixture-error-message" var="bathroomFixtureErrorMessage"/>
<@fmt.message key="sfmc-campaign-interested-product-error-message" var="interestedProductErrorMessage"/>


<#assign englishSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
    <#assign englishSite="/en" />
</#if>

<@hst.link var="hkDigitalShowroomThankYou" siteMapItemRefId="hkDigitalShowroomThankYou"/>


<input type="hidden" id="requestTypeErrorMessageLink" value="${requestTypeErrorMessage}" />
<input type="hidden" id="nameErrorMessageLink" value="${nameErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink" value="${emailErrorMessage}" />
<input type="hidden" id="mobileErrorMessageLink" value="${mobileErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink2" value="${emailErrorMessage2}" />
<input type="hidden" id="mobileErrorMessageLink2" value="${mobileErrorMessage2}" />
<input type="hidden" id="messageTimingLink" value="${messageTiming}" />
<input type="hidden" id="messageFadingTimingLink" value="${messageFadingTiming}" />
<input type="hidden" id="privacyErrorMessage" value="${privacyErrorMessage}"/>
<input type="hidden" id="hk-digital-ThankyouPage" value=${hkDigitalShowroomThankYou}>
<input type="hidden" id="formErrorMessage" value="${errorMessage}" />
<input type="hidden" id="bathroomFixtureErrorMessage" value="${bathroomFixtureErrorMessage}" />
<input type="hidden" id="interestedProductErrorMessage" value="${interestedProductErrorMessage}" />

<section class="c-koh-warranty-registration-form">
        <div class="koh-contact-container">
            <div class="koh-contact-header">
                <span class="koh-contact-title">${formHeading}</span>
            </div>
            <div id="successMessage" class="hide">
                ${successMessage}
            </div>
            <div id="failureMessage" class="hide">
                ${errorMessage}
            </div>
            <div class="koh-contact-form-wrapper">
                <form method="POST" class="form-horizontal" action="${englishSite}/api/hkdigitalshowroom" enctype="multipart/form-data" id="hkDigitalLeadForm" autocomplete="off">
                     <div class="form-group">
		                <label class="control-label col-sm-5" for="koh-bathroom-fixtures">${bathroomFixtureLabel}</label>
		                <div class="col-sm-4">
		                    <select class="form-control" id="koh-bathroom-fixtures" name="koh-bathroom-fixtures">
		                        <option value="">${choose}</option>
		                        <#list bathroomFixtureDropdownOptions?split(",") as bathroomFixtureDropdownList>
		                        	<option value="${bathroomFixtureDropdownList}">${bathroomFixtureDropdownList}</option>
		                        </#list>
		                    </select>
		                </div>
		            </div>
                    <div class="form-group">
		                <label class="control-label col-sm-5" for="koh-interest-products">${interestedProductsLabel}</label>
		                <div class="col-sm-4">
		                    <select class="form-control" id="koh-interest-products" name="koh-interest-products">
		                        <option value="">${choose}</option>
		                        <#list interestDropdownOptions?split(",") as interestedProductsDropdownList>
		                        	<option value="${interestedProductsDropdownList}">${interestedProductsDropdownList}</option>
		                        </#list>
		                    </select>
		                </div>
		            </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="hk-digital-form-name">${name}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="hk-digital-form-name" maxlength="254" placeholder="" name="hk-digital-form-name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="hk-digital-form-mobile-number">${mobileNumber}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="hk-digital-form-mobile-number" placeholder="" name="hk-digital-form-mobile-number" maxlength="15">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="hk-digital-form-email-address">${emailAddress}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="hk-digital-form-email-address" maxlength="254" placeholder="" name="hk-digital-form-email-address">
                        </div>
                    </div>
                    <div class="form-group">        
                        <div class="col-sm-offset-5 col-sm-4">
                            <label class="checkbox-label" id="hk-privacy-label">
								<input type="checkbox" class="hk-digital-checkbox" id="kohler-warranty-privacy-policy" name="warrantyPrivacyPolicy" value="TRUE">
								<div class="koh-privacy-sfmc-promo">
									${Part1} <a class="koh-privacy-policy-link-sfmc-cf" onclick="window.open('${tcLink}', '_blank', 'location=yes,height=600,width=1000,scrollbars=yes,status=yes');">${Part2}</a> ${and} <a class="koh-privacy-policy-link-sfmc-cf" onclick="window.open('${privacyPolicyLink}', '_blank', 'location=yes,height=600,width=1000,scrollbars=yes,status=yes');">${privacyPolicyTitle}</a>
								</div>
							</label>
                        </div>
                    </div>
                    <div class="form-group">        
                        <div class="col-sm-offset-5 col-sm-4">
                            <button id="hk-digital-from-btn" type="submit" class="btn btn-default">${submit}</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
</section>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-digital-showroom-form.min.js"></script>
</@hst.headContribution>

<#include "../include/imports.ftl">
<@fmt.message key="ibt-registration-form-title" var="formTitle"/>
<@fmt.message key="ibt-registration-subheading" var="subHeading"/>
<@fmt.message key="ibt-registration-required-message" var="requiredMessage"/>
<@fmt.message key="ibt-registration-project-type-label" var="projectTypeLabel"/>
<@fmt.message key="ibt-registration-project-type-placeholder" var="projectTypePlaceholder"/>
<@fmt.message key="ibt-registration-project-as-label" var="projectAsLabel"/>
<@fmt.message key="ibt-registartion-project-as-placeholder" var="projectAsPlaceholder"/>
<@fmt.message key="ibt-registration-budger-range-label" var="budgetRangeLabel"/>
<@fmt.message key="ibt-registration-budget-range-placeholder" var="budgetRangePlaceholder"/>
<@fmt.message key="ibt-registration-timeline-project-label" var="timelineProjectLabel"/>
<@fmt.message key="ibt-registration-timeline-project-placeholder" var="timelineProjectPlaceholder"/>
<@fmt.message key="ibt-registration-project-location-label" var="projectLocationLabel"/>
<@fmt.message key="ibt-registration-project-location-placeholder" var="projectLocationPlaceholder"/>
<@fmt.message key="ibt-registration-customer-name-label" var="customerNameLabel"/>
<@fmt.message key="ibt-registration-customer-name-placeholder" var="customerNamePlaceholder"/>
<@fmt.message key="ibt-registration-customer-email-label" var="customerEmailLabel"/>
<@fmt.message key="ibt-registration-customer-email-placeholder" var="customerEmailPlaceholder"/>
<@fmt.message key="ibt-registration-phone-number-label" var="phoneNumberLabel"/>
<@fmt.message key="ibt-registration-phone-number-placeholder" var="phoneNumberPlaceholder"/>
<@fmt.message key="captcha_client_secret_Key" var="clientSeceretKey"/>
<@fmt.message key="ibt-registration-project-type-option-list" var="projectTypeOptionList"/>
<@fmt.message key="ibt-registration-project-as-option-list" var="projectAsOptionList"/>
<@fmt.message key="ibt-registration-budget-range-option-list" var="budgetRangeOptionList"/>
<@fmt.message key="ibt-registration-timeline-project-option-list" var="timelineProjectOptionList"/>

<@fmt.message key="warranty-tc-checkbox-1stpart" var="Part1"/>
<@fmt.message key="warranty-tc-checkbox-2ndpart" var="Part2"/>
<@fmt.message key="warranty-privacy-label" var="privacyPolicyTitle"/>
<@fmt.message key="warranty-tc-checkbox-and" var="and"/>
<@fmt.message key="warranty-form-tclink" var="tcLink"/>
<@fmt.message key="warranty-ci-checkbox-content" var="ciCheckboxContent"/>
<@fmt.message key="warranty-submit-label" var="submit"/>
<@fmt.message key="contact-form-message-fading-timing" var="messageFadingTiming"/>
<@fmt.message key="contact-form-message-timing" var="messageTiming"/>
<@fmt.message key="warranty-privacy-link" var="privacyPolicyLink"/>

<@fmt.message key="ibt-registration-submit-error-message" var="submitErrorMessage"/>
<@fmt.message key="ibt-registration-project-type-error-message" var="projectTypeErrorMessage"/>
<@fmt.message key="ibt-registration-project-as-error-message" var="projectAsErrorMessage"/>
<@fmt.message key="ibt-registration-budget-range-error-message" var="budgetRangeErrorMessage"/>
<@fmt.message key="ibt-registration-timeline-project-error-message" var="timelineProjectErrorMessage"/>
<@fmt.message key="ibt-registration-project-location-error-message" var="projectLocationErrorMessage"/>
<@fmt.message key="ibt-registration-customer-name-error-message" var="customerNameErrorMessage"/>
<@fmt.message key="ibt-registration-phone-number-error-message" var="phoneNumberErrorMessage"/>
<@fmt.message key="warranty-privacy-error-message" var="privacyErrorMessage"/>
<@fmt.message key="warranty-email-error-message1" var="emailErrorMessage1"/>
<@fmt.message key="warranty-email-error-message2" var="emailErrorMessage2"/>
<@fmt.message key="warranty-captcha-error-message" var="captchaErrorMessage"/>

<input type="hidden" id="projectTypeErrorMessage" value="${projectTypeErrorMessage}"/>
<input type="hidden" id="projectAsErrorMessage" value="${projectAsErrorMessage}"/>
<input type="hidden" id="budgetRangeErrorMessage" value="${budgetRangeErrorMessage}"/>
<input type="hidden" id="timelineProjectErrorMessage" value="${timelineProjectErrorMessage}"/>
<input type="hidden" id="projectLocationErrorMessage" value="${projectLocationErrorMessage}"/>
<input type="hidden" id="customerNameErrorMessage" value="${customerNameErrorMessage}"/>
<input type="hidden" id="phoneNumberErrorMessage" value="${phoneNumberErrorMessage}"/>
<input type="hidden" id="privacyErrorMessage" value="${privacyErrorMessage}"/>
<input type="hidden" id="emailErrorMessage1Id" value="${emailErrorMessage1}"/>
<input type="hidden" id="emailErrorMessage2Id" value="${emailErrorMessage2}"/>
<input type="hidden" id="captchaErrorMessageId" value="${captchaErrorMessage}"/>

<input type="hidden" id="messageFadingTimingLink" value="${messageFadingTiming}" />
<input type="hidden" id="messageTimingLink" value="${messageTiming}" />


<#assign englishSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
    <#assign englishSite="/en" />
</#if>

<@hst.link var="thankyoupage" siteMapItemRefId="idbrandthankyoupage"/>
<input type="hidden" id="koh-brand-registration-thankyouPage" value=${thankyoupage}/>

<section class="c-koh-brand-registration-form">
    <div class="brand-form-container">
        <div class="brand-form-title">${formTitle}</div>
        <div class="brand-form-sub-heading">${subHeading}</div>
        <div class="submit-error-message hide" id="submit-error-message">${submitErrorMessage}</div>
        <div class="brand-form-require">${requiredMessage}</div>
        <form method="POST" class="brand-registration-form" action="${englishSite}/api/IBTform" enctype="multipart/form-data" id="idBrandRegistratationForm" autocomplete="off">
            <div class="brand-form-fields-wrapper">
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                        <label class="control-label brand-label input-required" for="koh-brand-project-type">
                            ${projectTypeLabel}
                        </label>
                        <div class="multi-checkbox-wrapper" id="multi-checkbox-wrapper">
                            <#list projectTypeOptionList?split(",") as projectTypeOptions>
                                <#assign updatedProjectTypeString = projectTypeOptions?replace(" ", "") />
                                <label class="multi-checkbox-label">
                                    <input type="checkbox" class="checkbox-round" id="koh-checkbox-${updatedProjectTypeString}" name="koh-warranty-about-kohler" value="${projectTypeOptions}">
                                    ${projectTypeOptions}
                                </label>
                            </#list>
                        </div>
                    </div>
                </div>
            </div> 
            <div class="brand-form-fields-wrapper">
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                            <label class="control-label brand-label input-required" for="koh-brand-project-as">
                                ${projectAsLabel}
                            </label>
                            <select class="form-control brand-input custom-select" id="koh-brand-project-as" name="koh-brand-project-as">
                                <option value="">${projectAsPlaceholder}</option>
                                 <#list projectAsOptionList?split(",") as projectAsOptions>
                                    <option value="${projectAsOptions}">${projectAsOptions}</option>
                                </#list>
                            </select>
                    </div>
                </div>
            </div>
            <div class="brand-form-fields-wrapper">
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                            <label class="control-label brand-label input-required" for="koh-brand-budget-range">
                                ${budgetRangeLabel}
                            </label>
                            <select class="form-control brand-input custom-select" id="koh-brand-budget-range" name="koh-brand-budget-range">
                                <option value="">${budgetRangePlaceholder}</option>
                                <#list budgetRangeOptionList?split(",") as budgetRangeOptions>
                                    <option value="${budgetRangeOptions}">${budgetRangeOptions}</option>
                                </#list>
                            </select>
                    </div>
                </div>
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                            <label class="control-label brand-label input-required" for="koh-brand-timeline-project">
                                ${timelineProjectLabel}
                            </label>
                            <select class="form-control brand-input custom-select" id="koh-brand-timeline-project" name="koh-brand-timeline-project">
                                <option value="">${timelineProjectPlaceholder}</option>
                                 <#list timelineProjectOptionList?split(",") as timelineProjectOptions>
                                    <option value="${timelineProjectOptions}">${timelineProjectOptions}</option>
                                </#list>
                            </select>
                    </div>
                </div>
            </div>
            <div class="brand-form-fields-wrapper">
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                            <label class="control-label brand-label input-required" for="koh-brand-project-location">
                                ${projectLocationLabel}
                            </label>
                            <input type="text" class="form-control brand-input" id="koh-brand-project-location" placeholder="${projectLocationPlaceholder}" name="koh-brand-project-location" maxlength="1000">
                    </div>
                </div>
            </div>
            <div class="brand-form-fields-wrapper">
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                            <label class="control-label brand-label input-required" for="koh-brand-customer-name">
                                ${customerNameLabel}
                            </label>
                            <input type="text" class="form-control brand-input" id="koh-brand-customer-name" placeholder="${customerNamePlaceholder}" name="koh-brand-customer-name" maxlength="254">
                    </div>
                </div>
            </div>
            <div class="brand-form-fields-wrapper">
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                            <label class="control-label brand-label input-required" for="koh-brand-customer-email">
                                ${customerEmailLabel}
                            </label>
                            <input type="email" class="form-control brand-input" id="koh-brand-customer-email" placeholder="${customerEmailPlaceholder}" name="koh-brand-customer-email" maxlength="254">
                    </div>
                </div>
            </div>
            <div class="brand-form-fields-wrapper">
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                            <label class="control-label brand-label input-required" for="koh-brand-phone-number">
                                ${phoneNumberLabel}
                            </label>
                            <input type="text" class="form-control brand-input" id="koh-brand-phone-number" placeholder="${phoneNumberPlaceholder}" name="koh-brand-phone-number" maxlength="15">
                    </div>
                </div>
            </div>
            <div class="brand-form-fields-wrapper">
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                        <label class="checkbox-container input-required" id="privacy-checkbox">
                            <input type="checkbox" id="kohler-warranty-privacy-policy" name="warrantyPrivacyPolicy" value="TRUE">
                            <div class="koh-privacy-sfmc-promo">
                                ${Part1} <a class="koh-privacy-policy-link-sfmc-cf" onclick="window.open('${tcLink}', '_blank', 'location=yes,height=600,width=1000,scrollbars=yes,status=yes');">${Part2}</a> ${and} <a class="koh-privacy-policy-link-sfmc-cf" onclick="window.open('${privacyPolicyLink}', '_blank', 'location=yes,height=600,width=1000,scrollbars=yes,status=yes');">${privacyPolicyTitle}</a>
                            </div>
                        </label>
                    </div>
                </div>
            </div>
            <div class="brand-form-fields-wrapper">
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                        <label class="checkbox-container">
                            <input type="checkbox" id="kohler-contact-info" name="warrantyContactInformation" value="TRUE">
                            <div class="koh-privacy-sfmc-ci-promo">
                                ${ciCheckboxContent}
                            </div>
                        </label>
                    </div>
                </div>
            </div>
            <div class="brand-form-fields-wrapper">
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                        <div class="g-recaptcha captcha-brand-form" id="g-recaptcha" name="koh-contact-google-captcha" data-callback="recaptchaCallback" data-sitekey="${clientSeceretKey}"></div>
                    </div>
                </div>
            </div>
            <div class="brand-form-fields-wrapper">
                <div class="brand-form-group">
                    <div class="brand-input-wrapper">
                        <button id="brand-submit-btn" type="submit" class="brand-submit-button btn">${submit}</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>


<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-id-brand-registration-form.min.js"></script>
</@hst.headContribution>
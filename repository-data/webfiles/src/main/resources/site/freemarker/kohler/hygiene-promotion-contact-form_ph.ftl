<#include "../include/imports.ftl">
<@fmt.message key="hygiene-contact-form-header" var="contactForm"/>
<@fmt.message key="hygene-contact-form-name" var="firstName"/>
<@fmt.message key="hygiene-contact-form-email" var="emailAddress"/>
<@fmt.message key="hygene-contact-form-mobileno" var="mobileNumber"/>
<@fmt.message key="hygene-promo-home-address-tiltle" var="homeAddress"/>
<@fmt.message key="hygiene-form-selectstoreName" var="storeTitle"/>
<@fmt.message key="hygene-contact-form-collection-title" var="collectionTitle"/>
<@fmt.message key="hygiene-form-privacyPolicyLink" var="privacyPolicyLink"/>
<@fmt.message key="hygiene-form-privacyPolicyTitle" var="privacyPolicyTitle"/>
<@fmt.message key="hygiene-contact-form-select" var="select"/>
<@fmt.message key="hygiene-form-store-drop-down-values" var="storList"/>
<@fmt.message key="hygiene-form-collection-dropdown-value" var="collectionList"/>
<@fmt.message key="hygiene-form-submit" var="submit"/>

<@fmt.message key="captcha_client_secret_Key" var="clientSeceretKey"/>
<@fmt.message key="contact-form-success-message" var="successMessage"/>
<@fmt.message key="contact-form-error-message" var="errorMessage"/>
<@fmt.message key="contact-form-google-captcha-error-message" var="captchaErrorMessage"/>

<@fmt.message key="hygene-contact-form-collection-error-message" var="collectionErrorMessage"/>
<@fmt.message key="hygene-contact-form-store-error-message" var="storeErrorMessage"/>
<@fmt.message key="hygene-contact-form-first-name-error-message" var="firstNameErrorMessage"/>
<@fmt.message key="hygiene-contact-form-email-error-message" var="emailErrorMessage"/>
<@fmt.message key="hygiene-contact-form-email-error-message2" var="emailErrorMessage2"/>
<@fmt.message key="hygiene-contact-form-mobile-error-message" var="mobileErrorMessage"/>
<@fmt.message key="hygiene-form-homeAddress-error-message" var="homeAddressErrorMessage"/>


<#assign englishSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
    <#assign englishSite="/en" />
</#if>

<@hst.include ref="container"/>

<input type="hidden" id="firstNameErrorMessageLink" value="${firstNameErrorMessage}" />
<input type="hidden" id="storeErrorMessageLink" value="${storeErrorMessage}" />
<input type="hidden" id="interestedErrorMessageLink" value="${collectionErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink" value="${emailErrorMessage}" />
<input type="hidden" id="emailErrorMessageLink2" value="${emailErrorMessage2}" />
<input type="hidden" id="mobileErrorMessageLink" value="${mobileErrorMessage}" />
<input type="hidden" id="captchaErrorMessageLink" value="${captchaErrorMessage}" />
<input type="hidden" id="homeAddressErrorMessageLink" value="${homeAddressErrorMessage}" />


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
               <form class="form-horizontal"  action="<@hst.actionURL escapeXml=false />" method="post" name="${form.name!}" id="hygienecontactform"
                 <#if form.multipart>enctype="multipart/form-data"</#if> autocomplete="off" >
                   
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-first-name">${firstName}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-first-name" maxlength="40" placeholder="" name="name" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-mobile-number">${mobileNumber}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-mobile-number" placeholder="" name="mobileno" maxlength="20">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="home-adress-type">${homeAddress}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-home-adress-type" maxlength="300" placeholder="" name="address">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-email-address">${emailAddress}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-email-address" maxlength="60" placeholder="" name="email" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-score">${storeTitle}</label>
                        <div class="col-sm-4">          
                             <select class="form-control" id="koh-contact-score" name="store">
                                <option value="">${select}</option>
                              <#list storList?split("&") as storeValue>
							     <option value="${storeValue}" >${storeValue}</option>
							  </#list>
                            </select>
                        </div>
                    </div>
                    
                     <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-interestedproducts">${collectionTitle}</label>
                        <div class="col-sm-4">
                           <select class="form-control" id="koh-contact-interestedproducts" name="collections">
                           			<option value="">${select}</option>
                                  <#list collectionList?split(",") as collectionValue>
							          <option value="${collectionValue}" >${collectionValue}</option>
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
                            <div class="g-recaptcha" id="g-recaptcha" name="koh-contact-google-captcha" data-callback="recaptchaCallback" data-sitekey="${clientSeceretKey}"></div>
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
    <script type="text/javascript" src="${link}/js/custom/koh-hygiene-promotion-form.min.js"></script>
</@hst.headContribution>

<script src= "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"> 
    </script> 
    <script> 
        $.getJSON("https://api.ipify.org?format=json", 
           function(data) { 
            $("#gfg").val(data.ip);
        }) 
 </script>

<#include "../include/imports.ftl">
<@fmt.message key="contact-form-drop-down-value1" var="dropDown1"/>
<@fmt.message key="contact-form-drop-down-value2" var="dropDown2"/>
<@fmt.message key="contact-form-drop-down-value3" var="dropDown3"/>
<@fmt.message key="contact-form-drop-down-value4" var="dropDown4"/>
<@fmt.message key="contact-form-drop-down-value5" var="dropDown5"/>
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
<@fmt.message key="contact-form-score-error-message" var="scoreErrorMessage"/>
<@fmt.message key="contact-form-interested-error-message" var="interestedErrorMessage"/>
<@fmt.message key="contact-form-country-error-message" var="countryErrorMessage"/>
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
<@hst.link var="thankyoupage1" siteMapItemRefId="thankyoupage1"/>




<input type="hidden" id="requestTypeErrorMessageLink" value="${requestTypeErrorMessage}" />
<input type="hidden" id="firstNameErrorMessageLink" value="${firstNameErrorMessage}" />
<input type="hidden" id="scoreErrorMessageLink" value="${scoreErrorMessage}" />
<input type="hidden" id="interestedErrorMessageLink" value="${interestedErrorMessage}" />
<input type="hidden" id="countryErrorMessageLink" value="${countryErrorMessage}" />
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
name-->${form.name!}
thankyoupage1--${thankyoupage1}
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
               <form class="form-horizontal"  action="<@hst.actionURL escapeXml=false />" method="post" name="${form.name!}" id="mucontactUsForm"
                 <#if form.multipart>enctype="multipart/form-data"</#if> autocomplete="off" >
                   
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-first-name">Name:</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-first-name" maxlength="40" placeholder="" name="name" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-email-address">Email</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-contact-email-address" maxlength="60" placeholder="" name="email" >
                        </div>
                    </div>
                    
                     <div class="form-group">
                        <label class="control-label col-sm-5" for="request-type">Country</label>
                        <div class="col-sm-4">
                           <select class="form-control" id="koh-contact-country" name="country">
                                <option value="Select" selected="selected">Select</option>
						        <option value="Australia" >Australia</option>
								<option value="Hong Kong (China)" >Hong Kong (China)</option>
								<option value="Indonesia" >Indonesia</option>
								<option value="Japan" >Japan</option>
								<option value="Malaysia" >Malaysia</option>
								<option value="New Zealand" >New Zealand</option>
								<option value="Singapore" >Singapore</option>
								<option value="South Korea" >South Korea</option>
								<option value="Thailand" >Thailand</option>
								<option value="Vietnam" >Vietnam</option>
                            </select>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-score">Score</label>
                        <div class="col-sm-4">          
                             <select class="form-control" id="koh-contact-score" name="score">
                                    <option value="Select" selected="selected">Select</option>
                                    <option value="3:0" >3:0</option>
									<option value="2:0" >2:0</option>
									<option value="3:1" >3:1</option>
									<option value="1:0" >1:0</option>
									<option value="2:1" >2:1</option>
									<option value="3:2" >3:2</option>
									<option value="0:0" >0:0</option>
									<option value="1:1" >1:1</option>
									<option value="2:2" >2:2</option>
									<option value="3:3" >3:3</option>
									<option value="2:3" >2:3</option>
									<option value="1:2" >1:2</option>
									<option value="0:1" >0:1</option>
									<option value="1:3" >1:3</option>
									<option value="0:2" >0:2</option>
									<option value="0:3" >0:3</option>
									<option value="Others" >Others</option>
                            </select>
                        </div>
                    </div>
                    
                     <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-contact-interestedproducts">Interested Products</label>
                        <div class="col-sm-4">
                           <select class="form-control" id="koh-contact-interestedproducts" name="interestedproducts">
                                    <option value="Select" selected="selected">Select</option>
                                    <option value="No, thanks" >No, thanks</option>
									<option value="Faucets" >Faucets</option>
									<option value="Baths" >Baths</option>
									<option value="Showering" >Showering</option>
									<option value="Shower doors" >Shower doors</option>
                            </select>
                        </div>
                    </div>
                  <input type="hidden" class="form-control" id="gfg" maxlength="60" placeholder="" name="ipaddress">
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
                     <input type="hidden" class="form-control" id="koh-contact-ThankyouPage" name="koh-contact-ThankyouPage" value="xxx">
                    
                </form>
            </div>
        </div>
</section>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-mu-contact-form.min.js"></script>
</@hst.headContribution>

<script src= "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"> 
    </script> 
    <script> 
        $.getJSON("https://api.ipify.org?format=json", 
           function(data) { 
            $("#gfg").val(data.ip);
        }) 
 </script>

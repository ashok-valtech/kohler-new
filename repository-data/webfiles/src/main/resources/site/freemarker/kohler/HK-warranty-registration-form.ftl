<#include "../include/imports.ftl">
<@fmt.message key="warranty-contact-title" var="contactForm"/>
<@fmt.message key="warranty-required-title" var="required"/>
<@fmt.message key="warranty-email-label" var="email"/>
<@fmt.message key="warranty-name-label" var="name"/>
<@fmt.message key="warranty-mobile-label" var="mobileNumber"/>
<@fmt.message key="warranty-address-label" var="address"/>
<@fmt.message key="warranty-deliveryDate-label" var="deliveryDate"/>
<@fmt.message key="warranty-buyingLocation-label" var="buyingLocation"/>
<@fmt.message key="warranty-itemNo-label" var="itemNo"/>
<@fmt.message key="warranty-purchased-label" var="purchased"/>
<@fmt.message key="warranty-choose-label" var="choose"/>
<@fmt.message key="warranty-choose-value1" var="value1"/>
<@fmt.message key="warranty-choose-value2" var="value2"/>
<@fmt.message key="warranty-about-label" var="knowAbout"/>
<@fmt.message key="warranty-about-media" var="media"/>
<@fmt.message key="warranty-about-advertising" var="advertising"/>
<@fmt.message key="warranty-about-blogger" var="blogger"/>
<@fmt.message key="warranty-about-social" var="social"/>
<@fmt.message key="warranty-about-magazines" var="magazines"/>
<@fmt.message key="warranty-about-forum" var="forum"/>
<@fmt.message key="warranty-about-architect" var="architect"/>
<@fmt.message key="warranty-about-salesPerson" var="salesPerson"/>
<@fmt.message key="warranty-about-wom" var="wom"/>
<@fmt.message key="warranty-about-commercial" var="commercial"/>
<@fmt.message key="warranty-about-other" var="other"/>

<@fmt.message key="warranty-advice-label" var="advice"/>
<@fmt.message key="warranty-emailNotification-label" var="emailNotification"/>
<@fmt.message key="warranty-emailNotification-agree" var="agree"/>
<@fmt.message key="warranty-emailNotification-disagree" var="disagree"/>
<@fmt.message key="warranty-privacy-label" var="privacyPolicyTitle"/>
<@fmt.message key="warranty-privacy-link" var="privacyPolicyLink"/>
<@fmt.message key="warranty-submit-label" var="submit"/>
<@fmt.message key="captcha_client_secret_Key" var="clientSeceretKey"/>
<@fmt.message key="contact-form-upload-file-title" var="uploadTitle"/>

<@fmt.message key="warranty-email-error-message1" var="emailErrorMessage1"/>
<@fmt.message key="warranty-email-error-message2" var="emailErrorMessage2"/>
<@fmt.message key="warranty-name-error-message" var="nameErrorMessage"/>
<@fmt.message key="warranty-mobile-error-message" var="mobileErrorMessage"/>
<@fmt.message key="warranty-address-error-message" var="addressErrorMessage"/>
<@fmt.message key="warranty-delivery-error-message1" var="deliveryErrorMessage1"/>
<@fmt.message key="warranty-delivery-error-message2" var="deliveryErrorMessage2"/>
<@fmt.message key="warranty-purchase-error-message" var="purchaseErrorMessage"/>
<@fmt.message key="warranty-emailnotification-error-message" var="emailNotificationErrorMessage"/>
<@fmt.message key="warranty-captcha-error-message" var="captchaErrorMessage"/>

<@fmt.message key="warranty-form-content1" var="content1"/>
<@fmt.message key="warranty-form-content2" var="content2"/>
<@fmt.message key="warranty-tc-checkbox-1stpart" var="Part1"/>
<@fmt.message key="warranty-tc-checkbox-2ndpart" var="Part2"/>
<@fmt.message key="warranty-tc-checkbox-and" var="and"/>
<@fmt.message key="warranty-form-tclink" var="tcLink"/>
<@fmt.message key="warranty-ci-checkbox-content" var="ciCheckboxContent"/>

<@fmt.message key="warranty-salutation-label" var="salutation"/>
<@fmt.message key="warranty-district-list" var="districtList"/>
<@fmt.message key="warranty-where-to-buy-list" var="whereToBuyList"/>
<@fmt.message key="warranty-type-of-product" var="productTypeList"/>
<@fmt.message key="warranty-district-lable" var="district"/>
<@fmt.message key="warranty-purchase-date-lable" var="purchaseDateLable"/>
<@fmt.message key="warranty-product-type" var="productType"/>
<@fmt.message key="warranty-file-upload-error-message" var="uploadErrorMessage"/>
<@fmt.message key="warranty-valid-file-error-message" var="validFileErrorMessage"/>
<@fmt.message key="warranty-file-size-error-message" var="fileSizeErrorMessage"/>
<@fmt.message key="warranty-salutation-error-message" var="salutationErrormessage"/>
<@fmt.message key="warranty-itemno-error-message" var="itemNoErrorMessage"/>
<@fmt.message key="warranty-dealer-error-message" var="dealerErrorMessage"/>
<@fmt.message key="warranty-product-type-error-message" var="productTypeErrorMessage"/>
<@fmt.message key="warranty-district-error-message" var="districtErrorMessage"/>
<@fmt.message key="warranty-purchase-date-error-message1" var="purchaseDateErrorMessage1"/>
<@fmt.message key="warranty-purchase-date-error-message2" var="purchaseDateErrorMessage2"/>
<@fmt.message key="warranty-upload-note-message" var="fileUploadNoteMessage"/>
<@fmt.message key="warranty-upload-label" var="uploadLabel"/>
<@fmt.message key="warranty-salutation-placeholder" var="salutationPlaceholder"/>
<@fmt.message key="warranty-where-to-buy-note" var="wheretobuyNote"/>
<@fmt.message key="warranty-title" var="warrantyTitle"/>
<@fmt.message key="warranty-privacy-error-message" var="privacyErrorMessage"/>
<@fmt.message key="warranty-invalid-date-error" var="invalidDateErrorMessage"/>
<@fmt.message key="contact-form-message-fading-timing" var="messageFadingTiming"/>
<@fmt.message key="contact-form-message-timing" var="messageTiming"/>
<@fmt.message key="contact-form-error-message" var="errorMessage"/>
<@fmt.message key="warranty-file-length-error-message" var="fileLengthErrorMessage"/>


<@hst.link var="hkthankyoupage" siteMapItemRefId="hkthankyoupage"/>
<@hst.link var="hkwarrantypage" siteMapItemRefId="warrantyTermsandConditions"/>

<input type="hidden" id="emailErrorMessage1Id" value="${emailErrorMessage1}"/>
<input type="hidden" id="emailErrorMessage2Id" value="${emailErrorMessage2}"/>
<input type="hidden" id="nameErrorMessageId" value="${nameErrorMessage}"/>
<input type="hidden" id="mobileErrorMessageId" value="${mobileErrorMessage}"/>
<input type="hidden" id="addressErrorMessageId" value="${addressErrorMessage}"/>
<input type="hidden" id="deliveryErrorMessage1Id" value="${deliveryErrorMessage1}"/>
<input type="hidden" id="deliveryErrorMessage2Id" value="${deliveryErrorMessage2}"/>
<input type="hidden" id="itemNoErrorMessageId" value="${itemNoErrorMessage}"/>
<input type="hidden" id="purchaseErrorMessageId" value="${purchaseErrorMessage}"/>
<input type="hidden" id="emailNotificationErrorMessageId" value="${emailNotificationErrorMessage}"/>
<input type="hidden" id="captchaErrorMessageId" value="${captchaErrorMessage}"/>
<input type="hidden" id="uploadErrorMessageId" value="${uploadErrorMessage}"/>
<input type="hidden" id="validFileErrorMessageId" value="${validFileErrorMessage}"/>
<input type="hidden" id="fileSizeErrorMessageId" value="${fileSizeErrorMessage}"/>
<input type="hidden" id="salutationErrormessageId" value="${salutationErrormessage}"/>
<input type="hidden" id="dealerErrorMessageId" value="${dealerErrorMessage}"/>
<input type="hidden" id="productTypeErrorMessageId" value="${productTypeErrorMessage}"/>
<input type="hidden" id="districtErrorMessageId" value="${districtErrorMessage}"/>
<input type="hidden" id="purchaseDateErrorMessage1Id" value="${purchaseDateErrorMessage1}"/>
<input type="hidden" id="purchaseDateErrorMessage2Id" value="${purchaseDateErrorMessage2}"/>
<input type="hidden" id="koh-warranty-thankyouPage" value=${hkthankyoupage}>
<input type="hidden" id="privacyErrorMessage" value="${privacyErrorMessage}"/>
<input type="hidden" id="invalidDateErrorMessage" value="${invalidDateErrorMessage}"/>
<input type="hidden" id="messageFadingTimingLink" value="${messageFadingTiming}" />
<input type="hidden" id="messageTimingLink" value="${messageTiming}" />
<input type="hidden" id="outletList" value="${whereToBuyList}" />
<input type="hidden" id="formErrorMessage" value="${errorMessage}" />
<input type="hidden" id="fileLengthErrorMessageId" value="${fileLengthErrorMessage}"/>



<#assign englishSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
    <#assign englishSite="/en" />
</#if>

<@hst.link var="thankyoupage" siteMapItemRefId="thankyoupage"/>

<section class="c-koh-warranty-registration-form">
        <div class="koh-contact-container">
            <div class="koh-contact-header">
                <span class="koh-contact-title">${contactForm}</span>
 				<span class="koh-contact-require">${required}</span>
            </div>
			<div id="failureMessage" class="hide">
                ${errorMessage}
            </div>
            <div class="koh-contact-form-wrapper">
                <form method="POST" class="form-horizontal" action="${englishSite}/api/hkwarrantyform" enctype="multipart/form-data" id="hkWarrantyRegistratationForm" autocomplete="off">
                
                	<div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-salutation">
                            ${salutation}
                        </label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="koh-warranty-salutation" placeholder="${salutationPlaceholder}" name="koh-warranty-salutation" maxlength="50">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-full-name">${name}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-warranty-full-name" name="koh-warranty-full-name" maxlength="254">
                        </div>
                    </div>
                    
                	<div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-email">${email}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-warranty-email" maxlength="254" placeholder="" name="koh-warranty-email">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-mobile">${mobileNumber}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-warranty-mobile" placeholder="" name="koh-warranty-mobile" maxlength="15">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-address">${address}</label>
                        <div class="col-sm-4">   
                            <textarea class="form-control" rows="5" id="koh-warranty-address" name="koh-warranty-address" maxlength="500"></textarea>
                            <div class="koh-wheretobuy-note">(Maximum 500 character allowed)</div>
                        </div>
                    </div>
                    
                    <div class="form-group">
		                <label class="control-label col-sm-5 koh-required" for="koh-warranty-district">
		                    ${district}
		                </label>
		                <div class="col-sm-4">
		                    <select class="form-control" id="koh-warranty-district" name="koh-warranty-district" maxlength="254">
		                        <option value="">${choose}</option>
		                        <#list districtList?split(",") as districtDropdownList>
		                        	<option value="${districtDropdownList}">${districtDropdownList}</option>
		                        </#list>
		                    </select>
		                </div>
		            </div>
                    
                    <div class="form-group">
		                <label class="control-label col-sm-5 koh-required" for="koh-warranty-product-purchase">${purchaseDateLable}</label>
		                <div class="col-sm-4">
		                    <input type="text" placeholder="dd-mm-yyyy" onfocus="(this.type='date')" class="form-control" id="koh-warranty-product-purchase" name="koh-warranty-product-purchase">
		                </div>
		            </div>
		            
                    <div class="form-group">
                    	<label class="control-label col-sm-5 koh-required" for="koh-warranty-product-delivery">${deliveryDate}</label>
                    	<div class="col-sm-4">
                    		<input type="text" placeholder="dd-mm-yyyy" onfocus="(this.type='date')" class="form-control" id="koh-warranty-product-delivery" name="koh-warranty-product-delivery">
                        </div>
                    </div>
                    
                    <div class="form-group">
		                <label class="control-label col-sm-5 koh-required" for="koh-warranty-outlet">${buyingLocation}</label>
		                <div class="col-sm-4">
		                    <select class="form-control" id="koh-warranty-outlet" name="koh-warranty-outlet" onchange='checkOthersValue(this.value);'>
		                        <option value="">${choose}</option>
		                        <#list whereToBuyList?split(",") as whereToBuyDropdownList>
		                        	<option value="${whereToBuyDropdownList}">${whereToBuyDropdownList}</option>
		                        </#list>
		                        <input type="text" id="koh-others-checkbox" name="koh-others-checkbox" style='display:none;'/>
		                    </select>
		                    <div class="koh-wheretobuy-note">${wheretobuyNote}</div>
		                </div>
		            </div>
		            
                    <#--  <div class="form-group">
		                <label class="control-label col-sm-5 koh-required" for="koh-warranty-product-type">${productType}</label>
		                <div class="col-sm-4">
		                    <select class="form-control" id="koh-warranty-product-type" name="koh-warranty-product-type">
		                        <option value="">${choose}</option>
		                        <#list productTypeList?split(",") as productTypeDropdownList>
		                        	<option value="${productTypeDropdownList}">${productTypeDropdownList}</option>
		                        </#list>
		                    </select>
		                </div>
		            </div>  -->

					<div class="form-group">
	                    <label class="control-label col-sm-5 koh-required" for="koh-warranty-product-type">${productType}</label>
	                    <div class="col-sm-4" id="koh-warranty-product-type">
							<#list productTypeList?split(",") as productTypeDropdownList>
								<label class="checkbox-about-kohler">
									<input type="checkbox" class="about-kohler" id="koh-warranty-checkbox-${productTypeDropdownList}" name="koh-warranty-product-type" maxlength="1000" value="${productTypeDropdownList}">${productTypeDropdownList}</label>
							</#list>	
	                    </div>
	                </div>
		            
		            <#--  <div class="form-group">
		                <label class="control-label col-sm-5 koh-required" for="koh-warranty-product-sku">${itemNo}</label>
		                <div class="col-sm-4">
		                    <input type="text" class="form-control" id="koh-warranty-product-sku" name="koh-warranty-product-sku">
		                </div>
		            </div>  -->
		            
		            <#--  <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-choose">${purchased}</label>
                        <div class="col-sm-4">
                           <select class="form-control" id="koh-warranty-choose" name="koh-warranty-choose">
                                <option value="">${choose}</option>
                                <option value="${value1}">${value1}</option>
                                <option value="${value2}">${value2}</option>
                            </select>
                        </div>
                    </div>  -->
                    
                    <#--  <div class="form-group">
	                    <label class="control-label col-sm-5" for="koh-warranty-multiple-choice">${knowAbout}</label>
	                    <div class="col-sm-4">
	                        <label class="checkbox-about-kohler">
	                            <input type="checkbox" class="about-kohler" id="koh-warranty-newspaper-checkbox" name="koh-warranty-about-kohler" value="${magazines}">${magazines}</label>
	                        <label class="checkbox-about-kohler">
	                            <input type="checkbox" class="about-kohler" id="koh-warranty-search-engine-checkbox" name="koh-warranty-about-kohler" value="${advertising}">${advertising}</label>
	                        <label class="checkbox-about-kohler">
	                            <input type="checkbox" class="about-kohler" id="koh-warranty-media-checkbox" name="koh-warranty-about-kohler" value="${media}">${media}</label>
	                        <label class="checkbox-about-kohler">
	                            <input type="checkbox" class="about-kohler" id="koh-warranty-Social-checkbox" name="koh-warranty-about-kohler" value="${social}">${social}</label>
	                        <label class="checkbox-about-kohler">
	                            <input type="checkbox" class="about-kohler" id="koh-warranty-Design-checkbox" name="koh-warranty-about-kohler" value="${forum}">${forum}</label>
	                        <label class="checkbox-about-kohler">
	                            <input type="checkbox" class="about-kohler" id="koh-warranty-Designer-checkbox" name="koh-warranty-about-kohler" value="${architect}">${architect}</label>
	                        <label class="checkbox-about-kohler">
	                            <input type="checkbox" class="about-kohler" id="koh-warranty-Salesperson-checkbox" name="koh-warranty-about-kohler" value="${salesPerson}">${salesPerson}</label>
	                        <label class="checkbox-about-kohler">
	                            <input type="checkbox" class="about-kohler" id="koh-warranty-family-checkbox" name="koh-warranty-about-kohler" value="${wom}">${wom}</label>
	                        <label class="checkbox-about-kohler">
	                            <input type="checkbox" class="about-kohler" id="koh-about-others-checkbox" name="koh-warranty-about-kohler" value="${other}">${other}</label>
	                    </div>
	                </div>  -->
	                
	                 <#--  <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-warranty-suggestions">${advice}</label>
                        <div class="col-sm-4">          
                            <textarea class="form-control" rows="5" id="koh-warranty-suggestions" name="koh-warranty-suggestions"></textarea>
                        </div>
                    </div>  -->
                    
					<#-- <div class="form-group">
						<div class=" form-group koh-content-promo">
							${content1}
						</div>
					</div>-->
					
                    <div class="form-group koh-sfmc-tc-checkbox">
						<div class="col-sm-4 koh-privacy-sfmc-checkbox">
							<label>
								<div class="koh-privacy-sfmc-promo">
									${Part1} <a class="koh-privacy-policy-link-sfmc-cf" onclick="window.open('${tcLink}', '_blank', 'location=yes,height=600,width=1000,scrollbars=yes,status=yes');">${Part2}</a> ${and} <a class="koh-privacy-policy-link-sfmc-cf" onclick="window.open('${privacyPolicyLink}', '_blank', 'location=yes,height=600,width=1000,scrollbars=yes,status=yes');">${privacyPolicyTitle}</a>
								</div>
								<input type="checkbox" id="kohler-warranty-privacy-policy" name="warrantyPrivacyPolicy" value="TRUE">
							</label>
						</div>
					</div>
					
					<div class="form-group koh-sfmc-ci-checkbox">
						<div class="col-sm-4 koh-privacy-sfmc-ci-checkbox">
							<label>
								<input type="checkbox" id="kohler-contact-info" name="warrantyContactInformation" value="TRUE">
								<div class="koh-privacy-sfmc-ci-promo">
									${ciCheckboxContent}
								</div>
							</label>
						</div>
					</div>
					
					<div class="form-group">
						<div class="form-group koh-content-promo">
							${content2}
						</div>
					</div>
					<div class="form-group">
                       <label class="control-label col-sm-5 koh-upload-label koh-required" for="koh-contact-upload-file">${uploadLabel}</label>
                        <div class="col-sm-4">   
                            <div class="file btn koh-upload-wrapper col-sm-6">
                                ${uploadTitle}
                                <input type="file" name="file" id="koh-contact-upload-file"/>
                            </div>
                            <div id="koh-contact-upload-file-name"></div>
                            <div class="koh-contact-upload-note">(${fileUploadNoteMessage})</div>
                        </div>
                    </div>
					
                    <div class="form-group">
                        <div class="col-sm-offset-5 col-sm-4 captcha-container">   
                            <div class="g-recaptcha" id="g-recaptcha" name="koh-contact-google-captcha" data-callback="recaptchaCallback" data-sitekey="${clientSeceretKey}"></div>
                        </div>
                    </div>
                    <div class="form-group">        
                        <div class="col-sm-offset-5 col-sm-4">
                            <button id="koh-contact-form-btn" type="submit" class="btn btn-default">${submit} </button>
                        </div>
                    </div>
                    <div class="form-group">
						<div class="form-group koh-content-promo">
							<a href="${hkwarrantypage}" target="_self">${warrantyTitle}</a>
						</div>
					</div>
                </form>
            </div>
        </div>
</section>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-hk-warranty-registration-form.min.js"></script>
</@hst.headContribution>

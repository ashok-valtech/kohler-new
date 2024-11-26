<#include "../include/imports.ftl">
<@fmt.message key="warranty-contact-title" var="contactForm"/>
<@fmt.message key="warranty-required-title" var="required"/>
<@fmt.message key="warranty-email-label" var="email"/>
<@fmt.message key="warranty-name-label" var="name"/>
<@fmt.message key="warranty-gender-label" var="gender"/>
<@fmt.message key="warranty-gender-malevalue" var="male"/>
<@fmt.message key="warranty-gender-femalevalue" var="female"/>
<@fmt.message key="warranty-dob-label" var="dob"/>
<@fmt.message key="warranty-mobile-label" var="mobileNumber"/>
<@fmt.message key="warranty-address-label" var="address"/>
<@fmt.message key="warranty-deliveryDate-label" var="deliveryDate"/>
<@fmt.message key="warranty-countryCity-label" var="countryCity"/>
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
<@fmt.message key="warranty-card-label" var="warrantyCard"/>
<@fmt.message key="warranty-serial-number-label" var="serialNumber"/>
<@fmt.message key="preview-modal-title" var="previewTitle"/>
<@fmt.message key="preview-edit-button" var="editButtonText"/>
<@fmt.message key="preview-confirm-button" var="confirmButtonText"/>

<#--  <@fmt.message key="warranty-about-media-value" var="mediaValue"/>
<@fmt.message key="warranty-about-advertising-value" var="advertisingValue"/>
<@fmt.message key="warranty-about-blogger-value" var="bloggerValue"/>
<@fmt.message key="warranty-about-social-value" var="socialValue"/>
<@fmt.message key="warranty-about-magazines-value" var="magazinesValue"/>
<@fmt.message key="warranty-about-forum-value" var="forumValue"/>
<@fmt.message key="warranty-about-architect-value" var="architectValue"/>
<@fmt.message key="warranty-about-salesPerson-value" var="salesPersonValue"/>
<@fmt.message key="warranty-about-wom-value" var="womValue"/>
<@fmt.message key="warranty-about-commercial-value" var="commercialValue"/>  -->
<@fmt.message key="warranty-about-other-value" var="otherValue"/>

<@fmt.message key="warranty-advice-label" var="advice"/>
<@fmt.message key="warranty-emailNotification-label" var="emailNotification"/>
<@fmt.message key="warranty-emailNotification-agree" var="agree"/>
<@fmt.message key="warranty-emailNotification-disagree" var="disagree"/>
<@fmt.message key="warranty-privacy-label" var="privacyPolicyTitle"/>
<@fmt.message key="warranty-privacy-link" var="privacyPolicyLink"/>
<@fmt.message key="warranty-submit-label" var="submit"/>
<@fmt.message key="captcha_client_secret_Key" var="clientSeceretKey"/>
<@fmt.message key="warranty-upload-label" var="uploadLabel"/>
<@fmt.message key="contact-form-upload-file-title" var="uploadTitle"/>
<@fmt.message key="warranty-upload-note-message" var="fileUploadNoteMessage"/>

<@fmt.message key="warranty-email-error-message1" var="emailErrorMessage1"/>
<@fmt.message key="warranty-email-error-message2" var="emailErrorMessage2"/>
<@fmt.message key="warranty-name-error-message" var="nameErrorMessage"/>
<@fmt.message key="warranty-mobile-error-message" var="mobileErrorMessage"/>
<@fmt.message key="warranty-address-error-message" var="addressErrorMessage"/>
<@fmt.message key="warranty-delivery-error-message1" var="deliveryErrorMessage1"/>
<@fmt.message key="warranty-delivery-error-message2" var="deliveryErrorMessage2"/>
<@fmt.message key="warranty-city-error-message" var="cityErrorMessage"/>
<@fmt.message key="warranty-dealer-error-message" var="dealerErrorMessage"/>
<@fmt.message key="warranty-itemno-error-message" var="itemNoErrorMessage"/>
<@fmt.message key="warranty-purchase-error-message" var="purchaseErrorMessage"/>
<@fmt.message key="warranty-emailnotification-error-message" var="emailNotificationErrorMessage"/>
<@fmt.message key="warranty-captcha-error-message" var="captchaErrorMessage"/>
<@fmt.message key="warranty-invalid-date-error-message" var="invalidDateErrorMessage"/>
<@fmt.message key="warranty-file-upload-error-message" var="uploadErrorMessage"/>
<@fmt.message key="warranty-valid-file-error-message" var="validFileErrorMessage"/>
<@fmt.message key="warranty-file-size-error-message" var="fileSizeErrorMessage"/>
<@fmt.message key="warranty-warranty-card-error-message" var="cardErrorMessage"/>
<@fmt.message key="warranty-serial-number-error-message" var="serialNumberErrorMessage"/>
<@fmt.message key="contact-form-message-fading-timing" var="messageFadingTiming"/>
<@fmt.message key="contact-form-message-timing" var="messageTiming"/>
<@fmt.message key="contact-form-error-message" var="errorMessage"/>
<@fmt.message key="warranty-file-length-error-message" var="fileLengthErrorMessage"/>


<@fmt.message key="warranty-form-content1" var="content1"/>
<@fmt.message key="warranty-form-content2" var="content2"/>
<@fmt.message key="warranty-tc-checkbox-1stpart" var="Part1"/>
<@fmt.message key="warranty-tc-checkbox-2ndpart" var="Part2"/>
<@fmt.message key="warranty-tc-checkbox-and" var="and"/>
<@fmt.message key="warranty-form-tclink" var="tcLink"/>
<@fmt.message key="warranty-ci-checkbox-content" var="ciCheckboxContent"/>
<@fmt.message key="warranty-file-upload-url" var="fileUploadURL"/>
<@fmt.message key="warranty-token-generation-url" var="tokenURL"/>

<input type="hidden" id="emailErrorMessage1Id" value="${emailErrorMessage1}"/>
<input type="hidden" id="emailErrorMessage2Id" value="${emailErrorMessage2}"/>
<input type="hidden" id="nameErrorMessageId" value="${nameErrorMessage}"/>
<input type="hidden" id="mobileErrorMessageId" value="${mobileErrorMessage}"/>
<input type="hidden" id="addressErrorMessageId" value="${addressErrorMessage}"/>
<input type="hidden" id="deliveryErrorMessage1Id" value="${deliveryErrorMessage1}"/>
<input type="hidden" id="deliveryErrorMessage2Id" value="${deliveryErrorMessage2}"/>
<input type="hidden" id="cityErrorMessageId" value="${cityErrorMessage}"/>
<input type="hidden" id="dealerErrorMessageId" value="${dealerErrorMessage}"/>
<input type="hidden" id="itemNoErrorMessageId" value="${itemNoErrorMessage}"/>
<input type="hidden" id="purchaseErrorMessageId" value="${purchaseErrorMessage}"/>
<input type="hidden" id="emailNotificationErrorMessageId" value="${emailNotificationErrorMessage}"/>
<input type="hidden" id="captchaErrorMessageId" value="${captchaErrorMessage}"/>
<input type="hidden" id="invalidDateErrorMessage" value="${invalidDateErrorMessage}"/>
<input type="hidden" id="uploadErrorMessageId" value="${uploadErrorMessage}"/>
<input type="hidden" id="validFileErrorMessageId" value="${validFileErrorMessage}"/>
<input type="hidden" id="cardErrorMessageId" value="${cardErrorMessage}"/>
<input type="hidden" id="serialNumberErrorMessageId" value="${serialNumberErrorMessage}"/>
<input type="hidden" id="messageFadingTimingLink" value="${messageFadingTiming}" />
<input type="hidden" id="messageTimingLink" value="${messageTiming}" />
<input type="hidden" id="formErrorMessage" value="${errorMessage}" />
<input type="hidden" id="fileSizeErrorMessageId" value="${fileSizeErrorMessage}"/>
<input type="hidden" id="fileLengthErrorMessageId" value="${fileLengthErrorMessage}"/>

<@hst.link var="thankyoupage" siteMapItemRefId="twthankyoupage"/>

<input type="hidden" id="cardLabel" value="${warrantyCard}"/>
<input type="hidden" id="emailLabel" value="${email}"/>
<input type="hidden" id="fullNameLabel" value="${name}"/>
<input type="hidden" id="mobileLabel" value="${mobileNumber}"/>
<input type="hidden" id="addressLabel" value="${address}"/>
<input type="hidden" id="productDeliveryDateLable" value="${deliveryDate}"/>
<input type="hidden" id="cityNameLable" value="${countryCity}"/>
<input type="hidden" id="storeNameLabel" value="${buyingLocation}"/>
<input type="hidden" id="productNameLabel" value="${itemNo}"/>
<input type="hidden" id="serialNumberLabel" value="${serialNumber}"/>
<input type="hidden" id="kohlerPurchaseLabel" value="${purchased}"/>
<input type="hidden" id="checkboxLabel" value="${knowAbout}"/>
<input type="hidden" id="kohlerAdviceLabel" value="${advice}"/>
<input type="hidden" id="koh-warranty-thankyouPage" value=${thankyoupage}/>
<input type="hidden" id="file-upload-url" value=${fileUploadURL}/>
<input type="hidden" id="token-url" value=${tokenURL}/>
<input type="hidden" id="fileLabel" value="${uploadLabel}"/>
<input type="hidden" id="previewTitle" value="${previewTitle}"/>
<input type="hidden" id="editButtonText" value="${editButtonText}"/>
<input type="hidden" id="confirmButtonText" value="${confirmButtonText}"/>

<#assign englishSite="" />
<#if hstRequestContext.getResolvedMount().getMount().getParent()??>
    <#assign englishSite="/en" />
</#if>

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
                <form method="POST" class="form-horizontal" action="${englishSite}/api/twwarrantyform" enctype="multipart/form-data" id="twWarrantyRegistratationForm" autocomplete="off">
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-card-number">${warrantyCard}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-warranty-card-number" maxlength="254" placeholder="" name="koh-warranty-card-number">
                        </div>
                    </div>
                	<div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-email">${email}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-warranty-email" maxlength="254" placeholder="" name="koh-warranty-email">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-full-name">${name}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-warranty-full-name" maxlength="254" name="koh-warranty-full-name">
                        </div>
                    </div>
                    
                   <#-- <div class="form-group">
						<label class="control-label col-sm-5">${gender}</label>
						<div class="col-sm-4">
							<label class="checkbox-about-kohler">
								<input type="radio" class="about-kohler" id="koh-warranty-male" name="koh-warranty-gender" value="${male}">${male}</label>
							<label class="checkbox-about-kohler">
								<input type="radio" class="about-kohler" id="koh-warranty-female" name="koh-warranty-gender" value="${female}">${female}</label>
						</div>
					</div>
                    
                    <div class="form-group">
                    	<label class="control-label col-sm-5" for="koh-warranty-Dob">${dob}</label>
                    	<div class="col-sm-4">
                    		<input type="text" placeholder="dd-mm-yyyy" onfocus="(this.type='date')" class="form-control" id="koh-warranty-Dob" name="koh-warranty-Dob" >
                        </div>
                    </div>-->
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-mobile">${mobileNumber}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-warranty-mobile" name="koh-warranty-mobile" maxlength="15">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-purchased">${countryCity}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-warranty-purchased" maxlength="254" name="koh-warranty-purchased">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-address">${address}</label>
                        <div class="col-sm-4">   
                            <textarea class="form-control" rows="5" id="koh-warranty-address" maxlength="500" name="koh-warranty-address"></textarea>
                        </div>
                    </div>
                    
                    <div class="form-group">
                    	<label class="control-label col-sm-5 koh-required" for="koh-warranty-product-delivery">${deliveryDate}</label>
                    	<div class="col-sm-4">
                    		<input type="text" placeholder="dd-mm-yyyy" onfocus="(this.type='date')" class="form-control" id="koh-warranty-product-delivery" name="koh-warranty-product-delivery">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-Store">${buyingLocation}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-warranty-Store" maxlength="254" name="koh-warranty-Store">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-product-name">${itemNo}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-warranty-product-name" maxlength="254" name="koh-warranty-product-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-product-serial-number">${serialNumber}</label>
                        <div class="col-sm-4">          
                            <input type="text" class="form-control" id="koh-warranty-product-serial-number" maxlength="254" name="koh-warranty-product-serial-number">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5 koh-required" for="koh-warranty-choose">${purchased}</label>
                        <div class="col-sm-4">
                           <select class="form-control" id="koh-warranty-choose" name="koh-warranty-choose">
                                <option value="">${choose}</option>
                                <option value="${value1}">${value1}</option>
                                <option value="${value2}">${value2}</option>
                            </select>
                        </div>
                    </div>
                    
                    <div class="form-group">
						<label class="control-label col-sm-5" for="koh-warranty-multiple-choice">${knowAbout}</label>
						<div class="col-sm-4">
							<label class="checkbox-about-kohler">
								<input type="checkbox" class="about-kohler" id="koh-warranty-media-checkbox" name="koh-warranty-about-kohler" value="${media}">${media}</label>
							<label class="checkbox-about-kohler">
								<input type="checkbox" class="about-kohler" id="koh-warranty-online-checkbox" name="koh-warranty-about-kohler" value="${advertising}">${advertising}</label>
							<label class="checkbox-about-kohler">
								<input type="checkbox" class="about-kohler" id="koh-warranty-blogger-checkbox" name="koh-warranty-about-kohler" value="${blogger}">${blogger}</label>
							<label class="checkbox-about-kohler">
								<input type="checkbox" class="about-kohler" id="koh-warranty-Social-checkbox" name="koh-warranty-about-kohler" value="${social}">${social}</label>
							<label class="checkbox-about-kohler">
								<input type="checkbox" class="about-kohler" id="koh-warranty-Design-checkbox" name="koh-warranty-about-kohler" value="${magazines}">${magazines}</label>
							<label class="checkbox-about-kohler">
								<input type="checkbox" class="about-kohler" id="koh-warranty-Forum-checkbox" name="koh-warranty-about-kohler" value="${forum}">${forum}</label>
							<label class="checkbox-about-kohler">
								<input type="checkbox" class="about-kohler" id="koh-warranty-architect-checkbox" name="koh-warranty-about-kohler" value="${architect}">${architect}</label>
							<label class="checkbox-about-kohler">
								<input type="checkbox" class="about-kohler" id="koh-warranty-Salesperson-checkbox" name="koh-warranty-about-kohler" value="${salesPerson}">${salesPerson}</label>
							<label class="checkbox-about-kohler">
								<input type="checkbox" class="about-kohler" id="koh-warranty-WOM-checkbox" name="koh-warranty-about-kohler" value="${wom}">${wom}</label>
							<label class="checkbox-about-kohler">
								<input type="checkbox" class="about-kohler" id="koh-warranty-commercial-checkbox" name="koh-warranty-about-kohler" value="${commercial}">${commercial}</label>
							<label class="checkbox-about-kohler">
								<input type="checkbox" class="about-kohler" onclick="var input = document.getElementById('koh-warranty-others-checkbox'); if(this.checked){ input.disabled = false; input.focus();}else{input.disabled=true;}" value="${otherValue}">${other}</label>
							<input type="text" id="koh-warranty-others-checkbox" name="koh-warranty-about-kohler" disabled="disabled" maxlength="284"/> </div>
					</div>
                    
                    <div class="form-group">
                        <label class="control-label col-sm-5" for="koh-warranty-suggestions">${advice}</label>
                        <div class="col-sm-4">          
                            <textarea class="form-control" rows="5" id="koh-warranty-suggestions" maxlength="254" name="koh-warranty-suggestions"></textarea>
                        </div>
                    </div>
                    
                   <#--  <div class="form-group">
						<label class="control-label col-sm-5 koh-required">${emailNotification}</label>
						<div class="col-sm-4">
						<div id="koh-radiobutton-email">
							<label class="checkbox-about-kohler">
								<input type="radio" class="about-kohler" id="koh-warranty-agree" name="koh-warranty-news-emails" value="TRUE">${agree}</label>
							<label class="checkbox-about-kohler">
								<input type="radio" class="about-kohler" id="koh-warranty-disagree" name="koh-warranty-news-emails" value="FALSE">${disagree}</label>
						</div>
						</div>
					</div> -->
					
					<div class="form-group">
						<div class=" form-group koh-content-promo">
							${content1}
						</div>
					</div>
					
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
						<div class=" form-group koh-content-promo">
							${content2}
						</div>
					</div>

                    <div class="form-group">
                       <label class="control-label col-sm-5 koh-upload-label koh-required" for="koh-contact-upload-file">${uploadLabel}</label>
                        <div class="col-sm-4">   
                            <div class="file btn koh-upload-wrapper col-sm-6">
                                ${uploadTitle}
                                <input type="file" name="file" id="koh-contact-upload-file"/>
                                <input type="hidden" id="koh-file-path" />
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
                            <button id="koh-contact-form-btn" class="btn btn-default" type="button">${submit} </button>
                            <input id="hidden-btn" type="submit" class="hidden-input"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div id="koh-preview-from-remodal" class="koh-preview-from-remodal"></div>
</section>

<@hst.webfile path="/" var="link" />
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-warranty-registration-form.min.js"></script>
</@hst.headContribution>

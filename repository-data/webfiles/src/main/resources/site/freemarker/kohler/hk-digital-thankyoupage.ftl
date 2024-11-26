<#include "../include/imports.ftl">
<@fmt.message key="sfmc-campaign-thankyou-Message" var="thankyouMessage"/>
<@fmt.message key="sfmc-campaign-thankyou-header" var="contactForm"/>

<section class="c-koh-warranty-registration-form">
        <div class="koh-contact-container">
            <div class="koh-contact-header">
                <span class="koh-contact-title">${contactForm}</span>
            </div>
         <div class="koh-contact-form-wrapper">
	         <div class="form-group">
	          <h3 class="text-center koh-map-main-header">${thankyouMessage}</h3>
	         </div>
         </div>
        </div>
</section>
	


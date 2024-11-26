<#include "../include/imports.ftl">
<@fmt.message key="warranty-contact-title" var="contactForm"/>
<@fmt.message key="warranty-thankyou-message" var="thankyouMessage"/>
<@fmt.message key="warranty-unsubscribe-message" var="unsubscribeMessage"/>

<section class="c-koh-warranty-registration-form">
    <div class="koh-contact-container">
        <div class="koh-contact-header">
            <span class="koh-contact-title">${contactForm}</span>
        </div>
     <div class="koh-contact-form-wrapper">
         <div class="form-group">
          <h3 class="text-center">${thankyouMessage}</h3>
          <em><h5 class="text-center">${unsubscribeMessage}</h5></em>
         </div>
     </div>
    </div>
</section>
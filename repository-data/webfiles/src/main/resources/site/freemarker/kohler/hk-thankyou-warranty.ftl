<#include "../include/imports.ftl">
<@fmt.message key="warranty-contact-title" var="contactForm"/>
<#if document??>
    <section class="c-koh-warranty-registration-form">
        <div class="koh-contact-container">
        	<div class="koh-contact-header">
	            <span class="koh-contact-title">${contactForm}</span>
	        </div>
            <#if document.title?? && document.title?has_content>
                <div class="koh-contact-form-wrapper">
                     <div class="form-group">
                      <h4 class="text-start">${document.title}</h4>
                     </div>
                 </div>
            </#if>
            <#if document.description.content?? && document.description.content?has_content>
                 <div class="koh-contact-form-wrapper heading-wrapper">
                     <div class="form-group">
                      <h4 class="text-end">${document.description.content}</h4>
                     </div>
                 </div>
             </#if>
        </div>
   </section>
</#if>
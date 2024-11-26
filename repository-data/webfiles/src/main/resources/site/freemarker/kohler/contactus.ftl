<#include "../include/imports.ftl">
<@fmt.message key="contact-us" var="contactUs"/>
<@fmt.message key="contact-us-intro" var="contactUsIntro"/>
<@fmt.message key="kohler-offices" var="kohlerOffices"/>
<@fmt.message key="koh-company-phone" var="kohCompanyPhone"/>
<@fmt.message key="koh-company-fax" var="kohCompanyFax"/>
<@fmt.message key="koh-company-email" var="kohCompanyEmail"/>
<@fmt.message key="koh-company-more" var="kohCompanyMore"/>
<@fmt.message key="contactUsMetaKeyword" var="contactUsMetaKeyword"/>
<@fmt.message key="contactUsMetaDesc" var="contactUsMetaDesc"/>
<@hst.link var="storelink" siteMapItemRefId="storelocator"/>
<@fmt.message key="contactUs-enquiry-withUs" var="contactUsEnquiryWithUs"/>
<@fmt.message key="contactUs-enquiry-withUsFlag" var="contactUsEnquiryWithUsFlag"/>
<#assign contactUsIntro = contactUsIntro?replace("{0}","${storelink}") />
<@hst.link var="shareContactFormLink" siteMapItemRefId="shareContactForm"/>
<@fmt.message key="enquire-now-title" var="enquireNowTitle"/>

    <section class="c-koh-contact-us">
        <div class="koh-contact-us-container">
            <div class="koh-contact-us-header">
                <span class="koh-contact-us-title">${contactUs}</span>
            </div>
            <div class="koh-contact-us-section col-xs-12">
                <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12 koh-address-container">
                    <div class="koh-contact-us-intro col-xs-12">
                       ${contactUsIntro}
                    </div>
                    <address class="koh-contact-us-address col-xs-12">
                        <h3 class="koh-contact-us-sub-title">
                            ${kohlerOffices}
                        </h3>
                        <ul class="koh-contact-us-address-list col-xs-12">
                        <#if pageable?? && pageable.items?has_content>
		                   <#list pageable.items as item>
                            <li class="col-lg-6 col-md-6 col-sm-6 col-xs-12 koh-contact-us-address-items">
                                <div class="koh-company-address">
                                    ${item.title?html}
                                </div>
                               <#if item.streeMain?? && item.streeMain?has_content>  
                                <div class="koh-company-street">
                                   ${item.streeMain}
                                </div>
                               </#if> 
                               <#if item.streetSub?? && item.streetSub?has_content> 
                                <div class="koh-company-street">
                                   ${item.streetSub}
                                </div>
                               </#if> 
                                <#if item.phoneLabel?? && item.phoneLabel?has_content>
	                                <div class="koh-company-phone">
	                                     ${item.phoneLabel}
	                                </div>
                                </#if>
                                <#if item.phoneNumber?has_content && item.phoneNumber??>
	                                <div class="koh-company-phone">
	                                    <strong>${kohCompanyPhone}</strong> <a href="tel:${item.phoneNumber}">${item.phoneNumber}</a>
	                                </div>
	                            </#if>    
                                <#if item.fax?has_content && item.fax??>
	                                <div class="koh-company-fax">
	                                    <strong>${kohCompanyFax}</strong> ${item.fax}
	                                </div>
                                </#if>
                                <#if item.email?has_content && item.email??>
	                                <div class="koh-company-email">
	                                    <strong>${kohCompanyEmail}</strong> <a href="mailto:${item.email}">${item.email}</a>
	                                </div>
                                </#if>
                                <#if item.externalLink?? && item.externalLink?has_content>
                                <div class="koh-company-more">
                                    <a href="${item.externalLink}" target="_blank" rel="noopener noreferrer">${kohCompanyMore}</a>
                                </div>
                                </#if> 
                            </li> 
                            </#list>		    
		                   </#if>                        
                        </ul>
                    </address>
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 koh-address-sidebar">
                    <div class="koh-address-sidebar-inner">
                        <@hst.include ref="container"/>
                        <#if contactUsEnquiryWithUsFlag?? && contactUsEnquiryWithUsFlag?has_content>
                        	<a href="${shareContactFormLink}" class="koh-enquire-with-us-btn">${contactUsEnquiryWithUs}</a>
                        </#if>	
                        
                        <a href="${storelink}" class="koh-enquire-with-us-btn">${enquireNowTitle}</a>
                    </div>
                </div>
            </div>
        </div>
    </section>
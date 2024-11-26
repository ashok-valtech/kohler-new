<#include "../include/imports.ftl">
<@hst.headContributions xhtml=true categoryExcludes="ext-scripts"/>
<@fmt.message key="contact" var="contact"/>
<@fmt.message key="call-us-today" var="callUsToday"/>
<@fmt.message key="contact-us-now" var="contactUsNow"/>
<@fmt.message key="address" var="address"/>
<@fmt.message key="phone" var="phone"/>
<@fmt.message key="facilityhours" var="facilityhours"/>
<@fmt.message key="mondaytofriday" var="mondayFirday"/>
<@fmt.message key="sundayClosed" var="sundayClosed"/>
<@fmt.message key="saturday1014" var="saturday1014"/>
<@fmt.message key="inspired-creations" var="inspiredCreations"/>
<@fmt.message key="new-products" var="newProducts"/>
<@fmt.message key="kohler-global-projects" var="kohlerGlobalProjects"/>
<@fmt.message key="mobile" var="mobile"/>
<@fmt.message key="Email" var="email"/>
<@fmt.message key="primary-contact" var="primaryContact"/>
<@fmt.message key="fax" var="fax"/>
<@fmt.message key="company.name" var="companyName"/>
<@hst.include ref="container"/>
<@hst.webfile path="/" var="link" />
<@fmt.message key="whereToBuyDetailsMetaDesc" var="whereToBuyDetailsMetaDesc"/>
<@fmt.message key="storeLogoDefaultImg" var="storeLogoDefaultImg"/>
<@fmt.message key="storeLogoSrcSet" var="storeLogoSrcSet"/>
<@fmt.message key="bingdatasource" var="bingdatasource"/>
<@fmt.message key="bingdatasourcekeys" var="bingdatasourcekeys"/>
<#assign language = hstRequest.getLocale().getLanguage() />
	<input type="hidden" id="redpin" name="Language" value="${link}/images/assets/red_pin.png">
	<input type="hidden" class="koh-bingdatasource" data-bingdatasource="${bingdatasource}">
	<input type="hidden" class="koh-bingdatasourcekeys" data-bingdatasourcekeys="${bingdatasourcekeys}">
	<input type="hidden" class="koh-bingdatasourcelanguage" data-bingdatasourcelanguage="${language}">

    <section class="c-koh-where-to-buy">
        <div class="koh-where-to-buy-container">
	        <#if storeDetail.nameOfStore?has_content>
	            <header>
	                <h2 class="koh-where-to-buy-title">${storeDetail.nameOfStore}</h2>
	            </header>
	        </#if>
          <div class="koh-where-to-buy-section col-xs-12">
                <div class="col-lg-2 col-md-2 col-sm-3 col-xs-4 koh-showroom-image">
               		<#if storeDetail.storeLogo?? && storeDetail.storeLogo?has_content>
                    	<img src="${storeDetail.storeLogo}" class="attachment-showroom-logo size-showroom-logo wp-post-image" alt="${storeDetail.storeLogo}" sizes="(max-width: 175px) 100vw, 175px" width="175" height="175">
                    <#else>
                    	<img src="${storeLogoDefaultImg}" class="attachment-showroom-logo size-showroom-logo wp-post-image" alt="" srcset="${storeLogoSrcSet}" sizes="(max-width: 175px) 100vw, 175px" width="175" height="175">
                    </#if>
                </div>
                <div class="col-lg-10 col-md-10 col-sm-9 col-xs-8 koh-showroom-info-text">
                    <#if storeDetail.phone?has_content || storeDetail.generalEmail?has_content>
	                    <div class="koh-showroom-info-text-inner">
	                        <h3>${contact}</h3>
	                        <#if storeDetail.phone?? && storeDetail?has_content>
		                       	<p>${callUsToday} <#if storeDetail.phone[0]?has_content><a href="tel:${storeDetail.phone[0]}">${storeDetail.phone[0]}</a></#if> 
		                       					  <#if storeDetail.phone[1]?has_content><a href="tel:${storeDetail.phone[1]}">| ${storeDetail.phone[1]}</a></#if>  
		                       					  <#if storeDetail.phone[2]?has_content><a href="tel:${storeDetail.phone[2]}">| ${storeDetail.phone[2]}</a></#if> 
		                       	</p>
	                        </#if>
	                         <#if storeDetail.generalEmail?has_content>
	                      	  <a href="mailto:${storeDetail.generalEmail}" class="btn btn-default btn-call-us-now">${contactUsNow}</a>
	                      	</#if>
	                    </div>
	                </#if>
                </div>
            </div>
            <div class="koh-where-to-buy-section-map col-xs-12">
                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 koh-address-list">
                    <div class="koh-address-list-info">
                   	    <#if storeDetail.addressLine1?has_content || storeDetail.addressLine2?has_content ||storeDetail.addressLine3?has_content>
	                        <p>
	                            <strong>${address}</strong> 
	                            <#if storeDetail.addressLine1?has_content>
	                             	${storeDetail.addressLine1}
	                             </#if>
	                             <#if storeDetail.addressLine2?has_content>
	                           		 , ${storeDetail.addressLine2}
	                             </#if>
	                            <#if storeDetail.addressLine3?has_content>
	                            	, ${storeDetail.addressLine3}
	                             </#if>
	                             <#if storeDetail.city?has_content>
	                            	, ${storeDetail.city}
	                             </#if>
	                              <#-- <#if storeDetail.state?has_content>
	                            	, ${storeDetail.state}
	                            	 </#if>-->	                            	 
	                            <#if storeDetail.state?? && storeDetail.state?has_content>
								    <#if storeDetail.city != storeDetail.state>
								        , ${storeDetail.state}
								    </#if>
								</#if>
	                             <#if storeDetail.zip?has_content>
	                            	- ${storeDetail.zip}
	                             </#if>
	                        </p>
                        </#if>
                        <#if storeDetail.phone?has_content || storeDetail.mobilePhone?has_content || storeDetail.fax?has_content>
	                        <p>
	                        	<#if storeDetail.phone?has_content>
	                            	<strong>${phone}</strong> 
	                            		<#if storeDetail.phone[0]?has_content>
	                            			<a href="tel:${storeDetail.phone[0]}">${storeDetail.phone[0]}</a></br>                       		
	                            		</#if>
	                            		<#if storeDetail.phone[1]?has_content>
											<a href="tel:${storeDetail.phone[1]}">${storeDetail.phone[1]}</a></br>                       		
	                            		</#if>
	                            		<#if storeDetail.phone[2]?has_content>
											<a href="tel:${storeDetail.phone[2]}">${storeDetail.phone[2]}</a>                            		
	                            		</#if>
	                            </#if>
	                            <#if storeDetail.mobilePhone?has_content>
	                            	<strong>${mobile}</strong><a href="tel:${storeDetail.mobilePhone}">${storeDetail.mobilePhone}</a>   
	                            </#if>
	                            <#if storeDetail.fax?has_content>
	                            	<strong>${fax}</strong> ${storeDetail.fax}
	                            </#if>
	                        </p>
                        </#if>
                        
                        
                        
                        <#if storeDetail.primaryContactEmail?has_content || storeDetail.generalEmail?has_content>
		                    <p>
			                    <strong>${email}</strong>
			                    <#if storeDetail.primaryContactEmail?has_content>
			                    	<a href="mailto:${storeDetail.primaryContactEmail}">${storeDetail.primaryContactEmail}</a><br/>
			                    </#if> 
			                    <#if storeDetail.generalEmail?has_content>
			                    	<a href="mailto:${storeDetail.generalEmail}">${storeDetail.generalEmail}</a>
			                	</#if>
		                    </p>
	                    </#if>
	                            
                        <#if storeDetail.primaryContactName?has_content || storeDetail.primaryContactName?has_content>
	                        <p>
	                            <strong>${primaryContact}</strong> ${storeDetail.primaryContactName}
	                        </p>
                        </#if>
                        <#if storeDetail.openingHours?? && storeDetail.openingHours?has_content>
	                        <p>
	                            <strong>${facilityhours}</strong> ${storeDetail.openingHours[0]}
	                            <br> ${storeDetail.openingHours[1]}
	                            <br> ${storeDetail.openingHours[2]}
	                        </p>
	                    </#if>
                        <#if storeDetail.website?has_content>
	                        <p>
	                            <strong>Website</strong> <a href="${storeDetail.website}" target="_blank" rel="noopener noreferrer">${storeDetail.website}</a>
	                        </p>
                        </#if>
                    </div>
                    <ul class="koh-social-share">
                     <#if storeDetail.facebookLink?has_content>
                        <li>
                            <a class="koh-sfacebook-share" href="${storeDetail.facebookLink}" target="_blank" rel="noopener noreferrer"><span class="icon" data-icon=""></span></a>
                        </li>
                      </#if>
                      <#if storeDetail.twitterLink?has_content>
                        <li>
                            <a class="koh-stwitter-share" href="${storeDetail.twitterLink}" target="_blank" rel="noopener noreferrer"><span class="icon" data-icon=""></span></a>
                        </li>
                      </#if>
                      <#if storeDetail.instagramLink?has_content>
                        <li>
                            <a class="koh-sinstagram-share" href="${storeDetail.instagramLink}" target="_blank" rel="noopener noreferrer"><span class="icon" data-icon=""></span></a>
                        </li>
                      </#if>
                      <#if storeDetail.lineLink?has_content>
                        <li>
                            <a class="koh-slinelink" href="${storeDetail.lineLink}" target="_blank" rel="noopener noreferrer"><span class="icon" data-icon=""></span></a>
                        </li>
                      </#if>
                    </ul>
                </div>
                <div class="col-lg-8 col-md-8 col-sm-6 col-xs-12 koh-address-map">
                   <table class="rootTable" border="0"> 
				        <tr> 
				            <td class="mapPanel"><div id="myMap"></div></td> 
				        </tr> 
				    </table> 
                 </div>               
            </div>
        </div>
    </section>

<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="${link}/js/custom/koh-locator.min.js"></script> 
</@hst.headContribution>
<@hst.headContribution category="ext-scripts">
    <script type="text/javascript" src="https://www.bing.com/api/maps/mapcontrol?callback=GetMap"></script> 
</@hst.headContribution>
<@hst.headContribution category="title">
	<title>${storeDetail.nameOfStore} | ${companyName}</title>
</@hst.headContribution>

<#if storeDetail.nameOfStore??>
<#assign SEOKeyword=storeDetail.nameOfStore+ " , " + storeDetail.addressLine1 + " , " + storeDetail.addressLine2 + " , " + storeDetail.addressLine3 />
</#if>
<@hst.headContribution category="meta-tags">
	<meta name="description" content="${whereToBuyDetailsMetaDesc}"/>
</@hst.headContribution>


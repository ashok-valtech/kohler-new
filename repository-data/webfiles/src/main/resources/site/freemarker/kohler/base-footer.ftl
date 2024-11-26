<#include "../include/imports.ftl">
<@hst.include ref="container"/>
<@hst.link var="storelink" siteMapItemRefId="storelocator"/>
<@fmt.message key="print-footer" var="printFooter"/>
<@fmt.message key="enquire-now-title" var="enquireNowTitle"/>
<@fmt.message key="whatsAppLink" var="whatsAppLink"/>
<@fmt.message key="whatsAppTitle" var="whatsAppTitle"/>
<@fmt.message key="isWhatsApp" var="isWhatsApp"/>
<@fmt.message key="call" var="call"/>
<@fmt.message key="email-us-floating-icon-title" var="emailUsTitle"/>
<@fmt.message key="floating-icon-phone-number" var="phoneNumber"/>
<@fmt.message key="floating-icon-line-URL" var="lineURL"/>
<@fmt.message key="floating-icon-whatsapp-URL" var="whatsappURL"/>
<@fmt.message key="floating-icon-emailUs-URL" var="emailUsURL"/>
<@fmt.message key="floating-chat-with-us-title" var="chatWithUs"/>

<#assign printFooter = printFooter?replace("{0}","${hstRequestContext.getBaseURL().getHostName()}") />
<#assign locale = hstRequest.getLocale().getCountry() />

<div class="c-koh-printfooter">
	${printFooter}
</div>

<div class="c-koh-footer-nav">
	<#if FooterNavigation??>
		<ul class="koh-nav-items">
			<#list FooterNavigation.children as item>
		            <#if item.children?has_content>
		                  <#list item.children as subItem>
							<li class="koh-nav-section">
								<span class="koh-nav-section-title">${subItem.siteMenuItem.name?html}</span>
								<#if subItem.children?has_content>
									<ul class="koh-nav-section-items">
										<#list subItem.children as subMenuItem1>
				                      		<#if subMenuItem1.menu??>
				                      			<#if subMenuItem1.menu.menuLink?has_content>
				                      				<#assign target="_blank" />
				                      				<#assign rel="noopener noreferrer" />
				                      				<#assign href=subMenuItem1.menu.menuLink />
					                      			<#if subMenuItem1.menu.menuLink?starts_with("/")>
					                      				<#assign target="_self" />
					                      				<#assign rel="" />
					                      				<#assign href=href/> 
		                      						</#if>
					                      		</#if>
					                      		<#if subMenuItem1.menu.image??>	
						                      		<@hst.link var="img" hippobean=subMenuItem1.menu.image/>
						                      	</#if>	
				                      			<li>
												   <#if subMenuItem1.menu.iconCode == 'Cookies'>
					                      					<a id="ot-sdk-link" class="ot-sdk-show-settings" href="#" rel="noopener noreferrer">
																${subMenuItem1.siteMenuItem.name?html}
															</a>
				                      					<#else>
														   <a <#if href?? && href?has_content>href="${href}"</#if> 
								                      			<#if target?? && target?has_content>target="${target}"</#if>
								                      			 <#if rel?has_content>rel="${rel}"</#if>>
																		<#if subMenuItem1.menu.iconCode?has_content>
																			<#if subMenuItem1.menu.iconCode == 'default'>
																			  	<span class="icon default-icon"></span>
																			<#elseif subMenuItem1.menu.iconCode == 'tiktok'>
																				<span class="icon tiktok-icon"></span>
																			<#elseif subMenuItem1.menu.iconCode == 'zalo'>
																				<span class="icon zalo-icon"></span>
																			<#else>
																			<span class="icon" data-icon="${subMenuItem1.menu.iconCode}"></span>
																		</#if>
									                      			</#if>
																	<span class="label">${subMenuItem1.siteMenuItem.name?html}</span>
															</a>
														</#if>
												</li>
				                      		<#elseif subMenuItem1.siteMenuItem.externalLink?has_content>
				                      			<li><a href="${subMenuItem1.siteMenuItem.externalLink}" target="_blank" rel="noopener noreferrer"><span class="label">${subMenuItem1.siteMenuItem.name?html}</span></a></li>
				                      		<#elseif subMenuItem1.siteMenuItem.hstLink?has_content>
				                      			<li><a href="<@hst.link link=subMenuItem1.siteMenuItem.hstLink/>"><span class="label">${subMenuItem1.siteMenuItem.name?html}</span></a></li>	
				                      		<#else>
				                      			<li><a><span class="label">${subMenuItem1.siteMenuItem.name?html}</span></a></li>	
				                      		</#if>
				                       </#list>
									</ul>
								</#if>
							</li>
						</#list>
		            </#if>
			</#list>
		</ul>
	</#if>
	
	<!--Footer mobile social links -->
	<#if FooterNavigation??>
	<div class="koh-social-links">
		<span>Follow Us</span>
		<#list FooterNavigation.children as item>
			<#if !item?has_next>
				<#if item.children?has_content>
					<#list item.children as subItem>
								<#if subItem.children?has_content>
									<ul class="koh-social-icons">
										<#list subItem.children as subMenuItem1>
				                      		<#if subMenuItem1.menu??>
				                      			<#if subMenuItem1.menu.menuLink?has_content>
				                      				<#assign target="_blank" />
				                      				<#assign rel="noopener noreferrer" />
				                      				<#assign href=subMenuItem1.menu.menuLink />
					                      			<#if subMenuItem1.menu.menuLink?starts_with("/")>
					                      				<#assign target="_self" />
					                      				<#assign rel="" />
					                      				<#assign href=href/> 
		                      						</#if>
					                      		</#if>
					                      		<#if subMenuItem1.menu.image??>	
						                      		<@hst.link var="img" hippobean=subMenuItem1.menu.image/>
						                      	</#if>	
				                      			<li>
												   <#if subMenuItem1.menu.iconCode == 'Cookies'>
					                      					<a id="ot-sdk-link" class="ot-sdk-show-settings" href="#" rel="noopener noreferrer">
																${subMenuItem1.siteMenuItem.name?html}
															</a>
				                      					<#else>
														   <a <#if href?? && href?has_content>href="${href}"</#if> 
								                      			<#if target?? && target?has_content>target="${target}"</#if>
								                      			 <#if rel?has_content>rel="${rel}"</#if>>
																		<#if subMenuItem1.menu.iconCode?has_content>
																			<#if subMenuItem1.menu.iconCode == 'default'>
																			  	<span class="social-icons"></span>
																			<#elseif subMenuItem1.menu.iconCode == 'tiktok'>
																				<span class="icon tiktok-icon"></span>
																			<#elseif subMenuItem1.menu.iconCode == 'zalo'>
																				<span class="icon zalo-icon"></span>
																			<#else>
																			<span class="kohler-icon" data-icon="${subMenuItem1.menu.iconCode}"></span>
																		</#if>
									                      			</#if>
																	<#--  <span class="label">${subMenuItem1.siteMenuItem.name?html}</span>  -->
															</a>
														</#if>
												</li>
				                      		<#--  <#elseif subMenuItem1.siteMenuItem.externalLink?has_content>
				                      			<li><a href="${subMenuItem1.siteMenuItem.externalLink}" target="_blank" rel="noopener noreferrer"><span class="label">${subMenuItem1.siteMenuItem.name?html}</span></a></li>
				                      		<#elseif subMenuItem1.siteMenuItem.hstLink?has_content>
				                      			<li><a href="<@hst.link link=subMenuItem1.siteMenuItem.hstLink/>"><span class="label">${subMenuItem1.siteMenuItem.name?html}</span></a></li>	
				                      		<#else>
				                      			<li><a><span class="label">${subMenuItem1.siteMenuItem.name?html}</span></a></li>	  -->
				                      		</#if>
				                       </#list>
									</ul>
								</#if>
						</#list>
					</#if>
				</#if>
		</#list>
	</div>
</#if>
</div>
<@fmt.message key="copy-right" var="copyRight"/>
<#assign aDateTime = .now>
<#assign copyRight = copyRight?replace("{0}","${aDateTime?string.yyyy}") />
<div class="c-koh-footer-copyright">
	<ul class="koh-footer-items">
	  <#if CopywriteNavigation??>
	  	<#list CopywriteNavigation.children as item>
		     <#if item.children?has_content>
			 	<#list item.children as subItem>
					<#if subItem.menu??>
						<#if subItem.menu.menuLink?has_content>
							<#assign target="_blank" />
								<#assign rel="noopener noreferrer" />
								<#assign href=subMenuItem.menu.menuLink />
								<#if subMenuItem.menu.menuLink?starts_with("/")>
									<#assign target="_self" />
									<#assign rel="" />
									<#assign href=href/> 
								</#if>
							<li>
								<a 
								<#if href?? && href?has_content>href="${href}"</#if>
								<#if target?? && target?has_content>target="${target}"</#if>
								<#if rel?has_content>rel="${rel}"</#if>
								>${subItem.siteMenuItem.name?html}</a>	
							</li>
						</#if>		
					<#elseif subItem.siteMenuItem.externalLink?has_content>
				        <li><a href="${subItem.siteMenuItem.externalLink}" target="_blank" rel="noopener noreferrer">${subItem.siteMenuItem.name?html}</a></li>
					<#elseif subItem.siteMenuItem.hstLink?has_content>
				        <li><a href="<@hst.link link=subItem.siteMenuItem.hstLink/>">${subItem.siteMenuItem.name?html}</a></li>	
				    <#else>
				        <li><a>${subMenuItem1.siteMenuItem.name?html}</a></li>		
					</#if>
				</#list>
			</#if>
		</#list>	
	 </#if>
		<span class="koh-copyright-text">${copyRight}</span>
	</ul>
</div>

<#if locale == "TH">
	<div class="koh-chat-with-us">
		<button class="chat-open-dialog"><span class="fa-chat"></span><span class="chat-btn-text">${whatsAppTitle}</span></button>
	  	<button class="chat-button-destroy"><span class="fa-close"></span></button>
		<div class="chat-popup">
		    <div class="chat-windows">
		      	<ul>
			        <li><a href="tel:${phoneNumber}"><span class="li-icon fa-call"></span>${call}</a></li>
			        <li><a href="${lineURL}" target="_blank"><span class="li-icon fa-line"></span>${chatWithUs}</a></li>
			        <li><a href="${whatsappURL}" target="_blank"><span class="li-icon fa-whatsapp"></span>${chatWithUs}</a></li>
			        <li><a href="mailto:${emailUsURL}"><span class="li-icon fa-learnmore"></span>${emailUsTitle}</a></li>
		      	</ul>
	    	</div>
	  	</div>
	</div>
</#if>

<#if isWhatsApp?? && isWhatsApp?has_content>
	<a class="koh-whatsApp-btn" href="${whatsAppLink}" target="_blank"><span class="icon koh-icon-envelope"></span><span class="chat-btn-text">${whatsAppTitle}</span></a>
</#if>
<a class="koh-fixed-find-store-btn" href="${storelink}"><span class="icon koh-icon-envelope"></span><span class="find-store-text">${enquireNowTitle}</span></a>

<#--  <#if locale == "TH">  -->
    <@hst.webfile path="/" var="link" />
    <@hst.headContribution category="ext-scripts">
            <script src="${link}/js/custom/c-koh-chat-with-us.min.js" type="text/javascript"></script>
    </@hst.headContribution>
<#--  </#if>  -->
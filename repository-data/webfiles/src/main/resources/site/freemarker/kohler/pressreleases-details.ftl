<#include "../include/imports.ftl">
<@fmt.message key="share" var="share"/>
<@fmt.message key="facebook" var="facebook"/>
<@fmt.message key="twitter" var="twitter"/>
<@fmt.message key="print" var="print"/>
<@fmt.message key="media-contacts" var="mediaContactsDetail"/>
<@fmt.message key="back-to-top" var="backTotop"/>
<@fmt.message key="pressReleasesMetaKeyword" var="pressReleasesMetaKeyword"/>

<@hst.include ref="container"/>

<section class="c-koh-press-release">
    <div class="koh-release-container">
        <div class="koh-release-header col-xs-12">
           <#if document??>
            <h1 class="koh-release-title">${document.title?html}</h1>
         <#if document.subtitle?? && document.subtitle?has_content>   
           <#assign subtitle = document.subtitle?replace("(R)","<sup>&reg;</sup>")/>
		   <#assign subtitle = subtitle?replace("(TM)","<sup>&trade;</sup>")/>   
            <span class="koh-release-description">${subtitle}</span>
         </#if> 
            <div class="koh-print-share-tools">
                <div class="koh-page-print">
                    <button onclick="javascript:window.print()" class="koh-page-print-button" data-hasqtip="2">
                        <span class="icon" data-icon=""></span>
                        <span class="label">Print</span>
                    </button>
                </div>
                <div class="koh-page-share">
                    <button class="koh-page-share-button" data-hasqtip="0"><span class="icon" data-icon="&#xe603"></span><span class="label">Share</span></button>
                    <ul class="koh-page-share-popover">
                        <li>
                            <button class="koh-share-facebook"><span class="icon" data-icon="&#xe609"></span><span class="label">Facebook</span></button>
                        </li>
                        <li>
                            <button class="koh-share-twitter"><span class="icon" data-icon="&#xe60d"></span><span class="label">Twitter</span></button>
                        </li>
                    </ul>
                </div>
            </div>
        </div>     
        <div class="koh-release-content col-lg-8 col-md-8 col-sm-7 col-xs-12">
            <div class="koh-release-content-section">
                <@hst.html hippohtml=document.text />
            </div>
        </div>
        </#if>
        <div class="koh-release-sidebar col-lg-4 col-md-4 col-sm-5 col-xs-12 ">
        <#if mediaContactsDetail??  && mediaContactsDetail?has_content>
            <div class="koh-release-contacts">
                <h3 class="koh-release-contacts-title">${mediaContactsDetail}</h3>
                <#if mediaContacts??>
                   <#list mediaContacts as mediaContactsitem>
                <ul class="koh-press-room-list">
                    <li class="col-xs-12">
                        <div class="koh-media-contact-name">${mediaContactsitem.name?html}</div>
                        <div class="koh-media-contact-designation">${mediaContactsitem.designation?html}</div>
                        <div class="koh-media-contact-email"><a href="mailto:${mediaContactsitem.email?html}">${mediaContactsitem.email?html}</a></div>
                        <div class="koh-media-contact-phone"><a href="tel:${mediaContactsitem.mobile?html}">${mediaContactsitem.mobile?html}</a></div>
                    </li>
                </ul>
                   </#list>
                </#if>
            </div>
          </#if>  
        </div>
    </div>
    <div class="koh-back-top">
        <button style="display: none;">
            <span class="icon" data-icon=""></span>${backTotop}
        </button>
    </div>
    <!-- koh-back-top -->
</section>
	<@fmt.message key="company.name" var="companyName"/>	

<#-- og tags for twitter-->
	<@hst.headContribution category="meta-tags">
	  	<meta name="twitter:title" content="${document.title?html}" />
	</@hst.headContribution>
	<@hst.headContribution category="meta-tags">
	  	<meta name="twitter:description" content="${document.description?html}" />
	</@hst.headContribution>
	<@hst.headContribution category="meta-tags">
	  	<meta name="twitter:site" content="@${companyName}" />
	</@hst.headContribution>

<#-- og tags for facebook-->
	<@hst.headContribution category="meta-tags">
	  	<meta property="og:title" content="${document.title?html}" />
	</@hst.headContribution>
	<@hst.headContribution category="meta-tags">
		<meta property="og:description" content="${document.description?html}" /> 
	</@hst.headContribution>
	<@hst.headContribution category="meta-tags">
	  	<meta property="og:site_name" content="${companyName}" />
	</@hst.headContribution>

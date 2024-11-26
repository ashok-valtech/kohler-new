<#include "../include/imports.ftl">
<@hst.link var="homeLink" siteMapItemRefId="root"/>
<@hst.link var="contactus" siteMapItemRefId="contact-us"/>
<@fmt.message key="pagenotfound.text" var="text"/>
<@fmt.message key="pagenotfound.title" var="title"/>
<@fmt.message key="company.name" var="companyName"/>
<@fmt.message key="page-not-found" var="pageNotFound"/>
<section class="c-koh-simple-content v-koh-full-page">
    <div class="koh-simple-content-container">
        <h2 class="koh-simple-content-title">
           ${title?html}
        </h2>
        <div class="koh-simple-content-body">
         <#assign text = text?replace("{0}",homeLink) />
         <#assign text = text?replace("{1}",contactus) />
         ${text}
         <@hst.include ref="container"/>
        </div>
    </div>
</section>
<@hst.headContribution category="title">
		<title>${pageNotFound} | ${companyName}</title>
</@hst.headContribution>
<@hst.headContribution category="title">
		<META NAME="ROBOTS" CONTENT="NOINDEX, NOFOLLOW" />
</@hst.headContribution>

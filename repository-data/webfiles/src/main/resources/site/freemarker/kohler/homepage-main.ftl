<#include "../include/imports.ftl">

<@fmt.message key="company.name" var="companyName"/>	
<@fmt.message key="company.region" var="companyRegion"/>
<@fmt.message key="bathroom" var="bathroom"/>
<@fmt.message key="kitchen" var="kitchen"/>
<@fmt.message key="homeMetaKeyword" var="homeMetaKeyword"/>
<@fmt.message key="ogTitle" var="ogTitle"/>
<@fmt.message key="ogDescription" var="ogDescription"/>
<@fmt.message key="ogUrl" var="ogUrl"/>

<@fmt.message key="isSchemaPresent" var="isSchemaPresent"/>
<@fmt.message key="schema-homepage-logo" var="schemaLogo"/>
<@fmt.message key="schema-homepage-image" var="schemaImage"/>
<@fmt.message key="schema-homepage-name" var="schemaName"/>
<@fmt.message key="schema-homepage-legalname" var="schemaLegalName"/>
<@fmt.message key="schema-homepage-description" var="schemaDesc"/>
<@fmt.message key="schema-homepage-founding-date" var="schemaFoundingDate"/>
<@fmt.message key="schema-homepage-sameas-facebook" var="schemaFacebook"/>
<@fmt.message key="schema-homepage-sameas-instagram" var="schemaInstagram"/>
<@fmt.message key="schema-homepage-sameas-zalo" var="schemaZalo"/>
<@fmt.message key="schema-homepage-sameas-youtube" var="schemaYoutube"/>
<@fmt.message key="schema-homepage-adresslocality" var="schemaAddressLocality"/>
<@fmt.message key="schema-homepage-adressregion" var="schemaAddressRegion"/>
<@fmt.message key="schema-homepage-postalcode" var="schemaPostalcode"/>
<@fmt.message key="schema-homepage-streetaddress" var="schemaStreetAddress"/>
<@fmt.message key="schema-homepage-telephone" var="schemaPhone"/>
<@fmt.message key="schema-homepage-email" var="schemaEmail"/>
<@hst.include ref="container"/>

<#if hstRequestContext??>
	<#assign partOfURL = hstRequestContext.getServletRequest().getPathInfo() />
	<#assign currentURL = hstRequestContext.getHstLinkCreator().create(partOfURL, hstRequestContext.getResolvedMount().getMount()).toUrlForm(hstRequestContext, true) />
</#if>

<#if ogTitle?? && ogTitle?has_content>
	<@hst.headContribution category="meta-tags">
	    <meta name="og:title" content="${ogTitle}"/>
	</@hst.headContribution>
</#if>

<#if ogDescription?? && ogDescription?has_content>
	<@hst.headContribution category="meta-tags">
	    <meta name="og:description" content="${ogDescription}"/>
	</@hst.headContribution>
</#if>

<#if ogUrl?? && ogUrl?has_content>
	<@hst.headContribution category="meta-tags">
	    <meta name="og:url" content="${ogUrl}"/>
	</@hst.headContribution>			
</#if>


<#if isSchemaPresent?? && isSchemaPresent?has_content>
	<@hst.headContribution category="schema">
		<script type="application/ld+json">
			{
			  "@context": "https://schema.org",
			  "@type": "Organization",
			  "@id": "${currentURL}",
			  "url": "${currentURL}",
			  "logo": "${schemaLogo}",
			  "image": "${schemaImage}",
			  "name": "${schemaName}",
			  "legalName": "${schemaLegalName}",
			  "description": "${schemaDesc}",
			  "foundingDate": "${schemaFoundingDate}",
			  "sameAs": [
				   "${schemaFacebook}",
				   "${schemaInstagram}",
				   "${schemaZalo}",
				   "${schemaYoutube}"
			  ],
			  "address": {
			    "@type": "PostalAddress",
			    "addressLocality": "${schemaAddressLocality}",
			    "AddressRegion": "${schemaAddressRegion}",
			    "postalCode": "${schemaPostalcode}",
			    "streetAddress": "${schemaStreetAddress}"
			  },
			  "contactPoint": {
			    "@type": "contactPoint",
			    "Telephone": "${schemaPhone}",
			    "Email": "${schemaEmail}"
			  }
		  }
		</script>
	</@hst.headContribution>
</#if>
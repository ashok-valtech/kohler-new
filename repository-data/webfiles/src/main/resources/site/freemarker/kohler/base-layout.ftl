<!doctype html>
<#include "../include/imports.ftl">
 <@fmt.message key="apac-GA" var="apacGA" />
 <@fmt.message key="dtm_file" var="dtmFile" />
 <@fmt.message key="naver-site-verification" var="naverSiteVerification" />
 <@fmt.message key="gtm_marketting_code" var="gtmmarkettingcode" />
 <@fmt.message key="gtm_marketting_code_1" var="gtmmarkettingcode1" />
 <@fmt.message key="facebook_pixel_code" var="facebookpixelcode" />
 <@fmt.message key="google_tag_code" var="googletagcode" />
 <@fmt.message key="iperceptions-link" var="helpUsImproveLink" />
 <@fmt.message key="iperceptions-title" var="helpUsImproveTitle" />
 <@fmt.message key="iperceptions-key" var="helpUsImproveKey" />
 <@fmt.message key="consent-manager-source" var="consentManagerSource" />
 <@fmt.message key="linkedin_partner_id" var="linkedin_partner_id" />
 <@fmt.message key="Kohler-Biz-Schema" var="kohlerBizSchema" />
 <@fmt.message key="gtm_site_tag_global_code" var="gtmsitetagglobalcode"/>
 <@fmt.message key="gtm_site_tag_conversation_code" var="gtmsitetagconversationcode"/>
 <@fmt.message key="gsc-Seo-Code" var="gscSeoCode" />
 <@fmt.message key="fb-Domain-Verification" var="fbDomainVerification" />
 <@fmt.message key="ga4integrationcode" var="ga4integrationcode" />
 <@fmt.message key="oneTrust-cookies-id" var="oneTrustCookiesId" />

 
 <#assign language= hstRequestContext.getResolvedMount().getMount().getLocale()?keep_before("_") />
 <@hst.link var="promoThankYouPage" siteMapItemRefId="promoThankYouPage"/>

<#assign locale = hstRequest.getLocale().getCountry() />

<#--  <#if hstRequestContext??>
    <#assign partOfCurrentURL = hstRequestContext.getServletRequest().getPathInfo() />
    <#-- <#assign currentURL = hstRequestContext.getHstLinkCreator().create(partOfCurrentURL, hstRequestContext.getResolvedMount().getMount()).toUrlForm(hstRequestContext, true) />  -->
<#--  </#if>  -->

<html lang="${language}">
  <head>
  	
  	  <@hst.headContributions categoryIncludes="SEO, title, meta-tags, schema" categoryExcludes="ext-scripts"  xhtml=true/>
	  
	  	<#if oneTrustCookiesId?? && oneTrustCookiesId?has_content>
		  	<!-- OneTrust Cookies Consent Banner -->
		  	<script type="text/javascript" src="https://cdn.cookielaw.org/consent/${oneTrustCookiesId}/OtAutoBlock.js"></script>
			<script src="https://cdn.cookielaw.org/scripttemplates/otSDKStub.js"  type="text/javascript" charset="UTF-8" data-domain-script="${oneTrustCookiesId}"></script>
			<script type="text/javascript">
				function OptanonWrapper() { }
			</script>
		</#if>
  	  
	  <#if gtmmarkettingcode?? && gtmmarkettingcode?has_content>
			<!-- Google Tag Manager -->
				<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
				new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
				j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
				'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
				})(window,document,'script','dataLayer','${gtmmarkettingcode}');</script>
			<!-- End Google Tag Manager -->
	  </#if> 
	    
	  <#if gtmmarkettingcode1?? && gtmmarkettingcode1?has_content>
			<!-- Google Tag Manager -->
				<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
				new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
				j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
				'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
				})(window,document,'script','dataLayer','${gtmmarkettingcode1}');</script>
			<!-- End Google Tag Manager -->
	  </#if> 
	  
	   <#if gtmsitetagglobalcode?? && gtmsitetagglobalcode?has_content>
			<!-- Global site tag (gtag.js) -->
 				<script async src="https://www.googletagmanager.com/gtag/js?id=${gtmsitetagglobalcode}">
				 </script>
 				<script> window.dataLayer = window.dataLayer || []; function gtag(){dataLayer.push(arguments);} 
 				  gtag('js', new Date()); gtag('config', '${gtmsitetagglobalcode}'); 
 				 </script>
	   </#if>
	   
	   <#if ga4integrationcode?? && ga4integrationcode?has_content>
		   <!-- Google tag (gtag.js) -->
			<script async src="https://www.googletagmanager.com/gtag/js?id=${ga4integrationcode}"></script>
			<script>
			  window.dataLayer = window.dataLayer || [];
			  function gtag(){dataLayer.push(arguments);}
			  gtag('js', new Date());

			  gtag('config', '${ga4integrationcode}');
			</script>
		</#if>
	 
	 <#if promoThankYouPage?? && promoThankYouPage?has_content>
	   <#if hstRequestContext.getServletRequest().getPathInfo()? has_content && hstRequestContext.getServletRequest().requestURI?contains(promoThankYouPage) >
          <#if gtmsitetagconversationcode?? && gtmsitetagconversationcode?has_content>
			  <!-- Event snippet for Website lead conversion page --> 
	  	      <script> gtag('event', 'conversion', {'send_to': '${gtmsitetagconversationcode}'});
	          </script>
	     </#if> 
	   </#if>
	 </#if>
  		<@hst.webfile path="/" var="link" />
	 	<meta charset="UTF-8">
		<meta name="apple-mobile-web-app-title" content="[[Site Title]]">
		<meta name="viewport" content="width=device-width, initial-scale=1 user-scalable = no">
		<#if naverSiteVerification?? && naverSiteVerification?has_content>
		  <meta name="naver-site-verification" content="${naverSiteVerification}"/>      
		 </#if>  
		<#if gscSeoCode?? && gscSeoCode?has_content>
		  <meta name="google-site-verification" content="${gscSeoCode}"/>      
		</#if> 
		<#if fbDomainVerification?? && fbDomainVerification?has_content>
		 <meta name="facebook-domain-verification" content="${fbDomainVerification}" />     
		</#if> 
		<#assign currentSite=hstRequestContext.getResolvedMount().getMount() />
		<#assign currentSiteName=currentSite.getName() />
		<#assign parentSite="" />
		<#if currentSite.getParent()??>
			<#assign parentSite=currentSite.getParent() />
		<#else>
			<#assign parentSite=currentSite />
		</#if>
		<#assign parentSiteLocale = parentSite.getLocale()?replace("_","-")>
		<#if parentSiteLocale?? && parentSiteLocale?has_content?? && parentSiteLocale?contains("en")>
			<#assign parentSiteLocale=parentSiteLocale?keep_before("-") />
		</#if>
		
       	<link rel="alternate" href="${hstRequestContext.getHstLinkCreator().create(hstRequestContext.getBaseURL().getPathInfo(), parentSite).toUrlForm(hstRequestContext, true)}" hreflang="${parentSiteLocale}" />
		<#list parentSite.getChildMounts() as mount>	
		 	<#if mount.isMapped()?string('yes','no') == 'yes'>
		       	<link rel="alternate" href="${hstRequestContext.getHstLinkCreator().create(hstRequestContext.getBaseURL().getPathInfo(), mount).toUrlForm(hstRequestContext, true)}" hreflang="${mount.getLocale()?replace("_","-")}" />
			</#if>
		</#list>
		 	 	 	
		<link rel="shortcut icon" href="${link}/icons/favicon.ico" type="image/x-icon">
		<link rel="icon" href="${link}/icons/favicon.ico" type="image/vnd.microsoft.icon">
	    <link rel="apple-touch-icon-precomposed" href="${link}/icons/favicon.ico">
	  	<link rel="stylesheet" href="${link}/css/koh-master.min.css">
		
		<#if dtmFile?? && dtmFile?has_content>
        	<script src="//assets.adobedtm.com/23ceb4338332bedeec5e6fe47b0f620ec9fedced/${dtmFile}"></script>
		</#if>
        <#if facebookpixelcode?? && facebookpixelcode?has_content>
           <!-- Facebook Pixel Code -->
			<script>
			  !function(f,b,e,v,n,t,s)
			  {if(f.fbq)return;n=f.fbq=function(){n.callMethod?
			  n.callMethod.apply(n,arguments):n.queue.push(arguments)};
			  if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';
			  n.queue=[];t=b.createElement(e);t.async=!0;
			  t.src=v;s=b.getElementsByTagName(e)[0];
			  s.parentNode.insertBefore(t,s)}(window, document,'script',
			  'https://connect.facebook.net/en_US/fbevents.js');
			  fbq('init', '${facebookpixelcode}');
			  fbq('track', 'PageView');
			</script>
			<noscript><img height="1" width="1" style="display:none"
			  src="https://www.facebook.com/tr?id=${facebookpixelcode}&ev=PageView&noscript=1"
			/></noscript>
			<!-- End Facebook Pixel Code -->
			</#if>
	   <#if googletagcode?? && googletagcode?has_content>
	      <!-- Global site tag (gtag.js) - Google Ads: 782927586 -->
			<script async src="https://www.googletagmanager.com/gtag/js?id=${googletagcode}"></script>
			<script>
			  window.dataLayer = window.dataLayer || [];
			  function gtag(){dataLayer.push(arguments);}
			  gtag('js', new Date());
			  gtag('config', '${googletagcode}');
			</script>
			<!-- End  Global site tag Code -->
	     </#if>
		 <#if apacGA?? && apacGA?has_content>
			<script>
			  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
			  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
			  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
			  ga('create', '${apacGA}', 'auto');
			  ga('send', 'pageview');
			</script>
		</#if>
		<#if kohlerBizSchema?? && kohlerBizSchema?has_content>
			<#if kohlerBizSchemaDoc?? && kohlerBizSchemaDoc.script?has_content>
				<script type="application/ld+json">
					${kohlerBizSchemaDoc.script}
				</script>
			</#if>	
		</#if>
  </head>
  <body class="koh-bil-${language}">
  <input type="hidden" autocomplete="off" id="localeValue" value="${locale}" />
	<!-- <input type="hidden" id="consentURL" value="<#if consentManagerSource?? && consentManagerSource?has_content>${consentManagerSource}</#if>" />-->
	  <#if gtmmarkettingcode?? && gtmmarkettingcode?has_content>
			  <!-- Google Tag Manager (noscript) -->
				<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=${gtmmarkettingcode}"
				height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
			 <!-- End Google Tag Manager (noscript) -->
	  </#if>
	   <#if gtmmarkettingcode1?? && gtmmarkettingcode1?has_content>
			  <!-- Google Tag Manager (noscript) -->
				<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=${gtmmarkettingcode1}"
				height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
			 <!-- End Google Tag Manager (noscript) -->
	  </#if>
  		<div id="koh-page-outer" class="koh-page-outer">
			<div class="koh-page-inner">
				<header>
					<div class="koh-global-navigation">
							
							<@hst.include ref="header"/>
							
							<@hst.include ref="promo"/>
							
							<@hst.include ref="menu"/>
					</div>		
				</header>
				<div class="koh-page">
					<@hst.include ref="main" />
				</div>
				<footer>
				<#-- <#if helpUsImproveLink?has_content>
					<div class="koh-improve-site">
						<a href="${helpUsImproveLink}" title="${helpUsImproveTitle}" id="koh-improve-site-link">${helpUsImproveTitle}</a>
					</div>
				</#if> -->
					<@hst.include ref="footer"/>
				</footer>
			
			</div>
		</div>	
		
	  	<script data-ot-ignore src="${link}/js/jquery/jquery.min.js" type="text/javascript"></script>
	  	<script src="${link}/js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
      	<script src="${link}/js/lodash/lodash.min.js" type="text/javascript"></script>
      	<script src="${link}/js/remodal/remodal.min.js" type="text/javascript"></script>
      	<script src="${link}/js/qtip2/jquery.qtip.min.js" type="text/javascript"></script>
      	<script src="${link}/js/slick/slick.min.js" type="text/javascript"></script>
      	<script src="${link}/js/imagesloaded/imagesloaded.pkgd.min.js" type="text/javascript"></script>
      	<script src="${link}/js/masonry/masonry.pkgd.min.js" type="text/javascript"></script>
      	<script src="${link}/js/parsley/parsley.min.js" type="text/javascript"></script>
      	<script src="${link}/js/parsley/pt-br.min.js" type="text/javascript"></script>
		<script src="${link}/js/koh-master.min.js" type="text/javascript"></script>
      	<#--  <#if ((partOfCurrentURL != "/browse/bathroom") && (partOfCurrentURL != "/en/browse/bathroom")) && ((partOfCurrentURL != "/browse/kitchen") && (partOfCurrentURL != "/en/browse/kitchen"))>  -->
            <script src="${link}/js/c-koh-banner-carousel.min.js" type="text/javascript"></script>
        <#--  </#if>  -->
		<script src="${link}/js/c-koh-product-faceted-search.min.js" type="text/javascript"></script>
		<script src="${link}/js/c-koh-promo-grid.min.js" type="text/javascript"></script>  	      	
		<#-- <script src="${link}/js/custom/koh-include-consent-banner.min.js" type="text/javascript"></script>-->
		<@hst.headContributions categoryIncludes="ext-scripts"/>	
		
	<#if dtmFile?? && dtmFile?has_content>
		<script type="text/javascript">_satellite.pageBottom();</script>
	</#if>	
	
	<#if helpUsImproveLink?has_content>
		<script>
			/*Copyright 2011-2015 iPerceptions, Inc. All rights reserved. Do not distribute.iPerceptions provides this code 'as is' without warranty of any kind, either express or implied. */ 
			window.iperceptionskey = '${helpUsImproveKey}';(function () 
			{var a = document.createElement('script'),b = document.getElementsByTagName('body')[0]; a.type = 'text/javascript'; a.async = true;a.src = 'https://universal.iperceptions.com/wrapper.js';b.appendChild(a);} 
			)();

			$( "#koh-improve-site-link" ).on({
				click: function(e) {
					e.preventDefault();
					window.open($(this).attr("href"), "_blank", "toolbar=no,scrollbars=no,resizable=no,top=100,left=400,width=670,height=710");
				}
			});
		</script>
	</#if>
    <#if linkedin_partner_id?? && linkedin_partner_id?has_content>	
		<script type="text/javascript"> 
			_linkedin_partner_id = "${linkedin_partner_id}";
			window._linkedin_data_partner_ids = window._linkedin_data_partner_ids || [];
			window._linkedin_data_partner_ids.push(_linkedin_partner_id);
		</script>
		<script type="text/javascript"> 
			(function(){
				var s = document.getElementsByTagName("script")[0];
				var b = document.createElement("script");
				b.type = "text/javascript";
				b.async = true; 
				b.src = "https://snap.licdn.com/li.lms-analytics/insight.min.js";
				s.parentNode.insertBefore(b, s);
			})(); 
		</script> 
		<noscript> 
			<img height="1" width="1" style="display:none;" alt="" src="https://dc.ads.linkedin.com/collect/?pid=${linkedin_partner_id}&fmt=gif" /> 
		</noscript>
	  </#if>		
	</body>
</html>

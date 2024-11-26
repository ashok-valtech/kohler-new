<section class="c-koh-promo-collections">
	<#include "../include/imports.ftl">
	<@hst.setBundle basename="apac.product.urls,apac.labels"/>
    <@fmt.message key="company.name" var="companyName"/>
	<@fmt.message key="collection-promo-header-title" var="collectionPromoHeader"/>
	<@fmt.message key="collection-promo-header-description" var="collectionPromoDescription"/>
	<header>
		<h1 class="koh-promo-collections-title">${collectionPromoHeader}</h1>
		<h3>${collectionPromoDescription}</h3>
	</header>
	<@hst.include ref="container"/>

</section>

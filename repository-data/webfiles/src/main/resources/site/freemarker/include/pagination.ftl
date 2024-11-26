<#include "../include/imports.ftl">
<#if pageable??>
 <#if pageable.totalPages gt 1>
  <div class="koh-search-pagination">
    <ul>
      <#list pageable.pageNumbersArray as pageNr>
        <@hst.renderURL var="pageUrl">
          <@hst.param name="page" value="${pageNr}"/>
          <@hst.param name="pageSize" value="${pageable.pageSize}"/>
        </@hst.renderURL>
        <#if pageNr_index==0>
          <#if pageable.previous>
            <@hst.renderURL var="pageUrlPrevious">
              <@hst.param name="page" value="${pageable.previousPage}"/>
              <@hst.param name="pageSize" value="${pageable.pageSize}"/>
            </@hst.renderURL>
            <li><a href="${pageUrlPrevious}"><span class="icon" data-icon=""></span></a></li>
          <#else>
            <li><span class="icon" data-icon=""></span></li>
          </#if>
        </#if>
        <#if pageable.currentPage == pageNr>
          <li>${pageNr}</li>
          <#else >
            <li><a href="${pageUrl}">${pageNr}</a></li>
        </#if>
        <#if !pageNr_has_next>
          <#if pageable.next>
            <@hst.renderURL var="pageUrlNext">
              <@hst.param name="page" value="${pageable.nextPage}"/>
              <@hst.param name="pageSize" value="${pageable.pageSize}"/>
            </@hst.renderURL>
            <li><a href="${pageUrlNext}"><span class="icon" data-icon=""></span></a></li>
          <#else>
            <li><span class="icon" data-icon=""></span></li>
          </#if>
        </#if>
      </#list>
    </ul>
  </div>
 </#if> 
</#if>
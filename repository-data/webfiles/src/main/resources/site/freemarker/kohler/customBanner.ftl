<#include "../include/imports.ftl">

<#if document??>
  <#if document.videoPath?? && document.videoPath?has_content>
   <div class="koh-article-img-video embed-responsive embed-responsive-16by9"> 
    <iframe src="${document.videoPath}?rel=0&amp;showinfo=0" frameborder="0" allowfullscreen></iframe>      
   </div>  
<#else>
   <#if document.title?? && document.title?has_content>
        <#assign imageAlt=document.title /> 
   </#if>
   <#if document.imageAlt?? && document.imageAlt?has_content>
        <#assign imageAlt=document.imageAlt /> 
   </#if>
   <img src= "<#if document.imagePath?? && document.imagePath?has_content>${document.imagePath}</#if>"  height="232" alt=${imageAlt} <#if document.imageTitle?? && document.imageTitle?has_content> title="${document.imageTitle}"</#if> >
  </#if>
 
</#if>
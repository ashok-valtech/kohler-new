"use strict";
$( document ).ready(function() {
    populateData(null,null,'country');
    loadAllData();
});

function changeCountry(selectItem)
{
  $('.disabled').removeClass('disabled')
   populateData(selectItem,null,'state');
   populateData(selectItem,null,'city');
	 $('.city.select').addClass('disabled');
	  $('input#submit').prop('disabled', true);
}

function changeState(selectItem)
{
   $('.disabled').removeClass('disabled');
   var country = $('#country :selected').text();
   if(country=="")
   {
     country = "${country}";
   }
   populateData(country,selectItem,'city');
   $('input#submit').prop('disabled', true);
}
function changeCity(selectedItem)
{
    $('.disabled').removeClass('disabled')
	$('#submit').removeAttr('disabled');
	
}
function populateData(country,state,id)
{
  var url = 'kohler/kohlerservice/dealer';
  if(country!=null && state==null)
  {
     url +='/'+country; 
  }
  else if(country!=null && state!=null)
  {
     url +='/'+country+'/'+state+'';
  }
  $.ajax({url: url.replace(/\s+/g, ''),  
   		  async:false,		
          success: function(result){
            $("#"+id+"").html(result);      
    }});
}

function loadAllData()
{
	var country = $(".koh-defaultcountry").data("defaultcountry");
    var state = $(".koh-defaultstate").data("defaultstate");
    var city = $(".koh-defaultcity").data("defaultcity");
    if(country!="")
    {
      $('#country').val(country);
       changeCountry(country);
    }
    if(country!="" && state!="")
    {
    	$('#state').val(state);
    	changeState(state);
      
    }
    if(country!="" && state!="" && city!="")
    {
       $('#city').val(city);
    }
    
}

 
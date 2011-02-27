$(document).bind('mobileinit', function(){
  
  $('#home').live('pagebeforecreate',function(event){
    setTimeout( function(){
      $('#splash').fadeOut();
    }, 2000)
  });
  
});
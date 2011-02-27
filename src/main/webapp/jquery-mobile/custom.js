$(document).bind('mobileinit', function(){
  
  $('#home').live('pagebeforecreate',function(event){
    $('#splash').delay(1000).fadeOut(500);
  });
  
});
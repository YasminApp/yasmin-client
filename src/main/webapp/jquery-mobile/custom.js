$(document).bind('mobileinit', function(){
  
  $('#home').live('pagebeforecreate',function(event){
    $('#splash').delay(3000).fadeOut(500);
  });
  
});
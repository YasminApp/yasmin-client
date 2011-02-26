$(document).bind('mobileinit', function(){
  
  $('#home').live('pagebeforecreate',function(event){
    setTimeout( function(){
      $.mobile.changePage('#dashboard');
    }, 3000)
  });
  
});
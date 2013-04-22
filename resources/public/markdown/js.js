$(function(){

  $markdown_preview = $('#markdown-preview');

  $("#markdown-input").resizable({
    handles: "e",
    resize: function(event, ui) {
      $markdown_preview.width($('body').width() - ui.size.width - 10); //10 is padding value
    }
  });

  var editor = $('.markdown-input').markdown();

  console.log(editor);



  key('⌘+s, ctrl+s', function(event, handler){

    console.log(handler.shortcut, handler.scope);

    return false;
  });

});

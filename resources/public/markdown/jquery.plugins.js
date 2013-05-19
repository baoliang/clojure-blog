// This is a jQuery plugins file.

(function($){

  // Needs require this lib at the moment:
  // CodeMirror
  // marked
  // codemirror/extend.js
  jQuery.fn.extend({
    markdown: function(){
      return this.each( function(){

        if ( this.type !== 'textarea' ){
          return false;
        }

        var $textarea = jQuery(this);
        var $wrapper  = $textarea.parent();
        var $preview  = jQuery($textarea.attr('data-markdown-preview'));

        var editor = CodeMirror.fromTextArea(this, {
          mode:             'gfm',
          theme:            "default",
          styleActiveLine:  true,
          matchBrackets:    true,
          lineWrapping:     true,
          autofocus:        true,
          indentWithTabs:   true,
          tabSize:          2,
          showCursorWhenSelecting:  true,
          extraKeys: {
            "Cmd-B"   : function(cm) { cm.wrapSymbolTag("**") },
            "Ctrl-B"  : function(cm) { cm.wrapSymbolTag("**") },

            "Cmd-I"   : function(cm) { cm.wrapSymbolTag("*") },
            "Ctrl-I"  : function(cm) { cm.wrapSymbolTag("*") },

            "Cmd-U"   : function(cm) { cm.wrapSymbolTag("~~") },
            "Ctrl-U"  : function(cm) { cm.wrapSymbolTag("~~") },

            "Cmd-K"   : function(cm) { cm.wrapSymbolTag("`") },
            "Ctrl-K"  : function(cm) { cm.wrapSymbolTag("`") },

            "Cmd-1"   : function(cm) { cm.insertTitle(1) },
            "Ctrl-1"  : function(cm) { cm.insertTitle(1) },

            "Cmd-2"   : function(cm) { cm.insertTitle(2) },
            "Ctrl-2"  : function(cm) { cm.insertTitle(2) },

            "Cmd-3"   : function(cm) { cm.insertTitle(3) },
            "Ctrl-3"  : function(cm) { cm.insertTitle(3) },

            "Cmd-4"   : function(cm) { cm.insertTitle(4) },
            "Ctrl-4"  : function(cm) { cm.insertTitle(4) },

            "Cmd-5"   : function(cm) { cm.insertTitle(5) },
            "Ctrl-5"  : function(cm) { cm.insertTitle(5) },

            "Cmd-6"   : function(cm) { cm.insertTitle(6) },
            "Ctrl-6"  : function(cm) { cm.insertTitle(6) },

            "Cmd-S"   : function(cm) { cm.saveMarkdownContent() },
            "Ctrl-S"  : function(cm) { cm.saveMarkdownContent() }
          }
        });

        editor.on("change", function(doc, changeObj) {
          $preview.html(marked(doc.getValue()));
          $textarea.val(doc.getValue());
        });

        editor.on('scroll', function(instance){
          var scrollInfo = instance.getScrollInfo();
          $preview.scrollTop( scrollInfo.top / scrollInfo.height * $preview[0].scrollHeight );
        });

        $preview.html(marked(editor.getValue()));

      });

    }
  });

})(jQuery);

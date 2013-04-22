/**
 * Extension for CodeMirror.
 *
 * @author Miclle <miclle.zheng@gmail.com>
 * Contributed under the same license terms as CodeMirror.
 */
(function() {

  /**
   * insert '## text'
   */
  CodeMirror.defineExtension("insertTitle", function(num) {
    var pos = this.getCursor();
        pos.ch = 0;
    this.replaceRange("###### ".slice(6 - num), pos, pos);
  });

  /**
   * insert '*text*' '**text**'
   */
  CodeMirror.defineExtension("wrapSymbolTag", function(star) {
    var selectString = this.getSelection();
    this.replaceSelection(star + selectString + star);
    var cursor = this.getCursor(false);
    if(selectString == "") cursor.ch = cursor.ch - star.length;
    this.setCursor(cursor);
  });

  /**
   * save markdown content
   */
  CodeMirror.defineExtension("saveMarkdownContent", function() {
    function eventFire(el, etype){
      if (el.fireEvent) {
        (el.fireEvent('on' + etype));
      } else {
        var evObj = document.createEvent('Events');
        evObj.initEvent(etype, true, false);
        el.dispatchEvent(evObj);
      }
    }
    var link = document.createElement("a");
    link.download = "markdown.md";
    link.href = "data:application/stream;base64," + $.base64.encode(this.getValue());
    eventFire(link, "click");
  });

})();

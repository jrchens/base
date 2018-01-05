function tabBack() {
    var tab = $('#tabs').tabs('getSelected');
    // console.log('tab',tab);
    // console.log('tab.panel',tab.panel);
    // console.log('tab.panel.options',tab.panel('options'));
    var itemKey = 'tabs-'.concat(tab.panel('options').id);
    tab.panel('refresh', localStorage.getItem(itemKey));
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function initRichContent(selector,content){
    // var ele = selector || '.ext-tinymce-rich-content';
    // var opts = options || {};
    // var config = $.extend(,opts);
        tinymce.remove(selector);
        tinymce.init({
            selector: selector,
            menubar: false,
            statusbar: false,
            inline: false,
            language: 'zh_CN',
            plugins: [
              'link image'
            ],
            init_instance_callback: function(editor){
                // console.log(editor);
                if(content) {
                  editor.setContent(content);  
                } 
            }
        });
}

if ($.fn.datagrid){
    $.fn.datagrid.defaults.emptyMsg = '查询无数据集合';
    $.fn.datagrid.defaults.pageSize = 20;
}



/*
UUID.generate();

$('#PATH_SELECTOR').find(':input')
.filter(function () {
    return $.trim(this.value).length > 0
})
.serializeJSON();
*/
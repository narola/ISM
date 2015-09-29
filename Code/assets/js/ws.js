$(document).ready(function () {
    $('.chat .chat_header').click(function () {
        if ($(this).parent().hasClass('passive')) {
            if ($(this).parent().hasClass('chat_3')) {
                $('.chat.active').addClass('chat_3 passive');
                $('.chat.active').removeClass('active');
            }
            if ($(this).parent().hasClass('chat_2')) {
                $('.chat.active').addClass('chat_2 passive');
                $('.chat.active').removeClass('active');
            }
            if ($(this).parent().hasClass('chat_1')) {
                $('.chat.active').addClass('chat_1 passive');
                $('.chat.active').removeClass('active');
            }
            $(this).parent().removeClass();
            $(this).parent().addClass('active');
            $(this).parent().addClass('chat');
        }
    });

    var _URL = window.URL || window.webkitURL;
    $("#chat_upload").change(function (e) {
        if(this.files[0].size <= 1024*1024*10){
            
        }else{
            
        }
         alert(this.files[0].size);
        var file, img;
        if ((file = this.files[0])) {
            img = new Image();
            img.onload = function () {
                alert(this.width + " " + this.height);
            };
            img.src = _URL.createObjectURL(file);
        }
    });



});

if ("WebSocket" in window)
{
    var ws = new WebSocket("ws://192.168.1.124:9300");

    ws.onopen = function ()
    {
        ws.send('{"type":"con","from":"' + wp + '","to":"self","error":""}');
    };

    ws.onmessage = function (evt)
    {
        var obj = $.parseJSON(evt.data);
        if (obj.type == 'studymate') {
            if (ws == obj.from) {
                $('.chat[data-mate="' + obj.to + '"] .chat_text #mCSB_4 #mCSB_4_container').append("<div class='to'><p>" + obj.message + "</p></div>");
            } else {
                $('.chat[data-mate="' + obj.from + '"] .chat_text #mCSB_4 #mCSB_4_container').append("<div class='from'><p>" + obj.message + "</p></div>");
            }
        } else if (obj.type == 'con') {
            if (obj.error != '') {
                alert(obj.error);
            }
        } else if (obj.type == 'notification') {
            if (obj.status == 'available') {
                set_status(obj.user_id, obj.live_status);
                alert(obj.message);
            }
        } else {
            alert('Message Not Catched!!');
        }
    };
    ws.onclose = function ()
    {
        alert('Disconnected from Server!');
    };
}

$('input[data-type="chat"]').keypress(function (e) {
    if (e.keyCode == 13 && this.value) {
        var request = {
            type: 'studymate',
            from: wp,
            to: $(this).data('id'),
            error: '',
            message: this.value
        };
        ws.send(JSON.stringify(request));
        $(this).val('');
    }
});


function set_status(id,status){
    if(status == true){
        value = $.cookie('status');
        if(value == 'undefind' || value == '' || value == null){
            $.cookie('status',id);
        }
         var splitString = value.split(',');
         if(splitString.length>1){
             check  = $.inArray(id,splitString);
             var a = splitString.indexOf(id);
             if(a != -1){
                append = $.cookie('status')+','+id;
                $.cookie('status',append);
             } 
         }
         else{
            if(value != id){
                append = $.cookie('status')+','+id;
                $.cookie('status',append);
            }

        }
    }
    else if(status == false){
        value = $.cookie('status');
        var splitString = value.split(',');
         if(splitString.length>1){
            y = jQuery.grep(splitString, function(value) {
                  return value != id;
                }); 

                $.cookie('status',y);
             
         }
         else{
            if(value == id){
                $.cookie('status','');
            }

        }
    }
    alert($.cookie('status'))
}

function active_check(id,status){
    if(status == true){
        value = $.cookie('active_check');
        if(value == 'undefind' || value == '' || value == null){
            $.cookie('active_check',id);
        }
         var splitString = value.split(',');
         if(splitString.length>1){
             check  = $.inArray(id,splitString);
             var a = splitString.indexOf(id);
             if(a != -1){
                append = $.cookie('active_check')+','+id;
                $.cookie('active_check',append);
             } 
         }
         else{
            if(value != id){
                append = $.cookie('active_check')+','+id;
                $.cookie('active_check',append);
            }

        }
    }
    else if(status == false){
        value = $.cookie('active_check');
        var splitString = value.split(',');
         if(splitString.length>1){
            y = jQuery.grep(splitString, function(value) {
                  return value != id;
                }); 

                $.cookie('active_check',y);
             
         }
         else{
            if(value == id){
                $.cookie('active_check','');
            }

        }
    }
    alert($.cookie('active_check'))
}

$(document).ready(function () {
    $(document).on('click', '.chat .chat_header', function () {
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
        if (this.files[0].size <= 1024 * 1024 * 10) {

        } else {
            alert('');
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
            if (wp == obj.from) {
                $('.chat[data-id="' + obj.to + '"] .chat_text #mCSB_5 #mCSB_5_container').append("<div class='to'><p>" + obj.message + "</p></div>");
            } else {
                $('.chat[data-id="' + obj.from + '"] .chat_text #mCSB_5 #mCSB_5_container').append("<div class='from'><p>" + obj.message + "</p></div>");
            }
        } else if (obj.type == 'con') {
            if (obj.error != '') {
                alert(obj.error);
            }
        } else if (obj.type == 'notification') {
            if (obj.status == 'available') {
                set_status(obj.user_id, obj.live_status);
            }
        } else if (obj.type == 'online_users') {
            var theString = obj.message;
            $.each(theString.split("-"), function (index, id) {
                $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('offline').addClass('online');
            });
        }else if(obj.type == 'get_latest_message'){
            $('.chat[data-id="' + obj.my_id + '"] .chat_text #mCSB_5 #mCSB_5_container').html(obj.message);
        }else {
            alert('Message Not Catched!!');
        }
    };
    ws.onclose = function ()
    {
        alert('Disconnected from Server!');
    };
}
/* Send message for individual chat. */
$(document).on('keypress', 'input[data-type="chat"]', function (e) {
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

/* Check user is online or not */

function set_status(id, status) {
    var value = $.cookie('status');
    var regex = new RegExp("-" + id + "-", "g");
    if (value == 'undefind' || value == '' || value == null) {
        if (status == true) {
            $.cookie('status', "-" + id + "-");
        } else {
            $.cookie('status', "-");
        }
    }
    if (value.indexOf("-" + id + "-") > -1) {
        if (status == false) {
            value = value.replace(regex, '-');
            $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('online').addClass('offline');
        }
    } else {
        if (status == true) {
            value = value + id + "-";
            $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('offline').addClass('online');
        }
    }
    $.cookie('status', value);
}

function active_check(id) {
    $.cookie('active', id);
}

$(document).on('click', '#mate_list', function () {
    active_check($(this).attr('data-id'));
    var cookie = $.cookie('active');
    var str = '';
    var id = $(this).data('id');
    var len = $('.chat_container .chat').length;
    var j = 3;
    var is_needed = true;

    for (var i = 1; i <= len; i++) {
            if ($(".chat_container .chat:nth-child(" + i + ")").data('id') == id) {
                is_needed = false;
            }
    }
    if(len >= 4 && is_needed == true){
        alert('removed');
            $(".chat_container .chat_1").remove();
    }

    for (var i = 1; i <= len; i++) {            
            if ($(".chat_container .chat:nth-child(" + i + ")").data('id') != id && j>0) {
                $(".chat_container .chat:nth-child(" + i + ")").attr('class', 'chat passive chat_' + j);
            j--;
            }
            
    }

    if (is_needed == true) {

        var request = {
            type: 'get_latest_message',
            to: 'self',
            my_id: id,
            error: ''
        };
        ws.send(JSON.stringify(request));

        str += '<div class="chat active" data-id="' + id + '">';
        str += '<div class="chat_header"><div class="chat_img_holder">';
        str += '<img src="' + $(this).children('div').children('img').attr('src') + '">';
        str += '</div><p class="chat_name">' + $(this).children('p').html() + '</p>';
        str += '<a href="#"><span class="icon icon_option"></span></a></div>';
        str += '<div class="chat_text"></div>';
        str += '<input type="text" class="chat_input" placeholder="Say It" data-type="chat" data-id="' + id + '">';
        str += '<a href="#" class="icon icon_emoji"></a>';
        str += '<a href="#" class="icon icon_pin"></a>';
        str += '<input type="file" id="chat_upload" class="chat_pin" data-type="chat" data-id="16">';
        str += '</div>';
        $('#chat_container').append(str);
          $("#chat_container .chat[data-id='"+id+"'] .chat_text").mCustomScrollbar({theme:"minimal-dark"});
    } else {
        $(".chat_container .chat[data-id='" + id + "']").attr('class', 'chat active');
    }

});


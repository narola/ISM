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
            }
        } else if (obj.type == 'online_users') {
            var theString = obj.message;
            $.each(theString.split("-"), function (index, id) {
                $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('offline').addClass('online');
            });
        } else {
            alert('Message Not Catched!!');
        }
    };
    ws.onclose = function ()
    {
        alert('Disconnected from Server!');
    };
}
/* Send message for individual chat. */
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

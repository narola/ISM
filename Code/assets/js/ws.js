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
                    $('#chat_container .chat[data-id="' + obj.to + '"] .chat_text .mCustomScrollBox .mCSB_container').append("<div class='to'><p>" + obj.message + "</p></div>");
                } else {
                    $('#chat_container .chat[data-id="' + obj.from + '"] .chat_text .mCustomScrollBox .mCSB_container').append("<div class='from'><p>" + obj.message + "</p></div>");
                }
            if( $('#chat_container .chat.active').data('id') != obj.from && wp != obj.from){
                
                var ac = $('.stm .stm_list .mCustomScrollBox .mCSB_container .stm_item[data-id="'+obj.from+'"]');
                $('.stm_list .mCustomScrollBox .mCSB_container').prepend('<div class="'+ac.attr('class')+'" data-id="'+obj.from+'">'+ac.remove().html()+'</div>');
            
                var c =  $('.stm .stm_list .mCustomScrollBox .mCSB_container .stm_item[data-id="'+obj.from+'"] a span.badge');
                var count = c.text();
                if(count == '' || count == 0 || count == ''){
                    count = 0;
                }
                c.html(++count);              
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
        }else if(obj.type == 'post'){
            generate_post(obj);
        }else if(obj.type == 'feed_comment'){
            generate_comment(obj);
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
    var ac = $('.stm .stm_list .mCustomScrollBox .mCSB_container .stm_item[data-id="'+id+'"]');
    if (value.indexOf("-" + id + "-") > -1) {
        if (status == false) {
            value = value.replace(regex, '-');
            $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('online').addClass('offline');
           $('.stm_list .mCustomScrollBox .mCSB_container').append('<div class="'+ac.attr('class')+'" data-id="'+id+'">'+ac.remove().html()+'</div>');     
            
        }
    } else {
        if (status == true) {
            value = value + id + "-";
            $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('offline').addClass('online');
            $('.stm_list .mCustomScrollBox .mCSB_container').prepend('<div class="'+ac.attr('class')+'" data-id="'+id+'">'+ac.remove().html()+'</div>');
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
    $(this).children('span').html('');

});


$(document).on('click','button[data-type="post"]',function(){
        var request = {
            type:'post',
            to: 'all',
            message: $('#feed_post').val(),
            error: ''
        };
        ws.send(JSON.stringify(request));
        $('#feed_post').val('');
});
$(document).on('keypress','#all_feed .box.feeds .write_comment input[data-type="feed_comment"]',function(e){
     
     if (e.keyCode == 13 && this.value) {

        var request = {
            type: 'feed_comment',
            to: $(this).data('id'),
            message: $(this).val(),
            error: ''
        };
        ws.send(JSON.stringify(request));
        $(this).val('');
     }
});


function generate_post(obj){
    str = '<div class="box feeds" data-id="'+obj.post_id+'">';
    str += '<div class="user_small_img">';
    str += '<img src="uploads/'+obj.profile_link+'">';
    str += '</div>';
    str += '<div class="feed_text">';
    str += '<h4>'+obj.full_name+'</h4>';
    str += '<span class="date">Sep 28, 2015</span>';
    str += '<div class="clearfix"></div>';
    str += '<pre>'+obj.message+'</pre>';
    str += '<a href="#" class="like_btn"><span class="icon icon_thumb_0"></span>0</a>';
    str += '<a href="#" class="comment_btn"><span class="icon icon_comment"></span>0</a>';
    str += '<div class="dropdown tag_user" style="display: inline-block;">';
    str += '<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><span class="icon icon_user_2"></span><span class="caret"></span></a>';
    str += '<ul class="dropdown-menu">';
    str += '<li><a href="#">Emma Mall</a></li>';
    str += '<li><a href="#">Gill Christ</a></li>';
    str += '<li><a href="#">Adam Stranger</a></li>';
    str += '</ul>';
    str += '</div>';
    str += '</div>';
    str += '<div class="clearfix"></div>';
    str += '<div id="feed_comments"></div>';
    str += '<div class="write_comment box_body">';
    str += '<input type="text" class="form-control" placeholder="Write Your Comment Here" data-type="feed_comment" data-id="'+obj.post_id+'">';                 
    str += '<a class="icon icon_image"></a>';
    str += '<input type="file">';
    str += '</div>';
    str += '</div>';
    $("#all_feed").prepend(str).fadeOut(0).fadeIn(400);
}

function generate_comment(obj){
    str = "";
    str += '<div class="comment" style="display:block">';
    str += '<div class="user_small_img user_comment">';
    str += '<img src="uploads/'+obj.profile_link+'">';
    str += '</div>';
    str += '<div class="notification_txt">';
    str += '<p><a href="#" class="noti_username">'+obj.full_name+'</a>&nbsp;&nbsp;'+obj.message+'</p>';
    str += '<span class="noti_time">1 Day</span> ';                          
    str += '</div>';
    str += '<div class="clearfix"></div>';
    str += '</div>';
    $('#all_feed .box.feeds[data-id="'+obj.to+'"] #feed_comments').prepend(str).fadeOut(0).fadeIn(400);
}

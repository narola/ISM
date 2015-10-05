$(document).ready(function () {

    /* Handle multiple chat window. */
    $(document).on('click', '.chat .chat_header', function () {
        if ($(this).parent().hasClass('passive')) {
            if ($(this).parent().hasClass('chat_3')) {
                $('.chat.active').removeClass('active').addClass('chat_3 passive');
            }
            if ($(this).parent().hasClass('chat_2')) {
                $('.chat.active').removeClass('active').addClass('chat_2 passive');
            }
            if ($(this).parent().hasClass('chat_1')) {
                $('.chat.active').removeClass('active').addClass('chat_1 passive');
            }
            $(this).parent().removeClass().addClass('active').addClass('chat');
        }
    });

/* Validate length of selected file. */
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
/* Check wheather web socket is supported by browser. */
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
                if(count == '' || count == 0 || count == '' || count == 'undefined'){
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
           // if(obj.id != wp){
                $('.alert_notification p').html("New feed from <b>"+obj.full_name+"</b>").show();
           // }
            generate_post(obj,true);
        }else if(obj.type == 'feed_comment'){
            generate_comment(obj);
        }else if(obj.type == 'load_more_feed'){
            $.each(obj.feed, function(index,jsonObject){
                generate_post(jsonObject,false);
            });
            $('button[data-type="load_more"]').attr('data-start',obj.start);
            $('button[data-type="load_more"]').prop('disabled', false);
        }else if(obj.type == 'discussion'){
            generate_cm(obj);
        }else if(obj.type == 'like'){
            if(wp == obj.id){
                if(obj.message == 'like'){
                    $('.like_btn[data-id="' + obj.fid + '"]').html('<span class="icon icon_thumb"></span>'+obj.like_cnt);
                }
                else{
                    $('.like_btn[data-id="' + obj.fid + '"]').html('<span class="icon icon_thumb_0"></span>'+obj.like_cnt);
                }
            }
            else{
                $('.like_btn[data-id="' + obj.fid + '"] span:nth-of-type(2)').html(obj.like_cnt);
            }   
        }else if(obj.type == "discussion-type"){
            $('.box_footer[data-id="'+obj.type_id+'"]').html(obj.message);
            setTimeout(function(){  $('.box_footer[data-id="'+obj.type_id+'"]').html('Online'); }, 2000);

        }else if(obj.type == "close_studymate"){
           $('#close_mate').modal('toggle');
            if(obj.error == ''){
                $('.studyamte_list .mCustomScrollBox  .mCSB_container  .study_mate[data-id="'+obj.studymate_id+'"]').fadeOut(300);
            }else{
                alert(" => " + obj.error);
            }

        }else if(obj.type == "dictionary"){
            $('#Serach_Result').html(obj.message);
            $('input[data-type="search-dictionary"]').removeAttr('disabled');

        }
        else if(obj.type == "send_studymate_request"){

            // $('#Serach_Result').html(obj.message);
            alert('hi');
            $('.suggested_mates_card .mate_descrip button[data-id="'+obj.studymate_id+'"]').removeClass('btn_green').attr('disabled',true).addClass('btn_black_normal').html('Request Already Sent');

        }
        else {
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
            if(wp != id)
            $('.stm_list .mCustomScrollBox .mCSB_container').append('<div class="'+ac.attr('class')+'" data-id="'+id+'">'+ac.remove().html()+'</div>');     
            
        }
    } else {
        if (status == true) {
            value = value + id + "-";
            $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('offline').addClass('online');
            if(wp != id)
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
        $("#chat_container .chat[data-id='"+id+"'] .chat_text")
        .mCustomScrollbar({
            theme:"minimal-dark"
        });
    } else {
        $(".chat_container .chat[data-id='" + id + "']").attr('class', 'chat active');
    }
    $(this).children('span').html('');

});

/* Send Feed Post */
$(document).on('click','button[data-type="post"]',function(){
       if($.trim($('#feed_post').val()) != ''){
        var request = {
            type:'post',
            to: 'all',
            message: $('#feed_post').val(),
            error: ''
        };
        ws.send(JSON.stringify(request));
        $('#feed_post').val('');
    }
});

/* Send comment */
$(document).on('keypress','#all_feed .box.feeds .write_comment input[data-type="feed_comment"]',function(e){
     if (e.keyCode == 13 && $.trim($(this).val()) != '') {
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

/* Generate HTML clock of Feed Post. */
function generate_post(obj,status){
    var cls = '';
    if(obj.my_like != 0){
        cls = '_0';
    }
    str = '<div class="box feeds" data-id="'+obj.post_id+'">';
    str += '<div class="user_small_img">';
    str += '<img src="uploads/'+obj.profile_link+'">';
    str += '</div>';
    str += '<div class="feed_text">';
    str += '<h4>'+obj.full_name+'</h4>';
    str += '<span class="date">Sep 28, 2015</span>';
    str += '<div class="clearfix"></div>';
    str += '<pre>'+obj.message+'</pre>';
    str += '<a href="javascript:void(0);" class="like_btn" data-type="feed-like" data-id="'+obj.post_id+'"><span class="icon icon_thumb'+cls+'"></span>'+obj.tot_like+'</a>';
    str += '<a href="#" class="comment_btn"><span class="icon icon_comment"></span>'+obj.tot_comment+'</a>';
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
    if(status == true){
        $("#all_feed").prepend(str);
    }else{
        $("#all_feed").append(str);
    }
     $("#all_feed .box.feeds[data-id='"+obj.post_id+"']").fadeOut(0).fadeIn(400);
}

/* Generate HTML block of feed comment. */
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
    $('#all_feed .box.feeds[data-id="'+obj.to+'"] #feed_comments').prepend(str);
    $('#all_feed .box.feeds[data-id="'+obj.to+'"] #feed_comments .comment:nth-child(1)').fadeOut(0).fadeIn(400);
}

/* load more feeds. */
$(document).on('click','button[data-type="load_more"]',function(){
     $('button[data-type="load_more"]').prop('disabled', true);
     var request = {
            type: 'load_more_feed',
            to: 'self',
            start: $(this).attr('data-start'),
            message: '',
            error: ''
        };
       ws.send(JSON.stringify(request));
})

/* Submit group discussion comment. */
$(document).on('click','.option_bar[data-type="discussion-submit"]',function(){
    if($.trim($('textarea[data-type="discussion"]').val()) != ''){
    var request = {
            type: 'discussion',
            to: 'all',
            message:$('textarea[data-type="discussion"]').val(),
            error: ''
        };
        ws.send(JSON.stringify(request));
    }
});

/* Send tying event. */
$(document).on('keypress','textarea[data-type="discussion"]',function(){
    if($(this).val().length%2 == 0){
    var request = {
            type: 'discussion-type',
            to: 'all',
            message:'Typing..',
            error: ''
        };
        ws.send(JSON.stringify(request));
    }
});

/* Generate html block of group disscussion  comment */
function generate_cm(obj){
    var cl_me = "";
    if(wp == obj.id)
        cl_me = "me";
    str = "";
    str += '<div class="col-sm-12 '+cl_me+'" data-id="'+obj.disscusion_id+'">';
    str += '<div class="mate_user_img">';
    str += '<img src="/uploads/'+obj.profile_link+'">';
    str += '</div>';
    str += '<div class="admin_question">';
    str += '<h4>'+obj.full_name+'<span>Sep 07 2015 2:32 pm</span></h4>';
    str += '<p>'+obj.message+'</p>';
    str += '</div>';
    str += '</div>';
    
    if(obj.active_count != 'skip'){
        $('#active_comment_count').html(obj.active_count);
    }

    if(obj.group_score != 'skip'){
        $('#group_score_count').html(obj.group_score);
    }

    if(obj.my_score != 'skip'){
        $('#my_score_count').html(obj.my_score);
    }

    $('textarea[data-type="discussion"]').val('');
    $('.row.discussion').append(str);
    $('.row.discussion div[data-id="'+obj.disscusion_id+'"]').fadeOut(0).fadeIn(400);
}

/* Feed like dislike  */
$(document).on('click','a[data-type="feed-like"]',function(e){   
    var request = {
        type: 'like',
        fid: $(this).data('id'),
        to:'',
        message: '',
        error: ''
    };
    ws.send(JSON.stringify(request));
    $(this).val('');

});

/* Weekday scroll in tutorial group. */
$(document).on('click','.tut_weekdays li a', function(e) {
    var nav = $(this).attr('href');
     e.preventDefault();
if (nav.length) {
        $('html,body').animate({
            scrollTop: $(nav).offset().top - 240},
            500);
    }
    return false;
});


$(document).on('change', '#action_studymate', function(){
    val = $(this).val();
    if(val == 1){
        $('button[data-type="close-studymate"]').attr('data-id',$(this).data('id'));   
        $('#close_mate').modal('show');
           
    }
});

$(document).on('click','button[data-type="close-studymate"]',function(e){

    var request = {
        type: 'close_studymate',
        to: 'self',
        studymate_id: $(this).attr('data-id'),
        error : ''
    };
    ws.send(JSON.stringify(request));
});

/* Send Request to search from dictionary... */
$(document).on('keypress','input[data-type="search-dictionary"]', function(e) {
  
    if (e.keyCode == 13 && this.value) {
    var request = {
            type: 'dictionary',
            to:'self',
            keyword: this.value,
            error: ''
        };
    ws.send(JSON.stringify(request));
    $('#Serach_Result').html('<img class="pre_loader" src="assets/images/loader1.GIF">').fadeIn(300);
    $(this).attr('disabled','');
    }

});

$(document).on('click','button[data-type="studyment-request"]',function(e){
   var request = {
        type: 'send_studymate_request',
        to: 'self',
        studymate_id: $(this).attr('data-id'),
        error : ''
    };
    ws.send(JSON.stringify(request)); 
});
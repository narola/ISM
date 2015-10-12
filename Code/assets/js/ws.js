/* Give notification for remainning active hours time. */

var remainning_time = 0;
var remainning_counter;
function remainning_time_timer()
{
  remainning_time=remainning_time-1;
  console.log("Remainnig Seconds to complete: "+remainning_time);
  if(remainning_time == 900){
      $(".alert_notification p").html("Active hours will <b>finish</b> within <b>15 minutes.</b>");
      $(".alert_notification").show().delay(5000).fadeOut();
  }else if(remainning_time == 300){
    console.log("5 minutes notification.");
      $(".alert_notification p").html("Active hours will <b>finish</b> within <b>5 minutes.</b>");
      $(".alert_notification").show().delay(5000).fadeOut();
  }else if(remainning_time == 60){
    console.log("1 minutes notification.");
     $(".alert_notification p").html("Active hours will <b>finish</b> within <b>1 minute.</b>");
     $(".alert_notification").show().delay(5000).fadeOut();
  }else if (remainning_time <= 0)
  { 
    $(".alert_notification p").html("Active hours <b>finished.</b>");
    $(".alert_notification").show().delay(5000).fadeOut();
      
    clearInterval(remainning_counter);
    return;
  }
}

var time_count_out = 0;
var max_count_out = 0;
var counter_out;

function timeout_timer()
{
  time_count_out=time_count_out-1;
  console.log("Remainnig Seconds :"+time_count_out);
  if(time_count_out == 900){
    console.log("15 minutes notification.");
      $(".alert_notification p").html("Active hours will start within <b>15 minutes.</b>");
      $(".alert_notification").show().delay(5000).fadeOut();
  }else if(time_count_out == 300){
    console.log("5 minutes notification.");
      $(".alert_notification p").html("Active hours will start within <b>5 minutes.</b>");
      $(".alert_notification").show().delay(5000).fadeOut();
  }else if(time_count_out == 60){
    console.log("1 minutes notification.");
     $(".alert_notification p").html("Active hours will start within <b>1 minute.</b>");
     $(".alert_notification").show().delay(5000).fadeOut();
  }else if (time_count_out <= 0)
  { 
    if(start_timer == true){
        console.log("Timer Started...");
        ws.send('{"type":"time_request","from":"' + wp + '","to":"self","error":""}');
    }else{
        $(".alert_notification p").html("Active hours started.");
        $(".alert_notification").show().delay(5000).fadeOut();
    }

    clearInterval(counter_out);
    return;
  }
}

$(document).ready(function () {

     $('#circle_process').circleProgress({
        value: 0.00,
        size: 156,
        fill: {
            color: '#1bc4a3'
        },
        emptyFill : '#000',
        thickness : 15,
        animation:{ duration: 500 } 
    });

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
            var handleFileSelect = function(evt) {
                var files = evt.target.files;
                var file = files[0];
                var user = $(this).data('id');
                var type = $(this).data('type');
                var type_of_data =  this.files[0].type;
                var file_name =  this.files[0].name;

                if(user != 'feed' && user != 'topic'){
                    $('.chat[data-id="'+user+'"] .chat_loading').fadeIn(300);
                }

                if (this.files[0].size <= 1024 * 1024 * 10) {
                if (files && file) {
                    var reader = new FileReader();
                    reader.onload = function(readerEvt) {
                        var binaryString = readerEvt.target.result;
                        console.log(btoa(binaryString));
                        var request = {
                            type : type,
                            name : file_name,
                            data_type : type_of_data,
                            data : btoa(binaryString),
                            to   : user
                        }
                        ws.send(JSON.stringify(request));

                    };
                        reader.readAsBinaryString(file);
                    } 
                }else {
                    alert('Max file upload limit 10MB!');
                } 
            };

            if (window.File && window.FileReader && window.FileList && window.Blob) {
                if($('#chat_file_share').length > 0){
                    document.getElementById('chat_file_share').addEventListener('change', handleFileSelect, false);
                }
                if($('#feed_file_share').length > 0){
                    document.getElementById('feed_file_share').addEventListener('change', handleFileSelect, false);
                }
                if($('#group_file_share').length > 0){
                     document.getElementById('group_file_share').addEventListener('change', handleFileSelect, false);
                }
               
            } else {
                alert('The File APIs are not fully supported in this browser.');
            }    
              
    });


/* Check wheather web socket is supported by browser. */
if ("WebSocket" in window)
{
    var ws = new WebSocket("ws://192.168.1.124:9300");

    ws.onopen = function ()
    {
        ws.send('{"type":"con","from":"' + wp + '","to":"self"}');
    };

    ws.onmessage = function (evt)
    {       
        var obj = $.parseJSON(evt.data);
        if(obj.error != 'skip'){
            $(".alert_notification p").html(obj.error);
            $(".alert_notification").show().delay(3000).fadeOut();
        }

        if(obj.time_to_left == 0 && obj.time_to_start > 0){
            time_count_out = obj.time_to_start;
            max_count_out = obj.time_to_start;
            counter_out=setInterval(timeout_timer, 1000);
        }


        if (obj.type == 'studymate') {
                if (wp == obj.from) {
                    $('#chat_container .chat[data-id="' + obj.to + '"] .chat_text .mCustomScrollBox .mCSB_container').prepend("<div class='to'><p>" + obj.message + "</p></div>");
                     $('.chat[data-id="'+obj.to+'"] .chat_loading').fadeOut(300);
                } else {
                    $('#chat_container .chat[data-id="' + obj.from + '"] .chat_text .mCustomScrollBox .mCSB_container').prepend("<div class='from'><p>" + obj.message + "</p></div>");
                }
            if( $('#chat_container .chat.active').data('id') != obj.from && wp != obj.from){
                var request = {
                    type: 'set_unread',
                    to:'none',
                    insert_id:obj.insert_id
                };
             ws.send(JSON.stringify(request));
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
            var theString = obj.online_user;
            $.each(theString.split("-"), function (index, id) {
                $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('offline').addClass('online');
                    if(start_timer == true){
                        $('.tut_group .box_footer[data-id="'+id+'"] p').html('Online');
                        $('.tut_group .box_body[data-id="'+id+'"] a').removeClass('icon_call_user').addClass('icon_call_user_disable');
                    }
                
            });

            if(obj.time_to_left > 0){
                remainning_time  = obj.time_to_left;
                remainning_counter = setInterval(remainning_time_timer, 1000);
            }
        } else if (obj.type == 'notification') {
                set_status(obj.user_id, obj.live_status);
        } else if(obj.type == 'get_latest_message'){
            $('.chat[data-id="' + obj.my_id + '"] .chat_text .mCustomScrollBox .mCSB_container').html(obj.message);
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
            if(obj.time_to_left > 0){
                clearInterval(counter);
                time_count = obj.time_to_left;
                counter = setInterval(timer, 1000);
            }
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
            }

        }else if(obj.type == "dictionary"){
            $('.dictionary_result .mCustomScrollBox .mCSB_container').html(obj.message);
            $('input[data-type="search-dictionary"]').removeAttr('disabled');

        }
        else if(obj.type == "send_studymate_request"){
            $('.mCSB_container .three_tabs .badge').html(obj.count);
            $('.suggested_mates_card .mate_descrip button[data-id="'+obj.studymate_id+'"]').removeClass('btn_green').attr('disabled',true).addClass('btn_black_normal').html('Request Already Sent');
        }else if(obj.type == 'time_request'){
                clearInterval(counter);
                time_count = obj.total_active_time;
                max_count = obj.total_active_time;
                counter = setInterval(timer, 1000);
        }else if(obj.type == 'time_start_request'){

             time_count_out = obj.total_deactive_time;
             max_count_out = obj.total_deactive_time;
             counter_out = setInterval(timeout_timer, 1000);

        }else if(obj.type == "view-all-comment-activities"){
            str = '';
              
            $.each(obj.comment, function (index, comment) {
                str += '<div class="user_small_img user_comment">';
                str += '<img src="uploads/'+obj.profile+'" onerror="this.src=\'assets/images/avatar.png\'">';
                str += '</div><div class="notification_txt">';
                str += '<p><a href="#" class="noti_username">'+obj.name+'</a> '+comment+'</p>';
                str += '<span class="noti_time">1 Day</span>';
                str += '</div>';
                str += '<div class="clearfix"></div>';
                
            });
            
            $('.commented_on .feeds .comment[data-id="'+obj.comment_id+'"]').html(str);
        }else if(obj.type == "decline-request"){
            
            if(obj.sub_type == 'accept-request'){
                if(obj.is_online == true)
                    status = 'online';
                else
                    status = 'offline';
                str = '';
                str += '<div class="stm_item '+status+'" data-id="'+obj.studymate_id+'" onerror="this.src=\'assets/images/avatar.png\'">';
                str += '<a href="javascript:void(0);" id="mate-list" data-id="'+obj.studymate_id+'">';
                str += '<div class="stm_user_img"><img src="uploads/'+obj.profile+'"></div>';
                str += '<p>'+obj.full_name+'</p></a>';
                str += '<div class="clearfix"></div></div>'
                $('.stm_list #mCSB_5 #mCSB_5_container').append(str);
            }
            if(obj.sub_type == 'decline-request'){
               // cnt = $('.box_body #carousel-studymate .carousel-inner #active-recomonded .suggested_mates_card').length;
               str = '';
                str += '<div class="suggested_mates_card">'
                str += '<div class="mate_user_img"><img src="uploads/'+obj.profile+'" onerror="this.src=\'assets/images/avatar.png\'"></div>';
                str += '<div class="mate_descrip"><p class="mate_name">'+obj.full_name+'</p>';
                str += '<p class="mate_following">Folowing 34 Authers</p>';
                str += '<p>'+obj.school_name+'</p>';
                str += '<p>'+obj.course_name+'</p><button class="btn btn_green" data-id="'+obj.studymate_id+'" data-type="studyment-request">Add Studymates</button></div></div>';
                $('.box_body #carousel-studymate .carousel-inner #active-recomonded').append(str);
            }  
            $('.box_body div[data-id="'+obj.studymate_id+'"]').remove().html();
            cnt = $('.box_body #my_request').length;
            if(cnt == 0)
            {
                $('#my_request_box').html('<div class="study_mate"><h3>No more studymate request</h3></div>');
            }    
        }
        else if(obj.type == 'get_studymate_name'){
            var i = 0;
            var j = 0;
            var k = 0;
            str = '';
            ids = '';
            len = obj.student_detail.length;
            $.each(obj.student_detail, function (index, list) {
                if(len == 1){
                    str += '<b>with</b> : <label class="label label_name"><a href="#">'+ list.name + '</a></label>';
                }
                else if(len == 2){
                    if(i == 0)
                        str += 'with <label class="label label_name"><a href="#">'+list.name +'</a></label>';
                    else
                        str += 'and <label class="label label_name"><a href="#">'+list.name +'</a></label>';
                    i++;
                }
                else if(len > 2){
                    if(j == 0)
                        str += 'with <label class="label label_name"><a href="#" >'+list.name +'</a></label>';
                    else{
                        l = parseInt(len) - parseInt(1);
                        if(k == 0){
                            str += 'and <label class="label label_name"><a>'+ l +' more</a>';
                            str += '</label>';
                            k++;
                        }
                    }
                    j++;
                }
                ids += list.id;    

            });
            $('#tagged-users').html(str);
            $('#tagged-users-id').val(ids);
        }
        else if(obj.type == 'file_notification'){
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
            message: this.value
        };
         $('.chat[data-id="'+wp+'"] .chat_loading').fadeIn(100);
        ws.send(JSON.stringify(request));
        $(this).val('');
    }
});

/* Check user is online or not */

function set_status(id, status) {
   
    var ac = $('.stm .stm_list .mCustomScrollBox .mCSB_container .stm_item[data-id="'+id+'"]');
        if (status == false) {
            
            $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('online').addClass('offline');
            if(wp != id){
                $('.stm_list .mCustomScrollBox .mCSB_container').append('<div class="'+ac.attr('class')+'" data-id="'+id+'">'+ac.remove().html()+'</div>');
                if(start_timer == true){
                    $('.tut_group .box_footer[data-id="'+id+'"] p').html('Offline');
                    $('.tut_group .box_body[data-id="'+id+'"] a').removeClass('icon_call_user_disable').addClass('icon_call_user');
                }
            }
        }else {
            $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('offline').addClass('online');
            if(wp != id){
            $('.stm_list .mCustomScrollBox .mCSB_container').prepend('<div class="'+ac.attr('class')+'" data-id="'+id+'">'+ac.remove().html()+'</div>');
           if(start_timer == true){
                    $('.tut_group .box_footer[data-id="'+id+'"] p').html('Online');
                    $('.tut_group .box_body[data-id="'+id+'"] a').removeClass('icon_call_user').addClass('icon_call_user_disable');
                }
            }
        }
}


$(document).on('click', '#mate_list', function () {
    $.cookie('active', $(this).attr('data-id'));
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
        str += '<div class="chat active" data-id="' + id + '">';
        str += '<div class="chat_header"><div class="chat_img_holder">';
        str += '<img src="' + $(this).children('div').children('img').attr('src') + '">';
        str += '</div><p class="chat_name">' + $(this).children('p').html() + '</p>';
        str += '<a href="#"><span class="icon icon_option"></span></a></div>';
        str += '<div class="chat_text"></div>';
        str += ' <img class="chat_loading" src="assets/images/progress_bar_sm.gif" style="display:none">';
        str += '<input type="text" class="chat_input" placeholder="Say It" data-type="chat" data-id="' + id + '">';
        str += '<a href="#" class="icon icon_emoji"></a>';
        str += '<a href="#" class="icon icon_pin"></a>';
        str += '<input type="file" id="chat_file_share" class="chat_pin" data-type="single_chat_file" data-id="16">';
        str += '</div>';
        $('#chat_container').append(str);
        $("#chat_container .chat[data-id='"+id+"'] .chat_text")
        .mCustomScrollbar({
            theme:"minimal-dark"
        }).delay(300);
         var request = {
            type: 'get_latest_message',
            to: 'self',
            my_id: id
        };

        ws.send(JSON.stringify(request));
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
            tagged_id : $('#tagged-users-id').val(),
            message: $('#feed_post').val()
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
            message: $(this).val()
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
    str += '<img onerror="this.src=\'assets/images/avatar.png\'" src="uploads/'+obj.profile_link+'">';
    str += '</div>';
    str += '<div class="feed_text">';
    str += '<h4>'+obj.full_name+'</h4>';

    len = obj.tagged_detail.length;
    if(len > 0){
        name = '';
        j = 0;
        $.each(obj.tagged_detail, function (index, list) {
                if(j == 0){
                    name += '&nbsp; with <label class="label label_name">'+list.full_name+'</label>';
                }
                else{
                    name += 'and <label class="label label_name">'+list.full_name+'<label>';
                }
            j++;
        });
    }
    str += '<span>'+name+'</span>';
    str += '<span class="date">Sep 28, 2015</span>';
    str += '<div class="clearfix"></div>';
    str += '<pre>'+obj.message+'</pre>';
    str += '<a href="javascript:void(0);" class="like_btn" data-type="feed-like" data-id="'+obj.post_id+'"><span class="icon icon_thumb'+cls+'"></span>'+obj.tot_like+'</a>';
    str += '<a href="javascript:void(0);" class="comment_btn"><span class="icon icon_comment"></span>'+obj.tot_comment+'</a>';
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
            message: ''
        };
       ws.send(JSON.stringify(request));
})

/* Submit group discussion comment. */
$(document).on('click','.option_bar[data-type="discussion-submit"]',function(){
    if($.trim($('textarea[data-type="discussion"]').val()) != ''){
    var request = {
            type: 'discussion',
            to: 'all',
            message:$('textarea[data-type="discussion"]').val()
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
            message:'Typing..'
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
        studymate_id: $(this).attr('data-id')
    };
    ws.send(JSON.stringify(request));
});

/* Send Request to search from dictionary... */
$(document).on('keypress','input[data-type="search-dictionary"], a[data-type="search-dictionary"]', function(e) {
  
    if (e.keyCode == 13 && this.value) {
    var request = {
            type: 'dictionary',
            to:'self',
            keyword: this.value
        };
    ws.send(JSON.stringify(request));
    $('.dictionary_result .mCustomScrollBox .mCSB_container').html('<img class="pre_loader" src="assets/images/loader1.GIF">').fadeIn(300);
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

$(document).on('click','a[data-type="view-all-comment-activities"]',function(e){
    var request = {
        type: 'view-all-comment-activities',
        to: 'self',
        comment_id: $(this).attr('data-id'),
        error : ''
    };
    ws.send(JSON.stringify(request)); 
});

$(document).on('click','button[data-type = "decline-request"]',function(e){
    var request = {
        type: 'decline-request',
        sub_type : $(this).data('subtype'),
        to: 'self',
        studymate_id: $(this).data('id'),
        error : ''
    };
    ws.send(JSON.stringify(request)); 
});

$(document).on('change', '#select-tag-user', function(e){
    var request = {
        type: 'get_studymate_name',
        to: 'self',
        studymate_id: e.val,
        error : ''
    };
    ws.send(JSON.stringify(request)); 
});

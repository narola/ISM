/* Exam time information and notification. */
var exam_time_to_start;
var exam_total_active_time;
var exam_total_deactive_time;
var exam_time_to_left;
var exam_started;
var exam_will_start;
var time_status = 1;
var time_spent_per_question = 0;
var time_spent = setInterval(time_spent_counter, 1000);;

function time_spent_counter()
{
  time_spent_per_question++;
  $('#time_spent').html(toHHMMSS(time_spent_per_question));
}




function exam_started_timer()
{
  exam_time_to_left = exam_time_to_left - 1;
  console.log("Exam remaing time: "+exam_time_to_left);
  if($('.clock_wrapper .clock .clock_block h1').length > 1){
        var x = toHHMMSS(exam_time_to_left);
        var res = x.split(":");
        console.log(res[1]);
        $('.clock_wrapper .clock:nth-child(1) .clock_block h1').html(res[0]);
        $('.clock_wrapper .clock:nth-child(3) .clock_block h1').html(res[1]);

   }
  if(exam_time_to_left >= 0 && $('#exam_status').length > 0){
            $('#exam_status h4[data-type="exam_sts_msg"] span:nth-child(1)').html('Exam will finish within : ');
            $('#exam_status h4[data-type="exam_sts_msg"] span:nth-child(2)').html(toHHMMSS(exam_time_to_left));
            if(is_exam_finished == false){
            $('#exam_status h4:nth-child(2)').html('[ Exam already started ]');
            }else{
                $('#exam_status h4:nth-child(2)').html('[ You have already attended exam! ]');
            }
  }
  if(exam_time_to_left == 900){
      $(".alert_notification p").html("Exam time will <b>finish</b> within <b>15 minutes.</b>");
      $(".alert_notification").show().delay(5000).fadeOut();
  }else if(exam_time_to_left == 300){
    console.log("5 minutes notification.");
      $(".alert_notification p").html("Exam time will <b>finish</b> within <b>5 minutes.</b>");
      $(".alert_notification").show().delay(5000).fadeOut();
  }else if(exam_time_to_left == 60){
    console.log("1 minutes notification.");
     $(".alert_notification p").html("Exam time will <b>finish</b> within <b>1 minute.</b>");
     $(".alert_notification").show().delay(5000).fadeOut();
  }else if (exam_time_to_left <= 0)
  { 
    $(".alert_notification p").html("<b>Exam time finished..</b>");
    $(".alert_notification").show().delay(5000).fadeOut();
    exam_time_to_start = exam_total_deactive_time;
    exam_will_start = setInterval(exam_will_start_timer, 1000);
    clearInterval(exam_started);
    return;
  }
}

function exam_will_start_timer()
{
  exam_time_to_start = exam_time_to_start - 1;
  console.log("Exam will start: "+ exam_time_to_start);
  if(exam_time_to_start >= 0 && $('#exam_status').length > 0){
           
           $('#exam_status h4[data-type="exam_sts_msg"] span:nth-child(1)').html('Exam will start within : ');
            $('#exam_status h4[data-type="exam_sts_msg"] span:nth-child(2)').html(toHHMMSS(exam_time_to_start));
            if(is_exam_finished == false){
            $('#exam_status h4:nth-child(2)').html('');
            }else{
                $('#exam_status h4:nth-child(2)').html('[ You have already attended exam! ]');
            }
  }

  if(exam_time_to_start == 900){
      $(".alert_notification p").html("Exam time will <b>start</b> within <b>15 minutes.</b>");
      $(".alert_notification").show().delay(5000).fadeOut();
  }else if(exam_time_to_start == 300){
    console.log("5 minutes notification.");
      $(".alert_notification p").html("Exam time will <b>start</b> within <b>5 minutes.</b>");
      $(".alert_notification").show().delay(5000).fadeOut();
  }else if(exam_time_to_start == 60){
    console.log("1 minutes notification.");
     $(".alert_notification p").html("Exam time will <b>start</b> within <b>1 minute.</b>");
     $(".alert_notification").show().delay(5000).fadeOut();
  }else if (exam_time_to_start <= 0)
  { 
    $('button[data-type="exam_start_request"]').removeAttr('disabled');
    $(".alert_notification p").html("<b>Exam time Started..</b>");
    $(".alert_notification").show().delay(5000).fadeOut();
    
    exam_time_to_left = exam_total_active_time;
    exam_started = setInterval(exam_started_timer, 1000);

    clearInterval(exam_will_start);
    return;
  }
}


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

        if(obj.time_to_left == 0 && obj.time_to_start > 0 && time_status == 1){
          time_status = 0;
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
            exam_time_to_start = obj.exam_time_to_start;
            exam_total_active_time = obj.exam_total_active_time;
            exam_total_deactive_time = obj.exam_total_deactive_time;
            exam_time_to_left = obj.exam_time_to_left;

            if(exam_time_to_start == 0 && exam_time_to_left > 0){
                exam_started = setInterval(exam_started_timer, 1000);
            }else if(exam_time_to_start > 0 && exam_time_to_left == 0){
                exam_will_start = setInterval(exam_will_start_timer, 1000);
            }

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
            
            if(obj.studymate_id == wp){
                cnt = $('.mCSB_container .three_tabs #study_request_cnt').html();
                if(cnt == 0 || cnt == '')
                    cnt = 1;
                else 
                    cnt = $('.mCSB_container .three_tabs #study_request_cnt').html() + 1;
                $('.mCSB_container .three_tabs #study_request_cnt').html(cnt);
            }            
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
            notification_str ='';
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

                if(wp == obj.studymate_id){
                    notification_str += '<li><a href="#">';
                    notification_str += '<div class="user_small_img"><img onerror="this.src=\'assets/images/avatar.png\'" src="uploads/'+obj.user_data.profile_link+'"></div>';
                    notification_str += '<div class="notification_txt">';
                    notification_str += '<p><span class="noti_username">'+obj.user_data.full_name+'</span> your friend request has been accepted</p>';
                    notification_str += '<span class="noti_time">1 hour ago</span></div>';
                    notification_str += '<div class="clearfix"></div>';
                    notification_str += '</a></li>';
                    $('.mCSB_container .three_tabs #notification-panel #no-more-notification').remove().html();
                    $('.mCSB_container .three_tabs #notification-panel').prepend(notification_str);
                    cnt = $('.mCSB_container .three_tabs #study_request_cnt').html();
                    if(cnt == '' || cnt == 0)
                        cnt = 1;
                    else
                        cnt = $('.mCSB_container .three_tabs #study_request_cnt').html() + 1;
                    $('.mCSB_container .three_tabs .bell_badge').html(cnt);
                }
                if(obj.user_data.id == wp){
                    cnt = $('.mCSB_container .three_tabs #study_request_cnt').html() - 1;
                    if(cnt == '' || cnt == 0)
                        $('.mCSB_container .three_tabs #study_request_cnt').remove().html();
                    else{
                        cnt = $('.mCSB_container .three_tabs #study_request_cnt').html() - 1;
                        $('.mCSB_container .three_tabs .bell_badge').html(cnt);
                    }
                }
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

        }else if(obj.type == 'get_studymate_name'){
          $("#tagged-users").show();

            var i = 0;
            var j = 0;
            var k = 0;
            str = '';
            ids = '';
            other_name = '';
            len = obj.student_detail.length;
            $.each(obj.student_detail, function (index, list) {
                if(len == 1){
                    str += '&nbsp;with : <label class="label label_name">'+ list.name + '</label>';
                    ids += list.id;
                }
                else if(len == 2){
                    if(i == 0){
                        str += '&nbsp;with <label class="label label_name">'+list.name +'</label>';
                        ids += list.id;
                    }else{
                        str += '&nbsp;and <label class="label label_name">'+list.name +'</label>';
                        ids += ','+list.id;
                    }
                    i++;
                }
                else if(len > 2){
                    
                    if(j == 0){
                        str += '&nbsp;with <label class="label label_name">'+list.name +'</label>';
                        ids += list.id;
                    }else{
                        other_name += list.name+'<div class=\'clearfix\'></div>';
                        l = parseInt(len) - parseInt(1);
                        if(j == l){

                            str += 'and <label class="label label_name">';
                            str += '<a href="javascript:void(0);" data-html="true" data-trigger="focus" data-placement="bottom" data-toggle="popover1" title="Other Tagged" data-content="'+other_name+'">'+ l +' more</a>';
                            str += '</label>';
                        }
                        ids += ','+list.id;    
                    }

                    j++;
                }

            });
            $('#tagged-users').html(str);
            $('#tagged-users-id').val(ids);
        }else if(obj.type == 'file_notification'){
        }else if(obj.type == 'question_responce'){
          time_spent_per_question = 0;
          $('.ques_numbers li[data-id="'+obj.question+'"]').attr('class',obj.status);
          $('button[data-type="clear_responce"]').removeAttr('disabled');
          $('button[data-type="question_responce"]').removeAttr('disabled');

          if(obj.qno > 0){
            $('#q_no').html(obj.qno);
            $('button[data-type="question_responce"]').attr('data-id',obj.qid);
            $('#time_spent').html('00:00');
            $('.question.text-center p').html(obj.question);
            $('.ans_options').html('');
             var chk = '';
            $.each(obj.answer, function (index, list){
               chk = ''; 
            if(obj.choice_id == list.id ){
                chk = 'checked';
            }
              $('.ans_options').append('<label><input '+chk+' type="radio" name="option" data-id="'+list.id+'">'+list.choice+'</label>'); 
            });
          }

          if($('ul.ques_numbers li[data-id="'+obj.qid+'"]').next().length == 0){
           $('button[data-status="next"]').html(' Save ');
          }
          $('ul.ques_numbers li[data-id="'+obj.question_id+'"]').attr('class',obj.status).data('class',obj.status);
          $('ul.ques_numbers li[data-id="'+obj.qid+'"]').attr('class','current');


        }else if(obj.type == 'get_question'){

          $('ul.ques_numbers li[class="current"]').attr('class', $('ul.ques_numbers li[class="current"]').data('class'));
          $('ul.ques_numbers li[data-id="'+obj.new_question.qid+'"]').attr('class','current');
          
          $('#q_no').html(obj.qno);
          $('button[data-type="question_responce"]').attr('data-id',obj.new_question.qid);
          $('#time_spent').html('00:00');
          $('.question.text-center p').html(obj.new_question.question);
          $('.ans_options').html('');
            var chk = '';
           $.each(obj.new_question.answer, function (index, list){
                chk = ''; 
            if(obj.new_question.choice_id == list.id ){
                chk = 'checked';
            }
            $('.ans_options').append('<label><input '+chk+' type="radio" name="option" data-id="'+list.id+'">'+list.choice+'</label>'); 
         });

           if($('ul.ques_numbers li[data-id="'+obj.new_question.qid+'"]').next().length == 0){
              $('button[data-status="next"]').html(' Save ');
          }else{
            $('button[data-status="next"]').html('Next <span class="fa fa-chevron-right"></span>');
          }

        }else if(obj.type == 'exam_start_request'){
            if(obj.exam_st == 'started'){
              location.href = '/student/exam';
            }
        }else if(obj.type == 'end_exam'){
            location.href= obj.redirect;
        }else if(obj.type == 'class_exam_start_request'){
            if(obj.class_exam_status == 'started'){
                location.href = '/student/class_exam';
            }
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
        $('#selection-box').hide();
        $(".js-example-basic-single").select2("val","");
        $("#tagged-users").hide();
        $('#tag_or_not').val('no'); 
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
        // alert(wp +'=='+ $(this).data('id'));
        // if(wp == $(this).data('id')){
            cnt = $('.comment_btn[data-id="' + $(this).data('id') + '"] span:nth-of-type(2)').html();
            $('.comment_btn[data-id="' + $(this).data('id') + '"] span:nth-of-type(2)').html(parseInt(cnt) + 1);
        // }
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
        i = 0;
        j = 0;
        k = 0;
        other_name = '';
        notification_str = '';
        $.each(obj.tagged_detail, function (index, list) {
            if(len == 1){
                name += '<b>with</b> : <label class="label label_name">'+ list.full_name + '</label>';
            }
            else if(len == 2){
                if(i == 0){
                    name += 'with <label class="label label_name">'+list.full_name +'</label>';
                }else{
                    name += 'and <label class="label label_name">'+list.full_name +'</label>';
                }
                i++;
            }
            else if(len > 2){
                if(j == 0){
                    name += 'with <label class="label label_name">'+list.full_name +'</label>';
                }else{
                    other_name += list.full_name+'<div class=\'clearfix\'></div>';
                    l = parseInt(len) - parseInt(1);
                    if(j == l){
                        name += 'and <label class="label label_name"><a href="javascript:void(0);" data-html="true" data-trigger="focus" data-placement="bottom" data-toggle="popover2" title="Other Tagged" data-content="'+other_name+'">'+ l +' more</a>';
                        name += '</label>';
                    }
                }
                j++;
            }
            notification_str += '<li><a href="#">';
            notification_str += '<div class="user_small_img"><img onerror="this.src=\'assets/images/avatar.png\'" src="uploads/'+obj.profile_link+'"></div>';
            notification_str += '<div class="notification_txt">';
            notification_str += '<p><span class="noti_username">'+obj.full_name+'</span> tagged you in a post</p>';
            notification_str += '<span class="noti_time">1 hour ago</span></div>';
            notification_str += '<div class="clearfix"></div>';
            notification_str += '</a></li>';
            if(wp == list.id){
                $('.mCSB_container .three_tabs #notification-panel #no-more-notification').remove().html();

                $('.mCSB_container .three_tabs #notification-panel').prepend(notification_str);
            

                notification_length = $('.mCSB_container .three_tabs #notification-panel li').length;
                if(notification_length == 0 ){
                    notification_length = $('.mCSB_container .three_tabs #notification-panel').prepend('<li><div class="notification_txt">No more notification</div></li>');
                    $('.mCSB_container .three_tabs .dropdown .badge').html(0);
                }
                else{
                    $('.mCSB_container .three_tabs .dropdown .badge').html(notification_length);
                }
            }

        });
        str += '<span>'+name+'</span>';
        }
    
    str += '<span class="date">Sep 28, 2015</span>';
    str += '<div class="clearfix"></div>';
    str += '<p>'+obj.message+'</p>';
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
     if(typeof(obj.comment) != 'undefined'){
        $.each(obj.comment, function (index, comment_list){
            generate_comment(comment_list);
        });
     }
        

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
$(document).on('click','.tut_weekdays li a[data-type="s"]', function(e) {
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
        $('button[data-type="close-studymate"]').attr('data-course',$(this).data('course')).attr('data-name',$(this).data('name')).attr('data-id',$(this).data('id')).attr('data-school',$(this).data('school')).attr('data-profile',$(this).data('profile'));   
        $('b[data-type="close-studymate-name"]').html($(this).data('name'));   
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
    $('#mCSB_2 #mCSB_2_container div[data-id="'+$(this).attr('data-id')+'"]').remove().html();
    str = '';
    str += '<div class="suggested_mates_card">'
    str += '<div class="mate_user_img"><img src="uploads/'+$(this).attr('data-profile')+'" onerror="this.src=\'assets/images/avatar.png\'"></div>';
    str += '<div class="mate_descrip"><p class="mate_name">'+$(this).attr('data-name')+'</p>';
    str += '<p class="mate_following">Folowing 34 Authers</p>';
    str += '<p>'+$(this).attr('data-school')+'</p>';
    str += '<p>'+$(this).attr('data-course')+'</p><button class="btn btn_green" data-id="'+$(this).attr('data-id')+'" data-type="studyment-request">Add Studymates</button></div></div>';
    $('.box_body #carousel-studymate .carousel-inner #active-recomonded').append(str);
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
        to: $(this).attr('data-id'),
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
        to: $(this).data('id'),
        studymate_id: $(this).data('id'),
        error : ''
    };
    ws.send(JSON.stringify(request)); 
});

$(document).on('click','button[data-type="save_and_next"]',function(e){
    var request = {
        type: 'exam_answer',
        qustion_id : $(this).data('qid'),
        to: 'self'
    };
    ws.send(JSON.stringify(request)); 

});

$(document).on('change', '#select-tag-user', function(e){
    if(e.val != ''){
        var request = {
        type: 'get_studymate_name',
        to: 'self',
        studymate_id: e.val,
        error : ''
        };
        ws.send(JSON.stringify(request)); 
    }
    else{
        $('#tagged-users').html('');
    }
});

$(document).on('click','button[data-type="exam_start_request"]',function(){
        $(this).attr('disabled','disabled');
        var request = {
        type: 'exam_start_request',
        to: 'self',
        };

        ws.send(JSON.stringify(request));
});

/* Clear selection */
$(document).on('click','button[data-type="clear_responce"]',function(){
  exam_choice = 0;
  $('.ans_options label input[name="option"]').attr('checked',false);
});

/* set answer */
$(document).on('click','.ans_options label input[name="option"]',function(){
    exam_choice = $(this).data('id');
});

/* Next question */
$(document).on('click','button[data-type="question_responce"]',function(){
  $('button[data-type="clear_responce"]').attr('disabled','disabled');
  $('button[data-type="question_responce"]').attr('disabled','disabled');
  var question_id = $(this).attr('data-id');
  var next_question = 0;
  var next_question_no = 0;

if($('ul.ques_numbers li[data-id="'+question_id+'"]').next().length  == 1){
  next_question = $('ul.ques_numbers li[data-id="'+question_id+'"]').next().data('id');
  next_question_no = $('ul.ques_numbers li[data-id="'+question_id+'"]').next().children('a').data('no');
}
  
  
  var request = {
    type : 'question_responce',
    to   : 'self',
    time : time_spent_per_question,
    question_id: question_id,
    qno: next_question_no,
    next: next_question,
    answer : exam_choice,
    status :$(this).attr('data-status')
  }
  
  ws.send(JSON.stringify(request));
});

$(document).on('click','a[data-type="get_question"]',function(){
  var request = {
    type : 'get_question',
    to   : 'self',
    question_no: $(this).data('id'),
    qno : $(this).data('no')
  }
 ws.send(JSON.stringify(request));
});

$(document).on('click','button[data-type="end_exam"]',function(){
  var request = {
    type : 'end_exam',
    to   : 'self'
  }
 ws.send(JSON.stringify(request));
});
$(document).on('click','button[data-type="class_exam_start_request"]',function(){
    $(this).attr('disabled','disabled');
    var request = {
    type: 'class_exam_start_request',
    to: 'self',
    exam_id: $(this).data('id')
    };

    ws.send(JSON.stringify(request));
});


$(document).on('click','a[data-type="tag-user-again"]',function(){
  
  var request = {
    type : 'tag-user-again',
    to   : 'all',
    tagged_id: $('#select-tag-user-again').val(),
    fid : $(this).data('id')
  }
  ws.send(JSON.stringify(request));
});

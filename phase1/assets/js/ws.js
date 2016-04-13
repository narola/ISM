/* Exam time information and notification. */
var exam_time_to_start;
var exam_total_active_time;
var exam_total_deactive_time;
var exam_time_to_left;
var exam_started;
var exam_will_start;
var time_status = 1;
var time_spent_per_question = 0;
var time_spent = setInterval(time_spent_counter, 1000);
var typing_timer = null;
function time_spent_counter()
{
    $('#time_spent').html(toHHMMSS(time_spent_per_question++));
}

function exam_started_timer()
{
    exam_time_to_left = exam_time_to_left - 1;
    // console.log("Exam remaing time: " + exam_time_to_left);
    if ($('.clock_wrapper .clock .clock_block h1').length > 1) {
        var x = toHHMMSS(exam_time_to_left);
        var res = x.split(":");
        $('.clock_wrapper .clock:nth-child(1) .clock_block.tls h1').html(res[0]);
        $('.clock_wrapper .clock:nth-child(3) .clock_block.tls h1').html(res[1]);
        $('.clock_wrapper .clock:nth-child(5) .clock_block.tls h1').html(res[2]);
    }
    if (exam_time_to_left >= 0 && $('#exam_status').length > 0) {
        $('#exam_status h4[data-type="exam_sts_msg"] span:nth-child(1)').html('Exam will finish within : ');
        $('#exam_status h4[data-type="exam_sts_msg"] span:nth-child(2)').html(toHHMMSS(exam_time_to_left));
        if (is_exam_finished == false) {
            $('#exam_status h4:nth-child(2)').html('[ Exam already started ]');
        } else {
            $('#exam_status h4:nth-child(2)').html('[ You have already attended exam! ]');
        }
    }
    if (exam_time_to_left == 900) {

        //$(".alert_notification p").html("Exam time will <b>finish</b> within <b>15 minutes.</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();

        $.notify({
            title: '<strong>Hurry up!</strong>',
            message: 'Exam time will <b>finish</b> within <b>15 minutes.</b>.'
        },{
            type: 'warning'
        });

    } else if (exam_time_to_left == 300) {
        //console.log("5 minutes notification.");
        //$(".alert_notification p").html("Exam time will <b>finish</b> within <b>5 minutes.</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();

          $.notify({
            title: '<strong>Hurry up!</strong>',
            message: 'Exam time will <b>finish</b> within <b>5 minutes.</b>.'
        },{
            type: 'warning'
        });

    } else if (exam_time_to_left == 60) {
       // console.log("1 minutes notification.");
        //$(".alert_notification p").html("Exam time will <b>finish</b> within <b>1 minute.</b>");
       // $(".alert_notification").show().delay(7000).fadeOut();

         $.notify({
            title: '<strong>Hurry up!</strong>',
            message: 'Exam time will <b>finish</b> within <b>1 minutes.</b>.'
        },{
            type: 'warning'
        });

    } else if (exam_time_to_left <= 0)
    {
       // $(".alert_notification p").html("<b>Exam time finished..</b>");
       // $(".alert_notification").show().delay(7000).fadeOut();
           $.notify({
            title: '<strong>Stop now!</strong>',
            message: '<b>Exam time finished..</b>.'
        },{
            type: 'warning'
        });   


        exam_time_to_start = exam_total_deactive_time;
        exam_will_start = setInterval(exam_will_start_timer, 1000);
        clearInterval(exam_started);
        return;
    }
}

function exam_will_start_timer()
{
    exam_time_to_start = exam_time_to_start - 1;
   // console.log("Exam will start: " + exam_time_to_start);
    if (exam_time_to_start >= 0 && $('#exam_status').length > 0) {

        $('#exam_status h4[data-type="exam_sts_msg"] span:nth-child(1)').html('Exam will start within : ');
        $('#exam_status h4[data-type="exam_sts_msg"] span:nth-child(2)').html(toHHMMSS(exam_time_to_start));
        if (is_exam_finished == false) {
            $('#exam_status h4:nth-child(2)').html('');
        } else {
            $('#exam_status h4:nth-child(2)').html('[ You have already attended exam! ]');
        }
    }

    if (exam_time_to_start == 900) {
       // $(".alert_notification p").html("Exam time will <b>start</b> within <b>15 minutes.</b>");
       // $(".alert_notification").show().delay(7000).fadeOut();

          $.notify({
            title: '<strong>Wait !</strong>',
            message: 'Exam time will <b>start</b> within <b>15 minutes.</b>.'
        },{
            type: 'warning'
        });


    } else if (exam_time_to_start == 300) {
        //console.log("5 minutes notification.");
        //$(".alert_notification p").html("Exam time will <b>start</b> within <b>5 minutes.</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();
          $.notify({
            title: '<strong>Wait !</strong>',
            message: 'Exam time will <b>start</b> within <b>5 minutes.</b>.'
        },{
            type: 'warning'
        });

    } else if (exam_time_to_start == 60) {
       // console.log("1 minutes notification.");
        //$(".alert_notification p").html("Exam time will <b>start</b> within <b>1 minute.</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();
         $.notify({
            title: '<strong>Wait !</strong>',
            message: 'Exam time will <b>start</b> within <b>1 minutes.</b>.'
        },{
            type: 'warning'
        });

    } else if (exam_time_to_start <= 0)
    {
        $('button[data-type="exam_start_request"]').removeAttr('disabled');
        //$(".alert_notification p").html("<b>Exam time Started..</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();
         $.notify({
            title: '<strong>Started !</strong>',
            message: '<b>Exam time Started..</b>.'
        },{
            type: 'warning'
        });

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
   
    remainning_time = remainning_time - 1;
   // console.log("Remainnig Seconds to complete: " + remainning_time);
    if (remainning_time == 900) {
        //$(".alert_notification p").html("Active hours will <b>finish</b> within <b>15 minutes.</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();
         $.notify({
            title: '<strong>Keep in mind !</strong>',
            message: 'Active hours will <b>finish</b> within <b>15 minutes.</b>.'
        },{
            type: 'warning'
        });


    } else if (remainning_time == 300) {
        // console.log("5 minutes notification.");
        //$(".alert_notification p").html("Active hours will <b>finish</b> within <b>5 minutes.</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();
         $.notify({
            title: '<strong>Keep in mind !</strong>',
            message: 'Active hours will <b>finish</b> within <b>5 minutes.</b>.'
        },{
            type: 'warning'
        });

    } else if (remainning_time == 60) {
        // console.log("1 minutes notification.");
        //$(".alert_notification p").html("Active hours will <b>finish</b> within <b>1 minute.</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();
         $.notify({
            title: '<strong>Keep in mind !</strong>',
            message: 'Active hours will <b>finish</b> within <b>1 minute.</b>.'
        },{
            type: 'warning'
        });

    } else if (remainning_time <= 0)
    {
        //$(".alert_notification p").html("Active hours <b>finished.</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();
         $.notify({
            title: '<strong>Thank you !</strong>',
            message: 'Active hours <b>finished.</b>.'
        },{
            type: 'warning'
        });

        clearInterval(remainning_counter);
        return;
    }
}

var time_count_out = 0;
var max_count_out = 0;
var counter_out;

function timeout_timer()
{
    time_count_out = time_count_out - 1;
    // console.log("Remainnig Seconds :" + time_count_out);
    if (time_count_out == 900) {
        console.log("15 minutes notification.");
        //$(".alert_notification p").html("Active hours will start within <b>15 minutes.</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();

          $.notify({
            title: '<strong>Wait !</strong>',
            message: 'Active hours will start within <b>15 minutes.</b>.'
        },{
            type: 'warning'
        });


    } else if (time_count_out == 300) {
        console.log("5 minutes notification.");
        //$(".alert_notification p").html("Active hours will start within <b>5 minutes.</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();
         $.notify({
            title: '<strong>Wait !</strong>',
            message: 'Active hours will start within <b>5 minutes.</b>.'
        },{
            type: 'warning'
        });

    } else if (time_count_out == 60) {
        //$(".alert_notification p").html("Active hours will start within <b>1 minute.</b>");
        //$(".alert_notification").show().delay(7000).fadeOut();
          $.notify({
            title: '<strong>Wait !</strong>',
            message: 'Active hours will start within <b>1 minutes.</b>.'
        },{
            type: 'warning'
        });

    } else if (time_count_out <= 0)
    {
        if (start_timer == true) {
            console.log("Timer Started...");
            ws.send('{"type":"time_request","from":"' + wp + '","to":"self","error":""}');
        } else {
            //$(".alert_notification p").html("Active hours started.");
            //$(".alert_notification").show().delay(7000).fadeOut();
              $.notify({
            title: '<strong>Now,</strong>',
            message: 'Active hours started.</b>.'
        },{
            type: 'warning'
        });

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
        emptyFill: '#000',
        thickness: 15,
        animation: {duration: 500}
    });

    /* Handle multiple chat window. */
    $(document).on('click', '.chat .chat_header', function () {
        if ($(this).parent().hasClass('passive')) {
            $(this).removeClass("blinking");
            $(this).removeClass("blink");
            if ($(this).parent().hasClass('chat_3')) {
                $('.chat.active').removeClass('active').addClass('chat_3 passive');
                clearInterval(chat_3);

            }
            if ($(this).parent().hasClass('chat_2')) {
                $('.chat.active').removeClass('active').addClass('chat_2 passive');
                clearInterval(chat_2);
            }
            if ($(this).parent().hasClass('chat_1')) {
                $('.chat.active').removeClass('active').addClass('chat_1 passive');
                clearInterval(chat_1);
            }

            $(this).parent().removeClass().addClass('active').addClass('chat');
            $('#mate_list .badge').html("");
            var chat_id = $('.chat_container .active').attr('data-id');
             var request = {
                                type: 'get_latest_message',
                                to: 'self',
                                my_id: chat_id
                            };
                                ws.send(JSON.stringify(request));
            
        }
    });

    /* Validate length of selected file. */
    var handleFileSelect = function (evt) {
        var files = evt.target.files;
        var file = files[0];
        var user = $(this).data('id');
        var types = $(this).data('type');
        var type_of_data = this.files[0].type;
        var file_name = this.files[0].name;

       /* if (types == 'feed_file_share') {
            if (type_of_data != 'image/png' && type_of_data != 'image/gif' && type_of_data != 'image/jpg' && type_of_data != 'image/jpeg') {
                alert('You can upload only images in feed!');
                return;
            }
        }*/



        if (user != 'feed' && user != 'topic') {
            $('.chat[data-id="' + user + '"] .chat_loading').fadeIn(300);
        }

        if (this.files[0].size <= 1024 * 1024 * 10) {
            if (files && file) {
                var reader = new FileReader();
                reader.onload = function (readerEvt) {
                    var binaryString = readerEvt.target.result;
                    var request = {
                        type: types,
                        name: file_name,
                        data_type: type_of_data,
                        data: btoa(binaryString),
                        to: user
                    }

                    if ($('#feed_post').length > 0) {
                        $('#feed_post').attr('readonly', 'readonly');
                        $('button[data-type="post"]').attr('disabled', 'disabled');
                    }

                    if ($('textarea[data-type="discussion"]').length > 0) {
                        $('textarea[data-type="discussion"]').attr('readonly', 'readonly');
                        $('div[data-type="discussion-submit"] button').attr('disabled', 'disabled');
                    }
                    if ($('.upload_loader').length > 0 && types != "chat") {
                        $('.upload_loader').fadeIn(400);
                    }


                    ws.send(JSON.stringify(request));
                };
                reader.readAsBinaryString(file);
            }
        } else {
             $('#image_upload_model').addClass('in',{duration:500});
             $('#image_upload_model').css("display","block");
            $(this).val('').clone(true);
        }
    };

    if (window.File && window.FileReader && window.FileList && window.Blob) {
        if ($('#chat_file_share').length > 0) {
            //document.getElementById('chat_file_share').addEventListener('change', handleFileSelect, false);
        }

        if ($('#feed_file_share').length > 0) {
            document.getElementById('feed_file_share').addEventListener('change', handleFileSelect, false);
            
        }

        if ($('#group_file_share').length > 0) {
            document.getElementById('group_file_share').addEventListener('change', handleFileSelect, false);
        }

    } else {
        alert('The File APIs are not fully supported in this browser.');
    }

});


/* Check wheather web socket is supported by browser. */
if ("WebSocket" in window)
{

      var ws = new WebSocket("ws://192.168.1.189:9301"); // pv
      // var ws = new WebSocket("ws://192.168.1.114:9301"); // nv
      //ws = new WebSocket("ws://52.28.165.231:9301"); // server

    ws.onopen = function ()
    {
        ws.send('{"type":"con","from":"' + wp + '","to":"self"}');
    };

    ws.onmessage = function (evt)
    {
        var obj = $.parseJSON(evt.data);
        
        if (obj.error != 'skip') {
            $(".alert_notification p").html(obj.error);
            $(".alert_notification").show().delay(7000).fadeOut();
        }

        if (obj.reload == 'yes') {
            setTimeout(function () {
                location.reload();
            }, 3000);

        }

        if ($('.upload_loader').length > 0) {
            $('.upload_loader').fadeOut(400);

            if ($('#feed_post').length > 0) {
                $('#feed_post').removeAttr('readonly');
                $('button[data-type="post"]').removeAttr('disabled');
            }

            if ($('textarea[data-type="discussion"]').length > 0) {
                $('textarea[data-type="discussion"]').removeAttr('readonly');
                $('div[data-type="discussion-submit"] button').removeAttr('disabled');
            }

        }

        if (obj.redirect != 'skip') {
            // console.log(obj);
            location.href = obj.redirect;
        }

        if (obj.time_to_left == 0 && obj.time_to_start > 0 && time_status == 1) {
            time_status = 0;
            time_count_out = obj.time_to_start;
            max_count_out = obj.time_to_start;
            counter_out = setInterval(timeout_timer, 1000);
        }


        if (obj.type == 'studymate') {
            if (wp == obj.from) {
                $('#chat_container .chat[data-id="' + obj.to + '"] .chat_text .mCustomScrollBox .mCSB_container').append("<div class='to'><p>" + obj.message + "</p><div class='just_now'>Just Now</div></div>");
                $('.chat[data-id="' + obj.to + '"] .chat_loading').fadeOut(300);
            } 

            /*else {
                console.log("here");
                console.log(obj);
                // setTimeout(function () {
                    $('#chat_container .chat[data-id="' + obj.from + '"] .chat_text .mCustomScrollBox .mCSB_container').append("<div class='from'><p>" + obj.message + "</p><div class='just_now'>Just Now</div></div>");
                // }, 400);
            }*/
            
            

               
            if ($('#chat_container .chat.active').data('id') != obj.from && wp != obj.from) {
                myfunction(obj.from);
              
                var ac = $('.stm .stm_list .mCustomScrollBox .mCSB_container .stm_item[data-id="' + obj.from + '"]');
                $('.stm_list .mCustomScrollBox .mCSB_container').prepend('<div class="' + ac.attr('class') + '" data-id="' + obj.from + '">' + ac.remove().html() + '</div>');

                var c = $('.stm .stm_list .mCustomScrollBox .mCSB_container .stm_item[data-id="' + obj.from + '"] a span.badge');
                var count = c.text();

                if (count == '' || count == 0 || count == 'undefined') {
                    count = 0;
                }
                if($('#chat_container .chat.active').data('id') != obj.from)
                {   
                      var request = {
                            type: 'set_unread',
                            to: 'none',
                            insert_id: obj.insert_id
                        };
                     ws.send(JSON.stringify(request));
                    c.html(++count);
                }
                
            }
            if (wp != obj.from) {
                if($(".chat_text").is(":visible")){
                    $('#chat_container .chat[data-id="' + obj.from + '"] .chat_text .mCustomScrollBox .mCSB_container').append("<div class='from'><p>" + obj.message + "</p><div class='just_now'>Just Now</div></div>");
                }
            }
            $('.just_now').timestatus();
          
              setTimeout(function(){
               // $(".chat_input").focus();
                $('.chat_text').mCustomScrollbar('update');
                $('.chat_text').mCustomScrollbar('scrollTo', "bottom");
               }, 300); 

        }else if (obj.type == 'chat_type') {
            
           if(wp == obj.to){
                clearTimeout(typing_timer);
                var is_active_window = $("#chat_container .chat[data-id='"+obj.user_iddd+"']").hasClass('active');
                if(is_active_window){
                        var name = $("#chat_container .chat[data-id='"+obj.user_iddd+"']").find(".chat_name").html();
                        $("span.chat_typing").html('');
                        $("#chat_container .chat[data-id='"+obj.user_iddd+"']").find("span.chat_typing").html(name + ' is '+obj.message);
                       // $("span.chat_typing").html();
                        //alert($("span.chat_typing").html());
                       typing_timer =  setTimeout(function () {
                           $("span.chat_typing").html('');
                        }, 4000);
                    }
            }

         }else if (obj.type == 'con') {
            exam_time_to_start = obj.exam_time_to_start;
            exam_total_active_time = obj.exam_total_active_time;
            exam_total_deactive_time = obj.exam_total_deactive_time;
            exam_time_to_left = obj.exam_time_to_left;

            if (exam_time_to_start == 0 && exam_time_to_left > 0) {
                exam_started = setInterval(exam_started_timer, 1000);
            } else if (exam_time_to_start > 0 && exam_time_to_left == 0) {
                exam_will_start = setInterval(exam_will_start_timer, 1000);
            }

            var theString = obj.online_user;
            $.each(theString.split("-"), function (index, id) {
                $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('offline').addClass('online');
                if (start_timer == true) {
                    $('.tut_group .box_footer[data-id="' + id + '"] p').html('Online');
                    $('.tut_group .box_body[data-id="' + id + '"] a').removeClass('icon_call_user').addClass('icon_call_user_disable');
                }

                if ($('.my_studymates .general_cred').length > 0) {
//                  alert($('.my_studymates .general_cred .studyamte_list .mCustomScrollBox .mCSB_container').attr('class'));
                }

            });

            if (obj.time_to_left > 0) {
                remainning_time = obj.time_to_left;
                remainning_counter = setInterval(remainning_time_timer, 1000);
            }
        } else if (obj.type == 'notification') {
            set_status(obj.user_id, obj.live_status);
        } else if (obj.type == 'get_latest_message') {
            $.each(obj.message, function (index, list) {
                var my_msg = '';
                if (list.is_text == 0) {
                    my_msg = '<a href="uploads/' + list.a_link + '"  target="_BLANK"><img src="' + list.img_link + '" width="50" height="50" /></a>';
                } else {
                    my_msg = list.text;
                }
                if (list.to == 1) {
                    my_msg = '<div class="to"><p>' + my_msg + '</p><div>' + list.cdate + '</div></div>';
                } else {
                    my_msg = '<div class="from"><p>' + my_msg + '</p><div>' + list.cdate + '</div></div>';
                }
                $('.chat[data-id="' + obj.my_id + '"] .chat_text .mCustomScrollBox .mCSB_container').append(my_msg);
            });


              setTimeout(function(){
               // $(".chat_input").focus();
                $('.chat_text').mCustomScrollbar('update');
                $('.chat_text').mCustomScrollbar('scrollTo', "bottom");
          }, 300);

        } else if (obj.type == 'post') {
            if (obj.id != wp) {

                    $.notify({
                                icon: 'uploads/' + obj.profile_link + '',
                                title: '<strong>New post</strong> ',
                                message: 'From ' + obj.full_name + ' : <span style="font-size:12px">' + obj.message.substr(0,100) + '...</span>'
                            },{
                                type: 'minimalist',
                                delay: 5000,
                                icon_type: 'image',
                                template: '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert">' +
                                    '<img data-notify="icon" class="img-circle pull-left">' +
                                    '<span data-notify="title">{1}</span>' +
                                    '<span data-notify="message">{2}</span>' +
                                '</div>'
                            });

                                    // $.notify({
                    //             icon: '' + obj.profile_link + '',
                    //             title: '<strong>New post:</strong> ',
                    //             message: 'From <strong>' + obj.full_name + '</strong>: ' + obj.message + '...'; 
                    //         },{
                    //             type: 'minimalist',
                    //             delay: 5000,
                    //             icon_type: 'image',
                    //             template: '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert">' +
                    //                 '<img data-notify="icon" class="img-circle pull-left">' +
                    //                 '<span data-notify="title">{1}</span>' +
                    //                 '<span data-notify="message">{2}</span>' +
                    //             '</div>'
                    //         });
               // $('.alert_notification p').html("New feed from <b>" + obj.full_name + "</b>").show();
            }
            generate_post(obj, true);

            $("#tagged-users-id").val("");
            $("#tag_or_not").val("");

        } else if (obj.type == 'feed_comment') {
            generate_comment(obj);
        } else if (obj.type == 'load_more_feed') {
            $.each(obj.feed, function (index, jsonObject) {
                generate_post(jsonObject, false);
            });
            $('button[data-type="load_more"]').attr('data-start', obj.start);
            $('button[data-type="load_more"]').prop('disabled', false);
        } else if (obj.type == 'discussion') {
            $('.discussion').mCustomScrollbar('scrollTo', 'bottom');
            if (obj.time_to_left > 0) {
                clearInterval(counter);
                time_count = obj.time_to_left;
                counter = setInterval(timer, 1000);
            }
            if ($('.upload_loader_whiteboard').is(':visible')) {
                $('.upload_loader_whiteboard').fadeOut(300);
            }
            generate_cm(obj);
        } else if (obj.type == 'like') {

            if (wp == obj.id) {
                if (obj.message == 'like') {
                    $('.like_btn[data-id="' + obj.fid + '"]').html('<span data-toggle="tooltip" title="Unlike" class="icon icon_thumb"></span>' + obj.like_cnt);
                }
                else {
                    $('.like_btn[data-id="' + obj.fid + '"]').html('<span data-toggle="tooltip" title="Like" class="icon icon_thumb_0"></span>' + obj.like_cnt);
                }
                // $('.icon').tooltip();
            }
            else {
                var btn_like_class = $('.like_btn[data-id="' + obj.fid + '"] span').attr('class');
                $('.like_btn[data-id="' + obj.fid + '"]').html('<span title="Like" class="'+btn_like_class+'"></span>' + obj.like_cnt);
                //$('.like_btn[data-id="' + obj.fid + '"] span:nth-of-type(2)').html(obj.like_cnt);
            }
        } else if (obj.type == "discussion-type") {
            $('.box_footer[data-id="' + obj.type_id + '"] p').html(obj.message);
            setTimeout(function () {
                $('.box_footer[data-id="' + obj.type_id + '"] p').html('Online');
            }, 2000);

        } else if (obj.type == "close_studymate") {
            $('#close_mate').modal('toggle');
            if (obj.error == '') {
                $('.studyamte_list .mCustomScrollBox  .mCSB_container  .study_mate[data-id="' + obj.studymate_id + '"]').fadeOut(300);
            }

        } else if (obj.type == "dictionary") {
            $('.dictionary_result .mCustomScrollBox .mCSB_container').html(obj.message);
            $('input[data-type="search-dictionary"]').removeAttr('disabled');

        } else if (obj.type == "send_studymate_request") {
            if (obj.studymate_id == wp) {
                cnt = $('.mCSB_container .three_tabs #study_request_cnt').html();
                if (cnt == 0 || cnt == '')
                    cnt = 1;
                else
                    cnt = parseInt($('.mCSB_container .three_tabs #study_request_cnt').html()) + 1;
                $('.mCSB_container .three_tabs #study_request_cnt').html(cnt);
            }
        } else if (obj.type == 'time_request') {
            clearInterval(counter);
            time_count = obj.total_active_time;
            max_count = obj.total_active_time;
            counter = setInterval(timer, 1000);
        } else if (obj.type == 'time_start_request') {

            time_count_out = obj.total_deactive_time;
            max_count_out = obj.total_deactive_time;
            counter_out = setInterval(timeout_timer, 1000);

        } else if (obj.type == "view-all-comment-activities") {
            str = '';

            $.each(obj.comment, function (index, comment) {
                str += '<div class="user_small_img user_comment">';
                str += '<img src="uploads/' + obj.profile + '" onerror="this.src=\'assets/images/avatar.png\'">';
                str += '</div><div class="notification_txt">';
                str += '<p><a class="noti_username">' + obj.name + '</a> ' + comment.comment + '</p>';
                str += '<span class="noti_time">' + comment.date + '</span>';
                str += '</div>';
                str += '<div class="clearfix"></div>';
            });

            $('.commented_on .feeds .comment[data-id="' + obj.comment_id + '"]').html(str);
        } else if (obj.type == "decline-request") {
            notification_str = '';
            if (obj.sub_type == 'accept-request') {
                if (obj.is_online == true)
                    status = 'online';
                else
                    status = 'offline';
                str = '';
                str += '<div class="stm_item ' + status + '" data-id="' + obj.studymate_id + '">';
                str += '<a href="javascript:void(0);" id="mate_list" data-id="' + obj.studymate_id + '">';
                str += '<div class="stm_user_img">';
                str += '<img onerror="this.src=\'assets/images/avatar.png\'" src="uploads/' + obj.profile + '" class="mCS_img_loaded"></div>';
                str += '<span class="badge message_badge"></span>';
                str += '<p>' + obj.full_name + '</p></a>';
                str += '<div class="clearfix"></div></div>';
                $('.stm_list #mCSB_5 #mCSB_5_container').append(str);

                if (wp == obj.studymate_id) {
                    notification_str += '<li><a href="#">';
                    notification_str += '<div class="user_small_img"><img onerror="this.src=\'assets/images/avatar.png\'" src="uploads/' + obj.user_data.profile_link + '"></div>';
                    notification_str += '<div class="notification_txt">';
                    notification_str += '<p><span class="noti_username">' + obj.user_data.full_name + '</span> accepted your friend request</p>';
                    notification_str += '<span class="noti_time just_noti">Just Now</span></div>';
                    notification_str += '<div class="clearfix"></div>';
                    notification_str += '</a></li>';
                    $('.mCSB_container .three_tabs #notification-panel #no-more-notification').remove().html();
                    $('.just_noti').timestatus();
                    $('.mCSB_container .three_tabs #notification-panel').prepend(notification_str);
                    cnt = $('.mCSB_container .three_tabs #study_request_cnt').html();
                    if (cnt == '' || cnt == 0)
                        cnt = 1;
                    else
                        cnt = $('.mCSB_container .three_tabs #study_request_cnt').html() + 1;
                    $('.mCSB_container .three_tabs .bell_badge').html(cnt);
                }
                if (obj.user_data.id == wp) {
                    cnt = $('.mCSB_container .three_tabs #study_request_cnt').html() - 1;
                    if (cnt == '' || cnt == 0)
                        $('.mCSB_container .three_tabs #study_request_cnt').html(0);
                    else {
//                        cnt = $('.mCSB_container .three_tabs #study_request_cnt').html() - 1;
                        $('.mCSB_container .three_tabs #study_request_cnt').html(cnt);
//                        $('.mCSB_container .three_tabs .bell_badge').html(cnt);
                    }
                }
            }
            if (obj.sub_type == 'decline-request') {
                // cnt = $('.box_body #carousel-studymate .carousel-inner #active-recomonded .suggested_mates_card').length;
                str = '';
                str += '<div class="suggested_mates_card">'
                str += '<div class="mate_user_img"><img src="uploads/' + obj.profile + '" onerror="this.src=\'assets/images/avatar.png\'"></div>';
                str += '<div class="mate_descrip"><p class="mate_name">' + obj.full_name + '</p>';
                str += '<p class="mate_following">Folowing 34 Authers</p>';
                str += '<p>' + obj.school_name + '</p>';
                str += '<p>' + obj.course_name + '</p><button class="btn btn_green" data-id="' + obj.studymate_id + '" data-type="studyment-request">Add Studymate</button></div></div>';
                $('.box_body #carousel-studymate .carousel-inner #active-recomonded').append(str);
                if (obj.user_data.id == wp) {
                    cnt = $('.mCSB_container .three_tabs #study_request_cnt').html() - 1;
                    if (cnt == '' || cnt == 0)
                        $('.mCSB_container .three_tabs #study_request_cnt').html(0);
                    else {
                        cnt = $('.mCSB_container .three_tabs #study_request_cnt').html(cnt);
                    }
                }
            }
            $('.box_body .mCustomScrollBox .mCSB_container div[data-id="' + obj.studymate_id + '"]').remove();
            cnt = $('.box_body #mCSB_3 #mCSB_3_container #my_request').length;
            if (cnt == 0)
            {
                $('#my_request_box').html('<div class="study_mate"><center><label class="txt_grey txt_red">no more studymate request</label></center></div>');
            }

        } else if (obj.type == 'get_studymate_name') {
            $("#tagged-users").show();
            var i = 0;
            var j = 0;
            var k = 0;
            str = '';
            ids = '';
            other_name = '';
            len = obj.student_detail.length;
            $.each(obj.student_detail, function (index, list) {
                if (len == 1) {
                    str += '&nbsp;tagged : <label class="label label_name">' + list.name + '</label>';
                    ids += list.id;
                }
                else if (len == 2) {
                    if (i == 0) {
                        str += '&nbsp;tagged : <label class="label label_name">' + list.name + '</label>';
                        ids += list.id;
                    } else {
                        str += 'and <label class="label label_name">' + list.name + '</label>';
                        ids += ',' + list.id;
                    }
                    i++;
                }
                else if (len > 2) {

                    if (j == 0) {
                        str += '&nbsp;tagged : <label class="label label_name">' + list.name + '</label>';
                        ids += list.id;
                    } else {
                        other_name += list.name + '<div class=\'clearfix\'></div>';
                        l = parseInt(len) - parseInt(1);
                        if (j == l) {

                            str += 'and <label class="label label_name">';
                            str += '<a href="javascript:void(0);" data-html="true" data-trigger="focus" data-placement="bottom" data-toggle="popover1" title="Other Tagged" data-content="' + other_name + '">' + l + ' more</a>';
                            str += '</label>';
                        }
                        ids += ',' + list.id;
                    }
                    j++;
                }
            });
            $('#tagged-users').html(str);
            $('#tagged-users-id').val(ids);
        } else if (obj.type == 'file_notification') {
            
        } else if (obj.type == 'question_responce') {
            time_spent_per_question = 0;
            $('.ques_numbers li[data-id="' + obj.question + '"]').attr('class', obj.status);
            $('button[data-type="clear_responce"]').removeAttr('disabled');
            $('button[data-type="question_responce"]').removeAttr('disabled');

            if (obj.qno > 0) {
                $('#q_no').html(obj.qno);
                $('button[data-type="question_responce"]').attr('data-id', obj.qid);
                $('#time_spent').html('00:00');
                $('.question.text-center p').html(obj.question);
                $('.ans_options').html('');
                var chk = '';
                $.each(obj.answer, function (index, list) {
                    chk = '';
                    if (obj.choice_id == list.id) {
                        chk = 'checked';
                        exam_choice = list.id;
                    }
                    $('.ans_options').append('<label><input ' + chk + ' type="radio" name="option" data-id="' + list.id + '">' + list.choice + '</label>');
                });
            }

            if ($('ul.ques_numbers li[data-id="' + obj.qid + '"]').next().length == 0) {
                $('button[data-status="next"]').html(' Save ');
            }
            $('ul.ques_numbers li[data-id="' + obj.question_id + '"]').attr('class', obj.status).data('class', obj.status);
            $('ul.ques_numbers li[data-id="' + obj.qid + '"]').attr('class', 'current');
        } else if (obj.type == 'get_question') {
            time_spent_per_question = 0;
            $('ul.ques_numbers li[class="current"]').attr('class', $('ul.ques_numbers li[class="current"]').data('class'));
            $('ul.ques_numbers li[data-id="' + obj.new_question.qid + '"]').attr('class', 'current');

            $('#q_no').html(obj.qno);
            $('button[data-type="question_responce"]').attr('data-id', obj.new_question.qid);
            $('#time_spent').html('00:00');
            $('.question.text-center p').html(obj.new_question.question);
            $('.ans_options').html('');
            var chk = '';
            $.each(obj.new_question.answer, function (index, list) {
                chk = '';
                if (obj.new_question.choice_id == list.id) {
                    chk = 'checked';
                    exam_choice = list.id;
                }
                $('.ans_options').append('<label><input ' + chk + ' type="radio" name="option" data-id="' + list.id + '">' + list.choice + '</label>');
            });

            if ($('ul.ques_numbers li[data-id="' + obj.new_question.qid + '"]').next().length == 0) {
                $('button[data-status="next"]').html(' Save ');
            } else {
                $('button[data-status="next"]').html('Next <span class="fa fa-chevron-right"></span>');
            }

        } else if (obj.type == 'exam_start_request') {
            if (obj.exam_st == 'started') {
                var irl = '/'
                if (window.location.host == 'clientapp.narolainfotech.com') {
                    irl = '/hd/ISM/'
                }
                location.href = 'student/exam';
                // location.href = irl + 'student/exam';
            }
        } else if (obj.type == 'end_exam') {

        } else if (obj.type == 'class_exam_start_request') {
            if (obj.class_exam_status == 'started') {
                var irl = '/'
                if (window.location.host == 'clientapp.narolainfotech.com') {
                    irl = '/hd/ISM/'
                }
                location.href = 'student/class_exam';
                // location.href = irl + 'student/class_exam';
            }
        } else if (obj.type == 'tag-user-again') {
            var i = 0;
            var j = 0;
            var k = 0;
            str = '';
            ids = '';
            other_name = '';
            notification_str = '';
             len = obj.already_available_tagged_detail.length;
            $.each(obj.already_available_tagged_detail, function (index, list) {
                if (len == 1) {
                    str += '&nbsp;tagged : <label style="cursor:pointer;" data-type="show-profile" data-id='+ list.id +' class="label label_name">' + list.full_name + '</label>';
                    ids += list.id;
                }
                else if (len == 2) {
                    if (i == 0) {
                        str += '&nbsp;tagged : <label style="cursor:pointer;" data-type="show-profile" data-id='+ list.id +'  class="label label_name">' + list.full_name + '</label>';
                        ids += list.id;
                    } else {
                        str += 'and <label style="cursor:pointer;" data-type="show-profile" data-id='+ list.id +'  class="label label_name">' + list.full_name + '</label>';
                        ids += ',' + list.id;
                    }
                    i++;
                }
                else if (len > 2) {
                    if (j == 0) {
                        str += '&nbsp;tagged : <label style="cursor:pointer;" data-type="show-profile" data-id='+ list.id +'  class="label label_name">' + list.full_name + '</label>';
                        ids += list.id;
                    } else {
                        //"<label style='cursor:pointer;' data-type='show-profile' data-id=" +  list.id + " class='label_name'>" + list.full_name + '</label><div class=\'clearfix\'></div>';
                        other_name += "<label style='cursor:pointer;' data-type='show-profile' data-id='" +  list.id + "' class='label label_name'>" + list.full_name + '</label><div class=\'clearfix\'></div>';
                        l = parseInt(len) - parseInt(1);
                        if (j == l) {
                            str += 'and <label class="label label_name">';
                            str += '<a href="javascript:void(0);" data-html="true" data-trigger="focus" data-placement="bottom" data-toggle="popover1" title="Other Tagged" data-content="' + other_name + '">' + l + ' more</a>';
                            str += '</label>';
                        }
                        ids += ',' + list.id;
                    }
                    j++;
                }


            });
 

                notification_str += '<li><a href="student/tagged_feed/'+obj.notification_detail[0]['id']+'">';
                //notification_str += '<span> hello</span>';
                notification_str += '<div class="user_small_img"><img onerror="this.src=\'assets/images/avatar.png\'" src="uploads/' + obj.notification_detail[0]['profile_link'] + '"></div>';
                notification_str += '<div class="notification_txt">';
                notification_str += '<p><span class="noti_username">' + obj.notification_detail[0]['full_name'] + '</span> tagged you in a post</p>';
                notification_str += '<span class="noti_time just_noti just_now">Just now</span></div>';
                notification_str += '<div class="clearfix"></div>';
                notification_str += '</a></li>';
                var is_notify="";

                $.each(obj.tagged_id, function (index, list) {
                    if(wp == list)
                    {
                      is_notify = "true"
                    }
                });

                
                if (is_notify == "true"){

                    /* notification for the tagged user */

                        $.notify({
                                icon: 'uploads/' + obj.notification_detail[0]['profile_link'] + '',
                                title: '<strong>You tagged in post</strong> ',
                                message: 'Tagged by ' + obj.notification_detail[0]['full_name'] + ' '
                            },{
                                type: 'minimalist',
                                delay: 5000,
                                icon_type: 'image',
                                template: '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert">' +
                                    '<img data-notify="icon" class="img-circle pull-left">' +
                                    '<span data-notify="title">{1}</span>' +
                                    '<span data-notify="message">{2}</span>' +
                                '</div>'
                            });


                    $('.mCSB_container .three_tabs #notification-panel #no-more-notification').remove().html();
                    $('.mCSB_container .three_tabs #notification-panel').prepend(notification_str);

                    $('.just_now').timestatus(obj.notification_detail[0]['created_date']);
                    notification_length = $('.mCSB_container .three_tabs #notification-panel li').length;
                    if (notification_length == 0) {
                        notification_length = $('.mCSB_container .three_tabs #notification-panel').prepend('<li><div class="notification_txt">No more notification</div></li>');
                        $('.mCSB_container .three_tabs .dropdown .badge').html(0);
                    }
                    else {
                        $('.mCSB_container .three_tabs .dropdown .badge').html(notification_length);
                    }
                }

        

            $('#all_feed div.box.feeds[data-id="' + obj.fid + '"] .feed_text span[data-id="' + obj.fid + '"]').html(str);


            already = '';
            i = 0;
            $.each(obj.already_tagged_detail, function (index, list) {
                if (i == 0)
                    already += list.full_name;
                else
                    already += '<br>' + list.full_name;
                i++;
            });
            if (already != '') {
                if (wp == obj.id) {
                    $(".alert_notification p").html(already + ' are already tagged');
                    $(".alert_notification").show().delay(7000).fadeOut();
                }
            }
        } else if (obj.type == 'study_mate_search' || obj.type == "load-studymate-more") {
            str = '';
            $('a[data-type="load-studymate-more"]').remove().html();
            $.each(obj.result, function (index, list) {
                str += '<div class="study_mate">';
                str += '<div class="col-lg-9 col-md-8 col-sm-7">';
                str += '<div class="mate_user_img">';
                str += '<img style="cursor:pointer;" data-type="show-profile" data-id="' + list.user_id + '" onerror="this.src=\'assets/images/avatar.png\'" src="uploads/' + list.profile_link + '">';
                str += '</div><h4 class="hover_search" class="search_mate" style="cursor:pointer;" data-type="show-profile" data-id="' + list.user_id + '">' + list.full_name + '</h4>';
                str += '<p>' + list.school_name + '</p>';
                str += '<p class="no_hover" class="txt_green">' + list.course_name + '</p>';
                str += '</div>';
                str += '<div class="col-lg-3 col-md-4 col-sm-5">';
                if (list.srid != '' && list.srid != null) {
                    str += ' <button class="btn btn_black_normal btn-block" data-type="studyment-request" data-id="' + list.user_id + '" disabled>Request Already Sent</button>';
                } else {
                    str += '<button class="btn btn_green btn-block" data-type="studyment-request" data-id="' + list.user_id + '">Add Studymate</button>';
                }
                str += ' </div>';
                str += ' <div class="clearfix"></div>';
                str += '</div>';
                i++;

            });
            if (obj.result.length >= 3) {
                str += '<div class="text-center">';
                str += '<a href="javascript:void(0);" data-start="' + obj.limit + '" data-type="load-studymate-more" class="search_result_label">';
                str += 'View More</a>';
                str += '</div>';
            }
            else {
                str += '<div class="text-center">';
                str += '<label class="txt_grey txt_red">no more studymates</label>';
                str += '</div>';
            }

            if (obj.type == "load-studymate-more") {
                $('.search_studymate  div[data-type="search_result"]').append(str);
            } else {
                $('.search_studymate  div[data-type="search_result"]').html(str);
            }
        } else if (obj.type == "load-activity-more") {
            var gender = "";
                if(obj.gender.toLowerCase() == "male")
                {
                    gender = "his";
                }else
                {
                    gender = "her";
                }

            str = '';
            if (obj.result.my_like.length > 0 || obj.result.my_comment.length > 0 || obj.result.my_studymate.length > 0 || obj.result.my_post.length > 0 || obj.result.my_topic.length > 0)
            {
                str += '<div class="clearfix"></div>';
                str += '<div class="divide_discussion">';
                str += '<hr><h4>' + obj.format_month + '</h4>';
                str += '</div>';
                str += '<div class="clearfix"></div>';
                t = 0;

                $.each(obj.result.my_topic, function (index, list) {
                    if(list.topic_name != null)
                    {
                    str += '<div class="topic_allocated">';
                    if (t == 0) {
                        str += '<h4 class="activity_heading">Topic Allocated</h4>';
                    }
                    str += '<div class="topic_div">';
                    str += '<h4>' + list.topic_name + '</h4>';
                    str += '<div>';
                    str += '<div><strong>Discussion</strong><p>' + list.total_discussion + ' Comments</p></div>';
                    str += '<div><strong>Discussion - Score</strong><p>Score : ' + list.discussion_score + '</p></div>';
                    str += '<div><strong>Examination - Quiz</strong><p>Percentage : ' + list.per + '%</p></div>';
                    str += '</div>';
                    str += '<div class="clearfix"></div>';
                    str += '</div>';
                    str += '</div>';
                    t++;
                  }
                });
                s = 0;
                $.each(obj.result.my_studymate, function (index, list) {

                    str += '<div class="studymate_with">';
                    if (s == 0) {
                        str += '<h4 class="box_header">Became studymate with</h4>';
                    }
                    str += '<span class="date">'+list.created_date+'</span>';
                    str += '<div class="study_mate">';
                    str += '<div style="cursor:pointer;" data-type="show-profile" data-id="'+ list.mate_of +'" class="mate_user_img">';   
                    str += '<img src="uploads/' + list.profile_link + '" class="mCS_img_loaded" onerror="this.src=\'assets/images/avatar.png\'">';
                    str += '</div>';
                    str += '<h4 style="cursor:pointer;" data-type="show-profile" data-id="'+ list.mate_of +'">' + list.full_name + '</h4>';
                    str += '<p>' + list.school_name + '</p>';
                    str += '<p class="txt_grey">' + list.course_name + '</p>';
                    str += '</div>';
                    str += '</div>';
                    s++;
                });

                var cnt_like = 0;
                $.each(obj.result.my_like, function (index, list) {
                    str += '<div class="status_like">';
                        if(cnt_like == 0)
                        {
                           str +='<h4 class="box_header">Status liked</h4>'
                        }
                        cnt_like++;
                    if(wp == list.l_id)
                        {
                           str += '<h4 class="activity_heading"><span style="cursor:pointer;" data-type="show-profile" data-id="'+ list.l_id +'" class="txt_green">' + list.post_username + '</span> Liked '+ gender +' own status.</h4>';
                        }else
                        {
                             str += '<h4 class="activity_heading">Liked status of <span style="cursor:pointer;" data-type="show-profile" data-id="'+ list.l_id +'" class="txt_green">' + list.post_username + '</span></h4>';
                        }
                    str += '<span class="date">'+list.created_date+'</span>';
                    str += '<div class="feed_text">';
                    str += '<p>' + list.feed_text + '</p>';
                    if (list.image_link != '' && list.image_link != null) {
                        str += '<div class="shared_images">';
                        str += '<div>';
                        if(list.feed_type == "doc")
                        {
                         str += '<a href="uploads/' + list.image_link + '" target="_BLANK" class="fancybox"> <img width="100" height="70" src="assets/images/default_chat.png" class="mCS_img_loaded"></a>';
                        }else
                        {
                            str += '<a href="uploads/' + list.image_link + '" class="fancybox"> <img src="uploads/' + list.image_link + '" width="100" height="70" class="mCS_img_loaded"></a>';
                        }
                        str += '</div></div>';
                    }
                    str += '</div>';
                    str += '<div class="clearfix"></div>';
                    str += '</div>';
                });
                // display my comment
                var cnt_comment = 0;
                $.each(obj.result.my_comment, function (index, list) {
                    str += '<div class="commented_on">';
                    if(cnt_comment == 0)
                    {
                       str +='<h4 class="box_header">Commented on</h4>'
                    }
                    cnt_comment++;
                    if(wp == list.uid)
                    {
                        str += '<h4 class="activity_heading">Commented on '+gender+' own post</h4>';
                    }else
                    {
                        str += '<h4 class="activity_heading">Commented on</h4>';
                    }
                    str += '<div class="feeds">';
                    str += '<div class="user_small_img">';
                    str += '<img style="cursor:pointer;" data-type="show-profile" data-id="'+ list.uid +'" onerror="this.src=\'assets/images/avatar.png\'" src="uploads/' + list.profile_link + '">';
                    str += '</div>';
                    str += '<div class="feed_text">';
                    str += '<h4 style="cursor:pointer;" data-type="show-profile" data-id="'+ list.uid +'">' + list.full_name + '</h4>';
                    str += '<span class="date">'+list.created_date+'</span>';
                    str += '<div class="clearfix"></div>';
                    str += '<p>' + list.feed_text + '</p>';

                    if (list.image_link != '' && list.image_link != null) {
                        str += '<div class="shared_images">';
                        str += '<div>';
                         if(list.feed_type == "doc")
                        {
                         str += '<a href="uploads/' + list.image_link + '" target="_BLANK" class="fancybox"> <img width="100" height="70" src="assets/images/default_chat.png" class="mCS_img_loaded"></a>';
                        }else
                        {
                            str += '<a href="uploads/' + list.image_link + '" class="fancybox"> <img src="uploads/' + list.image_link + '" width="100" height="70" class="mCS_img_loaded"></a>';
                        }
                        str += '</div></div>';
                    }
                    str += '<div class="clearfix"></div>';

                    if (list.totcomment > 1) {
                        str += '<a href="javascript:void(0);" data-type="view-all-comment-activities" data-id="' + list.id + '">View All</a>';
                    }
                    str += '</div>';
                    str += '<div class="clearfix"></div>';
                    str += '<div class="comment" data-id="' + list.id + '">';
                    str += '<div class="user_small_img user_comment">';
                    str += '<img style="cursor:pointer;" data-type="show-profile" data-id="'+ list.comment_by +'"  onerror="this.src=\'assets/images/avatar.png\'" src="uploads/' + obj.profile_link + '">';
                    str += '</div>';
                    str += '<div class="notification_txt">';
                    str += '<p><a style="cursor:pointer;" data-type="show-profile" data-id="'+ list.comment_by +'"  href="javascript:void(0);" class="noti_username">' + obj.full_name + '</a>&nbsp;' + list.comment + '</p>';
                    str += '<span class="noti_time">'+list.comment_date+'</span>';
                    str += '</div>';
                    str += '<div class="clearfix"></div>';
                    str += '</div>';
                    str += '</div>';
                    str += '</div>';
                });
                p = 0;
                $.each(obj.result.my_post, function (index, list) {
                    str += '<div class="status_like">';
                    if (p == 0) {
                        str += '<h4 class="activity_heading">Status updated</h4>';
                    }
                    str += '<span class="date">'+list.created_date+'</span>';
                    str += '<div class="feed_text">';
                    str += '<p><a class="noti_username" style="cursor:pointer;" data-type="show-profile" data-id="' + obj.id + '">' + obj.full_name + '</a><strong>updated ' + gender + ' status: </strong>' + list.feed_text + '</p>';
                  
                    if (list.image_link != '' && list.image_link != null) {
                        str += '<div class="shared_images">';
                        str += '<div>';
                         if(list.feed_type == "doc")
                        {
                         str += '<a href="uploads/' + list.image_link + '" target="_BLANK" class="fancybox"> <img width="100" height="70" src="assets/images/default_chat.png" class="mCS_img_loaded"></a>';
                        }else
                        {
                            str += '<a href="uploads/' + list.image_link + '" class="fancybox"> <img src="uploads/' + list.image_link + '" width="100" height="70" class="mCS_img_loaded"></a>';
                        }
                        str += '</div></div>';
                    }
                    str += '</div>';
                    str += '</div>';
                    p++;
                });
                $('a[data-type="load-activity-more"]').html('View More');
                $('div[data-type="activity-main"] div[data-type="activity-sub-main"] div[data-type="activity"] div[data-type="activity-body"] div[data-type="activity-sub-body"]').append(str);
                $('a[data-type="load-activity-more"]').attr('data-month', obj.new_month);
            }
            else {
                $('div[data-type="no-more"]').html('<lable class="txt_grey txt_red">no more activities</label>');
                $('a[data-type="load-activity-more"]').attr('value', 'no more activities');
            }
        }
        else if (obj.type == "show_profile") {
            $('#view_profile_model div[data-type="profile_pic"]').html('<img onerror="this.src=\'assets/images/avatar.png\'" src="uploads/' + obj.result.profile_link + '">');
            $('#view_profile_model h3[data-type="user-name"]').html(obj.result.full_name);
            $('#view_profile_model p[data-type="course-name"]').html('<span class="fa fa-graduation-cap"></span>' + obj.result.course_name);
            if (obj.result.birthdate == null)
                bdate = '-';
            else
                bdate = obj.result.birthdate;

            $('#view_profile_model p[data-type="birth"]').html('<span class="fa fa-birthday-cake"></span>' + bdate);
            $('#view_profile_model p[data-type="school"]').html('<span class="fa fa-university"></span>' + obj.result.school_name);
            $('#view_profile_model p[data-type="email"]').html('<span class="fa fa glyphicon glyphicon-envelope"></span><i>' + obj.result.email_id + '</i>');
            if($.inArray(obj.result.id,obj.my_studymate_list)!== -1){
                $('#mate_or_not').html('<button disabled class="btn btn_green btn-block">Studymate</button>');
            }else{
                if(obj.already == 'yes'){
                    $('#mate_or_not').html('<button class="btn btn_black_normal" disabled="" data-type="studyment-request">Request Already Sent</button>');
                }else{
                    $('#mate_or_not').html('<button class="btn btn_green" data-id="'+obj.result.id+'" data-type="studyment-request">Add Studymate</button>');
                }
            }

            $('#view_profile_model').modal('show');
        }
        else {

            alert('Message Not Catched!!');
        }
    };
    ws.onclose = function ()
    {
        // alert('Disconnected from Server!');
    };
}

function myfunction(from){
    var str = '';
    var id = from;
    var len = $('.chat_container .chat').length;
    var j = 3;
    var is_needed = true;
    var cls;
    for (var i = 1; i <= len; i++) {
        if ($(".chat_container .chat:nth-child(" + i + ")").data('id') == id) {
            if($(".chat_container .chat[data-id="+id+"]").hasClass('chat_3'))
                cls = 'chat_3';
            if($(".chat_container .chat[data-id="+id+"]").hasClass('chat_2'))
                cls = 'chat_2';
            if($(".chat_container .chat[data-id="+id+"]").hasClass('chat_1'))
                cls = 'chat_1';
            $(".chat_container .chat[data-id="+id+"] .chat_header").addClass("blinking");
            $(".chat_header").removeClass("blink");
            blink(".blinking", cls, 2000);
            // blink(".chat_container .chat[data-id="+id+"] .chat_header", cls, 1000);
            is_needed = false;
        }
    }
        if(len==1)
        j=3;
    else if(len==2)
        j=2;
    else if(len==3)
        j=1;
    if (len >= 4 && is_needed == true) {
        $(".chat_container .chat_1").remove();
        j=1;
    }
    var stm;

    

   /* for (var i = 1; i <= len; i++) {
        console.log("2nd for i: "+i);
        if ($(".chat_container .chat:nth-child(" + i + ")").data('id') != id && j > 0) {
        console.log("2if");
            $(".chat_container .chat:nth-child(" + i + ")").attr('class', 'chat passive chat_' + j);
        console.log("chat_"+j);
        j--;
}
}*/

    if(is_needed == true){
        stm = $('#mate_list[data-id="' + id + '"]');
            // console.log($(".chat_container .chat:nth-child(" + i + ")"));
            if(len==0)
                str += '<div class="chat active" data-id="' + id + '">';
            else
                str += '<div class="chat passive chat_' + j+'" data-id="' + id + '">';

            str += '<div class="chat_header"><div class="chat_img_holder" data-id="'+id+'" style="cursor:pointer;">';
            str += '<img src="' + stm.children('div').children('img').attr('src') + '">';
            str += '</div><p class="chat_name" data-id="'+id+'" style="cursor:pointer;">' + stm.children('p').html() + '</p>';
            str += '<a href="javascript:void(0);" data-type="close" data-id="' + id + '"><span class="close" >x</span></a></div>';
            str += '<div class="chat_text"></div>';
            str += '<span class="chat_typing"></span>';
            str += ' <img class="chat_loading" src="assets/images/progress_bar_sm.gif" style="display:none">';
            str += '<input type="text" class="chat_input" placeholder="Say It" data-type="chat" data-id="' + id + '">';
            str += '<!--<a href="#" class="icon icon_emoji"></a> -->';
            str += '<a href="#" class="icon icon_pin"></a>';
            str += '<input type="file" id="chat_file_share" class="chat_pin" data-type="single_chat_file" data-id="16">';
            str += '</div>';

            $('#chat_container').append(str);
            $("#chat_container .chat[data-id='" + id + "'] .chat_text")
                .mCustomScrollbar({
                    theme: "minimal-dark"
                }).delay(300);
        var request = {
            type: 'get_latest_message',
            to: 'self',
            my_id: id
        };
        ws.send(JSON.stringify(request));

        $(".chat_"+j+" .chat_header").addClass("blinking");
        $(".chat_header").removeClass("blink");
        blink(".blinking", 'chat_'+j, 2000);
        // blink(".chat_"+j+" .chat_header", 'chat_'+j, 1000);

        ws.send(JSON.stringify(request));
        }
        
        
    
         /*   j--;
        }
        }*/

   // }
/*
    if (is_needed == true) {
        str += '<div class="chat active" data-id="' + id + '">';
        str += '<div class="chat_header"><div class="chat_img_holder" data-type="show-profile" data-id="'+id+'" style="cursor:pointer;">';
        str += '<img src="' + $(this).children('div').children('img').attr('src') + '">';
        str += '</div><p class="chat_name" data-type="show-profile" data-id="'+id+'" style="cursor:pointer;">' + $(this).children('p').html() + '</p>';
        str += '<a href="javascript:void(0);" data-type="close" data-id="' + id + '"><span class="close" >x</span></a></div>';
        str += '<div class="chat_text"></div>';
        str += ' <img class="chat_loading" src="assets/images/progress_bar_sm.gif" style="display:none">';
        str += '<input type="text" class="chat_input" placeholder="Say It" data-type="chat" data-id="' + id + '">';
        str += '<!--<a href="#" class="icon icon_emoji"></a> -->';
        str += '<a href="#" class="icon icon_pin"></a>';
        str += '<input type="file" id="chat_file_share" class="chat_pin" data-type="single_chat_file" data-id="16">';
        str += '</div>';
        $('#chat_container').append(str);
        $("#chat_container .chat[data-id='" + id + "'] .chat_text")
                .mCustomScrollbar({
                    theme: "minimal-dark"
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

}*/
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
        $('.chat[data-id="' + wp + '"] .chat_loading').fadeIn(100);
        ws.send(JSON.stringify(request));
        $(this).val('');
    }
    if ($(this).val().length % 2 == 0 && e.keyCode != 13) {

        var request = {
            type: 'chat_type',
            to: $(this).data('id'),
            message: 'Typing..'
        };
        ws.send(JSON.stringify(request));
    }
});

/*chat file upload */
$(document).on("change", "#chat_file_share", function(evt)
{
        var files = evt.target.files;
        var file = files[0];
        var user = $(this).data('id');
        var types = $(this).data('type');
        var type_of_data = this.files[0].type;
        var file_name = this.files[0].name;

        if (this.files[0].size <= 1024 * 1024 * 10) {
            $('.chat[data-id="' + user + '"] .chat_loading').fadeIn(300);
            if (files && file) {
                var reader = new FileReader();
                reader.onload = function (readerEvt) {
                    var binaryString = readerEvt.target.result;
                    var request = {
                        type: types,
                        name: file_name,
                        data_type: type_of_data,
                        data: btoa(binaryString),
                        to: user
                    }
                    ws.send(JSON.stringify(request));
                };
                reader.readAsBinaryString(file);
            }
        } else {

             $('#image_upload_model').addClass('in',{duration:500});
             $('#image_upload_model').css("display","block");
             $('#chat_file_share').val('').clone(true);
        }
    
});

/* Check user is online or not */
function set_status(id, status) {

    var ac = $('.stm .stm_list .mCustomScrollBox .mCSB_container .stm_item[data-id="' + id + '"]');
    if (status == false) {

        $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('online').addClass('offline');
        if (wp != id) {
            $('.stm_list .mCustomScrollBox .mCSB_container').append('<div class="' + ac.attr('class') + '" data-id="' + id + '">' + ac.remove().html() + '</div>');
            if (start_timer == true) {
                $('.tut_group .box_footer[data-id="' + id + '"] p').html('Offline');
                $('.tut_group .box_body[data-id="' + id + '"] a').removeClass('icon_call_user_disable').addClass('icon_call_user');
            }
        }
    } else {
        $('#mate_list[data-id="' + id + '"]').parent('div').removeClass('offline').addClass('online');
        if (wp != id) {
            $('.stm_list .mCustomScrollBox .mCSB_container').prepend('<div class="' + ac.attr('class') + '" data-id="' + id + '">' + ac.remove().html() + '</div>');
            if (start_timer == true) {
                $('.tut_group .box_footer[data-id="' + id + '"] p').html('Online');
                $('.tut_group .box_body[data-id="' + id + '"] a').removeClass('icon_call_user').addClass('icon_call_user_disable');
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

    if (len >= 4 && is_needed == true) {
        $(".chat_container .chat_1").remove();
    }

    for (var i = 1; i <= len; i++) {
        if ($(".chat_container .chat:nth-child(" + i + ")").data('id') != id && j > 0) {
            $(".chat_container .chat:nth-child(" + i + ")").attr('class', 'chat passive chat_' + j);
            j--;
        }

    }

    if (is_needed == true) {
        str += '<div class="chat active" data-id="' + id + '">';
        str += '<div class="chat_header"><div class="chat_img_holder" data-id="'+id+'" style="cursor:pointer;">';
        str += '<img src="' + $(this).children('div').children('img').attr('src') + '">';
        str += '</div><p class="chat_name" data-id="'+id+'" style="cursor:pointer;">' + $(this).children('p').html() + '</p>';
        str += '<a href="javascript:void(0);" data-type="close" data-id="' + id + '"><span class="close" >x</span></a></div>';
        str += '<div class="chat_text"></div>';
        str += '<span class="chat_typing"></span>';
        str += ' <img class="chat_loading" src="assets/images/progress_bar_sm.gif" style="display:none">';
        str += '<input type="text" class="chat_input" placeholder="Say It" data-type="chat" data-id="' + id + '">';
        str += '<!--<a href="#" class="icon icon_emoji"></a> -->';
        str += '<a href="#" class="icon icon_pin"></a>';
        str += '<input type="file" data-toggle="tooltip" title="Attach file" id="chat_file_share" class="chat_pin" data-type="single_chat_file" data-id="'+id+'">';
        str += '</div>';
        $('#chat_container').append(str);
        $("#chat_container .chat[data-id='" + id + "'] .chat_text")
                .mCustomScrollbar({
                    theme: "minimal-dark"
                }).delay(300);
        var request = {
            type: 'get_latest_message',
            to: 'self',
            my_id: id
        };
        $('.chat_input').focus();
        ws.send(JSON.stringify(request));

    } else {
        $("#chat_container .chat[data-id='" + id + "']").attr('class', 'chat active');
        $("#chat_container .chat[data-id='" + id + "'] .chat_input").focus();
          var request = {
            type: 'get_latest_message',
            to: 'self',
            my_id: id
        };
        ws.send(JSON.stringify(request));

    }
    $(this).children('span').html('');
    setTimeout(function(){
       // $(".chat_input").focus();
        $('.chat_text').mCustomScrollbar('update');
        $('.chat_text').mCustomScrollbar('scrollTo', "bottom");
    }, 300);
        // $('.chat_text').html('');
    //console.log($('.chat_text').mCustomScrollbar('scrollTo', 'bottom'));
});

/* Send Feed Post */
$(document).on('click', 'button[data-type="post"]', function () {
    if ($.trim($('#feed_post').val()) != '') {
        var request = {
            type: 'post',
            to: 'all',
            feed: 'normal',
            tagged_id: $('#tagged-users-id').val(),
            message: $('#feed_post').val()
        };
        ws.send(JSON.stringify(request));
        $('#feed_post').val('');
        $('#selection-box').hide();
        $(".js-example-basic-single").select2("val", "");
        $("#tagged-users").hide();
        $('#tag_or_not').val('no');
    }
});


/* Edit post */

$(document).on('click', '.btn_green[data-type="save-edited-feed"]', function () {
     var a = $(this).attr("data-id");
     var feed = $('#edit_feed_post[data-feed="' + a + '"]').val();
    if ($.trim($('#edit_feed_post').val()) != '') {
        var request = {
            type: 'edit_post',
            to: 'all',
            feed: 'normal',
            feed_id: a,
            message: feed
        };
        ws.send(JSON.stringify(request));
        $('#all_feed .box[data-id="' + a + '"] .feed_text p').css("display","");
        $('#all_feed .box div#save_edited_feed[data-id="' + a + '"]').css("display","none");
        $('#all_feed .box .feed_text #edit_feed_post[data-feed="' + a + '"]').css("display","none");
        $('#all_feed .box[data-id="' + a + '"] .feed_text p').text(feed);
    }

});


/* Send comment */
$(document).on('keypress', '#all_feed .box.feeds .write_comment input[data-type="feed_comment"]', function (e) {

    var myTextareaVal = $(this).val();
    var myComment = myTextareaVal.replace(/\s\s+/g, '\n\n');
   
    if (e.keyCode == 13 && $.trim($(this).val()) != '') {
        var request = {
            type: 'feed_comment',
            to: $(this).data('id'),
            message :myComment
            // message :myComment
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

/* Generate HTML block of Feed Post. */
function generate_post(obj, status) {
    var cls = '';
    if (obj.my_like != 0) {
        cls = '_0';
    }

    /* feeder id when post or load more */
    if(obj.type == "post")
    {
        var p_id = obj.id;
    }else
    {
        var p_id = obj.feed_by;
    }

    str = '<div class="box feeds" data-id="' + obj.post_id + '">';
    str += '<div class="user_small_img">';
    str += '<img style="cursor:pointer;" onerror="this.src=\'assets/images/avatar.png\'" data-type="show-profile" data-id="' + p_id + '" src="uploads/' + obj.profile_link + '">';
    str += '</div>';
    str += '<div class="feed_text">';
    str += '<h4 style="cursor:pointer;" data-type="show-profile" data-id="' + p_id + '">' + obj.full_name + '</h4>';

    len = obj.tagged_detail.length;
    name = '';
    if (len > 0) {
        i = 0;
        j = 0;
        k = 0;
        other_name = '';
        notification_str = '';
        $.each(obj.tagged_detail, function (index, list) {
            if (len == 1) {
                name += '&nbsp;tagged : <label style="cursor:pointer;" data-type="show-profile" data-id='+ list.id +' class="label label_name">' + list.full_name + '</label>';
            }
            else if (len == 2) {
                if (i == 0) {
                    name += '&nbsp;tagged : <label style="cursor:pointer;" data-type="show-profile" data-id='+ list.id +'  class="label label_name">' + list.full_name + '</label>';
                } else {
                    name += '&nbsp;and : <label style="cursor:pointer;" data-type="show-profile" data-id='+ list.id +'  class="label label_name">' + list.full_name + '</label>';
                }
                i++;
            }
            else if (len > 2) {
                if (j == 0) {
                    name += '&nbsp;tagged : <label style="cursor:pointer;" data-type="show-profile" data-id='+ list.id +'  class="label label_name">' + list.full_name + '</label>';
                } else {
                    other_name += "<label style='cursor:pointer;' data-type='show-profile' data-id=" +  list.id + " class='label_name'>" + list.full_name + '</label><div class=\'clearfix\'></div>';
                    l = parseInt(len) - parseInt(1);
                    if (j == l) {
                        name += '&nbsp;and <label class="label label_name"><a href="javascript:void(0);" data-html="true" data-id="'+obj.post_id+'" data-trigger="focus" data-placement="bottom" data-toggle="popover2" title="Other Tagged" data-content="' + other_name + '">' + l + ' more</a>';
                        name += '</label>';
                    }
                }
                j++;
            }

            if(obj.feed == "normal" && list.id == wp)
                {
                   

                notification_str += '<li><a href="student/tagged_feed/'+obj.notification_detail[0]['id']+'">';
               // notification_str += '<span>hello</span>';
                notification_str += '<div class="user_small_img"><img onerror="this.src=\'assets/images/avatar.png\'" src="uploads/' + obj.profile_link + '"></div>';
                notification_str += '<div class="notification_txt">';
                notification_str += '<p><span class="noti_username">' + obj.full_name + '</span> tagged you in a post</p>';
                notification_str += '<span class="noti_time just_noti just_now">Just now</span></div>';
                notification_str += '<div class="clearfix"></div>';
                notification_str += '</a></li>';

                    $('.mCSB_container .three_tabs #notification-panel #no-more-notification').remove().html();
                    $('.mCSB_container .three_tabs #notification-panel').prepend(notification_str);

                    $('.just_now').timestatus(obj.posted_on);
                    notification_length = $('.mCSB_container .three_tabs #notification-panel li').length;
                    if (notification_length == 0) {
                        notification_length = $('.mCSB_container .three_tabs #notification-panel').prepend('<li><div class="notification_txt">No more notification</div></li>');
                        $('.mCSB_container .three_tabs .dropdown .badge').html(0);
                    }
                    else {
                        $('.mCSB_container .three_tabs .dropdown .badge').html(notification_length);
                    }

                }


        });
    }
    str += '<span data-id="' + obj.post_id + '">' + name + '</span>';

    options = '';
    
    var result = new Array();
    var result1 = new Array();
    $.each(obj.studymates_detail, function (index, study_list) {
       result.push({'id':study_list.id,'full_name':study_list.full_name});
    });


    result1 = result;
    tagged = obj.tagged_detail;
    var ids = new Array();
    for (var i = 0; i<result1.length; i++) {
        var id = result1[i].id;
        for (var j = 0; j<tagged.length; j++) {
            var tagged_id = tagged[j].id;
            if(id==tagged_id){
                ids.push(id);
            }
        }
        
    }
     
    for(i=0;i<ids.length;i++){
        for (var j = 0; j<result1.length; j++) {
            if(ids[i]==result1[j].id){
                result1.splice(j,1);
            }
        }
    }
    
    //console.log("hi");
    for (var i = 0; i<result1.length; i++) {
        options += '<option value="' + result1[i].id + '">' + result1[i].full_name + '</option>';
    };
    var feed_msg = obj.message;
     
    var total_comment="";
    str += '<span class="date noti_time just_now"></span>';
    str += '<div class="clearfix"></div>';
    str += '<textarea id="edit_feed_post" type="text" data-feed="' + obj.post_id + '" class="form-control post_input" style="display:none" placeholder="SAY IT">' + feed_msg + '</textarea>';
    str += '<div style="float:right;display:none;margin-top:5px;" id="save_edited_feed" data-id="' + obj.post_id + '">';       
    str += '<a style="color:white" href="javascript:void(0);" class="btn btn-xs btn_black_normal" data-type="cancel-edited-feed" data-id="' + obj.post_id + '">Cancel</a><span> &nbsp; </span>';
    str += '<a  style="color:white" href="javascript:void(0);" class="btn btn-xs btn_green" data-type="save-edited-feed" data-id="' + obj.post_id + '">Save</a>';
    str += '</div>';
    str += '<div class="clearfix"></div>';
    str += '<p>' + feed_msg.replace(/\n/g,"<br>") + '</p>';
    str += '<a href="javascript:void(0);" class="like_btn" data-type="feed-like" data-id="' + obj.post_id + '"><span title="Like" class="icon icon_thumb' + cls + '"></span>' + obj.tot_like + '</a>';
        if(obj.tot_comment > 4)
        {
            total_comment = "4";
            str += '<a href="javascript:void(0);" data-id="' + obj.post_id + '" class="comment_btn"><span class="icon icon_comment" title="Comment"></span><span>' + total_comment + '</span> of ' + obj.tot_comment +'</a>';
        }else
        {
             total_comment = obj.tot_comment;
             str += '<a href="javascript:void(0);" data-id="' + obj.post_id + '" class="comment_btn"><span class="icon icon_comment" title="Comment"></span>' + total_comment +'</a>';
        }
    
    if (typeof (obj.comment) != 'undefined') {
        if (obj.comment.length > 2) {
            str += '<a href="javascript:void(0);" data-type="showall" data-id="' + obj.post_id + '">View All</a>'
        }
    }else
    {
             str += '<a href="javascript:void(0);" class="comment_showall" data-type="showall" data-id="' + obj.post_id + '" style="display:none">View All</a>'
    }

    var show_tagged = "inline-block";
    if(result1.length == 0)
    {
        show_tagged = "none";
    }

    str += '<div class="dropdown tag_user user_'+obj.post_id+'" style="display: '+show_tagged+';">';
    str += '<a href="javascript:void(0);" class="dropdown-toggle" data-type="tag-again" data-id="' + obj.post_id + '" aria-haspopup="true" aria-expanded="true"><span class="icon icon_user_2"></span><span class="caret"></span></a>';
    str += '</div>';

     var arr_posted_on = Array();
     var is_editable = "";
     arr_posted_on = obj.posted_on.split(" ");
     if(/^[0-9]+$/.test(arr_posted_on[0]) && wp == p_id ||  arr_posted_on[0] == "Just" && wp == p_id)
     {
       is_editable = "yes";
        if(obj.feed_type == 'media' || obj.feed_type == 'doc')
            {
                 is_editable = "";
            }
     }


    if(is_editable == "yes"){ 
        str += '<a href="javascript:void(0);" data-feed="'+obj.post_id+'" class="edit_post_icon"><i title="Edit Post" class="fa fa-pencil-square-o"></i></a>';
    }
    str += '</div>';
    str += '<div style="float:right;display:none;" id="show-again" data-id="' + obj.post_id + '">';
    str += '<select style="width:200px;" name="all_users_again[]" id="' + obj.post_id + '" data-id="' + obj.post_id + '" class="js-example-basic-single form-control" multiple="multiple">';
    str += options;
    str += '</select>';
    str += '<a href="javascript:void(0);" class="btn btn_black_normal" data-type="tag-user-again" data-id="' + obj.post_id + '">Tag New</a>';
    str += '</div>';
    str += '<div class="clearfix"></div>';
    str += '<div id="feed_comments" data-id="' + obj.post_id + '" data-type="instance_post1"></div>';
    str += '<div class="write_comment box_body">';
    str += '<input type="text" class="form-control" placeholder="Write Your Comment Here" data-type="feed_comment" data-id="' + obj.post_id + '">';
    str += '</div>';
    str += '</div>';

    if (status == true) {
        $("#all_feed").prepend(str);
    } else {
        $("#all_feed").append(str);
    }
   

    $("#" + obj.post_id).select2();
    $("#all_feed .box.feeds[data-id='" + obj.post_id + "']").fadeOut(0).fadeIn(1000);
     $(".just_now").timestatus(obj.posted_on);
    if (typeof (obj.comment) != 'undefined') {
        i = 0;
        $.each(obj.comment, function (index, comment_list) {
            generate_comment(comment_list, i, true);
            i++;
        });
    }


}


/* Function for today date into Today and yesterday  into yesterday */
function date_to_day(post_date){
    var cur_date = new Date(post_date);
    var today = new Date();
    var sys_pre_date = new Date();
    sys_pre_date.setDate(today.getDate() - 1);
    var cur_date1 = cur_date.toDateString();
    var today1 = today.toDateString();
    var sys_pre_date1 = sys_pre_date.toDateString();

    var m_names = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
    if(cur_date1 == today1)
    {
        return "Today"
    }else if(cur_date1 == sys_pre_date1)
    {
        return "Yesterday"
    }else
    {
        var d = cur_date.getDate();
        var m = cur_date.getMonth();
        var y = cur_date.getFullYear();
        return m_names[m] + "  " + d + ", " + y;
   }
}


/* Generate HTML block of feed comment. */
function generate_comment(obj, i, k) { 
/* commenter id for feed post and load more, else part sets commeter id when event on load more */
     if(!obj.comment_by){
       var commenter_id = obj.id;
    }else
    {
         var commenter_id = obj.comment_by;
    }

    str = "";
    var msg = obj.message;
    i = typeof i !== 'undefined' ? i : 0;
    k = typeof k !== 'undefined' ? k : false;
    if (parseInt(i) > 3) {
        display = 'display:none !important';
        first_three = '';
    }
    else {
        display = '';
        first_three = 'true';
    }

    str += '<div class="comment" style="' + display + '" data-first="' + first_three + '" data-id="' + obj.to + '">';
    str += '<div class="user_small_img user_comment">';
    str += '<img style="cursor:pointer;" data-type="show-profile" data-id="'+ commenter_id +'" src="uploads/' + obj.profile_link + '" onerror="this.src=\'assets/images/avatar.png\'">';
    str += '</div>';
    str += '<div class="notification_txt">';
    str += '<p style="cursor:pointer;" data-type="show-profile" data-id="'+ commenter_id +'"><a  class="noti_username">' + obj.full_name + ' </a> ' + msg.replace(/\n/g,'<br/>') + '</p>';
    str += '<span class="noti_time just_now">' + obj.comment_date + '</span>';
    str += '</div>';
    str += '<div class="clearfix"></div>';
    str += '</div>';
    $('#all_feed .box.feeds[data-id="' + obj.to + '"] #feed_comments').append(str);
    $(".just_now").timestatus(obj.comment_date);
    if (k != true) {
        $('#all_feed .box.feeds[data-id="' + obj.to + '"] #feed_comments .comment:last-child').fadeOut(0).fadeIn(400);
    }
    
    if(k == false)
    {
    var comment_cols = $('#feed_comments[data-id="'+ obj.to +'"]').children().size();
    var is_instance_post = $('#feed_comments[data-id="'+ obj.to +'"]').attr('data-type');
    //var comment_cols2 = $('#feed_comments').children('.comment[data-id="'+obj.to+'"]').size();

    if(comment_cols < 4)
    {
        $('#feed_comments[data-id="'+ obj.to +'"]').attr('data-type','instance_post');
    }

    if(comment_cols > 4 && !is_instance_post && is_instance_post != "instance_post1")
            {   
                var n_th = comment_cols - 4;
                var shown_comment = parseInt($('.comment_btn[data-id="'+ obj.to +'"] span:nth-child('+'2'+')').text());
                if(!shown_comment)
                        {
                            shown_comment = 4;
                        }
                $('.comment_btn[data-id="'+ obj.to +'"]').html('<span class="icon icon_comment" title="Comment"></span><span>' + shown_comment + '</span> of ' + comment_cols);
                $('.comment_showall[data-id="' + obj.to + '"]').css("display", "");
            }
            else if(comment_cols > 4 && is_instance_post == "instance_post1")
                {
                var shown_comment = parseInt($('.comment_btn[data-id="'+ obj.to +'"] span:nth-child('+'2'+')').text());
                if(!shown_comment)
                        {
                            shown_comment = 4;
                        }
                    shown_comment++;
                $('.comment_btn[data-id="'+ obj.to +'"]').html('<span class="icon icon_comment" title="Comment"></span><span>' + shown_comment + '</span> of ' + comment_cols);
                $('.comment_showall[data-id="' + obj.to + '"]').css("display", "");
                    
                } else
                {
                    $('.comment_btn[data-id="' + obj.to + '"]').html("");
                    $('.comment_btn[data-id="' + obj.to + '"]').append('<span class="icon icon_comment" title="Comment"></span>'+comment_cols+'');
                }
    }
    else
    {

    }
}

/* load more feeds. */
$(document).on('click', 'button[data-type="load_more"]', function () {
    $('button[data-type="load_more"]').prop('disabled', true);
    var request = {
        type: 'load_more_feed',
        to: 'self',
        start: $(this).attr('data-start'),
        message: ''
    };
    ws.send(JSON.stringify(request));
});

// edit post
$(document).on('click', 'a.edit_post_icon', function () {
    var feed_id = $(this).data('feed');
    console.log(feed_id);

});


/* Submit group discussion comment. */
$(document).on('click', '.option_bar[data-type="discussion-submit"]', function () {
    if ($.trim($('textarea[data-type="discussion"]').val()) != '') {
        var request = {
            type: 'discussion',
            to: 'all',
            message: $('textarea[data-type="discussion"]').val()
        };
        ws.send(JSON.stringify(request));
    }
});

/* Send tying event. */
$(document).on('keypress', 'textarea[data-type="discussion"]', function () {
    if ($(this).val().length % 2 == 0) {
        var request = {
            type: 'discussion-type',
            to: 'all',
            message: 'Typing..'
        };
        ws.send(JSON.stringify(request));
    }
});

/* Generate html block of group disscussion  comment */
function generate_cm(obj) {
    var cl_me = "";
    if (wp == obj.id)
        cl_me = "me";
    var pclass="";
    if(obj.in_active==1){
        pclass='class="active"';
    }
    str = "";
    str += '<div class="col-sm-12 ' + cl_me + '" data-id="' + obj.disscusion_id + '">';
    str += '<div class="mate_user_img">';
    str += '<img src="/uploads/' + obj.profile_link + '">';
    str += '</div>';
    str += '<div class="admin_question">';
    str += '<h4>' + obj.full_name + '<span>' + obj.cdate + '</span></h4>';
    str += '<p '+ pclass +'>' + obj.message + '</p>';
    str += '</div>';
    str += '</div>';
    if (obj.active_count != 'skip') {
        $('#active_comment_count').html(obj.active_count);
    }

    if (obj.group_score != 'skip') {
        $('#group_score_count').html(obj.group_score);
    }

    if (obj.my_score != 'skip') {
        $('#my_score_count').html(obj.my_score);
    }

    $('textarea[data-type="discussion"]').val('');
    $('.discussion #inner_x').append(str);
    $('.discussion #inner_x div[data-id="' + obj.disscusion_id + '"]').fadeOut(0).fadeIn(400);
}


/*
 *   KAMLESH POKIYA (KAP).
 *   Like / dislike POST.
 */
$(document).on('click', 'a[data-type="feed-like"]', function (e) {

    var request = {
        type: 'like',
        fid: $(this).data('id'),
        to: 'all',
        message: '',
    };
    ws.send(JSON.stringify(request));
    $(this).val('');
});
/* Weekday scroll in tutorial group. */
$(document).on('click', '.tut_weekdays li a[data-type="s"]', function (e) {
    var nav = $(this).attr('href');

    $('.discussion').mCustomScrollbar('scrollTo', nav);

    return false;
});
/*
 *   KAMLESH POKIYA (KAP).
 *   Action of studymate with remove or not.
 */
$(document).on('change', '#action_studymate', function () {
    val = $(this).val();
    if (val == 1) {
        $('button[data-type="close-studymate"]').attr('data-course', $(this).data('course')).attr('data-name', $(this).data('name')).attr('data-id', $(this).data('id')).attr('data-school', $(this).data('school')).attr('data-profile', $(this).data('profile'));
        $('b[data-type="close-studymate-name"]').html($(this).data('name'));
        $('#close_mate').modal('show');
    }
});

/*
 *   KAMLESH POKIYA (KAP).
 *   Remove studymate.
 */
$(document).on('click', 'button[data-type="close-studymate"]', function (e) {
    var request = {
        type: 'close_studymate',
        to: 'self',
        studymate_id: $(this).attr('data-id')
    };
   ws.send(JSON.stringify(request));
    window.location.reload(window.location.pathname);
    $('#mCSB_2 #mCSB_2_container div[data-id="' + $(this).attr('data-id') + '"]').remove().html();
    $('#mCSB_6 #mCSB_6_container div[data-id="' + $(this).attr('data-id') + '"]').remove().html();
    if ($('#mCSB_2 #mCSB_2_container .my_studymates .box.general_cred .study_mate').length == 0) {
        $('#mCSB_2 #mCSB_2_container .my_studymates .box.general_cred').html('<div class="study_mate"><center><label class="txt_grey txt_red">no studymate found</label></center></div>');
    }
//    str = '';
//    str += '<div class="suggested_mates_card">'
//    str += '<div class="mate_user_img"><img src="uploads/' + $(this).attr('data-profile') + '" onerror="this.src=\'assets/images/avatar.png\'"></div>';
//    str += '<div class="mate_descrip"><p class="mate_name">' + $(this).attr('data-name') + '</p>';
//    str += '<p class="mate_following">Folowing 34 Authers</p>';
//    str += '<p>' + $(this).attr('data-school') + '</p>';
//    str += '<p>' + $(this).attr('data-course') + '</p><button class="btn btn_green" data-id="' + $(this).attr('data-id') + '" data-type="studyment-request">Add Studymates</button></div></div>';
//    $('.box_body #carousel-studymate .carousel-inner #active-recomonded').append(str);
});
/* Send Request to search from dictionary... */
$(document).on('keypress', 'input[data-type="search-dictionary"]', function (e) {

    if (e.keyCode == 13 && this.value) {
        var request = {
            type: 'dictionary',
            to: 'self',
            keyword: this.value
        };
        ws.send(JSON.stringify(request));
        $('.dictionary_result .mCustomScrollBox .mCSB_container').html('<img class="pre_loader" src="assets/images/loader1.GIF">').fadeIn(300);
        $(this).attr('disabled', '');
    }

});
$(document).on('click', 'a[data-type="search-dictionary"]', function (e) {
    if ($(this).prev('input').val()) {
        var request = {
            type: 'dictionary',
            to: 'self',
            keyword: $(this).prev('input').val()
        };
        ws.send(JSON.stringify(request));
        $('.dictionary_result .mCustomScrollBox .mCSB_container').html('<img class="pre_loader" src="assets/images/loader1.GIF">').fadeIn(300);
        $(this).attr('disabled', '');
    }

});

/*
 *   KAMLESH POKIYA (KAP).
 *   Send studymate request.
 */
$(document).on('click', 'button[data-type="studyment-request"]', function (e) {
    var request = {
        type: 'send_studymate_request',
        to: $(this).attr('data-id'),
        studymate_id: $(this).attr('data-id'),
        error: ''
    };
    ws.send(JSON.stringify(request));
    $(this).removeClass('btn_green').attr('disabled', true).addClass('btn_black_normal').html('Request Already Sent');
});
$(document).on('click', 'a[data-type="view-all-comment-activities"]', function (e) {
    var request = {
        type: 'view-all-comment-activities',
        to: 'self',
        comment_id: $(this).attr('data-id'),
        error: ''
    };
    ws.send(JSON.stringify(request));
});
$(document).on('click', 'button[data-type = "decline-request"]', function (e) {
    var request = {
        type: 'decline-request',
        sub_type: $(this).data('subtype'),
        to: $(this).data('id'),
        studymate_id: $(this).data('id'),
        error: ''
    };
    ws.send(JSON.stringify(request));
});
$(document).on('click', 'button[data-type="save_and_next"]', function (e) {
    var request = {
        type: 'exam_answer',
        qustion_id: $(this).data('qid'),
        to: 'self'
    };
    ws.send(JSON.stringify(request));
});
/*
 *   KAMLESH POKIYA (KAP).
 *   Tag user.
 */
$(document).on('change', '#select-tag-user', function (e) {
    if (e.val != '') {
        var request = {
            type: 'get_studymate_name',
            to: 'self',
            studymate_id: e.val,
            error: ''
        };
        ws.send(JSON.stringify(request));
    }
    else {
        $('#tagged-users').html('');
    }
});
$(document).on('click', 'button[data-type="exam_start_request"]', function () {
    $(this).attr('disabled', 'disabled');
    var request = {
        type: 'exam_start_request',
        to: 'self',
    };
    ws.send(JSON.stringify(request));
});
/* Clear selection */
$(document).on('click', 'button[data-type="clear_responce"]', function () {
    exam_choice = 0;
    $('.ans_options label input[name="option"]').attr('checked', false);
});
/* set answer */
$(document).on('click', '.ans_options label input[name="option"]', function () {
    exam_choice = $(this).data('id');
});
/* Next question */
$(document).on('click', 'button[data-type="question_responce"]', function () {
    $('button[data-type="clear_responce"]').attr('disabled', 'disabled');
    $('button[data-type="question_responce"]').attr('disabled', 'disabled');
    exam_choice = $(".ans_options label input[name='option']:checked").data('id');

    if (exam_choice == 'undefined' || exam_choice == '' || exam_choice == null) {
        exam_choice = 0;
    }

    var question_id = $(this).attr('data-id');
    var next_question = 0;
    var next_question_no = 0;
    if ($('ul.ques_numbers li[data-id="' + question_id + '"]').next().length == 1) {
        next_question = $('ul.ques_numbers li[data-id="' + question_id + '"]').next().data('id');
        next_question_no = $('ul.ques_numbers li[data-id="' + question_id + '"]').next().children('a').data('no');
    }


    var request = {
        type: 'question_responce',
        to: 'self',
        time: time_spent_per_question,
        question_id: question_id,
        qno: next_question_no,
        next: next_question,
        exam_type: $(this).data('change'),
        answer: exam_choice,
        status: $(this).attr('data-status')
    }

    ws.send(JSON.stringify(request));
    exam_choice = 0;
});
$(document).on('click', 'a[data-type="get_question"]', function () {
    var request = {
        type: 'get_question',
        to: 'self',
        question_no: $(this).data('id'),
        exam_type: $(this).data('change'),
        qno: $(this).data('no')
    }
    ws.send(JSON.stringify(request));
});
$(document).on('click', 'button[data-type="end_exam"]', function () {
    var request = {
        type: 'end_exam',
        to: 'self',
        exam_type: $(this).data('change'),
    }
    ws.send(JSON.stringify(request));
});
$(document).on('click', 'button[data-type="class_exam_start_request"]', function () {
    $(this).attr('disabled', 'disabled');
    var request = {
        type: 'class_exam_start_request',
        to: 'self',
        exam_id: $(this).data('id')
    };
    ws.send(JSON.stringify(request));
});
/*
 *   KAMLESH POKIYA (KAP).
 *   Tag studymate post.
 */
$(document).on('click', 'a[data-type="tag-user-again"]', function () {
    
     var d_id = $(this).data("id");
                var request = {
                    type: 'tag-user-again',
                    to: "all",
                    tagged_id: $('.js-example-basic-single[data-id="' + $(this).data('id') + '"]').val(),
                    fid: $(this).data('id') 
                 }
                 ws.send(JSON.stringify(request));


    var value = $("#select-tag-user-again").val();
    var value1 = $("select[data-id='"+d_id+"']").val();

 
    if(value != null)
        {  
             for( var i in value ) {
                    

                    $("select[data-id='"+d_id+"'] option[value="+value[i]+"]").remove();
                }
                     var value5 = $("select[data-id='"+d_id+"'] option").size();
                     var idp =  $(".box [data-id='"+d_id+"']").data("id");

                    if(value5 == 0 && idp == d_id)
                    {
                        $(".user_"+d_id).css('display','none');
                    }

             $(".js-example-basic-single").select2("val", "");
             $("#show-again[data-id='" + $(this).data("id") + "']").hide();
        }else
        { 
          for( var i in value1 ) {
                    $("select[data-id='"+d_id+"'] option[value="+value1[i]+"]").remove();
                }


                     var value5 = $("select[data-id='"+d_id+"'] option").size();                    
                    if(value5 == 0)
                    {
                         $(".user_"+d_id).css('display','none');
                    }   


                
             $(".js-example-basic-single").select2("val", "");
             $("#show-again[data-id='" + $(this).data("id") + "']").hide();
        }
       
});




/*
 *   KAMLESH POKIYA (KAP).
 *   Find studymate with textbox.
 */
$(document).on('keyup', 'input[data-type="study_mate_search"]', function () {

// if($('input[data-type="study_mate_search"]').val().length >= 2){  
    str = '<center><img src="assets/images/loading3.gif"></center>';
    $('.search_studymate .box.general_cred .box_body #mCSB_3 #mCSB_3_container').html(str);
    var request = {
        type: 'study_mate_search',
        to: 'self',
        search_txt: $('input[data-type="study_mate_search"]').val(),
        fid: $(this).data('id'),
        search_type: $('.row.filter_bar ul').find('li.active').data('id')
    }
    ws.send(JSON.stringify(request));
    // }
});
/*
 *   KAMLESH POKIYA (KAP).
 *   Find studymate with tab.
 */
$(document).on('click', 'li[data-type="search-type"]', function () {
// if($('input[data-type="study_mate_search"]').val().length >= 2){  
    str = '<center><img src="assets/images/loading3.gif"></center>';
    $('.search_studymate .box.general_cred .box_body #mCSB_3 #mCSB_3_container').html(str);
    var request = {
        type: 'study_mate_search',
        to: 'self',
        search_txt: $('input[data-type="study_mate_search"]').val(),
        fid: $(this).data('id'),
        search_type: $(this).data('id')
    }
    ws.send(JSON.stringify(request));
    // }
});
/*
 *   KAMLESH POKIYA (KAP).
 *   Find studymate with Load more.
 */
$(document).on('click', 'a[data-type="load-studymate-more"]', function () {
    var request = {
        type: 'load-studymate-more',
        to: 'self',
        search_txt: $('input[data-type="study_mate_search"]').val(),
        fid: $(this).data('id'),
        search_type: $('.row.filter_bar ul .active').data('id'),
        load_more: 'true',
        data_start: $(this).data('start')
    }
    ws.send(JSON.stringify(request));
});
/*
 *   KAMLESH POKIYA (KAP).
 *   View studymate profile.
 */
$(document).on('click', '#view_profile', function () {
    $('#view_profile_model div[data-type="profile_pic"]').html('<img src="uploads/' + $(this).data('profile') + '">');
    $('#view_profile_model h3[data-type="user-name"]').html($(this).data('name'));
    $('#view_profile_model p[data-type="course-name"]').html('<span class="fa fa-graduation-cap"></span>' + $(this).data('course'));
    $('#view_profile_model').modal('show');
});
/* close chat window */

/* close chat window */
$(document).on('click', 'a[data-type="close"]', function () {
    if($('#chat_container .chat[data-id="' + $(this).data('id') + '"]').hasClass('active')){
        $('#chat_container .chat[data-id="' + $(this).data('id') + '"]').remove();
        var len = $('#chat_container .chat').length;
        j=3;
            
            if(len > 0){
                for (var i = 1; i <= len; i++) {
                    if (j > 0 && i != len) {
                       $(".chat_container .chat:nth-child(" + i + ")").attr('class', 'chat passive chat_' + j);
                        j--;
                    }
                    if(i==len){
                        $(".chat_container .chat:nth-child(" + i + ")").attr('class', 'chat active');
                         var request = {
                            type: 'get_latest_message',
                            to: 'self',
                            my_id: id
                        };
                        ws.send(JSON.stringify(request));
                        $.cookie('active', $(".chat_container .chat:nth-child(" + i + ")").attr('data-id'));
                    }
                }
            }else{
                $.removeCookie('active');
            }
    }else{
        if($('#chat_container .chat[data-id="' + $(this).data('id') + '"]').hasClass("chat_3")){
        $('#chat_container .chat[data-id="' + $(this).data('id') + '"]').remove();
            $("#chat_container .chat_2").attr('class', 'chat passive chat_3');
            $("#chat_container .chat_1").attr('class', 'chat passive chat_2');
        }
        if($('#chat_container .chat[data-id="' + $(this).data('id') + '"]').hasClass("chat_2")){
        $('#chat_container .chat[data-id="' + $(this).data('id') + '"]').remove();
            $("#chat_container .chat_1").attr('class', 'chat passive chat_2');
        }
        if($('#chat_container .chat[data-id="' + $(this).data('id') + '"]').hasClass("chat_1")){
        $('#chat_container .chat[data-id="' + $(this).data('id') + '"]').remove();
            //$("#chat_container .chat_1").attr('class', 'chat passive chat_2');
        }
    }
    return false;
});
/*
 *   KAMLESH POKIYA (KAP).
 *   Find studymate with load more.
 */
$(document).on('click', 'a[data-type="load-activity-more"]', function () {
    $(this).html('<img src="assets/images/spinner.gif">');
    var request = {
        type: 'load-activity-more',
        to: 'self',
        month: $(this).attr('data-month')
    }
    ws.send(JSON.stringify(request));

});

$(document).on('click', 'label[data-type="show-profile"],span[data-type="show-profile"],a[data-type="show-profile"],img[data-type="show-profile"],h4[data-type="show-profile"],p[data-type="show-profile"],button[data-type="show-profile"],div[data-type="show-profile"]', function () {
    var request = {
        type: 'show_profile',
        to: 'self',
        user_id: $(this).attr('data-id')
    }
    ws.send(JSON.stringify(request));
});

function saveImg(image) {
    
    var arr = image.split(',');
    var request = {
        type: 'topic_file',
        name: 'whiteboard_img.png',
        data_type: 'image/png',
        data: arr[1],
        to: 'topic'
    }
    $('.upload_loader_whiteboard').fadeIn(300);
    ws.send(JSON.stringify(request));
}

var chat_3,chat_2,chat_1;
    function blink(elem, times, speed) {
        if(times == 'chat_3'){
            chat_3 = setInterval(function(){
                            $(elem).toggleClass("blink");
                        },speed);
        }
        if(times == 'chat_2'){
            chat_2 = setInterval(function(){
                            $(elem).toggleClass("blink");
                        },speed);
        }
        if(times == 'chat_1'){
            chat_1 = setInterval(function(){
                            $(elem).toggleClass("blink");
                        },speed);
        }
    }

$.fn.timestatus = function (msg) {
    //console.log(msg);
    var x = 0;
    var check_limit;
    if(typeof(msg) != "undefined" )
    {
        var temp = msg.split(" ");
        if(temp[1] == "min")
        {
          x = x + (temp[0] * 60);
        }else if(temp[1] == "sec")
        {
            x = x + parseInt(temp[0]);
        }else if(temp[1] == "hours" || temp[1] == "hour")
        {
            x = x + (temp[0] * 3600);
        }

        if(temp[1] != "Now" && temp[1] != "hours" && temp[1] != "hour" && temp[1] != "min" && temp[1] != "sec")
        {
            check_limit = true;
        }

        if(temp[1] == "hours"  && parseInt(temp[0]) > 2)
        {
            check_limit = true; 
        }

    }

    var id = Date.now();
    if($(this).hasClass('just_now')){
    this.removeClass('just_now');
    this.addClass("" + id);
    var dis = '';
    if(!check_limit){
    setInterval(function () {
        if (x >= 7200) {
            dis = '2 hours ago';
        } else if (x >= 3600 && x < 7200) {
            dis = '1 hour ago';
        } else if (x >= 1800 && x < 3600) {
            dis = '30 min ago';
        } else if (x >= 900 && x < 1800) {
            dis = '15 min ago';
        } else if (x >= 300 && x < 900) {
            dis = '5 min ago';
        } else if (x >= 120 && x < 300) {
            dis = '2 min ago';
        } else if (x >= 60 && x < 120) {
            dis = '1 min ago';
        } else {
            dis = 'Just Now';
        }
        $('.' + id).html(dis);
        x++;
    }, 1000, this);}
    else
    {   
         dis = msg;
        //this.removeClass('noti_time');
         $('.' + id).html(dis); 
    }
    }
};


/* Function for time counter for the comment's */
/* msg is time comes from. e.g : 5 min ago, 17 hours ago */


$.fn.timestatus1 = function (msg) {

    //console.log(msg);
    var x = 0;
    var check_limit;
    if(typeof(msg) != "undefined" )
    {
        var temp = msg.split(" ");
        if(temp[1] == "min")
        {
          x = x + (temp[0] * 60);
        }else if(temp[1] == "sec")
        {
            x = x + parseInt(temp[0]);
        }else if(temp[1] == "hours" || temp[1] == "hour")
        {
            x = x + (temp[0] * 3600);
        }

        if(temp[1] != "Now" && temp[1] != "hours" && temp[1] != "hour" && temp[1] != "min" && temp[1] != "sec")
        {
            check_limit = true;
        }

        if(temp[1] == "hours"  && parseInt(temp[0]) > 2)
        {
            check_limit = true; 
        }

    }

    var id = Date.now();
    if($(this).hasClass('just_now1')){
    this.removeClass('just_now1');
    this.addClass("" + id);
    var dis = '';
    if(!check_limit){
    setInterval(function () {
        if (x >= 7200) {
            dis = '2 hours ago';
        } else if (x >= 3600 && x < 7200) {
            dis = '1 hour ago';
        } else if (x >= 1800 && x < 3600) {
            dis = '30 min ago';
        } else if (x >= 900 && x < 1800) {
            dis = '15 min ago';
        } else if (x >= 300 && x < 900) {
            dis = '5 min ago';
        } else if (x >= 120 && x < 300) {
            dis = '2 min ago';
        } else if (x >= 60 && x < 120) {
            dis = '1 min ago';
        } else {
            dis = 'Just Now';
        }
        $('.' + id).html(dis);
        x++;
    }, 1000, this);}
    else
    {   
         dis = msg;
         this.removeClass('noti_time');
         $('.' + id).html(dis); 
    }
    }


};


$(document).ready(function () {

    $('.mscroll_custom').mCustomScrollbar({
        theme: "minimal-dark"
    });

    if ($('.discussion').length > 0) {
        $('.discussion').mCustomScrollbar({
            callbacks: {
                onInit: function ()
                {
                    $('.discussion').mCustomScrollbar('scrollTo', 'bottom');
                },
                onUpdate: function () {
                    $('.discussion').mCustomScrollbar('scrollTo', 'bottom');
                }
            }
        });
    }

});

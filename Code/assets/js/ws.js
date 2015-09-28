        $(document).ready(function() {
            $('.chat .chat_header').click(function(){
                if($(this).parent().hasClass('passive')){
                    if($(this).parent().hasClass('chat_3')){
                        $('.chat.active').addClass('chat_3 passive');                      
                        $('.chat.active').removeClass('active');
                    }
                    if($(this).parent().hasClass('chat_2')){
                        $('.chat.active').addClass('chat_2 passive');                      
                        $('.chat.active').removeClass('active');
                    }
                    if($(this).parent().hasClass('chat_1')){
                        $('.chat.active').addClass('chat_1 passive');                      
                        $('.chat.active').removeClass('active');
                    }
                    $(this).parent().removeClass();
                    $(this).parent().addClass('active');
                    $(this).parent().addClass('chat');
                }
            });
        });

var WS = function(url)
{
	var callbacks = {};
	var ws_url = url;
	var conn;

	this.bind = function(event_name, callback){
		callbacks[event_name] = callbacks[event_name] || [];
		callbacks[event_name].push(callback);
		return this;// chainable
	};

	this.send = function(event_name, event_data){
		this.conn.send( event_data );
		return this;
	};

	this.connect = function() {
		if ( typeof(MozWebSocket) == 'function' )
			this.conn = new MozWebSocket(url);
		else
			this.conn = new WebSocket(url);
		// dispatch to the right handlers
		this.conn.onmessage = function(evt){
			dispatch('message', evt.data);
		};

		this.conn.onclose = function(){dispatch('close',null)}
		this.conn.onopen = function(){dispatch('open',null)}
	};

	this.disconnect = function() {
		this.conn.close();
	};

	var dispatch = function(event_name, message){
		var chain = callbacks[event_name];
		if(typeof chain == 'undefined') return; // no callbacks for this event
		for(var i = 0; i < chain.length; i++){
			chain[i]( message )
		}
	}
};

var Server;
        function log( text ) {
            /*$log = $('.chat_text');
            //Add text to log
            $log.append(($log.val()?"\n":'')+text);
            //Autoscroll
            $log[0].scrollTop = $log[0].scrollHeight - $log[0].clientHeight;*/
        }

        function send( text ) {
            Server.send( 'message', text );
        }

        $(document).ready(function() {
            
            Server = new WS('ws://192.168.1.124:9300');       

            //Let the user know we're connected
            Server.bind('open', function() {
                log( "Connected." );
            });

            //OH NOES! Disconnection occurred.
            Server.bind('close', function( data ) {
                log( "Disconnected." );
            });

            //Log any messages sent from server
            Server.bind('message', function( payload ) {
            	var obj = $.parseJSON(payload);
                if(obj.type == 'studymate'){
                	if(ws == obj.from){
                		$('.chat[data-mate="'+obj.to+'"] .chat_text #mCSB_4 #mCSB_4_container').append("<div class='to'><p>"+obj.message+"</p></div>");	
                	}else{
                		$('.chat[data-mate="'+obj.from+'"] .chat_text #mCSB_4 #mCSB_4_container').append("<div class='from'><p>"+obj.message+"</p></div>");	
                	}
                	
                }
                
            });

            Server.connect();

            $('input[data-type="chat"]').keypress(function(e) {
                if ( e.keyCode == 13 && this.value ) {
                    var request = {
                    	type:"studymate",
			    		from: ws,
			    		to:$(this).data('id'),
			    		message:this.value
			    	};
                    send(JSON.stringify(request));
                    $(this).val('');
                }
            });



 });

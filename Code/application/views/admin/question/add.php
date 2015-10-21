<!--main-->
<div class="col-sm-7 main main2 main_wide">
	<!--breadcrumb-->
		<div class="row page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="admin/question/set">Assessment</a></li> 
              <li class="active">Question Bank</li>
            </ol>                 
        </div>
    </div>
    <!--//breadcrumb-->
    <!--main content-->
    <div class="question_bank">
    	<div class="col-sm-12 general_cred">
        	<!--box-->
            <div class="box">
                <div class="box_header filter">
                    <h3>Add New Question</h3>
                </div>
                <!--box_body-->
                <div class="box_body add_question_wrapper">
               		<div class="col-sm-12 padding_t15">
                    	<div class="form-group">
                            <label class="txt_red">Question</label>
                            <textarea class="form-control" name='question_text'></textarea>
                        </div>
                        <div class="form-group select">
                        	<label class="txt_red">Question Type</label>
                            <select class="form-control" id="question_type" name='question_type'>
                            	<option value="text">Text</option>
                            	<option value="paragraph">Paragraph Text</option>
                                <option selected="selected" value="mcq">Multiple Choice</option>
                            </select>
                        </div>
                    </div>
                    
                    <div class="col-sm-12" id="replacing_div1">
                    	<div class="form-group">
                            <input type="text" class="form-control add_answer" placeholder="Their Short Answer">
                        </div>
                        <button class="btn_green btn">Done</button>
                    </div>
                    <div class="col-sm-12" id="replacing_div2">
                    	<div class="form-group">
                            <textarea class="form-control add_answer" placeholder="Their Longer Answer"></textarea>
                        </div>
                        <button class="btn_green btn">Done</button>
                    </div>
                    <div class="col-sm-12" id="replacing_div3">
                    	<div class="form-group">
                            <input type="radio" name="correct_ans">
                            <input type="text" name="choices[]" class="form-control" placeholder="Question 1">
                        </div>
                        <div class="form-group">
                            <input type="radio" name="correct_ans">
                            <input type="text" name="choices[]" class="form-control" placeholder="Question 1">
                            <a href="#" class="icon icon_add_small"></a>
                        </div>                                    
                        <div class="form-group" id="ques_tags">
                        	<label class="txt_red">Question Tags</label>
                            <div class="tag_container">
                            	<input type="text" data-role="tagsinput"  class="typeahead" name="tags" id="tags">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12 col-md-6 inner_txtarea">
                    	<div class="box_header">
                        	<h3>Evaluation Notes</h3>
                        </div>
                    	<div class="form-group">
                            <textarea class="form-control" name="evaluation_notes"></textarea>
                        </div>
                    </div>
                    <div class="col-sm-12 col-md-6 inner_txtarea">
                    	<div class="box_header">
                        	<h3>Solution</h3>
                        </div>
                    	<div class="form-group">
                            <textarea class="form-control" name="solution"></textarea>
                        </div>
                    </div>

                    <!-- <input class="typeahead" type="text" placeholder="numbers (1-10)" autocomplete="off"> -->

                    <div class="clearfix"></div>
               	</div>
                <!--//box-body-->
                <div class="box_footer">
                	<input type="checkbox"><label class="save_box"></label><label>Add Question to Preview</label>
                	
                    <button class="btn btn_green pull-right no-margin">Save & Add More</button>
                    <button class="btn btn_red pull-right">Save</button>
                </div>
            </div>
            <!--//box-->
        </div>
        <div class="clearfix"></div>
    </div>
    <!--main content-->                
</div>
            <!--//main-->

<link rel="stylesheet" type="text/css" href="http://twitter.github.io/typeahead.js/css/examples.css">
<script type='text/javascript' src="http://twitter.github.io/typeahead.js/releases/latest/typeahead.bundle.js"></script>

<script>
    
    $('#question_type').val('mcq');        
    
    $(window).load(function(){

      // var numbers;

      // numbers = new Bloodhound({
      //   datumTokenizer: function(d) { return Bloodhound.tokenizers.whitespace(d.num); },
      //   queryTokenizer: Bloodhound.tokenizers.whitespace,
      //   local: [
      //     { num: 'one' },
      //     { num: 'two' },
      //     { num: 'three' },
      //     { num: 'four' },
      //     { num: 'five' },
      //     { num: 'six' },
      //     { num: 'seven' },
      //     { num: 'eight' },
      //     { num: 'nine' },
      //     { num: 'ten' }
      //   ]
      // });

      // numbers.initialize();

      // $('.typeahead').typeahead(null, {
      //   displayKey: 'num',
      //   source: numbers.ttAdapter()
      // });

   var cities = new Bloodhound({
      datumTokenizer: Bloodhound.tokenizers.obj.whitespace('text'),
      queryTokenizer: Bloodhound.tokenizers.whitespace,
      prefetch: '[{"value":1,"text":"Amsterdam","continent":"Europe"},{"value":4,"text":"Washington","continent":"America"},{"value":7,"text":"Sydney","continent":"Australia"},{"value":10,"text":"Beijing","continent":"Asia"},{"value":13,"text":"Cairo","continent":"Africa"}]'
    });
    cities.initialize();

    var elt = $('#tags');
    elt.tagsinput({
      tagClass: function(item) {
        switch (item.continent) {
          case 'Europe'   : return 'label label-primary';
          case 'America'  : return 'label label-danger label-important';
          case 'Australia': return 'label label-success';
          case 'Africa'   : return 'label label-default';
          case 'Asia'     : return 'label label-warning';
        }
      },
      itemValue: 'value',
      itemText: 'text',
      typeaheadjs: {
        name: 'cities',
        displayKey: 'text',
        source: cities.ttAdapter()
      }
    });

    elt.tagsinput('add', { "value": 1 , "text": "Amsterdam"   , "continent": "Europe"    });
    elt.tagsinput('add', { "value": 4 , "text": "Washington"  , "continent": "America"   });
    elt.tagsinput('add', { "value": 7 , "text": "Sydney"      , "continent": "Australia" });
    elt.tagsinput('add', { "value": 10, "text": "Beijing"     , "continent": "Asia"      });
    elt.tagsinput('add', { "value": 13, "text": "Cairo"       , "continent": "Africa"    });


});



    $(function() {       
        $('#question_type').change(function(){
            if($('#question_type').val() == 'text') {
                $('#replacing_div1').show();
                $('#replacing_div2').hide();
                $('#replacing_div3').hide();
            }
            if($('#question_type').val() == 'paragraph') {
                $('#replacing_div2').show();
                $('#replacing_div1').hide();
                $('#replacing_div3').hide();
            }
            if($('#question_type').val() == 'mcq') {
                $('#replacing_div3').show();
                $('#replacing_div2').hide();
                $('#replacing_div1').hide();
            }           
        });
    });

</script>
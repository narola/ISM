<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />

<!--main-->
            <div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
            	<!--breadcrumb-->
           		<div class=" page_header">
                	<div class="col-sm-12">
                    	<ol class="breadcrumb">
                          <li><a href="#">Report Card</a></li>
                        </ol>
                    </div>
                </div>
                <!--//breadcrumb-->
                <!--filter-->
                <div class=" filter exam_filter report_filter">
                	<div class="col-sm-12">
                    	<div class="form-group data_range">
                        	<label>Data Range :</label>
                            <div class="range_picker">
                            <input class="form-control" type="text" name="daterange" value="01/01/2015 - 01/31/2015" />
                                <a class="icon icon_calendar_red"></a>
                            </div>
                                <!-- <input type="text" class="form-control" placeholder="From">
                            <div class="range_picker">
                            	<input type="text" class="form-control" placeholder="To">
                                <a href="#" class="icon icon_calendar_red"></a>
                            </div> -->
                        </div>
                    	<div class="form-group">
                            <select class="form-control" name="course_id" onchange="get_classes(this.value)" id="course_id">
                               <option value=''>Course</option>
                                <?php if(!empty($courses)){ 
                                    foreach ($courses as $course) { ?>
                                        <option value="<?php echo $course['id']; ?>"><?php echo $course['course_name']; ?></option>        
                                <?php } 
                            }?>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control" name="classroom_id" onchange="get_subjects(this.value)" id="classroom_id">
                                <option>Classroom</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control" name="subject_id" onchange="get_topics(this.value)" id="subject_id">
                                <option>Subject</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Sort By</option>
                            </select>
                        </div>
                        
                    </div>
                </div>
                <!--//filter-->
                <!--report-->
                <div class=" reports">
                	<div class="col-sm-6 no-padding performance_wrapper">
                    	<h3>Question Submitted</h3>
                        <div id="performance_graph_1">
                        </div>
                    </div>
                    <div class="col-sm-6 no-padding performance_wrapper">
                    	<h3>Question Submitted</h3>
                        <div id="performance_graph_2">
                        </div>
                    </div>
                    <div class="col-sm-6 no-padding performance_wrapper">
                    	<h3>Top Ranking Group</h3>
                        <div class="top_ranking mCustomScrollbar" data-mcs-theme="minimal-dark">
                        	<div class="table-responsive">
                                <table class="table table-bordered">
                                    <tr>
                                        <th>Rank</th>
                                        <th>Group Name</th>
                                        <th>Course</th>
                                        <th>Score</th>
                                    </tr>
                                    <tr>
                                        <td>1</td>
                                        <td>Vanice Beauty</td>
                                        <td>HSC Sci</td>
                                        <td>5000</td>
                                    </tr>
                                    <tr>
                                        <td>2</td>
                                        <td>Allrounders</td>
                                        <td>HSC Sci</td>
                                        <td>4750</td>
                                    </tr>
                                    <tr>
                                        <td>3</td>
                                        <td>Ranker's Group</td>
                                        <td>HSC Sci</td>
                                        <td>4700</td>
                                    </tr>
                                    <tr>
                                        <td>4</td>
                                        <td>Heaven Group</td>
                                        <td>HSC Sci</td>
                                        <td>4450</td>
                                    </tr>
                                    <tr>
                                        <td>5</td>
                                        <td>Allrounders</td>
                                        <td>HSC Sci</td>
                                        <td>4300</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 no-padding performance_wrapper">
                    	<h3>Individual Student</h3>
                        <div id="performance_graph_3">
                        </div>
                    </div>
				</div>
                <!--//report-->
			</div>
            <!--//main-->
            <!--gharph-->
    <script src="http://code.highcharts.com/highcharts.js"></script>
    <script src="http://code.highcharts.com/modules/data.js"></script>
    <script src="http://code.highcharts.com/modules/drilldown.js"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
 
<!-- Include Date Range Picker -->
<script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
    <script type="text/javascript">
   
$(function() {
    $('input[name="daterange"]').daterangepicker();
});

function get_classes(course_id){
        $.ajax({
           url:'<?php echo base_url()."admin/question/ajax_get_classrooms"; ?>',
           type:'POST',
           data:{course_id:course_id},
           success:function(data){
              $("#classroom_id").html(data);
              $('#subject_id').val('');
              $('#topic_id').val('');
           }
        });
    }

  function get_subjects(classroom_id){
        $.ajax({
           url:'<?php echo base_url()."admin/question/ajax_get_subjects"; ?>',
           type:'POST',
           data:{classroom_id:classroom_id},
           success:function(data){
              $("#subject_id").html(data);
              $('#topic_id').val('');
           }
        });
  }
    // var course_onload = $("#course_id").val($("#course_id option:first").next().val());
    
   
        $(function () {
            // Create the chart
            $('#performance_graph_3').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: 'Avg. Score % V/S No of Student'
                },
                xAxis: {
                    type: 'category'
                },
                yAxis: {
                    title: {
                        text: 'Score'
                    }
                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true,
                            format: '{point.y:.1f}%'
                        }
                    }
                },
        
                tooltip: {
                    headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                    pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b><br/>'
                },
        
                series: [{
                    name: "No. of Students",
                    colorByPoint: true,
                    data: [{
                        name: "10",
                        y: 75,
                        drilldown: "10"
                    }, {
                        name: "20",
                        y: 82,
                        drilldown: "20"
                    }, {
                        name: "30",
                        y: 95,
                        drilldown: "30"
                    }, {
                        name: "40",
                        y: 65,
                        drilldown: "40"
                    }, {
                        name: "50",
                        y: 90,
                        drilldown: "50"
                    }]
                }]              
            });
        });
        $(function () {
            // Create the chart
            $('#performance_graph_2').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: 'Group V/S Score'
                },
                xAxis: {
                    type: 'category'
                },
                yAxis: {
                    title: {
                        text: 'Score'
                    }
                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true,
                            format: '{point.y:.1f}%'
                        }
                    }
                },
        
                tooltip: {
                    headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                    pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b><br/>'
                },
        
                series: [{
                    name: "Classes",
                    colorByPoint: true,
                    data: [{
                        name: "Venice Beauty",
                        y: 75,
                        drilldown: "Venice Beauty"
                    }, {
                        name: "Allrounders",
                        y: 82,
                        drilldown: "Allrounders"
                    }, {
                        name: "Heaven Group",
                        y: 95,
                        drilldown: "Heaven Group"
                    }, {
                        name: "Rankers' Group",
                        y: 65,
                        drilldown: "Rankers' Group"
                    }]
                }]
            });
        });
        $(function () {
            // Create the chart
            $('#performance_graph_1').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: 'Question V/S Course'
                },
                xAxis: {
                    type: 'category'
                },
                yAxis: {
                    title: {
                        text: 'Score'
                    }
                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true,
                            format: '{point.y:.1f}%'
                        }
                    }
                },
        
                tooltip: {
                    headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                    pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b><br/>'
                },
        
                series: [{
                    name: "Classes",
                    colorByPoint: true,
                    data: [{
                        name: "HSC Sci",
                        y: 90,
                        drilldown: "HSC Sci"
                    }, {
                        name: "HSC Comm",
                        y: 82,
                        drilldown: "HSC Comm"
                    }, {
                        name: "Agriculture Sci",
                        y: 55,
                        drilldown: "Agriculture Sci"
                    }, {
                        name: "Computer Sci",
                        y: 75,
                        drilldown: "Computer Sci"
                    }]
                }]
            });
        });




  
    </script>
   <!--main-->
<div class="col-sm-7 main main2 main_wide mCustomScrollbar" data-mcs-theme="minimal-dark">
	<!--breadcrumb-->
		<div class="page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="admin/group">Groups</a></li> 
              <li class="active">Group Performance</li>
            </ol>                  
        </div>
    </div>
    <!--//breadcrumb-->
    <!--banner-->
    <div class="exam_banner banner_admin">
     	<div class="col-sm-12 text-center">
        	<div>
                <div class="banner_text banner_group_image">
                    <img src="../images/group2.jpg" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                </div>
                <div class="banner_group_name">
                    <h2>Group Name: <strong><?php echo $all_groups['group_name']; ?></strong></h2>
                    <p><?php echo $all_groups['course_name']; ?></p>
                </div>
            </div>
        </div>
     </div>
    <!--//banner-->
    <!--main part-->
    <div class="">
    	<div class="col-sm-4 group_performance_1 general_cred">
        	<div class="box_header">
            	<h3>Members</h3>
            </div>
            <div class="group_member_wrapper mCustomScrollbar" data-mcs-theme="minimal-dark">
                <?php if(!empty($all_groups_members)){ foreach($all_groups_members as $member){ ?>
                <!--member1-->
                <div class="group_perf_member">                            
                    <div class="username">
                        <div class="chat_img_holder"><img src="<?php echo 'uploads/'.$member['profile_link']; ?>" 
                            onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" ></div>
                        <h4 class="txt_blue"><?php echo $member['username']; ?></h4>
                        <p><?php echo $member['school_name']; ?></p>
                        <p class="active">Since 3 Years</p>
                    </div>
                    <div class="group_score">
                        <hr>
                        <div>
                            <h2 class="group_total_points txt_red"><?php echo ($member['score']!='') ? $member['score'] : 0; ?></h2>
                            <p>Score</p>
                        </div>
                        <div>
                            <h2 class="group_rank txt_green"><?php echo $all_groups['exams']; ?></h2>
                            <p>Exams</p>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
                <!--//member1-->

                <?php } }?>
            </div>
        </div>
        <div class="col-sm-4 group_performance_2 general_cred">
			<div class="box_header">
            	<h3>Topics Allocated</h3>
            </div>
            <div class="allocate_topic_wrapper mCustomScrollbar" data-mcs-theme="minimal-dark">
                <!--month1-->

                    <?php if(!empty($all_groups_topics)){ foreach($all_groups_topics as $topic){ ?>
                <div class="allocated_topics month_wise">
                    <div class="month_name"><span>WEEK <?php echo $topic['week_no']; ?></span></div>
                        <!--card1-->
                        <div class="exams_holder">
                            <div class="exam_year">
                                <p><?php echo $topic['topic_name']; ?></p>
                            </div>
                            <div class="type_holder">
                                <div class="exam_type"><p>Discussion</p><p><?php echo $topic['total']; ?> Comments</p></div>
                                <div class="exam_cat"><p>Examination - Quiz</p><p><?php echo $topic['group_score']; ?> Score</p></div>
                                <div class="clearfix"></div>
                            </div>                                
                        </div>
                        <!--//card1-->
                </div>
                    <?php } } ?>                            
                <!--//month1-->
                <!--month2-->
                
                <!--//month2-->
            </div>
        </div>
        <div class="col-sm-4 group_performance_3 general_cred">
        	<div class="box_header">
            	<h3>Performance</h3>
            </div>
            <!--div-->
            <div class="performance_div mCustomScrollbar" data-mcs-theme="minimal-dark">
                <div class="performance_wrapper">
                    <div id="performance_graph">
                    </div>
                </div>
            </div>
            <!--//div-->
        </div>
    </div>
    <!--//main part-->
</div>
<!--//main-->

 <script type="text/javascript">
        $(function () {
           
            // Create the chart
            $('#performance_graph').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: 'Score -> Subject'
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
                    enabled: true
                },
                plotOptions: {
                    series: {
                        borderWidth: 0,
                        dataLabels: {
                            enabled: true,
                            format: '{point.y}'
                        }
                    }
                },
        
                tooltip: {
                    headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                    pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b><br/>'
                },
        
                series: [{
                    name: "Subjects",
                    colorByPoint: true,
                    data: <?php echo $group_performance; ?>
                }],
                 
            });
        });
    </script>
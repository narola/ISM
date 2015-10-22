<!--main-->
            <div class="col-sm-7 main main2">
            	<!--breadcrumb-->
           		<div class="row page_header">
                	<div class="col-sm-12">
                    	<ol class="breadcrumb">
                          <li><a href="#">Report Card</a></li>
                        </ol>
                    </div>
                </div>
                <!--//breadcrumb-->
                <!--filter-->
                <div class="row filter exam_filter report_filter">
                	<div class="col-sm-12">
                    	<div class="form-group data_range">
                        	<label>Data Range :</label>
                            <div class="range_picker">
                            	<input type="text" class="form-control" placeholder="From">
                                <a href="#" class="icon icon_calendar_red"></a>
                            </div>
                            <div class="range_picker">
                            	<input type="text" class="form-control" placeholder="To">
                                <a href="#" class="icon icon_calendar_red"></a>
                            </div>
                        </div>
                    	<div class="form-group">
                            <select class="form-control">
                                <option>Course</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Subject</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Classroom</option>
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
                <div class="row reports">
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
    <script type="text/javascript">
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
                    text: 'Point V/S Course'
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
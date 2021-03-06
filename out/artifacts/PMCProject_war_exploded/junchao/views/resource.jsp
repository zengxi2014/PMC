<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/junchao/resources/semantic/semantic.min.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/junchao/resources/js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/junchao/resources/semantic/semantic.min.js"></script>
    <!-- ECharts单文件引入 -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/junchao/resources/echart/echarts.js"></script>

    <style type="text/css" rel="stylesheet">
        body {
            margin: auto;
            text-align: center;

        }

        .ui.huge.header {
            padding: 0.125em;
            text-align: center;
            font-family: "黑体", serif;
        }

        section {
            margin: 5px auto;
            width: 1000px;
            text-align: center;
            border: 1px solid #000000;
        }

        .container {
            height: 400px;
            border: 1px solid #000000;
        }
    </style>
    <script type="text/javascript">

        $(function () {
            // 路径配置
            require.config({
                paths: {
                    echarts: "<%=request.getContextPath()%>/junchao/resources/echart/"
                }
            });

            var test = "${param.appId}"
            console.log(test);

            // 使用
            require([
                        'echarts',
                        'echarts/chart/line', // 使用条形图就加载line模块，按需加载
                        'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
                    ],
                    function (ec) {


                        $.ajax({
                            "url": "http://121.199.23.184/PerformanceMonitorCenter/cpuAnalysis!cpuAveragetRatio",
                            <%--"url": "<%=request.getContextPath()%>/cpuAnalysis!cpuAveragetRatio",--%>
                            "type": "POST",
                            "data":{
                                "appId": "85d4a553-ee8d-4136-80ab-2469adcae44d"
                            },
                            "dataType": "json",
                            "success": function (result) {
                                getLoadCpuAvg(ec, result)
                            }
                        });
                        $.ajax({
                            "url": "http://121.199.23.184/PerformanceMonitorCenter/memoryAnalysis!memoryAverageRatio",
                            <%--"url": "<%=request.getContextPath()%>/memoryAnalysis!memoryAverageRatio",--%>
                            "type": "POST",
                            "data":{
                                "appId": "85d4a553-ee8d-4136-80ab-2469adcae44d"
                            },
                            "dataType": "json",
                            "success": function (result) {
                                getMemoryAvg(ec, result)
                            }
                        });

//                        getLoadCpuAvg(ec);
//                        getMemoryAvg(ec);
                    }
            );

            function getLoadCpuAvg(ec, result) {
                var result = {
                    "appName": "开源中国",
                    "data": [{
                        "appVersion": "2.2",
                        "names": ["1", "2", "3", "4", "5", "6"],
                        "values": [45, 31.579, 0, 12.44, 9, 0]
                    }, {
                        "appVersion": "1.7.7.1",
                        "names": ["1", "2", "3", "4", "5", "6"],
                        "values": [45.455, 35, 0, 22.22, 0, 16.667]
                    }]
                };

                var $section = $("section.load-avg");
                $("span.app-name").text(result.appName);

                for (var i = 0; i < result.data.length; i++) {
                    var $main = $('<div class="container"></div>');
                    $section.append($main);
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init($main.get(0));

                    var option = {
                        title: {
                            text: 'CPU平均动态占用率',
                            subtext: "Version: " + result.data[i].appVersion
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['占用率（单位：%）']
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                mark: {show: true},
                                dataView: {show: true, readOnly: false},
                                magicType: {show: true, type: ['line', 'bar']},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        calculable: true,
                        xAxis: [
                            {
                                type: 'category',
                                data: result.data[i].names
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: '占用率（%）',
                                type: 'line',
                                data: result.data[i].values,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            }
                        ]
                    };

                    // 为echarts对象加载数据
                    myChart.setOption(option);
                }
            }

            function getMemoryAvg(ec, result) {
                var result = {
                    "appName": "开源中国", "data": [{
                        "appVersion": "2.2",
                        "names": ["1", "2", "3", "4", "5", "6"],
                        "values": [2296, 13624, 14108, 15992, 15988]
                    },
                        {
                            "appVersion": "1.7.7.1",
                            "names": ["1", "2", "3", "4", "5", "6"],
                            "values": [2376, 13408, 16008, 16016, 16044, 9988]
                        }]
                };

                var $section = $("section.usage-avg");
                $("span.app-name").text(result.appName);

                for (var i = 0; i < result.data.length; i++) {
                    var $main = $('<div class="container"></div>');
                    $section.append($main);
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init($main.get(0));

                    var option = {
                        title: {
                            text: '内存占用量',
                            subtext: "Version: " + result.data[i].appVersion
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['内存占用量（单位：KB）']
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                mark: {show: true},
                                dataView: {show: true, readOnly: false},
                                magicType: {show: true, type: ['line', 'bar']},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        calculable: true,
                        xAxis: [
                            {
                                type: 'category',
                                data: result.data[i].names
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: '内存（KB）',
                                type: 'line',
                                data: result.data[i].values,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            }
                        ]
                    };

                    // 为echarts对象加载数据
                    myChart.setOption(option);
                }

            }
        });

    </script>
</head>
<body>
<div class="sizer">
    <h1 class="ui huge header"><span class="app-name"></span>手机资源使用信息</h1>
</div>
<section class="load-avg">
</section>
<section class="usage-avg">
</section>
</body>
</html>

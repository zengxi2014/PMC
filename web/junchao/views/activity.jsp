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

            // 使用
            require([
                        'echarts',
                        'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                        'echarts/chart/line' // 使用条形图就加载line模块，按需加载
                    ],
                    function (ec) {
                        $.ajax({
                            <%--"url": "<%=request.getContextPath()%>/loadActivityInfo!getActivityAverageUseTime",--%>
                            "url": "http://121.199.23.184/PerformanceMonitorCenter/loadActivityInfo!getActivityAverageUseTime",
                            "type": "GET",
                            "data":{
                                    "appId": "85d4a553-ee8d-4136-80ab-2469adcae44d"
                            },
                            "dataType": "json",
                            "success": function (result) {
                                getUsageAvg(ec, result)
                            }
                        });


                        $.ajax({
                            "url": "http://121.199.23.184/PerformanceMonitorCenter/loadActivityInfo!getActivityVisitTimes",
                            <%--"url": "<%=request.getContextPath()%>/loadActivityInfo!getActivityVisitTimes",--%>
                            "type": "POST",
                            "data":{
                                "appId": "85d4a553-ee8d-4136-80ab-2469adcae44d"
                            },
                            "dataType": "json",
                            "success": function (result) {
                                getVisitCount(ec, result)
                            }
                        });

                        $.ajax({
                            "url": "http://121.199.23.184/PerformanceMonitorCenter/loadFragmentInfo!getFragementAverageLoadTime",
                            <%--"url": "<%=request.getContextPath()%>/loadFragmentInfo!getFragementAverageLoadTime",--%>
                            "type": "POST",
                            "data":{
                                "appId": "85d4a553-ee8d-4136-80ab-2469adcae44d"
                            },
                            "dataType": "json",
                            "success": function (result) {
                                getFragmentLoadAvg(ec, result)
                            }
                        });

                        $.ajax({
                            "url": "http://121.199.23.184/PerformanceMonitorCenter/loadFragmentInfo!getFragmentVisitTimes",
                            <%--"url": "<%=request.getContextPath()%>/loadFragmentInfo!getFragmentVisitTimes",--%>
                            "type": "POST",
                            "data":{
                                "appId": "85d4a553-ee8d-4136-80ab-2469adcae44d"
                            },
                            "dataType": "json",
                            "success": function (result) {
                                getFragmentVisitCount(ec, result)
                            }
                        });


//                        getLoadAvg(ec);
//                        getUsageAvg(ec);
//                        getFragmentLoadAvg(ec);
//                        getVisitCount(ec);
//                        getFragmentVisitCount(ec);
                    }
            );

            function getLoadAvg(ec, result) {
                var result = {
                    "appName": "开源中国",
                    "data": [{
                        "appVersion": "2.2",
                        "names": ["MainActivity", "AppStart", "LoginActivity", "DetailActivity", "SimpleBackActivity"],
                        "values": [384.5, 150, 23.5, 12.44, 9]
                    }, {
                        "appVersion": "1.7.7.1",
                        "names": ["QuestionDetail", "TweetDetail", "NewsDetail", "SoftwareDetail", "LoginDialog", "Main", "AppStart",
                            "About", "UserInfo", "TweetPub", "Setting", "ReportUi", "QuestionPub", "BlogDetail", "UserFriend", "Search",
                            "UserFavorite", "SoftwareLib", "CommentPub"],
                        "values": [2085.47, 1402.26, 830.74, 478.04, 275.6, 204.35, 166.12, 140.28, 123.14, 106.66, 77.5, 59, 47.5, 36.08, 35, 34, 29.5, 18.44, 17.25]
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
                            text: 'Activity加载时间',
                            subtext: "Version: " + result.data[i].appVersion
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['加载时间（单位：ms）']
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
                                name: '加载时间（ms）',
                                type: 'bar',
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

            function getUsageAvg(ec, result) {
//                var result = {
//                    "appName": "开源中国", "data": [{
//                        "appVersion": "2.2",
//                        "names": ["MainActivity", "LoginActivity", "SimpleBackActivity", "DetailActivity", "AppStart"],
//                        "values": [8638, 7151.5, 3903.33, 3112.88, 1171]
//                    },
//                        {
//                            "appVersion": "1.7.7.1",
//                            "names": ["Main", "Search", "ReportUi", "SoftwareLib", "TweetDetail", "About", "QuestionDetail", "TweetPub", "QuestionPub",
//                                "UserInfo", "Setting", "LoginDialog", "NewsDetail", "BlogDetail", "SoftwareDetail", "UserFriend", "UserFavorite", "AppStart", "CommentPub"],
//                            "values": [20263.47, 19685, 13830, 11213.16, 7325.2, 6532.85, 5065.42, 4580, 4241.5, 4111.28, 3930.2, 3195.71, 3057.64, 2996.82, 2976.51,
//                                2975.75, 2421.5, 2299.75, 1932.75]
//                        }]
//                };

                var $section = $("section.usage-avg");
                $("span.app-name").text(result.appName);

                for (var i = 0; i < result.data.length; i++) {
                    var $main = $('<div class="container"></div>');
                    $section.append($main);
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init($main.get(0));

                    var option = {
                        title: {
                            text: 'Activity使用时间',
                            subtext: "Version: " + result.data[i].appVersion
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['使用时间（单位：ms）']
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
                                name: '使用时间（ms）',
                                type: 'bar',
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

            function getFragmentLoadAvg(ec, result) {
//                var result = {
//                    "appName": "开源中国",
//                    "data": [{
//                        "appVersion": "2.2",
//                        "names": ["KJEmojiFragment", "NewsFragment", "NavigationDrawerFragment", "NewsViewPagerFragment", "ToolbarFragment",
//                            "NewsDetailFragment", "BlogDetailFragment", "BlogFragment", "CommentFrament", "BlogViewPagerFragment"],
//                        "values": [4046.5, 37, 32, 23.5, 17.7, 12.66, 10.16, 6.66, 2.5, 1]
//                    }]
//                };
                var $section = $("section.fragment-load-avg");
                $("span.app-name").text(result.appName);

                for (var i = 0; i < result.data.length; i++) {
                    var $main = $('<div class="container"></div>');
                    $section.append($main);
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init($main.get(0));

                    var option = {
                        title: {
                            text: 'Fragment加载时间',
                            subtext: "Version: " + result.data[i].appVersion
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['加载时间（单位：ms）']
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
                                name: '加载时间（ms）',
                                type: 'bar',
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

            function getVisitCount(ec, result) {
//                var result = {
//                    "appName": "开源中国",
//                    "data": [{
//                        "appVersion": "2.2",
//                        "names": ["DetailActivity", "SimpleBackActivity", "MainActivity", "AppStart", "LoginActivity"],
//                        "values": [9, 3, 2, 2, 2]
//                    }, {
//                        "appVersion": "1.7.7.1",
//                        "names": ["SoftwareDetail", "NewsDetail", "Main", "AppStart", "BlogDetail", "LoginDialog", "QuestionDetail",
//                            "SoftwareLib", "TweetDetail", "Setting", "About", "UserInfo", "UserFriend", "CommentPub", "TweetPub",
//                            "UserFavorite", "QuestionPub", "Search", "ReportUi"],
//                        "values": [70, 51, 42, 40, 34, 28, 21, 18, 15, 10, 7, 7, 4, 4, 3, 2, 2, 1, 1]
//                    }]
//                };

                var $section = $("section.visit-feq");
                $("span.app-name").text(result.appName);

                for (var i = 0; i < result.data.length; i++) {
                    var $main = $('<div class="container"></div>');
                    $section.append($main);
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init($main.get(0));

                    var option = {
                        title: {
                            text: 'Activity访问次数统计',
                            subtext: "Version: " + result.data[i].appVersion
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['访问次数（单位：次）']
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
                                name: '访问次数（次）',
                                type: 'bar',
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

            function getFragmentVisitCount(ec, result) {
//                var result = {
//                    "appName": "开源中国",
//                    "data": [{
//                        "appVersion": "2.2",
//                        "names": ["ToolbarFragment", "BlogDetailFragment", "NewsDetailFragment", "BlogFragment", "KJEmojiFragment",
//                            "CommentFrament", "NewsViewPagerFragment", "NewsFragment", "NavigationDrawerFragment", "BlogViewPagerFragment"],
//                        "values": [10, 6, 3, 3, 2, 2, 2, 2, 2, 1]
//                    }]
//                };

                var $section = $("section.fragment-visit-feq");
                $("span.app-name").text(result.appName);

                for (var i = 0; i < result.data.length; i++) {
                    var $main = $('<div class="container"></div>');
                    $section.append($main);
                    // 基于准备好的dom，初始化echarts图表
                    var myChart = ec.init($main.get(0));

                    var option = {
                        title: {
                            text: 'Fragment访问次数统计',
                            subtext: "Version: " + result.data[i].appVersion
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['访问次数（单位：次）']
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
                                name: '访问次数（次）',
                                type: 'bar',
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
    <h1 class="ui huge header"><span class="app-name"></span>页面信息</h1>
</div>
<section class="load-avg">

</section>
<section class="usage-avg">

</section>

<section class="fragment-load-avg">

</section>

<section class="visit-feq">

</section>

<section class="fragment-visit-feq">

</section>
</body>
</html>

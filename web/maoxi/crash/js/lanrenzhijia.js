// 代码整理：懒人之家 lanrenzhijia.com
  $.fn.zoomtabs = function (zoomPercent, easing) {
        if (!zoomPercent) zoomPercent = 10;
        
        return this.each(function () {
            var $zoomtab = $(this);
            var $tabs = $zoomtab.find('.tabs');
            var height = $tabs.height();
            
            var panelIds = $tabs.find('a').map(function () {
                return this.hash;
            }).get().join(',');
            
            $zoomtab.find('> div').scrollTop(0);
            
            var $panels = $(panelIds);
            var images = [];
            
            $panels.each(function () {
                var $panel = $(this),
                    bg = ($panel.css('backgroundImage') || "").match(/url\s*\(["']*(.*?)['"]*\)/),
                    img = null;
                
                if (bg !== null && bg.length && bg.length > 0) {
                    bg = bg[1];
                    img = new Image();
                    
                    $panel.find('*').wrap('<div style="position: relative; z-index: 2;" />');                    
                    $panel.css('backgroundImage', 'none');
                    
                    $(img).load(function () {
                        var w = this.width / 10;
                        var wIn = w / 100 * zoomPercent;
                        var h = this.height / 10;
                        var hIn = h / 100 * zoomPercent;
                        var top = 0;
                        
                        var fullView = {
                            height: h + 'em',
                            width: w + 'em',
                            top: top,
                            left: 0
                        };
                                                
                        var zoomView = {
                            height: (h + hIn) + 'em',
                            width: (w + wIn) + 'em',
                            top: top,
                            left: '-' + (wIn / 2) + 'em'
                        };
                        
                        $(this).data('fullView', fullView).data('zoomView', zoomView).css(zoomView);

                    }).prependTo($panel).css({'position' : 'absolute', 'top' : 0, 'left' : 0 }).attr('src', bg);
                    
                    images.push(img);
                }
            });
            
            function zoomImages(zoomType, speed) {
                $(images).each(function () {
                    var $image = $(this);
                    if ($image.is(':visible')) {
                        $image.stop().animate($image.data(zoomType), speed, easing);
                    } else {
                        $image.css($image.data(zoomType), speed);
                    }
                });
            }
                        
            $tabs.height(0).hide(); // have to manually set the initial state to get it animate properly.
            
            // this causes opear to render the images with zero height and width for the hidden image
            // $panels.hide().filter(':first').show();
            var speed = 200;
            
            $zoomtab.hover(function () {
                // show and zoom out
                zoomImages('fullView', speed);
                $tabs.stop().animate({ height : height }, speed, easing);
            }, function () {
                // hide and zoom in
                zoomImages('zoomView', speed);
                $tabs.stop().animate({ height : 0 }, speed, easing, function () {
                  $tabs.hide();
                });
            });
            
            var hoverIntent = null;
            $tabs.find('a').hover(function () {
                clearTimeout(hoverIntent);
                var el = this;
                hoverIntent = setTimeout(function () {
                    $panels.hide().filter(el.hash).show();
                }, 100);
            }, function () {
                clearTimeout(hoverIntent);
            }).click(function () {
                return false;
            });
        });
    };

    $(function () {
        $('.zoomoutmenu').zoomtabs(15);
    });
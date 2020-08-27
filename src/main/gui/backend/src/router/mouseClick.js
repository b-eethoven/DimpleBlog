onload = function() {
  var click_cnt = 0;
  var content = ['小林','❤','宝贝','我爱你呀','❤','爱你呦','宝贝','mua~','么么么','❤','爱你','一万年','❤','嘿嘿','七夕快乐呀','❤'];
  var $html = document.getElementsByTagName("html")[0];
  var $body = document.getElementsByTagName("body")[0];
  function getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
  }
  $html.onclick = function(e) {
    var $elem = document.createElement("b");
    // $elem.style.color = '#' + ('00000' + (Math.random() * 0x1000000 << 0).toString(16)).substr(-6);
    $elem.style.color = "#E94F06";
    $elem.style.zIndex = 9999;
    $elem.style.position = "absolute";
    $elem.style.select = "none";
    $elem.style.userSelect = "none";
    var x = e.pageX;
    var y = e.pageY;
    $elem.style.left = (x - 10) + "px";
    $elem.style.top = (y - 20) + "px";
    clearInterval(anim);
    if (click_cnt>content.length-1){
      click_cnt = 0
    }
    $elem.innerText = content[click_cnt];
    // 按序展示
    ++click_cnt;
    // 随机展示
    // click_cnt = getRandomInt(content.length);
    $elem.style.fontSize = Math.random() * 10 + 8 + "px";
    var increase = 0;
    var anim;
    setTimeout(function() {
      anim = setInterval(function() {
        if (++increase == 150) {
          clearInterval(anim);
          $body.removeChild($elem);
        }
        $elem.style.top = y - 20 - increase + "px";
        $elem.style.opacity = (150 - increase) / 120;
      }, 1);
    }, 10);
    $body.appendChild($elem);
  };
};


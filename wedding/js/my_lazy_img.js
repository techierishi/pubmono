registerListener('load', lazyLoad);
registerListener('scroll', lazyLoad);

function lazyLoad(){
        var lazy = 
        document.getElementsByClassName('lazy_img');
    
        for(var i=0; i<lazy.length; i++) {
            if(isInViewport(lazy[i])){
               lazy[i].src =
                lazy[i].getAttribute('data-src');
            }
        }
     }

function isInViewport(el){
    var rect = el.getBoundingClientRect();
    
    return (
        rect.bottom >= 0 && 
        rect.right >= 0 && 
        rect.top <= (window.innerHeight || document.documentElement.clientHeight) && 
        rect.left <= (window.innerWidth || document.documentElement.clientWidth)
     );
}

function registerListener(event, func) {
    if (window.addEventListener) {
        window.addEventListener(event, func)
    } else {
        window.attachEvent('on' + event, func)
    }
}
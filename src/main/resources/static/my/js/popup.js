$(function(){

    // 판매자 정보 팝업 띄우기
    $('.latest .info .company > a').click(function(e){
        e.preventDefault();
        $('#popSeller').addClass('on');
    });

    // 문의하기 팝업 띄우기
    $('.btnQuestion').click(function(e){
        e.preventDefault();
        $('.popup').removeClass('on');
        $('#popQuestion').addClass('on');
    });

    // 주문상세 팝업 띄우기
    $('#detailOrder').click(function(e){
        e.preventDefault();
        $('#popOrder').addClass('on');
    });

    // 수취확인 팝업 띄우기
    $('.latest .confirm > .receive').click(function(e){
        e.preventDefault();
        $('#popReceive').addClass('on');
    });

    // 상품평 작성 팝업 띄우기
    $('.latest .confirm > .review').click(function(e){
        e.preventDefault();
        $('#popReview').addClass('on');
    });
               
    // 팝업 닫기
    $('.btnClose').click(function(){                
        $(this).closest('.popup').removeClass('on');                
    });

    // 상품평 작성 레이팅바 기능
    $(".my-rating").starRating({
        starSize: 20,
        useFullStars: true,
        strokeWidth: 0,
        useGradient: false,
        minRating: 1,
        ratedColors: ['#ffa400', '#ffa400', '#ffa400', '#ffa400', '#ffa400'],
        callback: function(currentRating, $el){
            alert('rated ' + currentRating);
            console.log('DOM element ', $el);
        }
    });

});
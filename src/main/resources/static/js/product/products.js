$(function () {

    loadProductBySearch();

    $("#range-slider").slider({
        min: 0,
        max: 5,
        step: 1,
        values: [0, 5],
        slide: function (event, ui) {
            for (let i = 0; i < ui.values.length; ++i) {
                $("input.sliderValue[data-index=" + i + "]").val(ui.values[i]);
                $("span.sliderText[data-index=" + i + "]").text(ui.values[i]);
            }
        }
    });

    $("#price-slider").slider({
        min: 0,
        max: 10000,
        step: 10,
        values: [0, 10000],
        slide: function (event, ui) {
            for (let i = 0; i < ui.values.length; ++i) {
                $("input.priceSliderValue[data-index=" + i + "]").val(ui.values[i]);
                $("span.priceSliderText[data-index=" + i + "]").text(ui.values[i]);
            }
        }
    });

    $('.btn-search-product').click(function () {
        loadProductBySearch();
    });

});


function loadProductBySearch() {
    $('.products').parent().showLoading();
    $.get('/product/search' +
        '?title=' + $('input[name=title]').val() +
        '&minPrice=' + $('input[name=min-price]').val() +
        '&maxPrice=' + $('input[name=max-price]').val() +
        '&minRate=' + $('input[name=min-rate]').val() +
        '&maxRate=' + $('input[name=max-rate]').val(),
        function (data) {
            $('html, body').animate({scrollTop: $('.products').offset().top}, 600 , function (){
                $('.products').html(data);
                $('.products').parent().hideLoading();
            });
        }).catch(function (xhr, ajaxOptions, thrownError) {
    });
}


$.fn.extend({
    disablez: function () {
        $(this).attr('disabled', 'disabled');
    },
    enablez: function () {
        $(this).removeAttr('disabled');
    },
    reset: function () {
        $('button[type=reset]', this).trigger('click');
    }, showLoading: function () {
        $('.loading', this).remove();
        $(this).append('<div class="loading"><div class="loading-image"></div></div>');
    }, hideLoading: function () {
        let _this = this;
        $('.loading', _this).fadeOut(function () {
            $('.loading', _this).remove();
        });
    }, showInfinityLoading: function () {
        $('.infinity-loading', this).remove();
        $(this).append('<div class="infinity-loading"><div class="loading-image"></div></div>');
    }, hideInfinityLoading: function () {
        let _this = this;
        $('.infinity-loading', _this).fadeOut(function () {
            $('.infinity-loading', _this).remove();
        });
    }, showSmallLoading: function () {
        $(this).append('<div class="sm-loading"></div>');
    }, showRedSmallLoading: function () {
        $(this).append('<div class="sm-red-loading"></div>');
    }, hideSmallLoading: function () {
        let _this = this;
        $('.sm-loading', _this).fadeOut(function () {
            $('.sm-loading', _this).remove();
        });
    }, showSimpleOverlay: function () {
        $(this).append('<div class="simple-overlay"></div>');
    }, hideSimpleOverlay: function () {
        let _this = this;
        $('.simple-overlay', _this).fadeOut(function () {
            $('.simple-overlay', _this).remove();
        });
    }, selectParentRow: function () {
        $(this).closest('tr').addClass('bg-update');
    }, deSelectParentRow: function () {
        $(this).closest('tr').addClass('bg-updated').removeClass('bu-update');
    }, selectParentRowDiv: function () {
        let parent = $(this).closest('div.row');
        $(parent).addClass('bg-update').removeClass('bg-updated');
        $('button', parent).attr('disabled', 'disabled');
        $('.alert-status', parent).attr('disabled', 'disabled');
    }, selectRowDiv: function () {
        $(this).removeClass('bg-updated');
        $(this).addClass('bg-update');
    }, deSelectRowDiv: function () {
        $(this).removeClass('bg-update').addClass('bg-updated');
        $('button', this).removeAttr('disabled');
        $('.alert-status', this).removeAttr('disabled');
        $('input[name=alertId]').val('');
        $('input[name=alertStatus]').val('');
    }, reloadOptions: function (data) {
        let _this = this;
        $('option', _this).each(function () {
            if ($(this).val() != '0') {
                $(this).remove();
            }
        }).promise().done(function () {
            for (let i = 0; i < data.length; i++) {
                $(_this).append('<option value="' + data[i].toLocaleLowerCase() + '">' + data[i] + '</option>')
            }
        });
    }, reloadLinkList: function (data) {
        let _this = this;
        for (let i = 0; i < data.length; i++) {
            $(_this).append('<a class="dropdown-item d-block text-left ltr" href="#" lang="' + data[i].toLocaleLowerCase() + '">' + data[i] + '</a>')
            $(_this).append('<hr/>')
        }
    }
});

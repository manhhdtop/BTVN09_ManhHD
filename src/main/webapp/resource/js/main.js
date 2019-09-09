(function ($) {
    "use strict";

    var users = ['manhhd', 'admin'];

    $('[name=password]').bind('keypress', addLetter);
    $('[name=username]').bind('keypress', keyUpUsername);

    $('#registerForm').submit(function (e) {
        e.preventDefault();
        let validated = validate();
        if (validated) {
            let name = $('[name=name]').val();
            let username = $('[name=username]').val();
            let password = $('[name=password]').val();

            for (let i = users.length - 1; i >= 0; i--) {
                if (users[i] == username) {
                    validated = false;
                    $('#errUsername').html('Tên tài khoản đã tồn tại. Vui lòng chọn tài khoản khác.');
                    $('[name=username]').addClass('border border-danger');
                    $('[name=username]').addClass('is-invalid');
                    $('[name=username]').removeClass('is-valid');
                    break;
                }
            }

            if (validated) {
                $('input').addClass('is-valid');
                $('input').removeClass('is-invalid');
                $('#success').html('Đăng ký tài khoản thành công!<br/>Xin chào <em>' + name + '</em>');
            }
        }
    });

    $(document).ready(function () {

    });

    var validate = () =;
>
    {
        let name = $('[name=name]').val();
        let username = $('[name=username]').val();
        let password = $('[name=password]').val();

        let passPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*._\-#?&])[A-Za-z\d@$!%*._\-#?&]{9,}$/;
        let pattern = new RegExp(passPattern);
        let valid = true;

        if ($.trim(name) == '') {
            $('#errName').html('Họ tên không được để trống.');
            $('[name=name]').addClass('is-invalid');
            $('[name=name]').removeClass('is-valid');
            valid = false;
        } else {
            $('#errName').html('');
            $('[name=name]').addClass('is-valid');
            $('[name=name]').removeClass('is-invalid');
        }

        if ($.trim(username) == '') {
            $('#errUsername').html('Tên tài khoản không được để trống.');
            $('[name=username]').addClass('is-invalid');
            $('[name=username]').removeClass('is-valid');
            valid = false;
        } else {
            $('#errUsername').html('');
            $('[name=username]').addClass('is-valid');
            $('[name=username]').removeClass('is-invalid');
        }

        if (!pattern.test(password)) {
            $('#errPassword').html('Mật khẩu bao gồm chữ, số và có trên 9 ký tự.');
            $('[name=password]').addClass('is-invalid');
            $('[name=password]').removeClass('is-valid');
            valid = false;
        } else {
            $('#errPassword').html('');
            $('[name=password]').addClass('is-valid');
            $('[name=password]').removeClass('is-invalid');
        }


        return valid;
    }

    function addLetter(event) {
        let value = String.fromCharCode(event.which);
        let pattern = new RegExp(/[A-Za-z@$!%*._\-#?&\d]/);
        $('#errPassword').html('');
        $('[name=password]').removeClass('border border-danger');
        return pattern.test(value);
    }

    function keyUpUsername(event) {
        $('#errUsername').html('');
        $('[name=username]').removeClass('border border-danger');
    }


}(jQuery));
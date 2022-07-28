$(document).ready(function () {
    setNavigation();
    $('.all-task a').click();
});

function setNavigation () {
    $('.all-task a').click(function (e) {
        e.preventDefault();
        loadItems('all');
        $('.todo-nav a.active').removeClass('active');
        $(this).addClass('active');
    });

    $('.done-task a').click(function (e) {
        e.preventDefault();
        loadItems('done');
        $('.todo-nav a.active').removeClass('active');
        $(this).addClass('active');
    });

    $('.new-task a').click(function (e) {
        e.preventDefault();
        loadItems('new');
        $('.todo-nav a.active').removeClass('active');
        $(this).addClass('active');
    });
}

function loadItems (path) {
    $.get(
        `${window.location.origin}/items/${path}`,
        'html'
    )
        .done(items => {
            $('.todo-list').html(items);
            setCheckboxCallback();
            setDeleteCallback();
        })
}

function setCheckboxCallback () {
    $('.checker input').change(function () {
        const id = $(this).closest('.todo-item').attr('id');
        const done = $(this).is(':checked');
        $.post(
            `${window.location.origin}/items/update_checkbox`,
            {
                id,
                done
            })
            .done(()=> {
                $('.todo-nav a.active').click(); //обновляем список задач
            })
        })
}

function setDeleteCallback () {
    $('.delete-item').click(function (e) {
        e.preventDefault();
        const id = $(this).closest('.todo-item').attr('id');
        $.ajax({
            type: 'DELETE',
            url: `${window.location.origin}/items/${id}`
        })
            .done(() => {
                $('.todo-nav a.active').click();
            })
    })
}

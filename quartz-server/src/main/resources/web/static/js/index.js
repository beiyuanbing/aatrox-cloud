$(function () {
    toastr.options.positionClass = 'toast-top-center';
    /**执行**/
    $(".btnRun").click(function () {
        var quartzId = $(this).parent().data("id");
        $.ajax({
            url: "/api/runJob?t=" + new Date().getTime(),
            type: "POST",
            data: JSON.stringify(quartzId),
            dataType: "json",
            contentType: "application/json;",
            success: function (result) {
                if (result.code == "200") {
                    toastr.success(result.msg);
                } else {
                    toastr.error(result.msg);
                }
            }
        });
    });
    /**暂停 job**/
    $(".btnPause").click(function () {
        var quartzId = $(this).parent().data("id");
        $.ajax({
            url: "/api/pauseJob?t=" + new Date().getTime(),
            type: "POST",
            data: JSON.stringify(quartzId),
            contentType: "application/json;",
            dataType: "JSON",
            success: function (result) {
                if (result.code == "200") {
                    toastr.success(result.msg);
                    setTimeout(function () {
                        location.reload()
                    }, 1500);
                } else {
                    toastr.error(result.msg);
                }
            }
        });
    });
    /**resume job**/
    $(".btnResume").click(function () {
        var quartzId = $(this).parent().data("id");
        $.ajax({
            url: "/api/resumeJob?t=" + new Date().getTime(),
            type: "POST",
            data: JSON.stringify(quartzId),
            contentType: "application/json;",
            dataType: "JSON",
            success: function (result) {
                if (result.code == "200") {
                    toastr.success(result.msg);
                    setTimeout(function () {
                        location.reload()
                    }, 1500);
                } else {
                    toastr.error(result.msg);
                }
            }
        });
    });
    /**delete job**/
    $(".btnDelete").click(function () {
        var quartzId = $(this).parent().data("id");
        if (window.confirm("Are you sure?")) {
            $.ajax({
                url: "/api/deleteJob?t=" + new Date().getTime(),
                type: "POST",
                dataType: "JSON",
                data: JSON.stringify(quartzId),
                contentType: "application/json;",
                success: function (result) {
                    if (result.code == "200") {
                        toastr.success(result.msg);
                        location.reload();
                    } else {
                        toastr.error(result.msg);
                    }
                }
            });
        }
    });

    /** update**/
    $(".btnEdit").click(
        function () {
            $("#quartzModalLabel").html("cron edit");
            var quartzId = $(this).parent().data("id");
            $.ajax({
                url: "/api/selectJob?t=" + new Date().getTime(),
                type: "POST",
                dataType: "JSON",
                data: {"quartzId": quartzId},
                success: function (result) {
                    if (result.code == "200") {
                        let data = result.data;
                        fileForm('mainForm', data);
                        let array = ["jobName", "jobGroup", "status"];
                        //array.push("jobName", "jobGroup", "status");
                        disabledElem('mainForm', array);
                        $("#cron_val").val(data.cronExpression);
                    } else {
                        toastr.error(result.msg);
                    }
                }
            });
            $("#resetBtn").hide();
            $("#quartzModal").modal("show");
        });

    /**保存按钮**/
    $("#save").click(
        function () {
            var array = [];
            array.push("jobName", "jobGroup", "status");
            removeDisabledElem("mainForm", array);
            let object = serializeObjectJson('mainForm');
            $.ajax({
                url: "/api/saveOrUpdate?t=" + new Date().getTime(),
                type: "POST",
                dataType:"JSON",
                data: object,
                contentType: "application/json;",
                success: function (result) {
                    if (result.code == "200") {
                        toastr.success(result.msg);
                        setTimeout(function () {
                            $("#quartzModal").modal("hide");
                            location.reload();
                        }, 1500);

                    } else {
                        toastr.error(result.msg);
                    }
                }
            });
        });
    $("#resetBtn").click(function () {
        cleanForm("mainForm");
    });

    /** 创建 job**/
    $("#createBtn").click(
        function () {
            $("#resetBtn").show();
            $("#quartzModalLabel").html("create job");
            cleanForm("mainForm");
            var array = ["jobName", "jobGroup", "status"];
            //array.push("jobName", "jobGroup", "status");
            removeDisabledElem("mainForm", array);
            $("#quartzModal").modal("show");
        });

    /*    $("#edit_type").change(function () {
            if ("dynamic" == $("#edit_type").val()) {
                $("#classFile").show();
                $("#edit_content").attr("placeholder", "className...");
            } else if ("java" == $("#edit_type").val()) {
                $("#classFile").hide();
                $("#edit_content").attr("placeholder", "path or name...");
            } else if ("shell" == $("#edit_type").val()) {
                $("#classFile").hide();
                $("#edit_content").attr("placeholder", "command...");
            }
        });*/

    $("#cronExpression").cronGen({
        direction: 'right'
    });

});
/**
 * 表单序列化成对象
 * @param formId
 * @returns {string|null}
 */
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

/**
 * 表单序列化成json
 * @param formId
 * @returns {string|null}
 */
function serializeObjectJson(formId) {
    let object = $("#" + formId).serializeObject();
    if (object == null) {
        return null;
    }
    return JSON.stringify(object);
}

/**
 * 页面填充值
 * @param formId
 * @param data
 */
function fileForm(formId, data) {
    if (data == null) {
        return;
    }
    $("#" + formId + " input,select,textarea").each(function (index, element) {
        if ($(element).attr('type') == 'radio') {
            return;
        }
        $(element).val(getData(data, element.name));
    });
}

/**
 * 获取值
 * @param data
 * @param key
 * @returns {*}
 */
function getData(data, key) {
    var value = data;
    var keys = key.split('.');
    keys.forEach(function (e) {
        value = value[e];
    });
    return value;
}

/**
 * 控制表单的编辑
 */
function disabledElem(formId, array) {
    $.each(array, function (index, value) {
        $('#' + formId + " input[name='" + value + "']").attr("readonly", "readonly");
        $('#' + formId + " input[name='" + value + "']").attr("disabled", "disabled");
        $('#' + formId + " select[name='" + value + "']").attr("readonly", "readonly");
        $('#' + formId + " select[name='" + value + "']").attr("disabled", "disabled");
    });
}

/**
 * 删除不可编辑的样式
 * @param formId
 * @param array
 */
function removeDisabledElem(formId, array) {
    $.each(array, function (index, value) {
        $('#' + formId + " input[name='" + value + "']").removeAttr("readonly");
        $('#' + formId + " input[name='" + value + "']").removeAttr("disabled");
        $('#' + formId + " select[name='" + value + "']").removeAttr("readonly");
        $('#' + formId + " select[name='" + value + "']").removeAttr("disabled");
    });
}

/**
 * 清除表单
 * @param formId
 */
function cleanForm(formId) {
    $('#' + formId)[0].reset();
}
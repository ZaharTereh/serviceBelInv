$('#thirdLevelProductGroup').change(function () {
    let productGroupId = {};
    productGroupId["id"] = $(this).val();
    $.ajax({
        contentType: 'application/json',
        type: "POST",
        dataType: "json",
        data: JSON.stringify(productGroupId),
        url: "http://localhost:8080/hierarchy_update/get_hierarchies_by_group",
        success: function (data){
            console.log(data);
            deleteOptionsFromSelect("#hierarchy-list")
            let hierarchyList = $('#hierarchy-list');
            data.forEach(elem => {
                let option = document.createElement('option');
                option.value = elem.id;
                option.text = elem.name;
                option.className = "hierarchyOption";
                hierarchyList.append(option);
            })
            $('#hierarchy-list').val(0);
        } ,
        fail: function () {
            console.log('error');
        }
    });
});

$('#firstLevelProductGroup').change(function(){
    fillSecondLevelProductGroupFindForm();
});

$('#secondLevelProductGroup').change(function(){
    fillThirdLevelProductGroupFindForm();
});

$('#find-form').submit(function (){
    return validateFindForm();
});

function validateFindForm() {
    return true;
}

function fillSecondLevelProductGroupFindForm() {

    deleteOptionsFromSelect("#secondLevelProductGroup");
    deleteOptionsFromSelect("#thirdLevelProductGroup");

    fillProductGroupList('#firstLevelProductGroup','#secondLevelProductGroup');

    $('#secondLevelProductGroup').val(0);
    $('#thirdLevelProductGroup').val(0);
}

function fillThirdLevelProductGroupFindForm() {
    deleteOptionsFromSelect("#thirdLevelProductGroup");

    fillProductGroupList('#secondLevelProductGroup','#thirdLevelProductGroup');

    $('#thirdLevelProductGroup').val(0);

}

function fillProductGroupList(currentLevelSelector,nextLevelSelector) {
    let productGroups = parseProductGroups();
    let currentLevel = $(currentLevelSelector).val();
    let nextLevel = $(nextLevelSelector);

    productGroups.forEach(el => {
        if(el.hiId === currentLevel){
            let option = document.createElement('option');
            option.value = el.id;
            option.text = el.name;
            option.setAttribute("tableName",el.tableName);
            $(nextLevel).append(option);
        }
    });
}

function deleteOptionsFromSelect(selector) {
    document.querySelectorAll(selector).forEach(el => {
        $(el).children().each(function () {
            if(parseInt($(this).val()) !== 0){
                $(this).remove();
            }
        });
    })
}

function parseProductGroups() {
    let productGroups = [];
    $(".productGroupData").each(function () {
        let productGroup = {};
        productGroup["id"] = $(this).find('.id').text();
        productGroup["hiId"] = $(this).find('.hiId').text();
        productGroup["name"] = $(this).find('.name').text();
        productGroup["lev"] = $(this).find('.lev').text();
        productGroup["leaf"] = $(this).find('.leaf').text();
        productGroup["tableName"] = $(this).find('.tableName').text();
        productGroups.push(productGroup);
    })
    return productGroups;
}

$('#product-table').on('click', 'tbody tr', function () {
    $(this).addClass('selected').siblings().removeClass('selected');
    $('.nsi-directory-page-buttons button').removeAttr('disabled');

    if ($(this).attr("type") === "NEW"){
        $(document).find('.last-level').each(function (){
            $(this).find('input').val('');
        })
    }
    else {
        $(this).find("[data-update-hierarchy]").each(function (){
            let elem = $(".last-level").find('[name=' + this.className + ']');
            elem.val($(this).text());
        });
    }

});

$('.save-change-level-button').on('click',function () {
    let data = {};
    data["productsId"] = [];
    data["fields"] = {};
    data["productGroup"] = $(document).find('.hierarchy .productGroupId').attr("value");

    $(document).find('#product-table td .id').each(function () {
        data.productsId.push($(this).html());
    });

    $(this).closest('.level-data').find('tbody tr').each(function () {
        data.fields["\"" + $(this).find('.fieldName').attr("fieldName")+"\""] = $(this).find('.value input').val();
    });

    $.ajax({
        contentType: 'application/json',
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        url: "http://localhost:8080/hierarchy_update/update_level",
        success: function (data){
            console.log(data);
        } ,
        fail: function () {
            console.log('error');
        }
    });
});

$('.save-change-product-button').on('click',function () {
    let data = {};
    data["productsId"] = [];
    data["fields"] = {};
    data["tableName"] = $(document).find('.hierarchy .tableName').attr("value");

    $(document).find('#product-table tr.selected td.id').each(function () {
        data.productsId.push($(this).html());
    })

    $(this).closest('.level-data').find('.field-for-level tbody tr').each(function () {
        data.fields["\"" + $(this).find('.fieldName').attr("fieldName")+"\""] = {"value"   : $(this).find('.value input').val(),
                                                                                 "dataType": $(this).find('.fieldName').attr("dataType")};
    });

    $.ajax({
        contentType: 'application/json',
        type: "POST",
        dataType: "json",
        data: JSON.stringify(data),
        url: "http://localhost:8080/hierarchy_update/update_level",
        success: function (data){
            console.log(data);
        } ,
        fail: function () {
            console.log('error');
        }
    });
})
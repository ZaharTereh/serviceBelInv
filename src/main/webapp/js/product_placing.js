$('#firstLevelProductGroup').change(function(){
    fillSecondLevelProductGroupFindForm();
});

$('#secondLevelProductGroup').change(function(){
    fillThirdLevelProductGroupFindForm();
});

$('#thirdLevelProductGroup').change(function(){
    fillFourthLevelProductGroupFindForm();
});

$('#fourthLevelProductGroup').change(function(){
    fillFifthLevelProductGroupFindForm();
});

$('#species-of-product').change(function(){
    fillSubSpeciesOfProduct();
})

function fillSecondLevelProductGroupFindForm() {

    deleteOptionsFromSelect("#secondLevelProductGroup");
    deleteOptionsFromSelect("#thirdLevelProductGroup");
    deleteOptionsFromSelect("#fourthLevelProductGroup");
    deleteOptionsFromSelect("#fifthLevelProductGroup");

    fillProductGroupList('#firstLevelProductGroup','#secondLevelProductGroup');

    $('#secondLevelProductGroup').val(0);
    $('#thirdLevelProductGroup').val(0);
    $('#fourthLevelProductGroup').val(0);
    $('#fifthLevelProductGroup').val(0);
}

function fillThirdLevelProductGroupFindForm() {
    deleteOptionsFromSelect("#thirdLevelProductGroup");
    deleteOptionsFromSelect("#fourthLevelProductGroup");
    deleteOptionsFromSelect("#fifthLevelProductGroup");

    fillProductGroupList('#secondLevelProductGroup','#thirdLevelProductGroup');

    $('#thirdLevelProductGroup').val(0);
    $('#fourthLevelProductGroup').val(0);
    $('#fifthLevelProductGroup').val(0);
}

function fillFourthLevelProductGroupFindForm() {
    deleteOptionsFromSelect("#fourthLevelProductGroup");
    deleteOptionsFromSelect("#fifthLevelProductGroup");

    fillProductGroupList('#thirdLevelProductGroup','#fourthLevelProductGroup');

    $('#fourthLevelProductGroup').val(0);
    $('#fifthLevelProductGroup').val(0);
}

function fillFifthLevelProductGroupFindForm() {
    deleteOptionsFromSelect("#fifthLevelProductGroup");

    fillProductGroupList('#fourthLevelProductGroup','#fifthLevelProductGroup');

    $('#fifthLevelProductGroup').val(0);
}

function fillProductGroupList(currentLevelSelector,nextLevelSelector) {
    let productGroups = parseProductGroups();
    let currentLevel = $(currentLevelSelector).val();
    let nextLevel = $(nextLevelSelector);
    productGroups.forEach(el => {
        if(el.hiId === currentLevel && el.hiId !== ""){
            let option = document.createElement('option');
            option.value = el.id;
            option.text = el.name;
            option.setAttribute("tableName",el.tableName);
            $(nextLevel).append(option);
        }
    });
}

function parseProductGroups() {
    let productGroups = [];
    $(".productGroupData").each(function () {
        let productGroup = {};
        productGroup["id"] = $(this).find('.id').text();
        productGroup["hiId"] = $(this).find('.hiId').text();
        productGroup["name"] = $(this).find('.name').text();
        productGroup["lev"] = $(this).find('.lev').text();
        productGroup["tableName"] = $(this).find('.tableName').text();
        productGroups.push(productGroup);
    })
    return productGroups;
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

$('#tree').on('click', '.Product .Content', function () {
    $('#tree').find('.Product').each(function () {
        $(this).removeClass('selected');
    })

    $(this).closest('.Product').addClass('selected').siblings().removeClass('selected');
    $('#edit-product-placing-button').removeAttr('disabled');
    $('#check-product-button').removeAttr('disabled');
});

$('#edit-product-placing-button').on('click',function (){
    transformProductDataToForm();
    doProductFormActive();
    showProductForm();
})

$('#check-product-button').on('click',function (){
    transformProductDataToForm();
    disabledProductForm();
    showProductForm();
})

$('#save-product-change-button').on('click',function (){
    let id = $('.info-product-block').find('.id-of-product').attr("value");
    let bnId = $('#species-of-product').val();
    let dbId = $('#subspecies-of-product').val();

    $.ajax({
        contentType: 'application/json',
        type: "POST",
        dataType: "json",
        data: JSON.stringify({"id" : id, "bnId" : bnId , "dbId" : dbId}),
        url: "http://localhost:8080/product_placing/update",
        success: function (data) {
            alert("Продукт успешно обновлён")
        }
    });
})

$('#cancel-product-change-button').on('click',function (){
    clearProductForm();
    disabledProductForm();
    hideProductForm();
})

function showProductForm() {
    let productBlock = $('.info-product-block');
    $(productBlock).show();
    $(productBlock).removeAttr("hidden");
}

function transformProductDataToForm() {
    let product = $('#tree .selected');
    let productBlock = $('.info-product-block');

    createSpeciesForProduct($(product).find('.hiId').html());

    $(productBlock).find('.id-of-product').attr("value",$(product).find('.id').html());
    $(productBlock).find('.name-of-product').html($(product).find('.name').html());
    $(productBlock).find('.code-of-product').html($(product).find('.code').html());
    $(productBlock).find('#species-of-product').val($(product).find('.bnId').html());
    fillSubSpeciesOfProduct();
    $(productBlock).find('#subspecies-of-product').val($(product).find('.dbId').html());
}

function clearProductForm() {
    let productBlock = $('.info-product-block');
    $(productBlock).find('.id-of-product').attr("value",'');
    $(productBlock).find('.name-of-product').html('');
    $(productBlock).find('.code-of-product').html('');
    deleteOptionsFromSelect("#species-of-product")
    deleteOptionsFromSelect("#subspecies-of-product")
}

function doProductFormActive() {
    let productBlock = $('.info-product-block');
    $(productBlock).find('#species-of-product').removeAttr("disabled");
    $(productBlock).find('#subspecies-of-product').removeAttr("disabled");
    $(productBlock).find('#save-product-change-button').removeAttr("disabled");
}

function disabledProductForm() {
    let productBlock = $('.info-product-block');
    $(productBlock).find('#species-of-product').attr("disabled",'');
    $(productBlock).find('#subspecies-of-product').attr("disabled",'');
    $(productBlock).find('#save-product-change-button').attr("disabled",'');

}

function hideProductForm() {
    $('.info-product-block').hide();
}

function fillSubSpeciesOfProduct() {
    deleteOptionsFromSelect("#subspecies-of-product");
    let productGroups = parseProductGroups();
    let currentLevel = $('#species-of-product').val();
    let nextLevel = $('#subspecies-of-product');
    addEmptyOption(nextLevel);
    productGroups.forEach(el => {
        if(el.hiId === currentLevel && el.hiId !== ""){
            let option = document.createElement('option');
            option.value = el.id;
            option.text = el.name;
            option.setAttribute("tableName",el.tableName);
            $(nextLevel).append(option);
        }
    });
    $('#subspecies-of-product').val(0);
}

function createSpeciesForProduct(groupId) {
    let productGroups = parseProductGroups();
    let speciesForProduct = $('#species-of-product');

    deleteOptionsFromSelect("#species-of-product");
    addEmptyOption(speciesForProduct);
    productGroups.forEach(el => {
        if(el.hiId === groupId){
            let option = document.createElement('option');
            option.value = el.id;
            option.text = el.name;
            $(speciesForProduct).append(option);
        }
    });
}

function addEmptyOption(selectBlock) {
    $(selectBlock).append(document.createElement('option'));
}


$('#input-product-group-level-3-name-create').change( function () {

    deleteOptionsFromSelect('.criterionForLevel');
    deleteOptionsFromSelect('.fieldForLevel');
    deleteOptionsFromSelect('.result-list-of-field-for-level');

    $('.criterionForLevel').val(0);
    $('.fieldForLevel').val(0);

    $.ajax({
        contentType: 'application/json',
        type: "POST",
        dataType: "json",
        data: JSON.stringify({"tableName": getTableNameByProductId($(this).val()),
                                    "productGroupId": $(this).val()}),
        url: "http://localhost:8080/product_hierarchy/get_hierarchy_level",
        success: function (data){
            addOptionToSelect('.criterionForLevel',data.criterionList,"criterionOption");
            addOptionToSelect('.fieldForLevel',data.fieldList,"fieldOption");
            $('.criterionForLevel').each( function(){
                removeSelectOptionFromOtherCriterion(this);

            })
        } ,
        fail: function () {
            console.log('error');
        }
    });

});

$(document).on('change','.criterionForLevel', function (){
    removeSelectOptionFromOtherCriterion(this);
})

$(document).on('click','.plus-field-button', function () {
    let parent = $(this).closest('.level-of-hierarchy');
    addFieldToResultList(parent);
});

$(document).on('click','.minus-field-button', function () {
    let parent = $(this).closest('.level-of-hierarchy');
    deleteFieldFromResultList(parent);
});

$('.input-quantity-of-level').change( function () {
    redrawLevelsOfHierarchy();
})

$('.edit-nsi-modal-button').on('click',function (){
    doFormActive();
    clearEditForm();

    let data = $('#product-hierarchy-table').find('.selected');
    transportDataFromTableToForm(data);

    redrawLevelsOfHierarchy();


    let listOfFields = JSON.parse($(data).find('.productHierarchyStructs').text().replace('\t',''));

    let productGroupId = document.getElementById('input-product-group-level-3-name-create').value;

    $.ajax({
        contentType: 'application/json',
        type: "POST",
        dataType: "json",
        data: JSON.stringify({"productGroupId" : productGroupId,
                                    "tableName" : getTableNameByProductId(productGroupId)}),
        url: "http://localhost:8080/product_hierarchy/get_hierarchy_level",
        success: function (data) {
            addOptionToSelect('.criterionForLevel',data.criterionList,"criterionOption");
            addOptionToSelect('.fieldForLevel',data.fieldList,"fieldOption");

            let level = 1;
            let selectValueCriterion;
            $('.criterionForLevel').each(function () {
                listOfFields.forEach(elemList => {
                        if (elemList.crit !== null && elemList.level === level) {
                            this.querySelectorAll(".criterionOption").forEach(
                                el => {
                                    if (el.value === elemList.crit.toString()) {
                                        selectValueCriterion = el.value;
                                    }
                                }
                            )
                        }
                    }
                )
                this.value = selectValueCriterion;
                removeSelectOptionFromOtherCriterion(this);
                level++;
            })


            level = 1;
            $('.fieldForLevel').each(function () {
                listOfFields.forEach(elemList => {
                        if (elemList.crit === null && elemList.level === level) {
                            this.querySelectorAll(".fieldOption").forEach(
                                el => {
                                    /*if (el.textContent.replace('\t','') === elemList.fieldName) {
                                        this.value = el.value;
                                        let parent = this.closest('.level-of-hierarchy');
                                        addFieldToResultList(parent);
                                    }*/
                                    if($(el).attr("fieldname") === elemList.fieldName){
                                        this.value = el.value;
                                        let parent = this.closest('.level-of-hierarchy');
                                        addFieldToResultList(parent);
                                    }
                                }
                            )
                        }
                    }
                )
                level++;
            })

        }
    });
    moveToEditForm();
})

$('#product-hierarchy-table').on('click', 'tbody tr', function () {
    $(this).addClass('selected').siblings().removeClass('selected');
    $('.nsi-directory-page-buttons button').removeAttr('disabled');
});

$('.create-nsi-modal-button').on('click', function () {
    doFormActive();
    clearEditForm();
    redrawLevelsOfHierarchy();
    deleteSelectionFromElements();
    moveToEditForm();
});

$('.create-cancel-nsi-entry-button').on('click', function (){
    clearEditForm();
    redrawLevelsOfHierarchy();
    deleteSelectionFromElements();
    blockForm();
});

$('.create-nsi-entry-submit-button').on('click', function (){
    if(validateForm()){
        let data = parseUserInput();
        $.ajax(
            {
                contentType: 'application/json',
                type: "POST",
                dataType: "json",
                data: JSON.stringify(data),
                url: "http://localhost:8080/product_hierarchy/create",
                success: function () {
                    clearEditForm();
                    blockForm();
                },
                fail: function () {
                    console.log('error');
                }
            }
        );
        clearEditForm();
        blockForm();
    }

});

$('.input-product-hierarchy-name-create').change(function(){
    checkNameUniqueness();
});

$('#firstLevelProductGroup').change(function(){
    fillSecondLevelProductGroupFindForm();
});

$('#secondLevelProductGroup').change(function(){
    fillThirdLevelProductGroupFindForm();
});

$('#input-product-group-level-1-name-create').change(function () {
    fillSecondLevelProductGroupCreateForm();
})

$('#input-product-group-level-2-name-create').change(function () {
    fillThirdLevelProductGroupCreateForm();
})

function fillSecondLevelProductGroupCreateForm() {
    deleteOptionsFromSelect('#input-product-group-level-2-name-create');
    deleteOptionsFromSelect('#input-product-group-level-3-name-create');

    fillProductGroupList('#input-product-group-level-1-name-create','#input-product-group-level-2-name-create');
    $('#input-product-group-level-2-name-create').val(0);
    $('#input-product-group-level-3-name-create').val(0);
}

function fillThirdLevelProductGroupCreateForm() {
    deleteOptionsFromSelect('#input-product-group-level-3-name-create');

    fillProductGroupList('#input-product-group-level-2-name-create','#input-product-group-level-3-name-create');

    $('#input-product-group-level-3-name-create').val(0);
}

function fillSecondLevelProductGroupFindForm() {

    deleteOptionsFromSelect("#secondLevelProductGroup");
    deleteOptionsFromSelect("#thirdLevelProductGroup");

    fillProductGroupList('#firstLevelProductGroup','#secondLevelProductGroup');
}

function fillThirdLevelProductGroupFindForm() {
    deleteOptionsFromSelect("#thirdLevelProductGroup");

    fillProductGroupList('#secondLevelProductGroup','#thirdLevelProductGroup');
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
    })
}

function getTableNameByProductId(productId) {
    let result;
    let productGroups = parseProductGroups();
    productGroups.forEach(el => {
        if(el.id === productId){
            result = el.tableName;
        }
    })
    return result;
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

function deleteFieldFromResultList(levelOfHierarchyElement) {
    let indexOfLevel = $(levelOfHierarchyElement).index()+1;
    let option = $(levelOfHierarchyElement).find('.result-list-of-field-for-level option:selected');
    let resultSet = $(levelOfHierarchyElement).find('.fieldForLevel');


    $('.hidden-list-of-field-for-level').each( function() {
        if($(this).closest('.level-of-hierarchy').index()+1 !== indexOfLevel){
            $(this).find('option').each(function () {
                if($(this).attr('changeBy') == indexOfLevel && $(this).text() === $(option).text()){
                    $(this).removeAttr('changeBy');
                    let fieldList = $(this).closest('.level-of-hierarchy').find('.fieldForLevel');
                    $(fieldList).append($(this));
                }
            });
        }
    })

    if($(option).attr("value")!=='0'){
        resultSet.append(option);
    }
}

function deleteAllFieldsFromResultList(levelOfHierarchyElement) {
    let indexOfLevel = $(levelOfHierarchyElement).index()+1;

    $('.hidden-list-of-field-for-level').each( function() {
        if($(this).closest('.level-of-hierarchy').index()+1 !== indexOfLevel){
            $(this).find('option').each(function () {
                if($(this).attr('changeBy') == indexOfLevel){
                    $(this).removeAttr('changeBy');
                    let fieldList = $(this).closest('.level-of-hierarchy').find('.fieldForLevel');
                    $(fieldList).append($(this));
                }
            });
        }
    })
}

function addFieldToResultList(levelOfHierarchyElement) {
    let indexOfLevel = $(levelOfHierarchyElement).index()+1;
    let option = $(levelOfHierarchyElement).find('.fieldForLevel option:selected');
    let resultSet = $(levelOfHierarchyElement).find('.result-list-of-field-for-level');

    $('.fieldForLevel').each( function() {
        if($(this).closest('.level-of-hierarchy').index()+1 !== indexOfLevel){
            $(this).find('option').each(function () {
                if($(this).text() === $(option).text()){
                    let hiddenList = $(this).closest('.level-of-hierarchy').find('.hidden-list-of-field-for-level');
                    $(this).attr('changeBy', indexOfLevel);
                    $(hiddenList).append($(this));
                }
            });
        }
    })

    if($(option).attr("value")!=='0'){
        resultSet.append(option);
    }
}

function removeSelectOptionFromOtherCriterion(element) {
    let indexOfLevel = $(element).closest('.level-of-hierarchy').index()+1;
    let selectedCriterion = $(element).find('option:selected').text();

    $('.criterionForLevel').each( function() {
        if($(this).closest('.level-of-hierarchy').index()+1 !== indexOfLevel){
            $(this).find('option').each(function () {

                if($(this).attr('changeBy') == indexOfLevel){
                    $(this).removeAttr('hidden');
                    $(this).removeAttr('changeBy');
                }

                if($(this).text() === selectedCriterion){
                    $(this).attr('hidden', '');
                    $(this).attr('changeBy', indexOfLevel);
                }
            });
        }
    })
}

function addOptionToSelect(listSelector,dataList,classNameOfOptions) {
    $(listSelector).each(function () {
        dataList.forEach( el => {
            let option = document.createElement('option');
            option.value = el.id;
            option.text = el.name;
            option.setAttribute("fieldName",el.fieldName);
            option.className = classNameOfOptions;
            $(this).append(option);
        })
    });
}

function transportDataFromTableToForm(data) {
    data.find("[data-product-hierarchy]").each(function () {
        let elem = $(".edit-panel").find('[name=' + this.className + ']');
        elem.val($(this).text());
    });

    if(data.find('.activity').text() === "1"){
        $('#input-product-hierarchy-activity-create').prop('checked', "checked");
    }else {
        $('#input-product-hierarchy-activity-create').prop('checked', false);
    }

    let productGroupTree = {};
    productGroupTree["thirdProductGroup"] = JSON.parse(data.find('.thirdProductGroup').text());
    productGroupTree["secondProductGroup"] = JSON.parse(data.find('.secondProductGroup').text());
    productGroupTree["firstProductGroup"] = JSON.parse(data.find('.firstProductGroup').text());

    console.log(productGroupTree);

    $('#input-product-group-level-1-name-create').val(productGroupTree.firstProductGroup.id);
    fillSecondLevelProductGroupCreateForm();
    $('#input-product-group-level-2-name-create').val(productGroupTree.secondProductGroup.id);
    fillThirdLevelProductGroupCreateForm()
    $('#input-product-group-level-3-name-create').val(productGroupTree.thirdProductGroup.id);
}

function findProductGroupHiIdById(id) {
    let productGroups = parseProductGroups();
    console.log(productGroups);

    let result;
    productGroups.forEach( el=> {
        console.log("this id = " + el.id + " id = " + id);
        if(el.id === id){
            console.log("true");
            result = el.hiId;
        }
    })
    return result;
}

function blockForm(){
    $('.create-nsi-entry-submit-button').attr('disabled','');
    $('.create-cancel-nsi-entry-button').attr('disabled','');
    $('#input-product-group-level-1-name-create').attr('disabled','');
    $('#input-product-group-level-2-name-create').attr('disabled','');
    $('#input-product-group-level-3-name-create').attr('disabled','');
    $('#input-product-hierarchy-name-create').attr('disabled','');
    $('#input-product-hierarchy-levelQuant-create').attr('disabled','');
    $('#input-product-hierarchy-date-begin-create').attr('disabled','');
    $('#input-product-hierarchy-date-end-create').attr('disabled','');
    $('#input-product-hierarchy-activity-create').attr('disabled','');
    $('#select-criterion-for-level').attr('disabled','');
    $('#select-field-for-level').attr('disabled','');
    $('#result-list-of-field-for-level').attr('disabled','');
    $('.plus-field-button').attr('disabled','');
    $('.minus-field-button').attr('disabled','');
}

function doFormActive() {

    $('.create-nsi-entry-submit-button').removeAttr('disabled');
    $('.create-cancel-nsi-entry-button').removeAttr('disabled');
    $('#input-product-group-level-1-name-create').removeAttr('disabled');
    $('#input-product-group-level-2-name-create').removeAttr('disabled');
    $('#input-product-group-level-3-name-create').removeAttr('disabled');
    $('#input-product-hierarchy-name-create').removeAttr('disabled');
    $('#input-product-hierarchy-levelQuant-create').removeAttr('disabled');
    $('#input-product-hierarchy-date-begin-create').removeAttr('disabled');
    $('#input-product-hierarchy-date-end-create').removeAttr('disabled');
    $('#input-product-hierarchy-activity-create').removeAttr('disabled');
    $('#select-criterion-for-level').removeAttr('disabled');
    $('#select-field-for-level').removeAttr('disabled');
    $('#result-list-of-field-for-level').removeAttr('disabled');
    $('.plus-field-button').removeAttr('disabled');
    $('.minus-field-button').removeAttr('disabled');

}

function clearEditForm() {
    $('.error-input-text').html('');
    $('#hierarchy-id').val("");
    $('#input-product-hierarchy-name-create').val("");
    $('#input-product-group-level-1-name-create').val(0);
    $('#input-product-group-level-2-name-create').val(0);
    $('#input-product-group-level-3-name-create').val(0);
    $('#input-product-hierarchy-date-begin-create').val("");
    $('#input-product-hierarchy-date-end-create').val("");
    $('#input-product-hierarchy-activity-create').prop('checked', false);
    $('#input-product-hierarchy-levelQuant-create').val(1)


    while ($('.levels-of-hierarchy').children().length !== 1){
        $('.levels-of-hierarchy').children().last().remove();
    }

    deleteOptionsFromSelect('.criterionForLevel');
    deleteOptionsFromSelect('.fieldForLevel');
    deleteOptionsFromSelect('.result-list-of-field-for-level');

    $('.criterionForLevel').val(0);
    $('.fieldForLevel').val(0);
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

function deleteOptionsFromResultList(level) {
    $(level).find('.result-list-of-field-for-level option').each(function () {
        $(this).remove();
    });
}

function checkNameUniqueness() {
    let response = false;
    let productHierarchy = {};
    productHierarchy["name"] = $('#input-product-hierarchy-name-create').val();
    if(productHierarchy.name!==''){
        $.ajax(
            {
                contentType:"application/json",
                type:"POST",
                url: "http://localhost:8080/product_hierarchy/get_hierarchy_by_name",
                dataType:"json",
                data:JSON.stringify(productHierarchy),
                success:function(data){
                    console.log("Data is" + data);
                    response = data;
                },
                fail: function () {
                    console.log('error');
                }
            }
        );
    }
    return response;
}

function redrawLevelsOfHierarchy(){
    let levels = $(".levels-of-hierarchy").children('.level-of-hierarchy').length;
    let inputQuantity = parseInt($('#input-product-hierarchy-levelQuant-create').val());
    if(inputQuantity > 0 && inputQuantity < 10) {
        if (levels > inputQuantity) {
            removeLevels(levels - inputQuantity);
        } else if (levels < inputQuantity) {
            addLevels(inputQuantity - levels);
        }
    }
}

function addLevels(quantity) {
    for (let i=0;i<quantity;i++){
        let firstLevel = $('.levels-of-hierarchy').children().first();
        let firstLevelValue = $(firstLevel).find('.criterionForLevel').val();
        let block = firstLevel.clone();
        deleteOptionsFromResultList(block);

        $(block).find('.criterionForLevel').children().each(function () {
                if(firstLevelValue === $(this).val()){
                    $(this).attr('hidden', '');
                    $(this).attr('changeBy', 1);
                }
            }
        );
        let parent = $('.levels-of-hierarchy');
        parent.append(block);
    }


}

function removeLevels(quantity) {
    for(let i = 0; i < quantity;i++){
        let level = $('.levels-of-hierarchy').children().last();
        let indexOfLevel = $(level).index()+1;

        $('.criterionForLevel').each( function() {
            if($(this).closest('.level-of-hierarchy').index()+1 !== indexOfLevel){
                $(this).find('option').each(function () {
                    if($(this).attr('changeBy') == indexOfLevel){
                        $(this).removeAttr('hidden');
                        $(this).removeAttr('changeBy');
                    }
                });
            }
        })

        deleteAllFieldsFromResultList(level);
        $(level).remove();
    }
}

function parseUserInput() {
    let productHierarchy = {};

    productHierarchy["productGroup"] = {};
    productHierarchy.productGroup["id"] = $('#input-product-group-level-3-name-create').val();
    productHierarchy["id"] = $('#hierarchy-id').val();
    productHierarchy["name"] = $('#input-product-hierarchy-name-create').val();
    productHierarchy["levelQuant"] = $('#input-product-hierarchy-levelQuant-create').val();
    productHierarchy["dateBegin"] = $('#input-product-hierarchy-date-begin-create').val();
    productHierarchy["dateEnd"] = $('#input-product-hierarchy-date-end-create').val();
    if($('#input-product-hierarchy-activity-create').is(":checked")){
        productHierarchy["activity"] = 1;
    }else {
        productHierarchy["activity"] = 0;
    }

    productHierarchy["productHierarchyStructs"] = [];

    let productHierarchyStruct;
    $('.level-of-hierarchy #select-criterion-for-level option:selected, .level-of-hierarchy #result-list-of-field-for-level option').each(function () {

        productHierarchyStruct = {};
        productHierarchyStruct["tableName"] = getTableNameByProductId($('#input-product-group-level-3-name-create').val());
        productHierarchyStruct["level"] = $(this).closest('.level-of-hierarchy').index()+1;
        productHierarchyStruct["fieldName"] = $(this).attr("fieldName");
        if(this.parentElement.getAttribute("name")  === "criterionForLevel"){
            productHierarchyStruct["crit"] = $(this).attr("value");
        }
        else {
            productHierarchyStruct["crit"] = null;
        }
        productHierarchy.productHierarchyStructs.push(productHierarchyStruct);
    })

    return productHierarchy;
}

function deleteSelectionFromElements() {
    $('tbody tr').each(function (){
        $(this).removeClass('selected');
    });
    $('.edit-nsi-modal-button').attr('disabled', true);
}

function moveToEditForm() {
    document.getElementById("add-and-edit-form").scrollIntoView({block: "center", behavior: "smooth"});
}

function validateForm() {
    let response = true;

    if($('#input-product-hierarchy-name-create').val() === '' ){
        $('.error-input-text').html("Ошибка ввода! Не введено имя иерархии");
        response = false;
    }
    if($('#input-product-hierarchy-name-create').val().length > 50 ){
        $('.error-input-text').html("Ошибка ввода! Имя иерархии не должно превышать 50 символов");
        response = false;
    }
    if($('#input-product-group-level-3-name-create').val() == null ){
        $('.error-input-text').html("Ошибка ввода! Не выбрана группа иерархии");
        response = false;
    }
    if($('#input-product-hierarchy-date-begin-create').val() === '' ){
        $('.error-input-text').html("Ошибка ввода! Не введена дата начала действия");
        response = false;
    }
    if($('#input-product-hierarchy-date-end-create').val() === '' ){
        $('.error-input-text').html("Ошибка ввода! Не введена дата конца действия");
        response = false;
    }
    let levelQuant = parseInt($('#input-product-hierarchy-levelQuant-create').val());
    if(levelQuant > 9 || levelQuant < 0){
        $('.error-input-text').html("Ошибка ввода! Неправильно указано кол-во уровней (разрешается от 0 да 10)");
        response = false;
    }

    let dateBegin = new Date($('#input-product-hierarchy-date-begin-create').val());
    let dateEnd = new Date($('#input-product-hierarchy-date-end-create').val());
    if(dateEnd < dateBegin){
        $('.error-input-text').html("Ошибка ввода! Дата начала действия позже даты конца действия");
        response = false;
    }

    $('.criterionForLevel').each(function () {
        if($(this).val() == null){
            $('.error-input-text').html("Ошибка ввода! Не ввыбран критерий для уровня");
            response = false;
        }
    })

    $('.result-list-of-field-for-level').each(function () {
        if($(this).children().length < 1){
            $('.error-input-text').html("Ошибка ввода! Список полей для уровня не должен быть пустым");
            response = false;
        }
    })

    console.log(checkNameUniqueness());

    /*if(checkNameUniqueness()){
        $('.error-input-text').html("Ошибка ввода! Такое имя иерархии уже существует");
        response = false;
    }*/

    return response;

}


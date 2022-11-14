//TODO check JS code from effectiveness point of view
function setExchRates(dateTime, currencyName, sellRate, buyRate){
    setDateTimeElemntValue('data-value', dateTime);

    let divTableBody = document.getElementsByClassName(currencyName+'Row');
    if(divTableBody.length === 0){
        if(addNewRow(currencyName)){
            setRowValues(currencyName, sellRate, buyRate);
        }
    } else {
        setRowValues(currencyName, sellRate, buyRate);
    }
}

function addNewRow(currencyName){
   let divTableRow = document.createElement("div");
    divTableRow.classList.add('divTableRow');

    let divTableCellRow = document.createElement("div");
    divTableCellRow.classList.add('divTableCell');
    divTableCellRow.classList.add(currencyName+'Row');
    divTableRow.appendChild(divTableCellRow);

    let divTableCellSellVal = document.createElement("div");
    //divTableCellSellVal.textContent = val
    divTableCellSellVal.classList.add('divTableCell');
    divTableCellSellVal.classList.add(currencyName+'-sell-val');
    divTableRow.appendChild(divTableCellSellVal);

    let divTableCellBuyVal = document.createElement("div");
    divTableCellBuyVal.classList.add('divTableCell');
    divTableCellBuyVal.classList.add(currencyName+'-buy-val');
    divTableRow.appendChild(divTableCellBuyVal);

    let divTableBody = document.getElementsByClassName('divTableBody');
    if(divTableBody.length > 0){
        Array.from(divTableBody).forEach((foundField, i) => {
             foundField.appendChild(divTableRow);
         });
         return true;
    } else {
        return false;
    }
}

function setRowValues(currencyName, sellRate, buyRate){
    let foundCurrItems = document.getElementsByClassName(currencyName + 'Row');
    if(foundCurrItems.length > 0){
    Array.from(foundCurrItems).forEach((foundCurrField, i) => {
        if(foundCurrField)
            foundCurrField.textContent = currencyName.toUpperCase();
     });
    }

    let foundSellItems = document.getElementsByClassName(currencyName + '-sell-val');
    if(foundSellItems.length > 0){
    Array.from(foundSellItems).forEach((foundSellField, i) => {
        if(foundSellField)
            foundSellField.textContent = sellRate;
     });
    }

    let foundBuyItems = document.getElementsByClassName(currencyName + '-buy-val');
    if(foundBuyItems.length > 0){
    Array.from(foundBuyItems).forEach((foundBuyField, i) => {
        if(foundBuyField)
            foundBuyField.textContent = buyRate;
     });
    }
}

function setDateTimeElemntValue(elClass, val){

  let foundItems = document.getElementsByClassName(elClass);
  if(foundItems.length > 0){
    Array.from(foundItems).forEach((foundField, i) => {
        if(foundField)
            foundField.textContent = val;
     });
  }
}
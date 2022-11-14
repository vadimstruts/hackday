//TODO check JS code from effectiveness point of view
function setExchRates(currencyName, sellRate, updDate){
    setTimeout(() => {
        showProgress(false);
        let divTableBody = document.getElementsByClassName(currencyName+'Row');
        if(divTableBody.length === 0){
            if(addNewRow(currencyName)){
                setRowValues(currencyName, sellRate, updDate);
            }
        } else {
            setRowValues(currencyName, sellRate, updDate);
        }
    }, 1000);
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
    divTableCellBuyVal.classList.add(currencyName+'-upd-val');
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

function setRowValues(currencyName, sellRate, updDate){
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

    let foundBuyItems = document.getElementsByClassName(currencyName + '-upd-val');
    if(foundBuyItems.length > 0){
    Array.from(foundBuyItems).forEach((foundBuyField, i) => {
        if(foundBuyField)
            foundBuyField.textContent = updDate;
     });
    }
}

function showProgress(visible){
  let foundItems = document.getElementsByClassName('progress');
  if(foundItems.length > 0){
    Array.from(foundItems).forEach((foundField, i) => {
        if(foundField)
            foundField.style.display = visible ? 'block' : 'none';
     });
  }
}
const plot = document.getElementById("coordinateSystem");
const rCheckboxes = document.querySelectorAll('.r-checkbox');
const submitButton = document.getElementById('submitButton');
const inputX = document.getElementById('x');
const inputY = document.getElementById('y');
const inputR = document.getElementById('r');
const xButtons = document.querySelectorAll('.x-button');
let selectedX = null;

let selectedR = [];
const clearButton = document.getElementById('clearButton');

function updateData() {
    return {
        x: selectedX,
        y: inputY.value,
        r: selectedR
    }
}

document.addEventListener('DOMContentLoaded', function () {
    draw();
    drawLabels();
    const oldR = localStorage.getItem('selectedR')||'R';
    if  (oldR !== 'R') {
        console.log(resultsFromBean);
        updateLabels(parseFloat(oldR));
        resultsFromBean.forEach(result =>{
            if (result.r === parseFloat(oldR)) {
                drawPoint(result.x * 20 * 8 / result.r, -result.y * 20 * 8 / result.r, result.hit ? 'green' : 'red');
                rCheckboxes.forEach(elem => {elem.checked = parseFloat(elem.getAttribute('value')) === result.r;})
                selectedR.push(oldR);
                console.log('восстановленная точка: ',result.x * 20 * 8 / result.r, -result.y * 20 * 8 / result.r,  result.hit ? 'green' : 'red');
            }
        });
    }
});

xButtons.forEach(button => {
    button.addEventListener('click', function () {
        xButtons.forEach(btn => {
            btn.classList.remove('active');
        });
        this.classList.add('active');

        selectedX = this.value;
    });
});

rCheckboxes.forEach(checkbox => {
    checkbox.addEventListener('change', function () {
        if (this.checked) {
            selectedR.push(this.value);
        } else {
            selectedR = selectedR.filter(r => r !== this.value);
        }

        if (selectedR.length === 1) {
            const value = parseFloat(selectedR[0]);
            updateLabels(value);
            localStorage.setItem('selectedR', JSON.stringify(value));
        } else {
            draw();
            drawLabels();
            localStorage.setItem('selectedR', 'R');
        }
        console.log('Selected R:', selectedR);
    });
});

submitButton.addEventListener('click', (event) => {
    event.preventDefault();
    const data = updateData();
    let isValid = printErrors(data);
    if (isValid) {
        sendToServer(data.x, data.y, data.r);
        location.href = "table.jsp";
    }
});

plot.addEventListener('click', (e) => {
    if (selectedR.length !== 1) {
        clearErrors();
        printError(plot, selectedR.length === 0 ? 'Выберите значение R' : 'Выберите только один R для графика');
    } else {
        clearErrors();
        const rect = plot.getBoundingClientRect();
        const x = round(((e.clientX - rect.left - centerX) / 20 / 8) * selectedR[0], 3);
        const y = -round(((e.clientY - rect.top - centerY) / 20 / 8) * selectedR[0], 3);
        console.log(x, y)
        sendToServer(x, y, selectedR);
    }
})

function round(number, precision) {
    const coefficient = Math.pow(10, precision);
    return Math.round(number * coefficient) / coefficient;
}

function printError(element, message) {
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-text';
    errorDiv.textContent = message;
    errorDiv.style.color = 'red';
    errorDiv.style.fontStyle = '14px';
    errorDiv.style.textAlign = 'center';
    element.parentNode.insertBefore(errorDiv, element.nextSibling);
}

function printErrors(data) {
    clearErrors();
    let isValid = true;
    console.log('data: ', data);
    if (!validate(data.x)) {
        printError(inputX, 'Выберите значение Х');
        isValid = false;
    }
    if (!validate(data.y)) {
        printError(inputY, 'Введите значение Y');
        isValid = false;
    } else if (!validateNumber(data.y)) {
        printError(inputY, 'Введите целое или дробное число');
        isValid = false;
    } else if (!validateRange(data.y, -5, 3)) {
        printError(inputY, 'Число должно быть в пределах от -5 до 3');
        isValid = false;
    }
    if (!validate(data.r)) {
        printError(inputR, 'Выберите значение R');
        isValid = false;
    }
    return isValid;
}

function clearErrors() {
    document.querySelectorAll('.error-text').forEach(element => element.remove());
}




const plot = document.getElementById('coordinateSystem');
const submit = document.getElementById('pointForm:submit-button');

document.addEventListener('DOMContentLoaded', function () {
    draw();
    drawLabels();
});

function handleR() {
    let radius = parseFloat(document.querySelector('[name$=":r-input"]').value);
    updateLabels(radius);
}

plot.addEventListener('click', (e) => {
    const position = getClientClick(e);
    let x = position.x;
    let y = position.y;

    const formX = document.querySelector('[name$=":hiddenX"]');
    const formY = document.getElementById('pointForm:y');

    formX.value = x;
    formY.value = y;
    console.log('x: ', formX.value, 'y: ', formY.value);

    submit.click();
})

function getClientClick(e){
    const rect = plot.getBoundingClientRect();
    const r = parseFloat(document.querySelector('[name$=":r-input"]').value);
    const x = round(((e.clientX - rect.left - centerX) / 20 / 8) * r, 3);
    const y = -round(((e.clientY - rect.top - centerY) / 20 / 8) * r, 3);
    return { x: x, y: y , r: r };
}

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

clearButton.addEventListener('click', () => {
    document.querySelectorAll('.table-row').forEach(element => element.remove());
    clearSession().then(() => {
        draw();
        drawLabels();
        resultsFromBean.clear();
        localStorage.setItem('selectedR', 'R');
    });
});

async function clearSession() {
    try {
        await fetch('/lab2/clear', {
            method: 'POST'
        });
        console.log('Сессия очищена');
    } catch (error) {
        console.error('Ошибка очистки сессии:', error);
    }
}


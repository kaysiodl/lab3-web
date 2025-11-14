const plot = document.getElementById('coordinateSystem');
const submit = document.getElementById('pointForm:submit-button');

document.addEventListener('DOMContentLoaded', function () {
    draw();
    drawLabels();
    handleR();
});

function handleR() {
    let radius = parseFloat(document.querySelector('[name$=":r-input"]').value);
    if (!isNaN(radius)) updateLabels(radius);
}

plot.addEventListener('click', (e) => {
    const position = getClientClick(e);
    let x = position.x;
    let y = position.y;
    drawPoint(x * 20 * 8 / position.r, -y * 20 * 8 / position.r, 'black');


    const formX = document.querySelector('[name$=":hidden-x"]');
    const formY = document.getElementById('pointForm:y');

    const xPrev = formX.value;
    const yPrev = formY.value;

    formX.value = x;
    formY.value = y;
    console.log('x: ', formX.value, 'y: ', formY.value);

    submit.click();

    formX.value = xPrev;
    formY.value = yPrev;
})

function getClientClick(e) {
    const rect = plot.getBoundingClientRect();
    const r = parseFloat(document.querySelector('[name$=":r-input"]').value);
    const x = round(((e.clientX - rect.left - centerX) / 20 / 8) * r, 3);
    const y = -round(((e.clientY - rect.top - centerY) / 20 / 8) * r, 3);
    return {x: x, y: y, r: r};
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

function clearErrors() {
    document.querySelectorAll('.error-text').forEach(element => element.remove());
}



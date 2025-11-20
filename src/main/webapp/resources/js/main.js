const plot = document.getElementById('coordinate-system');

document.addEventListener('DOMContentLoaded', function () {
    start();
});

function start() {
    draw();
    drawLabels();
    handleR();
}

function handleR() {
    let radius = parseFloat(document.querySelector('[name$=":r-input"]').value);
    if (!isNaN(radius)) updateLabels(radius);
    drawResults();
}

plot.addEventListener('click', (e) => {
    if (parseFloat(document.querySelector('[name$=":r-input"]')) !== null) {
        const position = getClientClick(e);
        let x = position.x;
        let y = position.y;
        let r = position.r;

        const formX = document.querySelector('[name$=":hidden-x"]');
        const formY = document.querySelector('[name$=":hidden-y"]');
        const formR = document.querySelector('[name$=":hidden-r"]');

        formX.value = x;
        formY.value = y;
        formR.value = r;
        console.log('x: ', formX.value, 'y: ', formY.value);

        document.getElementById('hidden-form:hidden-submit').click();
    }
});


function drawResults() {
    const results = JSON.parse(document.querySelector('[name$=":hidden-results"]').value);
    const radius = parseFloat(document.querySelector('[name$=":r-input"]').value);
    console.log('results: ', results);
    results.filter(point => point.r === radius)
           .forEach(point => {drawPoint(point.x * 20 * 8 / point.r, -point.y * 20 * 8 / point.r, point.hit ? 'green' : 'red')});
}

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


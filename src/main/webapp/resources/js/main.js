const plot = document.getElementById('coordinateSystem');

document.addEventListener('DOMContentLoaded', function () {
    draw();
    drawLabels();
    handleR();
    drawResultsFromTable();
});

function handleR() {
    let radius = parseFloat(document.querySelector('[name$=":r-input"]').value);
    if (!isNaN(radius)) updateLabels(radius);
}

plot.addEventListener('click', (e) => {
    const position = getClientClick(e);
    let x = position.x;
    let y = position.y;

    const formX = document.querySelector('[name$=":hidden-x"]');
    const formY = document.querySelector('[name$=":y"]');

    formX.value = x;
    formY.value = y;
    console.log('x: ', formX.value, 'y: ', formY.value);

    document.getElementById('point-form:submit-button').click();
    setTimeout(drawResultsFromTable, 300);

    formY.value = '';
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

function drawResultsFromTable() {
    const canvas = document.getElementById('coordinateSystem');
    const ctx = canvas.getContext('2d');
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    draw();
    drawLabels();
    handleR();
    const table = document.getElementById('table-form:resultsTable');
    if (!table) {
        console.log('Таблица не найдена');
        return;
    }

    const tbody = document.getElementById('table-form:resultsTable_data');
    if (!tbody) {
        console.log('Тело таблицы не найдено');
        return;
    }

    const rows = tbody.querySelectorAll('tr');
    const currentR = parseFloat(document.querySelector('[id$=":r-input"]').value);

    if (isNaN(currentR)) {
        console.log('радиус не выбран');
        return;
    }

    console.log('строки:', rows.length);
    console.log('текущий радиус:', currentR);

    rows.forEach((row, index) => {
        const cells = row.querySelectorAll('td');
        const x = parseFloat(cells[0].textContent.trim());
        const y = parseFloat(cells[1].textContent.trim());
        const r = parseFloat(cells[2].textContent.trim());
        const hit = cells[3].textContent.trim().toLowerCase();

        console.log(`Строка ${index}: x=${x}, y=${y}, r=${r}, hit=${hit}`);

        if (Math.abs(r - currentR) < 0.01 && !isNaN(x) && !isNaN(y)) {
            const color = hit === 'true' ? 'green' : 'red';

            const canvasX = (x / r) * 20 * 8;
            const canvasY = (-y / r) * 20 * 8;

            console.log(`canvasX=${canvasX}, canvasY=${canvasY}, color=${color}`);
            drawPoint(canvasX, canvasY, color);
        }

    });
}


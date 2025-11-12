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
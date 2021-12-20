let startDateInput = document.getElementById('startDate');
let endDateInput = document.getElementById('finishedDate');
let messageDiv = document.getElementById('message');
let submitButton = document.getElementById('submit');

let compare = () => {
    let startValue = (new Date(startDateInput.value)).getTime();
    let endValue = (new Date(endDateInput.value)).getTime();

    if (endValue < startValue) {
        messageDiv.innerHTML = 'Start date must be before finished date!';
        messageDiv.style.color = 'red';
        submitButton.disabled = true;
    } else if (isNaN(endValue) || isNaN(startValue)) {
        messageDiv.innerHTML = 'Must select a start and finished date to submit!';
        messageDiv.style.color = 'black';
        submitButton.disabled = true;
    } else {
        messageDiv.innerHTML = '';
        submitButton.disabled = false;
    }
};

startDateInput.addEventListener('change', compare);
endDateInput.addEventListener('change', compare);
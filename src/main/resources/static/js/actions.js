function showMSForm() {
    const showMSForm = document.getElementById('showMSForm');
    if (document.getElementById('isMS').checked) {
        showMSForm.style.display = 'none';
    } else {
        showMSForm.style.display = 'block';
    }
}

function showGPForm() {
    const showGPForm = document.getElementById('showGPForm');
    if (!document.getElementById('isGP').checked) {
        showGPForm.style.display = 'none';
    } else {
        showGPForm.style.display = 'block';
    }
}


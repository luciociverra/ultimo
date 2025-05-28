<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Death Information Form -->
<form id="deathDataForm">
    <input type="hidden" id="deathId" name="deathId">
    <input type="hidden" id="deathPersonId" name="personId">
    
    <div class="alert alert-success d-none" id="deathDataSuccess" role="alert">
        Dati salvati con successo!
    </div>
    
    <div class="row mb-3">
        <div class="col-12">
            <label class="form-label">Data di Decesso</label>
            <select class="form-select mb-2" id="deathDateType">
                <option value="full">Data completa</option>
                <option value="year">Solo anno</option>
            </select>
        </div>
    </div>
    
    <div class="row mb-3" id="fullDeathDateContainer">
        <div class="col-md-6">
            <input type="date" class="form-control" id="editDeathDate" name="deathDate">
        </div>
        <div class="col-md-6">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="editDeathDateApproximate" name="isDeathDateApproximate">
                <label class="form-check-label" for="editDeathDateApproximate">
                    Data probabile
                </label>
            </div>
        </div>
    </div>
    
    <div class="row mb-3 d-none" id="deathYearContainer">
        <div class="col-md-6">
            <input type="number" class="form-control" id="editDeathYear" name="deathYear" placeholder="Anno (AAAA)">
        </div>
        <div class="col-md-6">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="editDeathYearApproximate" name="isDeathYearApproximate">
                <label class="form-check-label" for="editDeathYearApproximate">
                    Anno probabile
                </label>
            </div>
        </div>
    </div>
    
    <div class="row mb-3">
        <div class="col-md-6">
            <label for="editDeathCity" class="form-label">Città</label>
            <input type="text" class="form-control" id="editDeathCity" name="deathCity">
        </div>
    </div>
    
    <div class="row mb-3">
        <div class="col-12">
            <label for="editDeathNote" class="form-label">Note (luogo di sepoltura)</label>
            <textarea class="form-control" id="editDeathNote" name="deathNote" rows="3"></textarea>
        </div>
    </div>
    
    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
        <button type="button" class="btn btn-primary" id="saveDeathDataButton">Salva</button>
    </div>
</form>

<script>
    // Toggle between full date and year-only input
    document.getElementById('deathDateType').addEventListener('change', function() {
        const fullDateContainer = document.getElementById('fullDeathDateContainer');
        const yearContainer = document.getElementById('deathYearContainer');
        
        if (this.value === 'full') {
            fullDateContainer.classList.remove('d-none');
            yearContainer.classList.add('d-none');
        } else {
            fullDateContainer.classList.add('d-none');
            yearContainer.classList.remove('d-none');
        }
    });
    
    // Function to load death details
    function loadDeathDetails(personId) {
        document.getElementById('deathPersonId').value = personId;
        
        axios.get(`DeathServlet?action=get&personId=${personId}`)
            .then(function(response) {
                if (response.data && response.data.deathId) {
                    const death = response.data;
                    
                    document.getElementById('deathId').value = death.deathId;
                    
                    // Handle death date format
                    if (death.deathDate) {
                        document.getElementById('editDeathDate').value = death.deathDate;
                        document.getElementById('deathDateType').value = 'full';
                        document.getElementById('fullDeathDateContainer').classList.remove('d-none');
                        document.getElementById('deathYearContainer').classList.add('d-none');
                    } else if (death.deathYear) {
                        document.getElementById('editDeathYear').value = death.deathYear;
                        document.getElementById('deathDateType').value = 'year';
                        document.getElementById('fullDeathDateContainer').classList.add('d-none');
                        document.getElementById('deathYearContainer').classList.remove('d-none');
                    } else {
                        document.getElementById('deathDateType').value = 'full';
                        document.getElementById('fullDeathDateContainer').classList.remove('d-none');
                        document.getElementById('deathYearContainer').classList.add('d-none');
                    }
                    
                    document.getElementById('editDeathDateApproximate').checked = death.isDeathDateApproximate;
                    document.getElementById('editDeathCity').value = death.deathCity || '';
                    document.getElementById('editDeathNote').value = death.deathNote || '';
                } else {
                    // No death record exists yet
                    resetDeathForm();
                }
            })
            .catch(function(error) {
                console.error('Error loading death details:', error);
                resetDeathForm();
            });
    }
    
    // Function to reset death form
    function resetDeathForm() {
        document.getElementById('deathId').value = '';
        document.getElementById('editDeathDate').value = '';
        document.getElementById('editDeathYear').value = '';
        document.getElementById('editDeathDateApproximate').checked = false;
        document.getElementById('editDeathYearApproximate').checked = false;
        document.getElementById('editDeathCity').value = '';
        document.getElementById('editDeathNote').value = '';
        document.getElementById('deathDateType').value = 'full';
        document.getElementById('fullDeathDateContainer').classList.remove('d-none');
        document.getElementById('deathYearContainer').classList.add('d-none');
    }
    
    // Save death data
    document.getElementById('saveDeathDataButton').addEventListener('click', function() {
        const personId = document.getElementById('deathPersonId').value;
        if (!personId) {
            alert('Seleziona prima una persona dalla lista.');
            return;
        }
        
        // Get form data
        const formData = {
            deathId: document.getElementById('deathId').value,
            personId: personId,
            isDeathDateApproximate: document.getElementById('editDeathDateApproximate').checked
        };
        
        // Handle death date type
        const deathDateType = document.getElementById('deathDateType').value;
        if (deathDateType === 'full') {
            formData.deathDate = document.getElementById('editDeathDate').value;
            formData.deathYear = null;
        } else {
            formData.deathDate = null;
            formData.deathYear = document.getElementById('editDeathYear').value;
        }
        
        formData.deathCity = document.getElementById('editDeathCity').value;
        formData.deathNote = document.getElementById('editDeathNote').value;
        
        // Send data to server
        axios.post('DeathServlet', formData)
            .then(function(response) {
                if (response.data.success) {
                    // Update death ID if new record was created
                    if (response.data.deathId) {
                        document.getElementById('deathId').value = response.data.deathId;
                    }
                    
                    // Show success message
                    const successAlert = document.getElementById('deathDataSuccess');
                    successAlert.classList.remove('d-none');
                    
                    // Hide after 3 seconds
                    setTimeout(function() {
                        successAlert.classList.add('d-none');
                    }, 3000);
                } else {
                    alert('Errore: ' + response.data.message);
                }
            })
            .catch(function(error) {
                console.error('Error saving death data:', error);
                alert('Si è verificato un errore durante il salvataggio dei dati.');
            });
    });
</script>
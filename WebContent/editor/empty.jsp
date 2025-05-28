<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Selettore di Genere</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome per le icone -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .gender-btn {
            width: 100%;
            padding: 20px;
            transition: all 0.3s ease;
            border: 2px solid transparent;
        }
        
        .gender-btn.selected {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        
        .gender-btn.male.selected {
            border-color: #0d6efd;
            background-color: rgba(13, 110, 253, 0.1);
        }
        
        .gender-btn.female.selected {
            border-color: #d63384;
            background-color: rgba(214, 51, 132, 0.1);
        }
        
        .gender-icon {
            font-size: 2rem;
            margin-bottom: 10px;
        }
        
        /* Opzionale: effetto hover */
        .gender-btn:hover:not(.selected) {
            background-color: #f8f9fa;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Seleziona il genere</h2>
        
        <div class="row">
            <!-- Versione con bottoni grandi a blocco -->
            <div class="col-12 mb-5">
                <div class="row g-3">
                    <div class="col-md-6">
                        <button id="maleBtn" type="button" class="gender-btn male rounded shadow-sm text-primary" onclick="selectGender('male')">
                            <i class="fas fa-mars gender-icon"></i>
                          Maschio
                        </button>
                        <input type="hidden" id="genderMale" name="gender" value="male">
                    </div>
                    <div class="col-md-6">
                        <button id="femaleBtn" type="button" class="gender-btn female rounded shadow-sm text-danger" onclick="selectGender('female')">
                            <i class="fas fa-venus gender-icon"></i>
                           Femmina
                        </button>
                        <input type="hidden" id="genderFemale" name="gender" value="female">
                    </div>
                </div>
            </div>
            
            <!-- Versione con gruppo di bottoni Bootstrap -->
            <div class="col-12 mb-5">
               
                <div class="btn-group w-100" role="group" aria-label="Gender selection">
                    <input type="radio" class="btn-check" checked name="genderRadio" id="genderRadioMale" value="male" autocomplete="off">
                    <label class="btn btn-outline-primary" for="genderRadioMale">
                        <i class="fas fa-mars me-2"></i>Maschio
                    </label>
                    
                    <input type="radio" class="btn-check" name="genderRadio" id="genderRadioFemale" value="female" autocomplete="off">
                    <label class="btn btn-outline-danger" for="genderRadioFemale">
                        <i class="fas fa-venus me-2"></i>Femmina
                    </label>
                </div>
            </div>
            
            <!-- Versione con card selezionabili -->
           
        </div>
    </div>

     <script>
    
    
    $(document).ready(function(){
     	 $('input[name="genderRadio"]').on('change', function () {
    	      const selected = $(this).val();
    	      alert(selected);
    	    });
    });
    
    
        // Funzione per selezionare il genere (versione con bottoni grandi)
        function selectGender(gender) {
            // Rimuovi la classe 'selected' da tutti i bottoni
            document.querySelectorAll('.gender-btn').forEach(btn => {
                btn.classList.remove('selected');
            });
            
            // Aggiungi la classe 'selected' al bottone cliccato
            if (gender === 'male') {
                document.getElementById('maleBtn').classList.add('selected');
                document.getElementById('genderMale').checked = true;
            } else {
                document.getElementById('femaleBtn').classList.add('selected');
                document.getElementById('genderFemale').checked = true;
            }
            
            console.log('Genere selezionato:', gender);
        }
        
      
    </script>
</body>
</html>
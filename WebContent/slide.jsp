<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Slideshow Immagini</title>
  
  <!-- Bootstrap 5 CSS -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
  
  <!-- Font Awesome -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
  
  <style>
    body {
      background-color: #f8f9fa;
    }
    
    .carousel-container {
      max-width: 800px;
      margin: 0 auto;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
      overflow: hidden;
      background-color: #fff;
    }
    
    .carousel-inner {
      max-height: 500px;
    }
    
    .carousel-item img {
      object-fit: contain;
      width: 100%;
      height: 500px;
      background-color: #000;
      cursor: zoom-in;
      transition: transform 0.3s ease;
    }
    
    .carousel-caption {
      background-color: rgba(0, 0, 0, 0.6);
      border-radius: 8px;
      padding: 15px;
      bottom: 20px;
    }
    
    .image-info {
      background-color: #fff;
      padding: 20px;
      border-bottom-left-radius: 8px;
      border-bottom-right-radius: 8px;
    }
    
    .info-table {
      margin-bottom: 0;
    }
    
    .carousel-control-prev,
    .carousel-control-next {
      width: 5%;
      opacity: 0.8;
    }
    
    .carousel-indicators {
      bottom: -5px;
    }
    
    .image-count {
      position: absolute;
      top: 15px;
      right: 15px;
      background-color: rgba(0, 0, 0, 0.6);
      color: white;
      padding: 5px 10px;
      border-radius: 4px;
      font-size: 14px;
      z-index: 10;
    }
    
    .loading {
      height: 500px;
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: #f8f9fa;
    }
    
    .spinner-border {
      width: 3rem;
      height: 3rem;
    }
    
    .no-images {
      height: 500px;
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: #f8f9fa;
      flex-direction: column;
    }
    
    .metadata-badge {
      margin-right: 8px;
      font-size: 0.9em;
    }
    
    .gallery-title {
      text-align: center;
      margin: 30px 0;
      color: #333;
      font-weight: 300;
    }
    
    /* Modal per zoom */
    .zoom-modal {
      display: none;
      position: fixed;
      z-index: 1050;
      padding-top: 50px;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      overflow: auto;
      background-color: rgba(0, 0, 0, 0.9);
      transition: 0.3s;
    }
    
    .zoom-modal-content {
      display: block;
      margin: auto;
      max-width: 90%;
      max-height: 90vh;
      object-fit: contain;
      cursor: zoom-out;
    }
    
    .zoom-close {
      position: absolute;
      top: 15px;
      right: 35px;
      color: #f1f1f1;
      font-size: 40px;
      font-weight: bold;
      transition: 0.3s;
      z-index: 1060;
    }
    
    .zoom-close:hover,
    .zoom-close:focus {
      color: #bbb;
      text-decoration: none;
      cursor: pointer;
    }
    
    .zoom-btn {
      position: absolute;
      top: 15px;
      left: 15px;
      background-color: rgba(0, 0, 0, 0.6);
      color: white;
      border: none;
      border-radius: 4px;
      padding: 5px 10px;
      font-size: 14px;
      z-index: 10;
      transition: background-color 0.3s;
    }
    
    .zoom-btn:hover {
      background-color: rgba(0, 0, 0, 0.8);
    }
    
    /* Animazione zoom */
    @keyframes zoom {
      from {transform: scale(0)}
      to {transform: scale(1)}
    }
    
    .zoom-modal-content {
      animation-name: zoom;
      animation-duration: 0.3s;
    }
  </style>
</head>
<body>
  <!-- Modal per lo zoom delle immagini -->
  <div id="zoomModal" class="zoom-modal">
    <span class="zoom-close">&times;</span>
    <img class="zoom-modal-content" id="zoomImg">
  </div>
  
  <div class="container mt-5 mb-5">
   
    
    <div class="carousel-container">
      <!-- Caricamento iniziale -->
      <div id="loading" class="loading">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">Caricamento...</span>
        </div>
      </div>
      
      <!-- Messaggio nessuna immagine -->
      <div id="noImages" class="no-images d-none">
        <i class="fas fa-image fa-4x mb-3 text-secondary"></i>
        <h4 class="text-secondary">Nessuna immagine trovata</h4>
        <p class="text-muted">Non ci sono immagini disponibili nel database.</p>
      </div>
      
      <!-- Carousel -->
      <div id="imageCarousel" class="carousel slide d-none" data-bs-ride="carousel">
        <div class="carousel-indicators" id="carouselIndicators">
          <!-- Indicatori generati dinamicamente -->
        </div>
        
        <div class="image-count">
          <span id="currentImageNum">1</span>/<span id="totalImages">0</span>
        </div>
        
        <div class="carousel-inner" id="carouselInner">
          <!-- Le slide verranno generate dinamicamente -->
        </div>
        
        <button class="carousel-control-prev" type="button" data-bs-target="#imageCarousel" data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Precedente</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#imageCarousel" data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Successivo</span>
        </button>
      </div>
      
      <!-- Informazioni immagine corrente -->
      <div id="imageInfo" class="image-info d-none">
        <h4 id="imageTitolo" class="mb-3">Titolo immagine</h4>
        <p id="imageDescrizione" class="text-muted mb-3">Descrizione dell'immagine...</p>
        
        <div class="d-flex flex-wrap mb-3">
          <span id="imageData" class="badge bg-primary metadata-badge me-2 mb-2">
            <i class="far fa-calendar-alt me-1"></i> Data
          </span>
          <span id="imageLuogo" class="badge bg-success metadata-badge mb-2">
            <i class="fas fa-map-marker-alt me-1"></i> Luogo
          </span>
        </div>
        
        <div class="d-flex justify-content-between">
          <small id="imageUploadDate" class="text-muted">Caricato il: </small>
          <button class="btn btn-sm btn-outline-secondary" id="btnDownloadCurrent">
            <i class="fas fa-download me-1"></i> Scarica
          </button>
        </div>
      </div>
    </div>
  </div>
  <!-- Scripts -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/1.4.0/axios.min.js"></script>
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      // Elementi DOM
      const loading = document.getElementById('loading');
      const noImages = document.getElementById('noImages');
      const carousel = document.getElementById('imageCarousel');
      const infoPanel = document.getElementById('imageInfo');
      const carouselInner = document.getElementById('carouselInner');
      const carouselIndicators = document.getElementById('carouselIndicators');
      const currentImageNum = document.getElementById('currentImageNum');
      const totalImages = document.getElementById('totalImages');
      const imageTitolo = document.getElementById('imageTitolo');
      const imageDescrizione = document.getElementById('imageDescrizione');
      const imageData = document.getElementById('imageData');
      const imageLuogo = document.getElementById('imageLuogo');
      const imageUploadDate = document.getElementById('imageUploadDate');
      const btnDownloadCurrent = document.getElementById('btnDownloadCurrent');
      
      // Array per memorizzare le immagini
      let images = [];
      // Funzione per caricare le immagini dal server
      function loadImages() {
        // Mostra il loader
        loading.classList.remove('d-none');
        carousel.classList.add('d-none');
        infoPanel.classList.add('d-none');
        noImages.classList.add('d-none');
        
        // Richiesta con Axios per ottenere le immagini dalla servlet
 
        axios.get('slideservlet')
          .then(response => {
            images = response.data;
            totalImages.textContent = images.length;
            
            if (images.length === 0) {
              // Nessuna immagine trovata
              loading.classList.add('d-none');
              noImages.classList.remove('d-none');
              return;
            }
            
            // Genera gli indicatori e le slide del carousel
            generateCarouselContent();
            
            // Nascondi il loader e mostra il carousel
            loading.classList.add('d-none');
            carousel.classList.remove('d-none');
            infoPanel.classList.remove('d-none');
            
            // Inizializza il carousel
            const carouselInstance = new bootstrap.Carousel(carousel, {
              interval: 5000, // 5 secondi per slide
              wrap: true
            });
            
            // Aggiorna le informazioni quando cambia la slide
            carousel.addEventListener('slide.bs.carousel', event => {
              const index = event.to;
              updateImageInfo(index);
              currentImageNum.textContent = index + 1;
            });
            
            // Imposta le informazioni iniziali
            updateImageInfo(0);
          })
          .catch(error => {
            console.error('Errore:', error);
            loading.classList.add('d-none');
            noImages.classList.remove('d-none');
            
            // Modifica il messaggio per mostrare l'errore
            const noImagesTitle = noImages.querySelector('h4');
            const noImagesText = noImages.querySelector('p');
            noImagesTitle.textContent = 'Errore di caricamento';
            noImagesText.textContent = 'Si è verificato un errore durante il caricamento delle immagini: ' + 
                                      (error.response ? error.response.data.error : error.message);
          });
      }
      
      // Funzione per generare il contenuto del carousel
      function generateCarouselContent() {
        // Svuota i contenitori
        carouselInner.innerHTML = '';
        carouselIndicators.innerHTML = '';
        
        // Genera gli indicatori e le slide
        images.forEach((image, index) => {
          // Indicatore
          const indicator = document.createElement('button');
          indicator.setAttribute('type', 'button');
          indicator.setAttribute('data-bs-target', '#imageCarousel');
          indicator.setAttribute('data-bs-slide-to', index);
          if (index === 0) {
            indicator.classList.add('active');
          }
          indicator.setAttribute('aria-label', `Slide ${index + 1}`);
          carouselIndicators.appendChild(indicator);
          // Slide
          const slide = document.createElement('div');
          slide.classList.add('carousel-item');
          if (index === 0) {
            slide.classList.add('active');
          }
          // Immagine (caricata tramite GET alla servlet)
          slide.innerHTML = '<button class="zoom-btn" data-image-id="'+image.id+'"><i class="fas fa-search-plus"></i> Zoom</button>'+
            '<img src="getimmagine?id='+image.id+'" class="d-block" data-image-id="'+image.id+'">'+
            '<div class="carousel-caption d-none d-md-block"><h5>'+image.titolo+'</h5></div>';
          console.log(image.id);
          carouselInner.appendChild(slide);
        });
        
        // Aggiungi il comportamento di zoom
        setupZoomFunctionality();
      }
      
      // Funzione per aggiornare le informazioni dell'immagine
      function updateImageInfo(index) {
        const image = images[index];
        
        // Titolo e descrizione
     //   imageTitolo.textContent = image.titolo || 'Senza titolo';
     //   imageDescrizione.textContent = image.descrizione || 'Nessuna descrizione disponibile';
        
        // Data
        if (image.data) {
          imageData.innerHTML = '<i class="far fa-calendar-alt me-1"></i>'+formatDate(image.data);
          imageData.classList.remove('d-none');
        } else if (image.anno) {
          imageData.innerHTML = '<i class="far fa-calendar-alt me-1"></i>'+image.anno;
          imageData.classList.remove('d-none');
        } else {
          imageData.classList.add('d-none');
        }
        
        // Luogo
        if (image.luogo) {
          imageLuogo.innerHTML = `<i class="fas fa-map-marker-alt me-1"></i> ${image.luogo}`;
          imageLuogo.classList.remove('d-none');
        } else {
          imageLuogo.classList.add('d-none');
        }
        
        // Data di caricamento
        if (image.dataCaricamento) {
          imageUploadDate.textContent = 'Caricato il: '+formatDate(image.dataCaricamento);
        } else {
          imageUploadDate.textContent = '';
        }
        
        // Configura il pulsante di download
        btnDownloadCurrent.onclick = function() {
          // Reindirizzamento diretto alla servlet per scaricare l'immagine
          window.location.href = '/servlet/DownloadImmagineServlet?id='+image.id;
        };
      }
      
      // Funzione per formattare la data
      function formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('it-IT', {
          day: '2-digit',
          month: '2-digit',
          year: 'numeric'
        });
      }
      
      // Carica le immagini all'avvio
      loadImages();
      
      // Funzione per gestire lo  delle immagini
      function setupZoomFunctionality() {
        const zoomModal = document.getElementById('zoomModal');
        const zoomImg = document.getElementById('zoomImg');
        const zoomClose = document.querySelector('.zoom-close');
        
        // Aggiungi listener ai pulsanti di zoom e alle immagini
        document.querySelectorAll('.carousel-item img, .zoom-btn').forEach(element => {
          element.addEventListener('click', function() {
            // Ottieni l'ID dell'immagine
            let imageId;
            if (this.tagName === 'BUTTON') {
              imageId = this.getAttribute('data-image-id');
            } else {
              imageId = this.getAttribute('data-image-id');
            }
            
            // Imposta l'URL dell'immagine zoom
            zoomImg.src = 'getimmagine?id='+imageId;
            
            // Mostra il modal
            zoomModal.style.display = 'block';
            
            // Disabilita lo scorrimento della pagina
            document.body.style.overflow = 'hidden';
          });
        });
        
        // Chiudi il modal quando si clicca sulla X
        zoomClose.addEventListener('click', function() {
          zoomModal.style.display = 'none';
          document.body.style.overflow = 'auto';
        });
        
        // Chiudi il modal quando si clicca sull'immagine
        zoomImg.addEventListener('click', function() {
          zoomModal.style.display = 'none';
          document.body.style.overflow = 'auto';
        });
        
        // Chiudi il modal quando si clicca fuori dall'immagine
        zoomModal.addEventListener('click', function(event) {
          if (event.target === zoomModal) {
            zoomModal.style.display = 'none';
            document.body.style.overflow = 'auto';
          }
        });
        
        // Chiudi il modal con il tasto ESC
        document.addEventListener('keydown', function(event) {
          if (event.key === 'Escape' && zoomModal.style.display === 'block') {
            zoomModal.style.display = 'none';
            document.body.style.overflow = 'auto';
          }
        });
      }
    });
  </script>
</body>
</html>
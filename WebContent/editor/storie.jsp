 
    <style>
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 3rem 0;
            margin-bottom: 2rem;
        }
        
        .upload-section {
            background: #f8f9fa;
            border: 2px dashed #dee2e6;
            border-radius: 12px;
            padding: 2rem;
            text-align: center;
            transition: all 0.3s ease;
            margin-bottom: 3rem;
        }
        
        .upload-section:hover {
            border-color: #007bff;
            background: #e3f2fd;
        }
        
        .upload-icon {
            font-size: 3rem;
            color: #6c757d;
            margin-bottom: 1rem;
        }
        
        .story-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            transition: all 0.3s ease;
            overflow: hidden;
            margin-bottom: 2rem;
        }
        
        .story-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
        }
        
        .story-header {
            background: linear-gradient(45deg, #ff6b6b, #ffa726);
            color: white;
            padding: 1.5rem;
            position: relative;
        }
        
        .story-header.family { background: linear-gradient(45deg, #4ecdc4, #44a08d); }
        .story-header.travel { background: linear-gradient(45deg, #f093fb, #f5576c); }
        .story-header.work { background: linear-gradient(45deg, #667eea, #764ba2); }
        .story-header.childhood { background: linear-gradient(45deg, #ffecd2, #fcb69f); color: #333; }
        
        .story-badge {
            position: absolute;
            top: 1rem;
            right: 1rem;
            background: rgba(255,255,255,0.2);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 0.25rem 0.75rem;
            font-size: 0.8rem;
        }
        
        .story-content {
            padding: 1.5rem;
        }
        
        .story-preview {
            color: #6c757d;
            font-style: italic;
            line-height: 1.6;
            margin-bottom: 1rem;
        }
        
        .story-meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-top: 1rem;
            border-top: 1px solid #eee;
            font-size: 0.9rem;
            color: #6c757d;
        }
        
        .btn-story-action {
            border-radius: 25px;
            padding: 0.4rem 1rem;
            font-size: 0.85rem;
        }
        
        .filter-tabs {
            margin-bottom: 2rem;
        }
        
        .stats-card {
            background: white;
            border-radius: 15px;
            padding: 1.5rem;
            text-align: center;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            border-left: 4px solid #007bff;
        }
        
        .stats-number {
            font-size: 2rem;
            font-weight: bold;
            color: #007bff;
        }
        
        .search-bar {
            border-radius: 25px;
            border: 2px solid #e9ecef;
            padding: 0.75rem 1.5rem;
        }
        
        .search-bar:focus {
            border-color: #007bff;
            box-shadow: 0 0 0 0.2rem rgba(0,123,255,0.25);
        }
        
        .empty-state {
            text-align: center;
            padding: 3rem;
            color: #6c757d;
        }
        
        .empty-icon {
            font-size: 4rem;
            margin-bottom: 1rem;
            opacity: 0.5;
        }
    </style>
 
    <!-- Header Hero Section -->
    <div class="hero-section">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-8">
                    <h1 class="display-4 fw-bold mb-3">
                        <i class="fas fa-book-open me-3"></i>
                        Le Mie Storie
                    </h1>
                    <p class="lead">Racconta, conserva e condividi i momenti più preziosi della tua vita</p>
                </div>
                <div class="col-md-4 text-end">
                    <div class="stats-card bg-white bg-opacity-10 border-0">
                        <div class="stats-number text-white" id="totalStories">12</div>
                        <div class="text-white-50">Storie Totali</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <!-- Upload Section -->
        <div class="upload-section" onclick="document.getElementById('fileInput').click()">
            <div class="upload-icon">
                <i class="fas fa-cloud-upload-alt"></i>
            </div>
            <h4 class="mb-3">Carica una Nuova Storia</h4>
            <p class="text-muted mb-3">Trascina un file qui o clicca per selezionare</p>
            <button type="button" class="btn btn-primary btn-lg">
                <i class="fas fa-plus me-2"></i>
                Aggiungi Storia
            </button>
            <input type="file" id="fileInput" class="d-none" accept=".txt,.doc,.docx,.pdf" multiple>
            <div class="mt-3">
                <small class="text-muted">Formati supportati: TXT, DOC, DOCX, PDF (Max 10MB)</small>
            </div>
        </div>

        <!-- Search and Filters -->
        <div class="row mb-4">
            <div class="col-md-6">
                <div class="input-group">
                    <span class="input-group-text bg-transparent border-end-0">
                        <i class="fas fa-search text-muted"></i>
                    </span>
                    <input type="text" class="form-control search-bar border-start-0" 
                           placeholder="Cerca nelle tue storie..." id="searchInput">
                </div>
            </div>
            <div class="col-md-6">
                <div class="d-flex gap-2 justify-content-end">
                    <select class="form-select" style="max-width: 200px;" id="categoryFilter">
                        <option value="">Tutte le categorie</option>
                        <option value="family">Famiglia</option>
                        <option value="travel">Viaggi</option>
                        <option value="work">Lavoro</option>
                        <option value="childhood">Infanzia</option>
                    </select>
                    <button class="btn btn-outline-primary" id="sortBtn">
                        <i class="fas fa-sort me-1"></i>
                        Data
                    </button>
                </div>
            </div>
        </div>

        <!-- Filter Tabs -->
        <ul class="nav nav-pills filter-tabs justify-content-center" id="filterTabs">
            <li class="nav-item">
                <a class="nav-link active" data-category="all" href="#">
                    <i class="fas fa-th-list me-2"></i>Tutte (12)
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-category="family" href="#">
                    <i class="fas fa-users me-2"></i>Famiglia (4)
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-category="travel" href="#">
                    <i class="fas fa-plane me-2"></i>Viaggi (3)
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-category="work" href="#">
                    <i class="fas fa-briefcase me-2"></i>Lavoro (2)
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-category="childhood" href="#">
                    <i class="fas fa-child me-2"></i>Infanzia (3)
                </a>
            </li>
        </ul>

        <!-- Stories Grid -->
        <div class="row" id="storiesContainer">
            <!-- Story Card 1 -->
            <div class="col-lg-6 story-item" data-category="family">
                <div class="story-card">
                    <div class="story-header family">
                        <div class="story-badge">Famiglia</div>
                        <h5 class="mb-2">Il Matrimonio dei Nonni</h5>
                        <small><i class="fas fa-calendar me-1"></i>15 Marzo 2024</small>
                    </div>
                    <div class="story-content">
                        <p class="story-preview">
                            "Era il 1952 quando i miei nonni si incontrarono per la prima volta alla festa del paese. Lei aveva 19 anni, lui 22, e da quel momento le loro vite si intrecciarono per sempre..."
                        </p>
                        <div class="story-meta">
                            <div>
                                <i class="fas fa-clock me-1"></i>
                                <span>5 min di lettura</span>
                            </div>
                            <div>
                                <button class="btn btn-outline-primary btn-story-action me-2">
                                    <i class="fas fa-eye me-1"></i>Leggi
                                </button>
                                <div class="dropdown d-inline">
                                    <button class="btn btn-outline-secondary btn-story-action" data-bs-toggle="dropdown">
                                        <i class="fas fa-ellipsis-h"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-edit me-2"></i>Modifica</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-share me-2"></i>Condividi</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-download me-2"></i>Scarica</a></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item text-danger" href="#"><i class="fas fa-trash me-2"></i>Elimina</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Story Card 2 -->
            <div class="col-lg-6 story-item" data-category="travel">
                <div class="story-card">
                    <div class="story-header travel">
                        <div class="story-badge">Viaggi</div>
                        <h5 class="mb-2">Estate in Grecia</h5>
                        <small><i class="fas fa-calendar me-1"></i>10 Agosto 2024</small>
                    </div>
                    <div class="story-content">
                        <p class="story-preview">
                            "Le isole greche hanno sempre avuto un fascino particolare su di me. Quest'estate ho finalmente realizzato il sogno di visitare Santorini e Mykonos..."
                        </p>
                        <div class="story-meta">
                            <div>
                                <i class="fas fa-clock me-1"></i>
                                <span>8 min di lettura</span>
                            </div>
                            <div>
                                <button class="btn btn-outline-primary btn-story-action me-2">
                                    <i class="fas fa-eye me-1"></i>Leggi
                                </button>
                                <div class="dropdown d-inline">
                                    <button class="btn btn-outline-secondary btn-story-action" data-bs-toggle="dropdown">
                                        <i class="fas fa-ellipsis-h"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-edit me-2"></i>Modifica</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-share me-2"></i>Condividi</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-download me-2"></i>Scarica</a></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item text-danger" href="#"><i class="fas fa-trash me-2"></i>Elimina</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Story Card 3 -->
            <div class="col-lg-6 story-item" data-category="childhood">
                <div class="story-card">
                    <div class="story-header childhood">
                        <div class="story-badge">Infanzia</div>
                        <h5 class="mb-2">Il Primo Giorno di Scuola</h5>
                        <small><i class="fas fa-calendar me-1"></i>5 Settembre 2024</small>
                    </div>
                    <div class="story-content">
                        <p class="story-preview">
                            "Ricordo ancora quel settembre del 1985, avevo sei anni e una cartella nuova di zecca. Mamma mi accompagnò fino al cancello della scuola elementare..."
                        </p>
                        <div class="story-meta">
                            <div>
                                <i class="fas fa-clock me-1"></i>
                                <span>3 min di lettura</span>
                            </div>
                            <div>
                                <button class="btn btn-outline-primary btn-story-action me-2">
                                    <i class="fas fa-eye me-1"></i>Leggi
                                </button>
                                <div class="dropdown d-inline">
                                    <button class="btn btn-outline-secondary btn-story-action" data-bs-toggle="dropdown">
                                        <i class="fas fa-ellipsis-h"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-edit me-2"></i>Modifica</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-share me-2"></i>Condividi</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-download me-2"></i>Scarica</a></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item text-danger" href="#"><i class="fas fa-trash me-2"></i>Elimina</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Story Card 4 -->
            <div class="col-lg-6 story-item" data-category="work">
                <div class="story-card">
                    <div class="story-header work">
                        <div class="story-badge">Lavoro</div>
                        <h5 class="mb-2">La Mia Prima Promozione</h5>
                        <small><i class="fas fa-calendar me-1"></i>20 Novembre 2024</small>
                    </div>
                    <div class="story-content">
                        <p class="story-preview">
                            "Dopo tre anni di duro lavoro, finalmente arrivò la chiamata che aspettavo. Il direttore mi convocò nel suo ufficio quel lunedì mattina..."
                        </p>
                        <div class="story-meta">
                            <div>
                                <i class="fas fa-clock me-1"></i>
                                <span>6 min di lettura</span>
                            </div>
                            <div>
                                <button class="btn btn-outline-primary btn-story-action me-2">
                                    <i class="fas fa-eye me-1"></i>Leggi
                                </button>
                                <div class="dropdown d-inline">
                                    <button class="btn btn-outline-secondary btn-story-action" data-bs-toggle="dropdown">
                                        <i class="fas fa-ellipsis-h"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-edit me-2"></i>Modifica</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-share me-2"></i>Condividi</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-download me-2"></i>Scarica</a></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item text-danger" href="#"><i class="fas fa-trash me-2"></i>Elimina</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Story Card 5 -->
            <div class="col-lg-6 story-item" data-category="family">
                <div class="story-card">
                    <div class="story-header family">
                        <div class="story-badge">Famiglia</div>
                        <h5 class="mb-2">La Nascita di Mia Figlia</h5>
                        <small><i class="fas fa-calendar me-1"></i>12 Dicembre 2024</small>
                    </div>
                    <div class="story-content">
                        <p class="story-preview">
                            "Non dimenticherò mai quel giorno di primavera. Alle 3:47 del mattino, dopo 12 ore di travaglio, finalmente sentii il primo pianto di Sofia..."
                        </p>
                        <div class="story-meta">
                            <div>
                                <i class="fas fa-clock me-1"></i>
                                <span>7 min di lettura</span>
                            </div>
                            <div>
                                <button class="btn btn-outline-primary btn-story-action me-2">
                                    <i class="fas fa-eye me-1"></i>Leggi
                                </button>
                                <div class="dropdown d-inline">
                                    <button class="btn btn-outline-secondary btn-story-action" data-bs-toggle="dropdown">
                                        <i class="fas fa-ellipsis-h"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-edit me-2"></i>Modifica</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-share me-2"></i>Condividi</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-download me-2"></i>Scarica</a></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item text-danger" href="#"><i class="fas fa-trash me-2"></i>Elimina</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Story Card 6 -->
            <div class="col-lg-6 story-item" data-category="travel">
                <div class="story-card">
                    <div class="story-header travel">
                        <div class="story-badge">Viaggi</div>
                        <h5 class="mb-2">Avventura in Patagonia</h5>
                        <small><i class="fas fa-calendar me-1"></i>28 Gennaio 2024</small>
                    </div>
                    <div class="story-content">
                        <p class="story-preview">
                            "Il vento della Patagonia soffia forte e costante, ma niente poteva prepararmi alla maestosità dei ghiacciai e delle montagne che si stagliavano davanti a me..."
                        </p>
                        <div class="story-meta">
                            <div>
                                <i class="fas fa-clock me-1"></i>
                                <span>10 min di lettura</span>
                            </div>
                            <div>
                                <button class="btn btn-outline-primary btn-story-action me-2">
                                    <i class="fas fa-eye me-1"></i>Leggi
                                </button>
                                <div class="dropdown d-inline">
                                    <button class="btn btn-outline-secondary btn-story-action" data-bs-toggle="dropdown">
                                        <i class="fas fa-ellipsis-h"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-edit me-2"></i>Modifica</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-share me-2"></i>Condividi</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-download me-2"></i>Scarica</a></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item text-danger" href="#"><i class="fas fa-trash me-2"></i>Elimina</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Empty State (Hidden by default) -->
        <div class="empty-state d-none" id="emptyState">
            <div class="empty-icon">
                <i class="fas fa-book"></i>
            </div>
            <h4>Nessuna storia trovata</h4>
            <p>Prova a modificare i filtri di ricerca o carica la tua prima storia.</p>
            <button class="btn btn-primary" onclick="document.getElementById('fileInput').click()">
                <i class="fas fa-plus me-2"></i>
                Aggiungi la Prima Storia
            </button>
        </div>
    </div>

    <!-- Modal per Upload -->
    <div class="modal fade" id="uploadModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">
                        <i class="fas fa-upload me-2"></i>
                        Carica Nuova Storia
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form id="uploadForm">
                        <div class="mb-3">
                            <label for="storyTitle" class="form-label">Titolo della Storia</label>
                            <input type="text" class="form-control" id="storyTitle" required>
                        </div>
                        <div class="mb-3">
                            <label for="storyCategory" class="form-label">Categoria</label>
                            <select class="form-select" id="storyCategory" required>
                                <option value="">Seleziona categoria</option>
                                <option value="family">Famiglia</option>
                                <option value="travel">Viaggi</option>
                                <option value="work">Lavoro</option>
                                <option value="childhood">Infanzia</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="storyFile" class="form-label">File della Storia</label>
                            <input type="file" class="form-control" id="storyFile" accept=".txt,.doc,.docx,.pdf" required>
                        </div>
                        <div class="mb-3">
                            <label for="storyDescription" class="form-label">Descrizione (opzionale)</label>
                            <textarea class="form-control" id="storyDescription" rows="3" placeholder="Breve descrizione della storia..."></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
                    <button type="button" class="btn btn-primary" id="uploadBtn">
                        <i class="fas fa-upload me-2"></i>
                        Carica Storia
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Upload functionality
        document.getElementById('fileInput').addEventListener('change', function() {
            if (this.files.length > 0) {
                const modal = new bootstrap.Modal(document.getElementById('uploadModal'));
                modal.show();
            }
        });

        // Filter functionality
        document.querySelectorAll('#filterTabs .nav-link').forEach(tab => {
            tab.addEventListener('click', function(e) {
                e.preventDefault();
                
                // Remove active class from all tabs
                document.querySelectorAll('#filterTabs .nav-link').forEach(t => t.classList.remove('active'));
                // Add active class to clicked tab
                this.classList.add('active');
                
                const category = this.getAttribute('data-category');
                filterStories(category);
            });
        });

        // Search functionality
        document.getElementById('searchInput').addEventListener('input', function() {
            const searchTerm = this.value.toLowerCase();
            filterStoriesBySearch(searchTerm);
        });

        function filterStories(category) {
            const stories = document.querySelectorAll('.story-item');
            let visibleCount = 0;
            
            stories.forEach(story => {
                if (category === 'all' || story.getAttribute('data-category') === category) {
                    story.style.display = 'block';
                    visibleCount++;
                } else {
                    story.style.display = 'none';
                }
            });
            
            toggleEmptyState(visibleCount === 0);
        }

        function filterStoriesBySearch(searchTerm) {
            const stories = document.querySelectorAll('.story-item');
            let visibleCount = 0;
            
            stories.forEach(story => {
                const title = story.querySelector('h5').textContent.toLowerCase();
                const preview = story.querySelector('.story-preview').textContent.toLowerCase();
                
                if (title.includes(searchTerm) || preview.includes(searchTerm)) {
                    story.style.display = 'block';
                    visibleCount++;
                } else {
                    story.style.display = 'none';
                }
            });
            
            toggleEmptyState(visibleCount === 0);
        }

        function toggleEmptyState(show) {
            const emptyState = document.getElementById('emptyState');
            const storiesContainer = document.getElementById('storiesContainer');
            
            if (show) {
                emptyState.classList.remove('d-none');
                storiesContainer.style.display = 'none';
            } else {
                emptyState.classList.add('d-none');
                storiesContainer.style.display = 'flex';
            }
        }

        // Upload form submission
        document.getElementById('uploadBtn').addEventListener('click', function() {
            const form = document.getElementById('uploadForm');
            if (form.checkValidity()) {
                // Simulate upload process
                this.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Caricamento...';
                this.disabled = true;
                
                setTimeout(() => {
                    alert('Storia caricata con successo!');
                    bootstrap.Modal.getInstance(document.getElementById('uploadModal')).hide();
                    form.reset();
                    this.innerHTML = '<i class="fas fa-upload me-2"></i>Carica Storia';
                    this.disabled = false;
                    
                    // Update total count
                    const totalElement = document.getElementById('totalStories');
                    totalElement.textContent = parseInt(totalElement.textContent) + 1;
                }, 2000);
            } else {
                form.reportValidity();
            }
        });

        // Drag and drop functionality
        const uploadSection = document.querySelector('.upload-section');
        
        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
            uploadSection.addEventListener(eventName, preventDefaults, false);
        });

        function preventDefaults(e) {
            e.preventDefault();
            e.stopPropagation();
        }

        ['dragenter', 'dragover'].forEach(eventName => {
            uploadSection.addEventListener(eventName, highlight, false);
        });

        ['dragleave', 'drop'].forEach(eventName => {
            uploadSection.addEventListener(eventName, unhighlight, false);
        });

        function highlight(e) {
            uploadSection.classList.add('border-primary', 'bg-primary', 'bg-opacity-10');
        }

        function unhighlight(e) {
            uploadSection.classList.remove('border-primary', 'bg-primary', 'bg-opacity-10');
        }

        uploadSection.addEventListener('drop', handleDrop, false);

        function handleDrop(e) {
            const dt = e.dataTransfer;
            const files = dt.files;
            
            if (files.length > 0) {
                document.getElementById('fileInput').files = files;
                const modal = new bootstrap.Modal(document.getElementById('uploadModal'));
                modal.show();
            }
        }

        // Sort functionality
        let sortAscending = true;
        document.getElementById('sortBtn').addEventListener('click', function() {
            const container = document.getElementById('storiesContainer');
            const stories = Array.from(container.children);
            
            stories.sort((a, b) => {
                const dateA = new Date(a.querySelector('small').textContent.replace('📅 ', ''));
                const dateB = new Date(b.querySelector('small').textContent.replace('📅 ', ''));
                
                return sortAscending ? dateA - dateB : dateB - dateA;
            });
            
            // Clear container and re-append sorted stories
            container.innerHTML = '';
            stories.forEach(story => container.appendChild(story));
            
            // Update button text
            this.innerHTML = sortAscending 
                ? '<i class="fas fa-sort-up me-1"></i>Data ↑' 
                : '<i class="fas fa-sort-down me-1"></i>Data ↓';
            
            sortAscending = !sortAscending;
        });

        // Category filter dropdown
        document.getElementById('categoryFilter').addEventListener('change', function() {
            const category = this.value;
            
            // Update active tab
            document.querySelectorAll('#filterTabs .nav-link').forEach(tab => {
                tab.classList.remove('active');
                if (tab.getAttribute('data-category') === category || (category === '' && tab.getAttribute('data-category') === 'all')) {
                    tab.classList.add('active');
                }
            });
            
            filterStories(category || 'all');
        });

        // Story actions
        document.addEventListener('click', function(e) {
            if (e.target.closest('.dropdown-item')) {
                const action = e.target.closest('.dropdown-item').textContent.trim();
                const storyCard = e.target.closest('.story-card');
                const storyTitle = storyCard.querySelector('h5').textContent;
                
                switch(true) {
                    case action.includes('Modifica'):
                        showEditModal(storyTitle);
                        break;
                    case action.includes('Condividi'):
                        shareStory(storyTitle);
                        break;
                    case action.includes('Scarica'):
                        downloadStory(storyTitle);
                        break;
                    case action.includes('Elimina'):
                        deleteStory(storyCard, storyTitle);
                        break;
                }
            }
            
            // Read story action
            if (e.target.closest('.btn-story-action') && e.target.closest('.btn-story-action').textContent.includes('Leggi')) {
                const storyTitle = e.target.closest('.story-card').querySelector('h5').textContent;
                readStory(storyTitle);
            }
        });

        function showEditModal(storyTitle) {
            alert(`Apertura editor per: "${storyTitle}"`);
            // Qui implementeresti il modal di modifica
        }

        function shareStory(storyTitle) {
            if (navigator.share) {
                navigator.share({
                    title: storyTitle,
                    text: `Leggi questa storia: ${storyTitle}`,
                    url: window.location.href
                });
            } else {
                // Fallback: copia link negli appunti
                navigator.clipboard.writeText(window.location.href).then(() => {
                    showToast('Link copiato negli appunti!');
                });
            }
        }

        function downloadStory(storyTitle) {
            // Simula download
            const link = document.createElement('a');
            link.href = '#'; // Qui metteresti l'URL del file
            link.download = `${storyTitle}.pdf`;
            link.click();
            showToast(`Download di "${storyTitle}" avviato!`);
        }

        function deleteStory(storyCard, storyTitle) {
            if (confirm(`Sei sicuro di voler eliminare la storia "${storyTitle}"?`)) {
                // Animazione di eliminazione
                storyCard.style.transition = 'all 0.3s ease';
                storyCard.style.opacity = '0';
                storyCard.style.transform = 'scale(0.8)';
                
                setTimeout(() => {
                    storyCard.closest('.story-item').remove();
                    
                    // Aggiorna contatore
                    const totalElement = document.getElementById('totalStories');
                    totalElement.textContent = parseInt(totalElement.textContent) - 1;
                    
                    // Controlla se rimangono storie
                    const remainingStories = document.querySelectorAll('.story-item').length;
                    if (remainingStories === 0) {
                        toggleEmptyState(true);
                    }
                    
                    showToast(`Storia "${storyTitle}" eliminata con successo!`);
                }, 300);
            }
        }

        function readStory(storyTitle) {
            // Simula apertura lettore storie
            showToast(`Apertura lettore per: "${storyTitle}"`);
            // Qui implementeresti il modal di lettura o redirect a pagina dedicata
        }

        function showToast(message) {
            // Crea toast notification
            const toastContainer = document.getElementById('toastContainer') || createToastContainer();
            
            const toast = document.createElement('div');
            toast.className = 'toast align-items-center text-white bg-success border-0';
            toast.setAttribute('role', 'alert');
            toast.innerHTML = `
                <div class="d-flex">
                    <div class="toast-body">${message}</div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            `;
            
            toastContainer.appendChild(toast);
            const bsToast = new bootstrap.Toast(toast);
            bsToast.show();
            
            // Rimuovi toast dopo che è stato nascosto
            toast.addEventListener('hidden.bs.toast', () => {
                toast.remove();
            });
        }

        function createToastContainer() {
            const container = document.createElement('div');
            container.id = 'toastContainer';
            container.className = 'toast-container position-fixed bottom-0 end-0 p-3';
            container.style.zIndex = '1055';
            document.body.appendChild(container);
            return container;
        }

        // Initialize page
        document.addEventListener('DOMContentLoaded', function() {
            // Aggiorna contatori nelle tabs
            updateTabCounts();
            
            // Animazione di caricamento cards
            const cards = document.querySelectorAll('.story-card');
            cards.forEach((card, index) => {
                card.style.opacity = '0';
                card.style.transform = 'translateY(20px)';
                
                setTimeout(() => {
                    card.style.transition = 'all 0.5s ease';
                    card.style.opacity = '1';
                    card.style.transform = 'translateY(0)';
                }, index * 100);
            });
        });

        function updateTabCounts() {
            const categories = ['all', 'family', 'travel', 'work', 'childhood'];
            
            categories.forEach(category => {
                const tab = document.querySelector(`[data-category="${category}"]`);
                if (tab) {
                    const count = category === 'all' 
                        ? document.querySelectorAll('.story-item').length
                        : document.querySelectorAll(`[data-category="${category}"]`).length;
                    
                    const tabText = tab.textContent.replace(/\(\d+\)/, `(${count})`);
                    tab.innerHTML = tab.innerHTML.replace(/\(\d+\)/, `(${count})`);
                }
            });
        }

        // Responsive behavior
        window.addEventListener('resize', function() {
            // Adatta layout per mobile
            if (window.innerWidth < 768) {
                document.querySelectorAll('.story-card').forEach(card => {
                    card.style.marginBottom = '1rem';
                });
            }
        });

        // Keyboard shortcuts
        document.addEventListener('keydown', function(e) {
            // Ctrl/Cmd + U per upload
            if ((e.ctrlKey || e.metaKey) && e.key === 'u') {
                e.preventDefault();
                document.getElementById('fileInput').click();
            }
            
            // Ctrl/Cmd + F per focus su search
            if ((e.ctrlKey || e.metaKey) && e.key === 'f') {
                e.preventDefault();
                document.getElementById('searchInput').focus();
            }
        });

        console.log('📚 Pagina Storie caricata con successo!');
    </script>
</body>
</html>
 <style>
 .divMission {
    background-image: url(./images/sp/mission.jpg);
    -webkit-background-size: cover;
    background-size: cover;
    margin-top: 0px;
    width:100%;
    height:300px;
    
    
}
 </style>
 <header>
    <!-- navbar -->
    <nav class="navbar navbar-expand-lg fixed-top nav-menu">
      <a href="#" class="navbar-brand text-light"><span class="h2 font-weight-bold">SuiPassi</span></a>
      <button class="navbar-toggler nav-button" type="button" data-toggle="collapse" data-target="#myNavbar">
        <div class="bg-light line1"></div>
        <div class="bg-light line2"></div>
        <div class="bg-light line3"></div>
      </button>
      <div class="collapse navbar-collapse justify-content-end text-uppercase font-weight-bold" id="myNavbar">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a href="#" class="nav-link m-2 menu-item nav-active">Home</a>
          </li>
          <li class="nav-item">
            <a href="people.jsp" class="nav-link m-2 menu-item">Editor</a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link m-2 menu-item">Customers</a>
          </li>
          <li class="nav-item">
            <a href="#" class="nav-link m-2 menu-item">Pricing</a>
          </li>
          <li class="nav-item">
            <a href="login.jsp" class="nav-link m-2 menu-item">Login</a>
          </li>
        </ul>
      </div>
    </nav>
    <!-- end of navbar -->

    <!-- banner -->
    <div class="text-light text-md-right text-center banner">
      <h1 class="display-4 banner-heading">Benvenuto in <span class="text-uppercase">Sui </span><span class="display-3">Passi</span></h1>
      <p class="lead banner-par">Storie memorie persone gratitudine</p>
      <h1>
      <%
      //RsTable rs=getRsTable(sys,"Select * from People");
      //out.write(rs.asTable(true,true,""));
      %>
      </h1>
    </div>
    <!-- end of banner -->
  </header>
  <!-- end of header -->

  <!-- mission -->
  <section class="mission">
    <div class="container-fluid divMission ">
    
       <div class="p-2 pt-12 row text-white style="text-align:center;width:100%;padding-top:40%">
       <h1 class="pt-30"> ..e tutto questo andrà perduto.. come lacrime nella pioggia</h1>
      </div>
    </div>
  </section>
  <!-- end of mission -->
  
  <!-- collection -->
  <section class="bg-secondary py-4">
    <div class="container-fluid">
      <!-- title -->
      <div class="row text-white text-center">
        <div class="col m-4">
          <h1 class="display-4 mb-4">Collection</h1>
          <div class="underline mb-4"></div>
          <p class="lead">Lorem ipsum dolor sit amet consectetur adipisicing elit. Accusantium inventore, sint quisquam fugiat pariatur culpa officia. Eveniet omnis quia tempora.</p>
        </div>
      </div>
      <!-- end of title -->
      <div class="row">
        <div class="col-lg-4 col-sm-6 my-5">
          <div class="card border-0 card-shadow">
            <img src="images/nature.jpeg" class="card-img">
            <div class="card-img-overlay">
              <h5 class="text-white text-uppercase font-weight-bold p-2 heading">Nature Photography</h5>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-sm-6 my-5">
          <div class="card border-0 card-shadow">
            <img src="images/wedding.jpeg" class="card-img">
            <div class="card-img-overlay">
              <h5 class="text-white text-uppercase font-weight-bold p-2 heading">Wedding Photography</h5>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-sm-6 my-5">
          <div class="card border-0 card-shadow">
            <img src="images/party.jpeg" class="card-img">
            <div class="card-img-overlay">
              <h5 class="text-white text-uppercase font-weight-bold p-2 heading">Party Photography</h5>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-sm-6 my-5">
          <div class="card border-0 card-shadow">
            <img src="images/business.jpeg" class="card-img">
            <div class="card-img-overlay">
              <h5 class="text-white text-uppercase font-weight-bold p-2 heading">Business Photography</h5>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-sm-6 my-5">
          <div class="card border-0 card-shadow">
            <img src="images/fashion.jpeg" class="card-img">
            <div class="card-img-overlay">
              <h5 class="text-white text-uppercase font-weight-bold p-2 heading">Fashion Photography</h5>
            </div>
          </div>
        </div>
        <div class="col-lg-4 col-sm-6 my-5">
          <div class="card border-0 card-shadow">
            <img src="images/family.jpeg" class="card-img">
            <div class="card-img-overlay">
              <h5 class="text-white text-uppercase font-weight-bold p-2 heading">Family Photography</h5>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- end of collection -->

   <!-- pricing -->
  <section class="bg-light text-center p-5">
    <div class="container-fluid">
      <!-- title -->
      <div class="row text-muted text-center">
        <div class="col m-4">
          <h1 class="display-4 mb-4">Join Us</h1>
          <div class="underline-dark mb-4"></div>
          <p class="lead">Lorem ipsum dolor sit amet consectetur adipisicing elit. Accusantium inventore, sint quisquam fugiat pariatur culpa officia. Eveniet omnis quia tempora.</p>
        </div>
      </div>
      <!-- end of title -->
      <div class="row align-items-center">
        <div class="col-lg-4">
          <div class="card card-1 text-light py-4 my-4 mx-auto">
            <div class="card-body">
              <h5 class="text-uppercase font-weight-bold mb-5">Monthly Membership</h5>
              <h1 class="display-4"><i class="fas fa-dollar-sign"></i> 19</h1>
              <ul class="list-unstyled">
                <li class="font-weight-bold py-3 card-list-item">Photoshop</li>
                <li class="font-weight-bold py-3 card-list-item">After Effects</li>
                <li class="font-weight-bold py-3 card-list-item">Graphic Design</li>
                <li class="font-weight-bold py-3 card-list-item border-0">Video Montage</li>
              </ul>
              <a href="#" class="btn p-2 text-uppercase font-weight-bold price-card-button text-light">sign-up!</a>
            </div>
          </div>
        </div>
        <div class="col-lg-4">
          <div class="card card-2 text-light py-4 my-4 mx-auto">
            <div class="card-body">
              <h5 class="text-uppercase font-weight-bold mb-5">Unlimited Access</h5>
              <h1 class="display-4"><i class="fas fa-dollar-sign"></i> 499</h1>
              <ul class="list-unstyled">
                <li class="font-weight-bold py-3 card-list-item">Photoshop</li>
                <li class="font-weight-bold py-3 card-list-item">After Effects</li>
                <li class="font-weight-bold py-3 card-list-item">Graphic Design</li>
                <li class="font-weight-bold py-3 card-list-item ">Video Montage</li>
                <li class="font-weight-bold py-3 card-list-item border-0">Clip Making</li>
              </ul>
              <a href="#" class="btn p-2 text-uppercase font-weight-bold price-card-button text-light">sign-up!</a>
            </div>
          </div>
        </div>
        <div class="col-lg-4">
          <div class="card card-3 text-light py-4 my-4 mx-auto">
            <div class="card-body">
              <h5 class="text-uppercase font-weight-bold mb-5">Annual Membership</h5>
              <h1 class="display-4"><i class="fas fa-dollar-sign"></i> 199</h1>
              <ul class="list-unstyled">
                <li class="font-weight-bold py-3 card-list-item">Photoshop</li>
                <li class="font-weight-bold py-3 card-list-item">After Effects</li>
                <li class="font-weight-bold py-3 card-list-item">Graphic Design</li>
                <li class="font-weight-bold py-3 card-list-item border-0">Video Montage</li>
              </ul>
              <a href="#" class="btn p-2 text-uppercase font-weight-bold price-card-button text-light">sign-up!</a>
            </div>
          </div>
        </div>
      </div>
      <div class="my-5">
        <h2 class="text-muted mb-4">Thanks for being with us!</h2>
        <i class="fas fa-coffee fa-3x"></i>
      </div>
    </div>
  </section>
  <!-- end of pricing -->

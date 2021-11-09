<!doctype html>
<#import "/spring.ftl" as spring />
<#assign security = JspTaglibs["http://www.springframework.org/security/tags"]>
<html lang="zh">

  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">
    <base href="<@spring.url '/'/>" />

    <title>Login - G.Next Admin</title>

    <!-- include common vendor stylesheets & fontawesome -->
    <link rel="stylesheet" type="text/css" href="./vendors/bootstrap/bootstrap.min.css">

    <link rel="stylesheet" type="text/css" href="./vendors/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" type="text/css" href="./vendors/fontawesome/css/regular.min.css">
    <link rel="stylesheet" type="text/css" href="./vendors/fontawesome/css/brands.min.css">
    <link rel="stylesheet" type="text/css" href="./vendors/fontawesome/css/solid.min.css">

    <!-- include fonts -->
    <link rel="stylesheet" type="text/css" href="./vendors/opensans/css">

    <!-- ace.css -->
    <link rel="stylesheet" type="text/css" href="./vendors/ace/ace.min.css">

    <!-- favicon -->
    <link rel="icon" type="image/png" href="./img/favicon.png" />

    <!-- "Login" page styles, specific to this page for demo only -->
    <style>.body-container {
        background-image: linear-gradient(#6baace, #264783);
        background-attachment: fixed;
        background-repeat: no-repeat;
      }

      .carousel-item>div {
        height: 100%;
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center;
      }

      /* these rules are used to make sure in mobile devices, tab panes are not all the same height (for example 'forgot' pane is not as tall as 'signup' pane) */

      @media (max-width: 1199.98px) {
        .tab-sliding .tab-pane:not(.active) {
          max-height: 0 !important;
        }

        .tab-sliding .tab-pane.active {
          min-height: 80vh;
          max-height: none !important;
        }
      }
    </style>
  </head>

  <body>
    <div class="body-container">

      <div class="main-container container bgc-transparent">

        <div class="main-content minh-100 justify-content-center">
          <div class="p-2 p-md-4">
            <div class="row" id="row-1">
              <div class="col-12 col-xl-10 offset-xl-1 bgc-white shadow radius-1 overflow-hidden">

                <div class="row" id="row-2">

                  <div id="id-col-intro" class="col-lg-5 d-none d-lg-flex border-r-1 brc-default-l3 px-0">
                    <!-- the left side section is carousel in this demo, to show some example variations -->

                    <div id="loginBgCarousel" class="carousel slide minw-100 h-100">
                      <ol class="d-none carousel-indicators">
                        <li data-target="#loginBgCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#loginBgCarousel" data-slide-to="1"></li>
                        <li data-target="#loginBgCarousel" data-slide-to="2"></li>
                        <li data-target="#loginBgCarousel" data-slide-to="3"></li>
                      </ol>

                      <div class="carousel-inner minw-100 h-100">
                        <div class="carousel-item active minw-100 h-100">
                          <!-- default carousel section that you see when you open login page -->
                          <div style="background-image: url(/img/login-bg-1.svg);" class="px-3 bgc-blue-l4 d-flex flex-column align-items-center justify-content-center">
                            <a class="mt-5 mb-2" href="dashboard.html">
                              <i class="fa fa-leaf text-success-m2 fa-3x"></i>
                            </a>

                            <h2 class="text-primary-d1">
                              Ace <span class="text-80 text-dark-l1">Application</span>
                            </h2>

                            <div class="mt-5 mx-4 text-dark-tp3">
                              <span class="text-120">
                           Join our community to make friends,<br /> meet experts &amp; receive exclusive offers!
                       </span>
                              <hr class="mb-1 brc-black-tp10" />
                              <div>
                                <a id="id-start-carousel" href="../index.html#" class="text-95 text-dark-l2 d-inline-block mt-3">
                                  <i class="far fa-image text-110 text-purple-m1 mr-1 w-2"></i>
                                  Change background image
                                </a>
                                <br />
                                <a id="id-remove-carousel" href="../index.html#" class="text-md text-dark-l2 d-inline-block mt-3">
                                  <i class="far fa-trash-alt text-110 text-orange-d1 mr-1 w-2"></i>
                                  Remove this section
                                </a>
                                <br />
                                <a id="id-fullscreen" href="../index.html#" class="text-md text-dark-l2 d-inline-block mt-3">
                                  <i class="fa fa-expand text-110 text-green-m1 mr-1 w-2"></i>
                                  Make full-size
                                </a>
                              </div>
                            </div>

                            <div class="mt-auto mb-4 text-dark-tp2">
                              Ace Company &copy; 2021
                            </div>
                          </div>
                        </div>



                        <div class="carousel-item minw-100 h-100">
                          <!-- the second carousel item with dark background -->
                          <div style="background-image: url(/img/login-bg-2.svg);" class="d-flex flex-column align-items-center justify-content-start">
                            <a class="mt-5 mb-2" href="dashboard.html">
                              <i class="fa fa-leaf text-success-m2 fa-3x"></i>
                            </a>

                            <h2 class="text-blue-l1">
                              Ace <span class="text-80 text-white-tp3">Application</span>
                            </h2>
                          </div>
                        </div>



                        <div class="carousel-item minw-100 h-100">
                          <div style="background-image: url(/img/login-bg-3.jpg);" class="d-flex flex-column align-items-center justify-content-start">
                            <div class="bgc-black-tp4 radius-1 p-3 w-90 text-center my-3 h-100">
                              <a class="mt-5 mb-2" href="dashboard.html">
                                <i class="fa fa-leaf text-success-m2 fa-3x"></i>
                              </a>

                              <h2 class="text-blue-l1">
                                Ace <span class="text-80 text-white-tp3">Application</span>
                              </h2>
                            </div>
                          </div>
                        </div>



                        <div class="carousel-item minw-100 h-100">
                          <div style="background-image: url(/img/login-bg-4.jpg);" class="d-flex flex-column align-items-center justify-content-start">
                            <a class="mt-5 mb-2" href="dashboard.html">
                              <i class="fa fa-leaf text-success-m2 fa-3x"></i>
                            </a>

                            <h2 class="text-blue-d1">
                              Ace <span class="text-80 text-dark-tp3">Application</span>
                            </h2>
                          </div>
                        </div>

                      </div>
                    </div>
                  </div>


                  <div id="id-col-main" class="col-12 col-lg-7 py-lg-5 bgc-white px-0">
                    <!-- you can also use these tab links -->
                    <ul class="d-none mt-n4 mb-4 nav nav-tabs nav-tabs-simple justify-content-end bgc-black-tp11" role="tablist">
                      <li class="nav-item mx-2">
                        <a class="nav-link active px-2" data-toggle="tab" href="../index.html#id-tab-login" role="tab" aria-controls="id-tab-login" aria-selected="true">
                          Login
                        </a>
                      </li>
                      <li class="nav-item mx-2">
                        <a class="nav-link px-2" data-toggle="tab" href="../index.html#id-tab-signup" role="tab" aria-controls="id-tab-signup" aria-selected="false">
                          Signup
                        </a>
                      </li>
                    </ul>


                    <div class="tab-content tab-sliding border-0 p-0" data-swipe="right">

                      <div class="tab-pane active show mh-100 px-3 px-lg-0 pb-3" id="id-tab-login">
                        <!-- show this in desktop -->
                        <div class="d-none d-lg-block col-md-6 offset-md-3 mt-lg-4 px-0">
                          <h4 class="text-dark-tp4 border-b-1 brc-secondary-l2 pb-1 text-130">
                            <i class="fa fa-coffee text-orange-m1 mr-1"></i>
                            Welcome Back
                          </h4>
                        </div>

                        <!-- show this in mobile device -->
                        <div class="d-lg-none text-secondary-m1 my-4 text-center">
                          <a href="dashboard.html">
                            <i class="fa fa-leaf text-success-m2 text-200 mb-4"></i>
                          </a>
                          <h1 class="text-170">
                            <span class="text-blue-d1">
                                Ace <span class="text-80 text-dark-tp3">Application</span>
                            </span>
                          </h1>

                          Welcome back
                        </div>


                        <form autocomplete="off" class="form-row mt-4">

                          <@security.csrfInput />

                          <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3">
                            <div class="d-flex align-items-center input-floating-label text-blue brc-blue-m2">
                              <input placeholder="Username" type="text" class="form-control form-control-lg pr-4 shadow-none" id="id-login-username" />
                              <i class="fa fa-user text-grey-m2 ml-n4"></i>
                              <label class="floating-label text-grey-l1 ml-n3" for="id-login-username">
                                Username
                              </label>
                            </div>
                          </div>


                          <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 mt-2 mt-md-1">
                            <div class="d-flex align-items-center input-floating-label text-blue brc-blue-m2">
                              <input placeholder="Password" type="password" class="form-control form-control-lg pr-4 shadow-none" id="id-login-password" />
                              <i class="fa fa-key text-grey-m2 ml-n4"></i>
                              <label class="floating-label text-grey-l1 ml-n3" for="id-login-password">
                                Password
                              </label>
                            </div>
                          </div>


                          <div class="col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 text-right text-md-right mt-n2 mb-2">
                            <a href="../index.html#" class="text-primary-m1 text-95" data-toggle="tab" data-target="#id-tab-forgot">
                              Forgot Password?
                            </a>
                          </div>


                          <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3">
                            <label class="d-inline-block mt-3 mb-0 text-dark-l1">
                              <input type="checkbox" class="mr-1" id="id-remember-me" />
                              Remember me
                            </label>

                            <button type="button" class="btn btn-primary btn-block px-4 btn-bold mt-2 mb-4">
                              Sign In
                            </button>
                          </div>
                        </form>


                        <div class="form-row">
                          <div class="col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 d-flex flex-column align-items-center justify-content-center">

                            <hr class="brc-default-l2 mt-0 mb-2 w-100" />

                            <div class="p-0 px-md-2 text-dark-tp3 my-3">
                              Not a member?
                              <a class="text-success-m1 text-600 mx-1" data-toggle="tab" data-target="#id-tab-signup" href="../index.html#">
                                Signup now
                              </a>
                            </div>

                            <hr class="brc-default-l2 w-100 mb-2" />
                            <div class="mt-n4 bgc-white-tp2 px-3 py-1 text-secondary-d3 text-90">Or Get Started Using</div>

                            <div class="my-2">
                              <button type="button" class="btn btn-bgc-white btn-lighter-primary btn-h-primary btn-a-primary border-2 radius-round btn-lg mx-1">
                                <i class="fab fa-facebook-f text-110"></i>
                              </button>

                              <button type="button" class="btn btn-bgc-white btn-lighter-info btn-h-info btn-a-info border-2 radius-round btn-lg px-25 mx-1">
                                <i class="fab fa-twitter text-110"></i>
                              </button>

                              <button type="button" class="btn btn-bgc-white btn-lighter-red btn-h-red btn-a-red border-2 radius-round btn-lg px-25 mx-1">
                                <i class="fab fa-google text-110"></i>
                              </button>
                            </div>

                          </div>
                        </div>
                      </div>


                      <div class="tab-pane mh-100 px-3 px-lg-0 pb-3" id="id-tab-signup" data-swipe-prev="#id-tab-login">
                        <div class="position-tl ml-3 mt-3 mt-lg-0">
                          <a href="../index.html#" class="btn btn-light-default btn-h-light-default btn-a-light-default btn-bgc-tp" data-toggle="tab" data-target="#id-tab-login">
                            <i class="fa fa-arrow-left"></i>
                          </a>
                        </div>

                        <!-- show this in desktop -->
                        <div class="d-none d-lg-block col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 mt-lg-4 px-0">
                          <h4 class="text-dark-tp4 border-b-1 brc-grey-l1 pb-1 text-130">
                            <i class="fa fa-user text-purple mr-1"></i>
                            Create an Account
                          </h4>
                        </div>

                        <!-- show this in mobile device -->
                        <div class="d-lg-none text-secondary-m1 my-4 text-center">
                          <i class="fa fa-leaf text-success-m2 text-200 mb-4"></i>
                          <h1 class="text-170">
                            <span class="text-blue-d1">Ace <span class="text-80 text-dark-tp4">Application</span></span>
                          </h1>

                          Create an Account
                        </div>


                        <form autocomplete="off" class="form-row mt-4">
                          <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3">
                            <div class="d-flex align-items-center input-floating-label text-success brc-success-m2">
                              <input placeholder="Email" type="email" class="form-control form-control-lg pr-4 shadow-none" id="id-signup-email" />
                              <i class="fa fa-envelope text-grey-m2 ml-n4"></i>
                              <label class="floating-label text-grey-l1 text-100 ml-n3" for="id-signup-email">
                                Email
                              </label>
                            </div>
                          </div>


                          <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 mt-1">
                            <div class="d-flex align-items-center input-floating-label text-success brc-success-m2">
                              <input placeholder="Username" type="text" class="form-control form-control-lg pr-4 shadow-none" id="id-signup-username" />
                              <i class="fa fa-user text-grey-m2 ml-n4"></i>
                              <label class="floating-label text-grey-l1 text-100 ml-n3" for="id-signup-username">
                                Username
                              </label>
                            </div>
                          </div>


                          <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 mt-1">
                            <div class="d-flex align-items-center input-floating-label text-success brc-success-m2">
                              <input placeholder="Password" type="password" class="form-control form-control-lg pr-4 shadow-none" id="id-signup-password" />
                              <i class="fa fa-key text-grey-m2 ml-n4"></i>
                              <label class="floating-label text-grey-l1 text-100 ml-n3" for="id-signup-password">
                                Password
                              </label>
                            </div>
                          </div>


                          <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 mt-1">
                            <div class="d-flex align-items-center input-floating-label text-success brc-success-m2">
                              <input placeholder="Confirm Password" type="password" class="form-control form-control-lg pr-4 shadow-none" id="id-signup-password2" />
                              <i class="fas fa-sync-alt text-grey-m2 ml-n4"></i>
                              <label class="floating-label text-grey-l1 text-100 ml-n3" for="id-signup-password2">
                                Confirm Password
                              </label>
                            </div>
                          </div>


                          <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 mt-2">
                            <label class="d-inline-block mt-3 mb-0 text-secondary-d2">
                              <input type="checkbox" class="mr-1" id="id-agree" />
                              <span class="text-dark-m3">I have read and agree to <a href="../index.html#" class="text-blue-d2">terms</a></span>
                            </label>

                            <button type="button" class="btn btn-success btn-block px-4 btn-bold mt-2 mb-3">
                              Sign Up
                            </button>
                          </div>
                        </form>


                        <div class="form-row w-100">
                          <div class="col-12 col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 d-flex flex-column align-items-center justify-content-center">

                            <hr class="brc-default-l2 mt-0 mb-2 w-100" />

                            <div class="p-0 px-md-2 text-dark-tp4 my-3">
                              Already a member?
                              <a class="text-blue-d1 text-600 mx-1" data-toggle="tab" data-target="#id-tab-login" href="../index.html#">
                                Login here
                              </a>
                            </div>

                            <hr class="brc-default-l2 w-100 mb-2" />
                            <div class="mt-n4 bgc-white-tp2 px-3 py-1 text-secondary-d3 text-90">Or Register Using</div>

                            <div class="mt-2 mb-3">
                              <button type="button" class="btn btn-primary border-2 radius-round btn-lg mx-1">
                                <i class="fab fa-facebook-f text-110"></i>
                              </button>

                              <button type="button" class="btn btn-blue border-2 radius-round btn-lg px-25 mx-1">
                                <i class="fab fa-twitter text-110"></i>
                              </button>

                              <button type="button" class="btn btn-danger border-2 radius-round btn-lg px-25 mx-1">
                                <i class="fab fa-google text-110"></i>
                              </button>
                            </div>

                          </div>
                        </div>
                      </div>


                      <div class="tab-pane mh-100 px-3 px-lg-0 pb-3" id="id-tab-forgot" data-swipe-prev="#id-tab-login">
                        <div class="position-tl ml-3 mt-2">
                          <a href="../index.html#" class="btn btn-light-default btn-h-light-default btn-a-light-default btn-bgc-tp" data-toggle="tab" data-target="#id-tab-login">
                            <i class="fa fa-arrow-left"></i>
                          </a>
                        </div>


                        <div class="col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 mt-5 px-0">
                          <h4 class="pt-4 pt-md-0 text-dark-tp4 border-b-1 brc-grey-l2 pb-1 text-130">
                            <i class="fa fa-key text-brown-m1 mr-1"></i>
                            Recover Password
                          </h4>
                        </div>


                        <form autocomplete="off" class="form-row mt-4">
                          <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3">
                            <label class="text-secondary-d3 mb-3">
                              Enter your email address and we'll send you the instructions:
                            </label>
                            <div class="d-flex align-items-center">
                              <input type="email" class="form-control form-control-lg pr-4 shadow-none" id="id-recover-email" placeholder="Email" />
                              <i class="fa fa-envelope text-grey-m2 ml-n4"></i>
                            </div>
                          </div>

                          <div class="form-group col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 mt-1">
                            <button type="button" class="btn btn-orange btn-block px-4 btn-bold mt-2 mb-4">
                              Continue
                            </button>
                          </div>
                        </form>


                        <div class="form-row w-100">
                          <div class="col-12 col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3 d-flex flex-column align-items-center justify-content-center">

                            <hr class="brc-default-l2 mt-0 mb-2 w-100" />

                            <div class="p-0 px-md-2 text-dark-tp4 my-3">
                              <a class="text-blue-d1 text-600 btn-text-slide-x" data-toggle="tab" data-target="#id-tab-login" href="../index.html#">
                                <i class="btn-text-2 fa fa-arrow-left text-110 align-text-bottom mr-2"></i>Back to Login
                              </a>
                            </div>

                          </div>
                        </div>
                      </div>

                    </div><!-- .tab-content -->
                  </div>

                </div><!-- /.row -->

              </div><!-- /.col -->
            </div><!-- /.row -->

            <div class="d-lg-none my-3 text-white-tp1 text-center">
              <i class="fa fa-leaf text-success-l3 mr-1 text-110"></i> G.Next &copy; 2021
            </div>
          </div>
        </div>

      </div>

    </div>

    <!-- include common vendor scripts used in demo pages -->
    <script src="./vendors/jquery/jquery.min.js"></script>
    <script src="./vendors/popper/popper.min.js"></script>
    <script src="./vendors/bootstrap/bootstrap.min.js"></script>

    <!-- include vendor scripts used in "Login" page. see "/views//pages/partials/page-login/@vendor-scripts.hbs" -->

    <!-- include ace.js -->
    <script src="./vendors/ace/ace.min.js"></script>

    <!-- "Login" page script to enable its demo functionality -->
    <script>
      jQuery(function($) {
        // because "Login Here" and "Signup now" links are not inside a "UL" or ".nav", they preserve "active" class
        // and we should remove that, to be able to move between tab panes
        $('a[data-toggle="tab"]')
          .on('click', function() {
            $('a[data-toggle="tab"]').removeClass('active')
          })


        // start/show carousel to change backgrounds
        $('#id-start-carousel')
          .on('click', function(e) {
            e.preventDefault()
            $('.carousel-indicators').removeClass('d-none')
            $('#loginBgCarousel').carousel(1)
          })

        var isFullsize = false

        // remove the background/carousel section
        // if you want a compact login page (without the carousel area), you should do so in your HTML
        // but in this demo, we modify HTML using JS
        $('#id-remove-carousel')
          .on('click', function(e) {
            e.preventDefault()

            $('#id-col-intro').remove() // remove the .col that contains carousel/intro
            $('#id-col-main').removeClass('col-lg-7') // remove the col-* class name for the login area

            $('#row-1')
              .addClass('justify-content-center') // so .col is centered

              .find('> .col-12') // change .col-12.col-xl-10, etc to .col-12.col-lg-6.col-xl-5 (so our login area is 50% of document width in `lg` mode , etc)        
              .removeClass('col-12 col-xl-10 offset-xl-1').addClass(!isFullsize ? 'col-12 col-lg-6 col-xl-5' : '')


            $('.col-md-8.offset-md-2.col-lg-6.offset-lg-3') // the input elements that are inside "col-sm-10 offset-sm-1 col-md-8 offset-md-2 col-lg-6 offset-lg-3" columns
              // ... remove '.col-lg-6 offset-lg-3' (will become .col-md-8)
              .removeClass('col-lg-6 offset-lg-3')

            // remove "Welcome Back" etc titles that were meant for desktop, and show the other titles that were meant for mobile (lg-) view
            // because this compact login page is similar to mobile view
            $('h4').each(function() {
              var mobileTitle = $(this).parent().next()
              if (mobileTitle.hasClass('d-lg-none')) mobileTitle.removeClass('d-lg-none').prev().remove()
            })
          })


        // make the login area fullscreen
        // if you want a fullscreen login page you should do so in your HTML
        // but in this demo, we modify HTML using JS
        $('#id-fullscreen')
          .on('click', function(e) {
            e.preventDefault()
            if (window.navigator.msPointerEnabled) $('.body-container').addClass('h-100') // for IE only

            isFullsize = true

            $('.main-container').removeClass('container')

            $('.main-content').removeClass('justify-content-center minh-100').addClass('px-4 px-lg-0')
              .children().attr('class', 'd-flex flex-column flex-lg-row flex-grow-1 my-3 m-lg-0') // removes padding classes and add d-flex, etc

            $('#row-1')
              .addClass('flex-grow-1')
              .find('> .col-12').removeClass('shadow radius-1 col-xl-10 offset-xl-1').addClass('d-lg-flex') //remove shadow, etc from, the child .col and add d-lg-flex

            $('#row-2').addClass('flex-grow-1')

            $('#id-col-intro').removeClass('col-lg-5').addClass('col-lg-4')
            $('#id-col-main').removeClass('col-lg-7 offset-2').addClass('col-lg-6 mx-auto d-flex align-items-center justify-content-center')
          })

      })
    </script>
  </body>

</html>
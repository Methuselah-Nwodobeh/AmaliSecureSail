<a name="readme-top"></a>
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://ik.imagekit.io/methuselah/Mini/Link.png?updatedAt=1709999273863">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">AmaliSecureSail</h3>

  <p align="center">
    An SSO solution for Amalitech Services
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/othneildrew/Best-README-Template">View Demo</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Report Bug</a>
    ·
    <a href="https://github.com/othneildrew/Best-README-Template/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

This project is an implementation of a Single Sign-On (SSO) provider using Spring Boot, Spring Security, and MongoDB. The SSO provider allows users to authenticate once and access multiple services and tools within your company's ecosystem without needing to log in again.

Requirements:
* User Registration
* User Login
* Token validation
* Token Expiration
* User Logout
* Forgot Password
* Role-Based Access Control
* Multi-Factor Authentication
*

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Features
* **OAuth2 Authorization Server:** Implements OAuth2 authentication and authorization flows to issue access tokens for resource access.
* **OpenID Connect Support:** Provides support for OpenID Connect, enabling secure authentication and authorization between different applications and services.
* **MongoDB Integration:** Stores user information, authentication tokens, and other relevant data in a MongoDB database.
* **User Management:** Allows users to register new accounts, update their profile information, and manage their authentication preferences.
* **Session Management:** Manages user sessions and supports features like single sign-on (SSO), session timeouts, and session invalidation.
* **Secure Communication:** Ensures secure communication using HTTPS and proper security configurations.
* **Scalability:** Built on Spring Boot and MongoDB, providing scalability and flexibility to handle large numbers of users and requests.

### Built With

These are the frameworks and tools used to build this

* [![Next][Next.js]][Next-url]
* [![React][React.js]][React-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

* Java Development Kit (JDK) installed
* MongoDB installed and running locally or accessible via a remote server/docker
* Basic understanding of Spring Boot, Spring Security, and MongoDB

### Setup

1. **Clone the repo**
   ```sh
   git clone https://github.com/Methuselah-Nwodobeh/AmaliSecureSail
   ```
2. **Configuration:**
   1. Configure MongoDB settings in `application.properties`
   2. Configure Redis settings in `application.properties`
   3. Create a `.env` check `.env.example` for reference
   4. Set system environment variables check `.env.example` for reference
3. **Build and Run:**
   ```bash
   cd AmaliSecureSail
   ./mvnw spring-boot:run
   ```
4. **Access the service:**
   1. Use an api testing platform or the provided opendocs to test the api to make sure it works as expected

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

* Register new users: Users can register new accounts using the registration form.
* Authenticate and authorize: Users can authenticate using their credentials and authorize access to various services and tools.
* Manage user sessions: Sessions are managed securely, providing features like single sign-on (SSO) and session timeouts.
* Integrate with other services: Integrate the SSO provider with other services and tools within your company's ecosystem for seamless authentication and authorization.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



[//]: # (Add other fields later)


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
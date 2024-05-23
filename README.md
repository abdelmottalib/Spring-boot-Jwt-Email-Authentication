# Spring Security with JWT and Email Validation

This project demonstrates the implementation of the latest Spring Security mechanism using JWT (JSON Web Token) for authentication, email validation with tokens, and logout functionality.

## Features

- **User Registration with Email Validation**: Users register with their email, and receive a validation token to activate their account.
- **JWT Authentication**: Secure login using JWT for token-based authentication.
- **Logout Functionality**: Token invalidation to ensure secure logout.
- **Role-based Authorization**: Secure endpoints based on user roles.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- SMTP server configuration for email validation

### Setup

#### Clone the Repository

```sh
git clone https://github.com/yourusername/your-repo-name.git
cd your-repo-name
```

#### Configure Application Properties

Update the `src/main/resources/application.yml` file with your database and email server configurations.

```yaml
    security:
      jwt:
        secret-key: Your secret Key
        expiration: Your intended expiration
```

#### Run the docker compose 
This runs postgres and maildev continers
```sh
  docker-compose up -d
```

#### Run the Application

```sh
mvn spring-boot:run
```

The application will start on [http://localhost:8088](http://localhost:8088).

## API Endpoints

- **Register**: `POST api/v1/auth/register`
- **Authenticate**: `POST api/v1/auth/authenticate`
- **Activate Account**: `GET api/v1/auth/activate-account`
- **Logout**: `POST api/v1/auth/logout`
- - **movies**: `POST api/v1/movies`  #Test endpoint to test if the endpoint is working

## Project Structure

```bash
src/
└── main/
    ├── java/
    │   └── com/
    │       └── konami/
    │           ├── auth/                     # Authentication and Authorization
    │           ├── role/                     # User Roles
    │           ├── email/                    # Email validation configuration
    │           ├── handler/                  # Global exception handler
    │           ├── security/                 # Spring security configuration and jwt filter
    │           ├── movies/                   # Test Endpoint
    │           ├── config/                   # Security and application configuration
    │           └── user/                     # User management
    ├── resources/
    │   ├── application.yml
    │   ├── application-dev.yml
    └── test/
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -m 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

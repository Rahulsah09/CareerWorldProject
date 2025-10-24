# Career Guidance System - Authentication Setup Guide

## Overview
This guide provides complete instructions to set up and run the Career Guidance System with the new login/signup authentication system.

## Prerequisites
- Java 17+
- MySQL 8.0+
- Node.js 16+
- Maven 3.6+

## Backend Setup Instructions

### 1. Database Setup
1. Start MySQL server
2. Create the database:
   ```sql
   CREATE DATABASE career_guidance_db;
   ```
3. Run the user table creation script:
   ```bash
   mysql -u root -p career_guidance_db < database/create_user_table.sql
   ```

### 2. Backend Configuration
The backend is already configured with:
- JWT token expiration: 24 hours
- Database connection to MySQL
- CORS enabled for frontend (localhost:3000)
- Security configuration for authentication

### 3. Start Backend Server
```bash
cd backend/career-guidance
./mvnw spring-boot:run
```
Or on Windows:
```bash
cd backend/career-guidance
mvnw.cmd spring-boot:run
```

The backend will start on `http://localhost:8080`

## Frontend Setup Instructions

### 1. Install Dependencies
```bash
cd frontend1
npm install
```

### 2. Start Frontend Development Server
```bash
npm start
```

The frontend will start on `http://localhost:3000`

## System Features

### Authentication System
- **Registration**: Users can create accounts with username, email, and password
- **Login**: Users can sign in with username/email and password
- **JWT Tokens**: Secure authentication with 24-hour token expiration
- **Password Security**: BCrypt encryption, strength validation
- **Form Validation**: Real-time client-side and server-side validation
- **Protected Routes**: Dashboard and profile pages require authentication

### API Endpoints

#### Authentication Endpoints
- `POST /api/auth/signup` - User registration
- `POST /api/auth/login` - User login
- `GET /api/auth/profile` - Get current user profile (requires authentication)

#### Existing Endpoints (unchanged)
- `GET /api/careers` - Get all careers
- `GET /api/careers/{id}` - Get specific career
- `POST /api/assessments` - Submit career assessment
- `POST /api/assessments/recommendations` - Get career recommendations

### Frontend Pages
- **Home** (`/`) - Landing page with feature overview
- **Login/Signup** (`/auth`) - Authentication page with toggle between forms
- **Dashboard** (`/dashboard`) - Protected user dashboard
- **Careers** (`/careers`) - Browse all careers
- **Assessment** (`/assessment`) - Career assessment form
- **Career Details** (`/career/:id`) - Individual career information

## Testing the System

### 1. Test User Registration
1. Navigate to `http://localhost:3000/auth`
2. Click "Sign up" to switch to registration form
3. Fill in the form:
   - Username: testuser123
   - Email: test@example.com
   - Password: Password123
   - Confirm Password: Password123
4. Click "Create Account"
5. Should redirect to dashboard upon success

### 2. Test User Login
1. Navigate to `http://localhost:3000/auth`
2. Use the login form with:
   - Username: testuser123 (or test@example.com)
   - Password: Password123
3. Click "Sign In"
4. Should redirect to dashboard upon success

### 3. Test Protected Routes
1. Try accessing `http://localhost:3000/dashboard` without logging in
2. Should redirect to login page
3. Login and access dashboard - should work

### 4. Test Logout
1. From the dashboard or any page when logged in
2. Click "Logout" in the navbar
3. Should clear session and redirect to home page

## Password Requirements
- Minimum 8 characters
- At least 1 uppercase letter
- At least 1 lowercase letter
- At least 1 number
- Password strength indicator shows during signup

## Username Requirements
- Minimum 3 characters
- Only letters, numbers, and underscores allowed
- Must be unique

## Email Requirements
- Valid email format
- Must be unique

## Default Test Users
The system creates these test users by default:
- Username: `testuser`, Password: `Password123`
- Username: `admin`, Password: `Password123`

## Security Features
- JWT tokens stored in localStorage
- Automatic token validation on protected routes
- CORS protection
- Password encryption with BCrypt
- SQL injection protection with JPA
- Input validation on both client and server
- Secure headers and CSRF protection disabled for stateless JWT

## Troubleshooting

### Backend Issues
1. **Database Connection Error**: Check MySQL is running and credentials are correct
2. **Port 8080 in use**: Stop other applications using port 8080
3. **JWT Secret Error**: The system generates its own secret key automatically

### Frontend Issues
1. **Login/Signup not working**: Check backend is running on port 8080
2. **CORS errors**: Ensure backend CORS is configured for localhost:3000
3. **Token expired**: Login again to get a new token

### Common Fixes
1. Clear browser localStorage if experiencing auth issues
2. Restart both frontend and backend servers
3. Check browser console for detailed error messages

## File Structure
```
CareerWorldProject/
├── backend/career-guidance/
│   ├── src/main/java/com/career_guidance/
│   │   ├── controller/AuthController.java
│   │   ├── model/User.java
│   │   ├── dto/
│   │   ├── service/
│   │   ├── repository/
│   │   └── security/
│   └── src/main/resources/application.properties
├── frontend1/
│   ├── src/
│   │   ├── components/
│   │   │   ├── Auth.js
│   │   │   ├── Dashboard.js
│   │   │   ├── ProtectedRoute.js
│   │   │   └── ...
│   │   └── services/api.js
│   └── public/index.html
└── database/create_user_table.sql
```

## Next Steps
After setting up the authentication system, you can:
1. Extend user profiles with additional fields
2. Add "Forgot Password" functionality
3. Implement email verification
4. Add role-based access control
5. Create admin dashboard
6. Add user avatar uploads
7. Implement OAuth login (Google, GitHub, etc.)

## Support
If you encounter any issues, check:
1. All services are running on correct ports
2. Database connection is established
3. No conflicting applications on ports 3000/8080
4. Browser console for JavaScript errors
5. Backend logs for server errors
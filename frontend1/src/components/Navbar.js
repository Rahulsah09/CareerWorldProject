import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { authAPI } from '../services/api';

const Navbar = () => {
  const navigate = useNavigate();
  const isAuthenticated = localStorage.getItem('token') !== null;
  const currentUser = JSON.parse(localStorage.getItem('user') || '{}');

  const handleLogout = () => {
    authAPI.logout().then(() => {
      navigate('/');
    });
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
      <div className="container">
        <Link className="navbar-brand" to="/">
          <i className="fas fa-briefcase me-2"></i>
          CareerCompass
        </Link>
        
        <button 
          className="navbar-toggler" 
          type="button" 
          data-bs-toggle="collapse" 
          data-bs-target="#navbarNav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        
        <div className="collapse navbar-collapse" id="navbarNav">
          <div className="navbar-nav me-auto">
            <Link className="nav-link" to="/">Home</Link>
            <Link className="nav-link" to="/careers">Careers</Link>
            <Link className="nav-link" to="/assessment">Career Assessment</Link>
            {isAuthenticated && (
              <Link className="nav-link" to="/dashboard">Dashboard</Link>
            )}
          </div>
          
          <div className="navbar-nav">
            {isAuthenticated ? (
              <div className="d-flex align-items-center">
                <span className="navbar-text me-3">
                  <i className="fas fa-user me-1"></i>
                  Welcome, {currentUser?.username}!
                </span>
                <button 
                  className="btn btn-outline-light btn-sm" 
                  onClick={handleLogout}
                >
                  <i className="fas fa-sign-out-alt me-1"></i>
                  Logout
                </button>
              </div>
            ) : (
              <Link className="btn btn-outline-light" to="/auth">
                <i className="fas fa-sign-in-alt me-1"></i>
                Login
              </Link>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;

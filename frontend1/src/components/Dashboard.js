import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Dashboard = () => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserProfile = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          navigate('/auth');
          return;
        }

        // Set up axios with authorization header
        const response = await axios.get('http://localhost:8080/api/auth/profile', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });

        setUser(response.data);
      } catch (error) {
        console.error('Failed to fetch user profile:', error);
        if (error.response && error.response.status === 401) {
          // Token is invalid or expired
          localStorage.removeItem('token');
          localStorage.removeItem('user');
          navigate('/auth');
        } else {
          setError('Failed to load user profile');
        }
      } finally {
        setLoading(false);
      }
    };

    // Also get user from localStorage as backup
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }

    fetchUserProfile();
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    navigate('/auth');
  };

  const navigateTo = (path) => {
    navigate(path);
  };

  if (loading) {
    return (
      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-8">
            <div className="text-center">
              <div className="spinner-border text-primary" role="status">
                <span className="visually-hidden">Loading...</span>
              </div>
              <p className="mt-3">Loading dashboard...</p>
            </div>
          </div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container mt-5">
        <div className="alert alert-danger" role="alert">
          <h4 className="alert-heading">Error!</h4>
          <p>{error}</p>
          <hr />
          <p className="mb-0">
            <button className="btn btn-outline-danger" onClick={() => window.location.reload()}>
              Try Again
            </button>
          </p>
        </div>
      </div>
    );
  }

  return (
    <div className="container-fluid bg-light min-vh-100 py-4">
      <div className="container">
        {/* Header */}
        <div className="row">
          <div className="col-12">
            <div className="d-flex justify-content-between align-items-center mb-4">
              <div>
                <h1 className="h2 mb-1">Welcome back, {user?.username}!</h1>
                <p className="text-muted mb-0">Ready to explore your career opportunities?</p>
              </div>
              <button className="btn btn-outline-secondary" onClick={handleLogout}>
                <i className="fas fa-sign-out-alt me-2"></i>
                Logout
              </button>
            </div>
          </div>
        </div>

        {/* User Info Card */}
        <div className="row mb-4">
          <div className="col-12">
            <div className="card border-0 shadow-sm">
              <div className="card-body">
                <div className="row">
                  <div className="col-md-8">
                    <h5 className="card-title text-primary">
                      <i className="fas fa-user-circle me-2"></i>
                      Profile Information
                    </h5>
                    <div className="row">
                      <div className="col-sm-6">
                        <p className="mb-2">
                          <strong>Username:</strong> {user?.username}
                        </p>
                        <p className="mb-2">
                          <strong>Email:</strong> {user?.email}
                        </p>
                      </div>
                      <div className="col-sm-6">
                        <p className="mb-2">
                          <strong>Role:</strong> 
                          <span className="badge bg-primary ms-2">{user?.role}</span>
                        </p>
                        <p className="mb-2">
                          <strong>Account Status:</strong> 
                          <span className="badge bg-success ms-2">Active</span>
                        </p>
                      </div>
                    </div>
                  </div>
                  <div className="col-md-4 text-md-end">
                    <div className="bg-primary rounded-circle d-inline-flex align-items-center justify-content-center" 
                         style={{ width: '80px', height: '80px' }}>
                      <i className="fas fa-user text-white fa-2x"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Quick Actions */}
        <div className="row mb-4">
          <div className="col-12">
            <h3 className="h4 mb-3">Quick Actions</h3>
          </div>
          <div className="col-md-4 mb-3">
            <div className="card h-100 border-0 shadow-sm hover-shadow">
              <div className="card-body text-center">
                <div className="bg-success rounded-circle d-inline-flex align-items-center justify-content-center mb-3" 
                     style={{ width: '60px', height: '60px' }}>
                  <i className="fas fa-clipboard-list text-white fa-2x"></i>
                </div>
                <h5 className="card-title">Take Assessment</h5>
                <p className="card-text text-muted">
                  Discover your ideal career path with our comprehensive assessment.
                </p>
                <button 
                  className="btn btn-success"
                  onClick={() => navigateTo('/assessment')}
                >
                  Start Assessment
                </button>
              </div>
            </div>
          </div>
          <div className="col-md-4 mb-3">
            <div className="card h-100 border-0 shadow-sm hover-shadow">
              <div className="card-body text-center">
                <div className="bg-info rounded-circle d-inline-flex align-items-center justify-content-center mb-3" 
                     style={{ width: '60px', height: '60px' }}>
                  <i className="fas fa-briefcase text-white fa-2x"></i>
                </div>
                <h5 className="card-title">Browse Careers</h5>
                <p className="card-text text-muted">
                  Explore our database of career opportunities and requirements.
                </p>
                <button 
                  className="btn btn-info"
                  onClick={() => navigateTo('/careers')}
                >
                  View Careers
                </button>
              </div>
            </div>
          </div>
          <div className="col-md-4 mb-3">
            <div className="card h-100 border-0 shadow-sm hover-shadow">
              <div className="card-body text-center">
                <div className="bg-warning rounded-circle d-inline-flex align-items-center justify-content-center mb-3" 
                     style={{ width: '60px', height: '60px' }}>
                  <i className="fas fa-home text-white fa-2x"></i>
                </div>
                <h5 className="card-title">Go Home</h5>
                <p className="card-text text-muted">
                  Return to the main page to see all available features.
                </p>
                <button 
                  className="btn btn-warning"
                  onClick={() => navigateTo('/')}
                >
                  Go Home
                </button>
              </div>
            </div>
          </div>
        </div>

        {/* Recent Activity */}
        <div className="row">
          <div className="col-12">
            <div className="card border-0 shadow-sm">
              <div className="card-header bg-white">
                <h5 className="card-title mb-0">
                  <i className="fas fa-chart-line me-2 text-primary"></i>
                  Welcome to Career Guidance
                </h5>
              </div>
              <div className="card-body">
                <div className="row">
                  <div className="col-md-6">
                    <h6 className="text-primary">Getting Started</h6>
                    <ul className="list-unstyled">
                      <li className="mb-2">
                        <i className="fas fa-check-circle text-success me-2"></i>
                        Account created successfully
                      </li>
                      <li className="mb-2">
                        <i className="fas fa-clock text-warning me-2"></i>
                        Complete your first career assessment
                      </li>
                      <li className="mb-2">
                        <i className="fas fa-clock text-warning me-2"></i>
                        Browse career recommendations
                      </li>
                    </ul>
                  </div>
                  <div className="col-md-6">
                    <h6 className="text-primary">System Status</h6>
                    <p className="text-muted small">
                      All systems are operational. Your data is secure and your session is active.
                    </p>
                    <p className="small mb-0">
                      <strong>Last Login:</strong> <span className="text-muted">Just now</span>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <style jsx>{`
        .hover-shadow:hover {
          box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15) !important;
          transform: translateY(-2px);
          transition: all 0.3s ease;
        }
        .min-vh-100 {
          min-height: 100vh;
        }
      `}</style>
    </div>
  );
};

export default Dashboard;
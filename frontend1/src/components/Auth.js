import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Auth = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: ''
  });
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    // Clear error for this field when user starts typing
    if (errors[name]) {
      setErrors(prev => ({
        ...prev,
        [name]: ''
      }));
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.username.trim()) {
      newErrors.username = 'Username is required';
    } else if (formData.username.length < 3) {
      newErrors.username = 'Username must be at least 3 characters';
    } else if (!/^[a-zA-Z0-9_]+$/.test(formData.username)) {
      newErrors.username = 'Username can only contain letters, numbers, and underscores';
    }

    if (!isLogin && (!formData.email.trim())) {
      newErrors.email = 'Email is required';
    } else if (!isLogin && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = 'Please enter a valid email address';
    }

    if (!formData.password) {
      newErrors.password = 'Password is required';
    } else if (!isLogin && formData.password.length < 8) {
      newErrors.password = 'Password must be at least 8 characters';
    } else if (!isLogin && !/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/.test(formData.password)) {
      newErrors.password = 'Password must contain at least one uppercase letter, one lowercase letter, and one number';
    }

    if (!isLogin && formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = 'Passwords do not match';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const getPasswordStrength = (password) => {
    let strength = 0;
    if (password.length >= 8) strength++;
    if (/[a-z]/.test(password)) strength++;
    if (/[A-Z]/.test(password)) strength++;
    if (/\d/.test(password)) strength++;
    if (/[!@#$%^&*(),.?":{}|<>]/.test(password)) strength++;
    return strength;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    
    if (!validateForm()) {
      return;
    }

    setLoading(true);
    
    try {
      const endpoint = isLogin ? '/api/auth/login' : '/api/auth/signup';
      const payload = isLogin 
        ? { username: formData.username, password: formData.password }
        : {
            username: formData.username,
            email: formData.email,
            password: formData.password,
            confirmPassword: formData.confirmPassword
          };

      const response = await axios.post(`http://localhost:8080${endpoint}`, payload);
      
      if (response.data.token) {
        // Store token in localStorage
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('user', JSON.stringify({
          username: response.data.username,
          email: response.data.email,
          role: response.data.role
        }));
        
        setMessage(response.data.message || 'Success!');
        
        // Redirect to dashboard after a short delay
        setTimeout(() => {
          navigate('/dashboard');
        }, 1000);
      } else {
        setMessage(response.data.message || 'Operation completed successfully');
      }
      
    } catch (error) {
      console.error('Auth error:', error);
      if (error.response) {
        if (error.response.data && typeof error.response.data === 'object' && !error.response.data.message) {
          // Handle validation errors
          setErrors(error.response.data);
        } else {
          setMessage(error.response.data.message || 'An error occurred');
        }
      } else {
        setMessage('Network error. Please try again.');
      }
    } finally {
      setLoading(false);
    }
  };

  const toggleMode = () => {
    setIsLogin(!isLogin);
    setFormData({
      username: '',
      email: '',
      password: '',
      confirmPassword: ''
    });
    setErrors({});
    setMessage('');
  };

  const passwordStrength = getPasswordStrength(formData.password);
  const strengthColors = ['bg-secondary', 'bg-danger', 'bg-warning', 'bg-info', 'bg-success'];
  const strengthLabels = ['Very Weak', 'Weak', 'Fair', 'Good', 'Strong'];

  return (
    <div className="container-fluid" style={{ 
      minHeight: '100vh', 
      background: 'linear-gradient(135deg, #f8f9fa 0%, #e3f2fd 100%)', 
      paddingTop: '60px', 
      paddingBottom: '60px' 
    }}>
      <div className="row justify-content-center">
        <div className="col-md-6 col-lg-4">
          <div className="text-center mb-4">
            <h2 className="display-6 fw-bold mb-2 text-primary">
              {isLogin ? 'Welcome Back' : 'Create Account'}
            </h2>
            <p className="text-muted">
              {isLogin ? 'Sign in to your Career Guidance account' : 'Join us to discover your ideal career path'}
            </p>
          </div>

          <div className="card shadow-lg border-0">
            <div className="card-body p-4">
              <form onSubmit={handleSubmit}>
                {message && (
                  <div className={`alert ${message.includes('successful') || message.includes('Success') 
                    ? 'alert-success' : 'alert-danger'} alert-dismissible fade show`} role="alert">
                    {message}
                  </div>
                )}

                {/* Username Field */}
                <div className="mb-3">
                  <label htmlFor="username" className="form-label fw-semibold">
                    Username
                  </label>
                  <input
                    type="text"
                    id="username"
                    name="username"
                    value={formData.username}
                    onChange={handleInputChange}
                    className={`form-control ${errors.username ? 'is-invalid' : ''}`}
                    placeholder="Enter your username"
                    required
                  />
                  {errors.username && <div className="invalid-feedback">{errors.username}</div>}
                </div>

                {/* Email Field (Signup only) */}
                {!isLogin && (
                  <div className="mb-3">
                    <label htmlFor="email" className="form-label fw-semibold">
                      Email Address
                    </label>
                    <input
                      type="email"
                      id="email"
                      name="email"
                      value={formData.email}
                      onChange={handleInputChange}
                      className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                      placeholder="Enter your email"
                      required={!isLogin}
                    />
                    {errors.email && <div className="invalid-feedback">{errors.email}</div>}
                  </div>
                )}

                {/* Password Field */}
                <div className="mb-3">
                  <label htmlFor="password" className="form-label fw-semibold">
                    Password
                  </label>
                  <div className="input-group">
                    <input
                      type={showPassword ? 'text' : 'password'}
                      id="password"
                      name="password"
                      value={formData.password}
                      onChange={handleInputChange}
                      className={`form-control ${errors.password ? 'is-invalid' : ''}`}
                      placeholder="Enter your password"
                      required
                    />
                    <button
                      type="button"
                      className="btn btn-outline-secondary"
                      onClick={() => setShowPassword(!showPassword)}
                    >
                      {showPassword ? (
                        <i className="fas fa-eye-slash"></i>
                      ) : (
                        <i className="fas fa-eye"></i>
                      )}
                    </button>
                    {errors.password && <div className="invalid-feedback">{errors.password}</div>}
                  </div>
                  
                  {/* Password Strength Indicator (Signup only) */}
                  {!isLogin && formData.password && (
                    <div className="mt-2">
                      <div className="progress" style={{ height: '6px' }}>
                        <div 
                          className={`progress-bar ${strengthColors[passwordStrength] || 'bg-secondary'}`}
                          role="progressbar" 
                          style={{ width: `${(passwordStrength / 5) * 100}%` }}
                        ></div>
                      </div>
                      <small className="text-muted">
                        Password strength: {strengthLabels[passwordStrength - 1] || 'Very Weak'}
                      </small>
                    </div>
                  )}
                </div>

                {/* Confirm Password Field (Signup only) */}
                {!isLogin && (
                  <div className="mb-3">
                    <label htmlFor="confirmPassword" className="form-label fw-semibold">
                      Confirm Password
                    </label>
                    <div className="input-group">
                      <input
                        type={showConfirmPassword ? 'text' : 'password'}
                        id="confirmPassword"
                        name="confirmPassword"
                        value={formData.confirmPassword}
                        onChange={handleInputChange}
                        className={`form-control ${errors.confirmPassword ? 'is-invalid' : ''}`}
                        placeholder="Confirm your password"
                        required
                      />
                      <button
                        type="button"
                        className="btn btn-outline-secondary"
                        onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                      >
                        {showConfirmPassword ? (
                          <i className="fas fa-eye-slash"></i>
                        ) : (
                          <i className="fas fa-eye"></i>
                        )}
                      </button>
                      {errors.confirmPassword && <div className="invalid-feedback">{errors.confirmPassword}</div>}
                    </div>
                  </div>
                )}

                {/* Submit Button */}
                <button
                  type="submit"
                  disabled={loading}
                  className={`btn w-100 py-2 ${loading ? 'btn-secondary' : 'btn-primary'}`}
                >
                  {loading ? (
                    <>
                      <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                      Processing...
                    </>
                  ) : (
                    isLogin ? 'Sign In' : 'Create Account'
                  )}
                </button>

                {/* Forgot Password Link (Login only) */}
                {isLogin && (
                  <div className="text-center mt-3">
                    <button
                      type="button"
                      className="btn btn-link text-decoration-none p-0"
                      onClick={() => alert('Forgot Password functionality would be implemented here')}
                    >
                      <small>Forgot your password?</small>
                    </button>
                  </div>
                )}
              </form>

              {/* Toggle between Login and Signup */}
              <hr className="my-4" />
              <div className="text-center">
                <p className="mb-0">
                  <small className="text-muted">
                    {isLogin ? "Don't have an account? " : "Already have an account? "}
                  </small>
                  <button
                    type="button"
                    onClick={toggleMode}
                    className="btn btn-link text-decoration-none p-0 fw-semibold"
                  >
                    {isLogin ? 'Sign up' : 'Sign in'}
                  </button>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Auth;
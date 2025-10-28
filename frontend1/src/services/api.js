import axios from 'axios';

// Create axios instance
const api = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000,
});

// Request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor to handle auth errors
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      // Token is invalid or expired
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      // Redirect to login
      window.location.href = '/auth';
    }
    return Promise.reject(error);
  }
);

// Auth API methods
export const authAPI = {
  login: (credentials) => api.post('/api/auth/login', credentials),
  signup: (userData) => api.post('/api/auth/signup', userData),
  getProfile: () => api.get('/api/auth/profile'),
  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    return Promise.resolve();
  }
};

// Career API methods
export const careerAPI = {
  getAllCareers: () => api.get('/api/careers'),
  getCareerById: (id) => api.get(`/api/careers/${id}`),
  createCareer: (careerData) => api.post('/api/careers', careerData),
  reinitializeCareers: () => api.post('/api/careers/reinitialize')
};

// Assessment API methods
export const assessmentAPI = {
  submitAssessment: (assessmentData) => api.post('/api/assessments', assessmentData),
  getRecommendations: (assessmentData) => api.post('/api/assessments/recommendations', assessmentData),
  getUserAssessments: (email) => api.get(`/api/assessments/user/${email}`)
};

// User utility functions
export const userUtils = {
  isAuthenticated: () => {
    const token = localStorage.getItem('token');
    return !!token;
  },
  
  getCurrentUser: () => {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  },
  
  getToken: () => {
    return localStorage.getItem('token');
  },
  
  setUser: (userData) => {
    if (userData.token) {
      localStorage.setItem('token', userData.token);
    }
    localStorage.setItem('user', JSON.stringify({
      username: userData.username,
      email: userData.email,
      role: userData.role
    }));
  },
  
  clearAuth: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }
};

export default api;
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from './components/Navbar';
import Home from './components/Home';
import CareerList from './components/CareerList';
import CareerAssessment from './components/CareerAssessment';
import CareerDetails from './components/CareerDetails';
import Auth from './components/Auth';
import Dashboard from './components/Dashboard';
import ProtectedRoute from './components/ProtectedRoute';
import { authAPI } from './services/api';

function App() {
  const isAuthenticated = localStorage.getItem('token') !== null;

  return (
    <Router>
      <div className="App">
        {/* Only show Navbar if not on auth page */}
        <Routes>
          <Route path="/auth" element={<Auth />} />
          <Route path="/*" element={
            <>
              <Navbar />
              <div className="mt-4">
                <Routes>
                  <Route path="/" element={<div className="container"><Home /></div>} />
                  <Route path="/careers" element={<CareerList />} />
                  <Route path="/assessment" element={<div className="container"><CareerAssessment /></div>} />
                  <Route path="/career/:id" element={<div className="container"><CareerDetails /></div>} />
                  <Route path="/dashboard" element={
                    <ProtectedRoute>
                      <Dashboard />
                    </ProtectedRoute>
                  } />
                </Routes>
              </div>
            </>
          } />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
